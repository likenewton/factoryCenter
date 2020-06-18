<template>
  <div class="taskLog-container">
    <el-card class="clearfix" shadow="never" v-loading="loadData">
      <el-row>
        <el-form :inline="true" :model="formInline" class="search-form" size="small" @submit.native.prevent>
          <el-form-item>
            <el-input v-model="formInline.swCode" placeholder="软件代码" @keyup.enter.native="simpleSearch('swCode')"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="simpleSearch('swCode')">查询</el-button>
            <el-button type="primary" @click="searchVipVisible = true">高级查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-table v-viewer ref="listTable" :data="list.data" @sort-change="handleSortChange" :stripe="isStripe" :max-height="maxTableHeight" border resizable size="mini">
          <el-table-column prop="swCode" label="软件代码"></el-table-column>
          <el-table-column prop="romVersion" label="ROM版本号"></el-table-column>
          <el-table-column prop="projectModel" label="设备项目型号"></el-table-column>
          <el-table-column prop="orgCode" label="设备ROM包机构编码"></el-table-column>
          <el-table-column prop="reporter" label="上报者"></el-table-column>
          <el-table-column prop="addDatetime" label="创建时间" width="185" ></el-table-column>
          <el-table-column prop="updateDatetime" label="更新时间" width="185"></el-table-column>
        </el-table>		
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="list.currentPage" :page-sizes="pageSizes" :page-size="list.pagesize" layout="total, sizes, prev, pager, next, jumper" :total="list.total" class="clearfix pagination-table"></el-pagination>
      </el-row>
    </el-card>
    <!-- 高级查询 -->
    <el-dialog title="高级查询" :visible.sync="searchVipVisible" width="750px" :close-on-click-modal="false">
      <div slot>
        <div class="searchForm_vip" style="width:100%;overflow: auto">
          <el-form :inline="false" :model="formInline" size="small" label-width="120px" v-loading="loadData">
            <el-form-item label="软件代码">
              <el-input v-model="formInline.swCode" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="ROM版本号">
              <el-input v-model="formInline.romVersion" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="设备项目型号">
              <el-input v-model="formInline.projectModel" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="ROM包机构编码">
              <el-input v-model="formInline.orgCode" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="上报者">
              <el-input v-model="formInline.reporter" placeholder="请输入"></el-input>
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
        url: _axios.ajaxAd.softwareInfoList,
      })
    },
  },
}

</script>
<style lang="scss">
.taskLog-container {}

</style>
