package com.quesofttech.business.domain.general.iface;

import java.util.List;

import com.quesofttech.business.domain.general.UOM;import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;


/**
 * The <code>IUOMServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IUomServiceRemote {

	// UOM

	UOM findUOM(Long id)throws DoesNotExistException;

	List<UOM> findUOMs() throws DoesNotExistException;

	void updateUOM(UOM uom) throws BusinessException;
	
	void addUOM(UOM uom) throws BusinessException;
	
	void logicalDeleteUOM(UOM uom) throws BusinessException;

}
