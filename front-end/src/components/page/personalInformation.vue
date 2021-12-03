<template>
	<div>
		<el-card shadow="hover">
			<div style="width: 100%;height: 562px;text-align: center;">
				<div>
					name: {{user.name}}
				</div>
				<div>
					id: {{user.id}}
				</div>
				<div>
					gender: {{user.gender}}
				</div>
				<div>
					accountnumber: {{user.accountnumber}}
				</div>
				<div>
					contactinfo: {{user.contactinfo}}
				</div>
				<div>
					numofbookcanborrow: {{user.numofbookcanborrow}}
				</div>
			</div>
		</el-card>
	</div>
</template>

<script>
	import {
		readerDetail
	} from '../../api/index';
	export default {
		name: 'Personal Information',
		data() {
			return {
				user: {
					id: 11111,
					name: "Paul",
					gender: "M",
					accountnumber: "Paul111",
					contactinfo: "paul@asu.edu",
					numofbookcanborrow: 0
				},
				data: {
					accountnumber: localStorage.getItem('ms_username')
				}
			};
		},
		methods: {
			userinfo() {
				readerDetail(this.data).then(res => {
					console.log(res)
					if (res.code === 200) {
						this.user = res.data
					} else {
						this.$message.error("error");
					}
				})
			}
		},
		created() {
			this.userinfo()
		}
	};
</script>


<style scoped>

</style>
