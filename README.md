部署配置：
	1.修改path.js文件
		1.1 var ip = "127.0.0.1";	修改地址，本地尽量不要使用localhost而使用127.0.0.1
		1.2 端口号和额外的文件名，在 newurl = substr1+"//"+ ip + "/YouYouRobot/" + substr2;中添加，在ip后进行添加，如不需要单"/"即可
		1.3 Tomcat部署前后台，建议将端口号改为80
path.js的使用：
	1.在文件中导入 import path from '../../common/js/path'
	2.在路径中调用path.IntelliURLReplaceIP("")方法
		传参的路径需要携带端口号
			 if(url.indexOf(':8081/api/') == -1)
	        {
	           
	        }
			var substr2 = url.substring(url.indexOf('/api/')+1,url.length);
			该代码用于截取ip后字段
		2.1	get请求示例：get(path.IntelliURLReplaceIP("http://localhost:8081/api/paging?")+'&index='+index)
			get请求需要将路径传入方法，参数不需要传。参数在方法后拼接
		2.2 post请求示例：post(path.IntelliURLReplaceIP("http://localhost:8081/api/addSubject")
			post请求需要将路径传入方法