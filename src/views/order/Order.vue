<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true">
				<el-form-item>
					<el-input v-model="organization" placeholder="机构名称"></el-input>
				</el-form-item>
					<el-select v-model="statsValue" placeholder="请选择">
					    <el-option
					      v-for="item in options"
					      :key="item.value"
					      :label="item.label"
					      :value="item.value">
					    </el-option>
					</el-select>
				<el-form-item>
					<el-button type="primary" v-on:click="selOrder();">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="orderVO" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" prop="oid" width="55">
			</el-table-column>
			<el-table-column prop="oid" label="编号" width="90" sortable>
			</el-table-column>
			<el-table-column prop="subjectpojo.sname" label="课程名称" width="120">
			</el-table-column>
			<el-table-column prop="teacherpojo.tname" label="老师名称" width="100">
			</el-table-column>
			<el-table-column prop="userpojo.organization" label="机构名称" width="100">
			</el-table-column>
			<el-table-column prop="teacherpojo.tphone" label="手机号" width="125">
			</el-table-column>
			<el-table-column prop="stats" label="订单状态" min-width="100" :formatter="formatStats">
			</el-table-column>
			<el-table-column prop="detail" label="订单详情" min-width="100" :show-overflow-tooltip="true">
			</el-table-column>
			<el-table-column prop="modifydate" label="订单时间" min-width="180">
			</el-table-column>
			<el-table-column label="操作" width="220">
				<template scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">审核</el-button>
					<el-button size="small" @click="okStats(scope.$index, scope.row)">完成订单</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length==0">批量删除</el-button>
			<!-- 
			page-size每页数据量
			total总数据量
			他会自己算分多少页
			 -->
			<el-pagination layout="prev, pager, next" 
			@current-change="handleCurrentChange" 
			:page-size="pageOD.size" 
			:total="pageOD.count" 
			style="float:right;">
			</el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog
		  title="" 
		  :visible.sync="editFormVisible" width="30%">
		<!-- <el-dialog title="" v-model="editFormVisible" :close-on-click-modal="false" width="30%"> -->
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="任派教师">
					<el-select v-model="value" placeholder="请选择">
					    <el-option
					      v-for="item in userList"
					      :key="item.tid"
					      :label="item.tname"
					      :value="item.tid">
					    </el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>
		
	</section>
</template>

