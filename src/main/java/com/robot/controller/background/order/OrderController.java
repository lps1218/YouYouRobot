package com.robot.controller.background.order;

import com.robot.dao.background.order.OrderMapper;
import com.robot.pojo.RobotOrder;
import com.robot.pojo.VO.RobotOrderVO;
import com.robot.uitl.Dto;
import com.robot.uitl.DtoUtil;
import com.robot.uitl.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

//后台订单
@RestController
@RequestMapping("/api")
public class OrderController {
    @Resource
    OrderMapper dao;

    // 分页
    @RequestMapping("/pageOrder")
    public Dto pageOrder(int index,String organization,int stats){
        Page p = new Page();
        p.setOrganization(organization);// 根据机构查询
        p.setStats(stats);// 根据订单状态
        // 默认进来是第一页
        if(index == 0){
            p.setIndex(1);
        }else{
            p.setIndex(index);
        }
        int size = 2; // 每页多少条数据
        // 当前是第几页
        p.setIndex((p.getIndex()-1)*size);
        p.setSize(size);// 每页数据量
        // 总数据量
        int count = dao.selCount(p);
        p.setCount(count);
        // 计算总页数
        p.setPageCount(count % size == 0 ? count / size : count / size + 1);

        // 订单集合数据
        List<RobotOrderVO> list = dao.pageOrder(p);
        p.setList(list);
        return DtoUtil.returnDataSuccess(p);
    }

    // 修改订单状态
    @RequestMapping("/updstats")
    public Dto updStats(int oid,int tid,int stats){
        RobotOrder order = new RobotOrder();
        order.setOid(oid);
        order.setTid(tid);
        order.setStats(stats);
        int result = dao.updStats(order);

        return DtoUtil.returnDataSuccess(result);
    }
    // 查询所有空闲教师
    @RequestMapping("/selUser")
    public Dto selUser(){
        return DtoUtil.returnDataSuccess(dao.selUser());
    }
    // 单个删除
    @RequestMapping("/delId")
    public Dto delone(int id){
        return DtoUtil.returnDataSuccess(dao.delId(id));
    }
    // 根据id批量删除
    @RequestMapping("/delAll")
    public Dto delALL(String ids){
        // ids是许多id的字符串中间是逗号隔开
        List<Integer> list = new ArrayList<Integer>();
        // 将字符分割
        for (String i : ids.split(",")) {
            // 当分割出来的单个字符不等于空时加入集合中
            if(i != null && i != ""){
                list.add(new Integer(i));
            }
        }
        return DtoUtil.returnDataSuccess(dao.delALL(list));
    }
}
