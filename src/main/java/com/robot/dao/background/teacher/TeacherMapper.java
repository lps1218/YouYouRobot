package com.robot.dao.background.teacher;

import com.robot.pojo.RobotBackgroundTeacher;
import com.robot.uitl.PageUtilTeacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author 明月天涯
 * @desc 白夜
 * @date 2022-03-06 19:46:21
 */
public interface TeacherMapper {
    //查询
    public List<RobotBackgroundTeacher> list(PageUtilTeacher p);
    //查询总条数
    public int count(PageUtilTeacher p);
    //按编号查询
    public RobotBackgroundTeacher updsel(@Param("id")String id);
    //修改
    public int updat(RobotBackgroundTeacher teacher);
    //删除
    public int dele(@Param("tid")String tid);
    //批量删除
    public Object deletea(List<Object> tid);
    //增加
    public int insert(RobotBackgroundTeacher teacher);
}
