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
import com.quesofttech.business.domain.inventory.MaterialType;
import com.quesofttech.business.domain.system.DocumentType;
import com.quesofttech.business.common.exception.ValueRequiredException;
import com.quesofttech.util.StringUtil;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "SalesOrder", uniqueConstraints = { @UniqueConstraint(columnNames = { "docNo","docType" }) })
@SuppressWarnings("serial")
public class SalesOrder extends BaseEntity {
	

	//For Postgresql : @SequenceGenerator(name = "SalesOrderHeader_sequence", sequenceName = "SalesOrderHeader_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="SalesOrder_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="SalesOrder", valueColumnName="keyField")
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SalesOrderHeader_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SalesOrder_id")	
	@Column(name = "id_SalesOrder", nullable = false)
	private Long id;
	
	@Version
	@Column(nullable = false)
	private Long version;
	
	
	// Example of field	
	@Column(name = "so_Number", length = 10, nullable = false)
	private Long docNo;
	
	@Column(name = "so_CustomerPO", length = 20, nullable = false)
	private String customerPO;
	
	@Column(name = "so_DocType", length = 5, nullable = false)
	private String docType;

	// Foreign keys
	@ManyToOne
    @JoinColumn(name="fk_Customer")	
	private Customer customer;
	
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="salesOrder", targetEntity=SalesOrderMaterial.class)
	private List<SalesOrderMaterial> salesOrderMaterials; // = new List<<ForeignTable>>();
		
	@ManyToOne
    @JoinColumn(name="fk_DocumentType")	
	private DocumentType documentType;
	
	/*
	@ManyToOne
    @JoinColumn(name="fk_SalesPerson")	
	private SalesPerson salesPerson;
	*/
	
	/*
	@ManyToOne
    @JoinColumn(name="fk_Currency")	
	private Currency currency;
	*/
	
	/*
	@ManyToOne
    @JoinColumn(name="fk_ShipTo") // Should change to something like ShipmentContact	
	private ShipTo shipTo;
	*/
	
	
	
	//@Embedded
	//RowInfo rowInfo_1;
	
	
	/*
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="SalesOrderHeader", targetEntity=<ForeignTable>.class)
	private List<<ForeignTable>> <foreignTable>s; // = new List<<ForeignTable>>();
	*/
	
	public SalesOrder() {
		super();
		
		this.salesOrderMaterials = new ArrayList<SalesOrderMaterial>();
	}	

	
	// Constructors without the common fields
	public SalesOrder(Long docNo, String customerPO, String docType) {
		
		//super();
		this();
		
		this.docNo = docNo;
		this.customerPO = customerPO;
		this.docType = docType;
		
	}


	// Constructors with all fields
	public SalesOrder(/* all fields*/
			Long docNo, String customerPO, String docType,
			String recordStatus, String sessionId, String createLogin,
			String createApp, Timestamp createTimestamp,
			String modifyLogin, String modifyApp, Timestamp modifyTimestamp) {
		this(docNo,customerPO,docType);
		//super();
		
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
		buf.append("SalesOrderHeader: [");
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
		return (obj == this) || (obj instanceof SalesOrder) && getId() != null && ((SalesOrder) obj).getId().equals(this.getId());
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
		
		// Default the document type
		docType = "S"; 
		
		validate();
		
		
		rowInfo.setRecordStatus("A");
		
	    java.util.Date today = new java.util.Date();
	    //System.out.println(today.getTime());
	    
	    //rowInfo.setModifyDate(new java.sql.Date(today.getTime()));
	    rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	    //rowInfo.setCreateDate(rowInfo.getModifyDate());
	    rowInfo.setCreateTimestamp(rowInfo.getModifyTimestamp());	
		
	    System.out.println(this.toString());
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

		/*
		if (StringUtil.isEmpty(docNo)) {
			//System.out.println("Yeah");
			throw new ValueRequiredException(this, "SalesOrder_DocNo");
		}
        */
        
		if (StringUtil.isEmpty(docType)) {
			throw new ValueRequiredException(this, "SalesOrder_DocType");
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
	 * @return the customerPO
	 */
	public String getCustomerPO() {
		return customerPO;
	}


	/**
	 * @param customerPO the customerPO to set
	 */
	public void setCustomerPO(String customerPO) {
		this.customerPO = customerPO;
	}


	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}


	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	/**
	 * @return the salesOrderLines
	 */
	public List<SalesOrderMaterial> getSalesOrderMaterials() {
		return salesOrderMaterials;
	}


	/**
	 * @param salesOrderLines the salesOrderLines to set
	 */
	public void setSalesOrderMaterials(List<SalesOrderMaterial> salesOrderLines) {
		this.salesOrderMaterials = salesOrderLines;
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
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	
	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	
	
	
	

	
	
	/**
	 * @return the documentType
	 */
	public DocumentType getDocumentType() {
		return documentType;
	}


	/**
	 * @param documentType the documentType to set
	 */
	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	
	
	
	/*
	    Helper methods
	 */

	public void addSalesOrderMaterial(SalesOrderMaterial salesOrderMaterial) {
		if (!salesOrderMaterials.contains(salesOrderMaterial)) {
			salesOrderMaterials.add(salesOrderMaterial);
			/* Maintain the bidirectional relationship . */
			salesOrderMaterial.setSalesOrder(this);
		}
	}

	public String getCustomerCode() {
		if(customer!=null) return customer.getCode();
	    return "";
	}
	
	public String getCustomerName() {
		if(customer!=null) return customer.getName();
		return "";
	}
}
