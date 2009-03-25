package com.quesoware.business.domain.security;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;


import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.domain.base.BaseService;
import com.quesoware.business.domain.security.iface.ISecurityManagerServiceLocal;
import com.quesoware.business.domain.security.iface.ISecurityManagerServiceRemote;

//@SecurityDomain("jumpstart")
@Stateless
@Local(ISecurityManagerServiceLocal.class)
@Remote(ISecurityManagerServiceRemote.class)
public class SecurityManagerService extends BaseService implements ISecurityManagerServiceLocal, ISecurityManagerServiceRemote {

	/*******************************************************************************************************************
	 * BUSINESS METHODS
	 ******************************************************************************************************************/

	/**
	 * This method provides a way for users to change their own userPassword.
	 */
	public void changeUserPassword(Long id, String currentPassword, String newPassword) throws BusinessException {
		User user = (User) find(User.class, id);
		user.changePassword(currentPassword, newPassword);
	}

	/**
	 * This method provides a way for security officers to "reset" the userPassword.
	 */
	public void changeUserPassword(Long id, String newPassword) throws BusinessException {
		User user = (User) find(User.class, id);
		user.setPassword(newPassword);
	}

	public Long createUser(User user, String password) throws BusinessException {
		user.changePassword(null, password);
		persist(user);
		// DO NOT return the whole User, because its UserPassword is present in it
		return user.getId();
	}

	public void updateUser(User user) throws BusinessException {
		merge(user);
	}

	public void deleteUser(User user) throws BusinessException {
		user = (User) merge(user);
		remove(user);
	}
	


}
