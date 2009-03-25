package com.quesoware.business.domain.security.dto;

import java.io.Serializable;
//import java.util.Date;

@SuppressWarnings("serial")
public class UserSearchFields implements Serializable {

	private String login = "";
	private String salutation = "";
	private String firstName = "";
	private String lastName = "";
	private String emailAddress = "";
	private java.sql.Date expiryDate = null;
	private String recordStatus = null;
	private Integer version = null;

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("UserSearchFields: [");
		buf.append("login=" + login + ", ");
		buf.append("salutation=" + salutation + ", ");
		buf.append("firstName=" + firstName + ", ");
		buf.append("lastName=" + lastName + ", ");
		buf.append("emailAddress=" + emailAddress + ", ");
		buf.append("expiryDate=" + expiryDate + ", ");
		buf.append("record status=" + recordStatus + ", ");
		buf.append("version=" + version);
		buf.append("]");
		return buf.toString();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		login = login;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public java.sql.Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(java.sql.Date expiryDate) {
		this.expiryDate = expiryDate;
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
