<template>
  <div>
    <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item><a href="javascript:voild">试卷管理</a></el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/paperlist' }">试卷列表</el-breadcrumb-item>
        <el-breadcrumb-item>考试记录</el-breadcrumb-item>        
    </el-breadcrumb>

    <!-- 表单:试卷详情 -->
    <el-row>
        <el-col :span='6'>
            <div class="paperInfo">
                <el-form label-width="80px" :model="paperInfo">
                    <el-form-item label="试卷名称">
                        <el-input v-model="paperInfo.colExamName"></el-input>
                    </el-form-item>
                    <el-form-item label="考试时长">
                        <el-input v-model="paperInfo.colDuration"></el-input>
                    </el-form-item>
                    <el-form-item label="卷面总分">
                        <el-input v-model="paperInfo.colTotalScore"></el-input>
                    </el-form-item>
                    <el-form-item label="通过分数">
                        <el-input v-model="paperInfo.colPassScore"></el-input>
                    </el-form-item>
                </el-form>
            </div>
        </el-col>
    </el-row>

    <!-- 查询 -->
    <el-row>
        <el-col :span="6">
            <el-input placeholder="请输入用户id" class="search-input" v-model="query" @keydown.native.enter="initList">
                <el-button slot="append" icon="el-icon-search" @click="initList"></el-button>
            </el-input>
        </el-col>
    </el-row>
    <!-- 试卷列表 -->
    <el-table :data="stuList"  stripe style="width: 100%" @click="QuestionListIdFun()">
        <el-table-column type="index"  width="50"></el-table-column>
        <el-table-column prop="paperOwnerId" label="用户id" width="180"></el-table-column>
        <el-table-column prop="submitTime" label="提交时间" width="180"></el-table-column>
        <el-table-column prop="score" label="考试得分" width="180"></el-table-column>
        <el-table-column prop="paperStatus" label="是否通过" width="180"></el-table-column>
        <el-table-column label="操作">
        <template slot-scope="scope">
            <el-button size="mini" icon="el-icon-edit" type="primary" disabled plain @click="showEditDialog(scope.row)"></el-button>
            <!-- <el-button size="mini" icon="el-icon-document" type="warning" plain @click="showTestRecord(scope.row)"></el-button>  -->
            <el-button size="mini" icon="el-icon-delete" type="danger" disabled plain @click="showDeleteDialog(scope.$index,scope.row)"></el-button>
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
            id:"",
            query:"",
            stuList:[
                {
                paperOwnerId: "",
                submitTime: "",
                score: "",
                paperStatus:""
                },
            ],
            formLabelAlign: {
            name: '',
            region: '',
            type: ''
            },
            paperInfo: {
                colExamName: "",
                colDuration:"",
                colTotalScore:"",
                colPassScore:''
            },
        }
    },
    created() {
        // console.log(this.$route.params)
        // let id = this.$route.params.id
        // console.log(id)
        this.initList()
    },
    methods: {
        showEditDialog(){

        },
        showDeleteDialog(){

        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
        },
        initList() {
            console.log('进入了详情界面');
            let id = this.$route.params.id
            console.log(id);
            console.log('能否打印id');

            //1.初始化试卷基本信息
            axios.get('api/exam-manage/exam/'+id,{})
            .then(res=> {
                console.log(res);
                if(res.status==200){
                    console.log('试卷列表查询成功');
                    // console.log(res.data.data.infos);
                    this.paperInfo = res.data.data.infos
                }
            })
            //1.初始化考生信息
            axios.get('api/paper-manage/exampaper-exam/'+id,{})
            .then(res=> {
                console.log(1)
                console.log(res);
                if(res.status==200){
                    console.log('试卷列表查询成功');
                    console.log(res.data.data.infos);
                    var temp = res.data.data.infos;
                    for(var i=0;i<temp.length;i++){
                        temp[i].submitTime = this.timestampToTime(temp[i].submitTime);
                        if(temp[i].paperStatus==3){
                            temp[i].paperStatus="通过"
                        } else {
                            temp[i].paperStatus="不通过"                      
                        }                  
                    }
                    this.stuList=res.data.data.infos
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
    }
}
</script>
<style lang="scss" scoped>
    .paperInfo{
        background-color: #fff;
        padding: 10px;
        border-radius: 5px;
        margin-bottom: 5px;
    }
    .el-form-item {
        margin-bottom: 0px;
    }
    .page {
        padding: 5px 0;
        background-color: rgb(246, 247, 248);
        margin: 25px 0 -6px;
    }
</style>