package com.robot.dao.background.login;

import com.robot.pojo.RobotBackgroundUser;
import org.apache.ibatis.annotations.Param;

public interface LoginMapper {
	public RobotBackgroundUser getLogin(@Param("userName")String userName, @Param("password")String password);

}
