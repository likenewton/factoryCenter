// [chunk2](侧边栏菜单动态路由)
const Container = r => require.ensure([], () => r(require('@/components/container/index.vue')), 'chunk1')
const Blank = r => require.ensure([], () => r(require('@/components/container/blank.vue')), 'chunk1')
// 上报数据管理
const ThreeYards = r => require.ensure([], () => r(require('@/components/pages/manage/threeYards.vue')), 'chunk2') // 三码关系
const ReportedData = r => require.ensure([], () => r(require('@/components/pages/manage/reportedData.vue')), 'chunk2') // 上报数据
const AssembleInfo = r => require.ensure([], () => r(require('@/components/pages/manage/assembleInfo.vue')), 'chunk2') // 组装信息
const SnChange = r => require.ensure([], () => r(require('@/components/pages/manage/snChange.vue')), 'chunk2') // sn变更
const SoftwareInfo = r => require.ensure([], () => r(require('@/components/pages/manage/softwareInfo.vue')), 'chunk2') // 软件信息
const DeviceLog = r => require.ensure([], () => r(require('@/components/pages/manage/deviceLog.vue')), 'chunk2') // 设备服务日志

// 数据统计管理
const RepeatReport = r => require.ensure([], () => r(require('@/components/pages/statistics/repeatReport.vue')), 'chunk2') // 重复上报统计
const ReportData = r => require.ensure([], () => r(require('@/components/pages/statistics/reportData.vue')), 'chunk2') // 上报数据统计

// 生产数据上报
const Patch = r => require.ensure([], () => r(require('@/components/pages/production/patch.vue')), 'chunk2')
const Package = r => require.ensure([], () => r(require('@/components/pages/production/package.vue')), 'chunk2')
// 发货数据上报
const Channel = r => require.ensure([], () => r(require('@/components/pages/shipments/channel.vue')), 'chunk2')
const DeliverGoods = r => require.ensure([], () => r(require('@/components/pages/shipments/deliverGoods.vue')), 'chunk2')
// 统计报表
const Sell = r => require.ensure([], () => r(require('@/components/pages/report/sell.vue')), 'chunk2')
const Truck = r => require.ensure([], () => r(require('@/components/pages/report/truck.vue')), 'chunk2')

// 这里面试所有的配置，后续根据权限动态删除一些路由
let menuRoute = {
  path: '/menu',
  name: 'menu',
  component: Container,
  redirect: '/menu/production',
  // 侧边栏菜单
  children: [{
    path: 'production',
    name: 'production',
    component: Blank,
    children: [{
      path: 'patch',
      name: 'patch',
      component: Patch
    },{
      path: 'package',
      name: 'package',
      component: Package
    }]
  }, {
    path: 'shipments',
    name: 'shipments',
    component: Blank,
    children: [{
      path: 'channel',
      name: 'channel',
      component: Channel
    },{
      path: 'deliverGoods',
      name: 'deliverGoods',
      component: DeliverGoods
    }]
  }, {
    path: 'report',
    name: 'report',
    component: Blank,
    children: [{
      path: 'sell',
      name: 'sell',
      component: Sell
    }, {
      path: 'truck',
      name: 'truck',
      component: Truck
    }]
  }]
}

export default menuRoute
