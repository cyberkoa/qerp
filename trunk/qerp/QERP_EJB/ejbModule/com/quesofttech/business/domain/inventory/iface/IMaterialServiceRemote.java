package com.quesofttech.business.domain.inventory.iface;

import java.util.List;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.query.SearchOptions;
import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.inventory.MaterialType;
import com.quesofttech.business.domain.inventory.dto.MaterialSearchFields;


/**
 * The <code>IMaterialServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IMaterialServiceRemote {

	// Material

	Material findMaterial(Long id) throws DoesNotExistException;

	List<Material> findMaterials() throws DoesNotExistException;

	void updateMaterial(Material material) throws BusinessException;
	void addMaterial(Material material) throws BusinessException;
	void logicalDeleteMaterial(Material material) throws BusinessException;
	
	
	// Finders
	List<Material> findForSaleMaterials() throws DoesNotExistException;
	List<Material> findProducedMaterials() throws DoesNotExistException;
	List<Material> findMaterialsExceptFinishGood() throws DoesNotExistException;
	public List<Material> findMaterialBySearchFieldsRange(MaterialSearchFields lower,MaterialSearchFields upper,SearchOptions options) throws DoesNotExistException;
	public List<Material> findMaterialBySearchFields(MaterialSearchFields search,SearchOptions options)	throws DoesNotExistException;
	
	
}
