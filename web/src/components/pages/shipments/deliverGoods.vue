<template>
  <div class="deliverGoods-container">
    <el-card class="clearfix" shadow="never" v-loading="loadData">
      <el-row>
        <el-button-group style="margin-bottom: 10px">
          <el-button size="small" type="warning" @click="importVisible = true" :disabled="!pageAuthBtn.factory_deliverGoods_export">导入</el-button>
        </el-button-group>
        <el-form :inline="true" :model="formInline" class="search-form" size="small" @submit.native.prevent>
          <el-form-item>
            <el-select filterable clearable placeholder="组装工厂" v-model="formInline.factoryName" @change="simpleSearch('factoryName')">
              <el-option v-if="item.wordType === 1" v-for="(item, index) in yunovoDic" :key="index" :label="item.wordValue" :value="item.wordKey"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="simpleSearch('factoryName')" :disabled="!pageAuthBtn.factory_deliverGoods_list">查询</el-button>
            <el-button type="primary" @click="searchVipVisible = true" :disabled="!pageAuthBtn.factory_deliverGoods_list">高级查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-table v-viewer ref="listTable" :data="list.data" @sort-change="handleSortChange" :stripe="isStripe" :max-height="maxTableHeight" border resizable size="mini">
          <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
          <el-table-column prop="brandName" label="品牌" min-width="200">
            <template slot-scope="scope">{{scope.row.brandName | valueToLabel(orgs, 'cooOrganName', 'code')}}</template>
          </el-table-column>
          <el-table-column prop="factoryName" label="组装工厂" min-width="200">
            <template slot-scope="scope">{{scope.row.factoryName | valueToLabel(yunovoDic.filter((v) => v.wordType === 1), 'wordValue', 'wordKey')}}</template>
          </el-table-column>
          <el-table-column prop="area" label="区域" min-width="200"></el-table-column>
          <el-table-column prop="channelName" label="渠道" min-width="150"></el-table-column>
          <el-table-column prop="deviceNumber" label="设备总数量" width="100" align="right"></el-table-column>
          <el-table-column prop="lastImporttime" label="最后导入时间" width="160">
            <template slot-scope="scope">{{scope.row.lastImporttime}}</template>
          </el-table-column>
          <el-table-column label="操作" width="80" fixed="right">
            <template slot-scope="scope">
              <el-button type="text" class="text_parimaty" @click="goDetail(scope.row)" :disabled="!pageAuthBtn.factory_deliverGoods_detail">详情</el-button>
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
            <el-form-item label="组装工厂">
              <el-select filterable clearable placeholder="请选择" v-model="formInline.factoryName">
                <el-option v-if="item.wordType === 1" v-for="(item, index) in yunovoDic" :key="index" :label="item.wordValue" :value="item.wordKey"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="品牌">
              <el-select filterable clearable placeholder="请选择" v-model="formInline.brandName">
                <el-option v-for="(item, index) in orgs" :key="index" :label="item.cooOrganName" :value="item.code"></el-option>
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
    <!-- imei导入 -->
    <el-dialog class="imei_export_dialog" title="发货导入" @close="imeiClose" :visible.sync="importVisible" width="700px" :close-on-click-modal="false">
      <div slot>
        <el-form ref="uploadForm" :model="uploadForm" :rules="rules" :inline="false" size="small" label-width="110px">
          <el-form-item prop="brandName" label="品牌商：">
            <el-select filterable placeholder="请选择" v-model="uploadForm.brandName" @change="brandNameChange">
              <el-option v-for="(item, index) in orgs" :key="index" :label="item.cooOrganName" :value="item.code"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="channel" label="渠道：">
            <el-cascader v-if="importVisible" v-model="uploadForm.channel" :options="channels" :props="props" @change="handleCascaderChange" filterable :show-all-levels="false" placeholder="请选择品牌">
              <template slot-scope="{ node, data }">
                <span>{{ data.nodeName }}</span>
                <span v-if="!node.isLeaf"> ({{ data.childNode.length }}) </span>
              </template>
            </el-cascader>
          </el-form-item>
          <el-form-item prop="yunovoCode" label="云智码：">
            <el-input v-model="uploadForm.yunovoCode" placeholder="请输入" style="width: 215px" @change="yunovoCodeChange"></el-input>
          </el-form-item>
          <el-form-item prop="modelNumber" label="型号：">
            <el-input v-model="uploadForm.modelNumber" placeholder="请输入" style="width: 215px"></el-input>
          </el-form-item>
          <el-form-item prop="workOrderno" label="工单号：">
            <el-input v-model="uploadForm.workOrderno" placeholder="请输入" style="width: 215px"></el-input>
          </el-form-item>
          <el-form-item prop="productDate" label="生产月份：">
            <el-date-picker v-model="uploadForm.productDate" type="month" value-format="yyyy-MM" placeholder="生产月份" style="width: 215px"></el-date-picker>
          </el-form-item>
          <el-form-item prop="screenSize" label="屏幕尺寸：">
            <el-radio-group v-model="uploadForm.screenSize">
              <el-radio label="9">9英寸</el-radio>
              <el-radio label="10">10英寸</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="remark" label="备注：">
            <el-input v-model="uploadForm.remark" rows="4" type="textarea" placeholder="请输入" show-word-limit maxlength="256"></el-input>
          </el-form-item>
          <el-form-item label="导入方式：">
            <el-radio-group v-model="uploadForm.type" @change="exportTypeChange">
              <el-radio :label="0">二维码扫码导入</el-radio>
              <el-radio :label="1">条形码扫码导入</el-radio>
              <el-radio :label="2">文件导入</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="file" v-if="uploadForm.type === 2">
            <span slot="label"><span style="color:#ff7477">* </span>IMEI文件：</span>
            <v-upload ref="upload" :format="['.xlsx']" :hasPreview="true" @showPriview="showPriview"></v-upload>
          </el-form-item>
          <el-form-item prop="imeiList" v-if="uploadForm.type === 1" label="IMEI：">
            <el-input @input="imeiListChange" resize="none" type="textarea" rows="7" v-model="uploadForm.imeiList" placeholder="多个imei使用换行符（回车按钮）分隔"></el-input>
            <div class="annotation">当前包含 <span class="text_purple bold" style="font-size: 20px;">{{imeiLength}}</span> 个imei</div>
          </el-form-item>
          <el-form-item prop="qrcode" v-if="uploadForm.type === 0" label="IMEI：">
            <el-input @input="qrcodeChange" resize="none" type="textarea" rows="7" v-model="uploadForm.qrcode" placeholder="获取焦点后，使用扫码枪扫描二维码，目前仅支持15位imei"></el-input>
            <div class="annotation">当前包含 <span class="text_purple bold" style="font-size: 20px;">{{qrcodeLength}}</span> 个imei</div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('uploadForm')" :disabled="addBtnDisabled">保存</el-button>
            <el-button type="warning" @click="resetForm('uploadForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Api from 'assets/js/api.js'
