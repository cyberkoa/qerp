package com.quesofttech.business.domain.security;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.quesofttech.business.domain.base.BaseService;
import com.quesofttech.business.domain.security.Role;
import com.quesofttech.business.domain.security.iface.*;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;


@Stateless
@Local(IRoleProgramServiceLocal.class)
@Remote(IRoleProgramServiceRemote.class)
public class RoleProgramService extends BaseService implements IRoleProgramServiceLocal, IRoleProgramServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// Role

	public RoleProgram findRoleProgram(Long id) throws DoesNotExistException {
		RoleProgram roleProgram = (RoleProgram) find(RoleProgram.class, id);
		return roleProgram;
	}

	@SuppressWarnings("unchecked")
	public List<RoleProgram> findRolePrograms() throws DoesNotExistException {
		Query q = _em.createQuery("select roleProgram from RoleProgram roleProgram order by roleProgram.id");
		List l = q.getResultList();
		return l;
	}
/*
	public Role findRoleByType(String type) {
		Role role = _em.find(Role.class, id);
		return role;
	}
*/
	
	
	public void updateRoleProgram(RoleProgram roleProgram) throws BusinessException {		
		roleProgram = (RoleProgram) merge(roleProgram);
	}

	public void logicalDeleteRoleProgram(RoleProgram roleProgram) throws BusinessException {
		roleProgram.setRecordStatus("D");
		updateRoleProgram(roleProgram);
	}
	
	public void addRoleProgram(RoleProgram roleProgram) throws BusinessException {
		
		//try{
		//System.out.println("just before persist in RoleService");
		persist(roleProgram);
		
		//System.out.println("just after persist in RoleService");
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
