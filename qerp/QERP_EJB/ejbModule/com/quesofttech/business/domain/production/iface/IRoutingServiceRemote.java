package com.quesofttech.business.domain.production.iface;

import java.util.List;

import com.quesofttech.business.domain.production.Routing;import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;


/**
 * The <code>IRoutingServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IRoutingServiceRemote {

	// Routing

	Routing findRouting(Long id)throws DoesNotExistException;

	List<Routing> findRoutings() throws DoesNotExistException;

	void updateRouting(Routing routing) throws BusinessException;
	
	void addRouting(Routing routing) throws BusinessException;
	
	void logicalDeleteRouting(Routing routing) throws BusinessException;
	
	List<Routing> findRoutingsByMaterialId(Long materialId) throws DoesNotExistException;

}
