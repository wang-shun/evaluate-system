// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import jquery from 'jquery'
import store from './store/store'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './styles/index.scss'

Vue.prototype.HOST='/api'
Vue.use(ElementUI)
Vue.config.productionTip = false

//全局守卫
router.beforeEach((to, from, next) => {
	let token = localStorage.getItem('myid');
	if (token) {
		next()
	} else {
		if (to.path === '/login') {
			next()
		} else {
			next({ path: '/login' })
		}
	}
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
