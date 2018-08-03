import Vue from 'vue'
import Router from 'vue-router'

let login = () => import('@/components/Login/Login')
let index = () => import('@/components/index/index')
let exam = () => import('@/components/content/exam/exam')
let examinationPaper = () => import('@/components/content/exam/ExaminationPaper')
let performance = () => import('@/components/content/performance/performance')
let stuChangePwd = () => import('@/components/Login/stuChangePwd')

Vue.use(Router)

const router = new Router({
  routes: [{
    path: '/',
    name: 'index',
    component: index
  }, {
    path: '/login',
    name: 'login',
    component: login
  }, {
    path: '/exam',
    name: 'exam',
    component: exam
  }, {
    path: '/performance',
    name: 'performance',
    component: performance
  }, {
    path: '/examinationPaper',
    name: 'examinationPaper',
    component: examinationPaper
  }, {
    path: '/stuChangePwd',
    name: 'stuChangePwd',
    component: stuChangePwd
  }]
})

// router.beforeEach((to, from, next) => {
//   let userId = sessionStorage.myid
//   if (userId) {
//     next()
//   } else {
//     alert('登录超时')
//   }
// })

export default router
