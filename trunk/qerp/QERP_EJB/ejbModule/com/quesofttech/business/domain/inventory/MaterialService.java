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
import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.inventory.iface.IMaterialServiceLocal;
import com.quesofttech.business.domain.inventory.iface.IMaterialServiceRemote;

@Stateless
@Local(IMaterialServiceLocal.class)
@Remote(IMaterialServiceRemote.class)
public class MaterialService extends BaseService implements IMaterialServiceLocal, IMaterialServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// Material

	public Material findMaterial(Long id) throws DoesNotExistException {
		Material material = (Material) find(Material.class, id);
		return material;
	}

	@SuppressWarnings("unchecked")
	public List<Material> findMaterials() {
		Query q = _em.createQuery("select m from Material m where m.rowInfo.recordStatus='A' order by m.id");
		List l = q.getResultList();
		return l;
	}

	public void updateMaterial(Material material) throws BusinessException {
		
		System.out.println("[MaterialService - updateMaterial]\n" +  material.getMaterialType().toString());
		material = (Material) merge(material);
	}

	public void addMaterial(Material material) throws BusinessException {	
		persist(material);
		
	}
	
	public void logicalDeleteMaterial(Material material) throws BusinessException {
		material.rowInfo.setRecordStatus("D");
		updateMaterial(material);
	}
	
	
	// Finders
	@SuppressWarnings("unchecked")
	public List<Material> findForSaleMaterials() {
		Query q = _em.createQuery("select m from Material m where m.rowInfo.recordStatus='A' AND m.materialType.rowInfo.recordStatus='A' AND " +
				"m.materialType.isForSale=TRUE");
		List l = q.getResultList();
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<Material> findProducedMaterials() {
		Query q = _em.createQuery("select m from Material m where m.rowInfo.recordStatus='A' AND m.materialType.rowInfo.recordStatus='A' AND " +
				"m.materialType.isProduced=TRUE");
		List l = q.getResultList();
		return l;
	}
	
	
}
