<template>
	<div class="ExaminationPaper">
		<div class="ExaPap-SingleChoice" v-if="CurrentData.QuestionTypes == '0'">
			<div class="ExaPap-SingleChoice-stem">
				<span>{{QuestionNumber+1}}.</span><span>{{CurrentData.stem}} (单选题)</span>
			</div>
			<div v-if="CurrentData.subjectItems">
				<mt-radio title="" v-model="RadioVal" :options="CurrentData.subjectItems"  @change="SingleChoiceFun"></mt-radio>
			</div>
		</div>
		<div class="ExaPap-MultipleChoice" v-else-if="CurrentData.QuestionTypes == '1'">
			<div class="ExaPap-MultipleChoice-stem">
				<span>{{QuestionNumber+1}}.</span><span>{{CurrentData.stem}} (多选题)</span>
			</div>
			<div v-if="CurrentData.subjectItems">
				<mt-checklist title="复选框列表" v-model="ChecklistVal" :options="CurrentData.subjectItems"  @change="MultipleChoiceFun"> </mt-checklist>
			</div>
		</div>
		<div class="ExaminationPaper-switchover">
			<div>
				<mt-button size="normal" type="primary" @click="ShangYitiFun" v-if="QuestionNumber != 0">上一题</mt-button>
			</div>
			<div>
				<mt-button size="normal" type="primary" @click="XiaYitIFun" v-if="QuestionNumber+1 != ItemList.length">下一题</mt-button>
				<mt-button size="normal" type="primary" @click="SubmitPaper" v-else>提交试卷</mt-button>
			</div>
		</div>
		<!--<div class="ExaPap-Checking">
		</div>-->
	</div>
</template>

