package com.robot.pojo;

import java.io.Serializable;

/**
 * (RobotResources)前台资源表
 *
 * @author makejava
 * @since 2022-03-06 14:47:00
 */
public class RobotResources implements Serializable {
    private static final long serialVersionUID = -32427064045480634L;
    /**
     * 资源表id 
     */
    private Integer rid;
    /**
     * 资源类型 1:图片 2：视频 3：海报
     */
    private Integer rtype;
    /**
     * 具体路径
     */
    private String url;
    /**
     * 介绍
     */
    private String introduce;
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


    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getRtype() {
        return rtype;
    }

    public void setRtype(Integer rtype) {
        this.rtype = rtype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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

