package com.quesoware.business.domain.inventory.dto;


import java.io.Serializable;

import javax.persistence.Embedded;

import com.quesoware.business.domain.embeddable.RowInfo;
import com.quesoware.business.domain.inventory.MaterialType;

@SuppressWarnings("serial")
public class MaterialSearchFields implements Serializable {

	private String code = "";
	private String description = "";
	private String type = "";
	//private java.sql.Date createDate = null;
	//private String recordStatus = null;
	private Integer version = null;
	private String recordStatus = "";
	
	@Embedded
	RowInfo rowInfo_1;

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("MaterialSearchFields: [");
		buf.append("code=" + code + ", ");
		buf.append("description=" + description + ", ");
		buf.append("type=" + type + ", ");
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
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}


}