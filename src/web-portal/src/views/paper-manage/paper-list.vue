<template>
  <div class="paperList">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }"><span @click="HomeSidebarFun">首页</span></el-breadcrumb-item>
      <el-breadcrumb-item>试卷管理</el-breadcrumb-item>
      <el-breadcrumb-item>试卷列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- <el-row>
      <el-col :span="4">
        <el-input placeholder="请输入试卷名称" class="search-input" v-model="query" @keydown.native.enter="initList">
          <el-button slot="append" icon="el-icon-search" @click="initList"></el-button>
        </el-input>
      </el-col>
    </el-row> -->

    <!-- 试卷列表 -->
    <el-table :data="paperList" stripe style="width: 100%" @click="QuestionListIdFun()">
      <el-table-column type="index" width="50"> </el-table-column>
      <el-table-column prop="colExamName" label="试卷名称" width="180">
      </el-table-column>
      <el-table-column prop="colPublish" label="试卷状态" width="180">
      </el-table-column>
      <el-table-column prop="colOpenTime" label="开放时间" width="180">
      </el-table-column>
      <el-table-column prop="colCloseTime" label="关闭时间" width="180">
      </el-table-column>
      <el-table-column prop="colDuration" label="考试时长" width="180">
      </el-table-column>
      <el-table-column prop="colCreatorId" label="创建人" width="180">
      </el-table-column>
      <el-table-column prop="colCreateTime" label="创建时间" width="180">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="warning" plain @click="toTestRecord(scope.$index,scope.row)">详情</el-button>
          <!-- <el-button size="mini" icon="el-icon-delete" type="danger" plain @click="showDeleteDialog(scope.$index,scope.row)"></el-button> -->
          <el-button size="mini" type="success" plain @click="reCreateExam(scope.$index,scope.row)">重新发布</el-button>
          <el-button size="mini" type="primary" plain @click="addPerson(scope.$index,scope.row)">添加人员</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <!-- <el-pagination class="page"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="1"
      :page-sizes="[5, 10, 15, 20, 25, 30]"
      :page-size="1"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination> -->
  </div>
</template>
<script>
import axios from 'axios'
export default {
  data () {
    return {
      query: '',
      paperList: [{
        colExamName: '',
        colPublish: '',
        colOpenTime: '',
        colCloseTime: '',
        colDuration: '',
        creater: '',
        colCreateTime: ''
      } ]
    }
  },
  created () {
    this.initList()
  },
  methods: {
    HomeSidebarFun () {
      global.app.$emit('exitSelectModel', 'title')
    },
    handleSizeChange (val) {
      // console.log(`每页 ${val} 条`)
    },
    handleCurrentChange (val) {
      // console.log(`当前页: ${val}`)
    },
    initList () {
      // 初始化表格数据列表
      axios.get('api/exam-manage/examall', {})
        .then(res => {
          if (res.data.code === '0000') {
            var temp = res.data.data.infos
            for (var i = 0; i < temp.length; i++) {
              // 时间戳转换
              temp[i].colOpenTime = this.timestampToTime(temp[i].colOpenTime)
              temp[i].colCloseTime = this.timestampToTime(temp[i].colCloseTime)
              temp[i].colCreateTime = this.timestampToTime(temp[i].colCreateTime)
              if (temp[i].colPublish === 1) {
                temp[i].colPublish = '开放'
              } else {
                temp[i].colPublish = '关闭'
              }
            }
            this.paperList = temp
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

    // 查看考试记录详情
    toTestRecord (index, row) {
      this.$router.push({
        path: 'admin/testrecord',
        name: 'testrecord',
        params: {
          id: row.id
        }
      })
    },
    // 考试重新发布
    reCreateExam (index, row) {
      axios.post('api/exam-manage/examToRepeat/' + row.id + '/' + row.colCreatorId, {})
        .then(res => {
          if (res.data.code === '0000') {
            this.$message({
              message: '考试重新发布成功',
              type: 'success'
            })
          } else {
            this.$message.error('考试重新发布失败')
          }
        }).catch(() => {
          this.$message({
            showClose: true,
            message: '考试重新发布失败,请稍后重试!',
            type: 'warning'
          })
        })
    },
    // 添加人员
    addPerson (index, row) {
      console.log(row)
      this.$router.push({
        path: 'admin/addPerson',
        name: 'addPerson',
        query: {
          id: row.id,
          colExamName: row.colExamName
        }
      })
    }
  }
}

</script>
<style lang="scss" scoped>
  .page {
    padding: 5px 0;
    background-color: rgb(246, 247, 248);
    margin: 25px 0 -6px;
  }

</style>
