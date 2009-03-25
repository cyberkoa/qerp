package com.quesoware.business.domain.inventory.iface;

import java.util.List;

import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.common.query.SearchOptions;
import com.quesoware.business.domain.inventory.Material;
import com.quesoware.business.domain.inventory.MaterialType;
import com.quesoware.business.domain.inventory.dto.MaterialSearchFields;


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
	List<Material> findNotForSaleMaterials() throws DoesNotExistException;
	List<Material> findProducedMaterials() throws DoesNotExistException;
	//List<Material> findMaterialsExceptFinishGood() throws DoesNotExistException;
	public List<Material> findMaterialBySearchFieldsRange(MaterialSearchFields lower,MaterialSearchFields upper,SearchOptions options) throws DoesNotExistException;
	public List<Material> findMaterialBySearchFields(MaterialSearchFields search,SearchOptions options)	throws DoesNotExistException;
	
	
}
