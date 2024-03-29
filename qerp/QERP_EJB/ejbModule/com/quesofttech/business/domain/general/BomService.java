package com.quesofttech.business.domain.general;

import java.util.List;
import java.util.TreeSet;
import java.util.Calendar;


import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import com.quesofttech.util.TreeNode;
import com.quesofttech.util.iface.ITreeNodeFilter;


import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.base.BaseService;

import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.sales.SalesOrder;
import com.quesofttech.business.domain.sales.SalesOrderMaterial;
import com.quesofttech.business.domain.general.BOM;
import com.quesofttech.business.domain.general.iface.IBomServiceLocal;
import com.quesofttech.business.domain.general.iface.IBomServiceRemote;




@Stateless
@Local(IBomServiceLocal.class)
@Remote(IBomServiceRemote.class)
public class BomService extends BaseService implements IBomServiceLocal, IBomServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	
	// BOM

	public BOM findBOM(Long id) throws DoesNotExistException {
		BOM bom = (BOM) find(BOM.class, id);
		return bom;
	}

	@SuppressWarnings("unchecked")
	public List<BOM> findBOMs() throws DoesNotExistException {
		Query q = _em.createQuery("select bom from BOM bom where bom.rowInfo.recordStatus='A' order by bom.id");
		List l = q.getResultList();
		return l;
	}

	@SuppressWarnings("unchecked")
	public BOM findBomByMaterial(Material material, String type) throws DoesNotExistException {
		Query q = _em.createQuery("select bom from BOM bom " +
				                  " where bom.rowInfo.recordStatus='A' " +
				                  " AND bom.material.id = :materialId " +
				                  " AND bom.type = :type" +
				                  " order by bom.id");
		
		q.setParameter("materialId", material.getId());
		q.setParameter("type", type);
		
		List l = q.getResultList();
		
		System.out.println("[findBOMByMaterial] No of records found : " + l.size() );
		
		if(l.size() != 1)
		{
			return null;
		}
		else
		return (BOM)l.get(0);
	}

	
	
	public void updateBOM(BOM bom) throws BusinessException {
		bom = (BOM) merge(bom);
	}

	public void logicalDeleteBOM(BOM bom) throws BusinessException {
		bom.setRecordStatus("D");
		updateBOM(bom);
	}
	
	public void addBOM(BOM bom) throws BusinessException {
		
		//try{
		System.out.println("just before persist in BOMService");
		persist(bom);
		
		System.out.println("just after persist in BOMService");
		//}
	}
	

	// BomDetail
	

	
	
	public void updateBomDetail(BomDetail bomDetail) throws BusinessException {		
		bomDetail = (BomDetail) merge(bomDetail);
	}

	public void logicalDeleteBomDetail(BomDetail bomDetail) throws BusinessException {
		bomDetail.setRecordStatus("D");
		updateBomDetail(bomDetail);
	}
	
	public void addBomDetail(BomDetail bomDetail) throws BusinessException {
		
		//try{
		System.out.println("just before persist in BomDetailService");
		persist(bomDetail);
		
		System.out.println("just after persist in BomDetailService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[BomDetailService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addBomDetail] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
	
	

	
	
	// Finders
	
	public BomDetail findBomDetail(Long id) throws DoesNotExistException {
		BomDetail bomDetail = (BomDetail) find(BomDetail.class, id);
		return bomDetail;
	}

	@SuppressWarnings("unchecked")
	public List<BomDetail> findBomDetails() throws DoesNotExistException {
		Query q = _em.createQuery("select bomDetail from BomDetail bomDetail order by bomDetail.id");
		List l = q.getResultList();
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public List<BomDetail> findBomDetailsByBomId(Long bomId) throws DoesNotExistException {
		BOM bom = findBOM(bomId);
				
		Query q = _em.createQuery("select bomDetail from BomDetail bomDetail where bomDetail.bom.id = :bomId AND bomDetail.rowInfo.recordStatus='A'" + 
				" order by bomDetail.id");
		
		q.setParameter("bomId", bomId);
		
		List l = q.getResultList();
		return l;
	}
	
	public List<BomDetail> findBomDetailsByBom(BOM bom) throws DoesNotExistException {
				
		Query q = _em.createQuery("select bomDetail from BomDetail bomDetail where bomDetail.bom.id = :bomId AND bomDetail.rowInfo.recordStatus='A'" + 
				" order by bomDetail.id");
		
		q.setParameter("bomId", bom.getId());
		
		List l = q.getResultList();
		return l;
	}
	
	
	public List<BomDetail> findBomDetailsByParentMaterial(Material material,String type) throws DoesNotExistException {
		
		//BOM bom = this.findBOMByMaterial(material, type);
		
		//return findBomDetailsByBom(bom);
		
		Query q = _em.createQuery("select bomDetail from BomDetail bomDetail where bomDetail.bom.material.id = :materialId AND bomDetail.rowInfo.recordStatus='A'" + 
				" order by bomDetail.id");
		
		q.setParameter("materialId", material.getId());
		
		List l = q.getResultList();
		return l;
		
	}
	
	
	
	
	
	// Explode
	
	
	
	
	public BomTree buildBomTree(Material material, String type) throws DoesNotExistException {
		//TreeSet<BomTreeNodeData> bomTreeSet = new TreeSet<BomTreeNodeData>();
		//System.out.println("build1");
		BOM bom = this.findBomByMaterial(material, type);
		//System.out.println("build12");
		// Create a virtual BomDetail record for the root Material, to set the calculated value 
		
		BomDetail bomDetail = new BomDetail();
		bomDetail.setBom(bom);
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
		
		bomTree.setId(bom.getId());
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
		BOM bom = bomDetail.getBom();
		
		List<BomDetail> childrenBomDetail = null;
		
		try	{ 
			childrenBomDetail = this.findBomDetailsByParentMaterial(bomDetail.getMaterial(), type);
		}
		catch(DoesNotExistException e) {
			return;
		}
		
		for(BomDetail childBomDetail : childrenBomDetail)
		{
			
			if(treeNodeFilter.filter(childBomDetail)) // Condition
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
	
/*	
	private void explode(TreeNode<BomTreeNodeData> bomTreeNode, String type) {
		
		List<TreeNode<BomTreeNodeData>> children = bomTreeNode.getChildren();
		
		for(TreeNode<BomTreeNodeData> node : children)
		{
			explode(node,type); 
		}
		
	}
*/	
	
	public void addBomDetail(Long id,BomDetail bomdetail) throws BusinessException{
		//BomDetail BomDetailLine1 = new BomDetail();
		//System.out.println("finding id:" + id);
		BOM bomheader = findBOM(id);
		//salesOrderLine1.setSalesOrder(salesOrder);
		//System.out.println("found id:" + id);
		///BomDetailLine1.setLocation(bomdetail.getLocation());
		//BomDetailLine1.setQuantityRequired(bomdetail.getQuantityRequired());
		//BomDetailLine1.setScrapFactor(bomdetail.getScrapFactor());
		//BomDetailLine1.setStartDate(bomdetail.getStartDate());
		//BomDetailLine1.setBom(bomheader);
		//BomDetailLine1.setMaterial(bomdetail.getMaterial());
		//BomDetailLine1.setEndDate(bomdetail.getEndDate());
		bomheader.addBomDetail(bomdetail);
	}
	@SuppressWarnings("unchecked")
	public List<BomDetail> findBomDetailsByBomID(Long id) throws DoesNotExistException
	{
		BOM bom = findBOM(id);		
		Query q = _em.createQuery("select som from bomdetail som where som.bom = :bom AND som.rowInfo.recordStatus='A'" +
				" order by som.id");
		System.out.println("here liao 1 ");
		q.setParameter("bom", bom);
		System.out.println("here liao 2 ");
		List l = q.getResultList();
		System.out.println("here liao 3");
		if (l.isEmpty() || l==null)
		{
			System.out.println("cb liao lo");
		}
		return l;
	}
	

	
}