export default {
  data() {
    return {
      addBtnDisabled: false,
      importVisible: false,
      uploadForm: {
        screenSize: '9',
        type: 0,
      },
      rules: {
        brandName: [{
          required: true,
          message: '请选择品牌商',
          trigger: 'change'
        }],
        yunovoCode: [{
          required: true,
          message: '请输入云智码',
          trigger: 'blur'
        }, {
          validator: this.validatorYunovoCode,
          trigger: 'blur'
        }],
        channel: [{
          required: true,
          message: '请选择渠道',
          trigger: 'change'
        }],
        workOrderno: [{
          required: true,
          message: '请输入工单号',
          trigger: 'blur'
        }],
        modelNumber: [{
          required: true,
          message: '请输入型号',
          trigger: 'blur'
        }],
        imeiList: [{
          required: true,
          message: '请输入IMEI',
          trigger: 'blur'
        }, {
          validator: this.validatorImei,
          trigger: 'blur'
        }],
        qrcode: [{
          required: true,
          message: '请输入IMEI',
          trigger: 'blur'
        }, {
          validator: this.validatorImei,
          trigger: 'blur'
        }],
        screenSize: [{
          required: true,
          message: '请选择屏幕尺寸',
          trigger: 'change'
        }]
      },
      // 联级选择的配置属性
      props: {
        value: 'nodeId',
        label: 'nodeName',
        children: 'childNode',
        checkStrictly: true,
        emitPath: false,
      },
      channels: [], // 根据条件筛选的渠道
      Allchannels: [], // 所有的渠道
    }
  },
  mounted() {
    this.getData()
  },
  filters: {
    // 对树状渠道进行valueToLabel
    channelFilter: (value, channels) => {
      let result = ''
      const getResult = (dataArr) => {
        dataArr.forEach((v) => {
          if (v.nodeId == value) result = v.nodeName
          if (v.childNode) result = getResult(v.childNode)
        })
        return result
      }
      return getResult(channels)
    },
  },
  methods: {
    getData() {
      this.loadData = false
      Api.UNITS.getListData({
        vue: this,
        url: _axios.ajaxAd.statisticsDeliverGoods,
        data: {
          desc: 'last_importtime',
        }
      })
    },
    // 通过地址获取，暂不使用该接口
    getChannels(brandName) {
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
        params: { brandName },
        done: (res) => {
          if (brandName) this.channels = removeEmptyChildNode(res.data)
          else this.Allchannels = removeEmptyChildNode(res.data)
        }
      })
    },
    showPriview() {
      if (process.env.NODE_ENV === 'development') {
        window.open('../../../../static/imei.xlsx', '_self')
      } else {
        window.open(window.location.origin + '/factoryCenter/static/imei.xlsx', '_self')
      }
    },
    yunovoCodeChange(value) {
      const code = value.trim()
      const y = Api.UNITS.valueToLabel(code.substr(14, 1), this.yunovoDic.filter((v) => v.wordType === 7), 'wordValue', 'wordKey')
      const m = Api.UNITS.valueToLabel(code.substr(15, 1), this.yunovoDic.filter((v) => v.wordType === 8), 'wordValue', 'wordKey')
      if (y && m) {
        this.$set(this.uploadForm, 'productDate', `${y}-${m}`)
      } else {
        this.$set(this.uploadForm, 'productDate', '')
      }
    },
    brandNameChange(brandName) {
      // 重新选择品牌的时候清空渠道选择
      this.$delete(this.uploadForm, 'channel')
      this.getChannels(brandName)
    },
    imeiListChange(value) {
      // 特定的格式待后续看是否要用
      let str = value || ''
      let arr = str.split('\n')
      let searchArr = []
      arr.forEach((v, i) => {
        if ((i === arr.length - 1) || v.trim()) {
          searchArr.push(v.trim())
        }
      })
      this.uploadForm.imeiList = Array.from(new Set(searchArr)).join('\n')
      const repeatNum = searchArr.length - Array.from(new Set(searchArr)).length
      if (repeatNum > 0) {
        this.showMsgBox({
          type: 'warning',
          title: '温馨提示',
          message: `有 ${repeatNum} 个重复的imei，已自动过滤！`
        })
      }
    },
    qrcodeChange(value = '') {
      // 先待定是imei字符串
      let str = value.replace(/[\s\n]/g, "")
      let arr = []
      let searchArr = []
      for (let i = 0; i < str.length; i += 15) {
        arr.push(str.substr(i, 15))
      }
      arr.forEach((v, i) => {
        searchArr.push(v)
      })
      const noRepeatArr = Array.from(new Set(searchArr))
      const noRepeatStr = noRepeatArr.join('\n')
      this.uploadForm.qrcode = noRepeatStr
      const repeatNum = searchArr.length - noRepeatArr.length
      if (repeatNum > 0) {
        this.showMsgBox({
          type: 'warning',
          title: '温馨提示',
          message: `有 ${repeatNum} 个重复的imei，已自动过滤！`
        })
      }
    },
    handleCascaderChange(value) {
      console.log(value)
    },
    validatorImei(rule, value, callback) {
      if (value.replace(/[\s\n]/g, "").length % 15 === 0) {
        callback()
      } else {
        callback(new Error('检查到您有imei号不足15位，请先检查imei号是否正确！'))
      }
    },
    validatorYunovoCode(rule, value, callback) {
      if (/\s/.test(value)) {
        callback(new Error('输入内容不能包含空格！'))
      } else if (!/^[a-zA-Z0-9]/.test(value)) {
        callback(new Error('云智码只能包含字母与数字！'))
      } else {
        callback()
      }
    },
    exportTypeChange() {
      if (this.$refs.upload) {
        this.$refs.upload.fileList = []
      }
      this.$nextTick(() => {
        this.$refs.uploadForm.clearValidate(['imeiList', 'file'])
      })
    },
    imeiClose() {
      this.importVisible = false
      this.resetForm('uploadForm')
    },
    goDetail(row) {
      this.$router.push({
        name: 'deliverGoodsDetail',
        query: {
          id: row.id,
          channelName: row.channelName,
          title: '发货管理详情'
        },
      })
    },
    resetForm(formName, type) {
      this.addBtnDisabled = false
      this.$refs[formName] && this.$refs[formName].resetFields()
      this.uploadForm = {
        screenSize: '9',
        type: 0,
      }
      this.channels = []
      if (this.$refs.upload) {
        this.$refs.upload.fileList = []
      }
    },
    // 提交表单
    submitForm(formName) {
      console.log(this.uploadForm)
      if (this.addBtnDisabled) return
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.uploadForm.type === 2 && this.$refs.upload.fileList.length === 0) {
            Api.UNITS.showMsgBox({
              message: '请上传要导入的文件！'
            })
            return
          }
          // 验证通过
          let formData = new FormData()
          // 必传
          formData.append('brandName', this.uploadForm.brandName)
          formData.append('yunovoCode', this.uploadForm.yunovoCode)
          formData.append('channelId', this.uploadForm.channel)
          formData.append('productDate', this.uploadForm.productDate)
          formData.append('screenSize', this.uploadForm.screenSize)
          this.uploadForm.remark && formData.append('remark', this.uploadForm.remark)
          if (this.uploadForm.type === 0) {
            formData.append('imeis', this.uploadForm.qrcode)
          } else if (this.uploadForm.type === 1) {
            formData.append('imeis', this.uploadForm.imeiList)
          } else if (this.uploadForm.type === 2) {
            formData.append('imeis', this.$refs.upload.fileList[0].raw)
          }

          this.addBtnDisabled = true
          // 发送
          _axios.send({
            method: 'post',
            contentType: 'multipart/form-data',
            url: _axios.ajaxAd.getDeliverGoods,
            data: formData,
            done: ((res) => {
              this.addBtnDisabled = false
              this.imeiClose()
              this.searchData()
              setTimeout(() => {
                this.showMsgBox({
                  type: 'success',
                  message: `操作成功！本次导入${res.data}个imei`
                })
              }, 300)
            }),
            fail: () => {
              this.addBtnDisabled = false
            },
          })
        } else {
          Api.UNITS.showMsgBox()
          return false
        }
      })
    },
  },
  computed: {
    startDatePicker() {
      return Api.UNITS.startDatePicker(this)
    },
    startDatePicker_1() {
      return Api.UNITS.startDatePicker(this, this.formInline.selEndTime)
    },
    endDatePicker_1() {
      return Api.UNITS.endDatePicker(this, this.formInline.selStartTime)
    },
    // 有效imei长度
    imeiLength() {
      let str = this.uploadForm.imeiList || ''
      let arr = str.split('\n')
      let searchArr = []
      arr.forEach((v, i) => {
        if (v.trim()) {
          searchArr.push(v.trim())
        }
      })
      return searchArr.length
    },
    // 有效imei长度
    qrcodeLength() {
      let str = this.uploadForm.qrcode || ''
      let arr = str.split('\n')
      let searchArr = []
      arr.forEach((v, i) => {
        if (v.trim()) {
          searchArr.push(v.trim())
        }
      })
      return searchArr.length
    },
  }
}

</script>
<style lang="scss">
.deliverGoods-container {
  .imei_export_dialog {
    .el-dialog {
      margin-top: 3vh !important;
    }
  }
}

</style>
