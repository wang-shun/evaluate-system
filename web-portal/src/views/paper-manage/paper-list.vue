<template>
  <div class="paperList">
    <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item><a href="/">试卷管理</a></el-breadcrumb-item>
        <el-breadcrumb-item>试卷列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-row>
        <el-col :span="4">
            <el-input placeholder="请输入试卷名称" class="search-input" v-model="query" @keydown.native.enter="initList">
                <el-button slot="append" icon="el-icon-search" @click="initList"></el-button>
            </el-input>
        </el-col>
    </el-row>

    <!-- 试卷列表 -->
    <el-table :data="paperList"  stripe style="width: 100%" @click="QuestionListIdFun()">
        <el-table-column type="index"  width="50"> </el-table-column>
        <el-table-column
        prop="colExamName"
        label="试卷名称"
        width="180">
        </el-table-column>
        <el-table-column
        prop="colPublish"
        label="试卷状态"
        width="180">
        </el-table-column>
        <el-table-column
        prop="colOpenTime"
        label="开放时间"
        width="180">
        </el-table-column>
        <el-table-column
        prop="colCloseTime"
        label="关闭时间"
        width="180">
        </el-table-column>
        <el-table-column
        prop="colDuration"
        label="考试时长"
        width="180">
        </el-table-column>
        <el-table-column
        prop="colCreatorId"
        label="创建人"
        width="180">
        </el-table-column>
        <el-table-column
        prop="colCreateTime"
        label="创建时间"
        width="180">
        </el-table-column>
        <el-table-column label="操作">
        <template slot-scope="scope">
            <!-- <el-button size="mini" icon="el-icon-edit" type="primary" plain @click="showEditDialog(scope.row)"></el-button> -->
            <el-button size="mini" icon="el-icon-document" type="warning" plain @click="toTestRecord(scope.$index,scope.row)"></el-button>            
            <el-button size="mini" icon="el-icon-delete" type="danger" plain @click="showDeleteDialog(scope.$index,scope.row)"></el-button>
        </template>
        </el-table-column>
    </el-table>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      query:'',
      paperList:[
        {
          colExamName: "",
          colPublish: "",
          colOpenTime: "",
          colCloseTime:'',
          colDuration:"",
          creater:"",
          colCreateTime:''
        },
      ]
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
    initList() {
      //初始化表格数据列表
      axios.get('api/exam-manage/examall',{})
			    .then(res=> {
              if(res.data.code=="0000"){
                console.log(res.data.data.infos);
                var temp = res.data.data.infos;
                for(var i=0;i<temp.length;i++){
                    //时间戳转换
                    temp[i].colOpenTime = this.timestampToTime(temp[i].colOpenTime);
                    temp[i].colCloseTime = this.timestampToTime(temp[i].colCloseTime);
                    temp[i].colCreateTime = this.timestampToTime(temp[i].colCreateTime);
                    if(temp[i].colPublish==1){
                      temp[i].colPublish="开放"
                    } else {
                      temp[i].colPublish="关闭"                      
                    }                   
                }
                this.paperList=temp
                console.log('123');
                
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


    // 查看考试记录详情
    toTestRecord(index,row){
      console.log("点击按钮,查看这一份试卷信息");
      console.log(row);
      console.log(row.id);//打印出这一行所有数据
      // console.log(index);
       this.$router.push({
          path:'/testrecord',
          name:'testrecord',
          params:{
           id : row.id
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