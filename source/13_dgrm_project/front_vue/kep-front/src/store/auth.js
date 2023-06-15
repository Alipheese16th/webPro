export const state = () => ({
  login: false,
  user: {},
});
export const mutations = {
  add: (state, payload) => {
    state.login = true;
    state.user = payload;
  },
};
export const getters = {
  isLogin: (state) => {
    return state.login;
  },
  getUser: (state) => {
    return state.user;
  },
  getUsername: (state) => {
    return state.user.emp_no;
  },
};
export const actions = {
  // setUser({ commit }) {
  //   console.log('action start') // eslint-disable-line no-console
  //   this.$axios.$get(`/api/v1/common/whoami`).then((res) => {
  //     commit('add', res)
  //     console.log('result : ', res) // eslint-disable-line no-console
  //   })
  // },
  /**
   * 로그인 후 전달받은 사용자 정보를 넣어준다.
   * @param {*} param0 
   * @param {*} payload 
   */
  setUser: ({ commit }, payload) => {
    console.log('사용자 정보 입력');
    commit('add', payload);
  },
};
