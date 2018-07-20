<template>
	<div class="home">
		<el-container>
			<!-- 侧边栏 -->
			<el-aside width="200px">
				<div class="logo"><img src="../assets/logo.png" /></div>
				<el-menu :router="true" class="el-menu-admin" @open="handleOpen" @close="handleClose" background-color="rgba(0,0,0,0)" text-color="#fff" active-text-color="#ffd04b">
					<el-submenu index="1">
						<template slot="title">
							<i class="el-icon-location"></i>
							<span> 题库管理 </span>
						</template>
						<el-menu-item index="/tklist">
							<i class="el-icon-menu"> </i>
							<span slot="title"> 题库管理 </span>
						</el-menu-item>
						<el-menu-item index="/testlist">
							<i class="el-icon-menu"> </i>
							<span slot="title"> 试题列表 </span>
						</el-menu-item>
					</el-submenu>
					<el-submenu index="2">
						<template slot="title">
							<i class="el-icon-location"> </i>
							<span>试卷管理 </span>
						</template>
						<el-menu-item index="/createtest">
							<i class="el-icon-menu"> </i>
							<span slot="title"> 发布考试 </span>
						</el-menu-item>
						<el-menu-item index="/paperlist">
							<i class="el-icon-menu"> </i>
							<span slot="title"> 考试管理  </span>
						</el-menu-item>
					</el-submenu>
					<!-- <el-submenu index="3">
						<template slot="title">
							<i class="el-icon-location"> </i>
							<span> 统计分析 </span>
						</template>
						<el-menu-item index="3-1">
							<span slot="title"> 试卷分析 </span>
						</el-menu-item>
						<el-menu-item index="3-2">
							<span slot="title"> 考试分析 </span>
						</el-menu-item>
						<el-menu-item index="3-3">
							<span slot="title"> 成绩分析 </span>
						</el-menu-item>
					</el-submenu> -->
				</el-menu>
			</el-aside>
			<el-container>
				<!-- 头部 -->
				<el-header>
					<!--<i class="myicon myicon-menu toggle-btn"> </i>-->
					<div class="system-title"> 考核评测系统 </div>
					<div>
						<!-- {{$store.state.username}},您好 -->
						<span class="welcome"> admin,您好 </span>
						<el-button type="text" @click="logout"> 退出 </el-button>
					</div>
				</el-header>
				<!-- 内容区域 -->
				<el-main><router-view></router-view></el-main>
			</el-container>
		</el-container>
	</div>
</template>
<script>
	export default {
		data() {
			return {};
		},
		methods: {
			handleOpen(key, keyPath) {
				console.log(key, keyPath);
			},
			handleClose(key, keyPath) {
				console.log(key, keyPath);
			},
			logout() { //点击退出按钮
				//清除登录状态
				localStorage.removeItem('mytoken')
				//跳转到登录页面
				this.$router.push({
					name: 'login'
				})
			}
		}
	}
</script>
<style lang="scss" scoped>
	.home {
		height:100%;

	    background: -webkit-linear-gradient(top left, #e2cfc1 , #a2b8c6, #e2cfc1); /* Safari 5.1 - 6.0 */
	    background: -o-linear-gradient(top left, #e2cfc1, #a2b8c6, #e2cfc1); /* Opera 11.1 - 12.0 */
	    background: -moz-linear-gradient(top left, #e2cfc1, #a2b8c6, #e2cfc1); /* Firefox 3.6 - 15 */
	    background: linear-gradient(to top left, #e2cfc1 , #a2b8c6, #e2cfc1); /* 标准的语法（必须放在最后） */
		.el-menu-admin:not(.el-menu--collapse) {
			width:200px;
			min-height:400px;
		}
		.el-menu {
			border-right:0;
		}
		.el-main{
			background-color: #FFFFFF;
		}
		.el-container {
			height:100%;
		}
		.el-header {
			height: 100px !important;
			display:flex;
			justify-content:space-between;
			align-items:center;
		}
		.logo {
			height:100px;
			display: flex;
			justify-content: center;
			align-items: center;
		}
		.logo>img{
			width: 70%;
			height: auto;
		}
		.toggle-btn {
			padding:0 10px;
			margin-left:-20px;
			font-size:36px;
			line-height:60px;
			color:#989898;
			cursor:pointer;
				&:hover {
				background-color:#00635a;
			}
		}
		.system-title {
			font-size:28px;
			color:white;
		}
		.welcome {
			color:white;
		}
	}
</style>