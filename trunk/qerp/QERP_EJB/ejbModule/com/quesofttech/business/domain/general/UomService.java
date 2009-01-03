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
import com.quesofttech.business.domain.general.UOM;
import com.quesofttech.business.domain.general.iface.IUomServiceLocal;
import com.quesofttech.business.domain.general.iface.IUomServiceRemote;

@Stateless
@Local(IUomServiceLocal.class)
@Remote(IUomServiceRemote.class)
public class UomService extends BaseService implements IUomServiceLocal, IUomServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// UOM

	public UOM findUOM(Long id) throws DoesNotExistException {
		UOM uom = (UOM) find(UOM.class, id);
		return uom;
	}

	@SuppressWarnings("unchecked")
	public List<UOM> findUOMs() throws DoesNotExistException {
		Query q = _em.createQuery("select uom from UOM uom where uom.rowInfo.recordStatus='A' order by uom.id");
		List l = q.getResultList();
		return l;
	}
/*
	public UOM findUOMByType(String type) {
		UOM uom = _em.find(UOM.class, id);
		return uom;
	}
*/
	
	
	public void updateUOM(UOM uom) throws BusinessException {		
		uom = (UOM) merge(uom);
	}

	public void logicalDeleteUOM(UOM uom) throws BusinessException {
		uom.rowInfo.setRecordStatus("D");
		updateUOM(uom);
	}
	
	public void addUOM(UOM uom) throws BusinessException {
		
		//try{
		System.out.println("just before persist in UOMService");
		persist(uom);
		
		System.out.println("just after persist in UOMService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[UOMService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addUOM] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
}
