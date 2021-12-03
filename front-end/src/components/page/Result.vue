<template>
	<div>
		<div class="container">
			<div class="handle-box">
				<el-button type="primary" class="handle-del mr10">阈值： 0.5</el-button>
			</div>
			<el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
				<el-table-column prop="id" label="企业唯一标识"></el-table-column>
				<el-table-column prop="oplocdistrict" label="行政区划代码"></el-table-column>
				<el-table-column prop="industryphy" label="行业类别代码"></el-table-column>
				<el-table-column prop="industryco" label="行业细类代码"></el-table-column>
				<el-table-column prop="dom" label="经营地址"></el-table-column>
				<el-table-column prop="opscope" label="经营范围"></el-table-column>
				<el-table-column prop="enttype" label="企业类型"></el-table-column>
				<el-table-column prop="enttypeitem" label="企业类型小类"></el-table-column>
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
		fetchData
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
				pageTotal: 0
			};
		},
		created() {
			this.getData();
		},
		methods: {
			getData() {
				fetchData(this.query).then(res => {
					console.log(res);
					this.tableData = res.data;
					this.pageTotal = res.pageTotal;
				});
			},
			handlePageChange(val) {
				this.$set(this.query, 'pageIndex', val);
				this.getData();
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
</style>
