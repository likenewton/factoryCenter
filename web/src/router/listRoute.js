// [chunk2](侧边栏菜单动态路由)
const Container = r => require.ensure([], () => r(require('@/components/container/index.vue')), 'chunk1')

// [chunk4](一般是第三级页面，菜单只有两级)
const DeliverGoodsDetail = r => require.ensure([], () => r(require('@/components/pages/shipments/deliverGoodsDetail.vue')), 'chunk4') // 发货详情页
const PatchDetail = r => require.ensure([], () => r(require('@/components/pages/production/patchDetail.vue')), 'chunk4') // 贴片详情页


let ListRoute = {
  // 列表文件
  path: '/list',
  name: 'list',
  component: Container,
  children: [{
    path: 'deliverGoodsDetail',
    name: 'deliverGoodsDetail',
    component: DeliverGoodsDetail
  }, {
    path: 'patchDetail',
    name: 'patchDetail',
    component: PatchDetail
  }]
}

export default ListRoute
