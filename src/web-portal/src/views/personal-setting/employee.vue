<template>
  <div class="department">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }"><span @click="HomeSidebarFun">首页</span></el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>员工管理</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="departmentC">
      <el-button type="primary" style="margin-left:30px" @click='addPerson'>添加员工</el-button>
      <el-button type="primary" style="margin-left:30px" @click='plAddPerson'>批量导入员工</el-button>
    </div>

    <div class="department-search">
      <div class="department-search-department">
        <span>选择部门&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <span><el-cascader :options="treeArr" v-model="selectedOptions"></el-cascader></span>
      </div>
      <div class="department-search-name">
        <span>用户名&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <span><el-input placeholder="请输入内容" v-model="departmentSearchName" clearable></el-input></span>
      </div>
      <div class="department-search-jobNumber">
        <span>用户工号&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <span><el-input placeholder="请输入内容" v-model="JobNumber" clearable></el-input></span>
      </div>
      <div class="department-search-button">
        <el-button type="primary" @click="CandidateListFun">搜索</el-button>
      </div>
      <!-- <div class="department-search-entryTime">
        <span>入职时间&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <span><el-date-picker v-model="entryTime" type="datetimerange" align="right" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '00:00:00']"></el-date-picker></span>
      </div> -->
    </div>
    <div>
      <el-table :data="employList" stripe style="width: 100%" v-if='employList.length>0'>
        <el-table-column type="index" width="50"> </el-table-column>
        <el-table-column prop="colAccountName" label="用户名" width="180"></el-table-column>
        <el-table-column prop="id" label="工号" width="180"></el-table-column>
        <el-table-column prop="colMobile" label="手机" width="180"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" icon="icon iconfont hoperun-xiugai" type="primary" plain @click="editEmploree(scope.row)"></el-button>
            <el-button size="mini" icon="icon iconfont hoperun-zhongzhimima" type="warning" plain @click="resetPasswordsFun(scope.row)"></el-button>
            <el-button size="mini" icon="icon iconfont hoperun-remove-1-copy" type="danger" plain @click="delEmploree(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination class="employee-paging" @size-change="employeePageSizeFun" @current-change="employeePageNumFun" :current-page="pageNum" :page-sizes="[5, 10, 15, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
    </div>

    <el-dialog title="修改员工" :visible.sync="editEmployeeDis">
      <el-form :model="editEmployeeForm" :rules="rulesEdit" ref="rulesEdit">
        <el-form-item label="用户名" label-width="100px" prop="colAccountName">
          <el-input v-model="editEmployeeForm.colAccountName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="100px" prop="colMobile">
          <el-input v-model="editEmployeeForm.colMobile" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditDis">取 消</el-button>
        <el-button type="primary" @click="dialogEdit">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="添加员工" :visible.sync="addEmployeeDis">
      <el-form :model="addEmployeeForm" :rules="rulesAdd" ref="rulesAdd">
        <el-form-item label="用户名" label-width="100px" prop="colAccountName">
          <el-input v-model="addEmployeeForm.colAccountName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="工号" label-width="100px" prop="id">
          <el-input v-model="addEmployeeForm.id" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="所属部门" label-width="100px">
          <el-cascader :options="treeArr" v-model="selectedOptionsAdd"></el-cascader>
        </el-form-item>
        <el-form-item label="手机号" label-width="100px" prop="colMobile">
          <el-input v-model="addEmployeeForm.colMobile" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAddDis">取 消</el-button>
        <el-button type="primary" @click="dialogAdd">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="批量导入员工" :visible.sync="plAddPersonDialog">
      <el-form :model="plAddForm">
        <el-form-item label="所属部门：">
          <el-cascader :options="treeArr" v-model="selectedOptionsBatch"></el-cascader>
        </el-form-item>
        <el-form-item label="员工模板">
          <el-button style="margin-left: 10px;" size="small" type="primary" @click="templateDown">下载模板</el-button>
        </el-form-item>
        <el-form-item label="数据文件">
          <el-upload ref="upload" action="api/account-manage/importAccount" :on-preview="handlePreview" :data="pppss" accept=".xls,.xlsx"
            :on-remove="handleRemove" :before-remove="beforeRemove" :file-list="fileList" :auto-upload="false" :on-success="ShangChuanChengGongFun">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
            <div slot="tip" class="el-upload__tip">只能上传xls/xlsx文件</div>
          </el-upload>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>

</template>
<script>
import axios from 'axios'
import commit from '../../router/commit.js'

export default {
  data () {
    return {
      pppss: {
        creatorId: localStorage.getItem('myid'),
        departmentId: ''
      },
      plAddPersonDialog: false,
      addEmployeeDis: false,
      selectedOptions: [],
      selectedOptionsAdd: [],
      selectedOptionsBatch: [],
      treeArr: [],
      plAddForm: {},
      currenName: '',
      employList: [],
      editEmployeeDis: false,
      editEmployeeForm: {
        colAccountName: '',
        colMobile: ''
      },
      addEmployeeForm: {
        colAccountName: '',
        id: '',
        colMobile: ''
      },
      rulesAdd: {
        id: [{
          required: true,
          message: '请输入用户工号',
          trigger: 'blur'
        }, {
          max: 9,
          message: '长度不能超过9个字符',
          trigger: 'blur'
        }],
        colAccountName: [{
          required: true,
          message: '请输入用户名',
          trigger: 'blur'
        }],
        colMobile: [{
          required: true,
          message: '请输入手机号码',
          trigger: 'blur'
        }, {
          pattern: /^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/i,
          message: '请输入合理的手机号码',
          trigger: 'blur'
        }]
      },
      rulesEdit: {
        colAccountName: [{
          required: true,
          message: '请输入用户名',
          trigger: 'blur'
        }],
        colMobile: [{
          required: true,
          message: '请输入手机号码',
          trigger: 'blur'
        }, {
          pattern: /^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/i,
          message: '请输入合理的手机号码',
          trigger: 'blur'
        }]
      },
      currentAccount: {},
      fileList: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      JobNumber: '',
      entryTime: '',
      departmentSearchName: ''
    }
  },
  mounted () {
    this.initList()
    this.CandidateListFun()
  },
  methods: {
    // 下载文件
    download (data) {
      if (!data) {
        return
      }
      let url = window.URL.createObjectURL(new Blob([data]))
      let link = document.createElement('a') // 创建a标签
      link.style.display = 'none'
      link.href = url
      link.setAttribute('download', '员工模板.xls') // 设置download属性
      document.body.appendChild(link) // 插入到文档中
      link.click()
    },
    // 点击调用接口
    templateDown () {
      axios({
        method: 'get',
        url: 'api/account-manage/download',
        data: {},
        responseType: 'blob' // 类似于解码
      })
        .then(res => {
          this.download(res.data) // 请求成功,拿到res后,调用download函数
          console.log(res)
          if (res.data.code === '0000') {
            this.$message({
              message: '模板下载成功',
              type: 'success'
            })
          } else if (res.data.code === '0001') {
            this.$message({
              message: '模板下载失败',
              type: 'info'
            })
          } else if (res.data.code === '0003') {
            this.$message({
              message: '模板不存在',
              type: 'info'
            })
          }
        })
    },
    initList () {
      let _this = this
      axios.get('api/account-manage/departmenttree/' + 100).then((res) => {
        if (res.status === 200) {
          let JsonStr = JSON.stringify(res.data.data.infos)
          let JsonStrOne = JsonStr.replace(/sonList/g, 'children')
          let JsonStrTow = JsonStrOne.replace(/id/g, 'value')
          let JsonStrThree = JsonStrTow.replace(/colName/g, 'label')
          let NewArr = JSON.parse(JsonStrThree)
          _this.treeArr = commit.IterationDelateMenuChildren(NewArr)
        }
      })
    },
    HomeSidebarFun () {
      global.app.$emit('exitSelectModel', 'tklist')
    },
    handlePreview () {},
    handleRemove () {},
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    ShangChuanChengGongFun (response, file, fileList) {
      let _self = this
      if (response.code === '0000') {
        _self.plAddPersonDialog = false
        _self.$message({
          message: '人员添加成功',
          type: 'success',
          duration: 1000,
          onClose: function () {
            let depId = parseInt(_self.selectedOptionsBatch[_self.selectedOptionsBatch.length - 1])
            if (depId === 'NaN') {
              _self.$message({
                message: '请选择添加人员所属部门！',
                type: 'error'
              })
              return false
            }
            _self.fileList = []
            axios.get('api/account-manage/department/' + depId).then((res) => {
              if (res.data.code === '0000') {
                _self.employList = res.data.data.accountInfos
              }
            })
          }
        })
      } else {
        _self.plAddPersonDialog = false
        _self.$message({
          showClose: true,
          message: '导入失败,请重试',
          type: 'error'
        })
      }
    },
    // 上传到服务器
    submitUpload (res) {
      this.$refs.upload.submit()
      // console.log(res)
    },
    CandidateListFun (value) {
      let _this = this
      let departmentId = parseInt(_this.selectedOptions[_this.selectedOptions.length - 1])
      if (!_this.selectedOptions[_this.selectedOptions.length - 1]) {
        departmentId = ''
      }
      let upData = {
        params: {
          pageNum: _this.pageNum,
          pageSize: _this.pageSize,
          departmentId: departmentId,
          accountName: _this.departmentSearchName,
          id: _this.JobNumber
        }
      }
      console.log(upData)
      axios.get('api/account-manage/department', upData).then((res) => {
        console.log(res.data.list)
        if (res.status === 200) {
          _this.employList = res.data.list
        }
      })
    },
    plAddPerson () {
      let _this = this
      _this.plAddPersonDialog = true
    },
    addPerson () {
      let _this = this
      _this.addEmployeeDis = true
    },
    delEmploree (row) {
      let _this = this
      _this.$confirm('此操作将永久删除该员工, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let delArr = []
        delArr.push(row.id)
        axios({
          method: 'delete',
          url: 'api/account-manage/account/',
          data: delArr
        }).then((res) => {
          if (res.data.code === '0000') {
            _this.$message({
              message: '删除员工成功',
              type: 'success',
              duration: 1000,
              onClose: function () {
                axios.get('api/account-manage/department/' + _this.selectedOptions[_this.selectedOptions.length - 1]).then((res) => {
                  if (res.data.code === '0000') {
                    _this.employList = res.data.data.accountInfos
                  }
                })
              }

            })
          }
        })
      })
    },
    resetPasswordsFun (row) {
      let _slef = this
      _slef.$confirm('是否重置该用户密码！', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(data => {
          axios.put('api/account-manage/password/' + row.id)
            .then(res => {
              if (res.data.code === '0000') {
                _slef.$message({
                  message: '重置密码成功!',
                  type: 'success'
                })
              }
            })
        })
    },
    editEmploree (row) {
      let _this = this
      _this.editEmployeeDis = true
      _this.currentAccount = row
      _this.editEmployeeForm.colAccountName = row.colAccountName
      _this.editEmployeeForm.colMobile = row.colMobile
    },
    dialogEditDis () {
      let _this = this
      _this.editEmployeeDis = false
    },
    dialogAddDis () {
      let _this = this
      _this.addEmployeeDis = false
    },
    dialogEdit () {
      let _this = this
      _this.$refs.rulesEdit.validate((valid) => {
        if (valid) {
          let beenData = {}
          beenData.colAccountName = _this.editEmployeeForm.colAccountName
          beenData.colMobile = _this.editEmployeeForm.colMobile
          beenData.id = _this.currentAccount.id
          console.log(beenData)
          axios.put('api/account-manage/account', beenData).then((res) => {
            if (res.data.code === '0000') {
              _this.$message({
                message: '修改员工成功',
                type: 'success',
                duration: 1000,
                onClose: function () {
                  _this.editEmployeeDis = false
                  axios.get('api/account-manage/department/' + _this.selectedOptions[_this.selectedOptions.length - 1]).then((res) => {
                    if (res.data.code === '0000') {
                      _this.employList = res.data.data.accountInfos
                    }
                  })
                }
              })
            } else {
              _this.$message({
                message: res.data.message,
                type: 'error'
              })
            }
          })
        }
      })
    },
    dialogAdd () {
      let _this = this
      _this.$refs.rulesAdd.validate((valid) => {
        if (valid) {
          let beenData = {}
          beenData.id = _this.addEmployeeForm.id
          beenData.colAccountName = _this.addEmployeeForm.colAccountName
          beenData.colMobile = _this.addEmployeeForm.colMobile
          beenData.colPassowrd = '123456'
          beenData.colRoleId = 2
          beenData.colDepartmentId = parseInt(_this.selectedOptionsAdd[_this.selectedOptionsAdd.length - 1])
          beenData.colLevel = '1'
          beenData.colCreateTime = new Date().getTime()
          beenData.colUpdateTime = new Date().getTime()
          beenData.colLoginTime = new Date().getTime()
          beenData.colCreatorId = localStorage.getItem('myid')
          beenData.colUpdatorId = localStorage.getItem('myid')
          if (beenData.colDepartmentId === 'NaN') {
            _this.$message({
              message: '请选择部门！',
              type: 'error'
            })
            return false
          }
          axios.post('api/account-manage/account', beenData).then(res => {
            if (res.data.code === '0000') {
              _this.$message({
                message: '添加员工成功',
                type: 'success',
                duration: 1000,
                onClose: function () {
                  _this.addEmployeeDis = false
                  _this.addEmployeeForm.id = ''
                  _this.addEmployeeForm.colAccountName = ''
                  _this.addEmployeeForm.colMobile = ''
                  axios.get('api/account-manage/department/' + _this.selectedOptions[_this.selectedOptions.length - 1]).then((res) => {
                    if (res.data.code === '0000') {
                      _this.employList = res.data.data.accountInfos
                    }
                  })
                }
              })
            } else {
              _this.addEmployeeDis = false
              _this.$message({
                message: res.data.message,
                type: 'error'
              })
            }
          })
        }
      })
    },
    employeePageNumFun (val) {
      this.pageNum = val
      this.CandidateListFun()
    },
    employeePageSizeFun (val) {
      this.pageSize = val
      this.pageNum = 1
      this.CandidateListFun()
    }
  }
}
</script>
<style lang="scss">
  .department {
    min-width: 1400px;

    .departmentC {
      padding: 30px;
    }

    .el-tree-node__content {
      height: 40px;
    }

    .btnA {
      margin-left: 10px;
      font-size: 16px
    }

    .employee-paging{
      margin-top: 20px;
      display: flex;
      justify-content: center;
    }

    .department-search{
      display:  flex;
      justify-content: flex-start;
    }

    .department-search>div{
      display:  flex;
      justify-content: flex-start;
      align-items: center;
    }

    .department-search-jobNumber,.department-search-entryTime,.department-search-button,.department-search-name{
      margin-left: 20px;
    }

  }
</style>
