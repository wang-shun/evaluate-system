<template>
  <div class="login">
    <el-form ref="form" :model="form" class="container" :rules="rules">
        <el-form-item>
            <div class="avatar">
                <img src="../assets/avatar.jpg" width="120" height="120" alt="">
            </div>
        </el-form-item>
        <el-form-item prop="username">
            <el-input v-model="form.username"  placeholder="账户" type='text'></el-input>
        </el-form-item>
        <el-form-item prop="password">
            <el-input v-model="form.password"  placeholder="密码" type="password" @keydown.enter.native="loginSubmit('form')"></el-input>
        </el-form-item>
        <el-button type="primary" class="login-btn" @click="loginSubmit('form')">登录</el-button>
    </el-form>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  data() {
      return {
        form: {
          username: '',
          password:'',
        },
        rules: {
            username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
            password: [{ required: true, message: "请输入密码", trigger: "blur" }]
        }
      }
  },
  methods:{
      loginSubmit(formName){
      	let _self = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            axios.post('api/account-manage/accessvalidat',{
						"roleType" : "1",
						"id" : _self.form.username,
						"pwd" : _self.form.password
					})
			    .then(res=> {
			        console.log(1)
                    console.log(res);
                    if(res.data.code=="0000"){
                        console.log('登录成功');
                        localStorage.setItem('mytoken',_self.form.username)
                        localStorage.setItem('myid',_self.form.username)
                        this.$message({
                            message: '登录成功',
                            type: 'success'
                        });
                        this.$router.push({name:"home"})
                    } else {
                        // this.$message.error('用户名或密码错误')
                    }
			    })
			    .catch(error => {
                    this.$message({
                        showClose: true,
                        message: '登录失败,请稍后重试!',
                        type: 'warning'
                    })
                })
            
          } else {
            console.log('校验不通过!!')
            return false
          }
        })
      }
  }
}
</script>
<style lang="scss" scoped>
.login {
  position: fixed;
  width: 100%;
  height: 100%;
  background-color: #727880;
    .container {
        position: absolute;
        left: 0;
        right: 0;
        width: 400px;
        height: 266px;
        padding: 0px 40px 15px 40px;
        margin: 200px auto;
        background: white;
        .avatar{
            position: relative;
            left: 50%;
            width: 120px;
            height: 120px;
            margin-left: -60px;
            margin-top: -60px;
            box-sizing: border-box;
            border-radius: 50%;
            border: 10px solid #fff;
            box-shadow: 0 1px 5px #ccc;
            overflow: hidden;
        }
        .login-btn {
            width: 100%;
        }
    }
}
</style>
