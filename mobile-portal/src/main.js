// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import MintUI from 'mint-ui'
import 'mint-ui/lib/style.css'
import './assets/Reset.css'
import './Icon/iconfont.css'

Vue.config.productionTip = false

Vue.use(MintUI);

Vue.prototype.$ajax = axios;


//全局守卫
router.beforeEach((to, from, next) => {
let token = sessionStorage.getItem("myid");
if (token) {
    next()
} else {
    if (to.path === '/Login') {
      next()
    } else {
      next({ path: '/Login' })
    }
}
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})