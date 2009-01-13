package com.quesofttech.business.domain.inventory.dto;


import java.io.Serializable;

import javax.persistence.Embedded;

import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.business.domain.inventory.MaterialType;
//import java.util.Date;

@SuppressWarnings("serial")
public class MaterialSearchFields implements Serializable {

	private String code = "";
	private String description = "";
	private MaterialType type = null;
	//private java.sql.Date createDate = null;
	//private String recordStatus = null;
	private Integer version = null;
	
	@Embedded
	RowInfo rowInfo;

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("MaterialSearchFields: [");
		buf.append("code=" + code + ", ");
		buf.append("description=" + description + ", ");
		buf.append("type=" + type.getType() + ", ");
		//buf.append("create date=" + createDate + ", ");
		//buf.append("record status=" + recordStatus + ", ");
		buf.append("version=" + version);
		buf.append("]");
		return buf.toString();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type.getType();
	}

	public void setType(MaterialType type) {
		this.type = type;
	}


	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


    //RowInfo field
	
	public String getRecordStatus() {
		return rowInfo.getRecordStatus();
	}



	public void setRecordStatus(String recordStatus) {
		this.rowInfo.setRecordStatus(recordStatus);
	}



	public String getSessionId() {
		return rowInfo.getSessionId();
	}


	public void setSessionId(String sessionId) {
		this.rowInfo.setSessionId(sessionId);
	}



	public String getCreateLogin() {
		return rowInfo.getCreateLogin();
	}



	public void setCreateLogin(String createLogin) {
		this.rowInfo.setCreateLogin(createLogin);
	}



	public String getCreateApp() {
		return rowInfo.getCreateApp();
	}



	public void setCreateApp(String createApp) {
		this.rowInfo.setCreateApp(createApp);
	}


	public java.sql.Timestamp getCreateTimestamp() {
		return rowInfo.getCreateTimestamp();
	}

	public void setCreateTimestamp(java.sql.Timestamp createTimestamp) {
		this.rowInfo.setCreateTimestamp(createTimestamp);
	}
    

	public String getModifyLogin() {
		return rowInfo.getModifyLogin();
	}

	public void setModifyLogin(String modifyLogin) {
		this.rowInfo.setModifyLogin(modifyLogin);
	}



	public String getModifyApp() {
		return rowInfo.getModifyApp();
	}



	public void setModifyApp(String modifyApp) {
		this.rowInfo.setModifyApp(modifyApp);
	}



	public java.sql.Timestamp getModifyTimestamp() {
		return rowInfo.getModifyTimestamp();
	}

	public void setModifyTimestamp(java.sql.Timestamp modifyTimestamp) {
		this.rowInfo.setModifyTimestamp(modifyTimestamp);
	}
}
