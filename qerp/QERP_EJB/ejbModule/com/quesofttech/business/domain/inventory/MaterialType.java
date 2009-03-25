package com.quesofttech.business.domain.inventory;

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

//import com.quesofttech.business.domain.security.User;


import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.ValueRequiredException;
import com.quesoware.util.StringUtil;

import java.util.List;
//import java.util.ArrayList;

@Entity
@Table(name = "MaterialType", uniqueConstraints = { @UniqueConstraint(columnNames = { "matt_Type" }) })
@SuppressWarnings("serial")
public class MaterialType extends BaseEntity {
	
	//private static final long serialVersionUID = 7422574264557894633L;

	//For Postgresql : @SequenceGenerator(name = "materialType_sequence", sequenceName = "materialType_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="materialType_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="MaterialType", valueColumnName="keyField")
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materialType_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "materialType_id")	
	@Column(name = "id_MaterialType", nullable = false)
	private Long id;

	@Version
	@Column(nullable = false)
	private Long version;
	
	// Table field	
		
	@Column(name = "matt_Type", length = 1, nullable = false)
	private String type;
	
	@Column(name = "matt_Description", length = 100, nullable = false)
	private String description;
	
	@Column(name = "matt_IsProduced")
	private boolean  isProduced;
	
	@Column(name = "matt_IsPurchased")
	private boolean  isPurchased;
	
	@Column(name = "matt_IsForSale")
	private boolean  isForSale;

	@Column(name = "matt_IsJIT")
	private boolean  isJIT;
	
	//@Embedded
	//RowInfo rowInfo_1;
	
	
	
	
	// Common fields
	/*
	@Column(name = "matt_RecordStatus", length = 1, nullable = false)
	private String recordStatus;
		
	@Column(name = "matt_SessionId", length = 50, nullable = false)
	private String sessionId;
	
	@Column(name = "matt_CreateLogin", length = 20, nullable = false)
	private String createLogin;
	
	@Column(name = "matt_CreateApp", length = 50, nullable = false)
	private String createApp;
	
	@Column(name = "matt_CreateDate", nullable = false)
	private java.sql.Date createDate;
	
	@Column(name = "matt_CreateTime", nullable = false)
	private java.sql.Time createTime;
	
	@Column(name = "matt_ModifyLogin", length = 20, nullable = false)
	private String modifyLogin;
	
	@Column(name = "matt_ModifyApp", length = 50, nullable = false)
	private String modifyApp;
	
	@Column(name = "matt_ModifyDate", nullable = false)
	private java.sql.Date modifyDate;
	
	@Column(name = "matt_ModifyTime", nullable = false)
	private java.sql.Time modifyTime;
	*/
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="materialType", targetEntity=Material.class)
	private List<Material> materials;// = new List<Material>();
	
	public MaterialType() {
		super();
		
	}	

	public MaterialType(MaterialType materialType)
	{
		
		this(materialType.id,materialType.type,materialType.description,
				materialType.isProduced,materialType.isPurchased,materialType.isForSale,materialType.isJIT,
				null,null,null,null,null,null,null,null);
	}
	
	
	public MaterialType(Long id,String type, String description,
			boolean isProduced, boolean isPurchased, boolean isForSale, boolean isJIT) {
		
		this(id,type,description,isProduced,isPurchased,isForSale,isJIT,null,null,null,null,null,null,null,null);
		this.id = id;
		this.type = type;
		this.description = description;
		this.isProduced = isProduced;
		this.isPurchased = isPurchased;
		this.isForSale = isForSale;
		this.isJIT = isJIT;

	}



	public MaterialType(Long id, String type, String description,
			boolean isProduced, boolean isPurchased, boolean isForSale, boolean isJIT,
			String recordStatus, String sessionId, String createLogin,
			String createApp, Timestamp createTimestamp,
			String modifyLogin, String modifyApp, Timestamp modifyTimestamp) {
		this();
		
		this.id = id;
		this.type = type;
		this.description = description;
		this.isProduced = isProduced;
		this.isPurchased = isPurchased;
		this.isForSale = isForSale;
		this.isJIT = isJIT;
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
		buf.append("MaterialType: [");
		buf.append("id=" + id + ", ");
		buf.append("type=" + type + ", ");
		buf.append("description=" + description + ", ");
		buf.append("isProduced=" + isProduced + ", ");
		buf.append("isPurchased=" + isPurchased + ", ");
		buf.append("isForSales=" + isForSale + ", ");
		buf.append("isJIT=" + isJIT + ", ");
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
		return (obj == this) || (obj instanceof MaterialType) && getId() != null && ((MaterialType) obj).getId().equals(this.getId());
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

		if (StringUtil.isEmpty(type)) {
			//System.out.println("Yeah");
			throw new ValueRequiredException(this, "MaterialType_Type");
		}

		if (StringUtil.isEmpty(description)) {
			throw new ValueRequiredException(this, "MaterialType_Description");
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
	
	

	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public boolean isProduced() {
		return isProduced;
	}



	public void setProduced(boolean isProduced) {
		this.isProduced = isProduced;
	}



	public boolean isPurchased() {
		return isPurchased;
	}



	public void setPurchased(boolean isPurchased) {
		this.isPurchased = isPurchased;
	}



	public boolean isForSale() {
		return isForSale;
	}



	public void setForSale(boolean isForSale) {
		this.isForSale = isForSale;
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


/*
	public java.sql.Date getCreateDate() {
		return rowInfo.getCreateDate();
	}



	public void setCreateDate(java.sql.Date createDate) {
		this.rowInfo.setCreateDate(createDate);
	}
*/


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
	 * @return the isJIT
	 */
	public boolean isJIT() {
		return isJIT;
	}



	/**
	 * @param isJIT the isJIT to set
	 */
	public void setJIT(boolean isJIT) {
		this.isJIT = isJIT;
	}



	/**
	 * @return the materials
	 */
	public List<Material> getMaterials() {
		return materials;
	}



	/**
	 * @param materials the materials to set
	 */
	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}


	// Useful properties
	
	/*
	 *  Property : Type - Description
	 */
	public String getTypeDescription() {
		return type + " - " + description;
	}

	
}