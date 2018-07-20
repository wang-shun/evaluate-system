<template>
	<div class="Login">
		<div class="Login-content">
			<mt-field label="" placeholder="请输入用户名" v-model="username"></mt-field>
			<mt-field label="" placeholder="请输入密码" type="password" v-model="password"></mt-field>
			<mt-button @click.native="handleClick">登录</mt-button>
		</div>
	</div>
</template>

<script>
	import axios from 'axios';
	import { Toast } from 'mint-ui';
	export default {
		name:"Login",
		data(){
			return{
				username:"",
				password:""
			}
		},
		methods:{
			handleClick:function(){
					this.submitForm();
			},
			submitForm:function(){
				let _self = this;
				let postData = {
						"roleType" : "2",
						"id" : _self.username,
						"pwd" : _self.password
					}
				axios.post('/api/account-manage/accessvalidat',postData)
			    .then(response=> {
					if(response.data.code == "0000"){
						sessionStorage.setItem("myid", _self.username);
						_self.$router.push({
							path:'/',
							name:'Index',
							params:{
								id : _self.username
							}
						})  
					}else{
						Toast("登录名或密码错误！")
					}
			    })
			    .catch(function (error) {
			    	Toast("登陆失败。请稍后再试！")
			    })
			}
		}
	}
</script>

<style type="text/css">
	.Login{
		position: absolute;
		width: 100%;
		height: 100%;
		background-image: url(../../Image/Login/LoginBj.jpg);
		background-size: 100% 100%;
		display: flex;
		justify-content: center;
	}
	.Login-content{
		margin-top: 50%;
		width: 60%;
		height: 50px;
	}
	.mint-cell{
		background-color: rgba(255,255,255,0);
	}
	.mint-cell-value>input{
		width: 100%;
		background-color: rgba(255,255,255,0);
		border-bottom: 1px solid #EEEEEE;
		color: #757575;
	}
	.mint-button{
		margin-top: 20px;
		width: 100%;
		height: 35px;
		background-color: #26A2FF;
		color: #F0F0F0;
	}
</style>