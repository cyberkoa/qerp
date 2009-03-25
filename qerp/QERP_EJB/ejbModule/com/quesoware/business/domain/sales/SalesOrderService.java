package com.quesoware.business.domain.sales;

import java.util.ArrayList;
import java.util.List;


import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.quesofttech.business.domain.base.BaseService;

import com.quesofttech.business.domain.general.BOM;
import com.quesofttech.business.domain.general.BomDetail;
import com.quesofttech.business.domain.general.BomTree;
import com.quesofttech.business.domain.general.BomTreeNode;
import com.quesofttech.business.domain.general.BomService;
import com.quesofttech.business.domain.general.BomTreeNodeData;
import com.quesofttech.business.domain.general.iface.IBomServiceLocal;



import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.common.query.ComparisonOperator;
import com.quesoware.business.common.query.QueryBuilder;
import com.quesoware.business.common.query.SearchOptions;
import com.quesoware.business.domain.production.ProductionOrder;
import com.quesoware.business.domain.production.ProductionOrderMaterial;
import com.quesoware.business.domain.production.ProductionOrderOperation;
import com.quesoware.business.domain.production.Routing;
import com.quesoware.business.domain.production.iface.IProductionOrderServiceLocal;
import com.quesoware.business.domain.production.iface.IRoutingServiceLocal;
import com.quesoware.business.domain.sales.SalesOrder;
import com.quesoware.business.domain.sales.SalesOrderMaterial;
import com.quesoware.business.domain.sales.dto.SalesOrderMaterialSearchFields;
import com.quesoware.business.domain.sales.dto.SalesOrderSearchFields;
import com.quesoware.business.domain.sales.iface.ISalesOrderServiceLocal;
import com.quesoware.business.domain.sales.iface.ISalesOrderServiceRemote;
import com.quesoware.business.domain.system.iface.IDocumentTypeServiceLocal;
import com.quesoware.util.TreeNode;


@Stateless
@Local(ISalesOrderServiceLocal.class)
@Remote(ISalesOrderServiceRemote.class)
public class SalesOrderService extends BaseService implements ISalesOrderServiceLocal, ISalesOrderServiceRemote 
{

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	@EJB(beanName="BomService")
	private IBomServiceLocal bomService;

	@EJB(beanName="DocumentTypeService")
	private IDocumentTypeServiceLocal documentTypeService;	
	
	
	/**
	 * @param bomService the bomService to set
	 */
	public void setBomService(IBomServiceLocal bomService) {
		this.bomService = bomService;
	}
	
	@EJB(beanName="ProductionOrderService")
	private IProductionOrderServiceLocal productionOrderService;
	
	@EJB(beanName="RoutingService")
	private IRoutingServiceLocal routingService;
	
	
	// SalesOrder
	
	/**
	 * @param productionOrderService the productionOrderService to set
	 */
	public void setProductionOrderService(
			IProductionOrderServiceLocal productionOrderService) {
		this.productionOrderService = productionOrderService;
	}

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
	
