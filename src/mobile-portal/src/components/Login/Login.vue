<template>
  <div class="Login">
    <div class="Login-content">
      <mt-field label="" placeholder="请输入用户名" v-model="username"></mt-field>
      <mt-field label="" placeholder="请输入密码" type="password" v-model="password"></mt-field>
      <mt-button @click.native="handleClick">登录</mt-button>
      <br>
      <br>
      <div class="about-pwd">
        <span @click="forgetPwd" class='fr'>忘记密码</span>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import {
  Toast,
  Button,
  Field
} from 'mint-ui'
export default {
  name: 'login',
  data () {
    return {
      username: '',
      password: ''
    }
  },
  mounted () {

  },
  methods: {
    handleClick: function () {
      this.submitForm()
    },
    submitForm: function () {
      let _self = this
      let postData = {
        'roleType': '2',
        'id': _self.username,
        'pwd': _self.password
      }
      axios.post('/api/account-manage/accessvalidat', postData)
        .then(response => {
          if (response.data.code === '0000') {
            sessionStorage.setItem('myid', _self.username)
            _self.$router.push({
              path: '/',
              name: 'index',
              params: {
                id: _self.username
              }
            })
          } else if (response.data.code === '1111') {
            sessionStorage.setItem('myid', _self.username)
            _self.$router.push({
              path: '/stuChangePwd',
              name: 'stuChangePwd'
            })
            Toast('首次登陆,请修改密码')
          } else {
            Toast('登录名或密码错误！')
          }
        })
        .catch(function () {
          Toast('登陆失败。请稍后再试！')
        })
    },
    // 忘记密码
    forgetPwd () {
      Toast('请联系管理员')
    }
  },
  components: {
    Toast,
    Button,
    Field
  }
}
</script>

<style type="text/css">
  .Login {
    position: absolute;
    width: 100%;
    height: 100%;
    background-image: url(../../Image/Login/LoginBj.jpg);
    background-size: 100% 100%;
    display: flex;
    justify-content: center;
  }

  .Login .Login-content {
    margin-top: 50%;
    width: 60%;
    height: 50px;
  }

  .Login .mint-cell {
    background-color: rgba(255, 255, 255, 0);
  }

  .Login .mint-cell-value>input {
    width: 100%;
    background-color: rgba(255, 255, 255, 0);
    border-bottom: 1px solid #EEEEEE;
    color: #757575;
  }

  .Login .mint-button {
    margin-top: 20px;
    width: 100%;
    height: 35px;
    background-color: #26A2FF;
    color: #F0F0F0;
  }
</style>
