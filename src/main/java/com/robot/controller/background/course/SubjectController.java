package com.robot.controller.background.course;

import com.robot.dao.background.course.FunctionMapper;
import com.robot.pojo.RobotBackgroundSubject;
import com.robot.uitl.DateUtil;
import com.robot.uitl.Dto;
import com.robot.uitl.DtoUtil;
import com.robot.uitl.PageHelp;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * 课程管理类
 * @author LPS
 */
@RestController
@CrossOrigin
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
    @RequestMapping(value = "/revamp", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Dto revampSubject(@RequestParam("headPic")MultipartFile file, RobotBackgroundSubject subject, HttpServletRequest request, HttpServletResponse r) throws IOException {
        //如果上传目录为/static/images/upload/,则可以如下获取
        File path=new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()){
            path=new File("");
        }
        //创建
        File upload=new File(path.getAbsolutePath(),"api/static/images/uplaod/");
        if(!upload.exists()) {
            upload.mkdirs();
        }
        System.out.println(upload.getAbsolutePath());
        //获取上传的文件名
        String filename=file.getOriginalFilename();
        //截取后缀
        String suffix=filename.substring(filename.lastIndexOf("."));
        //使用UUID拼接后缀，定义一个不重复的文件名
        String newFileName= UUID.randomUUID()+suffix;
        File file1=new File(upload+File.separator+newFileName);
        //写入文件
        file.transferTo(file1);

        int i = 0;
        try {
            String date = DateUtil.currentTime("yyyy-MM-dd HH:mm:ss");
            subject.setCreatet(date);
            subject.setPicture(file1+"");
            subject.setPicurl(newFileName);
            i = functionMapper.revampSubjectId(subject);
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
    @RequestMapping(value = "/addSubject", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public Dto getIn(@RequestParam("headPic")MultipartFile file, RobotBackgroundSubject subject, HttpServletRequest request, HttpServletResponse r) throws IllegalStateException, IOException {
        //r.addHeader("Access-Control-Allow-Origin","*");
        //如果上传目录为/static/images/upload/,则可以如下获取
        File path=new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()){
            path=new File("");
        }
        //创建
        File upload=new File(path.getAbsolutePath(),"api/static/images/uplaod/");
        if(!upload.exists()) {
            upload.mkdirs();
        }
        System.out.println(upload.getAbsolutePath());
        //获取上传的文件名
        String filename=file.getOriginalFilename();
        //截取后缀
        String suffix=filename.substring(filename.lastIndexOf("."));
        //使用UUID拼接后缀，定义一个不重复的文件名
        String newFileName= UUID.randomUUID()+suffix;
        File file1=new File(upload+File.separator+newFileName);
        //写入文件
        file.transferTo(file1);

        //添加方法
        int i = 0;
        try {
            String date = DateUtil.currentTime("yyyy-MM-dd HH:mm:ss");
            subject.setCreatet(date);
            subject.setPicture(file1+"");
            subject.setPicurl(newFileName);
            i = functionMapper.addSubject(subject);
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
    public Dto deleteSubjectId(Integer sid,HttpServletResponse response){
        Integer deleteNum = functionMapper.deleteSubjectId(sid);
        if (deleteNum == 1){
            return DtoUtil.returnSuccess("10000");
        }else{
            return DtoUtil.returnFail("10001","删除错误");
        }
    }

    /**
     * 根据ID批量删除
     * @param ids ID集
     * @return Dto类型
     */
    @GetMapping("deleteBatchSubject")
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
