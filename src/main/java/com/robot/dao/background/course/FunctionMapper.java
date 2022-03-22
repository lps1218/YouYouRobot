package com.robot.dao.background.course;

import com.robot.pojo.RobotBackgroundSubject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 课程Mapper接口
 * @author LPS
 */

public interface FunctionMapper {

    /***
     * 查询
     * @param map 传递模糊查询条件与分页条件
     * @return List<RobotBackgroundSubject>类型
     */
    public List<RobotBackgroundSubject> queryAll(Map map);

    public Integer queryAllSum(@Param("name") String name);
    /**
     * 根据ID修改
     * @param robotBackgroundSubject 传递的数据
     * @return Integer类型
     */
    public Integer revampSubjectId(RobotBackgroundSubject robotBackgroundSubject);

    /**
     * 添加课程
     * @param robotBackgroundSubject 传递的数据
     * @return Integer类型
     */
    public Integer addSubject(RobotBackgroundSubject robotBackgroundSubject);

    /**
     * 根据ID删除课程
     * @param sid 用户ID
     * @return Integer类型
     */
    public Integer deleteSubjectId(@Param("sid") Integer sid);

    /**
     * 根据ID批量删除课程
     * @param list 用户ID的集合
     * @return Integer类型
     */
    public Integer batchDeleteSubjectId(List list);
}