<script>
	import util from '../../common/js/util'
	import path from '../../common/js/path'
	import { getUserListPage, removeUser, batchRemoveUser, editUser, addUser } from '../../api/api';
	import axios from "axios";
	export default {
		data() {
			return {
				organization:"",// 查询条件
				statsValue:"0",// 查询条件状态
				options: [{// 根据订单状态查询
				    value: '0',
				    label: '全部'
				}, {
					value: '1',
				    label: '已派师'
				}, {
				    value: '2',
				    label: '已下单'
				}, {
				    value: '3',
				    label: '已取消'
				}, {
				    value: '4',
				    label: '已完成'
				}],
				listLoading: false,// 列表加载
				sels: "",//列表选中列
				value: '',// 派遣教师
				editFormVisible: false,//编辑界面是否显示
				editLoading: false,//编译加载
				editFormRules: {
					name: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					]
				},
				//编辑界面数据
				editForm: {
					id: 0,
					name: '',
					sex: -1,
					age: 0,
					birth: '',
					addr: ''
				},
				pageOD:{},// 分页对象
				orderVO:[],// 订单数据
				userList:[],//教师数据
				oid:"",// 订单id,派遣教师时用到
			}
		},
		methods: {
			// 订单显示转换
			formatStats: function (row, column) {
				if(row.stats == 1){
					return "已派师";
				}else if(row.stats == 2){
					return "已下单";
				}else if(row.stats == 3){
					return "已取消";
				}else if(row.stats == 4){
					return "已完成";
				}else{
					return "异常";
				}
			},
			// 分页按钮
			handleCurrentChange(val) {
				// 调用分页查询方法
				this.pageOrder(val,this.organization,this.statsValue);
			},
			// 单个删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该订单吗?', '提示', {
					type: 'warning'
				}).then(() => {
					// 删除动画,列表加载
					this.listLoading = true;
					this.$http.post(path.IntelliURLReplaceIP("http://localhost:8081/api/delId"),
					{id:row.oid},
					{emulateJSON:true}).then(res => {
						// 删除成功取消加载
						this.listLoading = false;
						// 删除成功刷新页面重新查询,但还是当前页面
						this.pageOrder(this.pageOD.index,this.organization,this.statsValue);
						// 提示删除成功
						this.$message({
							message: '删除成功',
							type: 'success'
						});
					}, function () {
						this.listLoading = false;
					    // 失败回调
					    alert("删除失败，请检查网络");
					});
				}).catch(() => {
				});
			},
			//显示编辑界面
			handleEdit: function (index, row) {
				// 当订单状态取消或者完成时不可更改订单
				if(row.stats != 3 && row.stats != 4){
					// 把需要修改的id保存
					this.oid = row.oid;
					// 编辑页面显示
					this.editFormVisible = true;
					this.editForm = Object.assign({}, row);
					this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/selUser"),
					{emulateJSON:true})
					.then(res => {
						this.userList = res.body.data;
					});
				}else{
					this.$message({
						message: '该订单已完成 或 已取消',
						type: 'success'
					});
				}
			},
			// 派遣教师
			editSubmit: function () {
				this.$http.post(path.IntelliURLReplaceIP("http://localhost:8081/api/updstats"),
				{oid:this.oid,tid:this.value,stats:1},
				{emulateJSON:true})
				.then(res => {
					// 派遣老师成功刷新页面重新查询,但还是当前页面
					this.pageOrder(this.pageOD.index,this.organization,this.statsValue);
					// 派遣成功后把审核页面隐藏
					this.editLoading = false;
					this.editFormVisible = false;
					// 提示
					this.$message({
						message: '派遣成功',
						type: 'success'
					});
				}, function () {
					this.listLoading = false;
				    // 失败回调
				    alert("派遣失败，请检查网络");
				});
			},
			// 选择多列
			selsChange: function (sels) {
				// 将之前选择的清空
				this.sels = "";
				let thks = this;
				// 选择多列循环出来只要订单oid
				sels.forEach(function(item, index) {
					// 把选中的oid中间以逗号隔开写成一个字符串
					thks.sels += item.oid+",";
				});
			},
			//批量删除
			batchRemove: function () {
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					// 订单列表加载(小动画)
					this.listLoading = true;
					console.log(this.sels);
					this.$http.post(path.IntelliURLReplaceIP("http://localhost:8081/api/delAll"),
					{ids:this.sels},
					{emulateJSON:true})
					.then(res => {
						// 删除成功停止
						this.listLoading = false;
						// 删除成功查询列表
						this.pageOrder(this.pageOD.index,this.organization,this.statsValue);
						this.$message({
							message: '删除成功',
							type: 'success'
						});
					}, function () {
					    // 失败回调
					    alert("删除失败，请检查网络");
					});
				}).catch(() => {
				});
			},
			// 订单分页
			pageOrder:function(index,organization,statsValue){
				this.$http.post(path.IntelliURLReplaceIP("http://localhost:8081/api/pageOrder"),
				{index:index,organization:organization,stats:statsValue},
				{emulateJSON:true})
				.then(res => {
					console.log(res.body.data);
					// 保存分页对象
					this.pageOD = res.body.data;
					// 保存订单集合对象
					this.orderVO = res.body.data.list;
				}, function () {
                    // 失败回调
					this.$message.error('查询失败，请检查网络');
                });
			},
			// 条件查询
			selOrder:function(){
				this.pageOrder(this.pageOD.index,this.organization,this.statsValue);
			},
			okStats:function(index, row){
				console.log(row)
				if(row.stats == 1){
					this.$http.post(path.IntelliURLReplaceIP("http://localhost:8081/api/updstats"),
					{oid:row.oid,tid:0,stats:4},
					{emulateJSON:true})
					.then(res => {
						// 完成订单重新查询刷新页面
						this.pageOrder(this.pageOD.index,this.organization,this.statsValue);
						this.$message({
							message: '订单完成',
							type: 'success'
						});
					});
				}else{
					this.$message({
						message: '只有派师才能完成订单',
						type: 'warning'
					});
				}
			}
		},
		mounted() {
			// 进入页面执行查询
			this.pageOrder(1,"",0);
		}
	}

</script>