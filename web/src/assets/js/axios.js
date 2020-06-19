import { token } from './static.js'
import { getCookie } from './unit.js'

class AXIOS {
  constructor(para) {
    this.data = {
      method: 'get',
      params: null,
      timeout: 60000,
      data: null, // 请求体所带的参数
      done: null, // 接口请求成功回调函数
    }
    // 实例化时初始的参数
    this.constData = Object.assign(this.data, para)
    this.env = process.env.NODE_ENV // development

    // ajax请求地址
    this.ajaxAd = {
      // 系统单点登录基础
      isLogin: '/sso/isLogin',
      getLoginInfo: '/sso/getLoginInfo',
      getAuthMenu: '/sso/menus',
      getAuthButtons: '/sso/buttons',
      sites: '/sso/sitez',
      // 首页
      getHomeTotals: '/statistics/sum/totals', // 统计选择
      getPastersEchart: '/statistics/pasters/state', // 贴片图表
      getAssemblesEchart: '/statistics/assembles/state', // 组装图表
      getShippingEchart: '/shipping/lists/state', // 发货图表
      // 上报数据管理
      threeYardsList: '/report/device/cards', // 三码关系列表
      reportedDataList: '/report/device/reports', // 上报数据列表
      assembleInfoList: '/report/packings', // 组装信息列表
      snChangeList: '/other/sn/rewrites', // sn变更列表
      softwareInfoList: '/report/softwares', // 机构信息
      deviceLogList: '/other/devices', // 设备服务日志
      repeatReportList: '/statistics/repetition/reports', // 重复上报统计
      reportDataList: '/statistics/scan/reports', // 上报数据统计
      // 生产数据上报
      getPaster: '/statistics/pasters', // 贴片上报(统计)
      getAssemble: '/statistics/assembles', // 组装上报(统计)
      getPasterDetail: '/production/device/tests', // 详情
      getTestDetail: '/production/device/test/items', // 测试详情
      // 发货数据上报
      statisticsDeliverGoods: '/statistics/shippings', // 发货管理(统计)
      getDeliverGoods: '/shipping/lists', // 发货管理
      channels: '/shipping/channels', // 渠道管理
      getDeviceDetail: '/shipping/devices', // 获取发货设备信息
      getBrandList: '/shipping/orgs', // 获取品牌信息
      getAres: '/shipping/ares', // 获取地区
      yunovoDic: '/other/dictionarys', // 云智码检索表
      // 统计报表
      getAreasEchart: '/statistics/shippings/areas', // 地区销售分布
      getSellEchart: '/statistics/shippings/areaSale', // 地区销售分布
    }
  }

  send(para) {
    let data = Object.assign({}, this.constData, para)
    data.headers = para.headers || {}
    data.headers['iov-token'] = getCookie(token) || ''

    axios({
      method: data.method,
      url: 'http://127.0.0.1:9036'+`${this.env === 'development' ? '/factoryCenter/api' : '/factoryCenter/api'}${data.url}`,
      data: data.data,
      params: data.params,
      timeout: data.timeout,
      headers: data.headers
    }).then(res => {		
      // 这里要根据状态码来对不同的响应状态做处理
      if (res.data.code == 401 || res.data.status == 401) {
        // 如果没有登录要记录跳转次数，大于5次就不跳转了
        let loginCount = localStorage.getItem('loginCount') || '0'
        if (loginCount - 0 <= 5) {
          localStorage.setItem('loginCount', ++loginCount + '')
          sessionStorage.setItem('target_href', location.href)
          // 未登录状态跳转登录页
          location.replace(res.data.redirectUrl)
        } else {
          Vue.prototype.$notify.error({
            title: '错误',
            message: 'token校验异常'
          })
          localStorage.setItem('loginCount', '0')
          sessionStorage.removeItem('target_href')
        }
      } else {
        localStorage.setItem('loginCount', '0')
        // status === 0 为正常返回, 表示已经登录了
        if (res.data.code == 0 || res.data.status == 0) {
          return data.done && data.done(res.data)
        } else {
          data.fail && data.fail()
          Vue.prototype.$notify.error({
            title: '错误',
            message: res.data.msg
          })
        }
      }
    }).catch(error => {
      console.log(error)
      Vue.prototype.$notify.error({
        title: '错误',
        message: '未连接到服务器或服务器异常！'
      })
    })
  }

  static init(para) {
    return new AXIOS(para)
  }
}

export default AXIOS
