package com.quesofttech.business.domain.security;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.base.BaseService;
import com.quesofttech.business.domain.security.UserRole;
import com.quesofttech.business.domain.security.iface.IUserRoleServiceLocal;
import com.quesofttech.business.domain.security.iface.IUserRoleServiceRemote;

@Stateless
@Local(IUserRoleServiceLocal.class)
@Remote(IUserRoleServiceRemote.class)
public class UserRoleService extends BaseService implements IUserRoleServiceLocal, IUserRoleServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// UserRole

	public UserRole findUserRole(Long id) throws DoesNotExistException {
		UserRole userRole = (UserRole) find(UserRole.class, id);
		return userRole;
	}

	@SuppressWarnings("unchecked")
	public List<UserRole> findUserRoles() throws DoesNotExistException {
		Query q = _em.createQuery("select userRole from UserRole userRole where userRole.rowInfo.recordStatus='A' order by userRole.id");
		List l = q.getResultList();
		return l;
	}
/*
	public UserRole findUserRoleByType(String type) {
		UserRole userRole = _em.find(UserRole.class, id);
		return userRole;
	}
*/
	
	
	public void updateUserRole(UserRole userRole) throws BusinessException {		
		userRole = (UserRole) merge(userRole);
	}

	public void logicalDeleteUserRole(UserRole userRole) throws BusinessException {
		userRole.setRecordStatus("D");
		updateUserRole(userRole);
	}
	
	public void addUserRole(UserRole userRole) throws BusinessException {
		
		//try{
		System.out.println("just before persist in UserRoleService");
		persist(userRole);
		
		System.out.println("just after persist in UserRoleService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[UserRoleService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addUserRole] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
}
