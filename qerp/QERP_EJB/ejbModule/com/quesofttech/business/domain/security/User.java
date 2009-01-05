package com.quesofttech.business.domain.security;

import java.io.Serializable;
//import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.quesofttech.business.common.exception.AuthenticationException;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.GenericBusinessException;
import com.quesofttech.business.common.exception.ValueRequiredException;
import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.util.StringUtil;

/**
 * The User entity.
 */
@Entity
@Table(name = "User", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_Login" }) })
@SuppressWarnings("serial")
public class User extends BaseEntity {
	
	private static final long serialVersionUID = 4365329688768691261L;
	static public final String ADMIN_LOGINID = "admin";

	static public final String[] SALUTATIONS = { "Ms", "Mrs", "Mr", "Dr", "Prof" };

	//For Postgresql : @SequenceGenerator(name = "material_sequence", sequenceName = "material_id_seq")
	//Generic solution : (Use a table named PrimaryKeys, with 2 fields , tableName &  keyField)
	@TableGenerator(  name="user_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="user", valueColumnName="keyField")	
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "user_id")			
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_User", nullable = false)
	private Long id;

	@Version
	@Column(nullable = false)
	private Long version;

	@Column(name = "user_Login", length = 20, nullable = false)
	private String login;

	@Column(name = "user_FirstName", length = 30, nullable = false)
	private String firstName;

	@Column(name = "user_LastName", length = 30, nullable = false)
	private String lastName;

	@Column(name = "user_Salutation", length = 10)
	private String salutation;
	
	@Column(name = "user_Telephone", length = 15)
	private String telephone;	
	
	@Column(name = "user_EmailAddress", length = 50)
	private String emailAddress;

	@Column(name = "user_ExpiryDate")
	@Temporal(TemporalType.TIMESTAMP)
	private java.sql.Timestamp expiryDate;

	// Use LAZY fetch - we NEVER want this User object detached with the password in it!!!
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	// Do not use @PrimaryKeyJoinColumn because it ALWAYS eager fetches (in JBoss implementation and maybe others too)
	@JoinColumn(name = "fk_UserPassword")
	private UserPassword userPassword = new UserPassword();

	/*
   @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy="users")
   @JoinTable(name = "UserRole",
	          joinColumns = {@JoinColumn(name = "id_User")},
	          inverseJoinColumns = {@JoinColumn(name = "id_Role")})
	private List<Role> roles;
	*/
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="user", targetEntity=UserRole.class)
	private List<UserRole> userRoles;
   
   
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="user", targetEntity=UserProgram.class)
	private List<UserProgram> userPrograms;
	
	public User() {
		super();
		this.rowInfo = new RowInfo();
	}	
	
	@Embedded
	RowInfo rowInfo;
	
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("User: [");
		buf.append("id=" + id + ", ");
		buf.append("login=" + login + ", ");
		buf.append("salutation=" + salutation + ", ");
		buf.append("firstName=" + firstName + ", ");
		buf.append("lastName=" + lastName + ", ");
		buf.append("emailAddress=" + emailAddress + ", ");
		buf.append("expiryDate=" + expiryDate + ", ");
		buf.append("telephone=" + telephone + ", ");
		buf.append("version=" + version);
		buf.append("]");
		
		return buf.toString();
	}

	// The need for an equals() method is discussed at http://www.hibernate.org/109.html

	@Override
	public boolean equals(Object obj) {
		return (obj == this) || (obj instanceof User) && getId() != null && ((User) obj).getId().equals(this.getId());
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

	@PrePersist
	void prePersist() throws BusinessException {
		validate();
rowInfo.setRecordStatus("A");
		
	    java.util.Date today = new java.util.Date();
	    //System.out.println(today.getTime());
	    
	    //rowInfo.setModifyDate(new java.sql.Date(today.getTime()));
	    rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	    //rowInfo.setCreateDate(rowInfo.getModifyDate());
	    rowInfo.setCreateTimestamp(rowInfo.getModifyTimestamp());
	}

	@PostLoad
	void postLoad() {
	}

	@PreUpdate
	void preUpdate() throws BusinessException {
		if(rowInfo.getRecordStatus()!="D")
		{
			validate();
		}
			java.util.Date today = new java.util.Date();

			rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	}

	@PreRemove
	void preRemove() throws BusinessException {
		// Check business rules here, eg.
		// if (entity.getParts().size() > 0) {
		// throw new CannotDeleteIsNotEmptyException(this, id, "Part");
		// }

		// There's no need to remove me from the parent(s) - that's the caller's
		// responsibility (and for performance, it might not bother)
	}

	public void validate() throws BusinessException {

		// Validate syntax...

		if (StringUtil.isEmpty(login)) {
			throw new ValueRequiredException(this, "User_loginId");
		}

		if (StringUtil.isEmpty(firstName)) {
			throw new ValueRequiredException(this, "User_firstName");
		}

		if (StringUtil.isEmpty(lastName)) {
			throw new ValueRequiredException(this, "User_lastName");
		}

		// Validate semantics...

		if (expiryDate != null && login.equals(ADMIN_LOGINID)) {
			throw new GenericBusinessException("User_expirydate_not_permitted_for_user", new Object[] { ADMIN_LOGINID });
		}

	}

	public void authenticate(String password) throws AuthenticationException {
		System.out.println("[User] Just In");
		if(this.userPassword!=null)
			this.userPassword.authenticate(password);
		else
			System.out.println("[User] UserPassword = null");
	
	}

	/**
	 * This method provides a way for users to change their own passwords.
	 */
	void changePassword(String currentPassword, String newPassword) throws BusinessException {
		this.userPassword.changePassword(currentPassword, newPassword);
	}
/*
	public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
*/
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * This method provides a way for security officers to "reset" the userPassword.
	 */
	void setPassword(String newPassword) throws BusinessException {
		this.userPassword.setPassword(newPassword);
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

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public java.sql.Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(java.sql.Timestamp expiryDate) {
		java.util.Date today = new java.util.Date();
		expiryDate = new java.sql.Timestamp(today.getTime());
		this.expiryDate = expiryDate;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	// Common EJB field
	public Long getId() {
		return id;
	}
/*
	public void setId(Long id) {
		this.id = id;
	}
*/
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


	public java.sql.Timestamp getModifyTimestamp() {
		return rowInfo.getModifyTimestamp();
	}



	public void setModifyTimestamp(java.sql.Timestamp modifyTimestamp) {
		this.rowInfo.setModifyTimestamp(modifyTimestamp);
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
	 * @return the userPrograms
	 */
	public List<UserProgram> getUserPrograms() {
		return userPrograms;
	}

	/**
	 * @param userPrograms the userPrograms to set
	 */
	public void setUserPrograms(List<UserProgram> userPrograms) {
		this.userPrograms = userPrograms;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
