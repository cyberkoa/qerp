package com.quesofttech.business.domain.production;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.base.BaseService;
import com.quesofttech.business.domain.production.ProductionOrder;
import com.quesofttech.business.domain.production.iface.IProductionOrderServiceLocal;
import com.quesofttech.business.domain.production.iface.IProductionOrderServiceRemote;

import com.quesofttech.business.domain.production.ProductionOrderMaterial;
import com.quesofttech.business.domain.production.ProductionOrderOperation;
import com.quesofttech.business.domain.sales.SalesOrder;
import com.quesofttech.business.domain.sales.SalesOrderMaterial;

import com.quesofttech.business.domain.general.BomDetail;
import com.quesofttech.business.domain.general.BomTree;
import com.quesofttech.business.domain.general.BomService;
import com.quesofttech.business.domain.general.iface.IBomServiceLocal;

import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.inventory.MaterialType;

import com.quesofttech.business.domain.system.iface.IDocumentTypeServiceLocal;


@Stateless
@Local(IProductionOrderServiceLocal.class)
@Remote(IProductionOrderServiceRemote.class)
public class ProductionOrderService extends BaseService implements IProductionOrderServiceLocal, IProductionOrderServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	@EJB(beanName="DocumentTypeService")
	private IDocumentTypeServiceLocal documentTypeService;	
	
	
	// ProductionOrder

	public ProductionOrder findProductionOrder(Long id) throws DoesNotExistException {
		ProductionOrder productionOrder = (ProductionOrder) find(ProductionOrder.class, id);
		return productionOrder;
	}

	@SuppressWarnings("unchecked")
	public List<ProductionOrder> findProductionOrders() throws DoesNotExistException {
		Query q = _em.createQuery("select productionOrder from ProductionOrder productionOrder where productionOrder.rowInfo.recordStatus='A' order by productionOrder.id ");
		List l = q.getResultList();
		return l;
	}
