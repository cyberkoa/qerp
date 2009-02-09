package com.quesofttech.business.domain.sales;

import java.util.ArrayList;
import java.util.List;


import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.query.ComparisonOperator;
import com.quesofttech.business.common.query.QueryBuilder;
import com.quesofttech.business.common.query.SearchOptions;
import com.quesofttech.business.domain.base.BaseService;
import com.quesofttech.business.domain.sales.SalesOrder;
import com.quesofttech.business.domain.sales.SalesOrderMaterial;
import com.quesofttech.business.domain.sales.dto.SalesOrderSearchFields;

import com.quesofttech.business.domain.sales.iface.ISalesOrderServiceLocal;
import com.quesofttech.business.domain.sales.iface.ISalesOrderServiceRemote;
import com.quesofttech.business.domain.sales.dto.SalesOrderSearchFields;
import com.quesofttech.business.domain.sales.dto.SalesOrderMaterialSearchFields;
import com.quesofttech.business.domain.general.BOM;
import com.quesofttech.business.domain.general.BomDetail;
import com.quesofttech.business.domain.general.BomTree;
import com.quesofttech.business.domain.general.BomTreeNode;
import com.quesofttech.business.domain.general.BomService;
import com.quesofttech.business.domain.general.BomTreeNodeData;


import com.quesofttech.business.domain.production.ProductionOrder;
import com.quesofttech.business.domain.production.ProductionOrderOperation;
import com.quesofttech.business.domain.production.ProductionOrderMaterial;
import com.quesofttech.business.domain.production.ProductionOrderService;
import com.quesofttech.util.TreeNode;


@Stateless
@Local(ISalesOrderServiceLocal.class)
@Remote(ISalesOrderServiceRemote.class)
public class SalesOrderService extends BaseService implements ISalesOrderServiceLocal, ISalesOrderServiceRemote 
{

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// SalesOrder

	public SalesOrder findSalesOrder(Long id) throws DoesNotExistException {
		SalesOrder salesOrder = (SalesOrder) find(SalesOrder.class, id);
		return salesOrder;
	}

	@SuppressWarnings("unchecked")
	public List<SalesOrder> findSalesOrders() throws DoesNotExistException {
		Query q = _em.createQuery("select so from SalesOrder so where so.rowInfo.recordStatus='A' order by so.id");
		List l = q.getResultList();
		return l;
	}
/*
	public SalesOrder findSalesOrderByType(String type) {
		SalesOrder salesOrder = _em.find(SalesOrder.class, id);
		return salesOrder;
	}
*/
/*
 	@SuppressWarnings("unchecked")
	public List<User> findUsers(UserSearchFields search, SearchOptions options) {

		QueryBuilder builder = new QueryBuilder();
		builder.append("select u from User u");
		builder.appendEqualsSkipEmpty("u.recordStatus", search.getRecordStatus());
		builder.appendLikeIgnoreCaseSkipEmpty("u.login", search.getLogin());
		builder.appendEqualsSkipEmpty("u.salutation", search.getSalutation());
		builder.appendLikeIgnoreCaseSkipEmpty("u.firstName", search.getFirstName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.lastName", search.getLastName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.emailAddress", search.getEmailAddress());
		builder.appendComparison("u.expiryDate", ComparisonOperator.EQ, search.getExpiryDate());
		builder.appendEqualsSkipEmpty("u.version", search.getVersion());

		if (options.getSortColumnNames().size() == 0) {
			builder.append(" order by u.login");
		}

		Query q = builder.createQuery(_em, options, "u");

		List l = q.getResultList();

		return l;
	}
*/
	public List<SalesOrder> findSalesOrders(SalesOrderSearchFields search,SearchOptions options) throws DoesNotExistException {
		return null;
	}
	public List<SalesOrder> findSalesOrders(SalesOrderSearchFields from,SalesOrderSearchFields to,SearchOptions options) throws DoesNotExistException {
		return null;
	}
	
	
	
	public void updateSalesOrder(SalesOrder salesOrder) throws BusinessException {		
		salesOrder = (SalesOrder) merge(salesOrder);
		
		/*
		// If salesOrderLines not null 
		List<SalesOrderLine> salesOrderLines = salesOrder.getSalesOrderLines(); 
	
		if(salesOrderLines!=null && salesOrderLines.size() > 0)
		{		
			for(SalesOrderLine sol : salesOrderLines)
			{
			 sol = (SalesOrderLine) merge(sol);
			}
		}
		*/
	}

