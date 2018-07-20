<template>
  <div class="questionbank-list">
    <el-row>
        <el-col :span='24'>
            <!-- 面包屑导航 -->
            <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item><a href="/">题库管理</a></el-breadcrumb-item>
                <el-breadcrumb-item>试题列表</el-breadcrumb-item>
            </el-breadcrumb>
        </el-col>
    </el-row>
    <br>
    <el-button type="success" @click="addQuestion()">批量导入试题</el-button>

     <!-- 关键词搜索 -->
    <div class="main">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item label="关键词">
              <el-input v-model="formInline.keyword" placeholder="请输入关键词" @keydown.enter.native="initList"></el-input>
          </el-form-item>
          <!-- <el-form-item label="试题类型">
            <el-select v-model="questionId" placeholder="试题类型">
              <el-option v-for="(question,index) in questionList" :key="index" :label="question.subjectType" :value="question.id" ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="试题状态">
            <el-select v-model="questionId" placeholder="试题状态">
              <el-option v-for="(question,index) in questionList" :key="index" :label="question.isOpen" :value="question.id" ></el-option>
            </el-select>
          </el-form-item> -->
          <el-form-item>
            <el-button type="primary" @click="initList">搜索</el-button>
          </el-form-item>
        </el-form>

        <!-- 试题列表 -->
        <el-table :data="questionList" stripe style="width: 100%">
            <el-table-column prop="subjectName" label="所属题库" width="180"></el-table-column>
            <el-table-column prop="subjectType" label="试题类型" width="180"></el-table-column>
            <el-table-column prop="isOpen" label="状态" width="180"></el-table-column>
            <el-table-column prop="defficultyWeight" label="试题难度" width="180"></el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
            <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button size="mini" icon="el-icon-delete" type="danger" disabled plain @click="showDeleteDialog(scope.row)"></el-button>
            </template>
            </el-table-column>
        </el-table>
        
    </div>
    <!-- 批量导入试题 -->
    <el-dialog title="导入试题" :visible.sync="addDialogFormVisible">
      <el-form :model="addForm">
        <el-form-item label="请选择题库">
          <el-select v-model="pppss.subjectDbId" placeholder="请选择题库名称">
            <el-option v-for="(tiku,index) in tikuList" :key="index" :label="tiku.subjectDbName" :value="tiku.id" ></el-option>
          </el-select>
        </el-form-item>
        <div>
              <!-- 题库id为:　{{pppss.subjectDbId}} -->
        </div>
        <br>
        <el-form-item label="数据文件">
            <el-upload
            action="api/subject-manage/importFile"
            :on-preview="handlePreview"
            :data="pppss"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :limit="3"
            :on-exceed="handleExceed"
            :file-list="fileList">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  data() {
    return {
      pppss:{
        subjectDbId:parseInt(this.tikuId)
      },
      tikuList:[],
      tikuId:"",
      questionId:"",
      questionList: [{
        subjectName:"",
        subjectType:"",
        isOpen:"",
        defficultyWeight:"",
        createTime:"",
        creatorId:""
      }],
      addDialogFormVisible:false,
      addForm: {
        subjectDbName: "",
      },
      formInline: {
        keyword: "",
        subjectDbName: "",
        type:"",
        isOpen:""
      },
      fileList: []
    
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
    initList(){
      console.log("coming");
      //发请求获取题库
      axios.get('api/subject-manage/queryAllsubjectDb',{})
			    .then(res=> {
              console.log(res);
              if(res.status==200){
                  console.log('查询成功');
                  console.log(res.data);
                  this.tikuList=res.data
              }
      })
      //发请求获取试题
      axios.get('api/subject-manage/queySubjectByContent',{params:{
        subjectContent:this.formInline.keyword
      }})
			    .then(res=> {
              if(res.status==200){
                  console.log('题目列表请求成功');
                  console.log(res.data);
                  var temp = res.data;
                  for(var i=0;i<temp.length;i++){
                      //时间戳转换
                      temp[i].createTime = this.timestampToTime(temp[i].createTime);
                      if(temp[i].isOpen==1){
                        temp[i].isOpen="开放"
                      } else {
                        temp[i].isOpen="关闭"                      
                      }
                      if(temp[i].subjectType==0){
                        temp[i].subjectType="单选"
                      } else {
                        temp[i].subjectType="多选"                      
                      }
                      if(res.data[i].defficultyWeight==1){
                        temp[i].defficultyWeight="容易"                                            
                      } else if(res.data[i].defficultyWeight==2){
                        temp[i].defficultyWeight="一般"  
                      } else{
                        temp[i].defficultyWeight="较难"
                      }                  
                  }
                  this.questionList=res.data
              }
      })
    },
    timestampToTime(timestamp){
        var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        var D = date.getDate() + ' ';
        var h = date.getHours() + ':';
        if(date.getHours() < 10)
        {
          h = '0'+h;
        }
        var m = date.getMinutes() + ':';
        if(date.getMinutes() < 10)
        {
          m = '0'+m;
        }
        var s = date.getSeconds();
        if(date.getSeconds() < 10)
        {
          s = '0'+s;
        }
        return Y+M+D+h+m+s;
    },
    //点击删除显示对话框
    
    // 文件上传功能
    beforeUpload(file,id){
      console.log("file")
      let fd = new FormDate()
      fd.append('file',file)
      fd.append('subjectDbId',this.id)
      axios.post("api/subject-manage/importFile",fd)
      return false
    },
    newSubmitForm(){//确定上传
      alert("fff");
        this.$refs.newupload.submit();
    },
    newhandleChange(){

    },
    newhandlesuccess() {},
    beforeAvatarUpload(){

    },
    handleRemove(){

    },
    handleAvatarSuccess(){

    },
    handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
    },
    //点击批量导入******
    addQuestion(){
      this.addDialogFormVisible=true
      console.log('弹出导入提示框');
      console.log(this.pppss.subjectDbId);
      
      //发请求获取题库
      // axios.get('api/subject-manage/queryAllsubjectDb',{})
			//     .then(res=> {
			//         console.log(1)
      //         console.log(res);
      //         if(res.status==200){
      //             console.log('查询成功');
      //             console.log(res.data);
      //             this.tikuList=res.data
      //         }
			// })
    }

  }
};
</script>
<style lang="scss" scoped>
.main{
  margin-top:6px;
  padding:10px;
  border-radius: 5px;
  background-color:#FFFFFF;
}
.page {
  padding: 5px 0;
  background-color: rgb(246, 247, 248);
  margin: 25px 0 -6px;
}
.el-input__inner {
      width:140px;
      padding: 0 10px;
}
</style>