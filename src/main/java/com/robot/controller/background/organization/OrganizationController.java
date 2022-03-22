package com.robot.controller.background.organization;

import com.robot.dao.background.organization.OrganizationMapper;
import com.robot.pojo.RobotUser;
import com.robot.uitl.Dto;
import com.robot.uitl.DtoUtil;
import com.robot.uitl.PageOrganization;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class OrganizationController {

    @Resource
    OrganizationMapper dao;

    //模糊分页查询机构
    @RequestMapping("/getRobotUser")
    public Dto getRobotUser(int index,String uname){
        PageOrganization p = new PageOrganization();
        if(index == 0){
            index = 1;
        }
        int size = 2;

        p.setOrganization(uname);

        p.setSize(size);
        p.setIndex((index-1)*size);

        int count = dao.userCount(p);

        p.setCount(count);
        p.setPageCount(count%size==0?count/size:count/size+1);

        List<RobotUser> list =  dao.getRobotUser(p);
        p.setList(list);
        return DtoUtil.returnDataSuccess(p);
    }
    //添加机构
    @RequestMapping("/insertRobotUser")
    public int insertRobotUser(RobotUser user){
        int i = dao.insertRobotUser(user);
        return i;
    }
    //删除机构
    @RequestMapping("/deleteRobotUser")
    public Dto deleteRobotUser(int uid){
        return DtoUtil.returnDataSuccess(dao.deleteRobotUser(uid));
    }
    @RequestMapping("/deleteRobotUserAll")
    public Dto deleteRobotUserAll(String ids){
        List<Integer> list = new ArrayList<Integer>();
        for (String i : ids.split(",")){
            if (i != null && i != ""){
                list.add(new Integer(i));
            }
        }
        int result = dao.deleteRobotUserAll(list);
        return DtoUtil.returnDataSuccess(result);
    }
    //编辑机构
    @RequestMapping("/updateRobotUser")
    public int updateRobotUser(Integer uid,String uphone,String address){
      return dao.updateRobotUser(uid,uphone,address);
    }

}
