<template>
	<div>
		<el-col :span="16">
			<el-row :gutter="20" class="mgb20">
				<el-col :span="8">
					<el-card shadow="hover" :body-style="{padding: '0px'}">
						<div class="grid-content grid-con-1">
							<i class="el-icon-sunny grid-con-icon"></i>
							<div class="grid-cont-right">
								<div class="grid-num">{{dashinfo.running}}</div>
								<div>运行中</div>
							</div>
						</div>
					</el-card>
				</el-col>
				<el-col :span="8">
					<el-card shadow="hover" :body-style="{padding: '0px'}">
						<div class="grid-content grid-con-2">
							<i class="el-icon-data-analysis grid-con-icon"></i>
							<div class="grid-cont-right">
								<div class="grid-num">{{dashinfo.train_lines}}</div>
								<div>训练集数据</div>
							</div>
						</div>
					</el-card>
				</el-col>
				<el-col :span="8">
					<el-card shadow="hover" :body-style="{padding: '0px'}">
						<div class="grid-content grid-con-3">
							<i class="el-icon-timer grid-con-icon"></i>
							<div class="grid-cont-right">
								<div class="grid-num">{{dashinfo.forecast_times}}</div>
								<div>预测次数</div>
							</div>
						</div>
					</el-card>
				</el-col>
			</el-row>
			<el-card shadow="hover">
				<div slot="header" class="clearfix">
					<span>上传预测数据</span>
				</div>
				<div style="width: 100%;height: 562px;">
					<el-form ref="form" :model="param" label-width="100px">
						<el-form-item prop="base_info" label="基础信息">
							<el-input v-model="param.base_info" placeholder="基础信息">
							</el-input>
						</el-form-item>
						<el-form-item prop="annual_report_info" label="年度报告信息">
							<el-input v-model="param.annual_report_info" placeholder="年度报告信息">
							</el-input>
						</el-form-item>
						<el-form-item prop="tax_info" label="税务信息">
							<el-input v-model="param.tax_info" placeholder="税务信息">
							</el-input>
						</el-form-item>
						<el-form-item prop="change_info" label="改变信息">
							<el-input v-model="param.change_info" placeholder="改变信息">
							</el-input>
						</el-form-item>
						<el-form-item prop="news_info" label="新闻">
							<el-input v-model="param.news_info" placeholder="新闻">
							</el-input>
						</el-form-item>
						<el-form-item prop="other_info" label="其他信息">
							<el-input v-model="param.other_info" placeholder="其他信息">
							</el-input>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click="onSubmit">提交</el-button>
							<el-button @click="resetForm()">重置</el-button>
						</el-form-item>
					</el-form>
				</div>
			</el-card>
		</el-col>
		<el-col :span="8">
				<el-card shadow="hover" class="mgb20">
					<div class="schart-box">
						<schart class="schart" canvasId="bar" :options="options1" style="margin-left: 15px;"></schart>
					</div>
				</el-card>
			<el-card style="margin-top: 10px;" shadow="hover">
				<div class="mapDataDiv">
					<div style="font-size: 20px;">企业地区分布</div>
					<div id="map" style="width: 450px;height: 350px;"></div>
				</div>
			</el-card>
		</el-col>
	</div>
</template>

<script>
	import {
		upForcastData,getdash
	} from '../../api/index';
	import Schart from 'vue-schart';
	import echarts from 'echarts'
	import "echarts/map/js/china.js";
	export default {
		name: 'dashboard',
		data() {
			return {
				name: localStorage.getItem('ms_username'),
				options1: {
					type: 'bar',
					title: {
						text: '正负样本分布'
					},
					bgColor: '#fbfbfb',
					labels: [''],
					datasets: [{
							label: '正',
							fillColor: 'rgba(241, 49, 74, 0.5)',
							data: [0]
						},
						{
							label: '负',
							data: [0]
						}
					]
				},
				param: {
					base_info: '',
					annual_report_info: '',
					tax_info: '',
					change_info: '',
					news_info: '',
					other_info: ''
				},
				dashinfo: {
					forecast_times: '',
					running: '',
					train_lines: ''

				},
				series:{series: [{
					name: '数据',
					type: 'map',
					mapType: 'china',
					top: '5%',
					//roam: true, //是否需要缩放地图
					label: {
						normal: {
							show: false //省份名称
						},
						emphasis: {
							show: false
						}
					},
					itemStyle: {
						normal: {
							borderColor: 'rgba(0, 0, 0, 0.3)', //区域描边颜色
							color: 'rgba(0, 0, 0,0)', //区域名字颜色，这里是透明
							areaColor: 'rgba(0, 0, 0,0)', //区域颜色 透明
						},
					},
					emphasis: {
						itemStyle: {
							areaColor: 'rgba(111, 151, 242,0.3)', //高亮区域的颜色
						},
					},
					data: ''
				}]}
				
			};
		},
		components: {
			Schart
		},
		computed: {

		},
		methods: {
			resetForm() {
				this.$refs['form'].resetFields()
			},
			onSubmit() {
				upForcastData(this.param).then((res) => {
					if (res.code === 200) {
						this.$message.success('上传成功');
						this.options1.datasets[0].data = [res.data.p_and_n_proportion.positive]
						this.options1.datasets[1].data = [res.data.p_and_n_proportion.negative]
						this.series.series[0].data = res.data.area_distribution
						this.map()
						return true;
					} else {
						this.$message.error(res.msg);
						return false;
					}
				}).catch(err => {
					this.$message.error(err.msg);
					return false;
				})
			},
			map() {
				let map = echarts.init(document.getElementById('map'));
				// 绘制图表
				map.setOption(this.series);
			}
		},
		mounted() {
			this.map();
		},
		created(){
			getdash().then(res=>{
				this.dashinfo=res.data
			})
		}
	};
</script>


<style scoped>
	.el-row {
		margin-bottom: 20px;
	}

	.grid-content {
		display: flex;
		align-items: center;
		height: 100px;
	}

	.grid-cont-right {
		flex: 1;
		text-align: center;
		font-size: 14px;
		color: #999;
	}

	.grid-num {
		font-size: 30px;
		font-weight: bold;
	}

	.grid-con-icon {
		font-size: 50px;
		width: 100px;
		height: 100px;
		text-align: center;
		line-height: 100px;
		color: #fff;
	}

	.grid-con-1 .grid-con-icon {
		background: rgb(45, 140, 240);
	}

	.grid-con-1 .grid-num {
		color: rgb(45, 140, 240);
	}

	.grid-con-2 .grid-con-icon {
		background: rgb(100, 213, 114);
	}

	.grid-con-2 .grid-num {
		color: rgb(45, 140, 240);
	}

	.grid-con-3 .grid-con-icon {
		background: rgb(242, 94, 67);
	}

	.grid-con-3 .grid-num {
		color: rgb(242, 94, 67);
	}

	.schart-box {
		display: inline-block;
	}

	.schart {
		width: 470px;
		height: 300px;
	}
</style>
