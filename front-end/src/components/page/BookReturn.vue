<template>
	<div>
		<div class="container">
			<el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
				<el-table-column prop="isbn" label="isbn"></el-table-column>
				<el-table-column prop="author" label="author"></el-table-column>
				<el-table-column prop="title" label="title"></el-table-column>
				<el-table-column prop="publisher" label="publisher"></el-table-column>
				<el-table-column prop="booktype" label="booktype"></el-table-column>
				<el-table-column fixed="right" label="tool" width="70">
					<template slot-scope="scope">
						<el-button @click="handleClick(scope.row)" type="text" size="small">return</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div class="pagination">
				<el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
					:page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
			</div>
		</div>
	</div>
</template>

<script>
	import {
		getBorrow,
		returnBook
	} from '../../api/index';
	export default {
		name: 'basetable',
		data() {
			return {
				query: {
					pageIndex: 1,
					pageSize: 10
				},
				tableData: [],
				pageTotal: 1,
				user: {
					accountnumber: localStorage.getItem('ms_username')
				},
				borrowinfo:{
					isbn:''
				}
			};
		},
		created() {
			this.getData();
		},
		methods: {
			getData() {
				getBorrow(this.user).then(res => {
					this.tableData = res.data
					this.pageTotal = res.pageTotal;
				});
			},
			handlePageChange(val) {
				this.$set(this.query, 'pageIndex', val);
				this.getData();
			},
			handleClick(val){
				this.borrowinfo.isbn=val.isbn
				returnBook(this.borrowinfo).then(res => {
					console.log(res)
					if(res.code===200){
						this.$message.success("returned");
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
</style>
