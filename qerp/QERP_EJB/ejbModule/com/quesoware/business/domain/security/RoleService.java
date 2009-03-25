package com.quesoware.business.domain.security;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.quesofttech.business.domain.base.BaseService;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.domain.security.Role;
import com.quesoware.business.domain.security.iface.IRoleServiceLocal;
import com.quesoware.business.domain.security.iface.IRoleServiceRemote;

@Stateless
@Local(IRoleServiceLocal.class)
@Remote(IRoleServiceRemote.class)
public class RoleService extends BaseService implements IRoleServiceLocal, IRoleServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// Role

	public Role findRole(Long id) throws DoesNotExistException {
		Role role = (Role) find(Role.class, id);
		return role;
	}

	@SuppressWarnings("unchecked")
	public List<Role> findRoles() throws DoesNotExistException {
		Query q = _em.createQuery("select role from Role role order by role.id");
		List l = q.getResultList();
		return l;
	}
/*
	public Role findRoleByType(String type) {
		Role role = _em.find(Role.class, id);
		return role;
	}
*/
	
	
	public void updateRole(Role role) throws BusinessException {		
		role = (Role) merge(role);
	}

	public void logicalDeleteRole(Role role) throws BusinessException {
		role.setRecordStatus("D");
		updateRole(role);
	}
	
	public void addRole(Role role) throws BusinessException {
		
		//try{
		System.out.println("just before persist in RoleService");
		persist(role);
		
		System.out.println("just after persist in RoleService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[RoleService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addRole] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
}
