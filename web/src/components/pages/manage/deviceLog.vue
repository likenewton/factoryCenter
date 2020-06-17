<template>
  <div class="taskLog-container">
    <el-card class="clearfix" shadow="never" v-loading="loadData">
      <el-row>
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
          <el-table-column label="类型">
          	<template slot-scope="scope">
          		<span v-if="scope.row.optType == '0'">修改SN</span>
          		<span v-else-if="scope.row.optType == '1'">更新组织机构</span>
          		<span v-else-if="scope.row.optType == '2'">修改ICCID</span>
          	</template>
          </el-table-column>
          <el-table-column prop="optDesc" label="描述"></el-table-column>
          <el-table-column prop="reporter" label="上报者"></el-table-column>
          <el-table-column prop="addDatetime" label="创建时间" width="185" ></el-table-column>
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
            <el-form-item label="类型">
              <el-select filterable clearable placeholder="请选择" v-model="formInline.optType">
                <el-option label="修改SN" :value="0"></el-option>
                <el-option label="更新组织机构" :value="1"></el-option>
                <el-option label="修改ICCID" :value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="描述">
              <el-input v-model="formInline.optDesc" placeholder="请输入"></el-input>
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
        url: _axios.ajaxAd.deviceLogList,
      })
    },
  },
}

</script>
<style lang="scss">
.taskLog-container {}

</style>
