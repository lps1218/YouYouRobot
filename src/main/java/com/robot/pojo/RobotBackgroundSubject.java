package com.robot.pojo;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * (RobotBackgroundSubject)后台课程表
 *
 * @author makejava
 * @since 2022-03-06 12:03:55
 */
public class RobotBackgroundSubject implements Serializable {
    private static final long serialVersionUID = -15347166004786810L;
    private Integer size;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 课程表id
     */
    private Integer sid;
    /**
     * 课程名称
     */
    private String sname;
    /**
     * 课程介绍
     */
    private String introduce;
    /**
     * 是否推荐  0代表推荐 1代表不推荐
     */
    private Integer recommend;
    /**
     * 课程图片
     */
    private String picture;
    /**
     * 创建人
     */
    private String createp;
    /**
     * 创建时间
     */
    private String createt;
    /**
     * 备用字段1
     */
    private String picurl;
    /**
     * 备用字段2
     */
    private String backuptwo;
    /**
     * 备用字段3
     */
    private String backupthree;


    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCreatep() {
        return createp;
    }

    public void setCreatep(String createp) {
        this.createp = createp;
    }

    public String getCreatet() {
        return createt;
    }

    public void setCreatet(String createt) {
        this.createt = createt;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
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

