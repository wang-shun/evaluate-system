<template>
  <div class="home">
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="200px">
        <div class="logo">
          <img src="../assets/logo.png" />
        </div>
        <el-menu :router="true" class="el-menu-admin"  :default-active="currentActiveDirectory"  @open="handleOpen" @select="activateOptionFun" @close="handleClose" background-color="rgba(0,0,0,0)" text-color="#fff"
          active-text-color="#ffd04b">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span> 题库管理 </span>
            </template>
            <el-menu-item index="tklist">
              <i class="el-icon-menu"> </i>
              <span slot="title"> 题库管理 </span>
            </el-menu-item>
            <el-menu-item index="testlist">
              <i class="el-icon-menu"> </i>
              <span slot="title"> 试题列表 </span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-location"> </i>
              <span>试卷管理 </span>
            </template>
            <el-menu-item index="createtest">
              <i class="el-icon-menu"> </i>
              <span slot="title"> 发布考试 </span>
            </el-menu-item>
            <el-menu-item index="paperlist">
              <i class="el-icon-menu"> </i>
              <span slot="title"> 考试管理 </span>
            </el-menu-item>
            <el-menu-item index="paperTemplateList">
              <i class="el-icon-menu"> </i>
              <span slot="title"> 试题模板 </span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-location"> </i>
              <span> 系统管理 </span>
            </template>
            <el-menu-item index="department">
              <span slot="title"> 部门管理 </span>
            </el-menu-item>
            <el-menu-item index="employee">
              <span slot="title"> 员工管理 </span>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
        <!-- 头部 -->
        <el-header>
          <!--<i class="myicon myicon-menu toggle-btn"> </i>-->
          <div class="system-title"> 考核评测系统 </div>
          <div>
            <el-popover placement="bottom" width="100" trigger="hover">
              <ul class="homePersonal-setting">
                <li @click="changePasswordFun">修改密码</li>
              </ul>
              <span class="welcome" slot="reference" v-if="userName != ''"> {{userName}}</span>
              <span class="welcome" slot="reference" v-else> {{userId}}</span>
            </el-popover>
            <el-button type="text" @click="logout"> 退出 </el-button>
          </div>
        </el-header>
        <!-- 内容区域 -->
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
     </el-container>
  </div>
</template>
<script>
export default {
  data () {
    return {
      userName: '',
      userId: '',
      currentActiveDirectory: 'tklist'
    }
  },
  methods: {
    activateOptionFun (key, keyPath) {
      this.currentActiveDirectory = key
      sessionStorage.setItem('currentActiveDirectory', key)
    },
    handleOpen (key, keyPath) {
      // console.log(key, keyPath)
    },
    handleClose (key, keyPath) {
      console.log(key, keyPath)
    },
    logout () { // 点击退出按钮
      // 清除登录状态
      localStorage.removeItem('mytoken')
      localStorage.removeItem('myid')
      sessionStorage.removeItem('currentActiveDirectory')
      // 跳转到登录页面
      this.$router.push({
        name: 'login',
        path: 'admin/login'
      })
    },
    changePasswordFun () {
      this.$router.push({
        path: 'admin/changePassword',
        name: 'changePassword',
        params: {
          id: 'row.id'
        }
      })
    }
  },
  mounted: function () {
    let _self = this
    _self.userId = localStorage.getItem('myid')
    if (sessionStorage.getItem('currentActiveDirectory')) {
      _self.currentActiveDirectory = sessionStorage.getItem('currentActiveDirectory')
    }
    global.app.$on('exitSelectModel', (data) => {
      sessionStorage.setItem('currentActiveDirectory', data)
      this.currentActiveDirectory = 'tklist'
    })
  }
}

</script>
<style lang="scss" scoped>
  .home {
    height: 100%;

    background: -webkit-linear-gradient(top left, #e2cfc1, #a2b8c6, #e2cfc1);
    /* Safari 5.1 - 6.0 */
    background: -o-linear-gradient(top left, #e2cfc1, #a2b8c6, #e2cfc1);
    /* Opera 11.1 - 12.0 */
    background: -moz-linear-gradient(top left, #e2cfc1, #a2b8c6, #e2cfc1);
    /* Firefox 3.6 - 15 */
    background: linear-gradient(to top left, #e2cfc1, #a2b8c6, #e2cfc1);
    /* 标准的语法（必须放在最后） */
    .el-menu-admin:not(.el-menu--collapse) {
      width: 200px;
      min-height: 400px;
    }
    .el-menu {
      border-right: 0;
    }
    .el-main {
      background-color: #FFFFFF;
    }
    .el-container {
      height: 100%;
    }
    .el-header {
      height: 100px !important;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .logo {
      height: 100px;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    .logo>img {
      width: 70%;
      height: auto;
    }
    .toggle-btn {
      padding: 0 10px;
      margin-left: -20px;
      font-size: 36px;
      line-height: 60px;
      color: #989898;
      cursor: pointer;
      &:hover {
        background-color: #00635a;
      }
    }
    .system-title {
      font-size: 28px;
      color: white;
    }
    .welcome {
      color: white;
    }
  }

  .homePersonal-setting {
    width: 100%;
  }

  .homePersonal-setting>li {
    border-bottom: 1px solid #aaaaaa;
    width: 100%;
    line-height: 35px;
    color: #888;
    cursor: pointer;
  }

  .homePersonal-setting>li:last-child {
    border-bottom: 0px solid #aaaaaa;
  }

</style>
