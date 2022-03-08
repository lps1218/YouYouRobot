package com.robot.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * (RobotComment)前台评论表
 *
 * @author makejava
 * @since 2022-03-06 14:47:00
 */
public class RobotComment implements Serializable {
    private static final long serialVersionUID = -95537086661718047L;
    /**
     * 评论表id
     */
    private Integer cid;
    /**
     * 订单id
     */
    private Integer oid;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createdate;
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


    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
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

