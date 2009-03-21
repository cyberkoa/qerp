package com.quesofttech.business.domain.inventory;

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
import com.quesofttech.business.domain.inventory.MaterialType;
import com.quesofttech.business.domain.inventory.iface.IMaterialTypeServiceLocal;
import com.quesofttech.business.domain.inventory.iface.IMaterialTypeServiceRemote;

@Stateless
@Local(IMaterialTypeServiceLocal.class)
@Remote(IMaterialTypeServiceRemote.class)
public class MaterialTypeService extends BaseService implements IMaterialTypeServiceLocal, IMaterialTypeServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// MaterialType

	public MaterialType findMaterialType(Long id) throws DoesNotExistException {
		MaterialType materialType = (MaterialType) find(MaterialType.class, id);
		return materialType;
	}

	@SuppressWarnings("unchecked")
	public List<MaterialType> findMaterialTypes() throws DoesNotExistException {
		Query q = _em.createQuery("select mt from MaterialType mt where mt.rowInfo.recordStatus='A' order by mt.id");
		List l = q.getResultList();
		return l;
	}
/*
	public MaterialType findMaterialTypeByType(String type) {
		MaterialType materialType = _em.find(MaterialType.class, id);
		return materialType;
	}
*/
	
	
	public void updateMaterialType(MaterialType materialType) throws BusinessException {		
		materialType = (MaterialType) merge(materialType);
	}

	public void logicalDeleteMaterialType(MaterialType materialType) throws BusinessException {
		materialType.setRecordStatus("D");
		updateMaterialType(materialType);
	}
	
	public void addMaterialType(MaterialType materialType) throws BusinessException {
		
		//try{
		//System.out.println("just before persist in MTService");
		persist(materialType);
		
		//System.out.println("just after persist in MTService");
		//}
		//catch (java.lang.RuntimeException e)
		//{
		//	System.out.println("[MaterialTypeService] RuntimeException caught in persist");
		//	throw (RuntimeException) e;
		
		//}
		//catch(Exception e)
		//{
		//	System.out.println("[addMaterialType] : exception caught during persist" + e.getMessage());
		//	throw (BusinessException) e;
		//}
		
	}
}
