/*
package com.robot.config.reception;

import com.robot.uitl.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//前台拦截器
@Configuration
public class ReceptionFiter extends WebMvcConfigurerAdapter {
	//怎样把springmvc 这个对象当成组件注入到拦截器中
	@Bean
	public LoginFilter getmvc()
	{
		return new LoginFilter();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		InterceptorRegistration registration=registry.addInterceptor(getmvc());
		//所有的路径都拦截，但是要排除login
		registration.addPathPatterns("/reception/*");
		registration.excludePathPatterns("/reception/login");
		*/
/*registration.excludePathPatterns("/excel");*//*


	}
}

*/