<script>
	import axios from 'axios'
	import { Checklist,Toast } from 'mint-ui';
	export default{
		name:"ExaminationPaper",
		data(){
			return{
				OptionNumber:["A","B","C","D","E","F","G"],
				CurrentData:{},
				QuestionNumber:0,
				RadioVal:"",
				ChecklistVal:[],
				ItemList:[],
				StartTime:"",
				examId:"",
				paperId:"",
				UserId:""
			}
		},
		mounted:function(){
			let CurrentTime = new Date()
			this.StartTime =  Math.ceil(CurrentTime.getTime()/1000) ;
			this.examId = this.$route.params.examId;
			this.paperId = this.$route.params.TestQuestions.paperId;
			this.ChecklistFun(this.$route.params);
			this.UserId = sessionStorage.getItem("myid");
		},
		methods:{
			ChecklistFun:function(ChecklistData){
				let ArrayOne;
				let ArrayTow=[];
				let JsonStr = JSON.stringify(ChecklistData);
    			let JsonStrOne = JsonStr.replace(/subjectContent/g, 'stem');
    			let JsonStrTow = JsonStrOne.replace(/optionContent/g, 'label');
    			let JsonStrThree = JsonStrTow.replace(/optionOrder/g, 'value');
    			ArrayOne=JSON.parse(JsonStrThree);
				if (ArrayOne.TestQuestions.singleSubjects && ArrayOne.TestQuestions.singleSubjects.length>0) {
					for (let i=0;i<ArrayOne.TestQuestions.singleSubjects.length;i++) {
						ArrayOne.TestQuestions.singleSubjects[i].QuestionTypes = "0";
						ArrayOne.TestQuestions.singleSubjects[i].RadioVal = "";
						ArrayTow.push(ArrayOne.TestQuestions.singleSubjects[i]);
					}
				};
				if (ArrayOne.TestQuestions.multiSubjects && ArrayOne.TestQuestions.multiSubjects.length > 0) {
					for (let i=0;i<ArrayOne.TestQuestions.multiSubjects.length;i++) {
						ArrayOne.TestQuestions.multiSubjects[i].QuestionTypes = "1";
						ArrayOne.TestQuestions.multiSubjects[i].ChecklistVal = [];
						ArrayTow.push(ArrayOne.TestQuestions.multiSubjects[i]);
					}
				};
				this.ItemList = ArrayTow;
				for (let i=0;i<ArrayTow[0].subjectItems.length;i++) {
					ArrayTow[0].subjectItems[i].value = ArrayTow[0].subjectItems[i].value.toString();
				}
				this.CurrentData = ArrayTow[0];
				this.QuestionNumber = 0;
			},
			ShangYitiFun(){
				if (this.QuestionNumber>0) {
					this.QuestionNumber--;
					for (let i=0;i<this.ItemList[this.QuestionNumber].subjectItems.length;i++) {
						this.ItemList[this.QuestionNumber].subjectItems[i].value = this.ItemList[this.QuestionNumber].subjectItems[i].value.toString();
					}
					this.CurrentData = this.ItemList[this.QuestionNumber];
					if(this.ItemList[this.QuestionNumber].QuestionTypes == "0"){
						this.RadioVal = this.ItemList[this.QuestionNumber].RadioVal;
					}else if (this.ItemList[this.QuestionNumber].QuestionTypes =="1") {
						this.ChecklistVal = this.ItemList[this.QuestionNumber].ChecklistVal;
					}
				}else{
					Toast('当前为第一题');
				};
			},
			XiaYitIFun(){
				if(this.ItemList[this.QuestionNumber].QuestionTypes == "0"){
					if (this.RadioVal =="") {
						Toast('请选择当前题目答案！');
						return false;
					}
				}else if (this.ItemList[this.QuestionNumber].QuestionTypes =="1") {
					if (this.ChecklistVal.length == 0) {
						Toast('请选择当前题目答案！');
						return false;
					}
				};
				if (this.QuestionNumber < this.ItemList.length) {
					this.QuestionNumber++;
					for (let i=0;i<this.ItemList[this.QuestionNumber].subjectItems.length;i++) {
						this.ItemList[this.QuestionNumber].subjectItems[i].value = this.ItemList[this.QuestionNumber].subjectItems[i].value.toString();
					}
					this.CurrentData = this.ItemList[this.QuestionNumber];
					if(this.ItemList[this.QuestionNumber].QuestionTypes == "0"){
						this.RadioVal = this.ItemList[this.QuestionNumber].RadioVal;
					}
					if (this.ItemList[this.QuestionNumber].QuestionTypes == "1") {
						this.ChecklistVal = this.ItemList[this.QuestionNumber].ChecklistVal;
					}
				}else{
					Toast('当前为最后一题，请提交试卷！');
				};
			},
			SingleChoiceFun(value){
				this.ItemList[this.QuestionNumber].RadioVal = value;
			},
			MultipleChoiceFun(value){
				this.ItemList[this.QuestionNumber].ChecklistVal = value;
			},
			SubmitPaper(){
				let _self = this;
				if(this.ItemList[this.QuestionNumber].QuestionTypes == "0"){
					if (this.RadioVal =="") {
						Toast('请选择当前题目答案！');
						return false;
					}
				}else if (this.ItemList[this.QuestionNumber].QuestionTypes =="1") {
					if (this.ChecklistVal.length == 0) {
						Toast('请选择当前题目答案！');
						return false;
					}
				};
				let answer=[];
				for (let i=0;i<this.ItemList.length;i++) {
					let Arr = this.ItemList[i];
					if (Arr.QuestionTypes == "0") {
						let NewJson = {
							"subjectId":Arr.id,
							"submitAnswer":parseInt(Arr.RadioVal) 
						};
						answer.push(NewJson);
					}else if (Arr.QuestionTypes == "1") {
						let multiCheckAnswer=0;
						for (let j=0;j<Arr.ChecklistVal.length;j++) {
							multiCheckAnswer |=parseInt(Arr.ChecklistVal[j]);
						}
						let NewJson = {
							"subjectId":Arr.id,
							"submitAnswer":multiCheckAnswer
						};
						answer.push(NewJson);
					}
				}
				let CurrentTime = new Date();
				let UpData={
					"beginTime":this.StartTime,
					"endTime": Math.ceil(CurrentTime.getTime()/1000),
					"examPaperId":this.paperId,
					"ownerId":this.UserId,
					"answers":answer
				};
				axios.put('api/paper-manage/submitExamPaper',UpData)
				.then(res=> {
					if(res.status==200){
						if(res.data){
							if (res.data.isPass == 3) {
								Toast('恭喜通过！');
								_self.$router.push({
									path:'/',
									name:'Index',
									params:{
										id : _self.username
									}
								}) 
							}else{
								Toast('很遗憾您未通过本次测试！');
								_self.$router.push({
									path:'/',
									name:'Index',
									params:{
										id : _self.username
									}
								})
							}
						}								
					}
				})
			}
		}
	}
</script>

<style>
	.ExaminationPaper{
		padding: 10px;
	}
	.ExaPap-SingleChoice-stem{
		display: flex;
		justify-content: flex-start;
	}
	.ExaPap-SingleChoice-stem>span{
		font-size: 16px;
		color: #666;
	}
	.ExaPap-SingleChoice-stem>span:first-child{
		position: relative;
		top: 4px;
	}
	.ExaPap-SingleChoice-OptionList{
		margin-top: 10px;
		display: flex;
		justify-content: flex-start;
	}
	.mint-radiolist-label{
		display: flex;
		justify-content: flex-start;
		color: #777;
		font-size: 14px;
	}
	.ExaminationPaper-switchover{
		padding: 0px  10px;
		display: flex;
		justify-content: space-between;
	}
</style>