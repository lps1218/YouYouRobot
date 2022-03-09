<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="名称"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getUsers(filters.name,1)">查询</el-button>
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
			<el-table-column prop="sid" label="编号" width="100" sortable>
			</el-table-column>
			<el-table-column prop="sname" label="名称" width="100" sortable>
			</el-table-column>
			<el-table-column prop="introduce" label="介绍" width="100" sortable>
			</el-table-column>
			<el-table-column prop="recommend" label="推荐" width="100" sortable>
				<template slot-scope="scope">
					 {{ scope.row.recommend === 1 ? '推荐' : '不推荐' }}
				</template>
			</el-table-column>
			<el-table-column prop="picture" label="图片" width="100" sortable>
			</el-table-column>
			<el-table-column prop="createp" label="创建人" min-width="110" sortable>
			</el-table-column>
			<el-table-column prop="createt" label="创建时间" min-width="120" sortable>
			</el-table-column>
			<el-table-column label="操作" width="150">
				<template scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.row.sid)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove()" :disabled="this.sels.length===0">批量删除</el-button>
			 <el-pagination
			    layout="prev, pager, next"
				@current-change="handleCurrentChange"
			    :total="totals"
				:page-size="5"
				 style="float:right;">
			  </el-pagination>
			</el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="名称" prop="sname">
					<el-input v-model="editForm.sname" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="推荐">
					<el-radio-group v-model="editForm.recommend">
						<el-radio class="recommend" :label="1">推荐</el-radio>
						<el-radio class="recommend" :label="0">不推荐</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="图片" prop="picture">
					<el-upload
					  class="upload-demo"
					  action="https://jsonplaceholder.typicode.com/posts/"
					  :on-preview="handlePreview"
					  :on-remove="handleRemove"
					  :before-remove="beforeRemove"
					  multiple
					  :limit="3"
					  :on-exceed="handleExceed"
					  :file-list="fileList">
					  <el-button size="small" type="primary">点击上传</el-button>
					  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
					</el-upload>
				</el-form-item>
				<el-form-item label="介绍">
					<el-input type="textarea" v-model="editForm.introduce"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--新增界面-->
		<el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
				<el-form-item label="名称" prop="name">
					<el-input v-model="addForm.sname" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="推荐">
					<el-radio-group v-model="addForm.recommend">
						<el-radio class="radio" :label="1">推荐</el-radio>
						<el-radio class="radio" :label="0">不推荐</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="图片" prop="picture">
					<el-upload
					  class="upload-demo"
					  action="https://jsonplaceholder.typicode.com/posts/"
					  :on-preview="handlePreview"
					  :on-remove="handleRemove"
					  :before-remove="beforeRemove"
					  multiple
					  :limit="3"
					  :on-exceed="handleExceed"
					  :file-list="fileList">
					  <el-button size="small" type="primary">点击上传</el-button>
					  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
					</el-upload>
				</el-form-item>
				<el-form-item label="介绍">
					<el-input type="textarea" v-model="addForm.introduce"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
	import axios from 'axios'
	import moment from 'moment'
	import util from '../../common/js/util'
	import path from '../../common/js/path'
	//import NProgress from 'nprogress'
	import { getUserListPage, removeUser, batchRemoveUser, editUser, addUser } from '../../api/api';


	export default {
		data() {
			return {
				totals: 0,
				fileList: [],
				filters: {
					name: ''
				},
				users: [],
				total: 0,
				page: 1,
				listLoading: false,
				sels: [],//列表选中列

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					name: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					]
				},
				//编辑界面数据
				editForm: {
					sid: 0,
					sname: '',
					recommend: 0,
					picture: '',
					introduce: ''
				},

				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					sname: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					]
				},
				//新增界面数据
				addForm: {
					sid: 0,
					sname: '',
					recommend: 0,
					picture: '',
					introduce: ''
				}

			}
		},
		mounted(){
			this.getUsers(this.filters.name,1);
		},
		methods: {
			handleRemove(file, fileList) {
			        console.log(file, fileList);
			      },
			      handlePreview(file) {
			        console.log(file);
			      },
			      handleExceed(files, fileList) {
			        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
			      },
			      beforeRemove(file, fileList) {
			        return this.$confirm(`确定移除 ${ file.name }？`);
			      },
				  handleCurrentChange(val){
					  this.getUsers(this.filters.name,val)
				  },
			//获取用户列表
			getUsers : function(name,index) {
				this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/paging?")+'name='+name+'&index='+index).then(response => {
						   this.totals = response.data.data.count;
						   this.users = response.data.data.t;
					  }, response => {
						  console.log("error");
					  });
			},
			//删除
			handleDel: function (row) {
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				})
				this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/deleteSubject?")+'sid='+row).then(response => {
							   if(response.data.msg==10000){
								   console.log("成功")
								   this.getUsers(this.filters.name,1);
							   }else{
								   console.log("失败")
							   }
						  }, response => {
							  console.log("error");
						  });
			},
			//显示编辑界面
			handleEdit: function (index, row) {
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
			},
			//显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
				this.addForm = {
					sid: 0,
					sname: '',
					recommend: 0,
					picture: '',
					introduce: ''
				};
			},
			//编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.editForm);
							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');

							this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/revamp?")+'sid='+this.editForm.sid+'&sname='+this.editForm.sname+'&recommend='+this.editForm.recommend+'&picture='+this.editForm.picture+'&introduce='+this.editForm.introduce).then(response => {
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
								this.getUsers(this.filters.name,1);
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
							
							this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/addSubject?")+'sid='+this.addForm.sid+'&sname='+this.addForm.sname+'&recommend='+this.addForm.recommend+'&picture='+this.addForm.picture+'&introduce='+this.addForm.introduce).then(response => {
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
								this.getUsers(this.filters.name,1);
							});
						});
					}
				});
			},
			selsChange: function (sels) {
				this.sels = sels;
			},
			//批量删除
			batchRemove: function () {
				var ids = this.sels.map(item => item.sid).toString();
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				})
				this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/deleteBatchSubject?")+'ids='+ids).then(response => {
							   if(response.data.msg==10000){
								  this.$message({
								  	message: '删除成功',
								  	type: 'success'
								  });
								   this.getUsers(this.filters.name,1);
							   }else if(response.data.msg==10002){
								   this.$message({
								   	message: '删除失败',
								   	type: 'success'
								   });
							   }else{
								   this.$message({
								   	message: '未完全删除',
								   	type: 'success'
								   });
								   this.getUsers(this.filters.name,1);
							   }
						  }, response => {
							  console.log("error");
						  });
			}
		}
	}

</script>

<style scoped>
	
</style>