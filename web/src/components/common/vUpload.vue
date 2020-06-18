<template>
  <div class="v-upload-container">
    <el-upload ref="upload" :drag="drag" :on-change="uploadHandleChange" :on-exceed="fileExceed" :on-preview="choiceFile" :before-remove="beforeRemove" :with-credentials="true" :limit="limit" :multiple="limit > 1" :file-list="fileList" :auto-upload="false">
      <slot name="button">
        <el-button type="primary" size="small">选择文件</el-button>
      </slot>
      <div slot="tip" class="el-upload__tip" v-if="format_upload__tip">
        <slot name="tip">
          <span v-html="format_upload__tip"></span>
        </slot>
        <span v-if="hasPreview" class="text_primary pointer" @click="showPriview"> (格式预览)</span>
      </div>
    </el-upload>
  </div>
</template>
<script>
export default {
  name: 'vUpload',
  data() {
    return {
      fileList: [],
      cancel: null,
      msgObj: null,
    }
  },
  props: {
    format: { // 上传文件的格式
      type: Array,
      default: []
    },
    size: { // 上传文件大小限制
      type: Number,
      default: Number.MAX_SAFE_INTEGER // 单位：M
    },
    hasPreview: {
      type: Boolean,
      default: false
    },
    limit: {
      type: Number,
      default: 1
    },
    drag: {
      type: Boolean,
      default: false,
    }
  },
  methods: {
    beforeRemove(file) {
      // 处理文件的移除
      this.isInnerManual && this.cancel('上传文件操作已被取消！')
      this.fileList.forEach((v, index) => {
        if (v.uid === file.uid) {
          this.fileList.splice(index, 1)
        }
      })
      this.$emit('beforeRemove')
    },
    // 上传文件超出上限的钩子
    fileExceed() {
      this.showMsgBox({
        type: 'error',
        message: `一次最多只能上传${this.limit}个文件！`
      })
    },
    // 点击列表获取对应file
    choiceFile(file) {
      this.$emit('choiceFile', file)
    },
    uploadHandleChange(file, fileList) {
      // 处理文件的增加
      let [typeErrCount, sizeErrCount] = [0, 0]
      let newOkFileList = []
      // 先从fileList中分离出新增的list
      let newFileList = fileList.filter((v) => {
        let flag = true
        this.fileList.forEach((o) => {
          if (o.uid === v.uid) {
            flag = false
          }
        })
        return flag
      })
      // 遍历新增的list，将符合上传格式的加入到newOkFileList，并记录错误详情
      newFileList.forEach((v, index) => {
        let flag = true
        let type = v.name
        let size = v.size / (1024 * 1024) // 单位：M
        if (!this.validatorFileType(type)) {
          typeErrCount++
          flag = false
        }
        if (!this.validatorFileSize(size)) {
          sizeErrCount++
          flag = false
        }
        if (this.msgObj) {
          this.msgObj.close()
        }
        flag && newOkFileList.push(v)
      })

      if (typeErrCount > 0 || sizeErrCount > 0) {
        this.msgObj = this.showMsgBox({
          type: 'error',
          message: `${typeErrCount}个文件格式不正确，${sizeErrCount}个文件大小超过限制`
        })
      }
      // 将符合要求的文件加入到fileList中
      this.fileList.push(...newOkFileList)
      this.$emit('uploadChange', this.fileList)
    },
    // 验证上传的文件是否符合要求
    validatorFileType(type) { // 格式验证
      if (this.format.length === 0) return true
      let valid = false
      this.format.forEach((v) => {
        let ergexp = new RegExp("\\" + v + "$")
        if (ergexp.test(type)) {
          valid = true
        }
      })
      return valid
    },
    validatorFileSize(size) { // 大小验证
      if (!this.size || (size <= this.size)) return true
    },
    clearFileList() {
      this.fileList = []
    },
    showPriview() {
      this.$emit('showPriview')
    }
  },
  computed: {
    format_upload__tip() {
      if (this.format.length === 0 && !this.size) {
        return false
      }
      let str = ''
      if (this.format.length > 0) {
        str = '只能上传'
        this.format.forEach((v) => {
          str += v + ' '
        })
        str += '格式文件'
      }
      if (this.size !== Number.MAX_SAFE_INTEGER) {
        if (this.format.length > 0) str += '、'
        str += `大小不能超过${this.formatFlowUnit(this.size, 0, true)}`
      }
      return str
    }
  }
}
</script>
<style lang="scss">
.v-upload-container {
  position: relative;
  width: 100%;
  height: 100%;

  .upload {
    position: absolute;
    top: 1px;
    left: 94px;
  }

  .el-upload-dragger {
    border: none;
    width: 100%;
    height: 100%;
    position: static;

    &.is-dragover {
      border: none;
    }
  }

  .el-upload-list__item-name {
    cursor: pointer;
  }
}
</style>