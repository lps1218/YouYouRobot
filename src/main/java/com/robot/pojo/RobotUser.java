package com.robot.pojo;

import java.io.Serializable;

/**
 * (RobotUser)前台用户表
 *
 * @author makejava
 * @since 2022-03-06 14:47:00
 */
public class RobotUser implements Serializable {
    private static final long serialVersionUID = -78546231160224228L;
    /**
     * 用户表id
     */
    private Integer uid;
    /**
     * 微信用户名
     */
    private String uname;
    /**
     * 手机号
     */
    private String uphone;
    /**
     * 地址
     */
    private String address;
    /**
     * 机构名称
     */
    private String organization;
    /**
     * 密码
     */
    private String password;
    /**
     * 联系人姓名
     */
    private String cname;
    /**
     * 是否完善信息
     */
    private Integer statsid;
    /**
     * 性别 0代表男性  1代表女性
     */
    private Integer gender;
    /**
     * 备用字段一
     */
    private String backupone;
    /**
     * 备用字段二
     */
    private String backuptwo;
    /**
     * 备用字段三
     */
    private String backupthree;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Integer getStatsid() {
        return statsid;
    }

    public void setStatsid(Integer statsid) {
        this.statsid = statsid;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
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

