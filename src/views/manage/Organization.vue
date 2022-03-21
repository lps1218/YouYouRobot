<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" >
				<el-form-item>
					<el-input placeholder="机构名称" v-model="selUname"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="selUser(1,selUname)">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="Userfrom" style="width: 100%;" @selection-change="selsChange">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column prop="uid"  label="编号" sortable>
			</el-table-column>
			<el-table-column prop="uname"  label="用户名" sortable>
			</el-table-column>
			<el-table-column prop="gender" label="性别" sortable>
				<template slot-scope="scope">
					 {{ scope.row.sex === 1 ? '女' : '男' }}
				</template>
			</el-table-column>
			<el-table-column prop="address" label="地址"  sortable>
			</el-table-column>
			<el-table-column prop="organization" label="机构名称" sortable>
			</el-table-column>
			<el-table-column prop="cname" label="联系人姓名"  sortable>
			</el-table-column>
			<el-table-column prop="uphone" label="手机号"  sortable>
			</el-table-column>
			<el-table-column label="操作" width="180">
				<template scope="scope">
					<el-button type="danger" size="small" @click="handleDelete(scope.row)">删除</el-button>
						<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="handleDeleteAll" :disabled="this.sels.length==0">批量删除</el-button>
			<el-pagination layout="prev, pager, next" 
			@current-change="handleCurrentChange" 
			:page-size="pageUser.size" 
			:total="pageUser.count" style="float:right;">
			</el-pagination>
		</el-col>
	   
		<!--编辑界面-->
				<el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
					<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
						<el-form-item label="手机号" prop="phone">
							<el-input v-model="editForm.uphone" auto-complete="off"></el-input>
						</el-form-item>
						<el-form-item label="地址">
							<el-input type="textarea" v-model="editForm.address"></el-input>
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
		//import NProgress from 'nprogress'
		import { getUserListPage, removeUser, batchRemoveUser, editUser, addUser } from '../../api/api';

	export default {
		data() {
			return {
				pageUser:{},
				Userfrom:{},
				selUname:"",
				sels:[],
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
									uphone: '',
									address: ''
								}
				
							}
						},
		methods:{
			//性别显示转换
			formatSex: function (row, column) {
				return row.sex == 1 ? '男' : row.sex == 0 ? '女' : '未知';
			},
			handleCurrentChange(val) {
				this.page = val;
				this.getUsers();
			},
			selUser:function(index,uname){
				this.$http.post(path.IntelliURLReplaceIP("http://localhost:8081/api/getRobotUser"),{index:index,uname:uname},{emulateJSON:true}).then(res => {
					this.pageUser = res.body.data;
					this.Userfrom = res.body.data.list;
					
						// console.log(res.body);
					
				}, response => {
					console.log("error");
				});
			},
			//显示编辑界面
						handleEdit: function (index, row) {
							this.editFormVisible = true;
							this.editForm = Object.assign({}, row);
						},
			//修改
			    editSubmit: function () {
			     				this.$refs.editForm.validate((valid) => {
			     					if (valid) {
			     						this.$confirm('确认提交吗？', '提示', {}).then(() => {
			     							this.editLoading = true;
			     							//NProgress.start();
			     							let para = Object.assign({}, this.editForm);
			     							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
											this.$http.post(path.IntelliURLReplaceIP("http://localhost:8081/api/updateRobotUser"),{uid:this.editForm.uid,uphone:this.editForm.uphone,address:this.editForm.address},{emulateJSON:true}).then(res => {
												if(res.body == 1){
													this.$message({
														message: '提交成功',
														type: 'success'
													});
												}else{
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
			     								this.selUser(1,"");
			     							});
			     						});
			     					}
			     				});
			     			},
			handleCurrentChange(val){
				this.selUser(val,this.selUname);
			},
			handleDelete(row) {
			  this.$confirm("确定删除?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			  })
			  // 确认删除
				.then(() => {
					this.$http.post(path.IntelliURLReplaceIP("http://localhost:8081/api/deleteRobotUser"),{uid:row.uid},{emulateJSON:true}).then(res => {
						
						this.selUser(this.pageUser.index,this.selUname);
						this.$message.error("删除成功");
						
					}, response => {
						console.log("error");
					});
				})
				//取消删除
				.catch(() => {
				  this.$message({
					type: "info",
					message: "已取消删除",
				  });
				});
			},
			selsChange:function(sels){
				this.sels = sels;
			},
			//批量删除
			handleDeleteAll() {
			  this.$confirm("确定批量删除?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			  })
			  // 确认删除
				.then(() => {
					var ids = "";
					this.sels.forEach(function (item, index) {
						ids += item.uid + ",";
					})
					// console.log(ids);
					this.$http.post(path.IntelliURLReplaceIP("http://localhost:8081/api/deleteRobotUserAll"),{ids:ids},{emulateJSON:true}).then(res => {
						
						this.selUser(this.pageUser.index,this.selUname);
						this.$message.error("删除成功");
						
					}, response => {
						console.log("error");
					});
				})
				//取消删除
				.catch(() => {
				  this.$message({
					type: "info",
					message: "已取消删除",
				  });
				});
			},
		},
		mounted() {
			this.selUser(1,"");		
		}
	}

</script>
<style scoped>

</style>