package com.quesofttech.business.domain.sales.iface;


import java.util.List;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.query.SearchOptions;
import com.quesofttech.business.domain.sales.SalesOrder;
import com.quesofttech.business.domain.sales.SalesOrderMaterial;
import com.quesofttech.business.domain.sales.dto.SalesOrderSearchFields;
import com.quesofttech.business.domain.sales.dto.SalesOrderMaterialSearchFields;
import com.quesofttech.business.domain.embeddable.RowInfo;

/**
 * The <code>ISalesOrderServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface ISalesOrderServiceRemote {

	// Sales Order

	SalesOrder findSalesOrder(Long id)throws DoesNotExistException;
	List<SalesOrder> findSalesOrders() throws DoesNotExistException;
	void updateSalesOrder(SalesOrder salesOrder) throws BusinessException;
	void addSalesOrder(SalesOrder salesOrder) throws BusinessException;
	void addSalesOrderMaterial(Long id, SalesOrderMaterial salesordermaterial) throws BusinessException;
	void logicalDeleteSalesOrder(SalesOrder salesOrder) throws BusinessException;
	
	// SalesOrderMaterial
	
	SalesOrderMaterial findSalesOrderMaterial(Long id)throws DoesNotExistException;
	List<SalesOrderMaterial> findSalesOrderMaterialsBySalesOrderId(Long id) throws DoesNotExistException;
	List<SalesOrderMaterial> findSalesOrderMaterialsBySalesOrder(SalesOrder salesOrder) throws DoesNotExistException;
	
	void updateSalesOrderMaterial(SalesOrderMaterial salesOrderMaterial) throws BusinessException;
	void logicalDeleteSalesOrderMaterial(SalesOrderMaterial salesOrderMaterial) throws BusinessException;
	
	List<SalesOrder> findSalesOrdersBySearchFieldsRange(SalesOrderSearchFields lower, SalesOrderSearchFields upper,SearchOptions options) throws DoesNotExistException;
	List<SalesOrder> findSalesOrdersBySearchFields(SalesOrderSearchFields searchFields,SearchOptions options) throws DoesNotExistException;

	
	List<SalesOrderMaterial> findSalesOrdersMaterialBySearchFieldsRange(SalesOrderMaterialSearchFields lower, SalesOrderMaterialSearchFields upper,SearchOptions options) throws DoesNotExistException;
	List<SalesOrderMaterial> findSalesOrdersMaterialBySearchFields(SalesOrderMaterialSearchFields searchFields,SearchOptions options) throws DoesNotExistException;

	void convertOrderMaterialToProductionOrder(RowInfo rowInfo, SalesOrderMaterial salesOrderMaterial);
}
