""""
using python  version 3.7+
python-gitlab version 1.8.0
python-jenkins version 1.7.0

"""

import gitlab
import jenkins
import smtplib
import ssl
import markdown
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText


class JenkinsAuto:
    __host = ''
    __user_name = ''
    __password = ''

    def __init__(self, host, user_name, password):
        self.__host = host
        self.__user_name = user_name
        self.__password = password

    def build(self, buildList, job_suffix=None):
        server = jenkins.Jenkins(self.__host, username=self.__user_name, password=self.__password)
        for service in (buildList or []):
            job_name = service.name
            if job_suffix:
                job_name += '_{}'.format(job_suffix)
                job_name = job_name.upper()
            if server.job_exists(job_name):
                server.build_job(job_name)


class GitLabProject:
    name = ''
    url = ''

    def __init__(self, name, url):
        self.name = name
        self.url = url


class GitLabAuto:
    __host = ''
    __token = ''

    """
    Gitlab Merge Error Code 
    """
    # merge conflict
    __merge_conflict = 406
    # merge request already exists
    __merge_requst_alreay_exists = 409
    # merge no changes
    __merge_no_chagnes = 405
    __merge_success = 200

    def __init__(self, host, token):
        self.__host = host
        self.__token = token

    def merge(self, group, target_branch):
        gl = gitlab.Gitlab(self.__host, private_token=self.__token)
        gl_group = gl.groups.get(group)
        # list all projects of the group
        gl_projects = gl_group.projects.list(all=True)
        mr_conflict_list = []
        mr_succeed_list = []
        for project in gl_projects:
            project_name = project.attributes['name']
            project_url = project.attributes['web_url']
            project_id = project.attributes['id']

            gl_project = gl.projects.get(project_id)
            status = self.create_merge_request(gl_project, target_branch)
            if status == self.__merge_conflict:
                mr_conflict_list.append(GitLabProject(project_name, project_url))
            elif status == self.__merge_success:
                mr_succeed_list.append(GitLabProject(project_name, project_url))
            else:
                # ignore it
                pass

        return mr_succeed_list, mr_conflict_list

    def create_merge_request(self, project, target_branch):
        """

        :param project:
        :param target_branch:
        :return:
                200: merge succeed
                406: merge conflict
        """
        mr = None

        # warning !!! dont change it
        source_branch = 'master'
        try:
            compare_result = project.repository_compare(target_branch, source_branch)
            if not compare_result['commits']:
                return  self.__merge_no_chagnes
            mr = project.mergerequests.create({'source_branch': source_branch,
                                               'target_branch': target_branch,
                                               'title': 'merge latest code',
                                               'labels': ['automation']})
            mr.merge(merge_commit_message="accept by system")
        except gitlab.GitlabError as e:
            if e.response_code == self.__merge_no_chagnes:
                # if no changes, close merge request
                mr.state_event = 'close'
                mr.save()
            elif e.response_code == self.__merge_requst_alreay_exists:
                # try to merge it
                merge_accept_result = self.__accept_merge_request(project, source_branch, target_branch)
                if not merge_accept_result:
                    return self.__merge_conflict
            elif e.response_code == self.__merge_conflict:
                return e.response_code
            else:
                print("project name:{} unknown error code:{}, message:{}".format(project.attributes['name'],
                                                                                 e.response_code, e.response_body))
            return e.response_code

        return self.__merge_success

    def __accept_merge_request(self, project, source, target):
        """
        merge exist request
        :param project: project object
        :param source:  source branch
        :param target:  target branch
        """
        # check input
        # nothing to do

        mrs = project.mergerequests.list()
        for mr in (mrs or []):
            source_branch = mr.attributes['source_branch']
            target_branch = mr.attributes['target_branch']

            if source == source_branch and target == target_branch:
                try:
                    mr.merge(merge_commit_message="accept by system")
                except gitlab.GitlabError as e:
                    if e.response_code == self.__merge_conflict:
                        return False
                    # anything else ignored
                    else:
                        # close it.
                        mr.state_event = 'close'
                        mr.save()

        return True


class MailAuto:
    __host = ''
    __port = ''
    __send_user_account = ''
    __send_user_password = ''
    __receive_user_account = ''

    def __init__(self, host, port, send_user_account, send_user_password, receive_user_account):
        self.__host = host
        self.__port = port
        self.__send_user_account = send_user_account
        self.__send_user_password = send_user_password
        self.__receive_user_account = receive_user_account

    def send(self, subject, succeedList, conflictList=[]):
        message = MIMEMultipart("alternative")
        message["Subject"] = subject
        message["From"] = self.__send_user_account
        message["To"] = self.__receive_user_account
        html_content = self.__assemble_html_text(succeedList, conflictList)
        html_text = MIMEText(html_content, "html")
        message.attach(html_text)

        content = ssl.create_default_context()
        if not content:
            return

        with smtplib.SMTP_SSL(self.__host, self.__port, content) as server:
            server.login(self.__send_user_account, self.__send_user_password)
            server.sendmail(self.__send_user_account, self.__receive_user_account, message.as_string())

    def __assemble_html_text(self, succeedList, conflictList):
        msg = ''
        if succeedList:
            context = ''
            for succeed in succeedList:
                context += "1. [{}]({})\n".format(succeed.name, succeed.url)

            msg += u"""## 合并成功列表\n""" + context

        if conflictList:
            context = ''
            for conflict in conflictList:
                context += "1. [{}]({})\n".format(conflict.name, conflict.url)
            if msg:
                msg.join("\n\n")
            msg += u"""## 合并冲突列表\n""" + context
        return markdown.markdown(msg)

def main():
    """
    Gitlab Account Information
    """
    # gitlab host
    gitlab_host = 'http://localhost:82'
    # gitlab token
    gitlab_token = '123456'
    # group name
    gitlab_group = 'grp'
    # merge target branch
    gitlab_target_branch = 'dev'

    """
    Jenkins Account Information
    """
    jenkins_host = 'http://localhost:8080/'
    jenkins_user_name = 'dev'
    jenkins_user_password = '123456'
    jenkins_job_suffix = 'xxx'

    """
    Email Account Information
    """
    email_host = 'localhost'
    email_port = '465'
    email_send_user_account = 'xxx'
    email_send_user_password = '123456'
    email_receive_user_account = 'xxx'

    gla = GitLabAuto(gitlab_host, gitlab_token)

    # merge request
    mr_succeed_list, mr_conflict_list = gla.merge(gitlab_group, gitlab_target_branch)

    ja = JenkinsAuto(jenkins_host, jenkins_user_name, jenkins_user_password)
    # build jobs
    ja.build(mr_succeed_list, jenkins_job_suffix)

    # send mail
    ma = MailAuto(email_host, email_port, email_send_user_account, email_send_user_password, email_receive_user_account)
    ma.send('GitLab Merge Request', mr_succeed_list, mr_conflict_list)


if __name__ == '__main__':
    main()