/*
	public ProductionOrder findProductionOrderByType(String type) {
		ProductionOrder productionOrder = _em.find(ProductionOrder.class, id);
		return productionOrder;
	}
*/
	
	
	public void updateProductionOrder(ProductionOrder productionOrder) throws BusinessException {	
		System.out.println("[updateProductionOrder] before merging");
		
		ProductionOrder oldProductionOrder = this.findProductionOrder(productionOrder.getId());
		
		if(productionOrder.getQuantityOrder()!=oldProductionOrder.getQuantityOrder())
		{
			for(ProductionOrderOperation poo : productionOrder.getProductionOrderOperations())
			{
				poo.setQuantityOrder(poo.getQuantityOrder()*(productionOrder.getQuantityOrder()/oldProductionOrder.getQuantityOrder()));
			}
			
			for(ProductionOrderMaterial pom : productionOrder.getProductionOrderMaterials())
			{
				System.out.println("ProdO service : " + oldProductionOrder.getQuantityOrder() + "," + productionOrder.getQuantityOrder());
				pom.setQuantityRequired(pom.getQuantityRequired()*(productionOrder.getQuantityOrder()/oldProductionOrder.getQuantityOrder()));
			}
		}
		
		productionOrder = (ProductionOrder) merge(productionOrder);
		System.out.println("[updateProductionOrder] after merging");
	}

	public void logicalDeleteProductionOrder(ProductionOrder productionOrder) throws BusinessException {
		productionOrder.setRecordStatus("D");
		
		for(ProductionOrderOperation productionOrderOperation : productionOrder.getProductionOrderOperations())
		{
			productionOrderOperation.setRecordStatus("D");
		}
		
		for(ProductionOrderMaterial productionOrderMaterial : productionOrder.getProductionOrderMaterials())
		{
			productionOrderMaterial.setRecordStatus("D");
		}
		
		updateProductionOrder(productionOrder);
	}
	
	public void addProductionOrder(ProductionOrder productionOrder) throws BusinessException {
		
		//try{
		System.out.println("just before persist in ProductionOrderService , productionOrder : " + productionOrder + " DocNo : " + productionOrder.getDocNo());
		
		if(productionOrder.getDocNo()==null||productionOrder.getDocNo()==0)
		{
		   
		   productionOrder.setDocumentType(documentTypeService.getNextNumberByType("P"));
		   
		   if(productionOrder.getDocumentType()!=null)
		   {
		      productionOrder.setDocNo(productionOrder.getDocumentType().getRunningNo());
		   }
		   else
		   {
			   // error
			   System.out.println("DocType is null");
		   }
		}
		
		persist(productionOrder);
		
		System.out.println("just after persist in ProductionOrderService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[ProductionOrderService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addProductionOrder] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
	
	// ProductionOrderOperation
	

	public void addProductionOrderOperation(ProductionOrderOperation productionOrderOperation) throws BusinessException {
		persist(productionOrderOperation);
	}
	
	public void updateProductionOrderOperation(ProductionOrderOperation productionOrderOperation) throws BusinessException {		
		productionOrderOperation = (ProductionOrderOperation) merge(productionOrderOperation);
	}
	
	
	public void logicalDeleteProductionOrderOperation(ProductionOrderOperation productionOrderOperation) throws BusinessException {
		productionOrderOperation.setRecordStatus("D");
						
		updateProductionOrderOperation(productionOrderOperation);
	}
		
	
	// Finders

	public ProductionOrderOperation findProductionOrderOperation(Long id) throws DoesNotExistException {
		ProductionOrderOperation productionOrderMaterial = (ProductionOrderOperation) find(ProductionOrderOperation.class, id);
		return productionOrderMaterial;
	}

	@SuppressWarnings("unchecked")
	public List<ProductionOrderOperation> findProductionOrderOperations() throws DoesNotExistException {
		Query q = _em.createQuery("select prodoo from ProductionOrderOperation prodoo where prodoo.rowInfo.recordStatus='A' order by prodoo.sequence ");
		List l = q.getResultList();
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductionOrderOperation> findProductionOrderOperationsByProductionOrderId(Long productionOrderId) throws DoesNotExistException {
		//ProductionOrder productionOrder = this.findProductionOrder(productionOrderId);
		
		Query q = _em.createQuery("select prodoo from ProductionOrderOperation prodoo where prodoo.productionOrder.id = :productionOrderId AND prodoo.rowInfo.recordStatus='A'" +
				" order by prodoo.sequence");
		q.setParameter("productionOrderId", productionOrderId);
		List l = q.getResultList();
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductionOrderOperation> findProductionOrderOperationsByProductionOrder(ProductionOrder productionOrder) throws DoesNotExistException {
		
		Query q = _em.createQuery("select prodoo from ProductionOrderOperation prodoo where prodoo.productionOrder = :productionOrder AND prodoo.rowInfo.recordStatus='A'" +
				" order by prodoo.id");
		q.setParameter("productionOrder", productionOrder);
		List l = q.getResultList();
		return l;
	}
	@SuppressWarnings("unchecked")
	public List<ProductionOrder> findProductionOrderBySalesOrder(SalesOrder salesorder,Material material) throws DoesNotExistException {
		
		Query q = _em.createQuery("select prodo from ProductionOrder prodo where prodo.salesOrder = :salesorder and prodo.material = :material AND prodo.rowInfo.recordStatus='A'" +
				" order by prodo.id");
		q.setParameter("salesordermaterial", salesorder);
		q.setParameter("material", material);
		List l = q.getResultList();
		return l;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ProductionOrder> findProductionOrderBySalesOrderMaterial(Long salesordermaterial) throws DoesNotExistException {
		
		//System.out.println("salesordermaterial(): " + salesordermaterial.toString() + ", " + salesordermaterial.getId());
		Query q = _em.createQuery("select prodo from ProductionOrder prodo where prodo.salesOrderMaterial.id = :salesordermaterial AND prodo.rowInfo.recordStatus='A'" +
				" order by prodo.id");
		q.setParameter("salesordermaterial", salesordermaterial);
		System.out.println(q.toString());
		List l = q.getResultList();
		return l;
	}
	
	
	// ProductionOrderMaterial
	
	public void addProductionOrderMaterial(long id,ProductionOrderMaterial productionOrderMaterial) throws BusinessException {
		//persist(productionOrderMaterial);
		//ProductionOrderMaterial productionOrderMaterial = new ProductionOrderMaterial();
		ProductionOrder productionOrder = findProductionOrder(id);
		/*
		productionOrderMaterial.setMaterial(productionOrderMaterial.getMaterial());
		productionOrderMaterial.setQuantityRequired(productionOrderMaterial.getQuantityRequired());
		productionOrderMaterial.setQuantityConsumed(productionOrderMaterial.getQuantityConsumed());
		productionOrderMaterial.setRecordStatus("A");
		*/
		productionOrder.addProductionOrderMaterial(productionOrderMaterial);
		
		
		/*
		SalesOrderMaterial salesOrderLine1 = new SalesOrderMaterial();
		System.out.println("finding id:" + id);
		SalesOrder salesOrder = findSalesOrder(id);
		//salesOrderLine1.setSalesOrder(salesOrder);
		System.out.println("found id:" + id);
		salesOrderLine1.setLine((salesordermaterial.getLine()));
		salesOrderLine1.setQuantityOrder(salesordermaterial.getQuantityOrder());
		salesOrderLine1.setQuantityShipped(salesordermaterial.getQuantityShipped());
		salesOrderLine1.setCustomerPOLine(salesordermaterial.getCustomerPOLine());
		salesOrderLine1.setMaterial(salesordermaterial.getMaterial());
		salesOrder.addSalesOrderMaterial(salesOrderLine1);
		*/
		
		
	}	

	public void updateProductionOrderMaterial(ProductionOrderMaterial productionOrderMaterial) throws BusinessException {		
		productionOrderMaterial = (ProductionOrderMaterial) merge(productionOrderMaterial);
	}
	
	
	public void logicalDeleteProductionOrderMaterial(ProductionOrderMaterial productionOrderMaterial) throws BusinessException {
		productionOrderMaterial.setRecordStatus("D");
						
		updateProductionOrderMaterial(productionOrderMaterial);
	}
	
	
	
	
	
	// Finders

	public ProductionOrderMaterial findProductionOrderMaterial(Long id) throws DoesNotExistException {
		ProductionOrderMaterial productionOrderMaterial = (ProductionOrderMaterial) find(ProductionOrderMaterial.class, id);
		return productionOrderMaterial;
	}

	@SuppressWarnings("unchecked")
	public List<ProductionOrderMaterial> findProductionOrderMaterials() throws DoesNotExistException {
		Query q = _em.createQuery("select prodom from ProductionOrderMaterial prodom where prodom.rowInfo.recordStatus='A' order by prodom.id ");
		List l = q.getResultList();
		return l;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ProductionOrderMaterial> findProductionOrderMaterialsByProductionOrderId(Long productionOrderId) throws DoesNotExistException {
		
		ProductionOrder productionOrder = this.findProductionOrder(productionOrderId);
		
		Query q = _em.createQuery("select prodom from ProductionOrderMaterial prodom where prodom.productionOrder = :productionOrder AND prodom.rowInfo.recordStatus='A'" +
				" order by prodom.id");
		q.setParameter("productionOrder", productionOrder);
		List l = q.getResultList();
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductionOrderMaterial> findProductionOrderMaterialsByProductionOrder(ProductionOrder productionOrder) throws DoesNotExistException {
		
		Query q = _em.createQuery("select prodom from ProductionOrderMaterial prodom where prodom.productionOrder = :productionOrder AND prodom.rowInfo.recordStatus='A'" +
				" order by prodom.id");
		q.setParameter("productionOrder", productionOrder);
		List l = q.getResultList();
		return l;
	}
	/*
	public void releaseWorkOrder(Material material) throws DoesNotExistException
	{
	    BomService bomService = new BomService();	
	    BomTree bomTree = null;
	    
		bomTree = bomService.buildBomTree(material, "P");
		
		
	    
		List<BomDetail> childrenBomDetail = null;
		
		try	{ 
			childrenBomDetail = bomService.findBomDetailsByParentMaterial(material, "P");
		}
		catch(DoesNotExistException e) {
			return; // no child
		}
		
	}
	*/
	
	
}
