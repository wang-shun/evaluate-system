import Vue from 'vue'
import Router from 'vue-router'
import login from '@/views/login.vue'

import home from '@/views/home'
// import welcome from '@/views/welcome/welcome'
import tklist from '@/views/subjectdb-manage/tk-list'
import testlist from '@/views/subjectdb-manage/test-list'
import createtest from '@/views/paper-manage/create-test'
import paperlist from '@/views/paper-manage/paper-list'
import testrecord from '@/views/paper-manage/test-record'
import changePassword from '@/views/personal-setting/change-password'
import paperTemplate from '@/views/paper-manage/paperTemplate'
import addPerson from '@/views/paper-manage/addPerson'
import department from '@/views/personal-setting/department'
import employee from '@/views/personal-setting/employee'
import resetStuPwd from '@/views/personal-setting/resetStuPwd'
import addTestQuestions from '@/views/subjectdb-manage/addTestQuestions'
import paperTemplateList from '@/views/paper-manage/paperTemplateList'
import questionTemplateModification from '@/views/paper-manage/questionTemplateModification'

Vue.use(Router)

export
default new Router({
  routes: [{
    name: 'login',
    path: '/admin/login',
    component: login
  }, {
    name: 'home',
    path: '/',
    component: home,
    redirect: {
      path: 'admin/tklist'
    },
    // 路由重定向
    children: [{
      name: 'tklist',
      path: 'admin/tklist',
      component: tklist
    }, {
      name: 'testlist',
      path: 'admin/testlist',
      component: testlist
    }, {
      name: 'createtest',
      path: 'admin/createtest',
      component: createtest
    }, {
      name: 'paperlist',
      path: 'admin/paperlist',
      component: paperlist
    }, {
      name: 'testrecord',
      path: 'admin/testrecord',
      component: testrecord
    }, {
      name: 'changePassword',
      path: 'admin/changePassword',
      component: changePassword
    }, {
      name: 'paperTemplate',
      path: 'admin/paperTemplate',
      component: paperTemplate
    }, {
      name: 'addPerson',
      path: 'admin/addPerson',
      component: addPerson
    },
    {
      name: 'department',
      path: 'admin/department',
      component: department
    },
    {
      name: 'employee',
      path: 'admin/employee',
      component: employee
    }, {
      name: 'resetStuPwd',
      path: 'admin/resetStuPwd',
      component: resetStuPwd
    }, {
      name: 'addTestQuestions',
      path: 'admin/addTestQuestions',
      component: addTestQuestions
    }, {
      name: 'paperTemplateList',
      path: 'admin/paperTemplateList',
      component: paperTemplateList
    }, {
      name: 'questionTemplateModification',
      path: 'admin/questionTemplateModification',
      component: questionTemplateModification
    }]
  }]
})
