package com.quesofttech.business.domain.security.iface;

import java.util.List;

import com.quesofttech.business.domain.security.Role;import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;


/**
 * The <code>IRoleServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IRoleServiceRemote {

	// Role

	
	Role findRole(Long id)throws DoesNotExistException;

	List<Role> findRoles() throws DoesNotExistException;

	void updateRole(Role role) throws BusinessException;
	
	void addRole(Role role) throws BusinessException;
	
	void logicalDeleteRole(Role role) throws BusinessException;

}
