package com.quesofttech.business.domain.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.quesofttech.business.common.exception.AuthenticationException;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.GenericBusinessException;
import com.quesofttech.business.common.exception.ValueRequiredException;
import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.util.StringUtil;

/**
 * The User Password entity.
 */
@Entity
@Table(name = "UserPassword")
@SuppressWarnings("serial")
public class UserPassword extends BaseEntity {

	public UserPassword() {
		super();
		this.rowInfo = new RowInfo();
	}	
	//For Postgresql : @SequenceGenerator(name = "material_sequence", sequenceName = "material_id_seq")
	//Generic solution : (Use a table named PrimaryKeys, with 2 fields , tableName &  keyField)
	@TableGenerator(  name="userpassword_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="userpassword", valueColumnName="keyField")	
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userpassword_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "userpassword_id")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_UserPassword", nullable = false)
	private Long id;

	@Version
	@Column(name = "userp_Version", nullable = false)
	private Integer version;

	@OneToOne(mappedBy = "userPassword") // userPassword is a UserPassword instance in class User
	private User user;

	@Column(name = "userp_Password", length = 32)
	private String password;

	/*
	@Column(name = "userp_RecordStatus", length = 1)
	private String recordStatus;	
	
	@Column(name = "userp_SessionID", length = 50)
	private String sessionID;
	
	@Column(name = "userp_CreateLogin", length = 20)
	private String createLogin;
	
	@Column(name = "userp_CreateApp", length = 50)
	private String createApp;
	
	@Column(name = "userp_CreateDate")
	private java.sql.Date createDate;
	
	@Column(name = "userp_CreateTime")
	private java.sql.Time createTime;
	
	@Column(name = "userp_ModifyLogin", length = 20)
	private String modifyLogin;
	
	@Column(name = "userp_ModifyApp", length = 50)
	private String modifyApp;
	
	@Column(name = "userp_ModifyDate")
	private java.sql.Date modifyDate;
	
	@Column(name = "userp_ModifyTime")
	private java.sql.Time modifyTime;	
	*/
	@Transient
	private transient String _loaded_password = null;

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("UserPassword: [");
		buf.append("id=" + id + ", ");
		buf.append("user=" + user + ", ");
		buf.append("password=" + password + ", ");
		buf.append("version=" + version);
		buf.append("]");
		return buf.toString();
	}

	// The need for an equals() method is discussed at http://www.hibernate.org/109.html

	@Override
	public boolean equals(Object obj) {
		return (obj == this) || (obj instanceof UserPassword) && getId() != null
				&& ((UserPassword) obj).getId().equals(this.getId());
	}

	// The need for a hashCode() method is discussed at http://www.hibernate.org/109.html

	public String get_loaded_password() {
		return _loaded_password;
	}

	public void set_loaded_password(String _loaded_password) {
		this._loaded_password = _loaded_password;
	}

	@Override
	public int hashCode() {
		return getId() == null ? super.hashCode() : getId().hashCode();
	}

	@Override
	public Serializable getIdForMessages() {
		return getId();
	}
/*
	@PrePersist
	void prePersistOfUserPassword() throws BusinessException {
		validate();
	}
*/
	@PostLoad
	void postLoadOfUserPassword() {
		_loaded_password = password;
	}
/*
	@PreUpdate
	void preUpdateOfUserPassword() throws BusinessException {
		validate();
	}
*/
	@PreRemove
	void preRemoveOfUserPassword() throws BusinessException {
		// Check business rules here, eg.
		// if (entity.getParts().size() > 0) {
		// throw new CannotDeleteIsNotEmptyException(this, id, "Part");
		// }

		// There's no need to remove me from the parent(s) - that's the caller's
		// responsibility (and for performance, it might not bother)
	}

	public void validate() throws BusinessException  {

		// Validate syntax...

		if (StringUtil.isEmpty(password)) {
			throw new ValueRequiredException(this, "User_password");
		}

		// Validate semantics...

	}

	public void authenticate(String password) throws AuthenticationException {

		if (password == null || !password.equals(this.password)) {
			throw new AuthenticationException("User_password_incorrect");
		}

	}

	/**
	 * This method provides a way for users to change their own passwords.
	 */
	void changePassword(String currentPassword, String newPassword) throws BusinessException {

		if (this.password != null && !currentPassword.equals(this.password)) {
			throw new AuthenticationException("User_password_incorrect");
		}

		setPassword(newPassword);
	}

	public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * This method provides a way for security officers to "reset" the userPassword.
	 */
	public void setAssignPassword(String newPassword)throws BusinessException {
		if (StringUtil.isEmpty(newPassword)) {
			throw new ValueRequiredException(this, "User_password");
		}
		this.password = newPassword;
	}
	void setPassword(String newPassword) throws BusinessException {

		// Do all tests of userPassword size, content, history, etc. here...

		if (StringUtil.isEmpty(newPassword)) {
			throw new ValueRequiredException(this, "User_password");
		}

		if (_loaded_password != null && newPassword.equals(_loaded_password)) {
			throw new GenericBusinessException("User_newpassword_is_same");
		}

		this.password = newPassword;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
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
	
	
	
}
