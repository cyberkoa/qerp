package com.quesofttech.business.domain.security.iface;

import java.util.List;

import com.quesofttech.business.domain.security.Program;import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;


/**
 * The <code>IProgramServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IProgramServiceRemote {

	// Program

	Program findProgram(Long id)throws DoesNotExistException;

	List<Program> findPrograms() throws DoesNotExistException;

	void updateProgram(Program program) throws BusinessException;
	
	void addProgram(Program program) throws BusinessException;
	
	void logicalDeleteProgram(Program program) throws BusinessException;

}
