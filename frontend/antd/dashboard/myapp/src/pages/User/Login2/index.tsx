import { login } from "@/services/ant-design-pro/api";
import { useEmotionCss } from "@ant-design/use-emotion-css";
import { Helmet, history, useIntl, useModel } from "@umijs/max";
import { message } from "antd";
import Settings from '../../../../config/defaultSettings';
import { useState } from "react";
import { flushSync } from "react-dom";
import { LoginForm } from "@ant-design/pro-components";

const Login: React.FC = () => {


    const containerClassName = useEmotionCss(() => {
        return {
            display: 'flex',
            flexDirection: 'column',
            height: '100vh',
            overflow: 'auto',
            backgroundImage: "url('https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/V-_oS6r-i7wAAAAAAAAAAAAAFl94AQBr')",
            backgroundSize: '100% 100%',
        };
    });

    const [userLoginState, setUserLoginState] = useState<API.LoginResult>({});
    const [type, setType] = useState<string>('account');

    const { initialState, setInitialState } = useModel('@@initialState');

    const intl = useIntl();
    const fetchUserInfo = async () => {
        const userInfo = await initialState?.fetchUserInfo?.();
        if (userInfo) {
            flushSync(() => {
                setInitialState((s) => ({
                    ...s,
                    currentUser: userInfo,
                }));
            });
        }
    };

    const handleSubmit = async (values: API.LoginParams) => {
        try {
            // 登录
            const msg = await login({ ...values, type });
            if (msg.status === 'ok') {
                const defaultLoginSuccessMessage = intl.formatMessage({ id: 'pages.login.success', defaultMessage: '登录成功！', });
                message.success(defaultLoginSuccessMessage);
                await fetchUserInfo();
                const urlParams = new URL(window.location.href).searchParams;
                history.push(urlParams.get('redirect') || '/');
                return;
            }
            console.log(msg);
            // 如果失败去设置用户错误信息
            setUserLoginState(msg);



        } catch (error) {
            const defaultLoginFailureMessage = intl.formatMessage({ id: 'pages.login.failure', defaultMessage: '登录失败，请重试！', });
            console.log(error);
            message.error(defaultLoginFailureMessage);

        }

    };

    const { status, type: loginType } = userLoginState;

    return (
        <div className={containerClassName}>
            <Helmet>
                <title>
                    {intl.formatMessage({
                        id: 'menu.login',
                        defaultMessage: '登录页'

                    })}
                    - {Settings.title}
                </title>

            </Helmet>

            <LoginForm contentStyle={{            minWidth: 280,
            maxWidth: '75vw',}}
            logo={<img alt="logo" src="/logo.svg" />}
            title="Ant Design"
            
            >

            </LoginForm>
            
        </div>

    );


};



export default Login;