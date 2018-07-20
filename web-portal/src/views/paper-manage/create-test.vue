<template>
	<div class="create-test">
		<el-breadcrumb separator="/">
			<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item><a href="/">试卷管理</a></el-breadcrumb-item>
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
								<el-option v-for="Item in tikuList" :label="Item.subjectDbName" :value="Item.id"> </el-option>
							</el-select>
						</el-form-item>
						<el-form-item label="考试时间" required>
							<el-col :span="11">
								<el-form-item prop="StartTime">
									<el-date-picker type="date" format="yyyy-MM-dd" placeholder="选择开始日期" v-model="CreateTestFormData.StartTime" style="width: 100%;"></el-date-picker>
								</el-form-item>
							</el-col>
							<el-col class="line" :span="2"> - </el-col>
							<el-col :span="11">
								<el-form-item prop="EndTime">
									<el-date-picker type="date" format="yyyy-MM-dd" placeholder="选择结束时间" v-model="CreateTestFormData.EndTime" style="width: 100%;"></el-date-picker>
								</el-form-item>
							</el-col>
						</el-form-item>
						<el-row>
							<el-col :span="6">
								<el-form-item label="考试时长" prop="TestTime">
									<el-input v-model.number="CreateTestFormData.TestTime" > </el-input>
								</el-form-item>
							</el-col>
							<el-col :span="2" class="create-test-TestTime">
								&nbsp;&nbsp;&nbsp;&nbsp;分钟
							</el-col>
						</el-row>
						<el-form-item label="试卷状态" prop="delivery">
							<el-switch v-model="CreateTestFormData.delivery"></el-switch>
						</el-form-item>
						<div>
							<el-row v-for="(Item,index) in CreateTestFormData.domains">
								<el-col :span="6">
									<el-form-item :label="'试题题型'" >
										<el-select v-model.number="Item.colSubjectType" style="width: 100%;">
											<el-option label="单选" value="单选"> </el-option>
											<el-option label="多选" value="多选"> </el-option>
										</el-select>
									</el-form-item>
								</el-col>
								<el-col :span="6" class="create-test-TestTime">
									<el-form-item label="试题数量">
										<el-input v-model.number="Item.colSubjectCount" @blur="TotalPointsCount"> </el-input>
									</el-form-item>
								</el-col>
								<el-col :span="6" class="create-test-TestTime">
									<el-form-item label="每题分数">
										<el-input v-model.number="Item.colSubjectPoint" @blur="TotalPointsCount"> </el-input>
									</el-form-item>
								</el-col>
								<el-col :span="2" class="create-test-TestTime" v-if="index == 0">
									&nbsp;&nbsp;&nbsp;&nbsp;<el-button type="primary" @click="AddTopicFun">添加</el-button>
								</el-col>
								<el-col :span="2" class="create-test-TestTime" v-else>
									&nbsp;&nbsp;&nbsp;&nbsp;<el-button type="danger" icon="el-icon-delete" circle @click="DeleteTopicFun(index)"></el-button>
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
						<el-form-item label="选择部门" prop="desc">
							<el-cascader :options="Departmentlist"  v-model="selectedOptions" change-on-select  @change="CandidateListFun"></el-cascader>
							
						</el-form-item>
						<el-form-item label="考试人员" prop="desc">
							<el-row>
								<el-col :span="20">
									<el-row>
										<el-col v-for="item in CurrentandidatesList" :span="4" class="KaoShiRenYuan">
											<el-alert title="" type="info">{{item.colAccountName}}</el-alert>
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
							<el-button @click="resetForm('ruleForm')">重置</el-button>
						</el-form-item>
					</el-form>
				</div>
			</el-col>
		</el-row>
		<div class="renyuanzhanshi" v-show="CandidateListShow">
			<div class="renyuanzhanshi-content">
				<div class="renyuanzhanshi-hide">
					<el-checkbox :indeterminate="isIndeterminate" v-model="ExamineeCheckAll" @change="handleCheckAllChange">全选</el-checkbox>
					<div style="margin: 15px 0;"></div>
					<el-checkbox-group v-model="CurrentandidatesList" @change="handleCheckedCitiesChange">
						<el-checkbox v-for="item in CandidateList" :label="item" :key="item.id">{{item.colAccountName}}({{item.id}})</el-checkbox>
					</el-checkbox-group>
				</div>
			</div>
			 <el-button type="primary" @click="CandidateListShowFun">确认</el-button>
			 <el-button type="info"  @click="CandidateListShowFun">取消</el-button>
		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		inject:['reload'],
		data() {
			return {
				CreateTestFormData:{
					name: '',//试卷名称
					region: '',//题库ID
					TestTime:60,//考试时长
					StartTime: '',//开始时间
					EndTime: '',//结束时间
					delivery: true,//试卷是否开放
					desc: '',//试卷说明
					ThroughPoints:"",//通过分数
					domains:[{ colSubjectType: "单选",colSubjectCount:30,colSubjectPoint:1}],//题型规则列表
					TotalPoints:30//总分
				},
				CreateTestRules: {
					name: [{required: true,message: '试卷名称',trigger: 'blur'},{min: 3,max: 20,message: '长度在 3 到 20 个字符',trigger: 'blur'}],
					region: [{required: true,message: '请选择题库',trigger: 'change'}],
					StartTime: [{type: 'date',required: true,message: '请选择日期',trigger: 'change'}],
					EndTime: [{type: 'date',required: true,message: '请选择时间',trigger: 'change'}],
					TestTime:[{ required: true, message: '考试时长不能为空'},{ type: 'number', message: '考试时长必须为数字值'}],
					type: [{type: 'array',required: true,message: '请至少选择一个活动性质',trigger: 'change'}],
					desc: [{required: false,message: '请填写活动形式',trigger: 'blur'}],
					ThroughPoints:[{ required: true, message: '通过分数'}]
				},
				tikuList:[],//题库列表
				Departmentlist:[],//部门列表
				selectedOptions: [],//选择的部门id
				CandidateListShow:false,//考生列表页面展示
				ExamineeCheckAll: false,//考生全选
				CurrentandidatesList:[],//选中考生列表
		        CandidateList:[],//考生列表
		        isIndeterminate: true//不确定状态
			};
		},
		mounted:function(){
			this.TiKuListFun();
			this.BuMenListFun();
		},
		methods: {
			TiKuListFun(){//获取题库列表
				axios.get('api/subject-manage/queryAllsubjectDb')
			    .then(res=> {
			    	if(res.status==200){
			    		if(res.data){
			    			this.tikuList=res.data;
			    		}
			    	}
			    })
			},
			BuMenListFun(){//获取部门信息
				axios.get('api/account-manage/departmenttree/'+100)
			    .then(res=> {
			    	if(res.status==200){
			    		if(res.data){
			    			let JsonStr = JSON.stringify(res.data.data.infos);
			    			let JsonStrOne = JsonStr.replace(/sonList/g, 'children');
			    			let JsonStrTow = JsonStrOne.replace(/id/g, 'value');
			    			let JsonStrThree = JsonStrTow.replace(/colName/g, 'label');
			    			let JsonStrFour = JsonStrThree.replace(/chi?ldren:[],/g, '');
			    			this.Departmentlist=JSON.parse(JsonStrThree);		
			    		}
			    	}
			    })
			},
			CandidateListFun(value){//获取部门下的考生列表
				let index = value.length-1;
				axios.get('api//account-manage/department/'+value[index])
			    .then(res=> {
			    	if(res.status==200){
			    		if(res.data){
			    			this.CandidateList = res.data.data.accountInfos;
			    		}
			    	}
			    })
			},
			handleCheckAllChange(val) {//考生全选操作
				this.CurrentandidatesList = val ? this.CandidateList : [];
				this.isIndeterminate = false;
			},
			handleCheckedCitiesChange(value) {//单个考生选择操作
				let checkedCount = value.length;
				this.ExamineeCheckAll = checkedCount === this.CandidateList.length;
				this.isIndeterminate = checkedCount > 0 && checkedCount < this.CandidateList.length;
			},
			CandidateListShowFun(){
				if(this.CandidateList.length>0){
					if (this.CandidateListShow) {
						this.CandidateListShow = false;
					} else{
						this.CandidateListShow= true;
					}
				}else{
					this.$message({
						message: '您未选择部门或当前部门为空！请重新选择。',
						type: 'warning'
					});
				}
				
			},
			TotalPointsCount(){
				let _self = this;
				let TotalPoints = 0;
				for (let i=0;i<_self.CreateTestFormData.domains.length;i++) {
					TotalPoints = TotalPoints+_self.CreateTestFormData.domains[i].colSubjectCount*_self.CreateTestFormData.domains[i].colSubjectPoint;
				};
				_self.CreateTestFormData.TotalPoints = TotalPoints;
			},
			submitForm(formName) {
				let _self = this;
				let UserName = localStorage.getItem("myid");
				let colPublishVal = 1;
				if (!_self.CreateTestFormData.delivery) {
					colPublishVal = 0
				};
				let examineeId = [];
				if (_self.CurrentandidatesList.length>0) {
					for(let i=0;i<_self.CurrentandidatesList.length;i++){
						examineeId.push(_self.CurrentandidatesList[i].id)
						};
				} else{
					this.$message({
						message: '至少添加一名考生！',
						type: 'warning'
					});
					return false;
				}
				let Updomains =  _self.CreateTestFormData.domains;
				console.log( Updomains.length);
				for (let index = 0; index < Updomains.length; index++) {
					console.log(Updomains[index].colSubjectType);
					if (Updomains[index].colSubjectType == "单选") {
						Updomains[index].colSubjectType = 0;
					} else if (Updomains[index].colSubjectType == "多选") {
						Updomains[index].colSubjectType = 1;
					}		
				}
				let UpData = {
					"exam" : {
						"colExamName" : _self.CreateTestFormData.name,//
						"colExamDesc" : _self.CreateTestFormData.desc,
						"colSubjectDbId" : _self.CreateTestFormData.region,
						"colOpenTime" : _self.CreateTestFormData.StartTime.getTime(),
						"colCloseTime" : _self.CreateTestFormData.EndTime.getTime(),
						"colTotalScore" : _self.CreateTestFormData.TotalPoints,//总分
						"colPassScore" : _self.CreateTestFormData.ThroughPoints,
						"colDuration" : _self.CreateTestFormData.TestTime,
						"colLimitCount" : 1000,//考试次数
						"colPublish" : colPublishVal,//是否发布
						"colCreatorId" : UserName,//发布者id
						"colUpdateId" : UserName//更新者ID
					},
					"examStruct" : Updomains,
					"examAccountList" : {
						"ids" :examineeId,
						"creatId" : UserName,
						"limitCount" : 10
					}
				};
				axios.post('api/exam-manage/examination',UpData)
			    .then(res=> {
			    	console.log("开始打印CreateTestFormData")
					console.log(res)
			    	if(res.status==200){
						this.reload() //刷新当前页面
						this.$message({
							message: '考试发布成功',
							type: 'success'
						});
			    	} else {
						this.$message.error('考试发布失败');
					}
			    })
				
			},
			AddTopicFun() {
				if (this.CreateTestFormData.domains.length<2) {
					let NewJson = { colSubjectType: '',colSubjectCount:"",colSubjectPoint:""};
					this.CreateTestFormData.domains.push(NewJson);
				}
			},
			DeleteTopicFun(index) {
				this.CreateTestFormData.domains.splice(index, 1);
			},
			ObtainDepartmentalDataFun(){
				
			}
		}
	}
</script>

<style lang="scss" scoped>
	.create-test{
		min-width: 1400px;
	}
	.create-test-header{
		height: 40px;
		display: flex;
		align-items: center;
		font-size: 18px;
	}
	.create-test-TestTime{
		line-height: 35px;
	}
	.KaoShiRenYuan{
		box-sizing:border-box;
		-moz-box-sizing:border-box; /* Firefox */
		-webkit-box-sizing:border-box; /* Safari */
		padding: 5px;
	}
	.renyuanzhanshi{
		position: fixed;
		top: 0px;
		left: 0px;
		width: 100%;
		height: 100%;
		background-color: rgba(0,0,0,0.7);
		text-align: center;
	}
	.renyuanzhanshi-content{
		margin: 100px auto 30px;
		width: 60%;
		height: 60%;
		background-color: #FFFFFF;
		border-radius: 10px;
		padding: 10px;
		overflow-y: scroll;
	}
	.renyuanzhanshi-hide{
		width: 100%;
		height: auto;
	}
</style>