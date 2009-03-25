package com.quesoware.business.domain.inventory.iface;

import java.util.List;

import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.domain.inventory.MaterialGroup;


/**
 * The <code>IMaterialGroupServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IMaterialGroupServiceRemote {

	// MaterialGroup

	MaterialGroup findMaterialGroup(Long id)throws DoesNotExistException;

	List<MaterialGroup> findMaterialGroups() throws DoesNotExistException;

	void updateMaterialGroup(MaterialGroup materialGroup) throws BusinessException;
	
	void addMaterialGroup(MaterialGroup materialGroup) throws BusinessException;
	
	void logicalDeleteMaterialGroup(MaterialGroup materialGroup) throws BusinessException;

}
