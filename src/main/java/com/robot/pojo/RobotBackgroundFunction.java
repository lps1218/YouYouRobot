package com.robot.pojo;

import java.io.Serializable;

/**
 * (RobotBackgroundFunction)后台功能表/权限表
 *
 * @author makejava
 * @since 2022-03-06 12:03:53
 */
public class RobotBackgroundFunction implements Serializable {
    private static final long serialVersionUID = -21522791959176581L;
    /**
     * 功能表id
     */
    private Integer fid;
    /**
     * 1：增删改查2：增改查3：创建其他管理员4：上传资料
     */
    private Integer fname;
    /**
     * 对应后台用户表id
     */
    private Integer bid;
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


    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getFname() {
        return fname;
    }

    public void setFname(Integer fname) {
        this.fname = fname;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
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

