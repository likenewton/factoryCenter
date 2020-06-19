<template>
  <div>
    <div v-if="pageAuthBtn.factory_home_show" class="home_container">
      <!-- 卡片 -->
      <el-row :gutter="20" class="card-wrapper">
        <el-col :span="8">
          <el-card class="box-card" style="background: rgb(39, 218, 153);">
            <i class="iconfont-huaqiangxincheng-icon-"></i>
            <h3>贴片</h3>
            <span class="calc">今日：{{totals_1.todayNumber || 0}}</span>
            <span class="calc">累计：{{totals_1.sumTotal || 0}}</span>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card" style="background: rgb(255, 195, 103);">
            <i class="iconfont-zuzhuangfuwu"></i>
            <h3>组装</h3>
            <span class="calc">今日：{{totals_2.todayNumber || 0}}</span>
            <span class="calc">累计：{{totals_2.sumTotal || 0}}</span>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card" style="background: rgb(60, 177, 255);">
            <i class="iconfont-fahuo"></i>
            <h3>发货</h3>
            <span class="calc">今日：{{totals_3.todayNumber || 0}}</span>
            <span class="calc">累计：{{totals_3.sumTotal || 0}}</span>
          </el-card>
        </el-col>
      </el-row>
      <!-- 图表贴片 -->
      <el-row class="echars-wrapper" style="margin-top: 20px">
        <el-card>
          <el-col :span="24">
            <div id="echart_1"></div>
          </el-col>
        </el-card>
      </el-row>
      <!-- 图表组装 -->
      <el-row class="echars-wrapper" style="margin-top: 20px">
        <el-card>
          <el-col :span="24">
            <div id="echart_2"></div>
          </el-col>
        </el-card>
      </el-row>
      <!-- 图表发货 -->
      <el-row class="echars-wrapper" style="margin-top: 20px">
        <el-card>
          <el-col :span="24">
            <div id="echart_3"></div>
          </el-col>
        </el-card>
      </el-row>
    </div>
    <div v-else class="home_container">
      <img class="logo" src="../assets/images/welcome.png" height="400" width="400">
      <div class="welcome_wrapper">
        <div class="title">{{userInfo.userName}}，欢迎您的到来</div>
        <p class="text">专业的技术</p>
        <p class="text">精湛的工艺</p>
        <p class="text">打造智能高品质车载智能方案</p>
        <p class="text">车联网管理平台</p>
        <p class="text">感谢有您</p>
        <p class="version">当前您登录的版本是2.0.0</p>
      </div>
    </div>
  </div>
