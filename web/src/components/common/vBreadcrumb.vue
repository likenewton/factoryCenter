<template>
  <el-breadcrumb v-if="asideFlag.length > 0" separator-class="el-icon-arrow-right">
    <el-breadcrumb-item :to="{name: routeName}" v-for="routeName in asideFlag" :key="routeName">{{getBreadTitle(routeName)}}</el-breadcrumb-item>
    <el-breadcrumb-item v-if="thirdTitle">{{thirdTitle}}</el-breadcrumb-item>
  </el-breadcrumb>
</template>
<script>
import Api from 'assets/js/api.js'
import { mapState } from 'vuex'

export default {
  name: 'vBreadcrumb',
  data() {
    return {
      operData: Api.STATIC.operData
    }
  },
  methods: {
    // 获取相应状态的标题
    getBranchTitle(data) {
      if (typeof data === 'string') {
        return data
      } else {
        if (this.$route.query.type) {
          return data[this.$route.query.type]
        } else {
          return data['create']
        }
      }
    },
    // 获取跳转路由
    getBreadTitle(routeName) {
      let title = ''
      if (routeName === 'home') {
        return '首页'
      }
      this.isShowBreadCrumb = true
      this.authMenu.forEach((v1) => {
        if (v1.resUrl === routeName) {
          title = v1.resName
          return false
        }
        let children = v1.childResources || []
        children.forEach((v2) => {
          if (v2.resUrl === routeName) {
            title = v2.resName
            return false
          }
        })
      })
      return title
    }
  },
  computed: {
    ...mapState({
      asideFlag: 'asideFlag',
      authMenu: 'authMenu'
    }),
    routeName() {
      return this.$route.name
    },
    thirdTitle() {
      // 根据query.title, 来决定面包屑的名称
      let thirdTitle = ''
      if (this.$route.query && this.$route.query.title) {
        thirdTitle = this.$route.query.title
      }

      return thirdTitle
    }
  },

}

</script>
<style lang="scss">
.el-breadcrumb {
  position: relative;
  display: flex;
  width: 100%;
  align-items: center;
  height: 35px;
  min-height: 35px;
  padding: 0 20px;

  * {
    font-size: 14px;
  }
}

</style>