	public void logicalDeleteSalesOrder(SalesOrder salesOrder) throws BusinessException {
		salesOrder.rowInfo.setRecordStatus("D");
		
		for(SalesOrderMaterial salesOrderMaterial : salesOrder.getSalesOrderMaterials())
		{
			salesOrderMaterial.rowInfo.setRecordStatus("D");
		}
		updateSalesOrder(salesOrder);
	}
	public void addSalesOrderMaterial(Long id,SalesOrderMaterial salesOrderMaterial) throws BusinessException{
		
		//SalesOrderMaterial salesOrderLine = new SalesOrderMaterial();
		//System.out.println("finding id:" + id);
		SalesOrder salesOrder = findSalesOrder(id);
		//salesOrderLine1.setSalesOrder(salesOrder);
		/*
		System.out.println("found id:" + id);
		salesOrderLine.setLine((salesOrderMaterial.getLine()));
		salesOrderLine.setQuantityOrder(salesOrderMaterial.getQuantityOrder());
		salesOrderLine.setQuantityShipped(salesOrderMaterial.getQuantityShipped());
		salesOrderLine.setCustomerPOLine(salesOrderMaterial.getCustomerPOLine());
		salesOrderLine.setMaterial(salesOrderMaterial.getMaterial());
		*/
		//System.out.println(salesOrder.toString());s
		salesOrder.addSalesOrderMaterial(salesOrderMaterial);
	}
	
	
	public void addSalesOrder(SalesOrder salesOrder) throws BusinessException {
		
		
		
		
		persist(salesOrder);
		
		/* Koa : the following lines are not needed
		// If salesOrderLines not null 
		List<SalesOrderLine> salesOrderLines = salesOrder.getSalesOrderLines(); 
		if(salesOrderLines!=null && salesOrderLines.size() > 0)
		{		
			for(SalesOrderLine sol : salesOrderLines)
			{
			 persist(sol);
			}
		}
		*/
	}
	
	
	// SalesOrderMaterial

	public SalesOrderMaterial findSalesOrderMaterial(Long id) throws DoesNotExistException {
		SalesOrderMaterial salesOrderMaterial = (SalesOrderMaterial) find(SalesOrderMaterial.class, id);
		return salesOrderMaterial;
	}
	
	@SuppressWarnings("unchecked")
	public List<SalesOrderMaterial> findSalesOrdersMaterials() throws DoesNotExistException {
		Query q = _em.createQuery("select so from SalesOrderMaterial so where so.rowInfo.recordStatus='A' order by so.id");
		List l = q.getResultList();
		return l;
	}
	
