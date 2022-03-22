package com.robot.dao.background.order;


import com.robot.pojo.RobotBackgroundTeacher;
import com.robot.pojo.RobotOrder;
import com.robot.pojo.RobotUser;
import com.robot.pojo.VO.RobotOrderVO;
import com.robot.uitl.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
// 后台订单mapper层

public interface OrderMapper {
	// 分页
	List<RobotOrderVO> pageOrder(Page vo);
	// 一共多少数据量
	int selCount(Page vo);
	// 修改订单状态
	int updStats(RobotOrder order);
	// 查询所有空闲教师
	List<RobotBackgroundTeacher> selUser();
	// 根据id删除
	int delId(@Param("oid")int oid);
	// 根据id批量删除
	int delALL(List<Integer> list);
}
