package com.quesofttech.business.domain.system;

import java.io.Serializable;
//import java.sql.Date;
//import java.sql.Time;
import java.sql.Timestamp;

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
import javax.persistence.OneToMany;
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
import com.quesofttech.business.common.exception.GenericBusinessException;
import com.quesofttech.util.StringUtil;

import java.util.List;
//import java.util.ArrayList;

@Entity
@Table(name = "DocumentType", uniqueConstraints = { @UniqueConstraint(columnNames = { "<unique_field>" }) })
@SuppressWarnings("serial")
public class DocumentType extends BaseEntity {
	

	//For Postgresql : @SequenceGenerator(name = "DocumentType_sequence", sequenceName = "DocumentType_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="DocumentType_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="DocumentType", valueColumnName="keyField")
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DocumentType_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "DocumentType_id")	
	@Column(name = "id_DocumentType", nullable = false)
	private Long id;
	
	@Version
	@Column(nullable = false)
	private Long version;


	// Example of field	
	@Column(name = "doct_Type", length = 1, nullable = false)
	private String type;
	
	// Example of field	
	@Column(name = "doct_Description", length = 100, nullable = false)
	private String description;
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}



	@Column(name = "doct_Prefix", length = 5)
	private String prefix;
	
	
	@Column(name = "doct_RunningNo", nullable = false)
	private Long runningNo;
	
	// Vendor , Customer , Production or ... 
	@Column(name = "doct_Category", nullable = false)
	private Long catergory;
	
	
	
	
	/*
	@ManyToOne
	@JoinColumn(name="fk_<ForeignTable>")	
	private <ForeignTable> <foreignTable>;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="documentType", targetEntity=<ForeignTable>.class)
	private List<<ForeignTable>> <foreignTable>s; // = new List<<ForeignTable>>();
	*/

	public DocumentType() {
		super();

	}	

	
	// Constructors without the common fields
	//public DocumentType(/* all fields */) {
	//	super();
		/* Example of assignment
		this.id = id;
		this.type = type;
		this.description = description;
		*/
	//}


	


	public Long getCatergory() {
		return catergory;
	}


	public void setCatergory(Long catergory) {
		this.catergory = catergory;
	}


	// Constructors with all fields
	public DocumentType(/* all fields*/
			String recordStatus, String sessionId, String createLogin,
			String createApp, Timestamp createTimestamp,
			String modifyLogin, String modifyApp, Timestamp modifyTimestamp) {
		this();

		
		/* Example of assignment
		this.type = type;
		this.description = description;
		*/


		this.rowInfo.setRecordStatus(recordStatus);
		this.rowInfo.setSessionId(sessionId);
		this.rowInfo.setCreateLogin(createLogin);
		this.rowInfo.setCreateApp(createApp);
		this.rowInfo.setCreateTimestamp(createTimestamp);
		this.rowInfo.setModifyLogin(modifyLogin);
		this.rowInfo.setModifyApp(modifyApp);
		this.rowInfo.setModifyTimestamp(modifyTimestamp);
	}


	@Override
	public String toString() {
		
		StringBuffer buf = new StringBuffer();
		buf.append("DocumentType: [");
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
		buf.append("create timestamp=" + rowInfo.getCreateTimestamp() + ", ");
		buf.append("modify login=" + rowInfo.getModifyLogin() + ", ");
		buf.append("modify app=" + rowInfo.getModifyApp() + ", ");
		buf.append("modify time=" + rowInfo.getModifyTimestamp() + ", ");		
		buf.append("version=" + version);
		buf.append("]");
		return buf.toString();		
			
	}

	// The need for an equals() method is discussed at http://www.hibernate.org/109.html

	@Override
	public boolean equals(Object obj) {
		return (obj == this) || (obj instanceof DocumentType) && getId() != null && ((DocumentType) obj).getId().equals(this.getId());
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
	void prePersist() throws BusinessException {
		validate();
		
		rowInfo.setRecordStatus("A");
		
	    java.util.Date today = new java.util.Date();

	    rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
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
	void preUpdate() throws BusinessException {
		if(rowInfo.getRecordStatus()!="D")
		{
			try
			{
				validate();
			}
			catch(BusinessException be)
			{
				return;
			}
		}
			java.util.Date today = new java.util.Date();

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

	public void validate() throws BusinessException {

		
		// Validate syntax...

		if (StringUtil.isEmpty(type)) {
			//System.out.println("Yeah");
			throw new ValueRequiredException(this, "DocumentType_Type");
		}

		//if (StringUtil.isEmpty(description)) {
		//	throw new ValueRequiredException(this, "DocumentType_Description");
		//}

		/*
		if (StringUtil.isEmpty(lastName)) {
			throw new ValueRequiredException(this, "User_lastName");
		}
		*/

		// Validate semantics...
		/*	
		if (expiryDate != null && loginId.equals(ADMIN_LOGINID)) {
			throw new GenericBusinessException("User_expirydate_not_permitted_for_user", new Object[] { ADMIN_LOGINID });
		}
		*/
		 
		//throw new ValueRequiredException(this, this.getClass().getName() + " [validate] Please remove this line and code the validation logic");
	}
	
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}


	/**
	 * @param prefix the prefix to setnullable = false
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	/**
	 * @return the runningNo
	 */
	public Long getRunningNo() {
		return runningNo;
	}


	/**
	 * @param runningNo the runningNo to set
	 */
	public void setRunningNo(Long runningNo) {
		this.runningNo = runningNo;
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


	public java.sql.Timestamp getModifyTimestamp() {
		return rowInfo.getModifyTimestamp();
	}



	public void setModifyTimestamp(java.sql.Timestamp modifyTimestamp) {
		this.rowInfo.setModifyTimestamp(modifyTimestamp);
	}

	
}
