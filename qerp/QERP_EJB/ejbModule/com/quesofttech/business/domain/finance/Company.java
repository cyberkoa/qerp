package com.quesofttech.business.domain.finance;

import java.io.Serializable;
//import java.sql.Date;
//import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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


import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.business.domain.embeddable.Address;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.ValueRequiredException;
import com.quesoware.business.domain.production.ProductionOrder;
import com.quesoware.util.StringUtil;

import java.util.List;
//import java.util.ArrayList;

@Entity
@Table(name = "Company", uniqueConstraints = { @UniqueConstraint(columnNames = { "company_code" }) })
@SuppressWarnings("serial")
public class Company extends BaseEntity {
	

	//For Postgresql : @SequenceGenerator(name = "Company_sequence", sequenceName = "Company_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="Company_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="Company", valueColumnName="keyField")
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Company_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Company_id")	
	@Column(name = "id_Company", nullable = false)
	private Long id;
	
	@Version
	@Column(nullable = false)
	private Long version;


	// Example of field	
	@Column(name = "company_code", length = 10, nullable = false)
	private String code;
	
	@Column(name = "company_name", length = 100, nullable = false)
	private String name;

	/*
	// Foreign keys
	@ManyToOne
    @JoinColumn(name="fk_Currency")	
	private Currency currency;	
	*/
	
	
	@Embedded
    @AttributeOverrides( {
        @AttributeOverride(name="buildingLot", column = @Column(name="company_BuildingLot") ),
        @AttributeOverride(name="street", column = @Column(name="company_Street") ),
        @AttributeOverride(name="area", column = @Column(name="company_Area") ),
        @AttributeOverride(name="state", column = @Column(name="company_State") ),
        @AttributeOverride(name="city", column = @Column(name="company_City") ),
        @AttributeOverride(name="postalCode", column = @Column(name="company_PostalCode") ),
        @AttributeOverride(name="country", column = @Column(name="company_Country") )
    } )

	Address address;
	
	
	
	//@Embedded
	//RowInfo rowInfo_1;
	
	
	/*
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="Company", targetEntity=<ForeignTable>.class)
	private List<<ForeignTable>> <foreignTable>s; // = new List<<ForeignTable>>();
	*/
	public Company() {
		super();	
		
		
		this.address = new Address();
	}	

	
	// Constructors without the common fields
	public Company(/* all fields */
	String code,String name		
	) {
		this();
		this.code = code;
		this.name = name;
		
		/* Example of assignment
		this.id = id;
		this.type = type;
		this.description = description;
		*/
	}


	// Constructors with all fields
	public Company(/* all fields*/
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
		this.rowInfo.setCreateTimestamp(createTimestamp);
		this.rowInfo.setModifyLogin(modifyLogin);
		this.rowInfo.setModifyApp(modifyApp);
		this.rowInfo.setModifyTimestamp(modifyTimestamp);
	}


	@Override
	public String toString() {
		
		StringBuffer buf = new StringBuffer();
		buf.append("Company: [");
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
		return (obj == this) || (obj instanceof Company) && getId() != null && ((Company) obj).getId().equals(this.getId());
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
			validate();
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

	protected void validate() throws BusinessException  {

		
		// Validate syntax...

		if (StringUtil.isEmpty(code)) {
			//System.out.println("Yeah");
			throw new ValueRequiredException(this, "Company_Code");
		}

		if (StringUtil.isEmpty(name)) {
			throw new ValueRequiredException(this, "Company_Name");
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


	public java.sql.Timestamp getModifyTimestamp() {
		return rowInfo.getModifyTimestamp();
	}



	public void setModifyTimestamp(java.sql.Timestamp modifyTimestamp) {
		this.rowInfo.setModifyTimestamp(modifyTimestamp);
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	
	// Useful properties
	
	/*
	 *  Property : Code - Name
	 */
	public String getCodeName() {
		return code + " - " + name;
	}
	
	
	
}
