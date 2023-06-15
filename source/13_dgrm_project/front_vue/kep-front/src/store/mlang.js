export const state = () => ({
  list: [],
  current: {},
})
export const mutations = {
  add: (state, payload) => {
    state.list = payload
  },
  // setCurrent: (state, payload) => {
  //   state.current = payload
  // },
  current: (state, payload) => {
    state.current = payload
  },
}
export const getters = {
  getMultiLanguage: (state) => (mlangNo) => {
    const mlang = state.list.filter((item) => item.mlang_no === mlangNo)
    if (mlang.length > 0) {
      return state.list.filter((item) => item.mlang_no === mlangNo)[0].mlang_cntn
    } else {
      return 'NO LABEL'
    }
  },
}
// action추가하여 store를 사용하는 기준에서는 action을 호출 후 mutations을 호출하도록...
export const actions = {
  setAdd: ({ commit }, payload) => {
    commit('add', payload);
  },
  setCurrent: ({ commit }, payload) => {
    commit('current', payload);
  },
};