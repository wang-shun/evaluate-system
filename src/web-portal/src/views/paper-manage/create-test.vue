<template>
  <div class="create-test">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">
        <span @click="HomeSidebarFun">首页</span>
      </el-breadcrumb-item>
      <el-breadcrumb-item>试卷管理
      </el-breadcrumb-item>
      <el-breadcrumb-item>发布考试</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row>
      <el-col :span="20">
        <div class="grid-content bg-purple-dark">
          <el-form :model="CreateTestFormData" :rules="CreateTestRules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="试卷名称" prop="name">
              <el-input v-model="CreateTestFormData.name"> </el-input>
            </el-form-item>
            <el-form-item label="选择题库" prop="region">
              <el-select v-model="CreateTestFormData.region" placeholder="选择题库">
                <el-option v-for="Item in tikuList" :label="Item.subjectDbName" :value="Item.id" :key="Item.id"> </el-option>
              </el-select>
            </el-form-item>

            <!-- 日期选择器开始 -->
            <el-form-item label="考试时间" required>
              <el-col :span="11">
                <el-form-item prop="StartTime">
                  <el-date-picker type="date" format="yyyy-MM-dd" placeholder="选择开始日期" v-model="CreateTestFormData.StartTime" style="width: 100%;" :picker-options="pickerOptions0"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col class="line" :span="1">&mdash;</el-col>
              <el-col :span="11">
                <el-form-item prop="EndTime">
                  <el-date-picker type="date" format="yyyy-MM-dd" placeholder="选择结束时间" v-model="CreateTestFormData.EndTime" style="width: 100%;" :picker-options="pickerOptions1"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-form-item>
            <!-- 日期选择器结束 -->

            <el-row>
              <el-col :span="6">
                <el-form-item label="考试时长" prop="TestTime">
                  <el-input v-model.number="CreateTestFormData.TestTime"> </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="2" class="create-test-TestTime">
                &nbsp;&nbsp;&nbsp;&nbsp;分钟
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="考试次数" prop="TestTime">
                  <el-input v-model.number="CreateTestFormData.examIndex"> </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="2" class="create-test-TestTime">
                &nbsp;&nbsp;&nbsp;&nbsp;
              </el-col>
            </el-row>
            <el-form-item label="试卷状态" prop="delivery">
              <el-switch v-model="CreateTestFormData.delivery"></el-switch>
            </el-form-item>
            <el-form-item label="考题模板">
              <el-row>
                <span v-for="(item,index) in paperTemplate" :key="index">
                  <el-col :span="2" class="createTest-colTemplateName">
                    <el-button type="primary" @click="callQuestionTemplateFun(index)">{{item.colTemplateName}}</el-button>
                  </el-col>
                  <el-col :span="1">
                    &nbsp;
                  </el-col>
                </span>
              </el-row>
            </el-form-item>
            <div>
              <el-row v-for="(Item,index) in CreateTestFormData.domains" :key="index">
                <el-col :span="5">
                  <el-form-item label="试题题型">
                    <el-select v-model.number="Item.colSubjectType" style="width: 100%;" @change="duplicateCheckingFun(Item,index)">
                      <el-option label="单选" value="单选"> </el-option>
                      <el-option label="多选" value="多选"> </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="5" class="create-test-TestTime">
                  <el-form-item label="试题难度">
                    <el-select v-model.number="Item.colDifficuteLevel" style="width: 100%;" @change="duplicateCheckingFun(Item,index)">
                      <el-option label="简单" value="简单"> </el-option>
                      <el-option label="一般" value="一般"> </el-option>
                      <el-option label="较难" value="较难"> </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="5" class="create-test-TestTime">
                  <el-form-item label="试题数量">
                    <el-input v-model.number="Item.colSubjectCount" @input="TotalPointsCount"> </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="5" class="create-test-TestTime">
                  <el-form-item label="每题分数">
                    <el-input v-model.number="Item.colSubjectPoint" @input="TotalPointsCount"> </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="2" class="create-test-TestTime" v-if="index == 0">
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <el-button type="primary" @click="AddTopicFun">添加</el-button>
                </el-col>
                <el-col :span="2" class="create-test-TestTime" v-else>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <el-button type="danger" icon="el-icon-delete" circle @click="DeleteTopicFun(index)"></el-button>
                </el-col>
              </el-row>
            </div>
            <el-row>
              <el-col :span="6">
                <el-form-item label="总分数">
                  <el-input v-model.number="CreateTestFormData.TotalPoints" :disabled="true"> </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="通过分数" prop="ThroughPoints">
                  <el-input v-model.number="CreateTestFormData.ThroughPoints"> </el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="试卷说明" prop="desc">
              <el-input type="textarea" v-model="CreateTestFormData.desc"></el-input>
            </el-form-item>
            <!-- <el-form-item label="选择部门" prop="desc">
              <el-cascader :options="Departmentlist" v-model="selectedOptions" change-on-select @change="CandidateListFun"></el-cascader>

            </el-form-item> -->
            <el-form-item label="考试人员" prop="desc">
              <el-row>
                <el-col :span="20">
                  <el-row>
                    <el-col v-for="(item,index) in CurrentandidatesList" :span="3" class="KaoShiRenYuan" :key="item.id">
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
              <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
              <!-- <el-button @click="resetForm('ruleForm')">重置</el-button> -->
            </el-form-item>
          </el-form>
        </div>
      </el-col>
    </el-row>

    <el-dialog title="添加考生" :visible.sync="CandidateListShow" width='1000px'>
      <el-form :inline="true">
        <el-form-item label="选择部门">
          <el-cascader :options="Departmentlist" v-model="selectedOptions" :show-all-levels="false" @change="CandidateListFun" clearable></el-cascader>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="searchName" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="用户工号">
          <el-input v-model="searchID" placeholder="请输入用户工号"></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-button type="primary" @click='searenQuery'>查询</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="searchData" border stripe style="width: 100%" v-loading="searchLoading"  @selection-change="handleSelectionChange">
         <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="colAccountName" label="用户名" width="150"></el-table-column>
        <el-table-column prop="id" label="用户工号" width="200"></el-table-column>
        <el-table-column prop="colDepartmentId" label="所属部门"></el-table-column>
        <el-table-column prop="colMobile" label="手机号"></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click='closeDialog'>关闭</el-button>
      </div>

      <div class="pages">
        <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[10, 20, 30, 40]"
      :page-size=currentPageSize
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
      </div>

      <!-- <el-table :data="gridData">
    <el-table-column property="date" label="日期" width="150"></el-table-column>
    <el-table-column property="name" label="姓名" width="200"></el-table-column>
    <el-table-column property="address" label="地址"></el-table-column>
  </el-table> -->
    </el-dialog>
    <!-- <div class="renyuanzhanshi" v-show="CandidateListShow"> -->
    <!-- <div class="renyuanzhanshi-content">
        <div class="renyuanzhanshi-hide">
          <el-checkbox :indeterminate="isIndeterminate" v-model="ExamineeCheckAll" @change="handleCheckAllChange">全选</el-checkbox>
          <div style="margin: 15px 0;"></div>
          <el-row>
            <el-checkbox-group v-model="CurrentandidatesList" @change="handleCheckedCitiesChange">
              <el-col :span="6" v-for="item in CandidateList" :key="item.id" class="createTest-candidateList">
                <el-checkbox :label="item">{{item.colAccountName}}({{item.id}})</el-checkbox>
              </el-col>
            </el-checkbox-group>
          </el-row>
        </div>
      </div> -->
    <!-- <el-button type="primary" @click="CandidateListHideFun">关闭</el-button> -->
    <!-- <el-button type="info" @click="CandidateListHideFun">取消</el-button> -->
    <!-- </div> -->
  </div>
