package com.quesofttech.business.domain.security.iface;

import java.util.List;

import com.quesofttech.business.domain.security.UserRole;import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;


/**
 * The <code>IUserRoleServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IUserRoleServiceRemote {

	// UserRole

	UserRole findUserRole(Long id)throws DoesNotExistException;

	List<UserRole> findUserRoles() throws DoesNotExistException;

	void updateUserRole(UserRole userRole) throws BusinessException;
	
	void addUserRole(UserRole userRole) throws BusinessException;
	
	void logicalDeleteUserRole(UserRole userRole) throws BusinessException;

}
