package com.robot.dao.background.login;


import com.robot.pojo.RobotBackgroundFunction;
import com.robot.pojo.RobotBackgroundUser;
import com.robot.pojo.RobotBackgroundUserLogin;
import org.apache.ibatis.annotations.Param;


public interface LoginMapper {

	 RobotBackgroundUserLogin getLogin(@Param("userName") String userName, @Param("password") String password);

	 RobotBackgroundFunction authority(@Param("id") String id);

	int insertGuan(RobotBackgroundUserLogin robotBackgroundUser);

}
