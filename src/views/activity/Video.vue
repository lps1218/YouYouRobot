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
			<el-table-column type="selection" width="55"></el-table-column></div>
			<el-table-column align="center" prop="rid" label="编号" width="180"></el-table-column>
			<el-table-column align="center"  prop="rtype" label="资源地址"  width="180">
				<template slot-scope="scope">
					<div v-if="scope.row.rtype==1">
						<!-- 图片 -->
						<img width="50" v-image-preview height="50" :src="petImage(scope.$index,scope.row)"/>
					</div>
					<div  v-if="scope.row.rtype==2">
						
						<video width="100" @click="dialogVisible = true" height="50">
							<source :src="petVideo(scope.$index,scope.row)"></source>
						</video>
						<!-- 视频弹框 -->
						<!-- <el-dialog
						  title="提示"
						  :visible.sync="dialogVisible"
						  width="30%"
						  :before-close="handleClose">
						  <span class="home">
							  <video-player class="video-player vjs-custom-skin"
							                    ref="videoPlayer"
							                    :playsinline="true"
							                    :options="playerOptions"
							                    @play="onPlayerPlay($event)"
							                    @pause="onPlayerPause($event)"
							                    @ended="onPlayerEnded($event)"
							                    @waiting="onPlayerWaiting($event)"
							                    @playing="onPlayerPlaying($event)"
							                    @loadeddata="onPlayerLoadeddata($event)"
							                    @timeupdate="onPlayerTimeupdate($event)"
							                    @canplay="onPlayerCanplay($event)"
							                    @canplaythrough="onPlayerCanplaythrough($event)"
							                    @statechanged="playerStateChanged($event)"
							                    @ready="playerReadied"
							      >
							      </video-player>
						  </span>
						  <span slot="footer" class="dialog-footer">
						    <el-button @click="dialogVisible = false">取 消</el-button>
						    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
						  </span>
						</el-dialog> -->
					</div>
					<div v-if="scope.row.rtype==3">
						<!-- 海报 -->
						<img width="50" v-image-preview height="50" :src="petImage(scope.$index,scope.row)"/>
					</div>
				</template>
			</el-table-column>
			<el-table-column align="center" prop="name" label="名称"  width="180"></el-table-column>
			<el-table-column align="center" prop="rtype" label="类型" :formatter="forrtype"  width="180"></el-table-column>
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
				      :page-size="5"
				      layout="prev, pager, next, jumper"
				      :total="info.count">
				    </el-pagination>
		</el-col>

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
			                action="string"
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
	import axios from 'axios'
	import path from '../../common/js/path'
	export default {
		data() {
			return {
				
				//文件属性
				user: {},
				fileList: [],
				formData: "",
				multiple: true,
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
				url:require("../../assets/wu.png"),
				
				//playerOptions.sources['src']=''
				//视频播放属性
				/* playerOptions: {
				    playbackRates: [0.5, 1.0, 1.5, 2.0, 3.0], // 可选的播放速度
				    autoplay: false, // 如果为true,浏览器准备好时开始回放。
				    muted: false, // 默认情况下将会消除任何音频。
				    loop: false, // 是否视频一结束就重新开始。
				    preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
				    language: 'zh-CN',
				    aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
				    fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
				    sources: [{
				        type: "video/mp4", // 类型
				        src: '' // url地址
				    }],
				    poster: '', // 封面地址
				    notSupportedMessage: '此视频暂无法播放，请稍后再试', // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
				    controlBar: {
				    timeDivider: true, // 当前时间和持续时间的分隔符
				    durationDisplay: true, // 显示持续时间
				    remainingTimeDisplay: false, // 是否显示剩余时间功能
				    fullscreenToggle: true // 是否显示全屏按钮
					}
				}, */
				//视频弹框
				// dialogVisible: false
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
			//图片路径
			petImage(index, row) {
				var url="";
				if(row.picurl==null||row.picurl==""){
					//显示无图片
					url=this.url;
				}else{
					url = "http://localhost:8081/static/images/uplaod/"+row.picurl
				}
				console.log(url)
				return url
			},
			 handleClose(done) {
			        this.$confirm('确认关闭？')
			          .then(_ => {
			            done();
			          })
			          .catch(_ => {});
			      },
			//视频播放插件
			// 播放回调
			     onPlayerPlay(player) {
			        console.log('player play!', player)
			      },
			      // 暂停回调
			      onPlayerPause(player) {
			        console.log('player pause!', player)
			      },
			      // 视频播完回调
			      onPlayerEnded($event) {
			        console.log(player)
			      },
			      // DOM元素上的readyState更改导致播放停止
			      onPlayerWaiting($event) {
			        console.log(player)
			      },
			      // 已开始播放回调
			      onPlayerPlaying($event) {
			        console.log(player)
			      },
			      // 当播放器在当前播放位置下载数据时触发
			      onPlayerLoadeddata($event) {
			        console.log(player)
			      },
			      // 当前播放位置发生变化时触发。
			      onPlayerTimeupdate($event) {
			        console.log(player)
			      },
			      //媒体的readyState为HAVE_FUTURE_DATA或更高
			      onPlayerCanplay(player) {
			        // console.log('player Canplay!', player)
			      },
			      //媒体的readyState为HAVE_ENOUGH_DATA或更高。这意味着可以在不缓冲的情况下播放整个媒体文件。
			      onPlayerCanplaythrough(player) {
			        // console.log('player Canplaythrough!', player)
			      },
			      //播放状态改变回调
			      playerStateChanged(playerCurrentState) {
			        console.log('player current update state', playerCurrentState)
			      },
			      //将侦听器绑定到组件的就绪状态。与事件监听器的不同之处在于，如果ready事件已经发生，它将立即触发该函数。。
			      playerReadied(player) {
			        console.log('example player 1 readied', player);
			      },
			//视频路径
			petVideo(index, row){	
				
				var url="";
				if(row.picurl==null||row.picurl==""){
					//显示无
					url="http://localhost:8081/static/images/uplaod/video.mp4";
				}else{
				console.log(row.picurl)
				//	console.log("ddddd---"+"http://localhost:8081/static/images/uplaod/"+row.picurl);
					//this.playerOptions.sources[0].src="http://localhost:8081/static/images/uplaod/video.mp4"
					url = "http://localhost:8081/static/images/uplaod/"+row.picurl;
				}
				console.log(url)
				return url
			},
			forrtype:function(row,column){
				if(row.rtype==1){
					return "图片";
				}else if(row.rtype==2){
					return "视频";
				}else{
					return "海报";
				}
			},
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
					  
				     this.$http.post(path.IntelliURLReplaceIP("http://localhost:8081/api/addUser"),formData,{"Content-Type": "multipart/form-data;charset=utf-8"})
				        .then(response => {
				        	   // console.log(response.data);
							   //将弹框v-model绑定的值设定为false进行取消
							   this.addFormVisible = false;
				        	   this.$message({
				        	   	message: '上传成功',
				        	   	type: 'success'
				        	   });
							   //清空
							   this.fileList=[];
							   this.theme="";
				        	   this.getUsers();
				          }, response => {
				        	  console.log("error");
				          });	
				    },
			//分页
			getUsers(){
				this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/resource?")+'name='+this.name+'&rtype='+this.rtype+'&index='+this.num).then(response => {
						  
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
					this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/delete?")+'rid='+row.rid).then(response => {
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
					this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/deleteAll?")+'delId='+para.ids).then(response => {
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
/* .home {
    width: 1000px;
    height: 800px;
    margin: 0 auto;
    text-align: center;
  } */
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