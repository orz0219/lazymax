import Vue from 'vue'
import Router from 'vue-router'
import App from './App.vue'
import 'at-ui-style'
import AtUI from 'at-ui'
import routes from '@/lib/router'
import axiosUtil from "@/lib/axios";

Vue.config.productionTip = false
Vue.use(Router)
Vue.use(AtUI)
Vue.use(axiosUtil)

const router = new Router({
  mode: 'history',
  routes
})

new Vue({
  render: h => h(App),
  router
}).$mount('#app')
