package com.quesofttech.business.domain.sales;

import java.io.Serializable;
//import java.sql.Date;
//import java.sql.Time;
import java.sql.Timestamp;

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
import javax.persistence.Embedded;
import javax.persistence.Version;
//import javax.persistence.SequenceGenerator;


import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.common.exception.ValueRequiredException;
import com.quesofttech.util.StringUtil;

import java.util.List;
//import java.util.ArrayList;

@Entity
@Table(name = "SalesOrderMaterial", uniqueConstraints = { @UniqueConstraint(columnNames = { "fk_SalesOrder","fk_Material" }) })
@SuppressWarnings("serial")
public class SalesOrderMaterial extends BaseEntity {
	

	//For Postgresql : @SequenceGenerator(name = "SalesOrderMaterial_sequence", sequenceName = "SalesOrderMaterial_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="SalesOrderMaterial_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="SalesOrderMaterial", valueColumnName="keyField")
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SalesOrderMaterial_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SalesOrderMaterial_id")	
	@Column(name = "id_SalesOrderMaterial", nullable = false)
	private Long id;
	
	@Version
	@Column(nullable = false)
	private Long version;
	
	// Example of field	
	@Column(name = "sod_Line", nullable = false)
	private Long line;
	
	@Column(name = "sod_CustomerPOLine", nullable = false)
	private Long customerPOLine;
	
	@Column(name = "sod_QuantityOrder", nullable = false)
	private Double quantityOrder;
	
	@Column(name = "sod_QuantityShipped", nullable = false)
	private Double quantityShipped;


	@Column(name = "sod_Price", precision=4,  nullable = false)
	private Double price;

	
	@Column(name = "sod_Amount", precision=4,  nullable = false)
	private Double amount;
	
	/*
	// The sales price Id when this sales order line is created 
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="SalesOrderMaterial", targetEntity=SalesPrice.class)
	private List<SalesPrice> salePrices; // = new List<<ForeignTable>>();
	*/
		
	
	@Embedded
	RowInfo rowInfo;
	
	
	/*
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="SalesOrderMaterial", targetEntity=<ForeignTable>.class)
	private List<<ForeignTable>> <foreignTable>s; // = new List<<ForeignTable>>();
	*/
	
	// Foreign keys
	@ManyToOne
    @JoinColumn(name="fk_SalesOrder")	
	private SalesOrder salesOrder;
	
	
	// Foreign keys
	@ManyToOne
    @JoinColumn(name="fk_Material")	
	private Material material;
	
	
	public SalesOrderMaterial() {
		super();
		this.rowInfo = new RowInfo();
	}	

	
	// Constructors without the common fields
	public SalesOrderMaterial(Double line, Double customerPOLine, Double quantityOrder, Double quantityShipped, Double price) {
		this();
		/* Example of assignment
		this.id = id;
		this.type = type;
		this.description = description;
		*/
	}


	// Constructors with all fields
	public SalesOrderMaterial(/* all fields*/
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
		buf.append("SalesOrderMaterial: [");
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
		return (obj == this) || (obj instanceof SalesOrderMaterial) && getId() != null && ((SalesOrderMaterial) obj).getId().equals(this.getId());
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
		
		
		amount = quantityOrder * price;
		
		rowInfo.setRecordStatus("A");
		
	    java.util.Date today = new java.util.Date();
	    System.out.println("[SalesOrderLine.java] prePersist");
	    
	    //rowInfo.setModifyDate(new java.sql.Date(today.getTime()));
	    rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	    //rowInfo.setCreateDate(rowInfo.getModifyDate());
	    rowInfo.setCreateTimestamp(rowInfo.getModifyTimestamp());	
				
	    System.out.println(this.toString());
	}

	@PostPersist
	void postPersist() throws BusinessException {

	}
	
	@PostLoad
	void postLoad() {

	}

	@PreUpdate
	void preUpdate() throws BusinessException {
		if(rowInfo.getRecordStatus()!="D")
		{
			validate();
			amount = quantityOrder * price;
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

		if (quantityOrder==null||quantityOrder == 0) {
			//System.out.println("Yeah");
			throw new ValueRequiredException(this, "SalesOrderMaterial_QuantityOrder");
		}

		if (price==null||price == 0) {
			//System.out.println("Yeah");
			throw new ValueRequiredException(this, "SalesOrderMaterial_UnitPrice");
		}
		
		if(material==null) {
			throw new ValueRequiredException(this, "Mateiral");	
		}
		
		if(line==null||line==0) {
			throw new ValueRequiredException(this, "Line");	
		}
		
		
		//if (StringUtil.isEmpty(description)) {
		//	throw new ValueRequiredException(this, "MaterialType_Description");
		//}

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
	 * @return the line
	 */
	public Long getLine() {
		return line;
	}


	/**
	 * @param line the line to set
	 */
	public void setLine(Long line) {
		this.line = line;
	}


	/**
	 * @return the customerPOLine
	 */
	public Long getCustomerPOLine() {
		return customerPOLine;
	}


	/**
	 * @param customerPOLine the customerPOLine to set
	 */
	public void setCustomerPOLine(Long customerPOLine) {
		this.customerPOLine = customerPOLine;
	}


	/**
	 * @return the quantityOrder
	 */
	public Double getQuantityOrder() {
		return quantityOrder;
	}


	/**
	 * @param quantityOrder the quantityOrder to set
	 */
	public void setQuantityOrder(Double quantityOrder) {
		this.quantityOrder = quantityOrder;
	}


	/**
	 * @return the quantityShipped
	 */
	public Double getQuantityShipped() {
		return quantityShipped;
	}


	/**
	 * @param quantityShipped the quantityShipped to set
	 */
	public void setQuantityShipped(Double quantityShipped) {
		this.quantityShipped = quantityShipped;
	}


	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}


	/**
	 * @return the salesOrder
	 */
	public SalesOrder getSalesOrder() {
		return salesOrder;
	}


	/**
	 * @param salesOrder the salesOrder to set
	 */
	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}


	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}


	/**
	 * @param material the material to set
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public String getMaterialCode()
	{
		if(material!=null) return material.getCode();
		else return "";
	}


	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}


	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	

	
	/*
	 * Properties
	 */
	
	public String getDocNoMaterial()
	{
		return this.getSalesOrder().getDocNo() + "-" + this.material.getCode();
	}
	
	
}
