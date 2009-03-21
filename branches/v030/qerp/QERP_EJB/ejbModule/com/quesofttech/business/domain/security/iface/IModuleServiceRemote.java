package com.quesofttech.business.domain.security.iface;

import java.util.List;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.security.*;

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
