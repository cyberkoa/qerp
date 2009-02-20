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
import com.quesofttech.business.domain.security.UserProgram;
import com.quesofttech.business.domain.security.iface.IUserProgramServiceLocal;
import com.quesofttech.business.domain.security.iface.IUserProgramServiceRemote;

@Stateless
@Local(IUserProgramServiceLocal.class)
@Remote(IUserProgramServiceRemote.class)
public class UserProgramService extends BaseService implements IUserProgramServiceLocal, IUserProgramServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// UserProgram

	public UserProgram findUserProgram(Long id) throws DoesNotExistException {
		UserProgram userProgram = (UserProgram) find(UserProgram.class, id);
		return userProgram;
	}

	@SuppressWarnings("unchecked")
	public List<UserProgram> findUserPrograms() throws DoesNotExistException {
		Query q = _em.createQuery("select userProgram from UserProgram userProgram where userProgram.rowInfo.recordStatus='A' order by userProgram.id");
		List l = q.getResultList();
		return l;
	}
/*
	public UserProgram findUserProgramByType(String type) {
		UserProgram userProgram = _em.find(UserProgram.class, id);
		return userProgram;
	}
*/
	
	
	public void updateUserProgram(UserProgram userProgram) throws BusinessException {		
		userProgram = (UserProgram) merge(userProgram);
	}

	public void logicalDeleteUserProgram(UserProgram userProgram) throws BusinessException {
		userProgram.setRecordStatus("D");
		updateUserProgram(userProgram);
	}
	
	public void addUserProgram(UserProgram userProgram) throws BusinessException {
		
		//try{
		System.out.println("just before persist in UserProgramService");
		persist(userProgram);
		
		System.out.println("just after persist in UserProgramService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[UserProgramService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addUserProgram] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
}
