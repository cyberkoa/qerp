package com.quesofttech.business.domain.inventory;

import java.io.Serializable;
//import java.sql.Date;
//import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
//import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.Address;
import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.business.domain.embeddable.Dimension;
import com.quesofttech.business.domain.sales.SalesOrderMaterial;
import com.quesofttech.business.domain.general.BomDetail;
import com.quesofttech.business.domain.general.BOM;
import com.quesofttech.business.domain.general.UOM;
import com.quesofttech.business.domain.security.User;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.CannotDeleteIsReferencedException;
import com.quesoware.business.common.exception.GenericBusinessException;
import com.quesoware.business.common.exception.ValueRequiredException;
import com.quesoware.util.StringUtil;


@Entity
@Table(name = "Material", uniqueConstraints = { @UniqueConstraint(columnNames = { "mat_Code" }) })
@SuppressWarnings("serial")
public class Material extends BaseEntity {

	//private static final long serialVersionUID = 7422574264557894632L;
	//For Postgresql : @SequenceGenerator(name = "material_sequence", sequenceName = "material_id_seq")
	//Generic solution : (Use a table named PrimaryKeys, with 2 fields , tableName &  keyField)
	@TableGenerator(  name="material_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="Material", valueColumnName="keyField")	
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "material_id")
	@Column(name = "id_Material", nullable = false)
	private Long id;
	
	public MaterialGroup getMaterialGroup() {
		return materialGroup;
	}


	public void setMaterialGroup(MaterialGroup materialGroup) {
		this.materialGroup = materialGroup;
	}



	@Version
	@Column(nullable = false)
	private Integer version;
	
	
	// Table field
	
	@Column(name = "mat_Code", length = 30, unique= true, nullable = false)
	private String code;
	
	@Column(name = "mat_Description", length = 200, nullable = false)
	private String description;
	@Column(name = "mat_Grade", length = 30, unique= false, nullable = true)
	private String grade;
	
	
	@Embedded
    @AttributeOverrides( {
        @AttributeOverride(name="width", column = @Column(name="mat_Width") ),
        @AttributeOverride(name="length", column = @Column(name="mat_Length") ),
        @AttributeOverride(name="height", column = @Column(name="mat_Height") )
    } )  
    Dimension dimension;
	
	public Dimension getDimension() {
		return dimension;
	}


