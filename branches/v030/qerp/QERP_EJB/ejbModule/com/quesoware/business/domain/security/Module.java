package com.quesoware.business.domain.security;

import java.io.Serializable;
//import java.sql.Date;
//import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
//import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.TableGenerator;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.persistence.Embedded;

import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.RowInfo;

//import com.quesoware.business.domain.security.User;


import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.ValueRequiredException;
import com.quesoware.util.StringUtil;

import java.util.List;
//import java.util.ArrayList;

@Entity
@Table(name = "Module", uniqueConstraints = { @UniqueConstraint(columnNames = { "module_Code" }) })
@SuppressWarnings("serial")
public class Module extends BaseEntity {
	
	//private static final long serialVersionUID = 7422574264557894633L;

	//For Postgresql : @SequenceGenerator(name = "module_sequence", sequenceName = "module_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="module_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="module", valueColumnName="keyField")
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "module_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "module_id")	
	@Column(name = "id_Module", nullable = false)
	private Long id;

	@Version
	@Column(nullable = false)
	private Long version;
	
	// Table field	
		
	@Column(name = "module_Code", length = 10, nullable = false)
	private String code;
	
	@Column(name = "module_Description", length = 100, nullable = false)
	private String description;
	
	@Column(name = "module_IsEnabled")
	private Boolean  isEnabled;
	
	@Column(name = "module_IsPurchased")
	private Boolean  isPurchased;
	
	@Column(name = "module_IsBasic")
	private Boolean  isBasic;
	
	//@Embedded
	//RowInfo rowInfo_1;
	
		
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="module", targetEntity=Program.class)
	private List<Program> programs;
	
	public Module() {
		super();
		
	}	

	// Constructors without the common fields
	public Module(Long id) {
		super();
		/* Example of assignment
		this.id = id;
		this.type = type;
		this.description = description;
		*/
	}
	
	public Module(Long id,String code, String description,
			boolean isEnabled, boolean isPurchased, boolean isBasic) {
		
		this(id,code,description,isEnabled,isPurchased,isBasic,null,null,null,null,null,null,null,null);
		this.id = id;
		this.code = code;
		this.description = description;
		this.isEnabled = isEnabled;
		this.isPurchased = isPurchased;
		this.isBasic = isBasic;
	}



	public Module(Long id, String code, String description,
			Boolean isEnabled, Boolean isPurchased, Boolean isBasic,
			String recordStatus, String sessionId, String createLogin,
			String createApp, Timestamp createTimestamp,
			String modifyLogin, String modifyApp, Timestamp modifyTimestamp) {
		super();
		
		this.id = id;
		this.code = code;
		this.description = description;
		this.isEnabled = isEnabled;
		this.isPurchased = isPurchased;
		this.isBasic = isBasic;
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
		buf.append("Module: [");
		buf.append("id=" + id + ", ");
		buf.append("code=" + code + ", ");
		buf.append("description=" + description + ", ");
		buf.append("isEnabled=" + isEnabled + ", ");
		buf.append("isPurchased=" + isPurchased + ", ");
		buf.append("isBasic=" + isBasic + ", ");		
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
		return (obj == this) || (obj instanceof Module) && getId() != null && ((Module) obj).getId().equals(this.getId());
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
		
		//toString();
				
	}
*/
	@PostPersist
	void postPersist() throws BusinessException {
		//System.out.println("This is postPersist in MT");
	}
	
	@PostLoad
	void postLoad() {
		//_loaded_password = password;
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
		
		//setModifyDateTime(modifyDate,modifyTime);		
		
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

		if (StringUtil.isEmpty(code)) {
			//System.out.println("Yeah");
			throw new ValueRequiredException(this, "Module_Type");
		}

		if (StringUtil.isEmpty(description)) {
			throw new ValueRequiredException(this, "Module_Description");
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
	}
	
	


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




	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}



	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}



	/**
	 * @return the isPurchased
	 */
	public boolean isPurchased() {
		return isPurchased;
	}



	/**
	 * @param isPurchased the isPurchased to set
	 */
	public void setPurchased(boolean isPurchased) {
		this.isPurchased = isPurchased;
	}



	/**
	 * @return the isBasic
	 */
	public boolean isBasic() {
		return isBasic;
	}



	/**
	 * @param isBasic the isBasic to set
	 */
	public void setBasic(boolean isBasic) {
		this.isBasic = isBasic;
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
	 * @return the programs
	 */
	public List<Program> getPrograms() {
		return programs;
	}



	/**
	 * @param programs the programs to set
	 */
	public void setPrograms(List<Program> programs) {
		this.programs = programs;
	}

	// Useful properties
	
	/*
	 *  Property : Code - Description
	 */
	public String getCodeDescription() {
		return code + " - " + description;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean getIsPurchased() {
		return isPurchased;
	}

	public void setIsPurchased(Boolean isPurchased) {
		this.isPurchased = isPurchased;
	}

	public Boolean getIsBasic() {
		return isBasic;
	}

	public void setIsBasic(Boolean isBasic) {
		this.isBasic = isBasic;
	}

	
}