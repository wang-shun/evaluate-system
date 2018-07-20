<template>
  <div class="tk-list">
    <el-row>
        <el-col :span='24'>
            <!-- 面包屑导航 -->
            <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item><a href="/">题库管理</a></el-breadcrumb-item>
                <el-breadcrumb-item>题库列表</el-breadcrumb-item>
            </el-breadcrumb>
        </el-col>
    </el-row>
    <br>
    <el-button type="success" @click="addDialogFormVisible = true">添加题库</el-button>
    <el-table :data="tikuList"  stripe style="width: 100%" @click="QuestionListIdFun()">
        <el-table-column type="index"  width="50"> </el-table-column>
        <el-table-column
        prop="subjectDbName"
        label="题库名称"
        width="180">
        </el-table-column>
        <el-table-column
        prop="isOpen"
        label="题库状态"
        width="180">
        </el-table-column>
        <el-table-column prop="subjectDbDesc" label="题库描述">  </el-table-column>
        <el-table-column label="操作">
        <template slot-scope="scope">
            <el-button size="mini" icon="el-icon-edit" type="primary" disabled plain @click="showEditDialog(scope.row)"></el-button>
            <el-button size="mini" icon="el-icon-delete" type="danger" disabled plain @click="showDeleteDialog(scope.$index,scope.row)"></el-button>
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
            <el-form-item label="题库说明"  prop="subjectDbDesc">
                <el-input type="textarea" v-model="addForm.subjectDbDesc"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="addDialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="addTkSubmit('addTkForm')">确 定</el-button>
        </div>
    </el-dialog>
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
            <el-form-item label="题库说明"  prop="subjectDbDesc">
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
import axios from "axios";
export default {
  //   name:"tklist",
  data() {
    return {
      //题库列表视图
      tikuList: [],
      addDialogFormVisible: false, //对话框默认不显示

      //新增题库
      addForm: {
        subjectDbName: "",
        options: "开放",
        subjectDbDesc: ""
      },
      options: [
        {
          value: "选项1",
          label: "开放"
        },
        {
          value: "选项2",
          label: "关闭"
        }
      ],
      // value:'',

      //编辑题库
      editDialogFormVisible: false,
      editForm: {
        subjectDbName: "",
        isOpen: "",
        subjectDbDesc: "",
        id: 0
      },
      QuestionListId:"",
      //添加题库的表单验证
      rules: {
        subjectDbName: [
          { required: true, message: "请输入题库名称", trigger: "blur" }
        ]
      }

    };
  },
  created() {
      this.initList()
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },

    //表单下拉框
    getValue(value) {
      console.log(value.value);
      console.log(value.label);
    },
    //初始化表格数据(题库列表); 2.created()钩子函数中调用
    initList() {
      console.log("初始化了");
      //初始化表格数据列表
      axios.get('api/subject-manage/queryAllsubjectDb',{})
			    .then(res=> {
			        console.log(1)
              console.log(res);
              if(res.status==200){
                  console.log('查询成功');
                  for(var i=0;i<res.data.length;i++){
                    console.log(res.data[i]);
                    //对象中的数字状态赋值为中文
                    if(res.data[i].isOpen==1){
                      res.data[i].isOpen="开放"
                    } else {
                      res.data[i].isOpen="关闭"                      
                    }
                  }
                  this.tikuList=res.data
              }
			    })
			    .catch(function (error) {
			        console.log(error);
          })
    },
    // 确认添加题库
    addTkSubmit(formName) {
      console.log("进入了");
      this.$refs[formName].validate(valide => {
        if (valide) {
          console.log("发请求");
          //添加题库,发请求
           axios.post('api/subject-manage/addsubjectDb',{
               "creatorId":7910,
               "subjectDbDesc":this.addForm.subjectDbDesc,
               "subjectDbName":this.addForm.subjectDbName,
               })
			    .then(res=> {
			        console.log(1)
              console.log(res);
              if(res.status==200){
                  console.log('添加成功')
                  this.addDialogFormVisible = false
                  this.initList()
                  this.$message({
                    message: '添加题库成功!',
                    type: 'success'
                  });
              } else {
                this.$message({
                  message: '题库添加失败',
                  type: 'warning'
                });
              }
              
			    })
			    .catch(function (error) {
			        console.log(error);
          })
        }
      });
    },
    //显示编辑用户对话框
    showEditDialog(row) {
      //这个题库某一行所有的数据
      console.log(row); //打印row,查看id,根据id发请求获取题库信息
      this.editDialogFormVisible = true;
      $.ajax({
        url: "",
        type: "",
        data: {
          id: row.id
        },
        success: res => {
          console.log(res);
          if (res.meta.status == 200) {
            //请求成功,将res.data中的内容赋值给editForm.
            this.editForm.subjectDbName = res.data.subjectDbName;
            this.editForm.isOpen = res.data.isOpen;
            this.editForm.subjectDbDesc = res.data.subjectDbDesc;
            this.editForm.id = res.data.id;
          }
        }
      });
    },
    //编辑用户信息提交
    editTkSubmit(formName) {
      this.$refs[formName].validate(valide => {
        if (valide) {
          $.ajax({
            url: "",
            type: "",
            data: {
              id: this.editForm.id
            },
            success: function(res) {
              console.log(res);
              if (res.meta.status == 200) {
                this.$message({
                  type: "success",
                  message: "修改题库成功"
                });
                this.editDialogFormVisible = false;
                this.init();
              }
            }
          });
        }
      });
    },
    //显示删除对话框
    showDeleteDialog(index,row) {
      let param={id:row.id};
      let _self = this;
      console.log(param);
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {          
          axios.delete('api/subject-manage/deleteSubjectDbById',{params:param})
          .then(data => {
            _self.tikuList.splice(index,1);
            this.$message({
              type: 'success',
              message: '删除成功!'
           });
          })
          .catch(err => {
            this.$message({
              type: 'info',
              message: '删除失败!'
           });
          })
          
        });
  //     // this.$confirm("此操作将永久删除该题库, 是否继续?", "提示", {
  //     //   confirmButtonText: "确定",
  //     //   cancelButtonText: "取消",
  //     //   type: "warning"
  //     // }).then(() => {
  //     //   axios.DELETE('/api/addsubjectDb',{"id":event.id})
  //     //     .then(res => {
  //     //       console.log(res);
  //     //         if(res.data){

  //     //         }
  //     //     })
  //     // })
  //     // .catch(() => {
  //     //   this.$message({
  //     //     type: "info",
  //     //     message: "已取消删除"
  //     //   });
  //     // });
    }
  }
};
</script>
<style lang="scss" scoped>
.page {
  padding: 5px 0;
  background-color: #d3dce6;
  margin-top: 20px;
}
</style>