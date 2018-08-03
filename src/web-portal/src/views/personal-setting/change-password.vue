<template>
  <div class="changePassword">
    <el-form :model="changePasswordFrom" status-icon :rules="changePasswordRules" ref="changePasswordFrom" label-width="100px"
      class="demo-ruleForm">
      <el-row>
        <el-col :span="12">
          <el-form-item label="旧密码" prop="oldPass word">
            <el-input type="password" v-model.number="changePasswordFrom.oldPassword"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input type="password" v-model="changePasswordFrom.newPassword" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认新密码" prop="confirmPassword">
            <el-input type="password" v-model="changePasswordFrom.confirmPassword" auto-complete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item>&nbsp;</el-form-item>
          <el-form-item class="changePassword-cryStr" label="密码强度">
            <el-slider v-model="cryptographicStrength" :step="25" show-stops></el-slider>
          </el-form-item>
          <el-form-item>&nbsp;</el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="submitFormFun('changePasswordFrom')">确认修改</el-button>
        <el-button @click="resetForm('changePasswordFrom')">重置</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import axios from 'axios'
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
      userId: '',
      changePasswordFrom: {
        newPassword: '',
        confirmPassword: '',
        oldPassword: ''
      },
      cryptographicStrength: 0,
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
    submitFormFun (formName) {
      let _self = this
      if (_self.changePasswordFrom.oldPassword === _self.changePasswordFrom.newPassword) {
        _self.$message('新密码不能与旧密码相同')
        return false
      }
      if (_self.cryptographicStrength < 49) {
        _self.$message('您的的新密码存在安全风险！请重新输入')
        return false
      }
      axios.put('/api//account-manage/password', {
        'id': _self.userId,
        'roleid': 1,
        'oldpassword': _self.changePasswordFrom.oldPassword,
        'newpassword': _self.changePasswordFrom.newPassword
      }).then(res => {
        if (res.data) {
          if (res.data.code === '0000') {
            localStorage.removeItem('myid')
            _self.$message('修改成功,请用新密码登录')
            _self.$router.push({
              path: 'admin/login',
              name: 'login'
            })
          } else {
            _self.$message('请检查原始密码是否正确！')
          }
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  },
  mounted () {
    let _self = this
    _self.userId = localStorage.getItem('myid')
  }
}

</script>

<style>
  .changePassword .el-slider__button {
    width: 8px;
    height: 8px;
  }

</style>
