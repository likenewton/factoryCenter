<template>
  <el-aside style="width: auto">
    <el-menu class="menu-container" :default-active="selectData[2]" :default-openeds="[selectData[1]]" :collapse="asideCollapse" :collapse-transition="true" unique-opened>
      <!-- 首页 -->
      <router-link :to="{name: 'home'}">
        <el-menu-item class="no-submenu" index="home">
          <i class="menu-icon iconfont-shouye" style="font-size: 20px"></i>
          <span class="title-text">首页</span>
        </el-menu-item>
      </router-link>
      <!-- 循环渲染的列表页 -->
      <el-submenu :resUrl="menuItem.resUrl" :index="menuItem.resUrl" v-for="menuItem in authMenu" :key="menuItem.resUrl">
        <!-- 拥有二级菜单 -->
        <template v-if="menuItem.childResources && menuItem.childResources.length > 0" slot="title">
          <i class="submenu-icon" :class="menuItem.icon"></i>
          <span class="title-text">{{menuItem.resName}}</span>
        </template>
        <el-menu-item-group v-if="menuItem.childResources && menuItem.childResources.length > 0">
          <router-link :to="{name: submenuItem.resUrl}" v-for="submenuItem in menuItem.childResources" :key="submenuItem.resUrl">
            <el-menu-item class="sub-item" :index="submenuItem.resUrl">{{submenuItem.resName}}</el-menu-item>
          </router-link>
        </el-menu-item-group>
        <!-- 只有一级菜单 -->
        <template v-if="!menuItem.childResources || menuItem.childResources.length === 0" slot="title">
          <router-link class="single-meun" :to="{name: menuItem.resUrl}" :class="{'is-active': menuItem.resUrl == selectData[1]}">
            <i class="submenu-icon" :class="menuItem.icon"></i>
            <span class="title-text">{{menuItem.resName}}</span>
          </router-link>
        </template>
      </el-submenu>
      <div class="collapse-control pointer" @click="setCollapse">
        <i v-show="!asideCollapse" class="el-icon-arrow-left"></i>
        <i v-show="asideCollapse" class="el-icon-arrow-right"></i>
      </div>
    </el-menu>
  </el-aside>
</template>
<script>
import Api from 'assets/js/api.js'
import { mapState, mapMutations } from 'vuex'

export default {
  name: 'vAside',
  data() {
    return {}
  },
  mounted() {
    this.testFn()
  },
  computed: {
    ...mapState({
      asideCollapse: 'asideCollapse',
      asideFlag: 'asideFlag',
      authMenu: 'authMenu'
    }),
    routeName() {
      return this.$route.name
    },
    selectData() {
      return Api.UNITS.getBreadArr(this.routeName, this.authMenu)
    }
  },
  methods: {
    setCollapse() {
      this.SET_DATA({
        key: 'asideCollapse',
        value: !this.asideCollapse
      })
    },
    testFn() {
      if (this.selectData[0]) {
        this.SET_DATA({
          key: 'asideFlag',
          value: this.selectData
        })
      } else {
        this.SET_DATA({
          key: 'asideFlag',
          value: []
        })
      }
    }
  },
  watch: {
    '$route': function(newVal, oldVal) {
      this.testFn()
      Vue.nextTick(() => {
        Api.UNITS.compatibel_Ie_input() // 具有readOnly属性的input在IE下仍然会聚焦，且按backspace按钮会执行回退操作
      })
    }
  }
}

</script>
<style lang="scss">
.el-menu-item-group__title {
  padding-top: 0;
  padding-bottom: 0;
}

.no-submenu {
  padding-left: 20px;
  background: $asideBackgroundColor !important;

  .menu-icon {
    font-size: 18px;
    color: $iconNormalColor;
  }

  .title-text {
    font-size: 16px;
  }

  &.is-active {
    .menu-icon {
      color: $iconActiveColor;
    }
  }

  &:hover {
    background: $asideBackgroundColor;
  }
}

.el-aside.el-collapse--none {
  overflow: hidden;

  .el-menu--collapse {
    width: 0 !important;
  }
}

.el-aside {
  position: relative;
  padding-top: 30px;
  background: $asideBackgroundColor;
  color: #333;
  overflow-x: hidden;

  * {
    background: $asideBackgroundColor;
    color: #fff;
  }

  i {
    color: #fff;
  }

  .title-text {
    margin-left: 8px;
  }

  .menu-container {
    padding-bottom: 70px;
  }

  .el-menu {
    width: 200px;
    border: none;
    min-height: 100%;

    .el-submenu {

      .el-submenu__title {
        background: $asideBackgroundColor;

        i {
          font-size: 18px;
          color: $iconNormalColor;

          &.submenu-icon {
            font-size: 20px;
          }

          &.el-submenu__icon-arrow {
            color: #fff;
            margin-top: -9px;
          }
        }

        * {
          font-size: 16px;
        }

        .router-link-exact-active {
          i {
            color: $iconActiveColor;
          }
        }

        .single-meun {
          i {
            color: $iconNormalColor;
          }

          &.is-active {
            i {
              color: $iconActiveColor;
            }
          }
        }

        .single-meun+.el-submenu__icon-arrow {
          display: none;
        }
      }


      &.is-active {

        .el-submenu__title {
          i {
            color: $iconActiveColor;

            &.el-submenu__icon-arrow {
              color: #fff;
              margin-top: -9px;
            }
          }
        }
      }
    }

    .el-menu-item {
      background: none;
    }

    .sub-item {
      height: 45px;
      line-height: 45px;
      padding-left: 50px !important;

      &:hover {
        background: #2c5067 !important;
      }

      &.is-active {
        background: #2c5067;
        color: #fff;
      }
    }

    &.el-menu--collapse {
      width: 60px;

      .title-text {
        display: none;
      }
    }

    &.el-menu--none {
      width: 0;
    }

  }

  .collapse-control {
    position: absolute;
    width: 100%;
    padding: 0 20px;
    bottom: 0;
    height: 50px;
    background: rgba(0, 0, 0, .2);
    display: flex;
    align-items: center;
    justify-content: flex-end;

    &:hover {
      i {
        color: rgba(255, 255, 255, 1);
      }
    }

    i {
      font-weight: bold;
      background: transparent;
      color: rgba(255, 255, 255, 0.6);
    }
  }

}

</style>
