<template>
	<div class="Exam">
		<div class="Exam-content-title">
			<div class="Exam-content-name">
				<span>试卷名称</span>
			</div>
			<div class="Exam-content-time">
				<span>考试时间</span>
			</div>
			<div class="Exam-content-duration">
				<span>测试时长</span>
			</div>
			<div class="Exam-content-score">
				<span>考试分数</span>
			</div>
			<div class="Exam-content-start">
				操作
			</div>
		</div>
		<ul id="Exam-list">
			<li class="Exam-content" v-for="(examItme,index) in ExamListData">
				<div class="Exam-content-name">
					<span>{{examItme.colExamName}}</span>
				</div>
				<div class="Exam-content-time">
					<span>{{examItme.colOpenTime}}</span>
					<br>
					<span>{{examItme.colCloseTime}}</span>
				</div>
				<div class="Exam-content-duration">
					<span>{{examItme.colDuration}}</span>
					<span>分钟</span>
				</div>
				<div class="Exam-content-score">
					<span>{{examItme.colPassScore}}</span>
					<span>/</span>
					<span>{{examItme.colTotalScore}}</span>
				</div>
				<div class="Exam-content-start" @click="startTesting(examItme)" style="color: #26A2FF;">
					开始测试
				</div>
			</li>
		</ul>

	</div>
</template>

<script>
	import axios from 'axios'
	import loginVue from '../../../../../web-portal/src/views/login.vue';
	import Comm from '../../../router/commit';
	export default {
		name: 'Exam',
		data() {
			return {
				ExamListData:[]
			}
		},
		created() {
        	this.initList()
        },
		methods: {
			initList () {
				let _self = this;
				let id = sessionStorage.getItem("myid");
				axios.get('api/exam-manage/examaccount/'+id+'/'+0,{})
			    .then(res=> {
					if(res.status==200){
						let CrrArray = res.data.data.infos;
						for (let i=0;i<CrrArray.length;i++) {
							CrrArray[i].colOpenTime = Comm.timestampToTime(CrrArray[i].colOpenTime);
							CrrArray[i].colCloseTime = Comm.timestampToTime(CrrArray[i].colCloseTime);
						}
						_self.ExamListData=CrrArray;
						if (CrrArray.length == 0) {
							Toast('您当前没有可参与的测试！');
							_self.$router.back(-1);
						};
					}
			    })
			},
			startTesting(examItme){
				let myid = sessionStorage.getItem("myid");
				axios.get('api/exam-manage/examstruct/'+examItme.colExamId,{})
				.then(res=> {
					if(res.data.code=="0000"){
						let UpData = {
							examId:examItme.colExamId,
							examStructs:res.data.data.infos,
							paperName:"信息安全考试",
							paperOwnerId:myid,
							subjectDbId:examItme.colSubjectDbId
						};
						console.log(UpData);
						axios.post('api/paper-manage/examPapers',UpData)
						.then(res=> {
							if(res.status==200){
								if(res.data){
									this.$router.push({
										name:"ExaminationPaper", 
										params: {
											TestQuestions : res.data,
											examId:examItme.colExamId,
											paperOwnerId:myid
										}
									});
								}								
							}
						})
					}
			   })
			}
		}
	}
</script>

<style scoped>
	.Exam{
		position: absolute;
		width: 100%;
		height: 100%;
	}
	.Exam-list{
		width: 100%;
		height: calc(100% - 50px);
		position: absolute; left: 0;
	    overflow-x: hidden;
	    overflow-y: scroll;
	    
	}
	.Exam-list::-webkit-scrollbar {
    	display: none;
	}
	.Exam-content-name{
		width: 20%;
		text-align: center;
		overflow: hidden;
		text-overflow: ellipsis;
		display:-webkit-box;
		-webkit-box-orient:vertical; 
		-webkit-line-clamp:3;
		
	}.Exam-content-time{
		width: 22%;
		text-align: center;
	}.Exam-content-duration{
		width: 18%;
		text-align: center;
	}.Exam-content-score{
		width: 19%;
		text-align: center;
	}.Exam-content-start{
		width: 20%;
		text-align: center;
	}
	.Exam-content-title{
		padding: 0 10px;
		height: 50px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		background-color: #eee;
		color: #222;
		font-size: 14px;
		font-weight: 600;
	}
	.Exam-content{
		height: 50px;
		padding: 0 10px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		color: #444;
		font-size: 13px;
		border-top: 1px solid #eeeeee;
	}
</style>