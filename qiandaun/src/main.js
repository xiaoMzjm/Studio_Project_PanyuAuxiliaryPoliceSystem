import Vue from 'vue/dist/vue.esm.js'
import VueCookie from 'vue-cookie'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import router from './router'
import store from './store'
import request from './common/request'
import download from './common/download'

Vue.use(ElementUI)
Vue.use(VueCookie)
Vue.prototype.request = request
Vue.prototype.download = download

Vue.config.productionTip = false

new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app')
