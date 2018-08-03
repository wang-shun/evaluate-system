<template>
  <div class="changePwd">
    <mt-header title="修改密码">
      <router-link to="/" slot="left">
        <mt-button icon="back"></mt-button>
      </router-link>
    </mt-header>
    <!-- 修改密码 -->
    <div class="change-pwd" :model="changePasswordFrom" :rules="changePasswordRules" ref="changePasswordFrom">
      <mt-field label="" placeholder="请输入原密码" type="password" v-model="changePasswordFrom.oldPassword"></mt-field>
      <mt-field label="" placeholder="请输入新密码" type="password" v-model="changePasswordFrom.newPassword"></mt-field>
      <mt-field label="" placeholder="确认新密码" type="password" v-model="changePasswordFrom.confirmPassword"></mt-field>
      <br><br>
      <mt-button @click.native="changeSubmit('changePasswordFrom')">提交</mt-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { Toast } from 'mint-ui'
import commit from '../../router/commit.js'
export default {
  data () {
    var validatePass = (rule, value, callback) => {
      if (commit.checkStrong(value)) {
        this.cryptographicStrength = commit.checkStrong(value)
      }
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.changePasswordFrom.confirmPassword !== '') {
          this.$refs.changePasswordFrom.validateField('confirmPassword')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.changePasswordFrom.newPassword) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      changePasswordFrom: {
        newPassword: '',
        confirmPassword: '',
        oldPassword: ''
      },
      changePasswordRules: {
        newPassword: [{
          required: true,
          validator: validatePass,
          trigger: 'blur'
        }, {
          min: 8,
          max: 16,
          message: '长度在 8 到 16 个字符',
          trigger: 'blur'
        }],
        confirmPassword: [{
          required: true,
          validator: validatePass2,
          trigger: 'blur'
        }],
        oldPassword: [{
          required: true,
          message: '请输入旧密码',
          trigger: 'change'
        }]
      }
    }
  },
  methods: {
    changeSubmit (formName) {
      let id = sessionStorage.getItem('myid')
      if (this.changePasswordFrom.newPassword === this.changePasswordFrom.confirmPassword && this.changePasswordFrom.newPassword !== '' && this.oldPassword !== '') {
        // console.log('校验正确')
        let _self = this
        if (_self.changePasswordFrom.oldPassword === _self.changePasswordFrom.newPassword) {
          Toast('新密码不能与旧密码相同')
          return false
        }
        if (_self.changePasswordFrom.newPassword.length < 8) {
          Toast('密码长度至少8位')
          return false
        }
        // 发请求
        axios.put('/api//account-manage/password', {
          'id': id,
          'roleid': 2,
          'oldpassword': this.changePasswordFrom.oldPassword,
          'newpassword': this.changePasswordFrom.newPassword
        }).then(res => {
          if (res.data.code === '0000') {
            this.$router.push({
              path: '/login',
              name: 'login'
            })
            Toast('修改成功,请用新密码登录')
          } else {
            Toast('登录名或密码错误！')
          }
        })
      } else {
        Toast('输入错误,请重试')
      }
    }
  }
}

</script>

<style>
  .changePwd .mint-cell-wrapper {
    border-bottom: 1px solid #ccc;
  }
  .change-pwd {
    padding: 50px;
  }
  .changePwd .mint-header .mint-button {
    width: 14%
  }
  .mint-button {
    width: 100%;
    height: 35px;
    background-color: #26A2FF;
    color: #F0F0F0;
  }
</style>
