<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="name" placeholder="名称"></el-input>
				</el-form-item>
				<el-select v-model="rtype" clearable placeholder="请选择">
				    <el-option
				      v-for="item in options"
				      :key="item.rtype"
				      :label="item.label"
				      :value="item.rtype">
				    </el-option>
				  </el-select>
				<el-form-item>
					<el-button type="primary" v-on:click="getUsers">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">上传</el-button>
				</el-form-item>
			</el-form>
		</el-col>
		<!-- page -->
		<el-table :data="info.list" border highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%">
			<el-table-column type="selection" width="55"></el-table-column>
		    <el-table-column align="center" prop="rid" label="编号" width="180"></el-table-column>
			<el-table-column align="center" prop="url" label="资源地址"  width="180"></el-table-column>
			<el-table-column align="center" prop="name" label="名称"  width="180"></el-table-column>
			<el-table-column align="center" prop="rtype" label="类型"  width="180"></el-table-column>
		    
			
			<el-table-column align="center" prop="introduce" label="介绍"> </el-table-column>
			<el-table-column label="操作" width="150">
				<template scope="scope">
<!-- 					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
 -->					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table> 
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
			    
				 <el-pagination style="float:right"
				      :current-page.sync="info.current"
					  @current-change="handleCurrentChange"
				      :page-size="10"
				      layout="prev, pager, next, jumper"
				      :total="1000">
				    </el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
			<form>
				
			</form>
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="姓名" prop="name">
					<el-input v-model="editForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别">
					<el-radio-group v-model="editForm.sex">
						<el-radio class="radio" :label="1">男</el-radio>
						<el-radio class="radio" :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="年龄">
					<el-input-number v-model="editForm.age" :min="0" :max="200"></el-input-number>
				</el-form-item>
				<el-form-item label="生日">
					<el-date-picker type="date" placeholder="选择日期" v-model="editForm.birth"></el-date-picker>
				</el-form-item>
				<el-form-item label="地址">
					<el-input type="textarea" v-model="editForm.addr"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--上传界面-->
		<el-dialog title="上传" v-model="addFormVisible" :close-on-click-modal="false">
			<div class="man-container">
			    <div class="el-card">
			      <div class="el-card__header">
			        <slot name="header">
			          <p>文件上传</p>
			        </slot>
			      </div>
			      <div class="el-card__body">
			        <el-row style="margin: 10px 0 0 0">
			          <span>上传类型：</span>
			          <ul style="margin: 10px 0;display: inline-block;">
			              <el-select v-model="theme" placeholder="请选择">
			                <el-option
			                  v-for="item in ioptions"
			                  :key="item.ivalue"
			                  :label="item.ilabel"
			                  :value="item.ivalue"
			                ></el-option>
			              </el-select>
			          </ul>
			        </el-row>
					<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
						
						<el-form-item label="资源名称">
							<el-input v-model="addForm.name" auto-complete="off"></el-input>
						</el-form-item>
						<el-form-item label="资源介绍">
							<el-input type="textarea" v-model="addForm.addr"></el-input>
						</el-form-item> 
					</el-form>
					
			        <el-row style="margin: 0px">
			          <ul style="margin: 0;display: inline-block;">
			            <li>
			              <el-upload
			                class="upload-demo"
			                ref="upload"
			                action="http://localhost:8081/background/addUser"
			                :file-list="fileList"
			                :auto-upload="false"
			                :http-request="uploadFile"
			                :on-change="handleChange"
			                multiple="multiple"
			              >
			                <el-button slot="trigger" size="small" type="primary" @click="delFile">选取文件</el-button>
			                <el-button
			                  style="margin-left: 10px;"
			                  size="small"
			                  type="success"
			                  @click="submitUpload"
			                >上传到服务器</el-button>
			              </el-upload>
			            </li>
			          </ul>
			        </el-row>
			      </div>
			    </div>
			  </div>
			
		</el-dialog>
	</section>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				
				//文件属性
				user: {},
				fileList: [],
				formData: "",
				multiple: true,
				      /* options: [
				        {
				          value: "工作报告",
				          label: "工作报告"
				        },
				        {
				          value: "会议记录",
				          label: "会议记录"
				        }
				      ],
				      theme: "", */
				theme: "",	  
					  
					  
				filters: {
					name: ''
				},
				//list集合
				info:[],
				//根据资源名称查询
				name:'',
				//根据资源类型查询
				rtype:'',
				currentPage1: 5,
				currentPage2: 5,
				currentPage3: 5,
			    currentPage4: 4,
				//当前页
				num:1,
				//总条数:如果没用分页插件就不用传递总条数
				count:2,
				
				options: [{
				     rtype: '1',
				     label: '图片'
				}, {
				     rtype: '2',
				     label: '视频'
				}, {
				     rtype: '3',
				     label: '海报'
				}],
				//上传文件類型
				 ioptions: [{
				    ivalue: '1',
				    ilabel: '图片'
				 }, {
				    ivalue: '2',
				    ilabel: '视频'
				 }, {
				    ivalue: '3',
				    ilabel: '海报'
				 }],
				value: [],
				
				/* //图片文件地址
				imageList: [{
					name: 'food.jpeg', 
					url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
				}, {
					name: 'food2.jpeg', 
					url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
				}],
				//视频文件地址
				videoList: [{
					name: 'food.jpeg', 
					url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
				}, {
					name: 'food2.jpeg', 
					url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
				}], */

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
					id: 0,
					name: '',
					sex: -1,
					age: 0,
					birth: '',
					addr: ''
				},

				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					name: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					]
				},
				//新增界面数据
				addForm: {
					name: '',
					sex: -1,
					age: 0,
					birth: '',
					addr: '',
					file:[]
				},

			}
		},
		mounted() {
			 that = this;
			    let user = window.localStorage.getItem("access-user");
			    if (user) {
			      user = JSON.parse(user);
			      this.user = user;
			    }
			this.getUsers();
		},
		methods: {
			/* 文件方法 */
			delFile() {
			      this.fileList = [];
			    },
			    handleChange(file, fileList) {
			      this.fileList = fileList;
			    },
			    uploadFile(file) {
			      this.formData.append("file", file.file);
			    },
				//提交文件
				submitUpload() {
				      let formData = new FormData();
				      formData.append("rtype", this.theme);
				      formData.append("headPic", this.fileList[0].raw);
					  formData.append("introduce",this.addForm.addr);
					  formData.append("name",this.addForm.name);
					  
				     this.$http.post('http://localhost:8081/api/addUser',formData,{"Content-Type": "multipart/form-data;charset=utf-8"})
				        .then(response => {
				        	   console.log(response.data);
							   //将弹框v-model绑定的值设定为false进行取消
							   this.addFormVisible = false;
				        	   this.$message({
				        	   	message: '上传成功',
				        	   	type: 'success'
				        	   });
				        	   this.getUsers();
				          }, response => {
				        	  console.log("error");
				          });	
				    },
			//分页
			getUsers(){
				this.$http.get('http://localhost:8081/api/resource?name='+this.name+'&rtype='+this.rtype+'&index='+this.num).then(response => {
						   console.log(response.data);
						   this.info = response.body.data;
					  }, response => {
						  console.log("error");
					  });
			},
			handleCurrentChange(val) {
				//当前页
				this.num=val;
				this.getUsers();
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除第'+row.rid+'条记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.$http.get('http://localhost:8081/api/delete?rid='+row.rid).then(response => {
						   console.log(response.data);
						   this.info = response.body.data.list;
						   this.$message({
						   	message: '删除成功',
						   	type: 'success'
						   });
						   this.getUsers();
					  }, response => {
						  console.log("error");
					  });	
					
				}).catch(() => {

				});
			},
			//显示编辑界面
			/* handleEdit: function (index, row) {
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
			}, */
			//显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
				this.addForm = {
					name: '',
					sex: -1,
					age: 0,
					birth: '',
					addr: ''
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
							editUser(para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getUsers();
							});
						});
					}
				});
			},
			//新增
			addSubmit: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						/* let a=document.getElementsByName("file");
						let b=document.getElementsByName("img");
						alert(a)
						alert(b); */
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							//this.addLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.addForm);
							alert("类型:"+this.value+"名称:"+para.name+"介绍:"+para.addr+"图片:"+para.file)
							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							//alert(para.birth);
							addUser(para).then((res) => {
								this.$message({
									message: '上传成功',
									type: 'success'
								 });
								 this.getUsers();
								this.addFormVisible = false;
								/* this.$http.get('http://localhost:8081/background/file?filename='+row.rid).then(response => {
								   console.log(response.data);
								   this.info = response.body.data.list;
								   this.addLoading = false;
								   //NProgress.done();
								   this.$refs['addForm'].resetFields();
								   
								   this.getUsers();
								   this.$message({
										message: '上传成功',
										type: 'success'
								    });
									    this.getUsers();
									}, response => {
									    console.log("error");
								    });	 */
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
				var ids = this.sels.map(item => item.rid).toString();
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					//加载时间 模拟网络延迟
					this.listLoading = true;
					//NProgress.start();
					let para = { ids: ids };
					this.$http.get('http://localhost:8081/api/deleteAll?delId='+para.ids).then(response => {
						   //console.log(response.data);
						   this.listLoading = false;
						   this.$message({
						   	message: '删除成功',
						   	type: 'success'
						   });
						   this.getUsers();
					  }, response => {
						  console.log("error");
					  });	
				}).catch(() => {

				});
			}
		},
		mounted() {
			this.getUsers();
		}
	}

</script>

<style scoped>

</style>
<!-- <template>
  <div>
    <h1>vuex 测试</h1>
    Clicked: {{ getCount }} times
    <button @click="increment">+</button>
    <button @click="decrement">-</button>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { mapActions } from 'vuex'

  export default {
    computed: {
  	// 使用对象展开运算符将 getters 混入 computed 对象中
    ...mapGetters([
      'getCount'
      // ...
    ])
    },
    methods: {
    ...mapActions([
      'increment', // 映射 this.increment() 为 this.$store.dispatch('increment')
      'decrement'
    ])
      //...mapActions({
      //  add: 'increment' // 映射 this.add() 为 this.$store.dispatch('increment')
      //})
    }
  }

</script> -->