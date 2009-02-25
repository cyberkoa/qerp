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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.persistence.Embedded;
//import javax.persistence.SequenceGenerator;



import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.business.domain.inventory.Material;


import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.ValueRequiredException;
import com.quesofttech.business.common.exception.GenericBusinessException;
import com.quesofttech.util.StringUtil;

import java.util.List;
//import java.util.ArrayList;

@Entity
@Table(name = "BomDetail", uniqueConstraints = { @UniqueConstraint(columnNames = { "<unique_field>" }) })
@SuppressWarnings("serial")
public class BomDetail extends BaseEntity {
	

	//For Postgresql : @SequenceGenerator(name = "BomDetail_sequence", sequenceName = "BomDetail_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="BomDetail_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="BomDetail", valueColumnName="keyField")
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BomDetail_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BomDetail_id")	
	@Column(name = "id_BomDetail", nullable = false)
	private Long id;
	
	@Version
	@Column(nullable = false)
	private Long version;


	// Example of field	
	
	@Column(name = "bomd_startDate", nullable = false)
	private Timestamp startDate;
	
	@Column(name = "bomd_endDate", nullable = false)
	private Timestamp endDate;
		
	@Column(name = "bomd_quantityRequired", nullable = false)
	private Double quantityRequired;
		
	@Column(name = "bomd_scrapFactor", nullable = false)
	private Double scrapFactor;

	/*
	@Column(name = "bomd_Operation", nullable = false)
	private Integer operation;
	 */
	
	@Column(name = "bomd_Location", nullable = false)
	private String location;

	
	
	
	//@Embedded
	//RowInfo rowInfo_1;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="fk_BOM")	
	private BOM bom;

	
	// Parent material move to header, since the link is by head id
	/*
	@ManyToOne
	@JoinColumn(name="fk_ParentMaterial")	
	private Material parentMaterial;
	 */
	
	// Child material
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="fk_Material")	
	private Material material;
	
	
	/*
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="bomDetail", targetEntity=<ForeignTable>.class)
	private List<<ForeignTable>> <foreignTable>s; // = new List<<ForeignTable>>();
	*/

	public BomDetail() {
		super();
	        
	}	

	
	// Constructors without the common fields
	//public BomDetail(/* all fields */) {
	//	super();
		/* Example of assignment
		this.id = id;
		this.type = type;
		this.description = description;
		*/
	//}


	// Constructors with all fields
	public BomDetail(/* all fields*/
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
		buf.append("BomDetail: [");
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
		return (obj == this) || (obj instanceof BomDetail) && getId() != null && ((BomDetail) obj).getId().equals(this.getId());
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

	public void validate() throws BusinessException  {

		
		// Validate syntax...

		//if (StringUtil.isEmpty(type)) {
			//System.out.println("Yeah");
		//	throw new ValueRequiredException(this, "MaterialType_Type");
		//}

		if (quantityRequired == 0.0 ) {
			throw new ValueRequiredException(this, "BomDetail_QuantityRequired");
		}

		if (scrapFactor == 0.0 ) {
			throw new ValueRequiredException(this, "BomDetail_ScrapFactor");
		}

		
		if (startDate!=null) {
			throw new ValueRequiredException(this, "BomDetail_StartDate");
		}
		
		if (endDate!=null) {
			throw new ValueRequiredException(this, "BomDetail_EndDate");
		}

		
		
		// Validate semantics...
			
		if (startDate.after(endDate)) {
			throw new GenericBusinessException("BomDetail_StartDate_Later_Than_EndDate", new Object[] { startDate,endDate });
		}

		if (quantityRequired < 0.0 ) {
			throw new GenericBusinessException("BomDetail_QuantityRequired_Must_Positive", new Object[] { quantityRequired });
		}

		if (scrapFactor < 0.0 ) {
			throw new GenericBusinessException("BomDetail_ScrapFactor_Must_Positive", new Object[] { scrapFactor });
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
	 * @return the startDate
	 */
	public Timestamp getStartDate() {
		return startDate;
	}


	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}


	/**
	 * @return the endDate
	 */
	public Timestamp getEndDate() {
		return endDate;
	}


	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}


	/**
	 * @return the quantityRequired
	 */
	public Double getQuantityRequired() {
		return quantityRequired;
	}


	/**
	 * @param quantityRequired the quantityRequired to set
	 */
	public void setQuantityRequired(Double quantityRequired) {
		this.quantityRequired = quantityRequired;
	}


	/**
	 * @return the scrapFactor
	 */
	public Double getScrapFactor() {
		return scrapFactor;
	}


	/**
	 * @param scrapFactor the scrapFactor to set
	 */
	public void setScrapFactor(Double scrapFactor) {
		this.scrapFactor = scrapFactor;
	}


	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}


	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}


	/**
	 * @return the rowInfo
	 */
	public RowInfo getRowInfo() {
		return rowInfo;
	}


	/**
	 * @param rowInfo the rowInfo to set
	 */
	public void setRowInfo(RowInfo rowInfo) {
		this.rowInfo = rowInfo;
	}


	/**
	 * @return the bom
	 */
	public BOM getBom() {
		return bom;
	}


	/**
	 * @param bom the bom to set
	 */
	public void setBom(BOM bom) {
		this.bom = bom;
	}


	public Material getMaterial() {
		return material;
	}


	public void setMaterial(Material material) {
		this.material = material;
	}

	
	//  Properties
	public String getMaterialCode()
	{
		return material.getCode();
	}
	
	
	public String getMaterialDesc()
	{
		return material.getCodeDescription();
	}
	
	
	// Helper function
	
	public List<BomDetail> getChildrenBomDetail()
	{
		//this.material.getBoms()
		
		return null;
	}
	
}
