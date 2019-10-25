import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './routes/router'
import store from './store'
import vuetify from './plugins/vuetify'

import 'material-design-icons-iconfont/dist/material-design-icons.css'

Vue.config.productionTip = false

new Vue({
  router: router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')