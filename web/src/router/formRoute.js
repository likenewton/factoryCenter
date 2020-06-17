// [chunk2](侧边栏菜单动态路由)
const Container = r => require.ensure([], () => r(require('@/components/container/index.vue')), 'chunk1')

// [chunk3](一般是表单增删改查页面)
//const TaskForm = r => require.ensure([], () => r(require('@/components/pages/manage/taskForm.vue')), 'chunk3') // 任务

let formRoute = {
  // 表单文件
  path: '/forms',
  name: 'forms',
  component: Container,
  /*children: [{
    path: 'taskForm',
    name: 'taskForm',
    component: TaskForm
  }]*/
}

export default formRoute
