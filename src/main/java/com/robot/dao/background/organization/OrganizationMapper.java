package com.robot.dao.background.organization;

import com.robot.pojo.RobotUser;
import com.robot.uitl.PageOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//机构接口

public interface OrganizationMapper {
    //遍历机构
    public List<RobotUser> getRobotUser(PageOrganization p);
    int userCount(PageOrganization p);
    //添加机构
    public int insertRobotUser(RobotUser user);
    //删除机构
    public int deleteRobotUser(int uid);
    public int deleteRobotUserAll(List<Integer> list);
    //编辑机构
    public int updateRobotUser(@Param("uid")Integer uid,@Param("uphone")String uphone,@Param("address")String address);
    //分页

}
