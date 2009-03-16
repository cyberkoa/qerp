package com.quesofttech.business.domain.production.iface;

import java.util.List;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.production.ProductionOrder;
import com.quesofttech.business.domain.production.ProductionOrderOperation;import com.quesofttech.business.domain.production.ProductionOrderMaterial;
import com.quesofttech.business.domain.sales.*;
import com.quesofttech.business.domain.inventory.*;
/**
 * The <code>IProductionOrderServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IProductionOrderServiceRemote {

	// ProductionOrder

	ProductionOrder findProductionOrder(Long id)throws DoesNotExistException;

	List<ProductionOrder> findProductionOrders() throws DoesNotExistException;

	void updateProductionOrder(ProductionOrder productionOrder) throws BusinessException;
	
	void addProductionOrder(ProductionOrder productionOrder) throws BusinessException;
	
	void logicalDeleteProductionOrder(ProductionOrder productionOrder) throws BusinessException;

	
	// ProductionOrderOperationOperation

	ProductionOrderOperation findProductionOrderOperation(Long id)throws DoesNotExistException;

	List<ProductionOrderOperation> findProductionOrderOperations() throws DoesNotExistException;

	List<ProductionOrderOperation> findProductionOrderOperationsByProductionOrderId(Long ProductionOrderId) throws DoesNotExistException;
	
	List<ProductionOrderOperation> findProductionOrderOperationsByProductionOrder(ProductionOrder productionOrder) throws DoesNotExistException;
	
	void updateProductionOrderOperation(ProductionOrderOperation ProductionOrder) throws BusinessException;
	
	void addProductionOrderOperation(ProductionOrderOperation ProductionOrder) throws BusinessException;
	
	void logicalDeleteProductionOrderOperation(ProductionOrderOperation productionOrderOperation) throws BusinessException;

	
	

	// ProductionOrderMaterial
	List<ProductionOrder> findProductionOrderBySalesOrder(SalesOrder salesorder, Material material)throws DoesNotExistException;
	List<ProductionOrder> findProductionOrderBySalesOrderMaterialId(Long salesOrderMaterialId)throws DoesNotExistException;
	
	
	ProductionOrderMaterial findProductionOrderMaterial(Long id)throws DoesNotExistException;

	List<ProductionOrderMaterial> findProductionOrderMaterials() throws DoesNotExistException;

	void updateProductionOrderMaterial(ProductionOrderMaterial productionOrderMaterial) throws BusinessException;
	
	void addProductionOrderMaterial(long id,ProductionOrderMaterial productionOrderMaterial) throws BusinessException;
	
	void logicalDeleteProductionOrderMaterial(ProductionOrderMaterial productionOrderMaterial) throws BusinessException;
	
	List<ProductionOrderMaterial> findProductionOrderMaterialsByProductionOrderId(Long productionOrderId) throws DoesNotExistException;
	
	List<ProductionOrderMaterial> findProductionOrderMaterialsByProductionOrder(ProductionOrder productionOrder) throws DoesNotExistException;
	
	
}
