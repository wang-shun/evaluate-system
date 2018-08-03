<template>
  <div class="questionbank-list">
    <el-row>
      <el-col :span='24'>
        <!-- 面包屑导航 -->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }"><span @click="HomeSidebarFun">首页</span></el-breadcrumb-item>
          <el-breadcrumb-item>题库管理</el-breadcrumb-item>
          <el-breadcrumb-item>试题列表</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <br>
    <el-button type="success" @click="addOperationQuestionFun">添加试题</el-button>
    <el-button type="success" @click="addQuestion()">批量导入试题</el-button>
    <!-- 关键词搜索 -->
    <div class="main">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="关键词">
          <el-input v-model="formInline.keyword" placeholder="请输入关键词" @keydown.enter.native="questionbankListSearchFun"></el-input>
        </el-form-item>
        <el-form-item label="请选择题库">
          <el-select v-model="pppss.subjectDbId" placeholder="请选择题库名称">
            <el-option v-for="(tiku,index) in tikuList" :key="index" :label="tiku.subjectDbName" :value="tiku.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="试题类型">
          <el-select v-model="formInline.questionType" placeholder="试题类型">
            <el-option label="单选" value="单选"></el-option>
            <el-option label="多选" value="多选"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="试题状态">
          <el-select v-model="formInline.questionStatus" placeholder="试题状态">
            <el-option label="开放" value="开放"></el-option>
            <el-option label="关闭" value="关闭"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="试题难度">
          <el-select v-model="formInline.questionDifficulty" placeholder="试题难度">
            <el-option label="容易" value="容易"></el-option>
            <el-option label="一般" value="一般"></el-option>
            <el-option label="较难" value="较难"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="questionbankListSearchFun">搜索</el-button>
        </el-form-item>
      </el-form>

      <!-- 试题列表 -->
      <el-table :data="questionList" stripe style="width: 100%" class="questionlist-form">
        <el-table-column prop="subjectName" label="所属题库" width="180"></el-table-column>
        <el-table-column prop="subjectType" label="试题类型" width="180"></el-table-column>
        <el-table-column prop="subjectContent" label="题目名称" width="180"></el-table-column>
        <el-table-column prop="isOpen" label="状态" width="180"></el-table-column>
        <el-table-column prop="defficultyWeight" label="试题难度" width="180"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" icon="icon iconfont hoperun-kanguo" type="success" plain @click="subjectIsOpenFun(scope.row)" v-if="scope.row.isOpen == '开放'"></el-button>
            <el-button size="mini" icon="icon iconfont hoperun-in_biyan" type="danger" plain @click="subjectIsOpenFun(scope.row)" v-else></el-button>
            <el-button size="mini" icon="icon iconfont hoperun-remove-1-copy" type="danger" plain @click="subjectDeleteFun(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>
    <!-- 分页 -->
    <el-pagination class="page"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[5, 10, 15, 20, 25, 30]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
    <!-- 批量导入试题 -->
    <el-dialog title="导入试题" :visible.sync="addDialogFormVisible">
      <el-form :model="addForm">
        <el-form-item label="请选择题库">
          <el-select v-model="pppss.subjectDbId" placeholder="请选择题库名称">
            <el-option v-for="(tiku,index) in tikuList" :key="index" :label="tiku.subjectDbName" :value="tiku.id"></el-option>
          </el-select>
        </el-form-item>
        <div>
        </div>
        <el-form-item label="试题模板">
          <el-button style="margin-left: 10px;" size="small" type="primary" @click="templateDown">下载模板</el-button>
        </el-form-item>
        <el-form-item label="文件上传">
          <el-upload ref="upload" action="api/subject-manage/importFile" :on-preview="handlePreview" :data="pppss" accept=".xls,.xlsx"
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
export default {
  inject: ['reload'],
  data () {
    return {
      pppss: {
        subjectDbId: this.tikuId
      },
      total: 0,
      pageSize: 5,
      pageNum: 1, // 当前在第几页
      tikuList: [],
      tikuId: '',
      questionId: '',
      questionList: [],
      addDialogFormVisible: false,
      addForm: {
        subjectDbName: ''
      },
      formInline: {
        questionType: '',
        keyword: '',
        questionStatus: '',
        questionDifficulty: ''
      },
      fileList: []

    }
  },
  created () {
    this.getTikuListFun()
    this.initList()
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
      link.setAttribute('download', '试题模板.xls') // 设置download属性
      document.body.appendChild(link) // 插入到文档中
      link.click()
    },
    // 点击调用接口
    templateDown () {
      axios({
        method: 'get',
        url: 'api/subject-manage/downloadTemplate',
        data: {},
        responseType: 'blob' // 类似于解码
      })
        .then(res => {
          this.download(res.data) // 请求成功,拿到res后,调用download函数
          console.log(res.data)
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

    // 分页
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.pageNum = 1
      this.pageSize = val
      this.initList() // 方法中再次调用获取数据的函数
    },
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`) // val表示当前在第几页
      this.pageNum = val
      this.initList()
    },
    questionbankListSearchFun () {
      this.pageNum = 1
      this.initList()
    },
    HomeSidebarFun () {
      global.app.$emit('exitSelectModel', 'title')
    },
    // 试题页面获取题库列表
    getTikuListFun () {
      let _self = this
      let upData = {
        params: {
          // 分页后台默认传10条; 前台设置拿10万条数据(选择所有题库)
          pageNum: 1,
          pageSize: 100000
        }
      }
      axios.get('api/subject-manage/queryAllsubjectDb', upData)
        .then(res => {
          if (res.status === 200) {
            _self.tikuList = res.data.list
          }
        })
    },
    initList () {
      // 发请求获取试题
      let _self = this
      let NewJson = JSON.parse(JSON.stringify(_self.formInline))
      switch (NewJson.questionType) {
        case '单选':
          NewJson.questionType = 0
          break
        case '多选':
          NewJson.questionType = 1
          break
        default:
          NewJson.questionType = ''
          break
      }
      switch (NewJson.questionStatus) {
        case '关闭':
          NewJson.questionStatus = 0
          break
        case '开放':
          NewJson.questionStatus = 1
          break
        default:
          NewJson.questionStatus = ''
          break
      }
      switch (NewJson.questionDifficulty) {
        case '容易':
          NewJson.questionDifficulty = 1
          break
        case '一般':
          NewJson.questionDifficulty = 2
          break
        case '较难':
          NewJson.questionDifficulty = 3
          break
        default:
          NewJson.questionDifficulty = ''
          break
      }
      axios.get('api/subject-manage/queySubjectByContent', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          subjectContent: NewJson.keyword.trim(),
          subjectDbId: _self.pppss.subjectDbId,
          subjectType: NewJson.questionType,
          isOpen: NewJson.questionStatus,
          defficultyWeight: NewJson.questionDifficulty
        }
      })
        .then(res => {
          if (res.status === 200) {
            this.total = res.data.total
            var temp = res.data.list
            for (var i = 0; i < temp.length; i++) {
              // 时间戳转换
              temp[i].createTime = this.timestampToTime(temp[i].createTime)
              if (temp[i].isOpen === 1) {
                temp[i].isOpen = '开放'
              } else {
                temp[i].isOpen = '关闭'
              }
              if (temp[i].subjectType === 0) {
                temp[i].subjectType = '单选'
              } else {
                temp[i].subjectType = '多选'
              }
              if (temp[i].defficultyWeight === 1) {
                temp[i].defficultyWeight = '容易'
              } else if (temp[i].defficultyWeight === 2) {
                temp[i].defficultyWeight = '一般'
              } else {
                temp[i].defficultyWeight = '较难'
              }
            }
            _self.questionList = res.data.list
          }
        })
    },
    timestampToTime (timestamp) {
      var date = new Date(timestamp) // 时间戳为10位需*1000，时间戳为13位的话不需乘1000
      var Y = date.getFullYear() + '-'
      var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
      var D = date.getDate() + ' '
      var h = date.getHours() + ':'
      if (date.getHours() < 10) {
        h = '0' + h
      }
      var m = date.getMinutes() + ':'
      if (date.getMinutes() < 10) {
        m = '0' + m
      }
      var s = date.getSeconds()
      if (date.getSeconds() < 10) {
        s = '0' + s
      }
      return Y + M + D + h + m + s
    },
    subjectDeleteFun (obj) { // 试题删除
      let _self = this
      if (obj.id) {
        axios.delete('api/subject-manage/deleteSubject', {params: {id: parseInt(obj.id)}})
          .then(res => {
            if (res.status === 200 && res.data) {
              _self.initList()
              _self.$message({
                message: '试题删除成功！',
                type: 'success'
              })
            }
          })
      }
    },
    addOperationQuestionFun () {
      this.$router.push({
        name: 'addTestQuestions',
        path: 'admin/addTestQuestions'
      })
    },
    beforeAvatarUpload () {

    },
    handleAvatarSuccess () {

    },
    handleRemove (file, fileList) {
      // console.log(file, fileList)
    },
    handlePreview (file) {
      // console.log(file)
    },
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    // 点击批量导入******
    addQuestion () {
      this.addDialogFormVisible = true
    },
    ShangChuanChengGongFun (response, file, fileList) {
      let _self = this
      if (response.resultDesc === '成功') {
        _self.addDialogFormVisible = false
        _self.reload() // 刷新当前页面
        _self.$message({
          message: '试题导入成功',
          type: 'success'
        })
      } else {
        _self.addDialogFormVisible = false
        _self.reload() // 刷新当前页面
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
    },
    subjectIsOpenFun (obj) {
      let _self = this
      let NewisOpen = 1
      if (obj.isOpen === '开放') {
        NewisOpen = 0
      } else {
        NewisOpen = 1
      }
      let upData = { 'id': obj.id, 'isOpen': NewisOpen }
      axios.put('api/subject-manage/updatesubjects ', upData)
        .then(res => {
          if (res.status === 200 && res.data) {
            _self.$message({
              message: '修改成功',
              type: 'success'
            })
            _self.initList()
          }
        })
    }
  }
}

</script>
<style lang="scss">
  .main {
    margin-top: 6px;
    padding: 10px;
    border-radius: 5px;
    background-color: #FFFFFF;
  }

  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }

  .el-input__inner {
    // width: 140px;
    padding: 0 10px;
  }

  .el-table .cell {
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
  }

</style>
