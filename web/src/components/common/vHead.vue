<template>
  <div class="inner-head">
    <v-store></v-store>
    <img class="logo" src="../../assets/images/LOGO-188X56.png">
    <i v-if="showMenu" class="iconfont-caidan1 pointer menu-icon" @click="collapseAside"></i>
    <el-tooltip v-if="showMenu" class="sysmenu fr pointer" effect="dark" content="回到门户" placement="bottom-end">
      <i class="iconfont-tuichu1" style="color:#606266" @click="toUcIndexUrl"></i>
    </el-tooltip>
    <v-site></v-site>
    <el-dropdown trigger="click" class="pointer fr" @command="handleCommand">
      <span class="el-dropdown-link">{{userInfo.userName}}<i class="el-icon-caret-bottom el-icon--right"></i></span>
      <el-dropdown-menu slot="dropdown" style="">
        <el-dropdown-item command="quit" icon="iconfont-icon-tuichu">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <div class="avatar fr">
      <img :src="curSite.iconUrl">
    </div>
  </div>
</template>
<script>
import Api from 'assets/js/api.js'
import { mapState } from 'vuex'

export default {
  name: 'vHead',
  data() {
    return {
      // 控制侧边栏的展示
      isShow: true,
    }
  },
  props: {
    showMenu: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    handleCommand(command) {
      if (command === 'quit') {
        // 这里做一些退出的操作然后跳转注销页面
        location.href = this.userInfo.logoutUrl
      }
    },
    // 回到门户
    toUcIndexUrl() {
      location.href = this.userInfo.ucIndexUrl
    },
    collapseAside() {
      if (this.isShow && this.asideCollapse) {
        this.SET_DATA({
          key: 'asideCollapse',
          value: false
        })
        return
      }
      if (this.isShow) {
        $('.el-aside').addClass('el-collapse--none')
      } else {
        $('.el-aside').removeClass('el-collapse--none')
      }
      this.SET_DATA({
        key: 'asideCollapse',
        value: !this.asideCollapse
      })
      this.isShow = !this.isShow
    }
  },
  computed: {
    ...mapState({
      siteList: 'siteList', // 站点列表
    }),
    curSite() {
      let curSite = this.siteList.filter((v) => v.flag === 1)
      return curSite[0] || {}
    }
  }
}

</script>
<style lang="scss">
.el-header {
  font-size: 12px;
  background-color: #fff;
  border-bottom: 1px solid #D9DEE4;
  color: #333;
  line-height: 60px;

  .inner-head {
    padding: 0 20px 0 0;

    .logo {
      width: 132px;
      height: 26px;
      margin-top: -8px;
    }

    .menu-icon {
      color: #5A738E;
      font-size: 25px;
      vertical-align: middle;
      margin-left: 10px;
    }

    .avatar {
      position: relative;
      width: 40px;
      height: 40px;
      border-radius: 50%;
      margin: 10px 20px 0 0;
      overflow: hidden;

      img {
        position: absolute;
        width: 40px;
        height: 40px;
        top: 0;
        left: 0;
        width: 100%;
      }
    }

    .sysmenu {
      font-size: 22px;
      margin: 0 0 0 15px;
    }
  }
}

</style>
