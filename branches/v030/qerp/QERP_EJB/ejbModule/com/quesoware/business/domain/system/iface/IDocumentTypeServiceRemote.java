package com.quesoware.business.domain.system.iface;

import java.util.List;

import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.domain.system.DocumentType;


/**
 * The <code>IDocumentTypeServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IDocumentTypeServiceRemote {

	// DocumentType

	DocumentType findDocumentType(Long id)throws DoesNotExistException;

	List<DocumentType> findDocumentTypes() throws DoesNotExistException;

	void updateDocumentType(DocumentType documentType) throws BusinessException;
	
	void addDocumentType(DocumentType documentType) throws BusinessException;
	
	void logicalDeleteDocumentType(DocumentType documentType) throws BusinessException;

	DocumentType getNextNumberByType(String type) throws BusinessException;
}
