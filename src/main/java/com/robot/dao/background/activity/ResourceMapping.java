package com.robot.dao.background.activity;

import com.robot.pojo.RobotResources;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//资源
@Mapper
public interface ResourceMapping {
	//分页
	public List<RobotResources> getList(@Param("name")String name,@Param("rtype")String rtype, @Param("index")int index);
	public int count(@Param("name")String name,@Param("rtype")String rtype);
	//删除
	public int delete(@Param("rid")int rid);
	//批量删除
	public int deleteAll(List<Integer> list);
	//添加
	public int insert(RobotResources re);

	//public int insert(Integer , String , String , String );
}
