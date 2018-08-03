// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import Mint from 'mint-ui'
import 'mint-ui/lib/style.css'
import './assets/Reset.css'
import './Icon/iconfont.css'

Vue.config.productionTip = false
Vue.use(Mint)
Vue.prototype.$ajax = axios

// 全局守卫
router.beforeEach((to, from, next) => {
  let token = sessionStorage.getItem('myid')
  if (to.path !== '/login' && !token) {
    next({ path: '/login' })
  } else {
    next()
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: {
    App
  },
  template: '<App/>',
  data () {
    return {
      Bus: new Vue() // 给根组件app添加Bus属性(这样所有组件都可以通过this.$root.Bus访问到它,而且不需要引入任何文件)
    }
  }
})
