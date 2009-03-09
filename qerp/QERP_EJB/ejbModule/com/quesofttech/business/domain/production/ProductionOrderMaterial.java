package com.quesofttech.business.domain.production;

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
import javax.persistence.Version;
import javax.persistence.Embedded;
//import javax.persistence.SequenceGenerator;


import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.business.domain.sales.SalesOrder;
import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.common.exception.ValueRequiredException;
import com.quesofttech.business.common.exception.GenericBusinessException;
import com.quesofttech.util.StringUtil;

import java.util.List;
import java.util.logging.Logger;
//import java.util.ArrayList;

@Entity
@Table(name = "ProductionOrderMaterial", uniqueConstraints = { @UniqueConstraint(columnNames = { "fk_ProductionOrder","fk_ProductionOrderOperation","fk_Material" }) })
@SuppressWarnings("serial")
public class ProductionOrderMaterial extends BaseEntity {
	

	//For Postgresql : @SequenceGenerator(name = "ProductionOrderMaterial_sequence", sequenceName = "ProductionOrderMaterial_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="ProductionOrderMaterial_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="ProductionOrderMaterial", valueColumnName="keyField")
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProductionOrderMaterial_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ProductionOrderMaterial_id")	
	@Column(name = "id_ProductionOrderMaterial", nullable = false)
	private Long id;
	
	@Version
	@Column(nullable = false)
	private Long version;


	// Example of field	
	//@Column(name = "matt_Type", length = 1, nullable = false)
	//private String type;
	@Column(name = "prodom_QuantityRequired", nullable = false)
	private Double quantityRequired;
		
	@Column(name = "prodom_QuantityConsumed", nullable = false)
	private Double quantityConsumed;
	
	/*
	@Column(name = "prodom_Sequence", nullable = false)
	private Double sequence;
	*/
	
	
	//@Embedded
	//RowInfo rowInfo_1;
	
	// Foreign keys
	@ManyToOne(cascade={CascadeType.REFRESH})
    @JoinColumn(name="fk_ProductionOrder")	
	private ProductionOrder productionOrder;
	
	@ManyToOne
    @JoinColumn(name="fk_ProductionOrderOperation")	
	private ProductionOrderOperation productionOrderOperation;	
	
	
	// Foreign keys
	@ManyToOne
    @JoinColumn(name="fk_Material")	
	private Material material;
	
	//@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="ProductionOrderMaterial", targetEntity=<ForeignTable>.class)
	//private List<<ForeignTable>> <foreignTable>s; // = new List<<ForeignTable>>();
	
	public ProductionOrderMaterial() {
		super();
		
	}	

	
	// Constructors without the common fields
	//public ProductionOrderMaterial(/* all fields */) {
	//	super();
		/* Example of assignment
		this.id = id;
		this.type = type;
		this.description = description;
		*/
	//}


	// Constructors with all fields
	public ProductionOrderMaterial(/* all fields*/
			String recordStatus, String sessionId, String createLogin,
			String createApp, Timestamp createTimestamp,
			String modifyLogin, String modifyApp, Timestamp modifyTimestamp) {
		this();

		
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
		buf.append("ProductionOrderMaterial: [");
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
		return (obj == this) || (obj instanceof ProductionOrderMaterial) && getId() != null && ((ProductionOrderMaterial) obj).getId().equals(this.getId());
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
		System.out.println("prePersist of ProductionOrderMaterial");
		
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


	@PreUpdate
	protected void preUpdate() throws BusinessException {
		super.preUpdate();

		System.out.println("Production Order Material preUpdate");
		
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

	public void validate() throws BusinessException  {

		
		// Validate syntax...

		if (this.material == null) {
			//System.out.println("Yeah");
			throw new ValueRequiredException(this, "Material");
		}

		if (quantityRequired == null||quantityRequired==0) {
			//System.out.println("Yeah");
			throw new ValueRequiredException(this, "ProductionOrderMaterial_QuantityOrder");
		}
		
		//if (StringUtil.isEmpty(description)) {
		//	throw new ValueRequiredException(this, "MaterialType_Description");
		//}

		/*
		if (StringUtil.isEmpty(lastName)) {
			throw new ValueRequiredException(this, "User_lastName");
		}
		*/

		// Validate semantics...

		if (this.quantityRequired <=0) {
			throw new GenericBusinessException("ProductionOrder_QuantityOrderMustGreaterThanZero", new Object[] { });
		}
		
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
	 * @return the quantityRequired
	 */
	public Double getQuantityRequired() {
		return quantityRequired;
	}


	/**
	 * @param quantityRequired the quantityOrder to set
	 */
	public void setQuantityRequired(Double quantityRequired) {
		this.quantityRequired = quantityRequired;
	}


	/**
	 * @return the quantityConsumed
	 */
	public Double getQuantityConsumed() {
		return quantityConsumed;
	}


	/**
	 * @param quantityConsumed the quantityConsumed to set
	 */
	public void setQuantityConsumed(Double quantityConsumed) {
		this.quantityConsumed = quantityConsumed;
	}


	/**
	 * @return the productionOrder
	 */
	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}


	/**
	 * @param productionOrder the productionOrder to set
	 */
	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
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
		if(material!=null)			
			return material.getCode();
		else
			return "";
	}
	
}
