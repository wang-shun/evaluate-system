<template>
  <div class="create-test">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }"><span  @click="HomeSidebarFun">首页</span> </el-breadcrumb-item>
      <el-breadcrumb-item>试卷管理</el-breadcrumb-item>
      <el-breadcrumb-item>添加人员</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row>
      <el-col :span="20">
        <div class="grid-content bg-purple-dark">
          <el-form :model="CreateTestFormData" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="试卷名称" prop="name">
              <el-input v-model="CreateTestFormData.colExamName" disabled> </el-input>
            </el-form-item>
            <el-form-item label="选择部门" prop="desc">
              <el-cascader :options="Departmentlist" v-model="selectedOptions" change-on-select @change="CandidateListFun"></el-cascader>

            </el-form-item>
            <el-form-item label="考试人员" prop="desc">
              <el-row>
                <el-col :span="20">
                  <el-row>
                    <el-col v-for="(item,index) in CurrentandidatesList" :span="3" class="KaoShiRenYuan" :key="item.colAccountName">
                      <el-alert title="" type="info" @close="examineeListFun(item,index)">{{item.colAccountName}}</el-alert>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col :span="4">
                  <el-button type="primary" @click="CandidateListShowFun">添加考生</el-button>
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">确认添加</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
    </el-row>
    <div class="renyuanzhanshi" v-show="CandidateListShow">
      <div class="renyuanzhanshi-content">
        <div class="renyuanzhanshi-hide">
          <el-checkbox :indeterminate="isIndeterminate" v-model="ExamineeCheckAll" @change="handleCheckAllChange">全选</el-checkbox>
          <div style="margin: 15px 0;"></div>
          <el-checkbox-group v-model="CurrentandidatesList" @change="handleCheckedCitiesChange">
            <el-checkbox v-for="item in CandidateList" :label="item" :key="item.id">{{item.colAccountName}}({{item.id}})</el-checkbox>
          </el-checkbox-group>
        </div>
      </div>
      <el-button type="primary" @click="CandidateListHideFun">关闭</el-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import commit from '../../router/commit.js'

export default {
  inject: ['reload'],
  data () {
    return {
      CreateTestFormData: {
        colExamName: '',
        id: ''
      },
      Departmentlist: [], // 部门列表
      selectedOptions: [], // 选择的部门id
      CandidateListShow: false, // 考生列表页面展示
      ExamineeCheckAll: false, // 考生全选
      CurrentandidatesList: [], // 选中考生列表
      CandidateList: [], // 考生列表
      isIndeterminate: true
    }
  },
  mounted: function () {
    this.BuMenListFun()
    this.CreateTestFormData.colExamName = this.$route.query.colExamName
    this.CreateTestFormData.id = this.$route.query.id
  },
  methods: {
    HomeSidebarFun () {
      global.app.$emit('exitSelectModel', 'title')
    },
    BuMenListFun () { // 获取部门信息
      let _self = this
      axios.get('api/account-manage/departmenttree/' + 100)
        .then(res => {
          if (res.status === 200) {
            if (res.data) {
              let JsonStr = JSON.stringify(res.data.data.infos)
              let JsonStrOne = JsonStr.replace(/sonList/g, 'children')
              let JsonStrTow = JsonStrOne.replace(/id/g, 'value')
              let JsonStrThree = JsonStrTow.replace(/colName/g, 'label')
              let NewArr = JSON.parse(JsonStrThree)
              _self.Departmentlist = commit.IterationDelateMenuChildren(NewArr)
            }
          }
        })
    },
    CandidateListFun (value) { // 获取部门id
      let _self = this
      let index = value.length - 1
      axios.get('api/account-manage/department/' + value[index])
        .then(res => {
          if (res.status === 200) {
            if (res.data) {
              _self.CandidateList = res.data.data.accountInfos
            }
          }
        })
    },
    handleCheckAllChange (val) { // 考生全选操作
      this.CurrentandidatesList = val ? this.CandidateList : []
      this.isIndeterminate = false
    },
    handleCheckedCitiesChange (value) { // 单个考生选择操作
      let checkedCount = value.length
      this.ExamineeCheckAll = checkedCount === this.CandidateList.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.CandidateList.length
    },
    CandidateListShowFun () {
      this.CandidateListShow = true
    },
    CandidateListHideFun () {
      if (this.CandidateListShow) {
        this.CandidateListShow = false
      } else {
        this.CandidateListShow = true
      }
    },
    examineeListFun (event, index) {
      let _self = this
      let NewArr = _self.CurrentandidatesList.slice(0, _self.CandidateList.length)
      NewArr.splice(index, 1)
      _self.ExamineeCheckAll = false
      _self.CurrentandidatesList = NewArr
    },
    submitForm (formName) {
      for (var i = 0; i < this.Departmentlist.length; i++) {
      }
      let _self = this
      let id = this.CreateTestFormData.id
      let creatorid = localStorage.getItem('myid')
      let examineeId = []
      if (_self.CurrentandidatesList.length > 0) {
        for (let i = 0; i < _self.CurrentandidatesList.length; i++) {
          examineeId.push(_self.CurrentandidatesList[i].id)
        };
      } else {
        this.$message({
          message: '至少添加一名考生！',
          type: 'warning'
        })
        return false
      }
      let accountIds = []
      for (var j = 0; j < _self.CurrentandidatesList.length; j++) {
        accountIds.push(_self.CurrentandidatesList[j].id)
      }
      console.log(accountIds)
      // 发请求
      axios.post('api/exam-manage/examaccountadd/' + id + '/' + creatorid, accountIds)
        .then(res => {
          if (res.status === 200) {
            this.$message({
              message: '人员添加成功',
              type: 'success'
            })
          } else {
            this.$message.error('人员添加失败')
          }
        })
    }
  }
}

</script>

<style lang="scss" scoped>
  .create-test {
    min-width: 1400px;
  }

  .create-test-header {
    height: 40px;
    display: flex;
    align-items: center;
    font-size: 18px;
  }

  .create-test-TestTime {
    line-height: 35px;
  }

  .KaoShiRenYuan {
    box-sizing: border-box;
    -moz-box-sizing: border-box;
    /* Firefox */
    -webkit-box-sizing: border-box;
    /* Safari */
    padding: 5px;
  }

  .renyuanzhanshi {
    position: fixed;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    text-align: center;
  }

  .renyuanzhanshi-content {
    margin: 100px auto 30px;
    width: 60%;
    height: 60%;
    background-color: #FFFFFF;
    border-radius: 10px;
    padding: 10px;
    overflow-y: scroll;
  }

  .renyuanzhanshi-hide {
    width: 100%;
    height: auto;
  }

  .line {
    text-align: center;
  }

</style>
