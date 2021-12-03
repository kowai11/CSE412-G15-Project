<template>
	<div class="login-wrap">
		<div class="ms-login">
			<div class="ms-title">Library Manament System</div>
			<el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
				<el-form-item prop="accountNumber">
					<el-input v-model="param.accountNumber" placeholder="username">
						<el-button slot="prepend" icon="el-icon-lx-people"></el-button>
					</el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input type="password" placeholder="password" v-model="param.password"
						@keyup.enter.native="submitForm()">
						<el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
					</el-input>
				</el-form-item>
				<div class="login-btn">
					<el-button type="primary" @click="submitForm()">Login</el-button>
				</div>
			</el-form>
		</div>
	</div>
</template>

<script>
	import {
		login
	} from '../../api/index';
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
						message: 'Please input username',
						trigger: 'blur'
					}],
					password: [{
						required: true,
						message: 'Please input password',
						trigger: 'blur'
					}],
				},
			};
		},
		methods: {
			submitForm() {
				this.$refs.login.validate(valid => {
					if (valid) {
						login(this.param).then((res) => {
							if (res.code === 200) {
								this.$message.success('success');
								localStorage.setItem('ms_username', this.param.accountNumber);
								this.$router.push('/');
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
						this.$message.error('Please input username and password');
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
