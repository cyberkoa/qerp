package com.quesofttech.business.domain.general.iface;

import java.util.List;

import com.quesofttech.business.domain.general.Operation;import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;


/**
 * The <code>IOperationServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IOperationServiceRemote {

	// Operation

	Operation findOperation(Long id)throws DoesNotExistException;

	List<Operation> findOperations() throws DoesNotExistException;
	
	List<Operation> findOperationsByType(String type) throws DoesNotExistException;

	void updateOperation(Operation operation) throws BusinessException;
	
	void addOperation(Operation operation) throws BusinessException;
	
	void logicalDeleteOperation(Operation operation) throws BusinessException;

}
