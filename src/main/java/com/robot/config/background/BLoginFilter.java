package com.robot.config.background;

import com.robot.dao.background.login.LoginMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;


/**
 * @author star
 */
public class BLoginFilter implements HandlerInterceptor {

    @Resource
    LoginMapper dao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        /**
         * 获取用户登录的session
         */
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if(user != null){
            return  true;
        }
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.println("请先登录");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
