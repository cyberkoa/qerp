package com.quesofttech.business.domain.general.iface;

import java.util.List;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.general.BOM;import com.quesofttech.business.domain.general.BomDetail;

/**
 * The <code>IBOMServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IBOMServiceRemote {

	// BOM

	BOM findBOM(Long id)throws DoesNotExistException;

	List<BOM> findBOMs() throws DoesNotExistException;

	void updateBOM(BOM bom) throws BusinessException;
	
	void addBOM(BOM bom) throws BusinessException;
	
	void logicalDeleteBOM(BOM bom) throws BusinessException;
	
	
	// BomDetail

	BomDetail findBomDetail(Long id)throws DoesNotExistException;

	List<BomDetail> findBomDetailsByBomID(Long id) throws DoesNotExistException;

	void updateBomDetail(BomDetail bomDetail) throws BusinessException;
	
	void addBomDetail(Long _headerid,BomDetail bomDetail) throws BusinessException;
	
	void logicalDeleteBomDetail(BomDetail bomDetail) throws BusinessException;
	
	
	
	// finders
	List<BomDetail> findBomDetailsByBomId(Long bomId) throws DoesNotExistException;
	
	List<BomDetail> findBomDetailsByBom(BOM bom) throws DoesNotExistException;
	

}
