import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Example from './views/Example.vue'
import Login from './views/Login.vue'
import Join from './views/Join.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    { path: '/', name: 'home', component: Home },
    { path: '/example', name: 'example', component: Example },
    { path: '/login', name: 'login', component: Login },
    { path: '/join', name: 'join', component: Join },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
    }
  ]
})
