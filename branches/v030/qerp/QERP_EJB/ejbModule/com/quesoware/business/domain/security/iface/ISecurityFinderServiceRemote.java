package com.quesoware.business.domain.security.iface;

import java.util.List;


import com.quesoware.business.common.exception.AuthenticationException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.common.query.SearchOptions;
import com.quesoware.business.domain.security.Program;
import com.quesoware.business.domain.security.Role;
import com.quesoware.business.domain.security.User;
import com.quesoware.business.domain.security.UserPassword;
import com.quesoware.business.domain.security.dto.UserSearchFields;

/**
 * The <code>ISecurityFinderSvcRemote</code> bean exposes the business methods in the interface.
 */
public interface ISecurityFinderServiceRemote {
	
	// User
	
	User findUser(Long id) throws DoesNotExistException;
	
	UserPassword findUserPassword(Long id) throws DoesNotExistException;

	User findUserByLogin(String login) throws DoesNotExistException;

	List<User> findUsers();
	

	List<User> findUsers(UserSearchFields searchFields, SearchOptions searchOptions);
	
	User authenticateUser(String login, String password) throws AuthenticationException;
	
	
	// Role
	List<Role> findUserRoles(User user)  throws DoesNotExistException ;
	
	List<Role> findUserRoles(String login)  throws DoesNotExistException ;
	
	
	// Program
	List<Program> findAuthorizedProgramsByRole(Role role)  throws DoesNotExistException ;
	
	List<Program> findAuthorizedProgramsOfRolesByUser(User user)  throws DoesNotExistException ;
	
	List<Program> findAuthorizedProgramsOfUserByUser(User user)  throws DoesNotExistException  ;

	List<Program> findAuthorizedProgramsByUser(User user)  throws DoesNotExistException  ;

	List<Program> findAuthorizedProgramsByUserId(Long id)  throws DoesNotExistException  ;
	//List<Program> findUserAuthorizedPrograms(User user);

	Boolean IsAuthorizedToAccess(Long userId, String programCode)	throws DoesNotExistException ;
	
	
}
