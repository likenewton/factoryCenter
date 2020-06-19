<template>
  <div class="sell-container">
    <el-card>
      <el-form :inline="true" :model="formInline" class="search-form" size="small" @submit.native.prevent>
        <el-form-item>
          <el-select v-model="formInline.brandName" filterable clearable placeholder="品牌(不选为全部品牌)" @change="searchData">
            <el-option v-for="(item, index) in orgs" :key="index" :label="item.cooOrganName" :value="item.code"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchData">查询</el-button>
          <el-button type="warning" @click="resetData">重置</el-button>
        </el-form-item>
      </el-form>
      <div id="echart_1" v-loading="loadData"></div>
    </el-card>
  </div>
</template>
<script>
import Api from 'assets/js/api.js'

export default {
  data() {
    return {
      myChart_1: null,
    }
  },
  mounted() {
    this.getBaseInfo(() => {
      this.getData()
    })
  },
  methods: {
    getBaseInfo(cb) {
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
      Promise.all([b]).then(() => {
        cb && cb()
      })
    },
    getData() {
      this.loadData = true
      _axios.send({
        method: 'get',
        url: _axios.ajaxAd.getSellEchart,
        params: this.formInline,
        done: (res) => {
          this.loadData = false
          this.showEchart_1(res.data || [])
        }
      })
    },
    showEchart_1(data) {
      this.myChart_1 = this.$echarts.init(document.getElementById('echart_1'))
      const echartData = this.formatEchartData(data)
      console.log(echartData)

      let xList = [] // 横轴
      let yList = [] // 纵轴
      data.forEach((v) => {
        xList.push(v.area)
        yList.push(v.deviceNumber)
      })

      const _echart = new Api.ECHARTS({ dataViewTitle: '地区' })
      _echart.setOption({
        title: '品牌地区销售分布',
        legend: echartData.legend,
        xAxis: { data: echartData.xAxis },
        series: echartData.series,
      })
      let option = _echart.getOption()
      option.grid.top = 95
      option.toolbox.top = 50
      option.grid.right = 200
      option.legend.padding = [50, 8]
      option.legend.textStyle = {
        rich: {
          white: {
            padding: [0, 0, 1, 0]
          }
        }
      }
      option.legend.formatter = (name) => {
        return '{white|' + name.slice(0, 14) + '}'
      }
      Vue.nextTick(() => {
        this.myChart_1.setOption(option, true)
        $("[_echarts_instance_]").find(":last-child").trigger('click')
      })
    },
    formatEchartData(data) {
      // 处理数据
      let xAxis = []
      let series = []
      let legend = []
      let brandList = []

      data.forEach((v) => {
        xAxis.push(v.province)
        const fList = v.list || []
        // 先获取所有的品牌
        fList.forEach((f) => {
          let brandName = Api.UNITS.valueToLabel(f.brandName, this.orgs, 'cooOrganName', 'code')
          // 只有字典里面能查到的品牌才push进数组
          if (brandName) {
            brandList.push(f.brandName)
          }
        })
      })
      brandList = Array.from(new Set(brandList))
      // 对每一个品牌填充数据
      brandList.forEach((brandName) => {
        // 先看有没有包含品牌
        legend.push(Api.UNITS.valueToLabel(brandName, this.orgs, 'cooOrganName', 'code'))
        let numList = []
        data.forEach((v) => {
          const fList = v.list || []
          let num = 0
          fList.forEach((f) => {
            if (brandName === f.brandName) {
              num = f.deviceNumber
            }
          })
          numList.push(num)
        })

        series.push({
          data: numList,
          type: 'bar',
          stack: 'stack',
          label: {
            normal: {
              show: false,
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
  },
  watch: {
    asideCollapse(val, oldVal) {
      setTimeout(() => {
        this.myChart_1.resize()
      }, 300)
    },
  }
}

</script>
<style lang="scss">
.sell-container {
  #echart_1 {
    height: 450px;
  }

  .search-form {
    position: absolute;
    right: 20px;
    top: 60px;
    z-index: 777;
  }
}

</style>
