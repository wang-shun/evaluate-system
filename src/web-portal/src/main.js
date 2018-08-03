// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store/store'
import ElementUI from 'element-ui'
import eventListener from './router/eventListener'

import 'element-ui/lib/theme-chalk/index.css'
import './assets/addFont/iconfont.css'
import './styles/index.scss'

Vue.prototype.HOST = '/api'
Vue.use(ElementUI)
Vue.config.productionTip = false

global.app = eventListener

// 全局守卫
router.beforeEach((to, from, next) => {
  let token = localStorage.getItem('myid')
  if (token) {
    next()
  } else {
    if (to.path === '/admin/login') {
      next()
    } else {
      next({
        name: 'login',
        path: '/admin/login'
      })
    }
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: {
    App
  },
  template: '<App/>'
})
