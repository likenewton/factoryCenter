<template>
  <div class="sell-container">
    <el-card clell-ass="clearfix" shadow="never" v-loading="loadData" :style="{height: maxTableHeight}">
      <el-form :inline="true" :model="formInline" size="small" @submit.native.prevent class="echart-form">
        <!-- <el-form-item>
          <el-cascader v-model="formInline.channelId" :options="channels" :props="props" placeholder="渠道" clearable filterable :show-all-levels="false">
            <template slot-scope="{ node, data }">
              <span>{{ data.nodeName }}</span>
              <span v-if="!node.isLeaf"> ({{ data.childNode.length }}) </span>
            </template>
          </el-cascader>
        </el-form-item> -->
        <el-form-item>
          <el-select v-model="formInline.brandName" filterable clearable placeholder="品牌(不选为全部品牌)">
            <el-option v-for="(item, index) in orgs" :key="index" :label="item.cooOrganName" :value="item.code"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchData">查询</el-button>
          <el-button type="warning" @click="resetData">重置</el-button>
        </el-form-item>
      </el-form>
      <div id="china" class="clearfix" :style="{height: maxTableHeight2}"></div>
    </el-card>
  </div>
</template>
<script>
import Api from 'assets/js/api.js'
import Coor from 'assets/js/geoCoordMap1.js'

export default {
  data() {
    return {
      maxTableHeight: Api.UNITS.maxTableHeight(168) + 'px',
      maxTableHeight2: Api.UNITS.maxTableHeight(258) + 'px',
      myChart: null,
      channels: [],
      // 联级选择的配置属性
      props: {
        value: 'nodeId',
        label: 'nodeName',
        children: 'childNode',
        checkStrictly: true,
        emitPath: false,
      },
      option: {
        title: {
          text: '销售热力图',
          left: 'center'
        },
        tooltip: {
          formatter: function(params, ticket, callback) {
            return `${params.seriesName}<br />${params.name || '未知地区'}：${params.value || 0}`
          }
        },
        visualMap: {
          min: 0,
          max: 1500,
          left: 'left',
          top: 'bottom',
          text: ['高', '低'],
          inRange: {
            color: ['#e0ffff', '#006edd']
          },
          show: true
        },
        geo: {
          map: 'china',
          roam: false,
          zoom: 1.23,
          label: {
            normal: {
              show: true,
              fontSize: '10',
              color: 'rgba(0,0,0,0.7)'
            }
          },
          itemStyle: {
            normal: {
              borderColor: 'rgba(0, 0, 0, 0.2)'
            },
            emphasis: {
              areaColor: '#F3B329',
              shadowOffsetX: 0,
              shadowOffsetY: 0,
              shadowBlur: 20,
              borderWidth: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
        series: [{
          name: '出货数量',
          type: 'map',
          geoIndex: 0,
          data: []
        }]
      },
    }
  },
  mounted() {
    this.getChannels()
    this.getData()
  },
  methods: {
    getData() {
      this.loadData = true
      _axios.send({
        method: 'get',
        url: _axios.ajaxAd.getAreasEchart,
        params: this.formInline,
        done: (res) => {
          this.loadData = false
          this.showEchart_1(res.data || [])
        }
      })
    },
    getChannels() {
      const removeEmptyChildNode = (data = []) => {
        data.forEach((v) => {
          if (v.childNode.length === 0) {
            Reflect.deleteProperty(v, 'childNode')
          } else {
            removeEmptyChildNode(v.childNode)
          }
        })
        return data
      }
      _axios.send({
        method: 'get',
        url: `${_axios.ajaxAd.channels}?tree`,
        done: (res) => {
          this.channels = removeEmptyChildNode(res.data)
        }
      })
    },
    showEchart_1(data) {
      let geoCoordMap1 = JSON.parse(JSON.stringify(Coor.geoCoordMap1))
      let max = 0
      this.myChart = echarts.init(document.getElementById('china'))
      data.forEach((v) => {
        if (v.deviceNumber > max) max = v.deviceNumber
        geoCoordMap1.forEach((v1) => {
          if (v.area.includes(v1.name)) {
            v1.value = v.deviceNumber
          }
        })
      })
      this.option.series[0].data = geoCoordMap1
      this.option.visualMap.max = max === 0 ? 1 : max * 1.5
      this.myChart.setOption(this.option)
    }
  },
  watch: {
    asideCollapse(val, oldVal) {
      setTimeout(() => {
        this.myChart.resize()
      }, 300)
    },
  }
}

</script>
<style lang="scss">
.sell-container {
  #china {
    width: 100%;
    margin-top: 50px;
  }

  .echart-form {
    float: right;
    margin-top: 4px;
    margin-right: -1px;

    .el-input,
    .el-select,
    .el-date-editor {
      width: 180px !important;
    }
  }

}

</style>
