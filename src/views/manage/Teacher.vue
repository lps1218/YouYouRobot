<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				
				<el-form-item>
					<el-input v-model="filters.tname" placeholder="姓名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getUser(1,filters.tname)">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
			<el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
				<el-table-column type="selection" width="55">
				</el-table-column>
				<el-table-column prop="tid" label="编号" width="100">
				</el-table-column>
				<el-table-column prop="tname" label="姓名" width="120" sortable>
				</el-table-column>
				<el-table-column prop="tgender" label="性别" width="100" :formatter="formatSex"  sortable>
				</el-table-column>
				<el-table-column prop="age" label="年龄" width="100" sortable>
				</el-table-column>
				<el-table-column prop="tintroduce" label="介绍" width="120" sortable>
				</el-table-column>
				<el-table-column prop="tphone" label="电话" min-width="100" sortable>
				</el-table-column>
				<el-table-column prop="createp" label="创建人" min-width="100" sortable>
				</el-table-column>
				<el-table-column prop="createt" label="创建时间" min-width="180" sortable>
				</el-table-column>
				<el-table-column label="操作" width="150">
					<template scope="scope">
						<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
						<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			
			<!--工具条-->
			<el-col :span="24" class="toolbar">
						 <el-pagination
						    layout="prev, pager, next"
							@current-change="handleCurrentChange"
						    :total="pageTeacher.count"
							:page-size="pageTeacher.size"
							 style="float:right;">
						  </el-pagination>
					</el-col>
		
		<!--新增界面-->
		<el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
				<el-form-item label="姓名" prop="tname">
					<el-input v-model="addForm.tname" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别">
					<el-radio-group v-model="addForm.tgender">
						<el-radio class="radio" :label="1">男</el-radio>
						<el-radio class="radio" :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="年龄">
					<el-input v-model="addForm.age" :min="0" :max="200"></el-input>
				</el-form-item>
				<el-form-item label="电话" prop="tphone">
					<el-input v-model="addForm.tphone" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="介绍">
					<el-input type="textarea" v-model="addForm.tintroduce"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>
		
		<!--编辑界面-->
		<el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="姓名" prop="tname">
					<el-input v-model="editForm.tname" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别">
					<el-radio-group v-model="editForm.tgender">
						<el-radio class="radio" :label="1">男</el-radio>
						<el-radio class="radio" :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="年龄">
					<el-input v-model="editForm.age" :min="0" :max="200"></el-input>
				</el-form-item>
				<el-form-item label="电话" prop="tphone">
					<el-input v-model="editForm.tphone" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="介绍">
					<el-input type="textarea" v-model="editForm.tintroduce"></el-input>
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
	export default {
		data() {
			return {
				totals: 0,
				num: 1,
				filters: {
					tname: '',
					startName: ''
				},
				total: 0,
				tphone:'',
				users: [],
				total: 0,
				page: 1,
				listLoading: false,
				sels: [],//列表选中列
		
				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					tname: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					],
					tphone: [
						{ required: true, message: '请输入手机号', trigger: 'blur' },
						{ min: 7, max: 11, message: '长度在7-11个字符',trigger:'blur'}
					]
				},
				//编辑界面数据
				editForm: {
					tid:0,
					tname: '',
					tgender: -1,
					age: 0,
					tphone: '',
					tintroduce: ''					
				},
		
				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					tname: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					],
					tphone: [
						{ required: true, message: '请输入手机号', trigger: 'blur' },
						{ min: 7, max: 11, message: '长度在7-11个字符',trigger:'blur'}
					]
				},
				//新增界面数据
				addForm: {
					tid:0,
					tname: '',
					tgender: -1,
					age: 0,
					tphone: '',
					tintroduce: ''
				},
				pageTeacher:{},
		
			}
		},
		mounted(){
			this.getUser(1,"");
		},
		methods: {
			//性别显示转换
			formatSex: function (row,column) {
				console.log("1");
				return row.tgender == 1 ? '男' : row.tgender == 0 ? '女' : '未知';
			},
			handleCurrentChange(val) {
				this.num = val;
				this.getUser(val,this.filters.tname)
			},
			//获取用户列表
			getUser: function (index,tname) {
				this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/seletea?")+'index='+index+'&tname='+tname).then(response => {
						  // this.someData = response.body;
						  this.pageTeacher = response.body.data;
						  this.users=response.body.data.list;
					}, response => {
					console.log("error");
				});
			},
			//删除
			handleDel: function (index,row) {
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}),
				this.$confirm('确认提交吗？', '提示', {}).then(() => {
					this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/deletea?")+'tid='+row.tid).then(response => {
						// if(response.data.msg==10000){
							if(row.tid!=null){
							console.log("成功")
							this.getUser(this.num,this.filters.tname);
						}else{
							console.log("失败")
						}
					}, response => {
						console.log("error");
					});
				}, response => {
						});
	
					},
			//显示新增界面	
			handleAdd: function () {
				this.addFormVisible = true;
				this.addForm = {
					tname: '',
					tgender: -1,
					age: 0,
					tphone: '',
					tintroduce: ''
				};
			},
			//显示编辑界面
			handleEdit: function (index, row) {
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
			},
			//编辑
			editSubmit: function (index,row) {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							let para = Object.assign({}, this.editForm);
							this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/updtea?")+'tid='+this.editForm.tid+'&tname='+this.editForm.tname+'&tgender='+this.editForm.tgender+'&age='+this.editForm.age+'&tphone='+this.editForm.tphone+'&tintroduce='+this.editForm.tintroduce).then(response => {
							if(response.data.msg==10000){
								console.log("成功")
								this.$message({
									message: '提交成功',
									type: 'success'
								});
							}else{
								console.log("失败")
								this.$message({
								message: '提交失败',
								type: 'success'
							});
						}
						}, response => {
								console.log("error");
							});
						editUser(para).then((res) => {
							this.editLoading = false;
							//NProgress.done();
							this.$refs['editForm'].resetFields();
							this.editFormVisible = false;
							this.getUser(this.num,this.filters.tname);
										}); 
									});
								}
							});
						},
			//新增
			addSubmit: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.addForm);
							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/insetea?")+'tname='+this.addForm.tname+'&tgender='+this.addForm.tgender+'&age='+this.addForm.age+'&tphone='+this.addForm.tphone+'&tintroduce='+this.addForm.tintroduce).then(response => {
							if(response.data.msg==10000){
								console.log("成功")
								this.$message({
									message: '提交成功',
									type: 'success'
								});
							}else{
								console.log("失败")
								this.$message({
									message: '提交失败',
									type: 'success'
								});
							}
							}, response => {
								console.log("error");
							});
							addUser(para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
								this.getUser(this.num,this.filters.tname);
							});
							});
					}
				});
			},
			selsChange: function (sels) {
				this.sels = sels;
			}
		}
		
	};

</script>

<style>

</style>