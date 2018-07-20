<template>
	<div class="index">
		<mt-header title="润和集团考试评测系统">
			<!--<mt-button icon="more" slot="right" @click="popupMyFun"></mt-button>-->
		</mt-header>
		<ul class="index-list">
			<li>
				<router-link :to="{ path: 'Exam' }">
					<i class="icon iconfont hoperun-kaoshizhongxinoff indexFooter-icon"></i>
					<br>
					<span class="indexFooter-text">我的考试</span>
				</router-link>
			</li>
			<li>
				<router-link :to="{ path: 'Performance' }">
					<i class="icon iconfont hoperun-lishijilu  indexFooter-icon"></i>
					<br>
					<span class="indexFooter-text">成绩查询</span>
				</router-link>
			</li>
		</ul>
		<div id="indexPopup" class="index-popup" v-show="indexPopupShow"  @click="popupMyFun">
			<mt-button type="default" class="mint-button--large" @click.stop>退出</mt-button>
		</div>
	</div>
</template>

<script>
	import { Header, Button } from 'mint-ui';
	export default {
		name:"Index",
		data(){
			return{
				indexPopupShow:false,

			}
		},
		mounted:function(){
		},
		methods:{
			popupMyFun:function(){
				if(this.indexPopupShow){
					this.indexPopupShow = false;
				}else{
					this.indexPopupShow = true;
				}
			},
			useAction:function(){
	            let item = {"name":"000008121"};
	            store.dispatch('setUserList',item);
		   }
		},
		MyTestFun(){
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
</script>

<style>
	.index-list{
		display: flex;
		justify-content: flex-start;
		flex-wrap: wrap;
	}
	.index-list>li{
		width: 50%;
		max-width: 200px;
		text-align: center;
	}
	.index-list>li{
		padding: 10px 0px;
		box-sizing: border-box;
		border-bottom: 1px solid #eee;
	}
	.index-list>li:nth-child(even){
		border-left: 1px solid #eee;
	}
	.indexFooter-icon{
		font-size: 70px;
		color: #444  ;
	}
	.indexFooter-text{
		font-size: 16px;
	}
	.index-popup{
		position: fixed;
		top: 40px;
		right: 0px;
		width: 100%;
		height: calc(100% - 40px);
		background-color: rgba(255,255,255,1);
	}
	.mint-button--large{
		margin: 40% auto 0px;
		display: block;
		width: 90%;
		background-color: #26a2ff;
		color: #fff;
	}
</style>