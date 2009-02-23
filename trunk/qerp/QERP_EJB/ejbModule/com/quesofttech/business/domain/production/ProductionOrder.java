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

import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.production.ProductionOrder;
import com.quesofttech.business.domain.production.ProductionOrderMaterial;
import com.quesofttech.business.domain.production.ProductionOrderOperation;

import com.quesofttech.business.domain.sales.SalesOrder;

import com.quesofttech.business.domain.system.DocumentType;


import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.business.common.exception.ValueRequiredException;
import com.quesofttech.util.StringUtil;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "ProductionOrder", uniqueConstraints = { @UniqueConstraint(columnNames = { "docNo" }) })
@SuppressWarnings("serial")
public class ProductionOrder extends BaseEntity {
	

	//For Postgresql : @SequenceGenerator(name = "ProductionOrder_sequence", sequenceName = "ProductionOrder_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="ProductionOrder_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="ProductionOrder", valueColumnName="keyField")
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProductionOrder_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ProductionOrder_id")	
	@Column(name = "id_ProductionOrder", nullable = false)
	private Long id;
	
	@Version
	@Column(nullable = false)
	private Long version;

	

	@Column(name = "prodo_Number", nullable = false)
	private Long docNo;
	
	
	// Example of field	
	@Column(name = "prodo_QuantityOrder", nullable = false)
	private Double quantityOrder;
		
	@Column(name = "prodo_QuantityReported", nullable = false)
	private Double quantityReported;
	
	
	@ManyToOne
    @JoinColumn(name="fk_Material")	
	private Material material;	
	
	@ManyToOne
    @JoinColumn(name="fk_SalesOrder")	
	private SalesOrder salesOrder;
	
	
	@ManyToOne
    @JoinColumn(name="fk_DocumentType")	
	private DocumentType documentType;
	
	
	@OneToMany(cascade= {CascadeType.PERSIST}, fetch = FetchType.EAGER, mappedBy="productionOrder", targetEntity=ProductionOrderMaterial.class)
	private List<ProductionOrderMaterial> productionOrderMaterials;
	
	@OneToMany(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy="productionOrder", targetEntity=ProductionOrderOperation.class)
	private List<ProductionOrderOperation> productionOrderOperations;	
	
	
	//@Embedded
	//RowInfo rowInfo_1;
	
	public ProductionOrder() {
		super();
		this.rowInfo = new RowInfo();
		this.productionOrderMaterials = new ArrayList<ProductionOrderMaterial>();
		this.productionOrderOperations = new ArrayList<ProductionOrderOperation>();
	}	

	
	// Constructors without the common fields
	//public ProductionOrder(/* all fields */) {
	//	super();
		/* Example of assignment
		this.id = id;
		this.type = type;
		this.description = description;
		*/
	//}


	// Constructors with all fields
	public ProductionOrder(/* all fields*/
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
		buf.append("ProductionOrder: [");
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
		return (obj == this) || (obj instanceof ProductionOrder) && getId() != null && ((ProductionOrder) obj).getId().equals(this.getId());
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
	protected void prePersist() throws BusinessException {
		super.prePersist();
		System.out.println("prePersist of ProductionOrder");
		
		
		/*
		validate();
		
		
		
		rowInfo.setRecordStatus("A");
		
	    java.util.Date today = new java.util.Date();

	    rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	    rowInfo.setCreateTimestamp(rowInfo.getModifyTimestamp());			
		System.out.println("[PrePersist] " + rowInfo.getCreateLogin());
		*/
	}

	@PostPersist
	void postPersist() throws BusinessException {

	}
	
	@PostLoad
	void postLoad() {

	}
/*
	@PreUpdate
	protected void preUpdate() throws BusinessException {
		
		super.preUpdate();
		System.out.println("preUpdate of ProductionOrder");
		
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
	
	
	public void validate() throws BusinessException  {
        
		System.out.println("[ProductionOrder] validate");
		
		// Validate syntax...
        /*
		if (StringUtil.isEmpty(docNo)) {
			System.out.println("[ProductionOrder] DocNo is blank");
			throw new ValueRequiredException(this, "ProductionOrder_DocNo");
		}
        */


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

/*

	public String getSessionId() {
		return rowInfo.getSessionId();
	}


	public void setSessionId(String sessionId) {
		this.rowInfo.setSessionId(sessionId);
	}
*/


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
	 * @return the quantityReported
	 */
	public Double getQuantityReported() {
		return quantityReported;
	}


	/**
	 * @param quantityReported the quantityReported to set
	 */
	public void setQuantityReported(Double quantityReported) {
		this.quantityReported = quantityReported;
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


	/**
	 * @return the productionOrderMaterials
	 */
	public List<ProductionOrderMaterial> getProductionOrderMaterials() {
		return productionOrderMaterials;
	}


	/**
	 * @param productionOrderMaterials the productionOrderMaterials to set
	 */
	public void setProductionOrderMaterials(List<ProductionOrderMaterial> productionOrderMaterials) {
		this.productionOrderMaterials = productionOrderMaterials;
	}


	/**
	 * @return the docNo
	 */
	public Long getDocNo() {
		return docNo;
	}


	/**
	 * @param docNo the docNo to set
	 */
	public void setDocNo(Long docNo) {
		this.docNo = docNo;
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
	 * @return the productionOrderOperations
	 */
	public List<ProductionOrderOperation> getProductionOrderOperations() {
		return productionOrderOperations;
	}


	/**
	 * @param productionOrderOperations the productionOrderOperations to set
	 */
	public void setProductionOrderOperations(List<ProductionOrderOperation> productionOrderOperations) {
		this.productionOrderOperations = productionOrderOperations;
	}

	
	public void addProductionOrderMaterial(ProductionOrderMaterial productionOrderMaterial) {
		if (!productionOrderMaterials.contains(productionOrderMaterial)) {
			System.out.println("add new production Order Material");
			/* Maintain the bidirectional relationship . */
			productionOrderMaterial.setProductionOrder(this);
			productionOrderMaterials.add(productionOrderMaterial);
			
		}
	}

	public void addProductionOrderOperation(ProductionOrderOperation productionOrderOperation) {
		if (!productionOrderOperations.contains(productionOrderOperation)) {
			productionOrderOperations.add(productionOrderOperation);
			/* Maintain the bidirectional relationship . */
			productionOrderOperation.setProductionOrder(this);
		}
	}

	public String getMaterialCode()
	{
		if (material!=null) return material.getCode();
		else return "";
	}
	public Long getSalesOrderDoc()
	{
		if(salesOrder!=null) return salesOrder.getDocNo();
		else return null;
	}
	
}
