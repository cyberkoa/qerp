package com.quesofttech.business.domain.embeddable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
@SuppressWarnings("serial")
public class RowInfo implements Serializable {

	
	@Column(length = 1, nullable = false)
	private String recordStatus;
	
	@Column(length = 50, nullable = false)
	private String sessionId;
	
	@Column(length = 20, nullable = false)
	private String createLogin;
	
	@Column(length = 50, nullable = false)
	private String createApp;
	
	@Column(nullable = false)
	private java.sql.Timestamp createTimestamp;
	
	//@Column(nullable = false)
	//private java.sql.Time createTime;
	
	@Column(length = 20, nullable = false)
	private String modifyLogin;
	
	@Column(length = 50, nullable = false)
	private String modifyApp;
	
	@Column(nullable = false)
	private java.sql.Timestamp modifyTimestamp;
	
	//@Column(nullable = false)
	//private java.sql.Time modifyTime;
/*
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
*/
	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getCreateLogin() {
		return createLogin;
	}

	public void setCreateLogin(String createLogin) {
		this.createLogin = createLogin;
	}

	public String getCreateApp() {
		return createApp;
	}

	public void setCreateApp(String createApp) {
		this.createApp = createApp;
	}

	/*
	public java.sql.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.sql.Date createDate) {
		this.createDate = createDate;
	}

	public java.sql.Time getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.sql.Time createTime) {
		this.createTime = createTime;
	}
 */
	
	
	
	
	public String getModifyLogin() {
		return modifyLogin;
	}

	public void setModifyLogin(String modifyLogin) {
		this.modifyLogin = modifyLogin;
	}

	public String getModifyApp() {
		return modifyApp;
	}

	public void setModifyApp(String modifyApp) {
		this.modifyApp = modifyApp;
	}
/*
	public java.sql.Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(java.sql.Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public java.sql.Time getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(java.sql.Time modifyTime) {
		this.modifyTime = modifyTime;
	}
*/

	/**
	 * @return the createTimestamp
	 */
	public java.sql.Timestamp getCreateTimestamp() {
		return createTimestamp;
	}

	/**
	 * @param createTimestamp the createTimestamp to set
	 */
	public void setCreateTimestamp(java.sql.Timestamp createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	/**
	 * @return the modifyTimestamp
	 */
	public java.sql.Timestamp getModifyTimestamp() {
		return modifyTimestamp;
	}

	/**
	 * @param modifyTimestamp the modifyTimestamp to set
	 */
	public void setModifyTimestamp(java.sql.Timestamp modifyTimestamp) {
		this.modifyTimestamp = modifyTimestamp;
	}
	
	
	
}
