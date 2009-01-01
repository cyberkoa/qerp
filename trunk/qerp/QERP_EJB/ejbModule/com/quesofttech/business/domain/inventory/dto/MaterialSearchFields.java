package com.quesofttech.business.domain.inventory.dto;


import java.io.Serializable;
import com.quesofttech.business.domain.inventory.MaterialType;
//import java.util.Date;

@SuppressWarnings("serial")
public class MaterialSearchFields implements Serializable {

	private String code = "";
	private String description = "";
	private MaterialType type = null;
	private java.sql.Date createDate = null;
	private String recordStatus = null;
	private Integer version = null;

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("MaterialSearchFields: [");
		buf.append("code=" + code + ", ");
		buf.append("description=" + description + ", ");
		buf.append("type=" + type.getType() + ", ");
		buf.append("create date=" + createDate + ", ");
		buf.append("record status=" + recordStatus + ", ");
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

	public java.sql.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.sql.Date createDate) {
		this.createDate = createDate;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


}
