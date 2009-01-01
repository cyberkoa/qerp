package com.quesofttech.business.domain.sales;

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
import javax.persistence.Embedded;
import javax.persistence.Version;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
//import javax.persistence.SequenceGenerator;


import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.business.domain.embeddable.Address;
import com.quesofttech.business.common.exception.ValueRequiredException;
import com.quesofttech.util.StringUtil;

import java.util.List;
//import java.util.ArrayList;

@Entity
@Table(name = "Customer", uniqueConstraints = { @UniqueConstraint(columnNames = { "customer_Code" }) })
@SuppressWarnings("serial")
public class Customer extends BaseEntity {
	

	//For Postgresql : @SequenceGenerator(name = "Customer_sequence", sequenceName = "Customer_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="Customer_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="Customer", valueColumnName="keyField")
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Customer_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Customer_id")	
	@Column(name = "id_Customer", nullable = false)
	private Long id;
	
	@Version
	@Column(nullable = false)
	private Long version;
	
	
	// Example of field	
	@Column(name = "customer_Code", length = 10, nullable = false)
	private String code;
	

	@Column(name = "customer_Name", length = 50, nullable = false)
	private String name;

	@Column(name = "customer_Telephone", length = 15)
	private String telephone;
	
	@Column(name = "customer_Fax", length = 15)
	private String fax;
	
	@Column(name = "customer_Email", length = 30)
	private String email;

	
	@Embedded
    @AttributeOverrides( {
        @AttributeOverride(name="buildingLot", column = @Column(name="customer_BuildingLot") ),
        @AttributeOverride(name="street", column = @Column(name="customer_Street") ),
        @AttributeOverride(name="area", column = @Column(name="customer_Area") ),
        @AttributeOverride(name="state", column = @Column(name="customer_State") ),
        @AttributeOverride(name="city", column = @Column(name="customer_City") ),
        @AttributeOverride(name="postalCode", column = @Column(name="customer_PostalCode") ),
        @AttributeOverride(name="country", column = @Column(name="customer_Country") )
    } )

	Address address;
	
	
	@Embedded
	RowInfo rowInfo;
	
	
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy="customer", targetEntity=SalesOrder.class)
	private List<SalesOrder> salesOrders; // = new List<<ForeignTable>>();
	
	public Customer() {
		super();
		this.address = new Address();
		this.rowInfo = new RowInfo();
	}	

	
	// Constructors without the common fields
	public Customer(String name, String telephone, String fax, String email) {
		this();
		/* Example of assignment
		this.id = id;
		this.type = type;
		this.description = description;
		*/
	}


	// Constructors with all fields
	public Customer(/* all fields*/
			String recordStatus, String sessionId, String createLogin,
			String createApp, Timestamp createTimestamp,
			String modifyLogin, String modifyApp, Timestamp modifyTimestamp) {
		this();

		/* Example of assignment
		this.id = id;
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
		buf.append("Customer: [");
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
		return (obj == this) || (obj instanceof Customer) && getId() != null && ((Customer) obj).getId().equals(this.getId());
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
	    System.out.println("[Customer.java] " + today.getTime());
	    
	    //rowInfo.setModifyDate(new java.sql.Date(today.getTime()));
	    rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	    //rowInfo.setCreateDate(rowInfo.getModifyDate());
	    rowInfo.setCreateTimestamp(rowInfo.getModifyTimestamp());
	    System.out.println("[CreateTimestamp] " + rowInfo.getCreateTimestamp());
				
	}

	@PostPersist
	void postPersist() throws BusinessException {

	}
	
	@PostLoad
	void postLoadl() {

	}

	@PreUpdate
	void preUpdate() throws BusinessException {
		if(rowInfo.getRecordStatus()!="D")
		{
			validate();
		}
			java.util.Date today = new java.util.Date();

			//rowInfo.setModifyDate(new java.sql.Date(today.getTime()));
			rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
		
		//setModifyDateTime(modifyDate,modifyTime);		
		
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

		if (StringUtil.isEmpty(code)) {
			//System.out.println("Yeah");
			throw new ValueRequiredException(this, "Customer_Code");
		}

		if (StringUtil.isEmpty(name)) {
			throw new ValueRequiredException(this, "Customer_Name");
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
		//return;
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

	public Address getAddress() {
		return address;
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
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}


	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}


	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the salesOrders
	 */
	public List<SalesOrder> getSalesOrders() {
		return salesOrders;
	}


	/**
	 * @param salesOrders the salesOrders to set
	 */
	public void setSalesOrders(List<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}


	public String getAddressBuildingLot() {
		return address.getBuildingLot();
	}
	
	public String getAddressStreet() {
		return address.getStreet();
	}

	public String getAddressPostalCode() {
		return address.getPostalCode();
	}
	
	
	public String getAddressArea() {
		return address.getArea();
	}
	
	public String getAddressCity() {
		return address.getCity();
	}
	
	
	public String getAddressState() {
		return address.getState();
	}
	
	public String getAddressCountry() {
		return address.getCountry();
	}
	
	
	// Useful properties
	
	/*
	 *  Property : Code - Name
	 */
	public String getCodeName() {
		return code + " - " + name;
	}
	
	
}
