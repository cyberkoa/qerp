package com.quesoware.business.domain.security.iface;

import java.util.List;

import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.domain.security.UserPassword;


/**
 * The <code>IRoleServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IUserPasswordServiceRemote {

	// Role

	
	UserPassword findUserPassword(Long id)throws DoesNotExistException;

	void updateUserPassword(UserPassword userPassword) throws BusinessException;
	
	void addUserPassword(UserPassword userPassword) throws BusinessException;
	
}