</template>

<script>
import axios from 'axios'
import commit from '../../router/commit.js'

export default {
  inject: ['reload'],
  data () {
    return {
      // 选择今天以及今天以后的日期
      pickerOptions0: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      // 设置结束日期开始日期
      pickerOptions1: {
        disabledDate: (time) => {
          return time.getTime() < this.CreateTestFormData.StartTime
        }
      },
      searchCache: false,
      searchName: '',
      searchID: '',
      searchData: [],
      searchLoading: false,
      total: 0,
      currentPage: 1,
      currentPageSize: 10,
      CreateTestFormData: {
        name: '', // 试卷名称
        region: '', // 题库ID
        TestTime: 60, // 考试时长
        StartTime: '', // 开始时间
        EndTime: '', // 结束时间
        delivery: true, // 试卷是否开放
        desc: '', // 试卷说明
        ThroughPoints: '', // 通过分数
        domains: [{
          colSubjectType: '单选',
          colDifficultLevel: '一般',
          colSubjectCount: 30,
          colSubjectPoint: 1
        }], // 题型规则列表
        TotalPoints: 30, // 总分
        examIndex: 3
      },
      CreateTestRules: {
        name: [{
          required: true,
          message: '试卷名称',
          trigger: 'blur'
        }, {
          min: 3,
          max: 20,
          message: '长度在 3 到 20 个字符',
          trigger: 'blur'
        }],
        region: [{
          required: true,
          message: '请选择题库',
          trigger: 'change'
        }],
        StartTime: [{
          type: 'date',
          required: true,
          message: '请选择日期',
          trigger: 'change'
        }],
        EndTime: [{
          type: 'date',
          required: true,
          message: '请选择时间',
          trigger: 'change'
        }],
        TestTime: [{
          required: true,
          message: '考试时长不能为空'
        }, {
          type: 'number',
          message: '考试时长必须为数字值'
        }],
        type: [{
          type: 'array',
          required: true,
          message: '请至少选择一个活动性质',
          trigger: 'change'
        }],
        desc: [{
          required: false,
          message: '请填写活动形式',
          trigger: 'blur'
        }],
        ThroughPoints: [{
          required: true,
          message: '通过分数'
        }, {
          type: 'number',
          message: '通过分数必须为数字型'
        }]
      },
      tikuList: [], // 题库列表
      Departmentlist: [], // 部门列表
      selectedOptions: [], // 选择的部门id
      CandidateListShow: false, // 考生列表页面展示
      ExamineeCheckAll: false, // 考生全选
      CurrentandidatesList: [], // 选中考生列表
      CandidateList: [], // 考生列表
      isIndeterminate: true, // 不确定状态
      paperTemplate: [] // 试题模板
    }
  },
  mounted: function () {
    this.TiKuListFun()
    this.BuMenListFun()
    this.examPaperTemplateFun()
  },
  methods: {
    HomeSidebarFun () {
      global.app.$emit('exitSelectModel', 'title')
    },
    TiKuListFun () { // 获取题库列表
      let upData = {
        params: {
          pageNum: 1,
          pageSize: 1000000000
        }
      }
      axios.get('api/subject-manage/queryAllsubjectDb', upData)
        .then(res => {
          if (res.status === 200) {
            if (res.data) {
              this.tikuList = res.data.list
            }
          }
        })
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
    examPaperTemplateFun () { // 获取试题模板
      let _self = this
      axios.get('api/exam-manage/examTemplate')
        .then(res => {
          if (res.status === 200) {
            if (res.data && res.data.list) {
              for (let i = 0; i < res.data.list.length; i++) {
                if (res.data.list[i].details.length > 0) {
                  _self.paperTemplate.push(res.data.list[i])
                }
              }
            }
            _self.callQuestionTemplateFun(0)
          }
        })
    },
    callQuestionTemplateFun (val) {
      let _self = this
      console.log(_self.paperTemplate[val].details)
      let arr = _self.paperTemplate[val].details
      let arrOne = []
      let zongFen = 0
      for (let index = 0; index < arr.length; index++) {
        let NewJson = {
          colSubjectType: '单选',
          colDifficuteLevel: '一般',
          colSubjectCount: arr[index].colSubjectCount,
          colSubjectPoint: arr[index].colSubjectPoint
        }
        switch (arr[index].colSubjectType) {
          case 1:
            NewJson.colSubjectType = '多选'
            break
          default:
            NewJson.colSubjectType = '单选'
            break
        };
        switch (arr[index].colDifficuteLevel) {
          case 1:
            NewJson.colDifficuteLevel = '简单'
            break
          case 3:
            NewJson.colDifficuteLevel = '较难'
            break
          default:
            NewJson.colDifficuteLevel = '一般'
            break
        }
        zongFen = zongFen + arr[index].colSubjectCount * arr[index].colSubjectPoint
        arrOne.push(NewJson)
      }
      _self.CreateTestFormData.TotalPoints = zongFen
      _self.CreateTestFormData.domains = arrOne
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
    examineeListFun (event, index) {
      let _self = this
      let NewArr = _self.CurrentandidatesList.slice(0, _self.CandidateList.length)
      NewArr.splice(index, 1)
      _self.ExamineeCheckAll = false
      _self.CurrentandidatesList = NewArr
    },
    closeDialog () {
      let _this = this
      _this.CandidateListShow = false
    },
    CandidateListShowFun () {
      let _this = this
      let param = {}
      _this.currentPage = 1
      param.pageNum = _this.currentPage
      param.pageSize = _this.currentPageSize
      param.departmentId = _this.selectedOptions[_this.selectedOptions.length - 1]
      param.accountName = _this.searchName
      param.id = _this.searchID
      this.CandidateListShow = true
      if (!_this.searchCache) {
        _this.searchLoading = true
        _this.searchInit()
      } else {
        _this.searchLoading = true
        _this.searchInit(param)
      }
    },
    searchInit (param) {
      let _this = this
      if (param) {
        return axios.get('api/account-manage/department', {params: param}).then(res => {
          _this.searchLoading = false
          if (res.status === 200) {
            _this.searchCache = true
            _this.searchData = res.data.list
            _this.total = res.data.total
          } else {
            _this.$message({
              message: res.statusText,
              type: 'error'
            })
          }
        })
      }
      return axios.get('api/account-manage/department').then(res => {
        _this.searchLoading = false
        if (res.status === 200) {
          _this.searchData = res.data.list
          _this.total = res.data.total
        }
      })
    },
    CandidateListFun (value) { // 获取部门下的考生列表
      let _this = this
      let param = {}
      _this.currentPage = 1
      param.pageNum = _this.currentPage
      param.pageSize = _this.currentPageSize
      param.departmentId = value[value.length - 1]
      param.accountName = _this.searchName
      param.id = _this.searchID
      _this.searchLoading = true

      if (value.length > 0) {
        _this.searchInit(param)
      } else {
        param.departmentId = ''
        _this.searchInit(param)
      }
    },
    searenQuery () {
      let _this = this
      let param = {}
      _this.currentPage = 1
      param.pageNum = _this.currentPage
      param.pageSize = _this.currentPageSize
      param.departmentId = _this.selectedOptions[_this.selectedOptions.length - 1]
      param.accountName = _this.searchName
      param.id = _this.searchID
      _this.searchLoading = true
      _this.searchInit(param)
    },
    handleCurrentChange (val) {
      let _this = this
      let param = {}
      _this.currentPage = val
      param.pageNum = _this.currentPage
      param.pageSize = _this.currentPageSize
      param.departmentId = _this.selectedOptions[_this.selectedOptions.length - 1]
      param.accountName = _this.searchName
      param.id = _this.searchID
      _this.searchLoading = true
      _this.searchInit(param)
    },
    handleSizeChange (val) {
      let _this = this
      let param = {}
      _this.currentPageSize = val
      _this.currentPage = 1
      param.pageNum = _this.currentPage
      param.pageSize = _this.currentPageSize
      param.departmentId = _this.selectedOptions[_this.selectedOptions.length - 1]
      param.accountName = _this.searchName
      param.id = _this.searchID
      _this.searchLoading = true
      _this.searchInit(param)
    },
    handleSelectionChange (val) {

    },
    CandidateListHideFun () {
      if (this.CandidateListShow) {
        this.CandidateListShow = false
      } else {
        this.CandidateListShow = true
      }
    },
    TotalPointsCount () {
      let _self = this
      let TotalPoints = 0
      for (let i = 0; i < _self.CreateTestFormData.domains.length; i++) {
        TotalPoints = TotalPoints + _self.CreateTestFormData.domains[i].colSubjectCount * _self.CreateTestFormData.domains[i].colSubjectPoint
      };
      _self.CreateTestFormData.TotalPoints = TotalPoints
    },
    submitForm (formName) {
      let _self = this
      let UserName = localStorage.getItem('myid')
      let colPublishVal = 1
      if (!_self.CreateTestFormData.delivery) {
        colPublishVal = 0
      };
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
      let Updomains = _self.CreateTestFormData.domains
      let UpdomainsData = []
      for (let index = 0; index < Updomains.length; index++) {
        let newJson = {
          colSubjectType: Updomains[index].colSubjectType,
          colDifficultLevel: Updomains[index].colDifficuteLevel,
          colSubjectCount: Updomains[index].colSubjectCount,
          colSubjectPoint: Updomains[index].colSubjectPoint
        }
        switch (newJson.colSubjectType) {
          case '多选':
            newJson.colSubjectType = 1
            break
          default:
            newJson.colSubjectType = 0
            break
        };
        switch (newJson.colDifficultLevel) {
          case '简单':
            newJson.colDifficultLevel = 1
            break
          case '较难':
            newJson.colDifficultLevel = 3
            break
          default:
            newJson.colDifficultLevel = 2
            break
        }
        UpdomainsData.push(newJson)
      }
      let UpData = {
        'exam': {
          'colExamName': _self.CreateTestFormData.name.trim(), //
          'colExamDesc': _self.CreateTestFormData.desc,
          'colSubjectDbId': _self.CreateTestFormData.region,
          'colOpenTime': _self.CreateTestFormData.StartTime.getTime(),
          'colCloseTime': _self.CreateTestFormData.EndTime.getTime(),
          'colTotalScore': _self.CreateTestFormData.TotalPoints, // 总分
          'colPassScore': _self.CreateTestFormData.ThroughPoints,
          'colDuration': _self.CreateTestFormData.TestTime,
          'colLimitCount': _self.CreateTestFormData.examIndex, // 考试次数
          'colPublish': colPublishVal, // 是否发布
          'colCreatorId': UserName, // 发布者id
          'colUpdateId': UserName // 更新者ID
        },
        'examStruct': UpdomainsData,
        'examAccountList': {
          'ids': examineeId,
          'creatId': UserName,
          'limitCount': _self.CreateTestFormData.examIndex
        }
      }
      if (UpData.colTotalScore === '') {
        _self.$message({
          message: '您未设置总分数',
          type: 'warning'
        })
        return false
      }
      let kaiShiTime = _self.CreateTestFormData.StartTime.getTime()
      let jieShuTime = _self.CreateTestFormData.EndTime.getTime()
      if (jieShuTime - kaiShiTime < 86400000) {
        _self.$message({
          message: '测试时间段不能小于1天',
          type: 'warning'
        })
        return false
      }
      console.log(UpData)
      axios.post('api/exam-manage/examination', UpData)
        .then(res => {
          console.log(res)
          if (res.data.code === '0000') {
            this.reload() // 刷新当前页面
            this.$message({
              message: '考试发布成功',
              type: 'success'
            })
          } else {
            this.$message.error('考试发布失败')
          }
        })
    },
    AddTopicFun () {
      if (this.CreateTestFormData.domains.length < 6) {
        let NewJson = {
          colSubjectType: '',
          colDifficultLevel: '',
          colSubjectCount: '',
          colSubjectPoint: ''
        }
        this.CreateTestFormData.domains.push(NewJson)
      } else {
        this.$message({
          message: '已达到试题类型添加上限！',
          type: 'warning'
        })
      }
    },
    DeleteTopicFun (index) {
      this.CreateTestFormData.domains.splice(index, 1)
      this.TotalPointsCount()
    },
    duplicateCheckingFun (value, index) {
      let _self = this
      let copyDomains = _self.CreateTestFormData.domains
      for (let i = 0; i < copyDomains.length; i++) {
        if (copyDomains[i].colSubjectType === value.colSubjectType) {
          if (i !== index) {
            if (copyDomains[i].colDifficuteLevel === value.colDifficuteLevel) {
              value = {
                colSubjectType: '',
                colDifficuteLevel: '',
                colSubjectCount: '',
                colSubjectPoint: ''
              }
              _self.$set(_self.CreateTestFormData.domains, index, value)
              _self.$message({
                message: '您选择的题型重复请重新选择！',
                type: 'warning'
              })
            }
          }
        }
      }
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

  .createTest-candidateList {
    text-align: left
  }

  .pages {
    padding: 10px 0
  }

  .createTest-colTemplateName{
    margin-top: 10px;
  }
</style>
