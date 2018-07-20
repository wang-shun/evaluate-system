import Vue from 'vue'
import Vuex from 'vuex'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Login from '@/components/Login/Login'
import Index from '@/components/index/index'
import Exam from '@/components/content/exam/exam'
import ExaminationPaper from '@/components/content/exam/ExaminationPaper'
import Performance from '@/components/content/performance/performance'

Vue.use(Router)

const router = new Router({
	routes:[
		{
			path:'/',
			name: 'Index',
			component: Index
		},{
			path: '/Login',
			name:"Login",
			component: Login
		},{
			path: '/HelloWorld',
			name:"HelloWorld",
			component: HelloWorld
		},{
			path:'/Exam',
			name:"Exam",
			component:Exam
		},{
			path:"/Performance",
			name:"Performance",
			component:Performance
		},{
			path:"/ExaminationPaper",
			name:"ExaminationPaper",
			component:ExaminationPaper
		}
	]
})

router.beforeEach((to, from, next) => {
	let token = localStorage.MobilePortalUserName;
	if (to.matched.some(record => record.meta.requireAuth)){  // 判断该路由是否需要登录权限
		if (token) {  // 判断当前的token是否存在
			next();
			console.log(1)
		}
		else {
			console.log(2)
			next({
				path: '/Login',
				query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
			})
		}
	}
	else {
			console.log(to);
		console.log(3)
		next();
	}
});

export default router