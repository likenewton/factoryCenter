// 指令
function shadow(paras) {
  if (paras.scrollTop === 0) {
    if (paras.scrollHeight > paras.offsetHeight) {
      return 'inset 0px -12px 5px -12px rgba(0, 0, 0, 0.15)'
    } else {
      return ''
    }
  } else {
    if (paras.scrollHeight > paras.offsetHeight + paras.scrollTop) {
      return 'inset 0px 12px 5px -12px rgba(0, 0, 0, 0.2), inset 0px -12px 5px -12px rgba(0, 0, 0, 0.15)'
    } else {
      return 'inset 0px 12px 5px -12px rgba(0, 0, 0, 0.15)'
    }
  }
}

// 指令

// 让被绑定的元素无法被选择
Vue.directive('unselect', {
  // 当被绑定的元素插入到 DOM 中时……
  inserted: function(el) {
    $(el).find('input').attr('unselectable', 'on')
  }
})

// 当盒子内容超出盒子高度时，父盒子出现上下阴影
Vue.directive('shadow', {
  update: function(el, binding) {
    Vue.nextTick(() => {
      let boxShadow = shadow(el)
      el.style.boxShadow = boxShadow
    })
  },
  bind: function(el) {
    el.__vueClickOutside__ = function(e) {
      let boxShadow = shadow(e.target)
      el.style.boxShadow = boxShadow
    }
    el.addEventListener('scroll', el.__vueClickOutside__)
  },
  unbind: function(el) {
		el.removeEventListener('click', el.__vueClickOutside__)
  }
})
