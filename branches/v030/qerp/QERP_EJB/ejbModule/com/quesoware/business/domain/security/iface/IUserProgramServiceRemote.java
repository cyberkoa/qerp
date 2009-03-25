package com.quesoware.business.domain.security.iface;

import java.util.List;

import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.domain.security.UserProgram;


/**
 * The <code>IUserProgramServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IUserProgramServiceRemote {

	// UserProgram

	UserProgram findUserProgram(Long id)throws DoesNotExistException;

	List<UserProgram> findUserPrograms() throws DoesNotExistException;

	void updateUserProgram(UserProgram userProgram) throws BusinessException;
	
	void addUserProgram(UserProgram userProgram) throws BusinessException;
	
	void logicalDeleteUserProgram(UserProgram userProgram) throws BusinessException;

}
