<template>
  <div class="patch-container">
    <el-card class="clearfix" shadow="never" v-loading="loadData">
      <el-row>
        <el-form :inline="true" :model="formInline" class="search-form" size="small" @submit.native.prevent>
          <el-form-item>
            <el-select filterable clearable placeholder="贴片工厂" v-model="formInline.factoryName" @change="simpleSearch('factoryName')">
              <el-option v-if="item.wordType === 0" v-for="(item, index) in yunovoDic" :key="index" :label="item.wordValue" :value="item.wordKey"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="simpleSearch('factoryName')" :disabled="!pageAuthBtn.factory_patch_list">查询</el-button>
            <el-button type="primary" @click="searchVipVisible = true" :disabled="!pageAuthBtn.factory_patch_list">高级查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-table v-viewer ref="listTable" :data="list.data" @sort-change="handleSortChange" :stripe="isStripe" :max-height="maxTableHeight" border resizable size="mini">
          <el-table-column prop="id" label="ID" width="60" align="center"></el-table-column>
          <el-table-column prop="factoryName" label="贴片工厂" min-width="200">
            <template slot-scope="scope">{{scope.row.factoryName | valueToLabel(yunovoDic.filter((v) => v.wordType === 0), 'wordValue', 'wordKey')}}</template>
          </el-table-column>
          <el-table-column prop="pasterNumber" label="贴片数量" width="160" align="right"></el-table-column>
          <el-table-column prop="errorNumber" label="错误数量" width="160" align="right"></el-table-column>
          <el-table-column prop="reportTime" label="上报时间" width="160"></el-table-column>
          <el-table-column label="操作" width="80">
            <template slot-scope="scope">
              <el-button type="text" class="text_parimaty" @click="goDetail(scope.row)" :disabled="!pageAuthBtn.factory_patch_detail">详情</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="list.currentPage" :page-sizes="pageSizes" :page-size="list.pagesize" layout="total, sizes, prev, pager, next, jumper" :total="list.total" class="clearfix pagination-table"></el-pagination>
      </el-row>
    </el-card>
    <!-- 高级查询 -->
    <el-dialog title="高级查询" :visible.sync="searchVipVisible" width="640px" :close-on-click-modal="false">
      <div slot>
        <div class="searchForm_vip" style="width:100%;overflow: auto">
          <el-form :inline="false" :model="formInline" size="small" label-width="90px" v-loading="loadData">
            <el-form-item label="贴片工厂">
              <el-select filterable clearable placeholder="请选择" v-model="formInline.factoryName">
                <el-option v-if="item.wordType === 0" v-for="(item, index) in yunovoDic" :key="index" :label="item.wordValue" :value="item.wordKey"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="上报时间">
              <el-date-picker v-model="formInline.selStartTime" :picker-options="startDatePicker_1" type="date" value-format="yyyy-MM-dd" placeholder="上报时间（起）"></el-date-picker> -
              <el-date-picker v-model="formInline.selEndTime" :picker-options="endDatePicker_1" type="date" value-format="yyyy-MM-dd" placeholder="上报时间（止）"></el-date-picker>
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
      this.loadData = false
      Api.UNITS.getListData({
        vue: this,
        url: _axios.ajaxAd.getPaster,
        data: {
          desc: 'report_time',
        }
      })
    },
    goDetail(row) {
      let factoryName = Api.UNITS.valueToLabel(row.factoryName, this.yunovoDic.filter((v) => v.wordType === 0), 'wordValue', 'wordKey')
      this.$router.push({
        name: 'patchDetail',
        query: {
          title: `${factoryName}(${row.reportTime})`,
          factoryName: row.factoryName,
          reportTime: row.reportTime,
          productionPhase: 1,
        }
      })
    },
  },
  computed: {
    startDatePicker_1() {
      return Api.UNITS.startDatePicker(this, this.formInline.selEndTime)
    },
    endDatePicker_1() {
      return Api.UNITS.endDatePicker(this, this.formInline.selStartTime)
    },
  }
}

</script>
<style lang="scss">
.patch-container {}

</style>
