import formRoute from './formRoute.js' // 表单页面
import listRoute from './listRoute.js' // 列表页面

// [chunk1](公共页面)
const Home = r => require.ensure([], () => r(require('@/components/home.vue')), 'chunk1')
const Container = r => require.ensure([], () => r(require('@/components/container/index.vue')), 'chunk1')

let router = new VueRouter({
  mode: process.env.NODE_ENV === 'development' ? 'hash' : 'history',
  base: process.env.NODE_ENV === 'development' ? '/' : '/factoryCenter/',
  routes: [{
    path: '',
    component: Container,
    children: [{
      path: '',
      name: 'home',
      component: Home
    }]
  }]
})

router.addRoutes([formRoute])
router.addRoutes([listRoute])

export default router
