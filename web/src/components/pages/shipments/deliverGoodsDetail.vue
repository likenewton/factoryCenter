<template>
  <div class="deliverGoodsDetail-container">
    <el-card class="clearfix" shadow="never" v-loading="loadData">
      <el-row>
        <el-button-group style="margin-bottom: 10px">
          <el-button size="small" type="warning" @click="exportExcel">导出</el-button>
        </el-button-group>
        <el-form :inline="true" :model="formInline" class="search-form" size="small" @submit.native.prevent>
          <el-form-item>
            <el-input v-model="formInline.yunovoCode" @keyup.enter.native="simpleSearch('yunovoCode')" autocomplete="on" auto-complete="on" name="yunovoCode" placeholder="云智码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchData">查询</el-button>
            <el-button type="warning" @click="resetData">重置</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-table v-viewer ref="listTable" :data="list.data" @sort-change="handleSortChange" :stripe="isStripe" :max-height="maxTableHeight" border resizable size="mini">
          <el-table-column prop="id" label="ID" width="60" align="center"></el-table-column>
          <el-table-column prop="brandName" label="品牌" width="220">
            <template slot-scope="scope">{{scope.row.brandName | valueToLabel(orgs, 'cooOrganName', 'code')}}</template>
          </el-table-column>
          <el-table-column prop="factoryName" label="组装工厂" width="220">
            <template slot-scope="scope">{{scope.row.factoryName | valueToLabel(yunovoDic.filter((v) => v.wordType === 1), 'wordValue', 'wordKey')}}</template>
          </el-table-column>
          <el-table-column prop="area" label="区域" width="200"></el-table-column>
          <el-table-column prop="channelId" label="渠道" min-width="150">
            <template slot-scope="scope">{{scope.row.channelId | channelFilter(Allchannels)}}</template>
          </el-table-column>
          <el-table-column prop="yunovoCode" label="云智码" width="200"></el-table-column>
          <el-table-column prop="deviceNumber" label="设备数量" width="80" align="right"></el-table-column>
          <el-table-column prop="remark" label="备注" min-width="200"></el-table-column>
          <el-table-column prop="productDate" label="生产日期" width="120"></el-table-column>
          <el-table-column prop="importTime" label="导入时间" width="160"></el-table-column>
          <el-table-column prop="operator" label="操作者" width="100"></el-table-column>
          <el-table-column label="操作" width="110" fixed="right">
            <template slot-scope="scope">
              <el-button type="text" class="text_parimaty" @click="getDeviceDetail(scope.row)">详情</el-button>
              <el-button type="text" class="text_danger" @click="deleteItem(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="list.currentPage" :page-sizes="pageSizes" :page-size="list.pagesize" layout="total, sizes, prev, pager, next, jumper" :total="list.total" class="clearfix pagination-table"></el-pagination>
      </el-row>
    </el-card>
    <!-- 抽屉 -->
    <el-drawer title="IMEI详情" :visible.sync="drawer" :with-header="false">
      <el-table v-viewer ref="listTable" :data="imeiList.data.filter(data => !imei || data.imei.toLowerCase().includes(imei.toLowerCase()))" :stripe="true" :max-height="maxTableHeight2" resizable size="mini">
        <!--   <el-table-column prop="id" label="ID" width="100" align="center"></el-table-column> -->
        <el-table-column prop="imei" label="IMEI" min-width="160">
          <template slot="header" slot-scope="scope">
            <span style="line-height: 40px">IMEI</span>
            <el-input v-model="imei" size="mini" placeholder="输入imei搜索" style="width:200px;float: right" />
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>
  </div>
</template>
<script>
import Api from 'assets/js/api.js'
export default {
  data() {
    return {
      imei: '', // 当前输入的imei
      maxTableHeight2: Api.UNITS.maxTableHeight(100),
      drawer: false,
      imeiList: {
        data: [],
        pagesize: 20,
        currentPage: 1,
        total: 0,
      },
      id: Api.UNITS.getQuery('id'),
      Allchannels: [],
    }
  },
  mounted() {
    this.getFahuoById(() => {
      this.getData()
      this.getChannels()
    })
  },
  filters: {
    // 对树状渠道进行valueToLabel
    channelFilter: (value, channels) => {
      let result = ''
      const getResult = (dataArr) => {
        dataArr.forEach((v) => {
          if (v.nodeId == value) {
            result = v.nodeName
          }
          if (v.childNode) {
            result = getResult(v.childNode)
          }
        })
        return result
      }
      return getResult(channels)
    },
  },
  methods: {
    getFahuoById(cb) {
      _axios.send({
        url: `${_axios.ajaxAd.statisticsDeliverGoods}/${this.id}`,
        done: (res) => {
          const data = res.data || {}
          console.log(res)
          this.brandName = data.brandName
          this.factoryName = data.factoryName
          this.area = data.area,
            cb && cb()
        }
      })
    },
    getData() {
      this.loadData = false
      Api.UNITS.getListData({
        vue: this,
        url: _axios.ajaxAd.getDeliverGoods,
        data: {
          brandName: this.brandName,
          factoryName: this.factoryName,
          area: this.area,
          desc: 'import_time',
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
          this.Allchannels = removeEmptyChildNode(res.data)
        }
      })
    },
    getDeviceDetail(row) {
      _axios.send({
        url: _axios.ajaxAd.getDeviceDetail,
        params: {
          shippingId: row.id,
          pageSize: 99999,
          page: 1
        },
        done: (res) => {
          this.drawer = true
          this.imei = ''
          this.imeiList.data = res.data.records || []
        }
      })
    },
    deleteItem(row) {
      this.showCfmBox({
        message: '确定删除吗？',
        cb: () => {
          _axios.send({
            method: 'delete',
            url: `${_axios.ajaxAd.getDeliverGoods}/${row.id}`,
            done: (res) => {
              this.searchData()
              setTimeout(() => {
                this.showMsgBox({
                  type: 'success',
                  message: '删除成功！'
                })
              }, 300)
            }
          })
        }
      })
    },
    exportExcel() { // 导出
      Api.UNITS.exportExcel('/factoryCenter/api/' + _axios.ajaxAd.getDeviceDetail, {
        ...this.formInline,
        brandName: this.brandName,
        factoryName: this.factoryName,
        area: this.area,
        export: 'export',
      })
    },
  },
  computed: {
    startDatePicker_1() {
      return Api.UNITS.startDatePicker(this, this.formInline.timeAddedend)
    },
    endDatePicker_1() {
      return Api.UNITS.endDatePicker(this, this.formInline.timeAddedbegin)
    },
  }
}

</script>
<style lang="scss">
.deliverGoodsDetail-container {}

</style>
