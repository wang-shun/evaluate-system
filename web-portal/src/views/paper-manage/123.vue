<template>
	<div class="create-test">
		<el-row>
			<el-col :span="24">
				<div class="create-test-header">
					<el-breadcrumb separator="/">
						<el-breadcrumb-item :to="{ path: '/' }"> 首页 </el-breadcrumb-item>
						<el-breadcrumb-item> <a href="/"> 试卷管理 </a> </el-breadcrumb-item>
						<el-breadcrumb-item> 创建试卷 </el-breadcrumb-item>
					</el-breadcrumb>
				</div>
			</el-col>
		</el-row>
		<el-row>
			<el-col :span="20">
				<div class="grid-content bg-purple-dark">
					<el-form :model="CreateTestFormData" :rules="CreateTestRules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
						<el-form-item label="试卷名称" prop="name">
							<el-input v-model="CreateTestFormData.name"> </el-input>
						</el-form-item>
						<el-form-item label="选择题库" prop="region">
							<el-select v-model="CreateTestFormData.region" placeholder="选择题库">
								<el-option v-for="Item in TiKuList" :label="Item.subjectDbName" :value="Item.subjectDbDesc"> </el-option>
							</el-select>
						</el-form-item>
						<el-form-item label="考试时间" required>
							<el-col :span="11">
								<el-form-item prop="StartTime">
									<el-date-picker type="datetime" placeholder="选择开始日期" v-model="CreateTestFormData.StartTime" style="width: 100%;"></el-date-picker>
								</el-form-item>
							</el-col>
							<el-col class="line" :span="2"> - </el-col>
							<el-col :span="11">
								<el-form-item prop="EndTime">
									<el-date-picker type="datetime" placeholder="选择结束时间" v-model="CreateTestFormData.EndTime" style="width: 100%;"></el-date-picker>
								</el-form-item>
							</el-col>
						</el-form-item>
						<el-row>
							<el-col :span="11">
								<el-form-item label="考试时长" prop="TestTime">
									<el-input v-model.number="CreateTestFormData.TestTime"> </el-input>
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
									<el-form-item :label="'试题题型'+(index+1)" >
										<el-select v-model="Item.questiontopic" :placeholder="'试题题型'+(index+1)" style="width: 100%;">
											<el-option label="单选" value="shanghai"> </el-option>
											<el-option label="多选" value="shanghai"> </el-option>
											<el-option label="判断" value="beijing"> </el-option>
										</el-select>
									</el-form-item>
								</el-col>
								<el-col :span="6">
									<el-form-item label="试题数量">
										<el-input v-model.number="Item.ItemNumber"> </el-input>
									</el-form-item>
								</el-col>
								<el-col :span="6">
									<el-form-item label="每题分数">
										<el-input v-model.number="Item.ItemScore"> </el-input>
									</el-form-item>
								</el-col>
								<el-col :span="2" v-if="index == 0">
									&nbsp;&nbsp;&nbsp;&nbsp;<el-button type="primary" @click="AddTopicFun">添加</el-button>
								</el-col>
								<el-col :span="2" v-else>
									&nbsp;&nbsp;&nbsp;&nbsp;<el-button type="danger" icon="el-icon-delete" circle @click="DeleteTopicFun(index)"></el-button>
								</el-col>
							</el-row>
						</div>
						<el-form-item label="试卷说明" prop="desc">
							<el-input type="textarea" v-model="CreateTestFormData.desc"></el-input>
						</el-form-item>
						<el-form-item label="选择部门">
							<el-row>
								<el-col :span="4">
									<el-select v-model="DepartmentLevelId" placeholder="选择部门" @change="DepartmentLeveFun">
										<el-option v-for="(item,index) in DepartmentLeveList" :key="index" :label="item.colName" :value="index" ></el-option>
									</el-select>
								</el-col>
								<el-col :span="6" class="create-test-TestTime">
									&nbsp;&nbsp;&nbsp;&nbsp;
									<el-button type="primary" @click="DepartmentLevelDel">重新选择</el-button>
								</el-col>
							</el-row>
						</el-form-item>
						<el-form-item label="考试人员">
							<el-row>
								<el-col :span="18">
									<el-row>
										<el-col :span="6" v-for="item in checkedCities">
											<el-alert :title="item" type="info"> </el-alert>
										</el-col>	
									</el-row>
								</el-col>
								<el-col :span="6" class="create-test-TestTime">
									&nbsp;&nbsp;&nbsp;&nbsp;
									<el-button type="primary" @click="AddKaoShengFun">添加考生</el-button>
									<el-button type="primary" @click="DepartmentLevelDel">重新选择</el-button>
								</el-col>
							</el-row>
						</el-form-item>

						<el-form-item>
							<el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
							<!--<el-button @click="resetForm('ruleForm')">重置</el-button>-->
						</el-form-item>
					</el-form>
				</div>
			</el-col>
		</el-row>
		<div class="KaoShengList" v-show="KaoShengListShow">
			<div class="KaoShengList-content">
				<div class="KaoShengList-content-hide">
					<el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
					<div style="margin: 15px 0;"></div>
					<el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange">
						<el-checkbox v-for="city in YunGongList" :label="city" :key="city">{{city.colAccountName}}</el-checkbox>
					</el-checkbox-group>
				</div>
			</div>
			<div class="KaoShengList-Add">
				<el-button type="primary" @click="KaoShengListAddFun">添加</el-button>
				<el-button type="warning"  @click="KaoShengListAddFun">取消</el-button>
			</div>
		</div>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				YunGongList:[],
				KaoShengListShow:false,
				checkAll: false,
		        checkedCities: [],
		        isIndeterminate: true,
				CreateTestFormData:{
					name: '',
					region: '',
					StartTime: '',
					EndTime: '',
					TestTime:120,
					delivery: true,
					DepartmentLeveList:[],
					desc: '',
					domains:[{ questiontopic: '',ItemNumber:"",ItemScore:""}],
				},
				CreateTestRules: {
					name: [{required: true,message: '试卷名称',trigger: 'blur'},
						{min: 10,max: 50,message: '长度在 10 到 50 个字符',trigger: 'blur'}],
					region: [{required: true,message: '请选择题库',trigger: 'change'}],
					TestTime:[{ required: true, message: '考试时长不能为空'},
						{ type: 'number', message: '年龄必须为数字值'}],
					StartTime: [{type: 'date',required: true,message: '请选择日期',trigger: 'change'}],
					EndTime: [{type: 'date',required: true,message: '请选择时间',trigger: 'change'}],
					type: [{type: 'array',required: true,message: '请至少选择一个活动性质',trigger: 'change'}],
					desc: [{required: false,message: '试卷说明',trigger: 'blur'}]
				},
				DepartmentLevelId:"",
				DepartmentLeveList:[],
				TiKuList:[{
					subjectDbName: "eeee",
					isOpen: "eee",
					subjectDbDesc: "eee"
		        },{
					subjectDbName: "eeee",
					isOpen: "eee",
					subjectDbDesc: "eee"
		        }]
			};
		},
		mounted:function(){
			this.TiKuListFun();
			this.BuMenListFun(100);
		},
		methods: {
			AddTopicFun() {
				if (this.CreateTestFormData.domains.length<4) {
					let NewJson = { questiontopic: '',ItemNumber:"",ItemScore:""};
					this.CreateTestFormData.domains.push(NewJson);
				}
			},
			DeleteTopicFun(index) {
				this.CreateTestFormData.domains.splice(index, 1);
			},
			DepartmentLeveFun(index) {
				if (this.DepartmentLeveList[index].colParentId == -1) {
					this.BuMenYuanGongList(this.DepartmentLeveList[index].id);
				}else{
					this.BuMenListFun(this.DepartmentLeveList[index].id);
				}
			},
			DepartmentLevelDel(){
				this.BuMenListFun(100);
			},
			TiKuListFun(){
				axios.get('api/subject-manage/queryAllsubjectDb',{})
			    .then(res=> {
			        console.log(1)
			        console.log(res);
			        if(res.status==200){
			        	console.log('查询成功');
			        	this.TiKuList=res.data;
			        }
			    })
			    .catch(function (error) {
			    	console.log(error);
			    });
			},
			BuMenListFun(InparentId){
				let _self = this;
				axios.post('api/account-manage/department',{"parentId":InparentId})
			    .then(res=> {
			        console.log(1)
			        console.log(res);
			        if(res.status==200){
			        	console.log(res.data.data.sonDepartmentInfos);
			        	_self.DepartmentLeveList=res.data.data.sonDepartmentInfos;
			        	if(res.data.data.sonDepartmentInfos[0].colParentId == -1){
			        		_self.BuMenYuanGongList(res.data.data.sonDepartmentInfos[0].id);
			        	}
			        }
			    })
			    .catch(function (error) {
			    	console.log(error);
			    });
			},
			BuMenYuanGongList(BuMenId){
				console.log(BuMenId)
				axios.get('api/account-manage/department/'+BuMenId)
			    .then(res=> {
			        console.log(1)
			        console.log(res);
			        	console.log(res.data.data.accountInfos.length);
			        	if(res.data.data.accountInfos.length > 0){
			        		this.YunGongList=res.data.data.accountInfos;
			        		this.KaoShengListShow = true;  
			        	}
			    })
			    .catch(function (error) {
			    	console.log(error);
			    });
			},
			AddKaoShengFun(){
				this.KaoShengListShow = true;  
			},
			handleCheckAllChange(val) {
				this.checkedCities = val ? this.YunGongList : [];
				this.isIndeterminate = false;
			},
			handleCheckedCitiesChange(value) {
				let checkedCount = value.length;
				this.checkAll = checkedCount === this.cities.length;
				this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
				console.log(this.checkedCities) 
			},
			KaoShengListAddFun(){
				this.KaoShengListShow = false;  
			},
			submitForm(formName) {
				alert(typeof this.CreateTestFormData.TestTime);
				alert(this.CreateTestFormData.TestTime);
				this.$refs[formName].validate((valid) => {
					if (valid) {
						alert(this.CreateTestFormData);
					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},
			resetForm(formName) {
				this.$refs[formName].resetFields();
			}
		}
	}
</script>

<style lang="scss" scoped>
	.create-test{
		min-width: 1300px;
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
	.create {
		background-color:#fff;
		margin:20px 0;
		padding:20px 10px
	}
	.KaoShengList{
		position: fixed;
		top: 0px;
		left: 0px;
		width: 100%;
		height: 100%;
		background-color: rgba(0,0,0,0.6);
	}
	.KaoShengList-content{
		margin: 100px auto 20px;
		width: 60%;
		height: 60%;
		box-sizing: content-box;
		padding: 10px;
		background-color: #FFFFFF;
		overflow-x: hidden;
		overflow-y:scroll;
	}
	.KaoShengList-content-hide{
		width: 100%;
		height: auto;
	}
	.KaoShengList-Add{
		text-align: center;
	}
	.lastStep {
		margin-top:40px
	}
	.line{
		text-align: center;
	}
	.AddTopic{
		display: flex;
		justify-content: flex-start;
		flex-wrap: wrap;
	}
</style>