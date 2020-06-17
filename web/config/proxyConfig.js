const config = require("../src/assets/js/config")
module.exports = {
  proxy: {
    [config.ROOT]: {
      target: config.PROXYROOT, // 接口域名
      secure: false, // 如果是https接口，需要配置这个参数
      changeOrigin: true, //是否跨域
      pathRewrite: {
        [`^${config.ROOT}`]: config.PROXYROOT //需要rewrite的
      }
    }
  }
}
