package com.robot.controller.background.login;

import com.robot.dao.background.login.LoginMapper;
import com.robot.pojo.RobotBackgroundUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
//后台登录
@Controller
@RequestMapping("background")
public class LoginController {
	@Resource
	LoginMapper dao;
	@RequestMapping("/login")
	@ResponseBody
	public Object login(String userName,String password, HttpServletResponse R){
		R.addHeader("Access-Control-Allow-Origin","*");
		/*RobotBackgroundUser pojo = dao.getLogin(userName, password);
		if(pojo!=null){
			return pojo;
		}*/
		return "拦截";
	}

	@RequestMapping("/a")
	@ResponseBody
	public Object a(HttpServletResponse R){
		R.addHeader("Access-Control-Allow-Origin","*");

		return "不拦截";
	}
}
