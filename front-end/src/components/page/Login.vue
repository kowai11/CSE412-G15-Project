<template>
	<div class="login-wrap">
		<div class="ms-login">
			<div class="ms-title">非法集资预警平台</div>
			<el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
				<el-form-item prop="accountNumber">
					<el-input v-model="param.accountNumber" placeholder="用户名">
						<el-button slot="prepend" icon="el-icon-lx-people"></el-button>
					</el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input type="password" placeholder="密码" v-model="param.password"
						@keyup.enter.native="submitForm()">
						<el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
					</el-input>
				</el-form-item>
				<div class="login-btn">
					<el-button type="primary" @click="submitForm()">登录</el-button>
				</div>
			</el-form>
		</div>
	</div>
</template>

<script>
	import {
		login
	} from '../../api/index';
	import {
		sha256
	} from 'js-sha256';
	export default {
		data: function() {
			return {
				param: {
					accountNumber: '',
					password: '',
				},
				rules: {
					accountNumber: [{
						required: true,
						message: '请输入用户名',
						trigger: 'blur'
					}],
					password: [{
						required: true,
						message: '请输入密码',
						trigger: 'blur'
					}],
				},
			};
		},
		methods: {
			submitForm() {
				this.$refs.login.validate(valid => {
					if (valid) {
						// this.param.password = sha256('this.param.password');
						login(this.param).then((res) => {
							if (res.code === 200) {
								this.$message.success('登录成功');
								localStorage.setItem('ms_username', this.param.username);
								this.$router.push('/403');
								return true;
							} else {
								this.$message.error(res.msg);
								return false;
							}
						}).catch(err => {
							this.$message.error(err.msg);
							return false;
						})
					} else {
						this.$message.error('请输入账号和密码');
						return false;
					}
				});
			}
		}
	};
</script>

<style scoped lang="scss">
	.login-wrap {
		position: relative;
		width: 100%;
		height: 100%;
		background-image: url(../../assets/img/login-bg.jpg);
		background-size: 100%;
	}

	.ms-title {
		width: 100%;
		line-height: 50px;
		text-align: center;
		font-size: 20px;
		color: #fff;
		border-bottom: 1px solid #ddd;
	}

	.ms-login {
		position: absolute;
		left: 50%;
		top: 50%;
		width: 350px;
		margin: -190px 0 0 -175px;
		border-radius: 5px;
		background: rgba(255, 255, 255, 0.3);
		overflow: hidden;
	}

	.ms-content {
		padding: 30px 30px;
	}

	.login-btn {
		text-align: center;
	}

	.login-btn button {
		width: 100%;
		height: 36px;
		margin-bottom: 10px;
	}

	.login-tips {
		font-size: 12px;
		line-height: 30px;
		color: #fff;
	}

	.captcha-block {
		.captcha-input {
			width: 58%;
			justify-content: flex-start;
		}

		.captcha-img {
			height: 30px;
			width: 24%;
			vertical-align: middle;
			margin-left: 50px;
		}
	}
</style>
