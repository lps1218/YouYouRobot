package com.robot.pojo.VO;

import com.robot.pojo.RobotBackgroundSubject;
import com.robot.pojo.RobotBackgroundTeacher;
import com.robot.pojo.RobotBackgroundUser;

import java.io.Serializable;
import java.util.Date;

// 后台订单VO类
public class RobotOrderVO implements Serializable {
    // 教师实体类
    private RobotBackgroundTeacher teacherpojo;
    // user实体类
    private RobotBackgroundUser userpojo;
    // 课程实体类
    private RobotBackgroundSubject subjectpojo;

    public RobotBackgroundTeacher getTeacherpojo() {
        return teacherpojo;
    }

    public void setTeacherpojo(RobotBackgroundTeacher teacherpojo) {
        this.teacherpojo = teacherpojo;
    }

    public RobotBackgroundUser getUserpojo() {
        return userpojo;
    }

    public void setUserpojo(RobotBackgroundUser userpojo) {
        this.userpojo = userpojo;
    }

    public RobotBackgroundSubject getSubjectpojo() {
        return subjectpojo;
    }

    public void setSubjectpojo(RobotBackgroundSubject subjectpojo) {
        this.subjectpojo = subjectpojo;
    }

    /**
     * 订单表id
     */
    private Integer oid;
    /**
     * 机构id
     */
    private Integer uid;
    /**
     * 课程id
     */
    private Integer sid;
    /**
     * 老师id
     */
    private Integer tid;
    /**
     * 订单状态 1:已派师 2：已下单  3：已取消  4：已完成
     */
    private Integer stats;
    /**
     * 订单详情
     */
    private String detail;
    /**
     * 订单创建日期
     */
    private Date createdate;
    /**
     * 订单完成日期
     */
    private String modifydate;
    /**
     * 备用字段1
     */
    private String backupone;
    /**
     * 备用字段2
     */
    private String backuptwo;
    /**
     * 备用字段3
     */
    private String backupthree;


    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getStats() {
        return stats;
    }

    public void setStats(Integer stats) {
        this.stats = stats;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getModifydate() {
        return modifydate;
    }

    public void setModifydate(String modifydate) {
        this.modifydate = modifydate;
    }

    public String getBackupone() {
        return backupone;
    }

    public void setBackupone(String backupone) {
        this.backupone = backupone;
    }

    public String getBackuptwo() {
        return backuptwo;
    }

    public void setBackuptwo(String backuptwo) {
        this.backuptwo = backuptwo;
    }

    public String getBackupthree() {
        return backupthree;
    }

    public void setBackupthree(String backupthree) {
        this.backupthree = backupthree;
    }

}