	@SuppressWarnings("unchecked")
	public List<SalesOrderMaterial> findSalesOrderMaterials() throws DoesNotExistException {
		Query q = _em.createQuery("select so from SalesOrderMaterial so where so.rowInfo.recordStatus='A' order by  so.line");
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
		salesOrder.setRecordStatus("D");
		
		for(SalesOrderMaterial salesOrderMaterial : salesOrder.getSalesOrderMaterials())
		{
			salesOrderMaterial.setRecordStatus("D");
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
		System.out.println("salesOrder.getDocNo:" + salesOrder.getDocNo());
		if(salesOrder.getDocNo()==null ||salesOrder.getDocNo()==0 )
		{
			salesOrder.setDocumentType(documentTypeService.getNextNumberByType("S"));
			salesOrder.setDocType(salesOrder.getDocumentType().getType());
			salesOrder.setDocNo(salesOrder.getDocumentType().getRunningNo());
		}
		
		
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
		Query q = _em.createQuery("select so from SalesOrderMaterial so where so.rowInfo.recordStatus='A' order , so.id");
		List l = q.getResultList();
		return l;
	}
	
	public void logicalDeleteSalesOrderMaterial(SalesOrderMaterial salesOrderMaterial) throws BusinessException {
		salesOrderMaterial.setRecordStatus("D");
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
		builder.appendBetween("so.customerPO",lower.getCustomerPO(),upper.getCustomerPO(),true);
		builder.appendBetween("so.rowInfo.recordStatus",lower.getRecordStatus(),upper.getRecordStatus(),true);
		/*
		builder.appendLikeIgnoreCaseSkipEmpty("u.firstName", search.getFirstName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.lastName", search.getLastName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.emailAddress", search.getEmailAddress());
		builder.appendComparison("u.expiryDate", ComparisonOperator.EQ, search.getExpiryDate());
		builder.appendEqualsSkipEmpty("u.version", search.getVersion());
		*/
		System.out.println("SalesOrder:" + builder.getQueryString());
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
		System.out.println("SalesOrder:" + builder.getQueryString());
	
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
		System.out.println("material code lower:" + lower.getMaterial());
		System.out.println("Price lower:" + lower.getPrice());
		builder.appendBetween("so.rowInfo.recordStatus", lower.getRecordStatus(),upper.getRecordStatus(),true);
		builder.appendBetween("so.material.code", lower.getMaterial(),upper.getMaterial(),true);
		builder.appendBetween("so.price", lower.getPrice(),upper.getPrice(),true);
		//builder.appendBetween("so.quantityOrder", lower.getQtyOrder(),upper.getQtyOrder(),true);
		
		/*
		builder.appendLikeIgnoreCaseSkipEmpty("u.firstName", search.getFirstName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.lastName", search.getLastName());
		builder.appendLikeIgnoreCaseSkipEmpty("u.emailAddress", search.getEmailAddress());
		builder.appendComparison("u.expiryDate", ComparisonOperator.EQ, search.getExpiryDate());
		builder.appendEqualsSkipEmpty("u.version", search.getVersion());
		*/
		System.out.println("SalesOrderMaterial:" + builder.getQueryString());
		//if (options.getSortColumnNames().size() == 0) {
		//	builder.append(" order by so.material.code");
		//}
		System.out.println("findSalesOrdersMaterialBySearchFieldsRange: " + builder.getQueryString());
	
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
		System.out.println("SalesOrderMatserial:" + builder.getQueryString());
	
		System.out.println("findSalesOrdersMaterialBySearchFields: " + builder.getQueryString());
		Query q = builder.createQuery(_em, options, "u");
	
		List l = q.getResultList();
	
		return l;
			
	}
	//========================================================================================
	// SalesOrderMaterial Search.
	//========================================================================================

	public void convertOrderMaterialToProductionOrder(RowInfo rowInfo, SalesOrderMaterial salesOrderMaterial) throws Exception
	{
		List<ProductionOrder> productionOrders = new ArrayList<ProductionOrder>(); 
		//ProductionOrderService productionOrderService = new ProductionOrderService();
		
		BomTree bomTree = null;
		BOM bom = null;
		
		System.out.println("SO Material : " + salesOrderMaterial.getMaterial());
		
		if(productionOrderService.findProductionOrderBySalesOrderMaterialId(salesOrderMaterial.getId()).size()>0)
		{
			System.out.println("Production Order with the same Sales Order Material already exist");
			return;
		}
		
		if(salesOrderMaterial != null && salesOrderMaterial.getMaterial() != null) 
		{
			//try {	
			// bom = salesOrderMaterial.getMaterial().getBomByType("P");
		    //}
			//catch(NullPointerException e)
			//{

			//}	
			// if(bom!=null)
			 //{
			   try 
			   {
				   bomTree =  bomService.buildBomTree(salesOrderMaterial.getMaterial(), "P");
			   }
			   catch(BusinessException be)
			   {
				   System.out.println("[SalesOrderService.java] Error calling bomService.buildBomTree");
			   }
			// }
			// else // Bom does not maintain
			// {
			//	 // Bom does not maintain , please maintain
			//	 System.out.println("[SalesOrderService.java] bom = null");
			//	 return;
			// }
		}
		
		// if buildBomTree does not success, end the conversion process
		if(bomTree == null) return;
		
		
		List<TreeNode<BomTreeNodeData>> bomTreeNodeList = bomTree.toList();
		
		for(TreeNode<BomTreeNodeData> node : bomTreeNodeList)
		{
			System.out.println("BomDetail Id : " + node.getData().getBomDetail().getMaterialCode());
		}
		 
		
		//List<ProductionOrder> productionOrders = new ArrayList<ProductionOrder>();
          
		for(TreeNode<BomTreeNodeData> node : bomTreeNodeList)
		{
		 
			try
			{
				System.out.println("BomDetail Id : " + node.getData().getBomDetail().getId());
				if(node.getData().getBomDetail().getMaterial().getMaterialType().isProduced())
				{
					ProductionOrder productionOrder = new ProductionOrder();
					// Set the bom (master card)
					productionOrder.setBom(bomService.findBOM(bomTree.getId()));
					
					productionOrder.setSalesOrderMaterial(salesOrderMaterial);
					productionOrder.setSalesOrder(salesOrderMaterial.getSalesOrder());

					//productionOrder.setDocNo(Integer.toString(j));
					productionOrder.setQuantityOrder(node.getData().getTreeOriginalQuantityRequired() * salesOrderMaterial.getQuantityOrder());
					
					productionOrder.setMaterial(node.getData().getBomDetail().getMaterial());
					
					
					List<Routing> routings = routingService.findRoutingsByMaterialId(productionOrder.getMaterial().getId());
					
					for(Routing routing : routings)
					{
						
						ProductionOrderOperation productionOrderOperation = new ProductionOrderOperation();
						productionOrderOperation.setOperation(routing.getOperation());
						productionOrderOperation.setSequence(routing.getSequence());
						productionOrderOperation.setQuantityOrder(productionOrder.getQuantityOrder());
						productionOrderOperation.setQuantityReported((Double)0.0);
						
						
						productionOrder.addProductionOrderOperation(productionOrderOperation);
						
					}
					
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
					
					productionOrder.setCreateApp(rowInfo.getCreateApp());
					productionOrder.setModifyApp(rowInfo.getModifyApp());
					productionOrder.setCreateLogin(rowInfo.getCreateLogin());
					productionOrder.setModifyLogin(rowInfo.getModifyLogin());
					//productionOrder.setSessionId(rowInfo.getSessionId());
					
					//productionOrder.setRecordStatus(rowInfo.getRecordStatus());
					//productionOrder.setCreateTimestamp(rowInfo.getCreateTimestamp());
					//productionOrder.setModifyTimestamp(rowInfo.getModifyTimestamp());

					
					// Insert the productionOrder to db
					//productionOrderService.addProductionOrder(productionOrder);
					
					System.out.println("[After phantom explosion] :" + productionOrderChildren.size());
					//List<ProductionOrderMaterial> productionOrderMaterials = new ArrayList<ProductionOrderMaterial>();
					// After phantom explosion , productionOrderChildren should contain all the production order materials					
					for(TreeNode<BomTreeNodeData> child : productionOrderChildren)
					{
						ProductionOrderMaterial productionOrderMaterial = new ProductionOrderMaterial();
						
						productionOrderMaterial.setMaterial(child.getData().getBomDetail().getMaterial());
						//productionOrderMaterial.setProductionOrder(productionOrder);
						productionOrderMaterial.setQuantityConsumed(0.0);
						productionOrderMaterial.setQuantityRequired(child.getData().getTreeOriginalQuantityRequired() * productionOrder.getQuantityOrder());
						productionOrderMaterial.setOperation(child.getData().getBomDetail().getOperation());
						
						productionOrderMaterial.setCreateApp(rowInfo.getCreateApp());
						productionOrderMaterial.setModifyApp(rowInfo.getModifyApp());
						productionOrderMaterial.setCreateLogin(rowInfo.getCreateLogin());
						productionOrderMaterial.setModifyLogin(rowInfo.getModifyLogin());
						//productionOrderMaterial.setSessionId(rowInfo.getSessionId());
						productionOrderMaterial.setRecordStatus(rowInfo.getRecordStatus());
						productionOrderMaterial.setCreateTimestamp(rowInfo.getCreateTimestamp());
						productionOrderMaterial.setModifyTimestamp(rowInfo.getModifyTimestamp());
						
						
						//productionOrderMaterials.add(productionOrderMaterial);
						productionOrder.addProductionOrderMaterial(productionOrderMaterial);

					}
					
					
					
					
					productionOrderService.addProductionOrder(productionOrder);
					// update the production order with the production order materials
					//productionOrderService.updateProductionOrder(productionOrder);
					
					
					// Add the productionOrderMaterial
					
					//for(ProductionOrderMaterial pom : productionOrderMaterials)
					//	productionOrder.addProductionOrderMaterial(pom);
										

				    // Added to list (for future , to have a addProductionOrders function to insert record by list
				    productionOrders.add(productionOrder);
					
					
				}
				
				
			}
			catch(Exception e)
			{
				//System.out.println(e.getMessage());
				throw e;
			}
			
			
			
		}
		
	}
	
	
}
