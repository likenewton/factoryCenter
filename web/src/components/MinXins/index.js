import Api from 'assets/js/api.js'
import { mapState, mapMutations } from 'vuex'

// 全局混入对象
export default {
  data() {
    return {
      addBtnDisabled: false, // 防止按钮重复提交
      loadData: true,
      searchVipVisible: false, // 是否显示高级查询dialog
      pageSizes: [10, 20, 40, 60],
      list: {
        data: [],
        pagesize: 20,
        currentPage: 1,
        total: 0,
      },
      sort: {},
      formInline: {},
      maxTableHeight: Api.UNITS.maxTableHeight(),
      winHeight: $(window).height(),
      isStripe: true, // 列表是否开始斑马线
      channelDic: {
        '手机短信': 1,
        '微信': 2,
        'xhub': 3,
        '极光': 4,
      }
    }
  },
  methods: {
    ...mapMutations([
      'SET_DATA',
    ]),
    // 操作导航条
    handleSizeChange(val) {
      this.list.pagesize = val
      this.list.currentPage = 1
      this.getData()
    },
    handleCurrentChange(val) {
      this.list.currentPage = val
      this.getData()
    },
    // 处理排序
    handleSortChange(val) {
      Api.UNITS.setSortSearch(val, this)
      this.getData()
    },
    // 表单
    resetData() {
      this.list.currentPage = 1
      this.formInline = {}
      this.sort = {}
      this.$refs.listTable && this.$refs.listTable.clearSort()
      this.$refs.listTable && this.$refs.listTable.clearSelection()
      this.getData()
    },
    simpleSearch(key) {
      let value = this.formInline[key]
      this.formInline = {
        [key]: value
      }
      this.searchData()
    },
    searchData() {
      this.list.currentPage = 1
      this.getData()
    },
    resetForm(formName, type) {
      this.addBtnDisabled = false
      this.$refs[formName].resetFields()
      this.formInline = {}
      if (type != 'add') this.getData()
    },
    showMsgBox: Api.UNITS.showMsgBox,
    showCfmBox: Api.UNITS.showCfmBox,
    formatDate: Api.UNITS.formatDate,
    formatFlowUnit: Api.UNITS.formatFlowUnit,
  },
  computed: {
    ...mapState({
      orgs: 'orgs',
      authMenu: 'authMenu', // 菜单权限
      userInfo: 'userInfo', // 用户信息
      yunovoDic: 'yunovoDic', // 云智码库
      authButtons: 'authButtons', // 按钮权限
      asideCollapse: 'asideCollapse', // 侧边栏是否隐藏
      deviceType: 'deviceType', // 设备类型
    }),
    routeName() {
      return this.$route.name
    },
    pageAuthBtn() {
      let authArr = this.authButtons[`factory_${this.routeName}`]
      if (!authArr) return {}
      return authArr
    }
  }
}
