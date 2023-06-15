export const state = () => ({
  list: [],
  current: {},
});
export const mutations = {
  add: (state, payload) => {
    state.list = payload;
  },
  // setCurrent: (state, payload) => {
  //   state.current = payload;
  // },
  current: (state, payload) => {
    state.current = payload;
  },
};
export const getters = {
  getMainMenus: (state) => {
    return state.list.filter((item) => item.level === 0);
  },
  getSubMenus: (state) => (id) => {
    return state.list.filter((item) => item.up_menu_no === id);
  },
  getCurrent: (state) => {
    return state.current;
  },
};
// action추가하여 store를 사용하는 기준에서는 action을 호출 후 mutations을 호출하도록...
export const actions = {
  setAdd: ({ commit }, payload) => {
    commit('add', payload);
  },
  setCurrent: ({ commit }, payload) => {
    commit('current', payload);
  },
};
