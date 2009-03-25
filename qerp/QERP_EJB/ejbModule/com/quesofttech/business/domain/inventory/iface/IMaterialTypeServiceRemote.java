package com.quesofttech.business.domain.inventory.iface;

import java.util.List;

import com.quesofttech.business.domain.inventory.MaterialType;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;


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
