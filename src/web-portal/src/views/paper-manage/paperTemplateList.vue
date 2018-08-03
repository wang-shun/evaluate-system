<template>
  <div class="paperTemplateList">
    <div class="paperTemplateList-add">
      <el-button type="primary" @click="templateAddFun">新建模板</el-button>
    </div>

    <el-table :data="initList" stripe style="width: 100%" class="paperTemplateList-table">
      <el-table-column prop="colCreator" label="创建人" width="180"></el-table-column>
      <el-table-column prop="colTemplateName" label="模板名称"></el-table-column>
      <el-table-column prop="colCreateTime" label="创建时间"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" circle @click="modificationFun(scope.$index,scope.row)"></el-button>
          <el-button type="danger" icon="el-icon-delete" circle @click="deleteFun(scope.$index,scope.row)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination class="paperTemplateList-paging" @size-change="pageSizeFun" @current-change="pageNumFun" :current-page="pageNum" :page-sizes="[5, 10, 15, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>

  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'paperTemplateList',
  data () {
    return {
      initList: [],
      colTemplateName: '',
      colCreator: '',
      pageSize: 10,
      pageNum: 1,
      total: 0
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      let _self = this
      let upData = {
        params: {
          colTemplateName: _self.colTemplateName,
          colCreator: _self.colCreator,
          pageSize: _self.pageSize,
          pageNum: _self.pageNum
        }
      }
      axios.get('api/exam-manage/examTemplate', upData)
        .then(res => {
          console.log(res)
          if (res.status === 200) {
            _self.initList = res.data.list
            _self.total = res.data.total
          }
        })
    },
    templateAddFun () {
      this.$router.push({
        name: 'paperTemplate',
        path: 'admin/paperTemplate'
      })
    },
    modificationFun (index, row) {
      let temporary = JSON.stringify(row)
      this.$router.push({
        name: 'questionTemplateModification',
        path: 'admin/questionTemplateModification',
        query: {
          data: temporary
        }
      })
    },
    deleteFun (index, event) {
      let _self = this
      axios.delete('api/exam-manage/deleteTemPlate', {params: {id: event.id}})
        .then(res => {
          console.log(res)
          if (res.status === 200 && res.data.resultCode === '0') {
            _self.$message({
              message: '试题模板删除成功！',
              type: 'success'
            })
            _self.initList.splice(index, 1)
            _self.total = _self.total - 1
          } else {
            _self.$message({
              message: '网络错误请稍后重试！',
              type: 'success'
            })
          }
        })
    },
    pageSizeFun (val) {
      this.pageNum = 1
      this.pageSize = val
      this.init()
    },
    pageNumFun (val) {
      this.pageNum = val
      this.init()
    }
  }
}

</script>

<style scoped>
  .paperTemplateList-table{
    margin-top: 20px;
  }

  .paperTemplateList-paging{
    margin-top: 20px;
    display: flex;
    justify-content: center
  }
</style>
