<template>
  <div class='reset-pwd'>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }"><span @click="HomeSidebarFun">首页</span></el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>重置密码</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 重置密码 -->
    <el-button type="info" plain @click="confirmReset">确认重置</el-button>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  data () {
    return {

    }
  },
  methods: {
    HomeSidebarFun () {
      global.app.$emit('exitSelectModel', 'tklist')
    },
    confirmReset () {
      let id = this.$route.params.id // 员工管理页面传递id
      axios.put('api/account-manage/password' + id, {})
        .then(res => {
          if (res.status === 200) {
            this.$message({
              message: '密码重置成功',
              type: 'success'
            })
          } else {
            this.$message.error('密码重置失败')
          }
        })
    }
  }
}
</script>
<style lang="scss" scoped>

</style>
