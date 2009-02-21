package com.quesofttech.business.domain.general;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.base.BaseService;
import com.quesofttech.business.domain.general.DocumentType;
import com.quesofttech.business.domain.general.iface.IDocumentTypeServiceLocal;
import com.quesofttech.business.domain.general.iface.IDocumentTypeServiceRemote;

@Stateless
@Local(IDocumentTypeServiceLocal.class)
@Remote(IDocumentTypeServiceRemote.class)
public class DocumentTypeService extends BaseService implements IDocumentTypeServiceLocal, IDocumentTypeServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// DocumentType

	public DocumentType findDocumentType(Long id) throws DoesNotExistException {
		DocumentType documentType = (DocumentType) find(DocumentType.class, id);
		return documentType;
	}

	@SuppressWarnings("unchecked")
	public List<DocumentType> findDocumentTypes() throws DoesNotExistException {
		Query q = _em.createQuery("select documentType from DocumentType documentType where documentType.rowInfo.recordStatus='A' order by documentType.id");
		List l = q.getResultList();
		return l;
	}
/*
	public DocumentType findDocumentTypeByType(String type) {
		DocumentType documentType = _em.find(DocumentType.class, id);
		return documentType;
	}
*/
	
	
	public void updateDocumentType(DocumentType documentType) throws BusinessException {		
		documentType = (DocumentType) merge(documentType);
	}

	public void logicalDeleteDocumentType(DocumentType documentType) throws BusinessException {
		documentType.setRecordStatus("D");
		updateDocumentType(documentType);
	}
	
	public void addDocumentType(DocumentType documentType) throws BusinessException {
		
		//try{
		System.out.println("just before persist in DocumentTypeService");
		persist(documentType);
		
		System.out.println("just after persist in DocumentTypeService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[DocumentTypeService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addDocumentType] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
}
