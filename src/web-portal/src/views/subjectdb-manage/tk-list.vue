<template>
  <div class="tk-list">
    <el-row>
      <el-col :span='24'>
        <!-- 面包屑导航 -->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">
            <span @click="HomeSidebarFun">首页</span>
          </el-breadcrumb-item>
          <el-breadcrumb-item>题库管理</el-breadcrumb-item>
          <el-breadcrumb-item>题库列表</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <br>
    <el-button type="success" @click="addDialogFormVisible = true">添加题库</el-button>

    <el-form :inline="true" :model="questionBankFrom" class="demo-form-inline tkList-search">
      <el-form-item label="题库名称">
        <el-input v-model="questionBankFrom.questionBankName" placeholder="请输入题库名称" @keydown.enter.native="questionBankInquireFun"></el-input>
      </el-form-item>
      <el-form-item label="题库状态">
        <el-select v-model="questionBankFrom.questionBankOnOff" placeholder="请选择题库状态">
          <el-option label="开放" value="1"></el-option>
          <el-option label="关闭" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建者">
        <el-input v-model="questionBankFrom.questionBankCreator" placeholder="请输入创建者ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="questionBankInquireFun">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="tikuList" stripe style="width: 100%" @click="QuestionListIdFun()">
      <el-table-column type="index" width="50"> </el-table-column>
      <el-table-column prop="subjectDbName" label="题库名称" width="180">
      </el-table-column>
      <el-table-column prop="isOpen" label="题库状态" width="180">
      </el-table-column>
      <el-table-column prop="subjectDbDesc" label="题库描述"> </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button v-if="scope.row.isOpen === '开放'" size="mini" class="icon iconfont hoperun-kanguo" type="success" plain @click="questionBankOnOffFun(scope.row)"></el-button>
          <el-button v-else size="mini" class="icon iconfont hoperun-in_biyan" type="danger" plain @click="questionBankOnOffFun(scope.row)"></el-button>
          <el-button size="mini" class="icon iconfont hoperun-remove-1-copy" type="danger" plain @click="showDeleteDialog(scope.$index,scope.row)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加题库对话框 -->
    <el-dialog title="添加题库" :visible.sync="addDialogFormVisible">
      <el-form :model="addForm" label-width="80px" :rules="rules" ref="addTkForm">
        <el-form-item label="题库名称" prop="subjectDbName">
          <el-input v-model="addForm.subjectDbName"></el-input>
        </el-form-item>
        <el-form-item label="题库状态" prop="isOpen">
          <el-select v-model="addForm.isOpen" disabled placeholder="开放" @change="getValue">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="题库说明" prop="subjectDbDesc">
          <el-input type="textarea" v-model="addForm.subjectDbDesc"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addTkSubmit('addTkForm')">确 定</el-button>
      </div>
    </el-dialog>
    <el-pagination class="tkList-paging" @size-change="questionBankPageSizeFun" @current-change="questionBankPageNumFun" :current-page="pageNum"
      :page-sizes="[5, 10, 15, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
    </el-pagination>

    <!-- 编辑题库对话框 -->
    <el-dialog title="编辑题库" :visible.sync="editDialogFormVisible">
      <el-form :model="editForm" label-width="80px" :rules="rules" ref="editTkForm">
        <el-form-item label="题库名称" prop="subjectDbName">
          <el-input v-model="editForm.subjectDbName"></el-input>
        </el-form-item>
        <el-form-item label="题库状态" prop="isOpen">
          <el-select v-model="editForm.isOpen" placeholder="请选择状态">
            <el-option label="开放" value="shanghai"></el-option>
            <el-option label="关闭" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="题库说明" prop="subjectDbDesc">
          <el-input type="textarea" v-model="editForm.subjectDbDesc"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="editTkSubmit('editTkForm')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  //   name:"tklist",
  data () {
    return {
      userId: '',
      tikuList: [], // 题库列表视图
      addDialogFormVisible: false, // 对话框默认不显示
      questionBankFrom: {
        questionBankName: '',
        questionBankOnOff: '',
        questionBankCreator: ''
      },
      addForm: { // 新增题库
        subjectDbName: '',
        options: '开放',
        subjectDbDesc: ''
      },
      options: [{
        value: '选项1',
        label: '开放'
      },
      {
        value: '选项2',
        label: '关闭'
      }
      ],
      editDialogFormVisible: false, // 编辑题库
      editForm: {
        subjectDbName: '',
        isOpen: '',
        subjectDbDesc: '',
        id: 0
      },
      QuestionListId: '',
      rules: { // 添加题库的表单验证
        subjectDbName: [{
          required: true,
          message: '请输入题库名称',
          trigger: 'blur'
        }]
      },
      pageSize: 10,
      pageNum: 1,
      total: 10
    }
  },
  created () {
    this.userId = localStorage.getItem('myid')
    this.initList()
  },
  methods: {
    HomeSidebarFun () {
      global.app.$emit('exitSelectModel', 'title')
    },
    initList () { // 题库列表
      let _self = this
      let isOpenIsOpen = _self.questionBankFrom.questionBankOnOff
      if (isOpenIsOpen !== '') {
        isOpenIsOpen = parseInt(_self.questionBankFrom.questionBankOnOff)
      }
      let upData = {
        params: {
          subjectDbName: _self.questionBankFrom.questionBankName.trim(),
          isOpen: isOpenIsOpen,
          colCreatorId: _self.questionBankFrom.questionBankCreator.trim(),
          pageNum: _self.pageNum,
          pageSize: _self.pageSize
        }
      }
      console.log(upData)
      axios.get('api/subject-manage/queryAllsubjectDb', upData)
        .then(res => {
          console.log(res)
          if (res.status === 200) {
            if (res.data.list) {
              for (var i = 0; i < res.data.list.length; i++) {
              // 对象中的数字状态赋值为中文
                if (res.data.list[i].isOpen === 1) {
                  res.data.list[i].isOpen = '开放'
                } else {
                  res.data.list[i].isOpen = '关闭'
                }
              }
              _self.tikuList = res.data.list
              _self.total = res.data.total
            }
          }
        })
    },
    // 确认添加题库
    addTkSubmit (formName) {
      let _self = this
      _self.$refs[formName].validate(valide => {
        if (valide) {
          // 添加题库,发请求
          axios.post('api/subject-manage/addsubjectDb', {
            'creatorId': _self.userId,
            'subjectDbDesc': _self.addForm.subjectDbDesc,
            'subjectDbName': _self.addForm.subjectDbName
          })
            .then(res => {
              if (res.status === 200) {
                _self.addDialogFormVisible = false
                _self.initList()
                _self.addForm.subjectDbName = ''
                _self.addForm.subjectDbDesc = ''
                _self.$message({
                  message: '添加题库成功!',
                  type: 'success'
                })
              } else {
                _self.addForm.subjectDbName = ''
                _self.addForm.subjectDbDesc = ''
                _self.$message({
                  message: '题库添加失败!',
                  type: 'warning'
                })
              }
            })
        }
      })
    },
    // 显示编辑用户对话框
    showEditDialog (row) {
      // 这个题库某一行所有的数据
      // console.log(row) // 打印row,查看id,根据id发请求获取题库信息
      this.editDialogFormVisible = true
    },
    // 编辑用户信息提交
    editTkSubmit (formName) {
      this.$refs[formName].validate(valide => {
        if (valide) {}
      })
    },
    // 显示删除对话框
    showDeleteDialog (index, row) {
      let param = {
        id: row.id
      }
      let _self = this
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete('api/subject-manage/deleteSubjectDbById', {
          params: param
        })
          .then(res => {
            console.log(res)
            if (res.status === 200) {
              if (res.data.resultCode === '0006') { // 题库下面有试题
                this.$message({
                  type: 'warning',
                  message: '题库下有试题,不能删除'
                })
              } else {
                _self.tikuList.splice(index, 1)
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                })
              }
            }
          })
      })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除!'
          })
        })
    },
    // 修改题库状态
    questionBankOnOffFun (row) {
      let _self = this
      let NewisOpen = 1
      if (row.isOpen === '开放') {
        NewisOpen = 0
      } else {
        NewisOpen = 1
      }
      let upData = { 'id': row.id, 'isOpen': NewisOpen }
      axios.put('api/subject-manage/updateSubjectDb ', upData)
        .then(res => {
          if (res.status === 200 && res.data) {
            _self.$message({
              message: '修改成功',
              type: 'success'
            })
            _self.initList()
          }
        })
    },
    // 表单下拉框
    getValue (value) {
      console.log(value)
    },
    questionBankInquireFun () {
      this.pageNum = 1
      this.initList()
    },
    questionBankPageNumFun (val) {
      this.pageNum = val
      this.initList()
    },
    questionBankPageSizeFun (val) {
      this.pageNum = 1
      this.pageSize = val
      this.initList()
    }
  }
}

</script>
<style lang="scss" scoped>
  .page {
    padding: 5px 0;
    background-color: #d3dce6;
    margin-top: 20px;
  }

  .tkList-search {
    margin-top: 20px;
  }

  .tkList-paging {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }

</style>
