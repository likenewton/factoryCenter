module.exports = {

  // === 项目核心 start ===
  getAuthMenu(asideData = [], resources = []) { // 获取权限菜单列表
    // 排序
    resources.sort((v1, v2) => {
      return v1.seqNum - v2.seqNum
    })
    resources.forEach((v) => {
      let children = v.childResources || []
      children.sort((v1, v2) => {
        return v1.seqNum - v2.seqNum
      })
    })
    // 如果后台配置的页面在所有已开发的页面中都找不到不让其加到菜单列表当中
    let newResource = []
    resources.forEach((v1, i1) => {
      let level_1 = false
      asideData.forEach((v2, i2) => {
        if (v1.resUrl === v2.name) {
          v1.icon = v2.icon
          level_1 = true
        }
      })
      if (level_1) {
        newResource.push(v1)
      }
    })
    return newResource
  },

  getMenuRoute(menuRoute = [], resources = []) { // 获取菜单动态路由
    menuRoute.children.forEach((v1, i1) => {
      let level_1 = false
      resources.forEach((r1, j1) => {
        if (v1.name === r1.resUrl) {
          level_1 = true
          if (r1.childResources && r1.childResources[0]) {
            v1.redirect = `/menu/${r1.resUrl}/${r1.childResources[0].resUrl}`
          }
          let v1children = v1.children || []
          v1children.forEach((v2, i2) => {
            let level_2 = false
            let r1children = r1.childResources || []
            r1children.forEach((r2, j2) => {
              if (v2.name === r2.resUrl) {
                level_2 = true
              }
            })
            if (!level_2) {
              v1.children.splice(i2, 1)
            }
          })
        }
      })
      if (!level_1) {
        menuRoute.children.splice(i1, 1)
      }
    })
    return menuRoute
  },

  setSortSearch(val, _this, sort = 'sort') { // 设置升降序字段
    if (!val.prop) return _this[sort] = {}
    if (val.order === 'descending') { // 降序
      _this[sort] = {
        descs: val.prop
      }
    } else if (val.order === 'ascending') { // 升序
      _this[sort] = {
        ascs: val.prop,
      }
    } else { // 不排序
      _this[sort] = {}
    }
  },

  getListData(paras) { // 获取列表数据
    let para = paras
    let list = para.vue[para.list || 'list']
    let sort = para.vue[para.sort || 'sort']
    let formInline = para.vue[para.formInline || 'formInline'] || {}
    if (list.loadData === undefined) para.vue[para.loadData || 'loadData'] = true
    else list.loadData = true

    let submitData = {
      method: paras.method || 'get',
      url: para.url,
      done: (res) => {
        if (list.loadData === undefined) para.vue[para.loadData || 'loadData'] = false
        else list.loadData = false
        para.vue.searchVipVisible = false // 高级查询功能
        list.data = res.data.records || [],
          list.total = res.data.total || 0
        para.cb && para.cb(res)
      }
    }

    if (paras.method) {
      submitData.data = $.extend(paras.data || {}, formInline, {
        asc: sort.ascs,
        desc: sort.descs,
        pageSize: list.pagesize,
        page: list.currentPage
      })
    } else {
      submitData.params = $.extend(paras.data || {}, formInline, {
        asc: sort.ascs,
        desc: sort.descs,
        pageSize: list.pagesize,
        page: list.currentPage
      })
    }

    _axios.send(submitData)
  },

  getBreadArr(name, authMenu) { // 获取面包屑导航
    let breadArr = []
    if (name === 'home') return [, , 'home']
    authMenu.forEach((v1) => {
      if (v1.resUrl === name) {
        breadArr.push('home', v1.resUrl)
        return false
      }
      let children = v1.childResources || []
      children.forEach((v2) => {
        if (v2.resUrl === name) {
          breadArr.push('home', v1.resUrl, v2.resUrl)
          return false
        }
      })
    })
    if (breadArr.length > 0) {
      // 点击的是侧边栏页面
      sessionStorage.setItem('authMenu', breadArr)
    } else {
      // 打开的不是侧边栏页面，将目录拉取下来
      breadArr = sessionStorage.getItem('authMenu')
      if (breadArr) {
        // 如果有数据就代表是通过其他路由进入的
        breadArr = breadArr.split(',')
      } else {
        // 如果没有数据就代表是直接通过url进入的默认进入 第一个菜单第一项
        breadArr = ['home', authMenu[0].resUrl, authMenu[0].childResources[0].resUrl]
      }
    }
    return breadArr
  },

  maxTableHeight(leftHeight = 316) { // 设备main页面高度，使页面始终保持在页面最下方
    let winWidth = $(window).width()
    // let zoom = winWidth > 1200 ? winWidth / window.screen.width : 1
    let zoom = 1
    let calcHeight = ($(window).height() - leftHeight * zoom) / zoom
    return calcHeight
  },
  // === 项目核心 end ===

  // === 格式、约束 start ===
  formatDate(date, fmt) { // 格式时间
    date = new Date(date);
    let timeString = fmt || 'yyyy-mm-dd hh:ff:ss';
    let getFullYear = String(date.getFullYear());

    function padLeftZero(str) {
      var padLeft = '00';
      return (padLeft + str).substr(str.length);
    }

    // 如果存在至少一个y
    if (/(y+)/.test(timeString)) {
      // RegExp.$1 为匹配第一个大括号的内容
      timeString = timeString.replace(RegExp.$1, getFullYear.substr(4 - RegExp.$1.length));
    }
    let o = {
      'm+': date.getMonth() + 1,
      'd+': date.getDate(),
      'h+': date.getHours(),
      'f+': date.getMinutes(),
      's+': date.getSeconds(),
    };
    for (var k in o) {
      if (new RegExp(`(${k})`).test(timeString)) {
        let str = String(o[k]);
        timeString = timeString.replace(RegExp.$1, str.length == 1 ? padLeftZero(str) : str);
      }
    }
    return timeString;
  },

  // 格式化 流量M / G / T, 默认保留三位小数
  formatFlowUnit(count = 0, fix = 3, isHtmlStr = true) {
    count -= 0
    let htmlStr = ''
    if (isHtmlStr) {
      if (isNaN(count) || count == '99999999') {
        htmlStr = `<span style="font-weight:bold" class="text_danger">无限制</span>`
      } else if (Math.abs(count / 1024) < 1) {
        htmlStr = `<span>${count.toFixed(fix)}</span><span style="font-weight:bold" class="text_success">&nbsp;M</span>`
      } else if (Math.abs(count / 1024 / 1024) < 1) {
        htmlStr = `<span>${(count / 1024).toFixed(fix)}</span><span style="font-weight:bold" class="text_primary">&nbsp;G</span>`
      } else {
        htmlStr = `<span>${(count / 1024 / 1024).toFixed(fix)}</span><span style="font-weight:bold" class="text_danger">&nbsp;T</span>`
      }
    } else {
      if (isNaN(count) || count == '99999999') {
        htmlStr = '无限制'
      } else if (Math.abs(count / 1024) < 1) {
        htmlStr = `${count.toFixed(fix)} M`
      } else if (Math.abs(count / 1024 / 1024) < 1) {
        htmlStr = `${(count / 1024).toFixed(fix)} G`
      } else {
        htmlStr = `${(count / 1024 / 1024).toFixed(fix)} T`
      }
    }
    return htmlStr
  },

  startDatePicker(vue, end) { // 时间选择器约束-开始时间
    return {
      disabledDate(time) {
        if (end) {
          return new Date(end).getTime() <= time.getTime() || time.getTime() > Date.now()
        } else {
          return time.getTime() > Date.now()
        }
      }
    }
  },

  endDatePicker(vue, start) { // 时间选择器约束-结束时间
    return {
      disabledDate(time) {
        if (start) {
          return new Date(start).getTime() - 3600000 * 8 > time.getTime() || time.getTime() > Date.now()
        } else {
          return time.getTime() > Date.now()
        }
      }
    }
  },

  validatorPhoneNumber(value) { // 验证电话号码
    return /^1[3|4|5|6|7|8|9][0-9]\d{8}$/.test(value)
  },

  limitNumber(val, num1 = 3, num2 = 0) { // 限制小数位数和整数位数
    let expStr = ''
    if (num2 === 0) {
      // 如果是整数
      expStr = `^\\d{1,${num1}}`
    } else {
      // 如果是小数
      expStr = `^\\d{1,${num1}}(\\.\\d{0,${num2}})?`
    }
    return val.match(new RegExp(expStr)) && val.match(new RegExp(expStr))[0]
  },

  valueToLabel(value, data = [], tag = 'label', key = 'value') {
    let label = ''
    data.forEach((v) => {
      if (v[key] == value) {
        label = v[tag]
      }
    })
    return label
  },
  // === 格式化 end ===

  // === 提示、显示 start ===
  showMsgBox(para = {}) { // 提示
    Vue.prototype.$notify({
      type: para.type || 'error',
      title: para.title || (para.type === 'error' || !para.type ? '错误' : '温馨提示'),
      message: para.message || '提交的表单数据不符合规范！',
      duration: isNaN(para.duration) ? 4500 : para.duration
    })
  },

  showCfmBox(para = {}) { // 确认框
    Vue.prototype.$confirm(`${para.message || '确定执行该操作吗？'}`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: para.type || 'warning'
    }).then(() => {
      para.cb && para.cb()
    }).catch(() => {
      this.showMsgBox({
        type: 'info',
        message: '操作已取消'
      })
    })
  },

  showTxT(fileName, text) { // 浏览器打开.txt文件（ie不支持）
    let pom = document.createElement("a")
    pom.setAttribute(
      "href",
      "data:text/plain;charset=utf-8," + encodeURIComponent(text)
    )
    pom.setAttribute("download", fileName)
    if (document.createEvent) {
      let event = document.createEvent("MouseEvents")
      event.initEvent("click", true, true)
      pom.dispatchEvent(event)
    } else {
      pom.click()
    }
  },
  // === 提示、显示 end ===

  // === 工具 start ===
  getColorList(para, count) { // 获取主题颜色
    let colorList = ['#3cb1ff', '#ffc367', '#ff7477', '#27da99', '#3ecec9', '#9a83da']
    let colorData = {
      primary: '#3cb1ff',
      warning: '#ffc367',
      danger: '#ff7477',
      success: '#27da99',
      editor: '#3ecec9',
      purple: '#9a83da'
    }
    if (!count) {
      if (!para) return colorList
      else if (typeof para === 'string') {
        return colorData[para]
      } else if (Array.isArray(para)) {
        let arr = []
        para.forEach((v) => {
          arr.push(colorData[v])
        })
        return arr
      }
    } else {
      if (typeof para === 'string') {
        let arr = []
        for (let i = 0; i < count; i++) {
          arr.push(colorData[para])
        }
        return arr
      }
      if (Array.isArray(para)) {
        if (para.length === 0) {
          let arr = []
          for (let i = 0; i < Math.ceil(count / 6); i++) {
            arr = arr.concat(colorList)
          }
          return arr.slice(0, count)
        } else {
          let arr = []
          for (let i = 0; i < Math.ceil(count / para.length); i++) {
            arr = arr.concat(para)
          }
          return arr.slice(0, count)
        }
      }
    }
  },

  getQuery(attr) { // 获取查询字符串
    let href = location.href
    let queryStr = href.substr(href.indexOf('?') + 1)
    let queryArr = queryStr.split('&')
    let queryObj = {}
    queryArr.forEach(v => {
      let tplArr = v.split('=')
      queryObj[tplArr[0]] = tplArr[1]
    })
    if (attr) return queryObj[attr]
    else return queryObj
  },

  setCookie(cname, cvalue, exhours) { // 设置cookie
    let expires = 'expires='
    if (exhours) {
      let d = new Date()
      d.setTime(d.getTime() + (exhours * 60 * 60 * 1000))
      expires += d.toUTCString()
      document.cookie = `${cname}=${cvalue};${expires};path=/`
    } else {
      document.cookie = `${cname}=${cvalue};path=/`
    }
  },

  getCookie(attr) { // 获取cookie
    let cookieStr = document.cookie
    let cookieArr = cookieStr.split(';')
    let cookieObj = {}
    cookieArr.forEach((v) => {
      let tplArr = v.split('=')
      while (tplArr[0].charAt(0) === ' ') {
        tplArr[0] = tplArr[0].substring(1)
      }
      cookieObj[tplArr[0]] = tplArr[1]
    })
    if (attr) return cookieObj[attr]
    else return cookieObj
  },

  exportExcel(url, params = {}) { // 导出excel
    let link = `${url}?iov-token=${this.getCookie('iov-token-factory')}`
    for (let key in params) {
      if (params[key]) {
        link += `&${key}=${params[key]}`
      }
    }
    window.open(link, '_self')
  },
  // === 工具 end ===

  // === 其它 start ===
  compatibel_Ie_input() { // 处理input在IE浏览器readonly属性失效
    $('.el-select .el-input__inner').attr("unselectable", "on")
  },
  // === 其它 end ===

}
