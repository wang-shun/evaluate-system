<template>
  <div class="department">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }"><span @click="HomeSidebarFun">首页</span> </el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>部门管理</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="departmentC">
      <div style="border:1px solid #d7d7d7; padding:20px">
        <el-tree :data="treeArr" node-key="id" default-expand-all :expand-on-click-node="false" :render-content="renderContent">
      </el-tree>
      </div>

    </div>

    <el-dialog title="新增部门" :visible.sync="dialogAdd">
      <el-form :model="formAdd" :rules="rulesAdd" ref="ruleFormAdd">
        <el-form-item label="部门名称" label-width="100px" prop="colName">
          <el-input v-model="formAdd.colName" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="部门描述" label-width="100px" prop="colDesc">
          <el-input v-model="formAdd.colDesc" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAddDis">取 消</el-button>
        <el-button type="primary" @click="dialogAppend">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改部门" :visible.sync="dialogAdd2">
      <el-form :model="formAdd2" :rules="rulesAdd2" ref="ruleFormAdd2">
        <el-form-item label="部门名称" label-width="100px" prop="colName">
          <el-input v-model="formAdd2.colName" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="部门描述" label-width="100px" prop="colDesc">
          <el-input v-model="formAdd2.colDesc" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAddDis2">取 消</el-button>
        <el-button type="primary" @click="dialogAppend2">确 定</el-button>
      </div>
    </el-dialog>
  </div>

</template>
<script>
import axios from 'axios'
export default {
  data () {
    return {
      catchData: {},
      formAdd: {
        colName: '',
        colDesc: ''
      },
      formAdd2: {
        colName: '',
        colDesc: ''
      },
      rulesAdd: {
        colName: [{
          required: true,
          message: '请输入部门名称',
          trigger: 'blur'
        }],
        colDesc: [{
          required: true,
          message: '请输入部门描述',
          trigger: 'blur'
        }]
      },
      rulesAdd2: {
        colName: [{
          required: true,
          message: '请输入部门名称',
          trigger: 'blur'
        }],
        colDesc: [{
          required: true,
          message: '请输入部门描述',
          trigger: 'blur'
        }]
      },
      dialogAdd: false,
      dialogAdd2: false,
      treeArr: []
    }
  },
  mounted () {
    let _this = this
    axios.get('api/account-manage/departmenttree/' + 100).then((res) => {
      if (res.status === 200) {
        let depArr = res.data.data.infos
        let depList = JSON.parse(JSON.stringify(depArr).replace(/colName/g, 'label').replace(/sonList/g,
          'children'))
        _this.treeArr = depList
      }
    })
  },
  methods: {
    HomeSidebarFun () {
      global.app.$emit('exitSelectModel', 'tklist')
    },
    dialogAddDis () {
      let _this = this
      _this.dialogAdd = false
    },
    dialogAddDis2 () {
      let _this = this
      _this.dialogAdd2 = false
    },
    dialogAppend () {
      let _this = this
      _this.$refs.ruleFormAdd.validate((valid) => {
        if (valid) {
          let beenData = {}
          beenData.colName = _this.formAdd.colName
          beenData.colDesc = _this.formAdd.colDesc
          beenData.colParentId = _this.catchData.id
          beenData.colCreateTime = new Date().getTime()
          beenData.colCreateorId = localStorage.getItem('myid')
          beenData.colUpdaterId = localStorage.getItem('myid')
          axios.post('api/account-manage/departmentadd', beenData).then(res => {
            if (res.data.code === '0000') {
              _this.$message({
                message: '添加部门成功',
                type: 'success',
                duration: 1000,
                onClose: function () {
                  _this.dialogAdd = false
                  axios.get('api/account-manage/departmenttree/' + 100).then((res) => {
                    if (res.status === 200) {
                      let depArr = res.data.data.infos
                      let depList = JSON.parse(JSON.stringify(depArr).replace(/colName/g, 'label').replace(
                        /sonList/g, 'children'))
                      _this.treeArr = depList
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

    dialogAppend2 () {
      let _this = this
      _this.$refs.ruleFormAdd2.validate((valid) => {
        if (valid) {
          let beenData = {}
          beenData.colName = _this.formAdd2.colName
          beenData.colDesc = _this.formAdd2.colDesc
          beenData.id = _this.catchData.id
          axios.put('api/account-manage/department', beenData).then(res => {
            if (res.data.code === '0000') {
              _this.$message({
                message: '修改部门成功',
                type: 'success',
                duration: 1000,
                onClose: function () {
                  _this.dialogAdd2 = false
                  axios.get('api/account-manage/departmenttree/' + 100).then((res) => {
                    if (res.status === 200) {
                      let depArr = res.data.data.infos
                      let depList = JSON.parse(JSON.stringify(depArr).replace(/colName/g, 'label').replace(
                        /sonList/g, 'children'))
                      _this.treeArr = depList
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
    append (data) {
      let _this = this
      _this.catchData = data
      _this.dialogAdd = true
    },

    append2 (data) {
      let _this = this
      _this.catchData = data
      _this.formAdd2.colName = data.label
      _this.formAdd2.colDesc = data.colDesc
      _this.dialogAdd2 = true
    },

    remove (node, data) {
      let _this = this
      this.$confirm('此操作将永久删除该部门, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete('api/account-manage/department/' + data.id).then(res => {
          if (res.data.code === '0000') {
            _this.$message({
              message: '删除部门成功',
              type: 'success',
              duration: 1000,
              onClose: function () {
                axios.get('api/account-manage/departmenttree/' + 100).then((res) => {
                  if (res.status === 200) {
                    let depArr = res.data.data.infos
                    let depList = JSON.parse(JSON.stringify(depArr).replace(/colName/g, 'label').replace(
                      /sonList/g, 'children'))
                    _this.treeArr = depList
                  }
                })
              }

            })
            // let parent = node.parent
            // let children = parent.data.children || parent.data
            // let index = children.findIndex(d => d.id === data.id)
            // children.splice(index, 1)
          }
        })
      }).catch(() => {

      })
    },
    renderContent (h, { node, data, store }) {
      return (
        <span class="custom-tree-node">
          <span>{node.label}</span>
          <span class="depS">
            <el-button size="mini" class="btnA colorDeepBlue" type="text" on-click={ () => this.append(data) }>新增</el-button>
            <el-button size="mini" class="btnA colorDeepYellow" type="text" on-click={ () => this.append2(data) }>修改</el-button>
            <el-button size="mini" class="btnA colorDeepRed" type="text" on-click={ () => this.remove(node, data) }>删除</el-button>
          </span>
        </span>)
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
      font-size: 14px
    }

    .custom-tree-node>.depS{
      margin-left: 30px
    }

     .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;

    border-bottom: 1px solid #e2e2e2
  }
  }
</style>
