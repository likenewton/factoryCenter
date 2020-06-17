import Api from 'assets/js/api.js'
import router from '../router'
import menuRoute from '../router/menuRoute.js'

export default {
  // 菜单权限(同时生成动态路由)
  getAuthMenu(context) {
    _axios.send({
      method: 'get',
      // url: '../../static/authMenu.json',
      url: _axios.ajaxAd.getAuthMenu,
      done: ((res) => {
        context.commit('SET_DATA', {
          key: 'authMenu',
          value: Api.UNITS.getAuthMenu(Api.STATIC.asideData, res.data || [])
        })
        router.addRoutes([Api.UNITS.getMenuRoute(menuRoute, res.data || [])]) // 生成动态路由
      })
    })
  },
  // 功能按钮权限列表（所有页面）
  getAuthButtons(context) {
    _axios.send({
      method: 'get',
      url: _axios.ajaxAd.getAuthButtons,
      done: ((res) => {
        context.commit('SET_DATA', {
          key: 'authButtons',
          value: res.data || []
        })
      })
    })
  }, 
  // 获取用户信息
  getUserInfo(context) {
    _axios.send({
      method: 'get',
      url: _axios.ajaxAd.getLoginInfo,
      done: (res) => {
        context.commit('SET_DATA', {
          key: 'userInfo',
          value: res.data
        })
      }
    })
  },  
  // 获取机构列表
  getOrgs(context) {
    _axios.send({
      method: 'get',
      url: _axios.ajaxAd.getBrandList,
      done: (res) => {
        context.commit('SET_DATA', {
          key: 'orgs',
          value: res.data
        })
      }
    })
  },  
  // 云智码字典库列表
  getYunovoDic(context) {
    _axios.send({
      method: 'get',
      url: _axios.ajaxAd.yunovoDic,
      done: (res) => {
        context.commit('SET_DATA', {
          key: 'yunovoDic',
          value: res.data
        })
      }
    })
  },
}