</template>
<script>
import Api from 'assets/js/api.js'
export default {
  data() {
    return {
      yunovoDic: [],
      orgs: [],
      totals_1: {},
      totals_2: {},
      totals_3: {},
      hasAuth: true,
      myChart_1: null,
      myChart_2: null,
      myChart_3: null,
    }
  },
  mounted() {
    if (this.pageAuthBtn.factory_home_show) {
      this.getHomeTotals()
      this.getBaseInfo(() => {
        this.getPastersEchart()
        this.getAssemblesEchart()
        this.getShippingEchart()
      })
    }
  },
  methods: {
    getBaseInfo(cb) {
      let a = new Promise((resolve) => {
        _axios.send({
          method: 'get',
          url: _axios.ajaxAd.yunovoDic,
          done: (res) => {
            this.yunovoDic = res.data || []
            resolve()
          }
        })
      })
      let b = new Promise((resolve) => {
        _axios.send({
          method: 'get',
          url: _axios.ajaxAd.getBrandList,
          done: (res) => {
            this.orgs = res.data || []
            resolve()
          }
        })
      })
      Promise.all([a, b]).then(() => {
        cb && cb()
      })
    },
    getHomeTotals() {
      _axios.send({
        method: 'get',
        url: _axios.ajaxAd.getHomeTotals,
        done: (res) => {
          const data = res.data || []
          data.forEach((v) => {
            if (v.statisticsType === 1) {
              this.totals_1 = v
            } else if (v.statisticsType === 2) {
              this.totals_2 = v
            } else if (v.statisticsType === 3) {
              this.totals_3 = v
            }
          })
        }
      })
    },
    getPastersEchart() {
      _axios.send({
        method: 'get',
        url: _axios.ajaxAd.getPastersEchart,
        params: {
          desc: 'report_time'
        },
        done: (res) => {
          this.showEchart_1(res.data || [])
        }
      })
    },
    getAssemblesEchart() {
      _axios.send({
        method: 'get',
        url: _axios.ajaxAd.getAssemblesEchart,
        params: {
          desc: 'report_time'
        },
        done: (res) => {
          this.showEchart_2(res.data || [])
        }
      })
    },
    getShippingEchart() {
      _axios.send({
        method: 'get',
        url: _axios.ajaxAd.getShippingEchart,
        params: {
          desc: 'report_time'
        },
        done: (res) => {
          console.log(res)
          this.showEchart_3(res.data || [])
        }
      })
    },
    formatEchartData(data, numName, wordType = 0) {
      // 处理数据
      let xAxis = []
      let factoryList = []
      let series = []
      let legend = []
      // 先按时间排序,取最近的14天数据
      const factoryData = data.sort((v1, v2) => {
        return new Date(v2.time).getTime() - new Date(v1.time).getTime()
      }).slice(0, 14).reverse()
      factoryData.forEach((v) => {
        xAxis.push(v.time)
        const fList = v.list || []
        // 先获取所有的工厂
        fList.forEach((f) => {
          let factoryName = Api.UNITS.valueToLabel(f.factoryName, this.yunovoDic.filter((v) => v.wordType === wordType), 'wordValue', 'wordKey')
          // 只有字典里面能查到的工厂才push进数组
          if (factoryName) {
            if (f.brandName) {
              factoryList.push(`${f.factoryName}+${f.brandName}`)
            } else {
              factoryList.push(f.factoryName)
            }
          }
        })
      })
      factoryList = Array.from(new Set(factoryList))
      // 对每一个工厂填充数据
      factoryList.forEach((factoryName) => {
        // 先看有没有包含品牌
        let [a, b] = ['', '']
        if (factoryName.includes('+')) {
          a = Api.UNITS.valueToLabel(factoryName.split('+')[0], this.yunovoDic.filter((v) => v.wordType === wordType), 'wordValue', 'wordKey')
          b = Api.UNITS.valueToLabel(factoryName.split('+')[1], this.orgs, 'cooOrganName', 'code')
          legend.push(`${a}+${b} `)
        } else {
          a = Api.UNITS.valueToLabel(factoryName, this.yunovoDic.filter((v) => v.wordType === wordType), 'wordValue', 'wordKey')
          legend.push(a + ' ')
        }
        let numList = []
        factoryData.forEach((v) => {
          const fList = v.list || []
          let num = 0
          fList.forEach((f) => {
            if (f.brandName) {
              if (factoryName.split('+')[1] === f.brandName && factoryName.split('+')[0] === f.factoryName) {
                num = f[numName]
              }
            } else {
              if (factoryName === f.factoryName) {
                num = f[numName]
              }
            }
          })
          numList.push(num)
        })

        series.push({
          data: numList,
          label: {
            normal: {
              show: true,
              position: 'top'
            }
          },
        })
      })

      return {
        xAxis,
        series,
        legend,
      }
    },
    showEchart_1(data) {
      this.myChart_1 = this.$echarts.init(document.getElementById('echart_1'))
      const echartData = this.formatEchartData(data, 'pasterNumber', 0)

      const _echart = new Api.ECHARTS({ dataViewTitle: '时间' })
      _echart.setOption({
        title: '贴片统计',
        legend: echartData.legend,
        xAxis: { data: echartData.xAxis },
        series: echartData.series,
      })
      let option = _echart.getOption()
      option.grid.right = 270
      option.legend.padding = [6, 0]
      option.legend.textStyle = {
        rich: {
          white: {
            padding: [1, 0, 1, 0]
          }
        }
      }
      option.legend.formatter = (name) => {
        return '{white|' + name.slice(0, 20) + '}'
      }
      Vue.nextTick(() => {
        this.myChart_1.setOption(option, true)
        $("[_echarts_instance_]").find(":last-child").trigger('click')
      })
    },
    showEchart_2(data) {
      this.myChart_2 = this.$echarts.init(document.getElementById('echart_2'))
      const echartData = this.formatEchartData(data, 'deviceNumber', 1)

      const _echart = new Api.ECHARTS({ dataViewTitle: '时间' })
      _echart.setOption({
        title: '组装统计',
        legend: echartData.legend,
        xAxis: { data: echartData.xAxis },
        series: echartData.series,
      })
      let option = _echart.getOption()
      option.grid.right = 270
      option.legend.padding = [6, 0]
      option.legend.textStyle = {
        rich: {
          white: {
            padding: [1, 0, 1, 0]
          }
        }
      }
      option.legend.formatter = (name) => {
        return '{white|' + name.slice(0, 20) + '}'
      }
      Vue.nextTick(() => {
        this.myChart_2.setOption(option, true)
        $("[_echarts_instance_]").find(":last-child").trigger('click')
      })
    },
    showEchart_3(data) {
      this.myChart_3 = this.$echarts.init(document.getElementById('echart_3'))
      const echartData = this.formatEchartData(data, 'deviceNumber', 1)

      const _echart = new Api.ECHARTS({ dataViewTitle: '时间' })
      _echart.setOption({
        title: '发货统计',
        legend: echartData.legend,
        xAxis: { data: echartData.xAxis },
        series: echartData.series,
      })
      let option = _echart.getOption()
      option.grid.right = 270
      option.legend.padding = [6, 0]
      option.legend.textStyle = {
        rich: {
          white: {
            padding: [1, 0, 1, 0]
          }
        }
      }
      option.legend.formatter = (name) => {
        return '{white|' + name.slice(0, 20) + '}'
      }
      Vue.nextTick(() => {
        this.myChart_3.setOption(option, true)
        $("[_echarts_instance_]").find(":last-child").trigger('click')
      })
    }
  },
  watch: {
    asideCollapse(val, oldVal) {
      setTimeout(() => {
        this.myChart_1.resize()
        this.myChart_2.resize()
        this.myChart_3.resize()
      }, 300)
    },
    authButtons() {
      if (this.pageAuthBtn.factory_home_show) {
        this.getHomeTotals()
        this.getBaseInfo(() => {
          this.getPastersEchart()
          this.getAssemblesEchart()
          this.getShippingEchart()
        })
      }
    },
  }
}

