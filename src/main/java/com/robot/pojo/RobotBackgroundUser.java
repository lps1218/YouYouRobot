package com.robot.pojo;

import java.util.Date;
import java.io.Serializable;

/**
 * (RobotBackgroundUser)后台用户表
 *
 * @author makejava
 * @since 2022-03-06 12:03:56
 */
public class RobotBackgroundUser implements Serializable {
	private static final long serialVersionUID = 912713797835008609L;
	/**
	 * 后台用户表id
	 */
	private Integer bid;
	/**
	 * 名称
	 */
	private String bname;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 权限  0代表所有admin  1代表限制权限
	 */
	private Integer
			jurisdiction;
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


	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getjurisdiction() {
		return	jurisdiction;
	}

	public void setjurisdiction(Integer jurisdiction) {
		this.jurisdiction = jurisdiction;
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

