import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import routes from './routes/router'
import adminRouter from './routes/admin'
// import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import VueRouter from 'vue-router'

Vue.config.productionTip = false

new Vue({
  router: routes,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
