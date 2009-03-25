package com.quesoware.business.domain.security;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.domain.base.BaseService;
import com.quesoware.business.domain.security.Role;
import com.quesoware.business.domain.security.iface.*;
@Stateless
@Local(IUserPasswordServiceLocal.class)
@Remote(IUserPasswordServiceRemote.class)
public class UserPasswordService extends BaseService implements IUserPasswordServiceLocal, IUserPasswordServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// Role

	public UserPassword findUserPassword(Long id) throws DoesNotExistException {
		UserPassword userPassword = (UserPassword) find(UserPassword.class, id);
		return userPassword;
	}
/*
	public Role findRoleByType(String type) {
		Role role = _em.find(Role.class, id);
		return role;
	}
*/
	
	
	public void updateUserPassword(UserPassword userPassword) throws BusinessException {		
		userPassword = (UserPassword) merge(userPassword);
	}

	
	public void addUserPassword(UserPassword userPassword) throws BusinessException {		
		System.out.println("just before persist in RoleService");
		persist(userPassword);		
		System.out.println("just after persist in RoleService");		
	}
}
