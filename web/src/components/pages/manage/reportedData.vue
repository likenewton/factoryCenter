<template>
  <div class="taskLog-container">
    <el-card class="clearfix" shadow="never" v-loading="loadData">
      <el-row>
      	<el-button size="small" type="warning" @click="exportExcel">导出数据</el-button>
        <el-form :inline="true" :model="formInline" class="search-form" size="small" @submit.native.prevent>
          <el-form-item>
            <el-input v-model="formInline.iccid" placeholder="iccid" @keyup.enter.native="simpleSearch('iccid')"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="simpleSearch('iccid')">查询</el-button>
            <el-button type="primary" @click="searchVipVisible = true">高级查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-table v-viewer ref="listTable" :data="list.data" @sort-change="handleSortChange" :stripe="isStripe" :max-height="maxTableHeight" border resizable size="mini">
          <el-table-column prop="iccid" label="ICCID"></el-table-column>
          <el-table-column prop="imei" label="设备IMEI"></el-table-column>
          <el-table-column prop="sn" label="设备SN"></el-table-column>
          <el-table-column label="是否云智SN">
          	<template slot-scope="scope">
          		<span v-if="scope.row.customSn == '0'">云智SN</span>
          		<span v-else>非云智SN</span>
          	</template>
          </el-table-column>
          <el-table-column prop="attached" label="额外信息"></el-table-column>
          <el-table-column prop="btAddr" label="BT"></el-table-column>
          <el-table-column prop="wifiAddr" label="WIFI地址"></el-table-column>
           <el-table-column prop="reporter" label="上报者"></el-table-column>
          <el-table-column prop="createDatetime" label="新增时间" width="165"></el-table-column>
        </el-table>		
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="list.currentPage" :page-sizes="pageSizes" :page-size="list.pagesize" layout="total, sizes, prev, pager, next, jumper" :total="list.total" class="clearfix pagination-table"></el-pagination>
      </el-row>
    </el-card>
    <!-- 高级查询 -->
    <el-dialog title="高级查询" :visible.sync="searchVipVisible" width="730px" :close-on-click-modal="false">
      <div slot>
        <div class="searchForm_vip" style="width:100%;overflow: auto">
          <el-form :inline="false" :model="formInline" size="small" label-width="105px" v-loading="loadData">
            <el-form-item label="ICCID">
              <el-input v-model="formInline.iccid" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="设备IMEI">
              <el-input v-model="formInline.imei" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="设备SN">
              <el-input v-model="formInline.sn" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item style="width: 100%">
              <el-button type="primary" @click="searchData">查询</el-button>
              <el-button type="warning" @click="resetData">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Api from 'assets/js/api.js'
export default {
  data() {
    return {
     
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      this.loadData = true
      Api.UNITS.getListData({
        vue: this,
        url: _axios.ajaxAd.reportedDataList,
      })
    },
    exportExcel() { // 导出
    	let exports = {...this.formInline, export: 1}
      Api.UNITS.exportExcel("/factoryCenter/api"+_axios.ajaxAd.reportedDataList, exports)
    },
  },
}

</script>
<style lang="scss">
.taskLog-container {}

</style>
