
const SET_DATA = 'SET_DATA' // 站点列表

export default {
  [SET_DATA](state, paras) {
    state[paras.key] = paras.value
  }
}