	/**
	 * @param Dimension the Dimension to set
	 */
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	
	
	
	// Foreign keys
	@ManyToOne
    @JoinColumn(name="fk_MaterialType")	
	private MaterialType materialType;
	
	
	@ManyToOne
    @JoinColumn(name="fk_MaterialGroup")	
	private MaterialGroup materialGroup;
		
	
	@ManyToOne
    @JoinColumn(name="fk_BaseUOM")	
	private UOM baseUOM;
	
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="material", targetEntity=BOM.class)
	private List<BOM> boms;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="material", targetEntity=BomDetail.class)
	private List<BomDetail> bomDetails;
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy="material", targetEntity=SalesOrderMaterial.class)
	private List<SalesOrderMaterial> salesOrderMaterials;
	
	
	//@Embedded
	//RowInfo rowInfo_1;
	

	
	public Material() {
		super();
		
		this.dimension = new Dimension();
		
		// TODO Auto-generated constructor stub
	}
	
	public Material(Long id, String code, String description) {
		this();
		//super();
		this.id = id;
		this.code = code;
		this.description = description;
	}


	public Material(Long id, Integer version, String code, String description,
			String recordStatus, String sessionId, String createLogin,
			String createApp, Timestamp createTimestamp,
			String modifyLogin, String modifyApp, Timestamp modifyTimestamp) {
		this(id,code,description);
		//super();
		//this.id = id;
		this.version = version;
		//this.code = code;
		//this.description = description;
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
		buf.append("Material: [");
		buf.append("id=" + id + ", ");
		buf.append("code=" + code + ", ");
		buf.append("description=" + description + ", ");
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
		return (obj == this) || (obj instanceof Material) && getId() != null && ((Material) obj).getId().equals(this.getId());
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
		System.out.println("prePersist in Material");
		validate();	    
		java.util.Date today = new java.util.Date();
	    //System.out.println(today.getTime());	    
	    //rowInfo.setModifyDate(new java.sql.Date(today.getTime()));
	    rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	    //rowInfo.setCreateDate(rowInfo.getModifyDate());
	    rowInfo.setCreateTimestamp(rowInfo.getModifyTimestamp());
		
	}
    */
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
		
		System.out.println(today.getTime());
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

		if (StringUtil.isEmpty(code)) {
			throw new ValueRequiredException(this, "Material_Code");
		}
		
		if (StringUtil.isEmpty(description)) {
			throw new ValueRequiredException(this, "Material_Description");
		}
		
		if(materialType==null) {
			throw new ValueRequiredException(this, "Material_Type");
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
	
	
	public double getLength()
	{
		return dimension.getLength();
	}
	public double getHeight()
	{
		return dimension.getHeight();
	}
	
	public double getWidth()
	{
		return dimension.getWidth();
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}
	
	public void setGrade(String grade){
		this.grade = grade;
	}
	public String getGrade(){
		return grade;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	// Common field
	
	
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
	 * @return the materialType
	 */
	public MaterialType getMaterialType() {
		return materialType;
	}

	
	/**
	 * @param materialType the materialType to set
	 */
	
	public void setMaterialType(MaterialType materialType) {
		this.materialType = materialType;
	}
		
	
	
	/**
	 * @return the baseUOM
	 */
	public UOM getBaseUOM() {
		return baseUOM;
	}

	/**
	 * @param baseUOM the baseUOM to set
	 */
	public void setBaseUOM(UOM baseUOM) {
		this.baseUOM = baseUOM;
	}

	
	
	
	/**
	 * @return the boms
	 */
	public List<BOM> getBoms() {
		return boms;
	}


	/**
	 * @param boms the boms to set
	 */
	public void setBoms(List<BOM> boms) {
		this.boms = boms;
	}


	/**
	 * @return the bomDetails
	 */
	public List<BomDetail> getBomDetails() {
		return bomDetails;
	}


	/**
	 * @param bomDetails the bomDetails to set
	 */
	public void setBomDetails(List<BomDetail> bomDetails) {
		this.bomDetails = bomDetails;
	}


	/**
	 * @return the salesOrderMaterials
	 */
	public List<SalesOrderMaterial> getSalesOrderMaterials() {
		return salesOrderMaterials;
	}


	/**
	 * @param salesOrderMaterials the salesOrderMaterials to set
	 */
	public void setSalesOrderMaterials(List<SalesOrderMaterial> salesOrderMaterials) {
		this.salesOrderMaterials = salesOrderMaterials;
	}


	// Useful properties
	/*
	 *  Property : Code - Description
	 */
	public String getCodeDescription() {
		return code + " - " + description;
	}
	
	// get Materialtype code for Grid Component
	public String getMaterialTypeCode(){
		if(materialType!=null) return materialType.getType();
		else return "";
	}
	// get MaterialType Desc for Grid COmponent
	public String getMaterialTypeDesc(){
		if(materialType!=null) return materialType.getTypeDescription();
		else return "";
	}
	
	
	// methods
	
	public BOM getBomByType(String type)
	{
		System.out.println("boms = " + getBoms());
		
		if(this.getBoms() != null)
		{
			for(BOM bom : boms)
			{
				//System.out.println("[Material.java] getBomByType() : " + bom.getType() + " type : " + type);
				if(bom.getType().equals(type))
				{
					//System.out.println("Before return bom");
					return bom;
				}
			}
		}
		else
		{
			System.out.println("[Material.java] boms is null");
		}
		return null;
	}
	
}
