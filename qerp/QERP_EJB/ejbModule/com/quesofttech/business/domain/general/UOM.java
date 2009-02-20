package com.quesofttech.business.domain.general;

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
@Table(name = "UOM", uniqueConstraints = { @UniqueConstraint(columnNames = { "uom_Unit" }) })
@SuppressWarnings("serial")
public class UOM extends BaseEntity {
	

	//For Postgresql : @SequenceGenerator(name = "UOM_sequence", sequenceName = "UOM_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="UOM_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="UOM", valueColumnName="keyField")
	
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UOM_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "UOM_id")
	@Id
	@Column(name = "id_UOM", nullable = false)
	private Long id;
	
	@Version
	@Column(nullable = true)
	private Long version;


	// Example of field	
	//@Column(name = "matt_Type", length = 1, nullable = false)
	//private String type;
	
	
	@Column(name = "uom_Unit", length = 10, nullable = false)
	private String unit;

	@Column(name = "uom_ShortForm", length = 5, nullable = false)
	private String shortForm;

	@Column(name = "uom_SingularLabel", length = 5, nullable = false)
	private String singularLabel;

	@Column(name = "uom_PluralLabel", length = 5)
	private String pluralLabel;

	@Column(name = "uom_Symbol", length = 5)
	private String symbol;



	
	//@Embedded
	//RowInfo rowInfo_1;
	
	/*
	@ManyToOne
	@JoinColumn(name="fk_<ForeignTable>")	
	private <ForeignTable> <foreignTable>;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="uom", targetEntity=<ForeignTable>.class)
	private List<<ForeignTable>> <foreignTable>s; // = new List<<ForeignTable>>();
	*/

	public UOM() {
		super();
	        this.rowInfo = new RowInfo();
	}	

	
	// Constructors without the common fields
	//public UOM(/* all fields */) {
	//	super();
		/* Example of assignment
		this.id = id;
		this.type = type;
		this.description = description;
		*/
	//}


	// Constructors with all fields
	public UOM(/* all fields*/
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
		buf.append("UOM: [");
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
		return (obj == this) || (obj instanceof UOM) && getId() != null && ((UOM) obj).getId().equals(this.getId());
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
	protected void preUpdate() throws BusinessException {
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

	public void validate() throws BusinessException  {

		
		// Validate syntax...

		if (StringUtil.isEmpty(unit)) {
			throw new ValueRequiredException(this, "UOM_Unit");
		}

		if (StringUtil.isEmpty(shortForm)) {
			throw new ValueRequiredException(this, "UOM_ShortForm");
		}

		
		if (StringUtil.isEmpty(singularLabel)) {
			throw new ValueRequiredException(this, "UOM_SingularLabel");
		}
		

		// Validate semantics...
		/*	
		if (expiryDate != null && loginId.equals(ADMIN_LOGINID)) {
			throw new GenericBusinessException("User_expirydate_not_permitted_for_user", new Object[] { ADMIN_LOGINID });
		}
		*/
		 
		//throw new ValueRequiredException(this, this.getClass().getName() + " [validate] Please remove this line and code the validation logic");
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


	/**
	 * @return the shortForm
	 */
	public String getShortForm() {
		return shortForm;
	}


	/**
	 * @param shortForm the shortForm to set
	 */
	public void setShortForm(String shortForm) {
		this.shortForm = shortForm;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getSingularLabel() {
		return singularLabel;
	}


	public void setSingularLabel(String singularLabel) {
		this.singularLabel = singularLabel;
	}


	public String getPluralLabel() {
		return pluralLabel;
	}


	public void setPluralLabel(String pluralLabel) {
		this.pluralLabel = pluralLabel;
	}


	public String getSymbol() {
		return symbol;
	}


	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	
}
