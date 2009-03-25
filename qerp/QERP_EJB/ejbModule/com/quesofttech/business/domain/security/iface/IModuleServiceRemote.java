package com.quesofttech.business.domain.security.iface;

import java.util.List;

import com.quesofttech.business.domain.security.*;import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;


/**
 * The <code>IProgramServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IModuleServiceRemote {

	// Program

	Module findModule(Long id)throws DoesNotExistException;

	List<Module> findModules() throws DoesNotExistException;

	void updateModule(Module module) throws BusinessException;
	
	void addModule(Module module) throws BusinessException;
	
	void logicalDeleteModule(Module module) throws BusinessException;

}
