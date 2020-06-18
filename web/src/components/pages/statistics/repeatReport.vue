<template>
  <div class="taskLog-container">
    <el-card class="clearfix" shadow="never" v-loading="loadData">
      <el-row>
        <el-form :inline="true" :model="formInline" class="search-form" size="small" @submit.native.prevent>
          <el-form-item>
            <el-input v-model="formInline.fieldName" placeholder="字段名称" @keyup.enter.native="simpleSearch('fieldName')"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="simpleSearch('fieldName')">查询</el-button>
            <el-button type="primary" @click="searchVipVisible = true">高级查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-table v-viewer ref="listTable" :data="list.data" @sort-change="handleSortChange" :stripe="isStripe" :max-height="maxTableHeight" border resizable size="mini">
          <el-table-column prop="fieldName" label="字段名称"></el-table-column>
          <el-table-column prop="fieldValue" label="字段值"></el-table-column>
          <el-table-column prop="reportTimes" label="上报次数"></el-table-column>
          <el-table-column prop="lastDatetime" label="最后上报时间" width="185" ></el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="185" ></el-table-column>
          <el-table-column prop="updateTime" label="更新时间" width="185"></el-table-column>
        </el-table>		
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="list.currentPage" :page-sizes="pageSizes" :page-size="list.pagesize" layout="total, sizes, prev, pager, next, jumper" :total="list.total" class="clearfix pagination-table"></el-pagination>
      </el-row>
    </el-card>
    <!-- 高级查询 -->
    <el-dialog title="高级查询" :visible.sync="searchVipVisible" width="730px" :close-on-click-modal="false">
      <div slot>
        <div class="searchForm_vip" style="width:100%;overflow: auto">
          <el-form :inline="false" :model="formInline" size="small" label-width="105px" v-loading="loadData">
            <el-form-item label="字段名称">
              <el-input v-model="formInline.fieldName" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="字段值">
              <el-input v-model="formInline.fieldValue" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="上报次数">
              <el-input v-model="formInline.reportTimes" placeholder="请输入"></el-input>
            </el-form-item>
            <!--<el-form-item label="最后上报时间">
              <el-date-picker v-model="formInline.lastDatetime" :picker-options="startDatePicker" type="date" value-format="yyyy-MM-dd" placeholder="最后上报时间（起）"></el-date-picker> -
              <el-date-picker v-model="formInline.lastDatetime" :picker-options="endDatePicker" type="date" value-format="yyyy-MM-dd" placeholder="最后上报时间（止）"></el-date-picker>
            </el-form-item>-->
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
        url: _axios.ajaxAd.repeatReportList,
      })
    },
  },
}

</script>
<style lang="scss">
.taskLog-container {}

</style>
