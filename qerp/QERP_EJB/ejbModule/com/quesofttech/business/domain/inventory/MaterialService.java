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
import com.quesofttech.business.common.query.QueryBuilder;
import com.quesofttech.business.common.query.SearchOptions;
import com.quesofttech.business.domain.base.BaseService;
import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.inventory.dto.*;
import com.quesofttech.business.domain.inventory.iface.IMaterialServiceLocal;
import com.quesofttech.business.domain.inventory.iface.IMaterialServiceRemote;
import com.quesofttech.business.domain.sales.SalesOrderMaterial;
import com.quesofttech.business.domain.sales.dto.SalesOrderMaterialSearchFields;

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
	
	/*
	@SuppressWarnings("unchecked")
	public List<Material> findMaterialsExceptFinishGood() {
		Query q = _em.createQuery("select m from Material m where m.rowInfo.recordStatus='A' and m.materialtype.isForSale='1' order by m.id");
		List l = q.getResultList();
		return l;
	}
*/
	public void updateMaterial(Material material) throws BusinessException {
		
		System.out.println("[MaterialService - updateMaterial]\n" +  material.getMaterialType().toString());
		material = (Material) merge(material);
	}
	

	public void addMaterial(Material material) throws BusinessException {	
		persist(material);
		
	}
	
	public void logicalDeleteMaterial(Material material) throws BusinessException {
		material.setRecordStatus("D");
		updateMaterial(material);
	}
	
	
	// Finders
	@SuppressWarnings("unchecked")
	public List<Material> findForSaleMaterials() {
		Query q = _em.createQuery("select m from Material m where m.rowInfo.recordStatus='A' AND m.materialType.rowInfo.recordStatus='A' AND " +
				"m.materialType.isForSale=true");
		List l = q.getResultList();
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<Material> findNotForSaleMaterials() {
		Query q = _em.createQuery("select m from Material m where m.rowInfo.recordStatus='A' AND m.materialType.rowInfo.recordStatus='A' AND " +
				"m.materialType.isForSale<>true");
		List l = q.getResultList();
		return l;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Material> findProducedMaterials() {
		Query q = _em.createQuery("select m from Material m where m.rowInfo.recordStatus='A' AND m.materialType.rowInfo.recordStatus='A' AND " +
				"m.materialType.isProduced=true");
		List l = q.getResultList();
		return l;
	}
	
	public boolean isProduced(Material material)
	{
		return material.getMaterialType().isProduced();
	}
	
	
	
	
	
	
	public List<Material> findMaterialBySearchFieldsRange(MaterialSearchFields lower,MaterialSearchFields upper,SearchOptions options)
	throws DoesNotExistException
	{

		QueryBuilder builder = new QueryBuilder();
		
		builder.append("select so from Material so");
		builder.appendBetween("so.rowInfo.recordStatus", lower.getRecordStatus(),upper.getRecordStatus(),true);
		builder.appendBetween("so.code", lower.getCode(),upper.getCode(),true);
		builder.appendBetween("so.description", lower.getDescription(),upper.getDescription(),true);
		builder.appendBetween("so.materialtype.type", lower.getType(),upper.getType(),true);
		
		Query q = builder.createQuery(_em, options, "so");
	    System.out.println(builder.getQueryString());
		List l = q.getResultList();
	
		return l;

	
	}
	
	public List<Material> findMaterialBySearchFields(MaterialSearchFields search,SearchOptions options) 
	throws DoesNotExistException
	{		
		QueryBuilder builder = new QueryBuilder();
		
		if (options!=null && options.getSortColumnNames().size() > 0) 
		{}
		else{
			builder.append(" order by u.id");
		}
		Query q = builder.createQuery(_em, options, "u");	
		List l = q.getResultList();	
		return l;
			
	}
}