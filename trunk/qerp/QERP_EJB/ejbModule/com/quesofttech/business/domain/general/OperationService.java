package com.quesofttech.business.domain.general;

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
import com.quesofttech.business.domain.general.Operation;
import com.quesofttech.business.domain.general.iface.IOperationServiceLocal;
import com.quesofttech.business.domain.general.iface.IOperationServiceRemote;

@Stateless
@Local(IOperationServiceLocal.class)
@Remote(IOperationServiceRemote.class)
public class OperationService extends BaseService implements IOperationServiceLocal, IOperationServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// Operation

	public Operation findOperation(Long id) throws DoesNotExistException {
		Operation operation = (Operation) find(Operation.class, id);
		return operation;
	}

	@SuppressWarnings("unchecked")
	public List<Operation> findOperations() throws DoesNotExistException {
		Query q = _em.createQuery("select operation from Operation operation where operation.rowInfo.recordStatus='A' order by operation.id");
		List l = q.getResultList();
		return l;
	}

	public List<Operation>  findOperationsByType(String type) {
		Query q = _em.createQuery("select operation from Operation operation where operation.rowInfo.recordStatus='A' and operation.type=:type order by operation.id");
		
		q.setParameter("type", type);
		List l = q.getResultList();
		
		return l;
	}

	
	
	public void updateOperation(Operation operation) throws BusinessException {		
		operation = (Operation) merge(operation);
	}

	public void logicalDeleteOperation(Operation operation) throws BusinessException {
		operation.setRecordStatus("D");
		updateOperation(operation);
	}
	
	public void addOperation(Operation operation) throws BusinessException {
		
		//try{
		System.out.println("just before persist in OperationService");
		persist(operation);
		
		System.out.println("just after persist in OperationService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[OperationService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addOperation] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
}
