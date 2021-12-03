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
				<el-table-column fixed="right" label="tool" width="120">
					<template slot-scope="scope">
						<el-button @click="handleClick(scope.row)" type="text" size="small">detail</el-button>
						<el-button @click="borrow(scope.row)" type="text" size="small">borrow</el-button>
					</template>
				</el-table-column>
			</el-table>
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

	</div>
</template>

<script>
	import {
		searchBook,
		borrowBook
	} from '../../api/index';
	export default {
		name: 'basetable',
		data() {
			return {
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
				detailinfo:{},
				borrowinfo:{
					borrowtime:'',
					returntime:'',
					isbn:''
				}
			};
		},
		created() {
			this.Search();
		},
		methods: {
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
			handleClick(data) {
				this.detailinfo=data
				//getcomment
				this.showpopup()
			},
			borrow(data){
				var aData = new Date();
				this.borrowinfo.borrowtime=aData.getFullYear() + "-" + aData.getMonth()  + "-" + aData.getDate();
				this.borrowinfo.returntime=aData.getFullYear() + "-" + (aData.getMonth() + 1) + "-" + aData.getDate();
				this.borrowinfo.isbn=data.isbn
				borrowBook(this.borrowinfo).then(res => {
					console.log(res)
					if(res.code===200){
						this.$message.success("borrowed");
						this.Search()
					}
					else{
						this.$message.error("error");
					}
				})
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
