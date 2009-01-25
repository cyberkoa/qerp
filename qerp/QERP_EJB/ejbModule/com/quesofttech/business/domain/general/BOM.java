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


import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.business.domain.inventory.Material;

import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.exception.ValueRequiredException;
import com.quesofttech.business.common.exception.GenericBusinessException;
import com.quesofttech.util.StringUtil;
import com.quesofttech.util.TreeNode;
import com.quesofttech.util.iface.ITreeNodeFilter;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "BOM", uniqueConstraints = { @UniqueConstraint(columnNames = { "fk_Material","bom_Type" }) })
@SuppressWarnings("serial")
public class BOM extends BaseEntity {
	

	//For Postgresql : @SequenceGenerator(name = "BOM_sequence", sequenceName = "BOM_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="BOM_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="BOM", valueColumnName="keyField")
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOM_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BOM_id")	
	@Column(name = "id_BOM", nullable = false)
	private Long id;
	
	@Version
	@Column(nullable = false)
	private Long version;


	// Example of field	
	@Column(name = "bom_Type", length = 1, nullable = false)
	private String type;
	
	@Column(name = "bom_Code", length = 10)
	private String code;
	
	@Embedded
	RowInfo rowInfo;
	
	
	@ManyToOne
	@JoinColumn(name="fk_Material")	
	private Material material;
	
	
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="bom", targetEntity=BomDetail.class)
	private List<BomDetail> bomDetails; // = new List<<ForeignTable>>();
	

	public BOM() {
		super();
		this.rowInfo = new RowInfo();
		//this.bomDetails = new List<BomDetail>();
		
	}	

	
	// Constructors without the common fields
	//public BOM(/* all fields */) {
	//	super();
		/* Example of assignment
		this.id = id;
		this.type = type;
		this.description = description;
		*/
	//}


	// Constructors with all fields
	public BOM(/* all fields*/String type,
			String recordStatus, String sessionId, String createLogin,
			String createApp, Timestamp createTimestamp,
			String modifyLogin, String modifyApp, Timestamp modifyTimestamp) {
		this();

		
		
		this.type = type;
		/* Example of assignment
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
		buf.append("BOM: [");
		buf.append("id=" + id + ", ");

		
		buf.append("type=" + type + ", ");
		/* All fields
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
		return (obj == this) || (obj instanceof BOM) && getId() != null && ((BOM) obj).getId().equals(this.getId());
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

	    rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	    rowInfo.setCreateTimestamp(rowInfo.getModifyTimestamp());			
				
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
		}
			java.util.Date today = new java.util.Date();

			rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
		
		
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

		if (StringUtil.isEmpty(type)) {
			//System.out.println("Yeah");
			throw new ValueRequiredException(this, "BOM_Type");
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
	
	public String getMaterialCode()
	{
		return material.getCode();
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	
	
	public void addBomDetail(BomDetail bomDetail)
	{
		if (!bomDetails.contains(bomDetail)) {			
			bomDetails.add(bomDetail);
			bomDetail.setBom(this);
		}		
	}


	public Material getMaterial() {
		return material;
	}


	public void setMaterial(Material material) {
		this.material = material;
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
	

	
	// methods
	
	public BomTree buildBomTree() throws DoesNotExistException {
		//TreeSet<BomTreeNodeData> bomTreeSet = new TreeSet<BomTreeNodeData>();
		//System.out.println("build1");
		//BOM bom = this.findBOMByMaterial(material, type);
		//System.out.println("build12");
		// Create a virtual BomDetail record for the root Material, to set the calculated value 
		
		BomDetail bomDetail = new BomDetail();
		bomDetail.setBom(this);
		bomDetail.setMaterial(material);
		
		// Default and scrap factor and quantity required as 1, as this value will be used to calculate children corresponding value
		bomDetail.setScrapFactor(1.0);
        bomDetail.setQuantityRequired(1.0);
        
	    java.util.Calendar calendar = Calendar.getInstance();

		bomDetail.setStartDate(new java.sql.Timestamp(calendar.getTime().getTime()));
		
	    calendar.add(Calendar.MONTH, 1); // Add 1 month
		bomDetail.setEndDate(new java.sql.Timestamp(calendar.getTime().getTime()));
		
		// Prepare bomTreeNodeData
		BomTreeNodeData bomTreeNodeData = new BomTreeNodeData();
		
		bomTreeNodeData.setBomDetail(bomDetail);
		
		// Set the quantity required as 1 to initial the BOM explosion
		bomTreeNodeData.setTreeActualQuantityRequired(1.00);
		bomTreeNodeData.setTreeOriginalQuantityRequired(1.00);
		
		bomTreeNodeData.setTreeActualValue(1.00);
		bomTreeNodeData.setTreeOriginalValue(1.00);
		
		
		
		BomTreeNode bomTreeNode = new BomTreeNode();
		
		bomTreeNode.setData(bomTreeNodeData);
		bomTreeNode.setLevel(0);
		bomTreeNode.setParent(null);
		
		
		// Start the explosion
		this.explode(bomTreeNode, type);
		
		
		System.out.println("After explode");
				
		// Declare a bomTree
		BomTree bomTree = new BomTree();
		// Set bomTreeNode as RootElement
		bomTree.setRootElement(bomTreeNode);
		
		System.out.println("[BomService.java] bomTree.toList().size() = " + bomTree.toList().size());
		return bomTree;
	}
	
	/**
	 * 
	 * @param bomTreeNode Initial TreeNode that to be explode for current level
	 * @param type Type of Bom
	 * @param treeNodeFilter  A delegate interface, to determine whether child should be added to tree as node or not
	 */
	private void explode(TreeNode<BomTreeNodeData> bomTreeNode, String type, ITreeNodeFilter treeNodeFilter) {
		
		
		BomTreeNodeData bomTreeNodeData = bomTreeNode.getData();
		
		if(bomTreeNodeData==null) return;
		BomDetail bomDetail = bomTreeNodeData.getBomDetail();
		
		if(bomDetail==null) return;
		//BOM bom = bomDetail.getBom();
		
		//List<BomDetail> childrenBomDetail = null;
		
			//childrenBomDetail = this.findBomDetailsByParentMaterial(bomDetail.getMaterial(), type);

		
		for(BomDetail childBomDetail : this.bomDetails)
		{
			
			if(this.type == type && treeNodeFilter.filter(childBomDetail)) // Condition
			{
				// Create a bom node data
				BomTreeNodeData childBomTreeNodeData = new BomTreeNodeData();			
				childBomTreeNodeData.setBomDetail(childBomDetail);
				childBomTreeNodeData.setTreeActualQuantityRequired(childBomDetail.getQuantityRequired() * childBomDetail.getScrapFactor() * bomTreeNodeData.getTreeActualQuantityRequired());
				childBomTreeNodeData.setTreeOriginalQuantityRequired(childBomDetail.getQuantityRequired() * bomTreeNodeData.getTreeOriginalQuantityRequired());
				
				// Temporary not used , until costing module design firm
				//childBomTreeNodeData.setTreeActualValue(treeActualValue);
				//childBomTreeNodeData.setTreeOriginalValue(treeOriginalValue);
				
				BomTreeNode childBomTreeNode = new BomTreeNode();
						
				childBomTreeNode.setData(childBomTreeNodeData);
				childBomTreeNode.setLevel(bomTreeNode.getLevel() + 1);
				childBomTreeNode.setParent(bomTreeNode);
				
				bomTreeNode.addChild(childBomTreeNode);
				
				// Instead of directly explode to lowest level, putting it out in another for loop to a allow per level explosion
				//this.explode(childBomTreeNode, type);
			}
		}
		
		for(TreeNode<BomTreeNodeData> childBomTreeNode : bomTreeNode.getChildren())
		{
			//System.out.println("[Inside for getChildren] Material : " + childBomTreeNode.data.getBomDetail().getMaterial().getCodeDescription() );
			this.explode(childBomTreeNode, type,treeNodeFilter);
		}
		
		
	}
	
	private void explode(TreeNode<BomTreeNodeData> bomTreeNode, String type) {
		
		// Pass true to as filter
		explode(bomTreeNode,type, new ITreeNodeFilter() 
		                          { 
			                       public boolean filter(Object obj)
			                       {
			                    	   return true;
			                       }
			                      } 
		
		);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}





