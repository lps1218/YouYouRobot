<template>
  <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px" class="demo-ruleForm login-container">
    <h3 class="title">系统登录</h3>
    <el-form-item prop="account">
      <el-input type="text" v-model="ruleForm2.account" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="checkPass">
      <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
	<!-- <div>
	  <el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox>
	</div> -->
	
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2" :loading="logining">登录</el-button>
	  <slide-verify 
	        :l="30"
	        :r="10"
	        :w="350"
	        :h="155"
	        slider-text="拖动左边滑块完成上方拼图"
	        @success="onSuccess"
	        @fail="onFail">
	      </slide-verify>
      <!-- <el-button @click.native.prevent="handleReset2">重置</el-button> -->
    </el-form-item>
  </el-form>
</template>

<script> 
	import path from '../common/js/path.js'
  import { requestLogin } from '../api/api'
  //import NProgress from 'nprogress'
  export default {
    data() {
      return {
		  stateCode: 0,
        logining: false,
        ruleForm2: {
          account: 'admin',
          checkPass: ''
        },
        rules2: {
          account: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            //{ validator: validaePass }
          ],
          checkPass: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            //{ validator: validaePass2 }
          ]
        },
        checked: true
      };
    },
    methods: {
		 onSuccess(){
					this.stateCode = 1;
		            console.log('滑块验证通过')
		        },
		        onFail(){
					this.stateCode = 0;
		            console.log('滑块验证不通过')
		        },
      handleReset2() {
        this.$refs.ruleForm2.resetFields();
      },
      handleSubmit2(ev) {
        var _this = this;
        this.$refs.ruleForm2.validate((valid) => {
			if (this.stateCode == 1){
				if (valid) {
				  //_this.$router.replace('/table');
				  this.logining = true;
				  //NProgress.start();
				  var loginParams = { username: this.ruleForm2.account, password: this.ruleForm2.checkPass };
				  requestLogin(loginParams).then(data => {
				    this.logining = false;
							  this.$http.get(path.IntelliURLReplaceIP("http://localhost:8081/api/login?")+'userName='+this.ruleForm2.account+'&password='+this.ruleForm2.checkPass).then(response => {
											  if (response.data.errorCode == 0) {
							  					  sessionStorage.setItem('user', JSON.stringify(response.data.data));
							  					  this.$router.push({ path: '/teacher' });
							  				    } else {
							  						 this.$message({
							  						   message: response.data.msg,
							  						   type: 'error'
							  						 });
							  				    }
							  				  });
							  	  }, response => {
							  		  console.log("error");
							  	  });
				} else {
				  console.log('error submit!!');
				  return false;
				}
			}else{
				alert("请完成验证");
			}
         
        });
      }
    }
  }

</script>

<style lang="scss" scoped>
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>