	public void logicalDeleteSalesOrderMaterial(SalesOrderMaterial salesOrderMaterial) throws BusinessException {
		salesOrderMaterial.rowInfo.setRecordStatus("D");
		//updateSalesOrder(salesOrderMaterial);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SalesOrderMaterial> findSalesOrderMaterialsBySalesOrderId(Long salesOrderId) throws DoesNotExistException {
		
		SalesOrder salesOrder = findSalesOrder(salesOrderId);
		System.out.println("here liao" + salesOrderId);
		
		Query q = _em.createQuery("select som from SalesOrderMaterial som where som.salesOrder = :salesOrder AND som.rowInfo.recordStatus='A'" +
				" order by som.id");
		System.out.println("here liao 1 ");
		q.setParameter("salesOrder", salesOrder);
		System.out.println("here liao 2 ");
		List l = q.getResultList();
		System.out.println("here liao 3");
		if (l.isEmpty() || l==null)
		{
			System.out.println("cb liao lo");
		}
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public List<SalesOrderMaterial> findSalesOrderMaterialsBySalesOrder(SalesOrder salesOrder) throws DoesNotExistException {
			
		Query q = _em.createQuery("select som from SalesOrderMaterial som where som.salesOrder = :salesOrder AND som.rowInfo.recordStatus='A'" +
				" order by som.id");
		q.setParameter("salesOrder", salesOrder);
		List l = q.getResultList();
		return l;
	}
	
	
	
	public void updateSalesOrderMaterial(SalesOrderMaterial salesOrderMaterial) throws BusinessException {		
		salesOrderMaterial = (SalesOrderMaterial) merge(salesOrderMaterial);
		
	}
	

	public List<SalesOrder> findSalesOrdersBySearchFieldsRange(SalesOrderSearchFields lower,SalesOrderSearchFields upper,SearchOptions options)
	throws DoesNotExistException
	{

		QueryBuilder builder = new QueryBuilder();
		
		builder.append("select so from SalesOrder so");
		//builder.appendBetween("so.rowInfo.recordStatus", lower.getRecordStatus(),upper.getRecordStatus(),true);
	
		builder.appendBetween("so.customer.code", lower.getCustomerCode(),upper.getCustomerCode(),true);
		builder.appendBetween("so.docNo",lower.getDocNo(),upper.getDocNo(),true);
		/*
		builder.appendLikeIgnoreCaseSkipEmpty("u.firstName", search.getFirstName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.lastName", search.getLastName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.emailAddress", search.getEmailAddress());
		builder.appendComparison("u.expiryDate", ComparisonOperator.EQ, search.getExpiryDate());
		builder.appendEqualsSkipEmpty("u.version", search.getVersion());
		*/
		if (options.getSortColumnNames().size() == 0) {
			builder.append(" order by so.docNo");
		}
	
		Query q = builder.createQuery(_em, options, "so");
	    System.out.println(builder.getQueryString());
		List l = q.getResultList();
	
		return l;

	
	}
	
	public List<SalesOrder> findSalesOrdersBySearchFields(SalesOrderSearchFields search,SearchOptions options)
	throws DoesNotExistException
	{		
		QueryBuilder builder = new QueryBuilder();
		/*
		builder.append("select so from SalesOrder so");
		builder.appendBetween("so.recordStatus", search.getRecordStatus());
		builder.appendLikeIgnoreCaseSkipEmpty("u.login", search.getLogin());
		builder.appendEqualsSkipEmpty("u.salutation", search.getSalutation());
		builder.appendLikeIgnoreCaseSkipEmpty("u.firstName", search.getFirstName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.lastName", search.getLastName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.emailAddress", search.getEmailAddress());
		builder.appendComparison("u.expiryDate", ComparisonOperator.EQ, search.getExpiryDate());
		builder.appendEqualsSkipEmpty("u.version", search.getVersion());
		 */
		if (options!=null && options.getSortColumnNames().size() > 0) 
		{}
		else{
			builder.append(" order by u.login");
		}
	
		Query q = builder.createQuery(_em, options, "u");
	
		List l = q.getResultList();
	
		return l;
			
	}
	//========================================================================================
	// SalesOrderMaterial Search.
	//========================================================================================

	public List<SalesOrderMaterial> findSalesOrdersMaterialBySearchFieldsRange(SalesOrderMaterialSearchFields lower,SalesOrderMaterialSearchFields upper,SearchOptions options)
	throws DoesNotExistException
	{

		QueryBuilder builder = new QueryBuilder();
		
		builder.append("select so from SalesOrderMaterial so");
		//builder.appendBetween("so.rowInfo.recordStatus", lower.getRecordStatus(),upper.getRecordStatus(),true);
		builder.appendBetween("so.material.code", lower.getMaterial(),upper.getMaterial(),true);
		builder.appendBetween("so.price", lower.getPrice(),upper.getPrice(),true);
		builder.appendBetween("so.quantityOrder", lower.getQtyOrder(),upper.getQtyOrder(),true);
		
		/*
		builder.appendLikeIgnoreCaseSkipEmpty("u.firstName", search.getFirstName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.lastName", search.getLastName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.emailAddress", search.getEmailAddress());
		builder.appendComparison("u.expiryDate", ComparisonOperator.EQ, search.getExpiryDate());
		builder.appendEqualsSkipEmpty("u.version", search.getVersion());
		*/
		if (options.getSortColumnNames().size() == 0) {
			builder.append(" order by so.salesorder.docNo");
		}
	
		Query q = builder.createQuery(_em, options, "so");
	    System.out.println(builder.getQueryString());
		List l = q.getResultList();
	
		return l;

	
	}
	
	public List<SalesOrderMaterial> findSalesOrdersMaterialBySearchFields(SalesOrderMaterialSearchFields search,SearchOptions options)
	throws DoesNotExistException
	{		
		QueryBuilder builder = new QueryBuilder();
		/*
		builder.append("select so from SalesOrder so");
		builder.appendBetween("so.recordStatus", search.getRecordStatus());
		builder.appendLikeIgnoreCaseSkipEmpty("u.login", search.getLogin());
		builder.appendEqualsSkipEmpty("u.salutation", search.getSalutation());
		builder.appendLikeIgnoreCaseSkipEmpty("u.firstName", search.getFirstName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.lastName", search.getLastName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.emailAddress", search.getEmailAddress());
		builder.appendComparison("u.expiryDate", ComparisonOperator.EQ, search.getExpiryDate());
		builder.appendEqualsSkipEmpty("u.version", search.getVersion());
		 */
		if (options!=null && options.getSortColumnNames().size() > 0) 
		{}
		else{
			builder.append(" order by u.login");
		}
	
		Query q = builder.createQuery(_em, options, "u");
	
		List l = q.getResultList();
	
		return l;
			
	}
	//========================================================================================
	// SalesOrderMaterial Search.
	//========================================================================================

	public void convertOrderMaterialToProductionOrder(SalesOrderMaterial salesOrderMaterial)
	{
		List<ProductionOrder> productionOrders = new ArrayList<ProductionOrder>(); 
		//ProductionOrderService productionOrderService = new ProductionOrderService();
		
		BomTree bomTree = null;
		BOM bom = null;
		//BomService bomService = new BomService();
		System.out.println("SO Material : " + salesOrderMaterial.getMaterial());
		
		if(salesOrderMaterial != null && salesOrderMaterial.getMaterial() != null) 
		{
			//try {	
			 bom = salesOrderMaterial.getMaterial().getBomByType("P");
		    //}
			//catch(NullPointerException e)
			//{

			//}		
			try 
			{
			 bom.buildBomTree();
			}
			catch(BusinessException be)
			{
				
			}
		}
		
		List<TreeNode<BomTreeNodeData>> bomTreeNodeList = bomTree.toList();
		
		for(TreeNode<BomTreeNodeData> node : bomTreeNodeList)
		{
		 
			try
			{
				if(node.getData().getBomDetail().getMaterial().getMaterialType().isProduced())
				{
					ProductionOrder productionOrder = new ProductionOrder();
					
					// temporary
					productionOrder.setDocNo("0");
					productionOrder.setQuantityOrder(node.getData().getTreeOriginalQuantityRequired() * salesOrderMaterial.getQuantityOrder());
					
					productionOrder.setMaterial(node.getData().getBomDetail().getMaterial());
					
					// Do a phantom explosion
					
					List<TreeNode<BomTreeNodeData>> productionOrderChildren = new ArrayList<TreeNode<BomTreeNodeData>>(node.getChildren());
					System.out.println("[Before phantom explosion] :" + productionOrderChildren.size());
					for(int i=0; i < productionOrderChildren.size();++i)
					{
					  TreeNode<BomTreeNodeData> child = productionOrderChildren.get(i);
					  
					  // If is phantom type (isJIT = true indicates it is a phantom type)
					  if(child.getData().getBomDetail().getMaterial().getMaterialType().isJIT())
					  {
						  // Add the children to the end of the list
						  productionOrderChildren.addAll(child.getChildren());
					  }
					}
					
					
					System.out.println("[After phantom explosion] :" + productionOrderChildren.size());
					/*
					// After phantom explosion , productionOrderChildren should contain all the production order materials					
					for(TreeNode<BomTreeNodeData> child : productionOrderChildren)
					{
						ProductionOrderMaterial productionOrderMaterial = new ProductionOrderMaterial();
						
						productionOrderMaterial.setMaterial(child.getData().getBomDetail().getMaterial());
						productionOrderMaterial.setProductionOrder(productionOrder);
						productionOrderMaterial.setQuantityConsumed(0.0);
						productionOrderMaterial.setQuantityRequired(child.getData().getTreeOriginalQuantityRequired() * productionOrder.getQuantityOrder());
						
						
						// Add the productionOrderMaterial
						productionOrder.addProductionOrderMaterial(productionOrderMaterial);
					}
					// Insert the productionOrder to db
					productionOrderService.addProductionOrder(productionOrder);
				    // Added to list (for future , to have a addProductionOrders function to insert record by list
				    productionOrders.add(productionOrder);
					*/
					
				}
				
				
			}
			catch(Exception e)
			{
				//throw e;
			}
			
			
			
		}
		
	}
	
	
}
