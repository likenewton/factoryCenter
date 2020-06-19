<template>
  <div class="channel-container">
    <el-card class="clearfix" shadow="never" v-loading="loadData">
      <el-row>
        <el-button-group style="margin-bottom: 10px">
          <el-button size="small" type="success" @click="showDialog('create')" :disabled="!pageAuthBtn.factory_channel_create">新建渠道</el-button>
        </el-button-group>
        <el-form :inline="true" :model="formInline" class="search-form" size="small" @submit.native.prevent>
          <el-form-item>
            <el-input v-model="formInline.channelName" @keyup.enter.native="webFilter('channelName')" autocomplete="on" auto-complete="on" name="channelName" placeholder="渠道名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="webFilter('channelName')" :disabled="!pageAuthBtn.factory_channel_list">查询</el-button>
            <el-button type="primary" @click="searchVipVisible = true" :disabled="!pageAuthBtn.factory_channel_list">高级查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-table v-viewer ref="listTable" :data="list.data" row-key="nodeId" :tree-props="{children: 'childNode', hasChildren: 'hasChildren'}" @sort-change="handleSortChange" :stripe="false" :max-height="maxTableHeight" border resizable size="mini">
          <el-table-column prop="channelName" label="渠道名称" min-width="200">
            <template slot-scope="scope">{{scope.row.node.channelName}}</template>
          </el-table-column>
          <el-table-column prop="area" label="区域" min-width="200">
            <template slot-scope="scope">{{scope.row.node.area}}</template>
          </el-table-column>
          <el-table-column prop="brandName" label="品牌" min-width="200">
            <template slot-scope="scope">{{scope.row.node.brandName | valueToLabel(orgs, 'cooOrganName', 'code')}}</template>
          </el-table-column>
          <el-table-column prop="level" label="级别" width="100">
            <template slot-scope="scope">{{scope.row.node.level | valueToLabel(levels)}}</template>
          </el-table-column>
          <el-table-column prop="contacts" label="联系人" width="140">
            <template slot-scope="scope">{{scope.row.node.contacts}}</template>
          </el-table-column>
          <el-table-column prop="phone" label="手机" width="140">
            <template slot-scope="scope">{{scope.row.node.phone}}</template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="160">
            <template slot-scope="scope">{{scope.row.node.createTime}}</template>
          </el-table-column>
          <el-table-column label="操作" width="140" fixed="right">
            <template slot-scope="scope">
              <el-button type="text" class="text_parimaty" @click="showDialog('check', scope.row)" :disabled="!pageAuthBtn.factory_channel_detail">详情</el-button>
              <el-button type="text" class="text_editor" @click="showDialog('editor', scope.row)" :disabled="!pageAuthBtn.factory_channel_editor">编辑</el-button>
              <el-button type="text" class="text_danger" @click="deleteChannel(scope.row)" :disabled="!pageAuthBtn.factory_channel_delete">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
    </el-card>
    <!-- 高级查询 -->
    <el-dialog title="高级查询" :visible.sync="searchVipVisible" width="640px" :close-on-click-modal="false">
      <div slot>
        <div class="searchForm_vip" style="width:100%;overflow: auto">
          <el-form :inline="false" :model="formInline" size="small" label-width="90px" v-loading="loadData">
            <el-form-item label="渠道名称">
              <el-input v-model="formInline.channelName" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="区域">
              <el-input v-model="formInline.area" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="联系人">
              <el-input v-model="formInline.contacts" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="手机">
              <el-input v-model="formInline.phone" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="创建时间">
              <el-date-picker v-model="formInline.timeAddedbegin" :picker-options="startDatePicker_1" type="date" value-format="yyyy-MM-dd" placeholder="创建时间（起）"></el-date-picker> -
              <el-date-picker v-model="formInline.timeAddedend" :picker-options="endDatePicker_1" type="date" value-format="yyyy-MM-dd" placeholder="创建时间（止）"></el-date-picker>
            </el-form-item>
            <el-form-item style="width: 100%">
              <el-button type="primary" @click="webFilter">查询</el-button>
              <el-button type="warning" @click="resetData">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </el-dialog>
    <!-- 渠道表单 -->
    <el-dialog :title="formInfo[formType].title" @close="channelClose" :visible.sync="importVisible" width="830px" :close-on-click-modal="false">
      <div slot>
        <el-form ref="uploadForm" v-loading="formLoad" :model="uploadForm" :rules="rules" :inline="false" size="small" label-width="110px">
          <el-form-item prop="channelName" label="渠道名称：">
            <el-input v-model="uploadForm.channelName" placeholder="请输入" style="width: 215px" :disabled="isDisabed"></el-input>
          </el-form-item>
          <el-form-item label="品牌：" prop="brandName">
            <el-select filterable clearable placeholder="请选择" v-model="uploadForm.brandName" :disabled="isDisabed">
              <el-option v-for="(item, index) in orgs" :key="index" :label="item.cooOrganName" :value="item.code"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="contacts" label="联系人：">
            <el-input v-model="uploadForm.contacts" placeholder="请输入" style="width: 215px" :disabled="isDisabed"></el-input>
          </el-form-item>
          <el-form-item prop="phone" label="手机：">
            <el-input v-model="uploadForm.phone" placeholder="请输入" style="width: 215px" :disabled="isDisabed"></el-input>
          </el-form-item>
          <el-form-item prop="province" label="区域：">
            <el-select v-model="uploadForm.province" filterable placeholder="省" @change="provinceSelect" :disabled="isDisabed">
              <el-option v-for="(item, index) in provinceData" :key="index" :label="item.name" :value="item.tno"></el-option>
            </el-select>&nbsp;
            <el-select v-model="uploadForm.city" filterable clearable placeholder="市" :disabled="!uploadForm.province || isDisabed" @change="citySelect">
              <el-option v-for="(item, index) in cityData" :key="index" :label="item.name" :value="item.tno"></el-option>
            </el-select>&nbsp;
            <el-select v-model="uploadForm.district" filterable clearable placeholder="区" :disabled="!uploadForm.city || isDisabed" @change="districtSelect">
              <el-option v-for="(item, index) in districtData" :key="index" :label="item.name" :value="item.tno"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="address" label="详细地址：">
            <el-input v-model="uploadForm.address" placeholder="请输入" style="width: 660px" :disabled="isDisabed"></el-input>
          </el-form-item>
          <el-form-item label="上级渠道：">
            <el-cascader v-if="importVisible" v-model="uploadForm.channelTree" :options="cascaderList" :props="props" :disabled="isDisabed" clearable filterable :show-all-levels="false">
              <template slot-scope="{ node, data }">
                <span>{{ data.nodeName }}</span>
                <!-- <span v-if="!node.isLeaf"> ({{ data.childNode.length }}) </span> -->
              </template>
            </el-cascader>
            <div class="annotation">不选，则为顶级渠道</div>
          </el-form-item>
          <el-form-item v-if="!isDisabed">
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
      constData: [], // 因为是一次性请求的，这里未channel所有的数据并且不能被修改
      cascaderList: [],
      provinceData: [],
      cityData: [],
      districtData: [],
      uploadForm: {},
      curChannel: {},
      importVisible: false,
      formType: 'create', // 表单的类型 create/editor/check
      formLoad: true,
      formInfo: {
        create: {
          title: '新建渠道',
          method: 'post',
        },
        editor: {
          title: '编辑渠道',
          method: 'put',
        },
        check: {
          title: '渠道详情'
        },
      },
      levels: [{
        label: '省代',
        value: 0
      }, {
        label: '市代',
        value: 1
      }, {
        label: '三级',
        value: 2
      }, {
        label: '四级',
        value: 3
      }, {
        label: '五级',
        value: 4
      }, {
        label: '六级',
        value: 5
      }, {
        label: '七级',
        value: 6
      }, {
        label: '八级',
        value: 7
      }, {
        label: '九级',
        value: 8
      }, {
        label: '十级',
        value: 9
      }],
      props: {
        value: 'nodeId',
        label: 'nodeName',
        children: 'childNode',
        checkStrictly: true,
      },
      rules: {
        channelName: [{
          required: true,
          message: '请输入渠道名称',
          trigger: 'blur'
        }],
        contacts: [{
          required: true,
          message: '请输入联系人',
          trigger: 'blur'
        }],
        phone: [{
          required: true,
          message: '请输入手机',
          trigger: 'blur'
        }, {
          validator: this.validatorPhoneNumber,
          trigger: 'blur'
        }],
        province: [{
          required: true,
          message: '请选择地区',
          trigger: 'change'
        }],        
        brandName: [{
          required: true,
          message: '请选择品牌',
          trigger: 'change'
        }],
        address: [{
          required: true,
          message: '请输入详细地址',
          trigger: 'blur'
        }]
      },
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    // === 地区选择 start ===
    getNations(parentNo = 'root', cb) {
      _axios.send({
        method: 'get',
        url: _axios.ajaxAd.getAres,
        params: { parentNo },
        done: ((res) => {
          cb && cb(res)
        })
      })
    },
    provinceSelect(id) { // 省级选择
      this.setRegionData(id, 'cityData')
      this.$delete(this.uploadForm, 'city')
      this.$delete(this.uploadForm, 'district')
    },
    citySelect(id) { // 市级选择
      this.setRegionData(id, 'districtData')
      this.$delete(this.uploadForm, 'district')
    },
    setRegionData(id, key) { // 保存数据，处理被删除的区域
      this.getNations(id, (res) => {
        this[key] = res.data || []
      })
    },
    // === 地区选择 end ===
    getData() {
      _axios.send({
        method: 'get',
        url: _axios.ajaxAd.channels,
        params: {
          tree: 'tree',
          desc: 'create_time',
        },
        done: (res) => {
          this.loadData = false
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
          this.list.data = removeEmptyChildNode(res.data || [])
          // 额外copy一份使用
          this.constData = this.list.data.slice()
        },
        fail: () => {
          this.loadData = false
        },
      })
    },
    resetData() {
      this.formInline = {}
      this.sort = {}
      this.searchVipVisible = false
      this.getData()
    },
    // 前端自行过滤行数
    webFilter(key) {
      this.list.data = []

      if (typeof key === 'string') { // 如果是简单查询
        const value = this.formInline[key]
        this.formInline = {}
        this.$set(this.formInline, key, value)
      }

      const find = (obj, k) => {
        // 对时间等特殊字段处理
        if (k === 'timeAddedbegin') {
          if (new Date(obj.node.createTime) >= new Date(this.formInline.timeAddedbegin)) {
            return [obj]
          }
        }

        if (k === 'timeAddedend') {
          if (new Date(obj.node.createTime) < new Date(this.formInline.timeAddedend).getTime() + 24 * 3600 * 1000) {
            return [obj]
          }
        }

        if (obj.node[k] && obj.node[k].includes(this.formInline[k])) {
          return [obj]
        } else {
          if (obj.childNode) {
            let result = []
            obj.childNode.forEach((v) => {
              if (find(v, k)) {
                result.push(...find(v, k))
              }
            })
            return result
          }
        }
      }

      const filter = (obj, k) => {
        obj.forEach((v) => {
          const result = find(v, k)
          if (result) {
            this.list.data.push(...result)
          }
        })
      }

      if (typeof key === 'string') { // 根据key值来过滤
        const data = this.constData.slice()
        if (!this.formInline[key] || !this.formInline[key].trim()) {
          this.list.data = data
          return
        }
        filter(data, key)
      } else { // 根据formLine过滤
        let data = this.constData.slice()
        let keysArr = ['channelName', 'area', 'contacts', 'phone', 'timeAddedbegin', 'timeAddedend']

        keysArr.forEach((k) => {
          if (this.formInline[k] && this.formInline[k].trim()) {
            filter(data, k)
            // 保存该次的查询结果
            data = this.list.data.slice()
            this.list.data = []
          }
        })
        this.list.data = data.slice()
      }

      this.searchVipVisible = false
    },
    getChannelDetail() {
      this.formLoad = true
      _axios.send({
        method: 'get',
        url: `${_axios.ajaxAd.channels}/${this.curChannel.nodeId}`,
        done: ((res) => {
          const data = res.data || {}
          const areaIds = data.areaIds.split(',')
          this.uploadForm = {
            channelName: data.channelName,
            contacts: data.contacts,
            phone: data.phone,
            channelTree: data.paths ? data.paths.split(',') : [],
            address: data.address,
            brandName: data.brandName,
          }
          this.$set(this.uploadForm, 'province', areaIds[0] || '')
          areaIds[0] && this.provinceSelect(areaIds[0]) // 从这里获取城市
          this.$set(this.uploadForm, 'city', areaIds[1] || '')
          areaIds[1] && this.citySelect(areaIds[1]) // 从这里获取区域
          this.$set(this.uploadForm, 'district', areaIds[2] || '')
          this.$nextTick(() => {
            this.$refs.uploadForm.clearValidate()
          })
          setTimeout(() => {
            this.formLoad = false
          }, 300)
        }),
        fail: () => {
          this.formLoad = false
        }
      })
    },
    showDialog(type, row) {
      this.formType = type
      this.curChannel = row || {}
      this.importVisible = true

      const setDisabled = (data = []) => {
        data.forEach((v, i) => {
          if (v.nodeId === this.curChannel.nodeId) data.splice(i, 1)
          if (v.childNode) setDisabled(v.childNode)
        })
        return data
      }

      const removeEmptyChildNode = (data = []) => {
        data.forEach((v) => {
          if (!v.childNode || v.childNode.length === 0) {
            Reflect.deleteProperty(v, 'childNode')
          } else {
            removeEmptyChildNode(v.childNode)
          }
        })
        return data
      }

      if (type !== 'create') {
        // 获取单个查询数据
        this.getChannelDetail()
        // 给渠道设置disable, 当前渠道不能选择自己作为父渠道
        this.cascaderList = setDisabled(JSON.parse(JSON.stringify(this.constData.slice())))
        this.cascaderList = removeEmptyChildNode(JSON.parse(JSON.stringify(this.cascaderList.slice())))
      } else {
        this.formLoad = false
        this.cascaderList = this.constData.slice()
        this.resetForm('uploadForm')
      }
      this.setRegionData('root', 'provinceData')
    },
    channelClose() {
      this.importVisible = false
      this.resetForm('uploadForm')
    },
    deleteChannel(row) {
      if (row.childNode && row.childNode.length > 0) {
        this.showMsgBox({
          type: 'warning',
          message: '请先删除下级渠道！'
        })
        return
      }
      this.showCfmBox({
        message: `确定要删除「${row.nodeName}」吗？`,
        cb: () => {
          _axios.send({
            method: 'delete',
            url: `${_axios.ajaxAd.channels}/${row.nodeId}`,
            done: (res) => {
              this.searchData()
              setTimeout(() => {
                this.showMsgBox({
                  type: 'success',
                  message: '删除成功！',
                })
              }, 300)
            }
          })
        },
      })
    },
    resetForm(formName, type) {
      this.addBtnDisabled = false
      this.$refs[formName] && this.$refs[formName].resetFields()
      this.uploadForm = {}
      this.$nextTick(() => {
        this.$refs[formName] && this.$refs[formName].clearValidate()
      })
      if (this.formType !== 'create') this.getChannelDetail()
    },
    // 提交表单
    submitForm(formName) {
      const province = Api.UNITS.valueToLabel(this.uploadForm.province, this.provinceData, 'name', 'tno')
      const city = Api.UNITS.valueToLabel(this.uploadForm.city, this.cityData, 'name', 'tno')
      const district = Api.UNITS.valueToLabel(this.uploadForm.district, this.districtData, 'name', 'tno')
      const channelTree = this.uploadForm.channelTree || []
      if (this.addBtnDisabled) return
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.addBtnDisabled = true
          // 发送
          _axios.send({
            method: this.formInfo[this.formType].method,
            url: _axios.ajaxAd.channels,
            data: {
              ...this.uploadForm,
              level: channelTree.length,
              parentId: channelTree.length > 0 ? channelTree[channelTree.length - 1] - 0 : 0,
              paths: channelTree.length > 0 ? channelTree.join(',') : '',
              area: province + city + district,
              areaIds: [this.uploadForm.province, this.uploadForm.city, this.uploadForm.district].join(','),
              id: this.curChannel.nodeId,
            },
            done: ((res) => {
              this.addBtnDisabled = false
              this.channelClose()
              this.searchData()
              setTimeout(() => {
                this.showMsgBox({
                  type: 'success',
                  message: '操作成功！'
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
    // 以下是表单验证工具函数
    validatorPhoneNumber(rule, value, callback) {
      if (!value || Api.UNITS.validatorPhoneNumber(value)) {
        callback()
      } else {
        callback(new Error('手机格式不正确'))
      }
    },
  },
  computed: {
    startDatePicker_1() {
      return Api.UNITS.startDatePicker(this, this.formInline.timeAddedend)
    },
    endDatePicker_1() {
      return Api.UNITS.endDatePicker(this, this.formInline.timeAddedbegin)
    },
    isDisabed() {
      return this.formType === 'check'
    }
  }
}

</script>
<style lang="scss">
.channel-container {
  // .el-table__row.el-table__row--level-1 {
  //   background: rgba(253, 245, 230, 0.35);
  // }

  // .el-table__row.el-table__row--level-2 {
  //   background: rgba(240, 249, 235, 0.35);
  // }

  // .el-table__row.el-table__row--level-3 {
  //   background: rgba(240, 240, 240, 0.35);
  // }
}

</style>
