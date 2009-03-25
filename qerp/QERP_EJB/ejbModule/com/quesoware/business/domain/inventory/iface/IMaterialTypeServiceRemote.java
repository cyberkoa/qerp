package com.quesoware.business.domain.inventory.iface;

import java.util.List;

import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.domain.inventory.MaterialType;


/**
 * The <code>IMaterialTypeServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IMaterialTypeServiceRemote {

	// Material

	MaterialType findMaterialType(Long id)throws DoesNotExistException;

	List<MaterialType> findMaterialTypes() throws DoesNotExistException;

	void updateMaterialType(MaterialType materialType) throws BusinessException;
	
	void addMaterialType(MaterialType materialType) throws BusinessException;
	
	void logicalDeleteMaterialType(MaterialType materialType) throws BusinessException;

}
