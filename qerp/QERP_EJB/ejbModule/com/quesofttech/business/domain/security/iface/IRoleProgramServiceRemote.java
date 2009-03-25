package com.quesofttech.business.domain.security.iface;

import java.util.List;

import com.quesofttech.business.domain.security.RoleProgram;import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;


/**
 * The <code>IRoleServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IRoleProgramServiceRemote {

	// Role

	RoleProgram findRoleProgram(Long id)throws DoesNotExistException;

	List<RoleProgram> findRolePrograms() throws DoesNotExistException;

	void updateRoleProgram(RoleProgram roleProgram) throws BusinessException;
	
	void addRoleProgram(RoleProgram roleProgram) throws BusinessException;
	
	void logicalDeleteRoleProgram(RoleProgram roleProgram) throws BusinessException;

}
