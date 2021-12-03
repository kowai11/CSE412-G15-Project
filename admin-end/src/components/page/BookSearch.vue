<template>
	<div>
		<div class="container">
			<div class="handle-box">
				<el-input v-model="title.title" placeholder="Search Content" class="search">
				</el-input>
				<el-button type="primary" class="handle-del mr10" @click="Search()">Search</el-button>
			</div>

			<el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
				<el-table-column prop="isbn" label="isbn"></el-table-column>
				<el-table-column prop="author" label="author"></el-table-column>
				<el-table-column prop="title" label="title"></el-table-column>
				<el-table-column prop="publisher" label="publisher"></el-table-column>
				<el-table-column prop="booktype" label="booktype"></el-table-column>
				<el-table-column prop="numinstock" label="numinstock"></el-table-column>
				<el-table-column fixed="right" label="tool" width="150">
					<template slot-scope="scope">
						<el-button @click="handleClick(scope.row)" type="text" size="small">detail</el-button>
						<el-button @click="edit(scope.row)" type="text" size="small">edit</el-button>
						<el-button @click="deletebook(scope.row)" type="text" size="small">delete</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div style="margin-top: 20px">
				<el-button @click="add()">Add</el-button>
			</div>
			<div class="pagination">
				<el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
					:page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
			</div>
		</div>


		<div v-show="popup" @click="closepopup">
			<div class="login">
				<div>
					isbn: {{detailinfo.isbn}}
				</div>
				<div>
					author: {{detailinfo.author}}
				</div>
				<div>
					title: {{detailinfo.title}}
				</div>
				<div>
					publisher: {{detailinfo.publisher}}
				</div>
				<div>
					booktype: {{detailinfo.booktype}}
				</div>
				<div>
					numinstock: {{detailinfo.numinstock}}
				</div>
				<div class="comment">
					<div v-for="item in items" :key="item.id">
						{{ item }}
					</div>
				</div>
			</div>
			<div class="over"></div>
		</div>

		<div v-show="addpopup">
			<div class="login">
				<el-form :model="param" :rules="rules" ref="add" label-width="0px" class="ms-content">
					<el-form-item prop="isbn">
						<el-input v-model="param.isbn" placeholder="isbn"></el-input>
					</el-form-item>
					<el-form-item prop="author">
						<el-input v-model="param.author" placeholder="author"></el-input>
					</el-form-item>
					<el-form-item prop="title">
						<el-input v-model="param.title" placeholder="title"></el-input>
					</el-form-item>
					<el-form-item prop="publisher">
						<el-input v-model="param.publisher" placeholder="publisher"></el-input>
					</el-form-item>
					<el-form-item prop="booktype">
						<el-input v-model="param.booktype" placeholder="booktype"></el-input>
					</el-form-item>
					<el-form-item prop="numinstock">
						<el-input v-model="param.numinstock" placeholder="numinstock"></el-input>
					</el-form-item>
					<div class="login-btn">
						<el-button type="primary" @click="submitForm()">Add</el-button>
						<el-button type="primary" @click="addclosepopup()">Cancle</el-button>
					</div>
				</el-form>
			</div>
			<div class="over"></div>
		</div>
		
		<div v-show="editpopup">
			<div class="login">
				<el-form :model="param" :rules="rules" ref="edit" label-width="0px" class="ms-content">
					<el-form-item prop="isbn">
						<el-input v-model="param.isbn" placeholder="isbn"></el-input>
					</el-form-item>
					<el-form-item prop="author">
						<el-input v-model="param.author" placeholder="author"></el-input>
					</el-form-item>
					<el-form-item prop="title">
						<el-input v-model="param.title" placeholder="title"></el-input>
					</el-form-item>
					<el-form-item prop="publisher">
						<el-input v-model="param.publisher" placeholder="publisher"></el-input>
					</el-form-item>
					<el-form-item prop="booktype">
						<el-input v-model="param.booktype" placeholder="booktype"></el-input>
					</el-form-item>
					<el-form-item prop="numinstock">
						<el-input v-model="param.numinstock" placeholder="numinstock"></el-input>
					</el-form-item>
					<div class="login-btn">
						<el-button type="primary" @click="submitForm1()">Confirm</el-button>
						<el-button type="primary" @click="editclosepopup()">Cancle</el-button>
					</div>
				</el-form>
			</div>
			<div class="over"></div>
		</div>

	</div>
</template>

