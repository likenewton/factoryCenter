<template>
  <div class="v-site" v-if="siteList.length > 1">
    <el-dropdown @command="handleCommand">
      <el-button type="primary" size="mini">
        {{curSite.name}}<i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <el-dropdown-menu slot="dropdown">
        <!-- userInfo 是通过混入插入到所有vue的实例中，确定你的项目有该功能(门户页暂时放置在外面) -->
        <!-- <el-dropdown-item :key="-1" :command="userInfo.ucIndexUrl">门户页</el-dropdown-item> -->
        <el-dropdown-item v-if="item.flag !== 1" v-for="(item, index) in siteList" :key="index" :command="item.url">
          <img class="site_icon_img" :src="item.iconUrl">
          <span>{{item.name}}</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>
<script>
import { mapState, mapMutations } from 'vuex'

export default {
  name: 'vSite',
  data() {
    return {}
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      _axios.send({
        method: 'get',
        // 不同的站点注意更换地址
        url: '/sso/sitez',
        done: ((res) => {
          this.SET_DATA({ 
            key: 'siteList',
            value: res.data || [],
          })
        })
      })
    },
    handleCommand(command) {
      location.href = command
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
.v-site {
  float: right;
  margin-left: 20px;
  z-index: 1;
}

.site_icon_img {
  display: inline-block;
  width: 26px;
  height: 26px;
  border-radius: 50%;
  margin-right: 4px;
}

</style>
