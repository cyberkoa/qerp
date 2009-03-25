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
import com.quesoware.business.domain.security.Module;
import com.quesoware.business.domain.security.iface.*;

@Stateless
@Local(IModuleServiceLocal.class)
@Remote(IModuleServiceRemote.class)
public class ModuleService extends BaseService implements IModuleServiceLocal, IModuleServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// Program

	public Module findModule(Long id) throws DoesNotExistException {
		Module module = (Module) find(Module.class, id);
		return module;
	}

	@SuppressWarnings("unchecked")
	public List<Module> findModules() throws DoesNotExistException {
		Query q = _em.createQuery("select module from Module module where module.rowInfo.recordStatus='A' order by module.id");
		List l = q.getResultList();
		return l;
	}
/*
	public Program findProgramByType(String type) {
		Program program = _em.find(Program.class, id);
		return program;
	}
*/
	
	
	public void updateModule(Module module) throws BusinessException {		
		module = (Module) merge(module);
	}

	public void logicalDeleteModule(Module module) throws BusinessException {
		module.setRecordStatus("D");
		updateModule(module);
	}
	
	public void addModule(Module module) throws BusinessException {
		
		//try{
		System.out.println("just before persist in ProgramService");
		persist(module);
		
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
