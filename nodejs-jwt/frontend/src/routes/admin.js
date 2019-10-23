import Vue from 'vue'
import Router from 'vue-router'
import AdminHome from '../views/admin/AdminHome.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    { path: '/admin', name: 'adminHome', component: AdminHome },
  ]
})
