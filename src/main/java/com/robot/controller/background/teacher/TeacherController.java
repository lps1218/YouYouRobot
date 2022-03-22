package com.robot.controller.background.teacher;

import com.robot.dao.background.teacher.TeacherMapper;
import com.robot.pojo.RobotBackgroundTeacher;
import com.robot.uitl.Dto;
import com.robot.uitl.DtoUtil;
import com.robot.uitl.PageUtilTeacher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

//后台教师类
@CrossOrigin
@Controller
@RequestMapping("api")
public class TeacherController {
    @Resource
    TeacherMapper dao;
    //分页查询数据
    @RequestMapping("/seletea")
    @ResponseBody
    public Dto getList(int index, String tname){
        PageUtilTeacher page = new PageUtilTeacher();

        int size = 5;
        tname=tname==null?"":tname;
        index=index==0?1:index;

        page.setSize(size);
        page.setIndex((index-1)*size);
        page.setName(tname);

        int count=dao.count(page);

        page.setCount(count);
        page.setPageCount(count%size==0?count/size:count/size+1);

        List<RobotBackgroundTeacher> list = dao.list(page);

        page.setList(list);

        return DtoUtil.returnDataSuccess(page);
    }
    //删除数据
    @RequestMapping("/deletea")
    @ResponseBody
    public Object delList(String tid){
        dao.dele(tid);
        return DtoUtil.returnSuccess("成功",tid);
    }
    //批量删除数据
    @PostMapping("/deletea1")
    @ResponseBody
    public Object delList1(@RequestBody List<Object> tid){
        dao.deletea(tid);
        return DtoUtil.returnSuccess("成功123",tid);
    }
    //按编号查询
    @RequestMapping("/updseltea")
    @ResponseBody
    public String updsel(String id,Map map){
        RobotBackgroundTeacher list = dao.updsel(id);
        map.put("list", list);
        return "edit-teacher";
    }
    //修改
    @RequestMapping("/updtea")
    @ResponseBody
    public String updList(RobotBackgroundTeacher teacher){
        dao.updat(teacher);
        return "edit-teacher";
    }
    //增加
    @RequestMapping("/insetea")
    @ResponseBody
    public String inseList(RobotBackgroundTeacher teacher){
        dao.insert(teacher);
        return "add-teacher";
    }
}
