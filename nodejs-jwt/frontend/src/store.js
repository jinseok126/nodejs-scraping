import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token')
  },

  // state 값을 변경하는 부분
  mutations: {
    removeToken: function(state, payload) {
      return state.token = null;
    }
  },
  actions: {

  },
  getters: {
    getToken: function(state) {
      return state.token;
    }
  }
})
