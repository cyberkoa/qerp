package com.quesofttech.business.domain.system.iface;

import java.util.List;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.system.DocumentType;


/**
 * The <code>IDocumentTypeServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IDocumentTypeServiceRemote {

	// DocumentType

	DocumentType findDocumentType(Long id)throws DoesNotExistException;
	DocumentType findDocumentTypeByType(String type) throws DoesNotExistException;

	List<DocumentType> findDocumentTypes() throws DoesNotExistException;

	void updateDocumentType(DocumentType documentType) throws BusinessException;
	
	void addDocumentType(DocumentType documentType) throws BusinessException;
	
	void logicalDeleteDocumentType(DocumentType documentType) throws BusinessException;

	DocumentType getNextNumberByType(String type) throws BusinessException;
}
