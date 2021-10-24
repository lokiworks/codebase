import { Settings as LayoutSettings } from '@ant-design/pro-layout';


// type ProSettings = PureSettings & RenderSetting;
const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'light',
  // 拂晓蓝
  primaryColor: '#1890ff',
  layout: 'mix',
  contentWidth: 'Fluid',
  fixedHeader: false,
  fixSiderbar: true,
  colorWeak: false,
  title: '交易管理平台',
  pwa: false,
  logo: 'logo.svg',
  iconfontUrl: '',
};

export default Settings;
