import getters from './getter'
import mutations from './mutations'
import actions from './actions'

Vue.use(Vuex)

const state = {
  // 页面基础
  isLogin: false,
  asideCollapse: false, // 侧边栏折叠状态
  asideFlag: [], // 面包屑地址导航数据，配合static.js中的数据使用，用于同步面包屑与侧边栏在选中菜单层级的一致性
  dialogVisible: false, // 公共dialog是否显示
  authMenu: [], // 授权可以展示的菜单
  authButtons: {}, // 授权页面中的功能按钮权限
  yunovoDic: [], // 云智码列表
  userInfo: {}, // 用户信息
  orgs: [], // 机构列表
  siteList: [], // 站点列表
  deviceType: [{ label: '后视镜', value: 0 }, { label: '大屏车机', value: 1 }, { label: '行车记录仪', value: 2 }, { label: '未知', value: -1 }],
}

export default new Vuex.Store({
  state,
  getters,
  mutations,
  actions
})
