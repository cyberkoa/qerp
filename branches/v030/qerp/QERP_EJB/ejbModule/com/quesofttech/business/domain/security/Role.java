package com.quesofttech.business.domain.security;

import java.io.Serializable;
//import java.sql.Date;
//import java.sql.Time;
import java.sql.Timestamp;
//import java.sql.*;
import java.util.List;


import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.TableGenerator;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.persistence.Embedded;
//import javax.persistence.SequenceGenerator;


import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.business.common.exception.ValueRequiredException;
import com.quesofttech.util.StringUtil;


//import java.util.ArrayList;

@Entity
@Table(name = "Role", uniqueConstraints = { @UniqueConstraint(columnNames = { "role_Role" }) })
@SuppressWarnings("serial")
public class Role extends BaseEntity {
	

	//For Postgresql : @SequenceGenerator(name = "Role_sequence", sequenceName = "Role_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="Role_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="Role", valueColumnName="keyField")
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Role_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Role_id")	
	@Column(name = "id_Role", nullable = false)
	private Long id;
	
	@Version
	@Column(nullable = false)
	private Long version;


	// Example of field	
	@Column(name = "role_Role", length = 20, nullable = false)
	private String role;
	
	@Column(name = "role_Description", length = 100)
	private String description;
	
	//@Embedded
	//RowInfo rowInfo_1;
	/*
   @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy="roles")
   @JoinTable(name = "UserRole",
	          joinColumns = {@JoinColumn(name = "id_Role")},
	          inverseJoinColumns = {@JoinColumn(name = "id_User")})
	 
	private List<User> users;
	*/
   
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="role", targetEntity=UserRole.class)
	private List<UserRole> userRoles;
    
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="role", targetEntity=RoleProgram.class)
	private List<RoleProgram> rolePrograms;


	public Role() {
		super();
		
	}	

	
	// Constructors without the common fields
	public Role(Long id) {
		super();
		/* Example of assignment
		this.id = id;
		this.type = type;
		this.description = description;
		*/
	}


	// Constructors with all fields
	public Role(/* all fields*/
			String recordStatus, String sessionId, String createLogin,
			String createApp, Timestamp createTimestamp,
			String modifyLogin, String modifyApp, Timestamp modifyTimestamp) {
		super();

		
		this.id = id;
		/* Example of assignment
		this.type = type;
		this.description = description;
		*/


		this.rowInfo.setRecordStatus(recordStatus);
		this.rowInfo.setSessionId(sessionId);
		this.rowInfo.setCreateLogin(createLogin);
		this.rowInfo.setCreateApp(createApp);
		//this.rowInfo.setCreateDate(createDate);
		this.rowInfo.setCreateTimestamp(createTimestamp);
		this.rowInfo.setModifyLogin(modifyLogin);
		this.rowInfo.setModifyApp(modifyApp);
		//this.rowInfo.setModifyDate(modifyDate);
		this.rowInfo.setModifyTimestamp(modifyTimestamp);
	}


	@Override
	public String toString() {
		
		StringBuffer buf = new StringBuffer();
		buf.append("Role: [");
		buf.append("id=" + id + ", ");

		/* All fields
		buf.append("type=" + type + ", ");
		buf.append("description=" + description + ", ");
		buf.append("isProduced=" + isProduced + ", ");
		buf.append("isPurchased=" + isPurchased + ", ");
		buf.append("isForSales=" + isForSale + ", ");
		*/
		// Common fields
		buf.append("record status=" + rowInfo.getRecordStatus() + ", ");
		buf.append("session id=" + rowInfo.getSessionId() + ", ");
		buf.append("create login=" + rowInfo.getCreateLogin() + ", ");
		buf.append("create app=" + rowInfo.getCreateApp() + ", ");
		//buf.append("create date=" + rowInfo.getCreateDate() + ", ");
		buf.append("create timestamp=" + rowInfo.getCreateTimestamp() + ", ");
		buf.append("modify login=" + rowInfo.getModifyLogin() + ", ");
		buf.append("modify app=" + rowInfo.getModifyApp() + ", ");
		//buf.append("modify date=" + rowInfo.getModifyDate() + ", ");
		buf.append("modify time=" + rowInfo.getModifyTimestamp() + ", ");		
		buf.append("version=" + version);
		buf.append("]");
		return buf.toString();		
			
	}

	// The need for an equals() method is discussed at http://www.hibernate.org/109.html

	@Override
	public boolean equals(Object obj) {
		return (obj == this) || (obj instanceof Role) && getId() != null && ((Role) obj).getId().equals(this.getId());
	}

	// The need for a hashCode() method is discussed at http://www.hibernate.org/109.html

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
	protected void prePersist() throws BusinessException {
		validate();
		
		rowInfo.setRecordStatus("A");
		
	    java.util.Date today = new java.util.Date();
	    //System.out.println(today.getTime());
	    
	    //rowInfo.setModifyDate(new java.sql.Date(today.getTime()));
	    rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	    //rowInfo.setCreateDate(rowInfo.getModifyDate());
	    rowInfo.setCreateTimestamp(rowInfo.getModifyTimestamp());		
				
	}
