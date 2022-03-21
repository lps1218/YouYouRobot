var newurl = "";
//ip地址
// var ip = "127.0.0.1";
var ip = "120.48.21.237";
//端口(允许为空)
var path = "";
//路径前缀文件名
var fileName = "/YouYouRobot/";
// var fileName = "/";
export default {
	
	IntelliURLReplaceIP: function(url) {
	    var url = url||"";
	
	    try {
	        if (url == "") {
	            return url;
	        }
	
	        if(url.indexOf(ip) != -1)
	        {
	            // console.log("the same ip.");
	            return url;//没有此字段则退出
	        }
	
	        //截取ip前字段"http:"
	        if(url.indexOf('//') == -1)
	        {
	            return url;//没有此字段则退出
	        }
	        var substr1 = url.substring(0,url.indexOf('//'));
	        //console.log("oldurl protocol:",substr1);//"http:"
	
	        //截取ip后字段"it/local/facerec/default/20200609112555435018.jpg""
	        if(url.indexOf(':8081/api/') == -1)
	        {
	            return url;//没有此字段则退出
	        }
	        var substr2 = url.substring(url.indexOf('/api/')+1,url.length);
	        // console.log("oldurl path:", substr2);//"it/local/facerec/default/20200609112555435018.jpg"
	
			newurl = substr1+"//"+ ip + path + fileName + substr2;
	        console.log("newurl:",newurl);
	
	        return newurl;
	
	    } catch(exception){
	        alert("error");
	    }
}

}

