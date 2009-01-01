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
import com.quesofttech.business.domain.security.Program;
import com.quesofttech.business.domain.security.iface.IProgramServiceLocal;
import com.quesofttech.business.domain.security.iface.IProgramServiceRemote;

@Stateless
@Local(IProgramServiceLocal.class)
@Remote(IProgramServiceRemote.class)
public class ProgramService extends BaseService implements IProgramServiceLocal, IProgramServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// Program

	public Program findProgram(Long id) throws DoesNotExistException {
		Program program = (Program) find(Program.class, id);
		return program;
	}

	@SuppressWarnings("unchecked")
	public List<Program> findPrograms() throws DoesNotExistException {
		Query q = _em.createQuery("select program from Program program where program.rowInfo.recordStatus='A' order by program.id");
		List l = q.getResultList();
		return l;
	}
/*
	public Program findProgramByType(String type) {
		Program program = _em.find(Program.class, id);
		return program;
	}
*/
	
	
	public void updateProgram(Program program) throws BusinessException {		
		program = (Program) merge(program);
	}

	public void logicalDeleteProgram(Program program) throws BusinessException {
		program.rowInfo.setRecordStatus("D");
		updateProgram(program);
	}
	
	public void addProgram(Program program) throws BusinessException {
		
		//try{
		System.out.println("just before persist in ProgramService");
		persist(program);
		
		System.out.println("just after persist in ProgramService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[ProgramService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addProgram] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
}
