package com.robot.controller.background.activity;

import com.robot.dao.background.activity.ResourceMapping;
import com.robot.pojo.RobotResources;
import com.robot.uitl.Dto;
import com.robot.uitl.DtoUtil;
import com.robot.uitl.PageHelp;
import com.robot.uitl.PageHelpActivity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

//前台 资源
@Controller
@RequestMapping("api")
public class ActivityController {
	@Resource
	ResourceMapping dao;
	//分页
	@RequestMapping("/resource")
	@ResponseBody
	public Object getList(String name,String rtype,Integer index,HttpServletResponse r){
		r.setHeader("Access-Control-Allow-Origin","*");
		List<RobotResources> ac = null;
		if(index==null){
			index=1;
		}
		if(name==""){
			ac=dao.getList("%"+name+"%","%"+rtype+"%",(index-1)*5);
		}else{
			ac=dao.getList("%"+name+"%","%"+rtype+"%",(index-1)*5);
		}

		int pageNo=dao.count("%"+name+"%","%"+rtype+"%")%5==0?dao.count("%"+name+"%","%"+rtype+"%")/5:(dao.count("%"+name+"%","%"+rtype+"%")/5)+1;
		PageHelpActivity page=new PageHelpActivity(index,dao.count("%"+name+"%","%"+rtype+"%"),pageNo,ac);
		return DtoUtil.returnDataSuccess(page);
	}
	//删除
	@RequestMapping("/delete")
	@ResponseBody
	public Object getDelete(int rid,HttpServletResponse r){
		//r.addHeader("Access-Control-Allow-Origin","*");
		System.out.println(1);
		return DtoUtil.returnDataSuccess(dao.delete(rid));
	}
	//批量删除
	@RequestMapping("/deleteAll")
	@ResponseBody
	public Object getDelAll(String delId,HttpServletResponse R){
		//R.addHeader("Access-Control-Allow-Origin","*");
		List<Integer> list=new ArrayList<>();
		//以","作为分割符
		for (String i: delId.split(",")) {
			if(i!=null&&i!=""){
				list.add(new Integer(i));
			}
		}
		//dao层删除方法
		int i=dao.deleteAll(list);
		return DtoUtil.returnDataSuccess(i);
	}

	//文件上传 含用户添加
	//添加功能和上传单个文件功能
	//@PostMapping("/addUser")
	@RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
	@ResponseBody
	public Dto getIn(@RequestParam("headPic")MultipartFile file,@RequestParam("rtype")String theme,RobotResources res,Model model,HttpServletRequest request,HttpServletResponse r) throws IllegalStateException, IOException{
		//r.addHeader("Access-Control-Allow-Origin","*");
//如果上传目录为/static/images/upload/,则可以如下获取
		File path=new File(ResourceUtils.getURL("classpath:").getPath());
		if(!path.exists()){
			path=new File("");
		}
//创建
		File upload=new File(path.getAbsolutePath(),"api/static/images/uplaod/");
		if(!upload.exists()) {
			upload.mkdirs();
		}
		System.out.println(upload.getAbsolutePath());
//获取上传的文件名
		String filename=file.getOriginalFilename();
//截取后缀
		String suffix=filename.substring(filename.lastIndexOf("."));
//使用UUID拼接后缀，定义一个不重复的文件名
		String newFileName=UUID.randomUUID()+suffix;
		File file1=new File(upload+File.separator+newFileName);
//写入文件
		file.transferTo(file1);

//添加方法
		res.setUrl(file1+"");//存入数据库中的路径
		res.setPicurl(newFileName);
		int row=dao.insert(res);

		if(row>0){
			return DtoUtil.returnDataSuccess("成功");
		}
		return DtoUtil.returnDataSuccess("上传失败");
	}
	/*@Resource
	FileServer uploadService;
	@PostMapping("/fileupload")
	@ResponseBody
	public Map<String, String> uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
		return uploadService.upfile(file, request);//这里调用service的upfile方法   ，传入两个参数。
	}*/

	/*@RequestMapping("/aa")
	@ResponseBody
	public Object getIn(@RequestParam("file") MultipartFile file,RobotResources resources,HttpServletRequest request,HttpServletResponse r){
		r.addHeader("Access-Control-Allow-Origin","*");
		String realPath=request.getSession().getServletContext().getRealPath("/WEB-INF/files");
		System.out.println(1);
		return DtoUtil.returnDataSuccess("成功");
	}*/

}
