<template>
  <el-container style="height: 100%">
    <el-header>
      <v-head></v-head>
    </el-header>
    <el-container>
      <v-aside></v-aside>
      <el-container>
        <el-main class="small-scroll">
          <div class="main-inner">
            <v-breadcrumb></v-breadcrumb>
            <router-view></router-view>
            <el-footer height="50px">
              <v-footer></v-footer>
            </el-footer>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>
<script>
export default {
  data() {
    return {}
  }
}

</script>
<style lang="scss">

.el-container {
  overflow: hidden;
}

.el-main {
  position: relative;
  padding: 20px 20px 0 !important;
  background: #f5f5f5;
  overflow-x: hidden;

  .main-inner {
    position: relative;
    padding: 0 0 50px;
    width: 100%;
    min-height: 100%;
  }
}

</style>