<script>
	import {
		searchBook,
		addBook,
		editBook,
		deleteBook
	} from '../../api/index';
	export default {
		name: 'basetable',
		data() {
			return {
				param: {
					isbn: '',
					author: '',
					title:'',
					publisher:'',
					booktype:'',
					numinstock:''
				},
				rules: {
					isbn: [{
						required: true,
						message: 'Please input isbn',
						trigger: 'blur'
					}],
					author: [{
						required: true,
						message: 'Please input author',
						trigger: 'blur'
					}],
					title: [{
						required: true,
						message: 'Please input title',
						trigger: 'blur'
					}],
					publisher: [{
						required: true,
						message: 'Please input publisher',
						trigger: 'blur'
					}],
					booktype: [{
						required: true,
						message: 'Please input booktype',
						trigger: 'blur'
					}],
					numinstock: [{
						required: true,
						message: 'Please input numinstock',
						trigger: 'blur'
					}],
				},
				title: {
					title: ''
				},
				query: {
					pageIndex: 1,
					pageSize: 10
				},
				tableData: [],
				pageTotal: 1,
				popup: 0,
				addpopup: 0,
				editpopup: 0,
				detailinfo: {},
				borrowinfo: {
					borrowtime: '',
					returntime: '',
					isbn: ''
				},
				deleteinfo:{
					isbn:''
				}
			};
		},
		created() {
			this.Search();
		},
		methods: {
			submitForm() {
				this.$refs.add.validate(valid => {
					if (valid) {
						addBook(this.param).then((res) => {
							if (res.code === 200) {
								this.$message.success('success');
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
						this.$message.error('Please input complete infomation');
						return false;
					}
				});
			},
			submitForm1() {
				this.$refs.add.validate(valid => {
					if (valid) {
						editBook(this.param).then((res) => {
							if (res.code === 200) {
								this.$message.success('success');
								return true;
							} else {
								this.$message.error("fail");
								return false;
							}
						}).catch(err => {
							this.$message.error("fail");
							return false;
						})
					} else {
						this.$message.error('Please input complete infomation');
						return false;
					}
				});
			},
			add(){
				this.addshowpopup()
			},
			edit(data){
				this.param=data
				this.editshowpopup()
			},
			deletebook(data){
				this.deleteinfo.isbn=data.isbn
				deleteBook(this.deleteinfo).then(res=>{
					if (res.code === 200) {
						this.$message.success('success');
						return true;
					} else {
						this.$message.error("fail");
						return false;
					}
				})
			},
			handlePageChange(val) {
				this.$set(this.query, 'pageIndex', val);
				this.getData();
			},
			Search() {
				searchBook(this.title).then(res => {
					console.log(res);
					this.tableData = res.data;
					this.pageTotal = res.pageTotal;
				});
			},
			showpopup() {
				this.popup = 1;
			},
			closepopup() {
				this.popup = 0;
			},
			addshowpopup() {
				this.addpopup = 1;
			},
			addclosepopup() {
				this.addpopup = 0;
			},
			editshowpopup() {
				this.editpopup = 1;
			},
			editclosepopup() {
				this.editpopup = 0;
			},
			handleClick(data) {
				this.detailinfo = data
				//getcomment
				this.showpopup()
			}
		}
	};
</script>

<style scoped>
	.handle-box {
		margin-bottom: 20px;
	}

	.handle-select {
		width: 120px;
	}

	.handle-input {
		width: 300px;
		display: inline-block;
	}

	.table {
		width: 100%;
		font-size: 14px;
	}

	.red {
		color: #ff0000;
	}

	.mr10 {
		margin-right: 10px;
	}

	.table-td-thumb {
		display: block;
		margin: auto;
		width: 40px;
		height: 40px;
	}

	.search {
		width: 90%;
	}

	.rule {
		position: absolute;
		width: 0.82rem;
		height: 0.36rem;
		top: 0.08rem;
		right: 0rem;
		background: #111111;
	}

	.login {
		padding: 10px;
		position: fixed;
		font-size: 24px;
		height: 500px;
		width: 400px;
		background-color: white;
		border-radius: 0.25rem;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -50%);
		z-index: 1000;
	}

	.over {
		position: fixed;
		width: 100%;
		height: 100%;
		opacity: 0.7;
		filter: alpha(opacity=70);
		top: 0;
		left: 0;
		z-index: 999;
		background-color: #111111;
	}
</style>
