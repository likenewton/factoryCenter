import App from './App'
import router from './router'
import store from './store'
import upperFirst from 'lodash/upperFirst'
import camelCase from 'lodash/camelCase'
import Api from 'assets/js/api.js'
import MinXin from '@/components/MinXins/index.js'
import 'babel-polyfill'
import '../theme/index.css'
import '../static/iconfont/iconfont.css'
import 'assets/js/filter.js'
import 'assets/js/directive.js'

const requireComponent = require.context(
  // 其组件目录的相对路径
  './components/common',
  // 是否查询其子目录
  false,
  // 匹配基础组件文件名的正则表达式
  /[A-Z]\w+\.(vue|js)$/
)

requireComponent.keys().forEach(fileName => {
  // 获取组件配置
  const componentConfig = requireComponent(fileName)

  // 获取组件的 PascalCase 命名
  const componentName = upperFirst(
    camelCase(
      // 剥去文件名开头的 `./` 和结尾的扩展名
      fileName.replace(/^\.\/(.*)\.\w+$/, '$1')
    )
  )

  // 全局注册组件
  Vue.component(
    componentName,
    // 如果这个组件选项是通过 `export default` 导出的，
    // 那么就会优先使用 `.default`，
    // 否则回退到使用模块的根。
    componentConfig.default || componentConfig
  )
})

Vue.mixin(MinXin) // 全局混入
window._axios = Api.AXIOS.init() // 将_axios注册到全局，方便调用
Vue.prototype.$echarts = echarts
Vue.prototype.$http = axios
Vue.prototype.$store = store
Vue.config.productionTip = false

// 路由进入前的全局钩子
router.beforeEach((to, from, next) => {
  if (Api.UNITS.getQuery('iov-token')) {
    // 当页面重定向过来的时候带的token 要保存进去
    Api.UNITS.setCookie(Api.STATIC.token, Api.UNITS.getQuery('iov-token'))
    let targetHref = sessionStorage.getItem('target_href')
    sessionStorage.removeItem('target_href')
    if (targetHref) location.href = targetHref
    else location.href = location.href.split('?')[0]
  }

  // 验证是否登录
  if (!store.state.isLogin) {
    _axios.send({
      method: 'get',
      url: _axios.ajaxAd.isLogin,
      done: (res) => {
        // 这里一定登录了
        if (store.state.authMenu.length === 0) store.dispatch('getAuthMenu')
        store.commit('SET_DATA', {
          key: 'isLogin',
          value: true
        })
        next()
      }
    })
  } else {
    if (store.state.authMenu.length === 0) store.dispatch('getAuthMenu')
    next()
  }
})

new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