</script>
<style lang="scss">
.home_container {

  .card-wrapper {

    .box-card .el-card__body {
      position: relative;
      display: flex;
      justify-content: center;
      align-content: center;
      flex-wrap: wrap;
      height: 160px;

      h3 {
        width: 100%;
        font-size: 24px;
        font-weight: 700;
        line-height: 50px;
      }

      .calc {
        display: inline-block;
        padding: 0 20px;
        line-height: 40px;
      }

      i {
        position: absolute;
        top: 20px;
        right: 20px;
        font-size: 45px;
        color: rgba(0, 0, 0, .3);
      }

    }

    * {
      color: #fff;
      text-align: center;
    }
  }

  .echars-wrapper {

    #echart_1,
    #echart_2,
    #echart_3 {
      height: 350px;
    }

  }

  .logo {
    position: absolute;
    top: 16%;
    left: 14%;
  }

  .welcome_wrapper {
    position: absolute;
    top: 15%;
    left: calc(14% + 480px);

    .title {
      font-size: 34px;
      line-height: 70px;
      margin-left: -50px;
    }

    .text {
      font-family: KaiTi !important;
      font-size: 20px;
      font-weight: 200;
      color: #f48337;
      line-height: 34px;
    }

    .version {
      line-height: 80px;
      color: #999;
    }
  }
}

</style>
