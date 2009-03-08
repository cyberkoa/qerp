package com.quesofttech.business.domain.production;

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
import com.quesofttech.business.domain.production.Routing;
import com.quesofttech.business.domain.production.iface.IRoutingServiceLocal;
import com.quesofttech.business.domain.production.iface.IRoutingServiceRemote;

@Stateless
@Local(IRoutingServiceLocal.class)
@Remote(IRoutingServiceRemote.class)
public class RoutingService extends BaseService implements IRoutingServiceLocal, IRoutingServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// Routing

	public Routing findRouting(Long id) throws DoesNotExistException {
		Routing routing = (Routing) find(Routing.class, id);
		return routing;
	}

	@SuppressWarnings("unchecked")
	public List<Routing> findRoutings() throws DoesNotExistException {
		Query q = _em.createQuery("select routing from Routing routing order by routing.id");
		List l = q.getResultList();
		return l;
	}
/*
	public Routing findRoutingByType(String type) {
		Routing routing = _em.find(Routing.class, id);
		return routing;
	}
*/
	
	
	public void updateRouting(Routing routing) throws BusinessException {		
		routing = (Routing) merge(routing);
	}

	public void logicalDeleteRouting(Routing routing) throws BusinessException {
		routing.setRecordStatus("D");
		updateRouting(routing);
	}
	
	public void addRouting(Routing routing) throws BusinessException {
		
		//try{
		System.out.println("just before persist in RoutingService");
		persist(routing);
		
		System.out.println("just after persist in RoutingService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[RoutingService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addRouting] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
	
	@SuppressWarnings("unchecked")
	public List<Routing> findRoutingsByMaterialId(Long materialId) throws DoesNotExistException {
		Query q = _em.createQuery("select routing from Routing routing where routing.material.id=:materialId order by routing.operation");
		q.setParameter("materialId", materialId);
		List l = q.getResultList();
		return l;
	}
	
}
