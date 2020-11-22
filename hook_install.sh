#!/usr/bin/env bash

set -eu

msg_color_error='\033[31m'
msg_color_success='\033[32m'
msg_color_warning='\033[33m'
msg_color_none='\033[0m'

function chmod_hooks_scripts
{
    echo "${msg_color_warning}Begin chmod_hooks_scripts ...${msg_color_none} \n"
    HOOK_TEMPLATE_DIR=$(git rev-parse --show-toplevel)/.githooks
    for file in $1
    do
        chmod +x "${HOOK_TEMPLATE_DIR}/${file}"
    done
    echo "${msg_color_success}chmod_hooks_scripts success ${msg_color_none} \n"
}


function link_hooks_scripts
{
    echo "${msg_color_warning}Begin link_hooks_scripts ...${msg_color_none} \n"
    CUR_DIR=`pwd`
    HOOK_DIR=$(git rev-parse --show-toplevel)/.git/hooks
    for file in $1
    do
        ln -s $CUR_DIR/.githooks/$file $HOOK_DIR/$file  
        echo "${msg_color_success} ${file} link  to ${HOOK_DIR}/${file} ${msg_color_none} \n"
    done
    echo "${msg_color_success}link_hooks_scripts success ${msg_color_none} \n"
}


#applypatch-msg commit-msg post-applypatch post-checkout post-commit post-merge post-receive pre-applypatch pre-auto-gc pre-commit prepare-commit-msg pre-rebase pre-receive update pre-push
hook_git_commond=commit-msg

chmod_hooks_scripts $hook_git_commond
link_hooks_scripts $hook_git_commond