<template>
  <div class="v-store-container"></div>
</template>
<script>
import { mapActions } from 'vuex'

// 该组件主要用于vuex 中获取只需获取一次的数据，用于公共的展示
export default {
  name: 'vStore',
  data() {
    return {}
  },
  created() {
    this.getUserInfo()
    this.getAuthButtons()
    this.getOrgs()
    this.getYunovoDic()
  },
  methods: {
    ...mapActions([
      'getOrgs',
      'getUserInfo',
      'getAuthButtons',
      'getYunovoDic',
    ])
  }
}

</script>
<style lang="scss">
.v-store-container {
  position: fixed;
  height: 0;
  width: 0;
}

</style>
