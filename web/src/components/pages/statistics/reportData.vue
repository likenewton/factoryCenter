<template>
  <div class="taskLog-container">
    <el-card class="clearfix" shadow="never" v-loading="loadData">
      <el-row>
      	<el-button size="small" type="primary" @click="showEchart">扫描上报趋势</el-button>
        <el-form :inline="true" :model="formInline" class="search-form" size="small" @submit.native.prevent>
          <el-form-item>
          	<el-date-picker style="width:220px !important;" v-model="formInline.stateDatetime" value-format="yyyy-MM-dd HH:mm:ss" :picker-options="startDatePicker" type="datetime" placeholder="统计时间"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="simpleSearch('stateDatetime')">查询</el-button>
            <el-button type="primary" @click="searchVipVisible = true">高级查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-table v-viewer ref="listTable" :data="list.data" @sort-change="handleSortChange" :stripe="isStripe" :max-height="maxTableHeight" border resizable size="mini">
          <el-table-column prop="stateDatetime" label="统计时间"></el-table-column>
          <el-table-column prop="reportTimes" label="扫描上报次数"></el-table-column>
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
            <el-form-item label="统计时间">
              <el-date-picker v-model="formInline.stateDatetime" value-format="yyyy-MM-dd HH:mm:ss" :picker-options="startDatePicker" type="datetime" placeholder="统计时间"></el-date-picker>
            </el-form-item>
            <el-form-item label="扫描上报次数">
              <el-input v-model="formInline.reportTimes" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item style="width: 100%">
              <el-button type="primary" @click="searchData">查询</el-button>
              <el-button type="warning" @click="resetData">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </el-dialog>
    
    <!-- 扫描上报趋势 -->
    <el-dialog title="扫描上报趋势（次）" :visible.sync="echartVisible" center width="1200px" :close-on-click-modal="false">
      <div slot>
        <div id="myChart_0" style="width: 1150px;height: 400px;" v-loading="echartLoadData"></div>
      </div>
    </el-dialog>
    
  </div>
</template>
<script>
import Api from 'assets/js/api.js'
export default {
  data() {
    return {
    	pageSizes: [15],
    	list: {
        data: [],
        pagesize: 15,
        currentPage: 1,
        total: 0,
      },
     	echartVisible: false,
      echartLoadData: false,
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
        url: _axios.ajaxAd.reportDataList,
      })
    },
    showEchart() {		
    	this.echartVisible = true;
    	this.echartLoadData = true;
    	this.$nextTick(function() {
    		let myChart = this.$echarts.init(document.getElementById('myChart_0'));
    		let xAxisData = [], dataList = [];
    		this.list.data.map((v)=>{
    			xAxisData.push(v.stateDatetime.substring(0,10));
    			dataList.push(v.reportTimes);
    		})						
    		// 绘制图表
		    myChart.setOption({
		    		tooltip: {
							trigger: 'axis',
							backgroundColor: 'rgba(0,0,0,1)',
						},
						toolbox:{
			        right:20,
			        feature:{
			            saveAsImage: {},
			            restore: {},
			            magicType: {
			                type:['line','bar']
			            },
			        }
				    },
				    xAxis: {
			        data: xAxisData,
			        axisLabel: {
                interval: 0,  
   							rotate: 45
	            }
				    },
				    yAxis: {
				    	splitLine: {
							    show: false,
								lineStyle: {
									color: 'rgba(131,101,101,0.2)',
									type: 'dashed',
								}
							}
				    },
				    series: [{
				        type: 'line',
				        lineStyle: {
									normal: {
										color: "#48B3FF"
									},
								},
								label: {
									show: false,
									position: 'top',
									textStyle: {
										color: '#48B3FF',
									}
								},
								itemStyle: {
									color: "#FFF",
									borderColor: "#48B3FF",
									borderWidth: 2,
								},
								tooltip: {
									show: true
								},
				        data: dataList
				    }]
				});
		    this.echartLoadData = false
    	});
    },
  },
  computed: {
    // 起始时间约数
    startDatePicker() {
      return Api.UNITS.startDatePicker(this)
    },
    /*// 结束时间约数
    endDatePicker() {
      return Api.UNITS.endDatePicker(this, this.formInline.activeTimeBegin)
    }*/
  }
}

</script>
<style lang="scss">
.taskLog-container {}

</style>
