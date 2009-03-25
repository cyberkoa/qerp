package com.quesofttech.business.domain.base;

//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.CannotDeleteIsReferencedException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.common.exception.DuplicateAlternateKeyException;
import com.quesoware.business.common.exception.DuplicatePrimaryKeyException;
import com.quesoware.business.common.interpreter.ServicesExceptionInterpreter;

public abstract class BaseService {

	@PersistenceContext(unitName = "QERP_EJB")
	protected EntityManager _em;

	protected ServicesExceptionInterpreter _servicesExceptionInterpreter = new ServicesExceptionInterpreter();

/*
	FileWriter outFile;
	BufferedWriter outBuffer;
	String file;
	*/
	
	protected Object find(Class<?> cls, Serializable id) throws DoesNotExistException {

		if (id == null) {
			throw new IllegalArgumentException("find(class, id) has been given null id.  Class is " + cls.getName()
					+ ".");
		}
		else if (id.equals(0)) {
			throw new IllegalArgumentException("find(class, id) has been given zero id.  Class is " + cls.getName()
					+ ".");
		}

		try {
			Object obj = _em.find(cls, id);

			if (obj == null) {
				throw new DoesNotExistException(cls, id);
			}

			return obj;
		}
		catch (IllegalArgumentException e) {
			// Invalid id
			throw new IllegalArgumentException("find(class, id) has been given invalid id.  Class is " + cls.getName()
					+ ", id is \"" + id + "\".", e);
		}
		catch (Exception e) {
			// Doesn't exist
			throw new DoesNotExistException(cls, id);
		}
	}

	public void persist(BaseEntity entity) throws BusinessException,RuntimeException {
		System.out.println("just in Base");
		if (entity == null) {					
			throw new IllegalArgumentException("persist(entity) has been given a null entity.");
		}
		try {
			System.out.println("just before persist in Base");
			
			//validate(entity);
			//prePersist(entity);
			
			_em.persist(entity);
			
			// [OpenJPA] An explicit flush is needed as no exception will be thrown immediately when unique constraint hit at databases level
			// reference link : 
			//  1) http://mail-archives.apache.org/mod_mbox/openjpa-users/200712.mbox/%3C7EAD3D99-0B63-42F5-A962-88850748D48C@SUN.com%3E
			//  2) http://www.mail-archive.com/dev@geronimo.apache.org/msg58087.html
			
			System.out.println("just before flush in Base");
			_em.flush();
			System.out.println("just after flush in Base");
		}
		/*catch (java.lang.RuntimeException e)
		{
			System.out.println("RuntimeException caught in persist");
			throw e;
		}*/
		catch (Exception e) {
			System.out.println("[persist] Exception caught in persist");
			
			interpretAsBusinessExceptionAndThrowIt(e, entity, entity.getIdForMessages());
			throw new IllegalStateException("Shouldn't get here.");
		}

	}

	public BaseEntity merge(BaseEntity entity) throws BusinessException {
		if (entity == null) {
			throw new IllegalArgumentException("merge(entity) has been given a null entity.");
		}
		try {
			
			//validate(entity);
			//preUpdate(entity);
			entity = _em.merge(entity);
		}
		catch (Exception e) {
			interpretAsBusinessExceptionAndThrowIt(e, entity, entity.getIdForMessages());
			throw new IllegalStateException("Shouldn't get here.");
		}
		return entity;
	}

	public void remove(BaseEntity entity) throws BusinessException {
		if (entity == null) {
			throw new IllegalArgumentException("remove(entity) has been given a null entity.");
		}
		try {
			_em.remove(entity);
		}
		catch (Exception e) {
			interpretAsBusinessExceptionAndThrowIt(e, entity, entity.getIdForMessages());
			throw new IllegalStateException("Shouldn't get here.");
		}
	}

	protected void interpretAsBusinessExceptionAndThrowIt(Throwable t, BaseEntity entity, Serializable id)
			throws BusinessException {

		BusinessException be = _servicesExceptionInterpreter.interpretAsBusinessException(t);

		// "Repackage" certain exceptions that we more info about

		if (be instanceof DuplicatePrimaryKeyException) {
			System.out.println("DuplicatePrimaryKeyException");
			be = new DuplicatePrimaryKeyException(entity, id);
		}
		else if (be instanceof DuplicateAlternateKeyException) {
			System.out.println("DuplicateAlternateKeyException");
			be = new DuplicateAlternateKeyException(entity, be.getMessage());
		}
		else if (be instanceof CannotDeleteIsReferencedException) {
			System.out.println("CannotDeleteIsReferencedException");
			CannotDeleteIsReferencedException c = (CannotDeleteIsReferencedException) be;
			be = new CannotDeleteIsReferencedException(entity, id, c.getReferencedByEntityName(), c
					.getReferencedByPropertyName());
		}

		throw be;

	}

	/*
	protected Boolean validate(BaseEntity baseEntity)
	{
		return true;
	}

	protected Boolean prePersist(BaseEntity baseEntity)
	{
		
		baseEntity.setRecordStatus("A");
		
	    java.util.Date today = new java.util.Date();

	    baseEntity.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	    baseEntity.setCreateTimestamp(baseEntity.getModifyTimestamp());
	    
		return true;
	}	
	
	protected Boolean preUpdate(BaseEntity baseEntity)
	{
				
	    java.util.Date today = new java.util.Date();

	    baseEntity.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	    
		return true;
	}
	*/
	
}
