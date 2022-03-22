package com.robot.pojo;

import java.util.Date;
import java.io.Serializable;

/**
 * (RobotBackgroundTeacher)后台教师表
 *
 * @author makejava
 * @since 2022-03-06 12:03:56
 */
public class RobotBackgroundTeacher implements Serializable {
    private static final long serialVersionUID = -34756906155889099L;
    /**
     * 老师表id
     */
    private Integer tid;
    /**
     * 老师姓名
     */
    private String tname;
    /**
     * 老师性别
     */
    private String tgender;
    /**
     * 老师年龄
     */
    private Integer age;
    /**
     * 老师电话
     */
    private String tphone;
    /**
     * 课程id
     */
    private Integer sid;
    /**
     * 老师出勤状态 1：已出勤 2：空闲
     */
    private Integer tstates;
    /**
     * 老师介绍
     */
    private String tintroduce;
    /**
     * 创建人
     */
    private String createp;
    /**
     * 创建时间
     */
    private Date createt;
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


    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTgender() {
        return tgender;
    }

    public void setTgender(String tgender) {
        this.tgender = tgender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTphone() {
        return tphone;
    }

    public void setTphone(String tphone) {
        this.tphone = tphone;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getTstates() {
        return tstates;
    }

    public void setTstates(Integer tstates) {
        this.tstates = tstates;
    }

    public String getTintroduce() {
        return tintroduce;
    }

    public void setTintroduce(String tintroduce) {
        this.tintroduce = tintroduce;
    }

    public String getCreatep() {
        return createp;
    }

    public void setCreatep(String createp) {
        this.createp = createp;
    }

    public Date getCreatet() {
        return createt;
    }

    public void setCreatet(Date createt) {
        this.createt = createt;
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