*/
	@PostPersist
	void postPersist() throws BusinessException {

	}
	
	@PostLoad
	void postLoad() {

	}
/*
	@PreUpdate
	protected void preUpdate() throws BusinessException {
		if(rowInfo.getRecordStatus()!="D")
		{
			validate();
		}
		java.util.Date today = new java.util.Date();

		//rowInfo.setModifyDate(new java.sql.Date(today.getTime()));
		rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
		
	}
*/
	@PreRemove
	void preRemove() throws BusinessException {
		// Check business rules here, eg.
		// if (entity.getParts().size() > 0) {
		// throw new CannotDeleteIsNotEmptyException(this, id, "Part");
		// }

		// There's no need to remove me from the parent(s) - that's the caller's
		// responsibility (and for performance, it might not bother)
	}

	public void validate() throws BusinessException  {
	
		// Validate syntax...

		if (StringUtil.isEmpty(role)) {
			throw new ValueRequiredException(this, "Role");		
		}

		if (StringUtil.isEmpty(description)) {
			throw new ValueRequiredException(this, "Role_Description");
		}

		/*
		if (StringUtil.isEmpty(lastName)) {
			throw new ValueRequiredException(this, "User_lastName");
		}

		// Validate semantics...

		if (expiryDate != null && loginId.equals(ADMIN_LOGINID)) {
			throw new GenericBusinessException("User_expirydate_not_permitted_for_user", new Object[] { ADMIN_LOGINID });
		}

		 */
		//throw new ValueRequiredException(this, "Please remove this line");
	}
	
	

	// Common EJB field
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}



	public void setVersion(Long version) {
		this.version = version;
	}




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


/*
	public java.sql.Date getModifyDate() {
		return rowInfo.getModifyDate();
	}



	public void setModifyDate(java.sql.Date modifyDate) {
		this.rowInfo.setModifyDate(modifyDate);
	}

*/

	public java.sql.Timestamp getModifyTimestamp() {
		return rowInfo.getModifyTimestamp();
	}



	public void setModifyTimestamp(java.sql.Timestamp modifyTimestamp) {
		this.rowInfo.setModifyTimestamp(modifyTimestamp);
	}


	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}


	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}


	/**
	 * @return the userRoles
	 */
	public List<UserRole> getUserRoles() {
		return userRoles;
	}


	/**
	 * @param userRoles the userRoles to set
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}


	/**
	 * @return the rolePrograms
	 */
	public List<RoleProgram> getRolePrograms() {
		return rolePrograms;
	}


	/**
	 * @param rolePrograms the rolePrograms to set
	 */
	public void setRolePrograms(List<RoleProgram> rolePrograms) {
		this.rolePrograms = rolePrograms;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getRoleDescription() {
		return role + " - " + description;
	}


	
}
