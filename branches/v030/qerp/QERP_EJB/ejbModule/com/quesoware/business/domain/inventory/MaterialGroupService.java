package com.quesoware.business.domain.inventory;

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
import com.quesoware.business.domain.inventory.MaterialGroup;
import com.quesoware.business.domain.inventory.iface.IMaterialGroupServiceLocal;
import com.quesoware.business.domain.inventory.iface.IMaterialGroupServiceRemote;

@Stateless
@Local(IMaterialGroupServiceLocal.class)
@Remote(IMaterialGroupServiceRemote.class)
public class MaterialGroupService extends BaseService implements IMaterialGroupServiceLocal, IMaterialGroupServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// MaterialGroup

	public MaterialGroup findMaterialGroup(Long id) throws DoesNotExistException {
		MaterialGroup materialGroup = (MaterialGroup) find(MaterialGroup.class, id);
		return materialGroup;
	}

	@SuppressWarnings("unchecked")
	public List<MaterialGroup> findMaterialGroups() throws DoesNotExistException {
		Query q = _em.createQuery("select materialGroup from MaterialGroup materialGroup where materialGroup.rowInfo.recordStatus='A' order by materialGroup.id");
		List l = q.getResultList();
		return l;
	}
/*
	public MaterialGroup findMaterialGroupByType(String type) {
		MaterialGroup materialGroup = _em.find(MaterialGroup.class, id);
		return materialGroup;
	}
*/
	
	
	public void updateMaterialGroup(MaterialGroup materialGroup) throws BusinessException {		
		materialGroup = (MaterialGroup) merge(materialGroup);
	}

	public void logicalDeleteMaterialGroup(MaterialGroup materialGroup) throws BusinessException {
		materialGroup.setRecordStatus("D");
		updateMaterialGroup(materialGroup);
	}
	
	public void addMaterialGroup(MaterialGroup materialGroup) throws BusinessException {
		
		//try{
		System.out.println("just before persist in MaterialGroupService");
		persist(materialGroup);
		
		System.out.println("just after persist in MaterialGroupService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[MaterialGroupService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addMaterialGroup] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
}
