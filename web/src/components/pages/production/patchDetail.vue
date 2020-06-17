<template>
  <div class="patchDetail-container">
    <el-card class="clearfix" shadow="never" v-loading="loadData">
      <el-row>
        <el-button-group style="margin-bottom: 10px">
          <el-button size="small" type="warning" @click="exportExcel">导出</el-button>
        </el-button-group>
        <el-form :inline="true" :model="formInline" class="search-form" size="small" @submit.native.prevent>
          <el-form-item>
            <el-input v-model="formInline.sn" @keyup.enter.native="simpleSearch('sn')" autocomplete="on" auto-complete="on" name="sn" placeholder="SN号"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="simpleSearch('sn')">查询</el-button>
            <el-button type="primary" @click="searchVipVisible = true">高级查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-table v-viewer ref="listTable" :data="list.data" @sort-change="handleSortChange" :stripe="isStripe" :max-height="maxTableHeight" border resizable size="mini">
          <el-table-column prop="id" label="ID" width="60" align="center"></el-table-column>
          <el-table-column prop="sn" label="SN" width="160"></el-table-column>
          <el-table-column prop="imei" label="IMEI" width="160"></el-table-column>
          <el-table-column prop="iccid" label="ICCID" width="180"></el-table-column>
          <el-table-column prop="dtype" label="设备类型" width="100">
            <template slot-scope="scope">{{scope.row.dtype | valueToLabel(deviceType)}}</template>
          </el-table-column>
          <el-table-column prop="orgCode" label="机构名称" min-width="160">
            <template slot-scope="scope">{{scope.row.orgCode | valueToLabel(orgs, 'cooOrganName', 'code')}}</template>
          </el-table-column>
          <el-table-column prop="succNumber" label="测试成功数量" width="100" align="right"></el-table-column>
          <el-table-column prop="errorNumber" label="测试错误数量" width="100" align="right"></el-table-column>
          <el-table-column prop="romVersion" label="ROM版本" min-width="160"></el-table-column>
          <el-table-column prop="apkVersion" label="APK版本" min-width="160"></el-table-column>
          <el-table-column prop="mcuVersion" label="MCU版本" min-width="160"></el-table-column>
          <el-table-column prop="location" label="位置信息" width="160"></el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="160"></el-table-column>
          <el-table-column label="操作" width="80" fixed="right">
            <template slot-scope="scope">
              <el-button type="text" class="text_parimaty" @click="getTestDetail(scope.row)">详情</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="list.currentPage" :page-sizes="pageSizes" :page-size="list.pagesize" layout="total, sizes, prev, pager, next, jumper" :total="list.total" class="clearfix pagination-table"></el-pagination>
      </el-row>
    </el-card>
    <!-- 高级查询 -->
    <el-dialog title="高级查询" :visible.sync="searchVipVisible" width="710px" :close-on-click-modal="false">
      <div slot>
        <div class="searchForm_vip" style="width:100%;overflow: auto">
          <el-form :inline="false" :model="formInline" size="small" label-width="96px" v-loading="loadData">
            <el-form-item label="SN">
              <el-input v-model="formInline.sn" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="IMEI">
              <el-input v-model="formInline.imei" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="ICCID">
              <el-input v-model="formInline.iccid" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="ROM版本">
              <el-input v-model="formInline.romVersion" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="APK版本">
              <el-input v-model="formInline.apkVersion" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="MCU版本">
              <el-input v-model="formInline.mcuVersion" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="机构名称">
              <el-select filterable clearable placeholder="请选择" v-model="formInline.orgCode">
                <el-option v-for="(item, index) in orgs" :key="index" :label="item.cooOrganName" :value="item.code"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="设备类型">
              <el-select filterable clearable placeholder="请选择" v-model="formInline.dtype">
                <el-option v-if="item.value !== -1" v-for="(item, index) in deviceType" :key="index" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item style="width: 100%">
              <el-button type="primary" @click="searchData">查询</el-button>
              <el-button type="warning" @click="resetData">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </el-dialog>
    <!-- 抽屉 -->
    <el-drawer custom-class="patch-dertail-drawer" title="测试详情" :visible.sync="drawer" :with-header="false">
      <el-table v-viewer ref="listTable" :data="testList.data" :stripe="true" :max-height="maxTableHeight2" resizable border size="mini">
        <el-table-column prop="testId" label="测试ID" width="60" align="center"></el-table-column>
        <el-table-column prop="testItem" label="测试项目" min-width="160"></el-table-column>
        <el-table-column prop="testMethod" label="测试类型" width="100">
          <template slot-scope="scope">
            <span v-if="scope.row.testMethod === 1">自动测试项</span>
            <span v-if="scope.row.testMethod === 2">手动测试项</span>
          </template>
        </el-table-column>
        <el-table-column prop="testValue" label="测试值" width="80"></el-table-column>
        <el-table-column prop="testResult" label="测试结果" width="80">
          <template slot-scope="scope">
            <span v-if="scope.row.testResult === 0"><i style="display:inline-block;width:6px;height:6px;border-radius:50%;background:#27da99;margin-right:4px"></i>PASS</span>
            <span v-if="scope.row.testResult === 1"><i style="display:inline-block;width:6px;height:6px;border-radius:50%;background:#ff7477;margin-right:4px"></i>FAIL</span>
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
      maxTableHeight2: Api.UNITS.maxTableHeight(100),
      brandName: Api.UNITS.getQuery('brandName'),
      factoryName: Api.UNITS.getQuery('factoryName'),
      createTime: Api.UNITS.getQuery('reportTime'),
      productionPhase: Api.UNITS.getQuery('productionPhase'),
      drawer: false,
      testList: {
        data: []
      },
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
        url: _axios.ajaxAd.getPasterDetail,
        data: {
          orgCode: this.brandName,
          factoryName: this.factoryName,
          createTime: this.createTime,
          productionPhase: this.productionPhase, // 贴片 or 组装
          desc: 'create_time',
        }
      })
    },
    getTestDetail(row) {
      _axios.send({
        url: _axios.ajaxAd.getTestDetail,
        params: {
          testId: row.id,
          pageSize: 99999,
          page: 1
        },
        done: (res) => {
          this.drawer = true
          this.testList.data = res.data.records || []
        }
      })
    },
    exportExcel() { // 导出
      Api.UNITS.exportExcel('/factoryCenter/api/' + _axios.ajaxAd.getPasterDetail, {
        ...this.formInline,
        createTime: this.createTime,
        orgCode: this.brandName,
        factoryName: this.factoryName,
        productionPhase: this.productionPhase,
        export: 'export',
      })
    },
  },
  computed: {}
}

</script>
<style lang="scss">
.patchDetail-container {}

.patch-dertail-drawer {
  width: 500px !important;
}

</style>
