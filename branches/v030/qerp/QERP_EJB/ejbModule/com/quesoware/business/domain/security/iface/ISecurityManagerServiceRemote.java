package com.quesoware.business.domain.security.iface;


import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.domain.security.User;

/**
 * The <code>ISecurityManagerSvcRemote</code> bean exposes the business methods in the interface.
 */
public interface ISecurityManagerServiceRemote {

	/*******************************************************************************************************************
	 * BUSINESS METHODS
	 ******************************************************************************************************************/

	/**
	 * This method provides a way for users to change their own userPassword.
	 */
	void changeUserPassword(Long id, String currentPassword, String newPassword) throws BusinessException;

	/**
	 * This method provides a way for security officers to "reset" the userPassword.
	 */
	void changeUserPassword(Long id, String newPassword) throws BusinessException;

	/*******************************************************************************************************************
	 * PERSISTENCE METHODS
	 ******************************************************************************************************************/

	// User
	Long createUser(User user, String password) throws BusinessException;

	void updateUser(User user) throws BusinessException;

	void deleteUser(User user) throws BusinessException;

}
