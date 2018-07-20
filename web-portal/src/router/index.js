import Vue from 'vue'
import Router from 'vue-router'
import login from '@/views/login.vue'

import home from "@/views/home";
import welcome from "@/views/welcome/welcome";
import tklist from "@/views/subjectdb-manage/tk-list";
import testlist from "@/views/subjectdb-manage/test-list";
import createtest from "@/views/paper-manage/create-test";
import paperlist from "@/views/paper-manage/paper-list";
import testrecord from "@/views/paper-manage/test-record";


Vue.use(Router)

export default new Router({
  routes: [
    { name: "login", path: "/login", component: login },
    { 
        name: "home", 
        path: "/", 
        component: home,
        redirect: {path:'welcome'},//路由重定向        
        children:[
            {
                name:'welcome',
                path:'welcome',
                component:welcome,
            },{
                name:'tklist',
                path:'tklist',
                component:tklist,
            },{
                name:'testlist',
                path:'testlist',
                component:testlist,
            },{
                name:'createtest',
                path:'createtest',
                component:createtest,
            },{
                name:'paperlist',
                path:'paperlist',
                component:paperlist, 
            },{
                name:'testrecord',
                path:'testrecord',
                component:testrecord, 
            }

        ]
    }
  ]
})
