<template>
	<div class="">
		<el-card shadow="hover" :body-style="{padding: '20px'}" class="card">
			<el-form ref="form" class="form" label-width="100px">
				<el-form-item prop="file" style=" margin-right: 40px;">
					<div class="crop-demo-btn">{{name}}
						<input class="crop-input" type="file" accept="application/msexcel" @change="setFile" />
					</div>
				</el-form-item>
				<el-form-item prop="remarks" label="训练集备注">
					<el-input v-model="remarks" placeholder="训练集备注" class="reinput">
					</el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="onSubmit" style="margin-right: 230px;">上传</el-button>
					<el-button type="primary" @click="" style="margin-right: 230px;">下载训练集</el-button>
					<el-button type="primary" @click="">开始训练</el-button>
				</el-form-item>
			</el-form>
		</el-card>
		<el-card shadow="hover" :body-style="{padding: '20px'}" style="margin-top: 20px;">
			<el-col :span="18">
				<el-row>
					<el-col :span="12">
						<div class="schart-box">
							<schart class="schart" canvasId="bar" :options="options1"></schart>
						</div>
					</el-col>
					<el-col :span="12">
						<div class="mapDataDiv">
							<div style="font-size: 20px;">企业地区分布</div>
							<div id="map" style="width: 450px;height: 350px;"></div>
						</div>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="8">
						<div class="schart-box">
							<schart class="pie" canvasId="pie1" :options="pie1"></schart>
						</div>
					</el-col>
					<el-col :span="8">
						<div class="schart-box">
							<schart class="pie" canvasId="pie2" :options="pie2"></schart>
						</div>
					</el-col>
					<el-col :span="8">
						<div class="schart-box">
							<schart class="pie" canvasId="pie3" :options="pie3"></schart>
						</div>
					</el-col>
				</el-row>
			</el-col>
			<el-col :span="6">
				<div id="myChart" :style="{width: '390px', height: '300px'}"></div>
			</el-col>
		</el-card>
	</div>
</template>

<script>
	import {
		upload,
		getpie
	} from '../../api/index';
	import Schart from 'vue-schart';
	import echarts from 'echarts'
	import "echarts/map/js/china.js";
	export default {
		data() {
			return {
				file: '',
				remarks: '',
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
				name: '选择训练集',
				series: {
					series: [{
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
					}]
				},
				pie1: {
					type: 'pie',
					title: {
						text: '企业类型'
					},
					legend: {
						position: 'left'
					},
					bgColor: '#fbfbfb',
					labels: '',
					datasets: []
				},
				pie2: {
					type: 'pie',
					title: {
						text: '企业行业'
					},
					legend: {
						position: 'left'
					},
					bgColor: '#fbfbfb',
					labels: '',
					datasets: []
				},
				pie3: {
					type: 'pie',
					title: {
						text: '经营方式'
					},
					legend: {
						position: 'left'
					},
					bgColor: '#fbfbfb',
					labels: '',
					datasets: [],

				}
			}
		},
		components: {
			Schart
		},
		methods: {
			setFile(e) {
				let formData = new FormData()
				formData.append('train', e.target.files[0])
				this.file = formData
				this.name = formData.get('train').name
			},
			onSubmit() {
				this.file.append('remarks', this.remarks)
				upload(this.file).then((res) => {
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
			},
			drawLine() {
				// 基于准备好的dom，初始化echarts实例
				let myChart = echarts.init(document.getElementById('myChart'))
				// 绘制图表
				myChart.setOption({
					title: {
						text: '验证得分：',
						left: 'center', //  标题居中的代码
						top: 20 //项目要求标题距离顶部有间距，可以直接用数字也可用百分比。
					},
					tooltip: {},
					xAxis: {
						type: 'value',
						show: false,
						boundaryGap: ['5%', '5%'],
					},
					yAxis: {
						type: 'category', //增加这一行代码，即可柱状图横置
						data: ["f1_score"],
						axisTick: {
							show: false
						},
						axisLine: {
							show: false,
						},
						axisLabel: {
							inside: false,
							textStyle: {
								color: '#000',
								fontSize: '20',
								itemSize: ''
							}
						},
						max: 6
					},
					series: [{
						name: '验证得分',
						type: 'bar',
						data: [ 0.1343141345],
						label: {
							show: true,
							formatter: '{c}%', //显示数据带上百分比
							position: 'right' //控制数据显示位置，‘’right‘’为显示在柱状图右侧
						},
						barWidth: 30,
						barCategoryGap:'50%',
					}],
					grid: {
						containLabel: true //设置自适应画布大小状态为开，也可通过设置left左移实现相同效果。
					},
				});
			}
		},
		mounted() {
			this.map();
			this.drawLine();
		},
		created() {
			getpie().then(res => {
				this.pie1.labels = res.data[0].labels
				this.pie1.datasets[0] = res.data[0].datasets
				this.pie2.labels = res.data[1].labels
				this.pie2.datasets[0] = res.data[1].datasets
				this.pie3.labels = res.data[2].labels
				this.pie3.datasets[0] = res.data[2].datasets
			})
		}
	}
</script>

<style lang="scss">
	.crop-demo-btn {
		position: relative;
		width: 110px;
		height: 40px;
		line-height: 40px;
		padding: 0 20px;
		margin-left: 30px;
		background-color: #409eff;
		color: #fff;
		font-size: 14px;
		border-radius: 4px;
		box-sizing: border-box;
	}

	.crop-input {
		position: absolute;
		width: 100px;
		height: 40px;
		left: 0;
		top: 0;
		opacity: 0;
		cursor: pointer;
	}

	.card {
		display: flex;
		justify-items: center;
		align-items: center;
		height: 60px;
		vertical-align: middle;

		.form {
			margin-top: 15px;
			display: flex;
			width: 100%;
			justify-items: center;
			align-items: center;
			vertical-align: middle;

			.input {
				width: 200px;
			}
		}
	}

	.schart-box {
		display: inline-block;
	}

	.schart {
		width: 470px;
		height: 350px;
	}

	.pie {
		width: 380px;
		height: 300px;
	}
</style>
