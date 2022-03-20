package com.robot.controller.background.login;

import com.robot.dao.background.login.LoginMapper;
import com.robot.pojo.RobotBackgroundUser;
import com.robot.pojo.RobotBackgroundUserLogin;
import com.robot.uitl.DateUtil;
import com.robot.uitl.DtoUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

//后台登录
@CrossOrigin
@Controller
@RequestMapping("api")
public class LoginController {
	@Resource
	LoginMapper dao;

	@RequestMapping("/login")
	@ResponseBody
	public Object login(String userName, String password, HttpServletResponse R,HttpServletRequest request ,HttpSession session){

		RobotBackgroundUserLogin pojo = dao.getLogin(userName, password);
		if(pojo!=null){
			/**
			 * 将用户信息存储到session中
			 */
			HttpSession session1 = request.getSession();
			session1.setAttribute("user",pojo);
			return DtoUtil.returnSuccess("登录成功",pojo);
		}
		return DtoUtil.returnFail("没有此用户","300");

	}

	/**
	 * 点击添加管理员
	 * @param name 用户名
	 * @param phone 手机号
	 * @param password 密码
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	/*@RequestMapping("/insertGuan")
	@ResponseBody
	public Object a( String name , String phone, String password, HttpServletRequest request) throws ParseException {

		System.out.println(name);
		//实体类
		RobotBackgroundUserLogin robotBackgroundUser = new RobotBackgroundUserLogin();
		robotBackgroundUser.setBname(name);
		robotBackgroundUser.setPassword(password);
		robotBackgroundUser.setPhone(phone);
		robotBackgroundUser.setjurisdiction(0);
		HttpSession session = request.getSession();
		RobotBackgroundUserLogin user = (RobotBackgroundUserLogin) session.getAttribute("user");
		robotBackgroundUser.setCreatep(user.getBname());
		robotBackgroundUser.setCreatet(DateUtil.currentTimeDate("yyyy-MM-dd HH:mm:ss"));
		int i = dao.insertGuan(robotBackgroundUser);
		if(i>0){
			return DtoUtil.returnSuccess();
		}
		return DtoUtil.returnFail("添加失败","10000");
	}*/


}
