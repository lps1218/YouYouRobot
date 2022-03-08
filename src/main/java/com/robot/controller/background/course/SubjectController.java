package com.robot.controller.background.course;

import com.robot.dao.background.course.FunctionMapper;
import com.robot.pojo.RobotBackgroundSubject;
import com.robot.uitl.DateUtil;
import com.robot.uitl.Dto;
import com.robot.uitl.DtoUtil;
import com.robot.uitl.PageHelp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 课程管理类
 * @author LPS
 */
@RestController
@RequestMapping("/api")
public class SubjectController {

    @Resource
    FunctionMapper functionMapper;

    /**
     * 分页模糊查询课程
     * @param name 课程名称
     * @param index 分页索引
     * @return Dto类型
     */
    @GetMapping( "/paging")
    public Dto queryPaging(String name, Integer index, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        HashMap<String, Object> queryPagingMap = new HashMap<>(16);
        String show = "undefined";
        Integer integerSum = 0;
        if (name.equals(show) || name ==null) {
            queryPagingMap.put("name","%%");
            integerSum = functionMapper.queryAllSum("%%");
        }else{
            queryPagingMap.put("name","%"+name+"%");
            integerSum = functionMapper.queryAllSum("%" + name + "%");
        }
        queryPagingMap.put("index",(index-1)*5);
        List<RobotBackgroundSubject> queryAllList = functionMapper.queryAll(queryPagingMap);
        Integer sum = integerSum % 5 == 0 ? integerSum / 5 : integerSum / 5 + 1;
        PageHelp pageHelp = new PageHelp(index,integerSum,sum,queryAllList);
        return DtoUtil.returnSuccess("10000",pageHelp);
    }

    /**
     * 根据ID修改课程
     * @param
     * @return Dto类型
     */
    @GetMapping("revamp")
    @CrossOrigin
    public Dto revampSubject(Integer sid,String sname,Integer recommend,String picture,String introduce){
        RobotBackgroundSubject robotBackgroundSubject = new RobotBackgroundSubject();
        robotBackgroundSubject.setSid(sid);
        robotBackgroundSubject.setSname(sname);
        robotBackgroundSubject.setRecommend(recommend);
        robotBackgroundSubject.setPicture(picture);
        robotBackgroundSubject.setIntroduce(introduce);
        int i = 0;
        try {
            String date = DateUtil.currentTime("yyyy-MM-dd HH:mm:ss");
            robotBackgroundSubject.setCreatet(date);
            i = functionMapper.revampSubjectId(robotBackgroundSubject);
        } catch (ParseException e) {
            e.printStackTrace();
            return DtoUtil.returnFail("10002","修改错误");
        }
        if (i == 1 ){
            return DtoUtil.returnSuccess("10000");
        }else {
            return DtoUtil.returnFail("10001","修改失败");
        }
    }

    /**
     * 添加课程
     * @param
     * @return Dto类型
     */
    @GetMapping("addSubject")
    public Dto addSubject(Integer sid, String sname, Integer recommend, String picture, String introduce){
        RobotBackgroundSubject robotBackgroundSubject = new RobotBackgroundSubject();
        robotBackgroundSubject.setSid(sid);
        robotBackgroundSubject.setSname(sname);
        robotBackgroundSubject.setRecommend(recommend);
        robotBackgroundSubject.setPicture(picture);
        robotBackgroundSubject.setIntroduce(introduce);
        int i = 0;
        try {
            String date = DateUtil.currentTime("yyyy-MM-dd HH:mm:ss");
            robotBackgroundSubject.setCreatet(date);
            i = functionMapper.addSubject(robotBackgroundSubject);
        } catch (ParseException e) {
            e.printStackTrace();
            return DtoUtil.returnFail("10002","添加错误");
        }
        if (i == 1 ){
            return DtoUtil.returnSuccess("10000");
        }else {
            return DtoUtil.returnFail("10001","添加失败");
        }
    }

    /**
     * 根据ID删除课程
     * @param sid 课程ID
     * @return Dto类型
     */
    @GetMapping("deleteSubject")
    @CrossOrigin
    public Dto deleteSubjectId(Integer sid,HttpServletResponse response){
        //response.setHeader("Access-Control-Allow-Origin","*");
        Integer deleteNum = functionMapper.deleteSubjectId(sid);
        if (deleteNum == 1){
            return DtoUtil.returnSuccess("10000");
        }else{
            return DtoUtil.returnFail("10001","删除错误");
        }
    }

    @GetMapping("deleteBatchSubject")
    @CrossOrigin
    public Dto deleteBatchSubject(String ids){
        List<String> delList = new ArrayList<String>();
        String[] strs = ids.split(",");
        for (String str : strs) {
            delList.add(str);
        }
        int size = delList.size();
        //开始循环批量删除
        Integer batchNum = functionMapper.batchDeleteSubjectId(delList);
        //判断删除数据
        if (batchNum == size){
            return DtoUtil.returnSuccess("10000");
        }else if (batchNum == 0){
            return DtoUtil.returnFail("10002","删除失败");
        }else{
            return DtoUtil.returnFail("10001","没完全删除");
        }
    }
}
