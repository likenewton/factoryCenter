<template>
  <div class="inner-footer">
    <span>Copyright©2014-2020&nbsp;深圳市云智易联科技有限公司&nbsp;版权所有</span>
  </div>
</template>
<script>

export default {
  name: 'vFooter',
  data() {
    return {

    }
  },
  mounted() {

  }
}

</script>
<style lang="scss">

.el-footer {
  position: absolute;
  bottom: 0;
  padding: 0;
  background: #f5f5f5;
  width: 100%;
  overflow: hidden;
}

.inner-footer {
  position: absolute;
  display: inline-block;
  height: 100%;
  padding: 0 20px;
  left: 50%;
  transform: translateX(-50%);
  margin-left: -100px;
  span {
    font-size: 16px;
    color: #999;
    line-height: 50px;
  }
}

</style>
