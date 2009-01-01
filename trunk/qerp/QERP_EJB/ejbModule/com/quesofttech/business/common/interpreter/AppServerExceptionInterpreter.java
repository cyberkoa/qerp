package com.quesofttech.business.common.interpreter;

import java.io.Serializable;
import java.sql.BatchUpdateException;
import java.sql.SQLException;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;

//import org.apache.openjpa.persistence.PersistenceException;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.CannotDeleteIsReferencedException;
import com.quesofttech.business.common.exception.DuplicateAlternateKeyException;
import com.quesofttech.business.common.exception.DuplicatePrimaryKeyException;
import com.quesofttech.business.common.exception.OptimisticLockException;
import com.quesofttech.business.common.exception.UnexpectedException;

public class AppServerExceptionInterpreter implements IEJBExceptionInterpreter {

	static public final int SQL_ERRORCODE_HSQLDB_INTEGRITY_CONSTRAINT_VIOLATION = -8;
	static public final int SQL_ERRORCODE_HSQLDB_UNIQUE_CONSTRAINT_VIOLATION = -104;
	static public final int SQL_ERRORCODE_MYSQL_CANNOT_DELETE_OR_UPDATE_A_PARENT_ROW = 1451;
	static public final int SQL_ERRORCODE_MYSQL_CANNOT_DELETE_OR_UPDATE_A_CHILD_ROW = 1452;
	static public final int SQL_ERRORCODE_MYSQL_DUPLICATE_ENTRY = 1062;
	static public final int SQL_ERRORCODE_UNKNOWN = 0;

	public BusinessException interpretOptimisticLockException(javax.persistence.OptimisticLockException e) {
		BusinessException be = null;
		/*
		Throwable rootCause = org.hibernate.exception.ExceptionUtils.getRootCause(e);

		if (rootCause instanceof org.hibernate.StaleObjectStateException) {
			org.hibernate.StaleObjectStateException rootE = (org.hibernate.StaleObjectStateException) rootCause;
			be = new OptimisticLockException(rootE.getEntityName(), rootE.getIdentifier(), rootE);
		}
*/
		if (be == null) {
//			throw new UnexpectedException(e, rootCause);
		}

		return be;
	}

	public BusinessException interpretEntityExistsException(EntityExistsException e) {
		BusinessException be = null;

		Throwable cause = e.getCause();
		/*
		Throwable rootCause = org.hibernate.exception.ExceptionUtils.getRootCause(e);

		if (cause instanceof org.hibernate.exception.ConstraintViolationException) {
			be = interpretConstraintViolationException((org.hibernate.exception.ConstraintViolationException) cause);
		}
		else if (rootCause instanceof org.hibernate.NonUniqueObjectException) {
			org.hibernate.NonUniqueObjectException rootE = (org.hibernate.NonUniqueObjectException) rootCause;
			// String technicalMessage = rootE.getLocalizedMessage();
			// String entityName = rootE.getEntityName();
			Serializable identifier = rootE.getIdentifier();
			be = new DuplicatePrimaryKeyException(identifier);
		}
*/
		if (be == null) {
//			throw new UnexpectedException(rootCause);
		}

		return be;
	}

	public BusinessException interpretOtherPersistenceException(PersistenceException e) {
		BusinessException be = null;

		Throwable cause;
		
		//System.out.println("[AppServerException] root cause ? "  + cause.getClass());
		
		
		// OpenJPA UniqueConstrait exception handling
		
		// org.apache.openjpa.persistence.PersistenceException: The transaction has been rolled back.  See the nested exceptions for details on the errors that occurred.
		// Caused by: <openjpa-1.0.2-r420667:627158 nonfatal general error> org.apache.openjpa.persistence.PersistenceException: Duplicate entry 'R' for key 2 {prepstmnt 12283743 INSERT INTO MaterialType (id_MaterialType, matt_CreateApp, matt_CreateDate, matt_CreateLogin, matt_CreateTime, matt_Description, matt_IsForSale, matt_IsProduced, matt_IsPurchased, matt_ModifyApp, matt_ModifyDate, matt_ModifyLogin, matt_ModifyTime, matt_RecordStatus, matt_SessionId, matt_Type, matt_Version) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) [params=(long) 1050, (String) , (Date) 2008-06-03, (String) , (Time) 20:43:02, (String) afsadfsdf, (int) 0, (int) 0, (int) 1, (String) , (Date) 2008-06-03, (String) , (Time) 20:43:02, (String) A, (String) , (String) R, (int) 1]} [code=1062, state=23000]
		// Caused by: org.apache.openjpa.lib.jdbc.ReportingSQLException: Duplicate entry 'R' for key 2 {prepstmnt 12283743 INSERT INTO MaterialType (id_MaterialType, matt_CreateApp, matt_CreateDate, matt_CreateLogin, matt_CreateTime, matt_Description, matt_IsForSale, matt_IsProduced, matt_IsPurchased, matt_ModifyApp, matt_ModifyDate, matt_ModifyLogin, matt_ModifyTime, matt_RecordStatus, matt_SessionId, matt_Type, matt_Version) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) [params=(long) 1050, (String) , (Date) 2008-06-03, (String) , (Time) 20:43:02, (String) afsadfsdf, (int) 0, (int) 0, (int) 1, (String) , (Date) 2008-06-03, (String) , (Time) 20:43:02, (String) A, (String) , (String) R, (int) 1]} [code=1062, state=23000]

		if (e instanceof org.apache.openjpa.persistence.PersistenceException) {
			Throwable causes[] = ((org.apache.openjpa.persistence.PersistenceException) e).getNestedThrowables();
			cause = causes[causes.length -1];
			System.out.println("[AppServerException] In instanceof org.apache.persistence.PersistenceException , root cause :" + cause.getMessage()  );
			String rootMsg = cause.getMessage();
			String technicalMessage = cause.getLocalizedMessage();
			String[] chunks = rootMsg.split("\'");
			if (rootMsg.indexOf("for key 1") > -1) {
				Serializable identifier = chunks[1];
				be = new DuplicatePrimaryKeyException(identifier);
			}
			else {
				be = new DuplicateAlternateKeyException(technicalMessage);
			}
			
		}

		
		
		
		
		
				
		// Assume it's one of JBoss/Hibernate's deplorable deeply wrapped
		// exceptions - use its utility to find the root cause
/*
		Throwable rootCause = org.hibernate.exception.ExceptionUtils.getRootCause(e);

		if (rootCause instanceof BusinessException) {
			// A BusinessException - it was probably thrown by an Entity Lifecycle callback
			be = (BusinessException) rootCause;
		}
		else if (rootCause instanceof org.hibernate.PropertyValueException) {
			be = interpretPropertyValueException((org.hibernate.PropertyValueException) rootCause);
		}
		else if (rootCause instanceof BatchUpdateException) {
			SQLException rootE = (SQLException) rootCause;
			int errorCode = rootE.getErrorCode();

			if (errorCode == SQL_ERRORCODE_UNKNOWN) {
				// Have seen this in HSQLDB, and it was due to duplicate primary key!!
				be = new DuplicatePrimaryKeyException();
			}
		}
*/
		if (be == null) {
	//		throw new UnexpectedException(rootCause);
		}

		return be;
	}
/*
	private BusinessException interpretConstraintViolationException(
			org.hibernate.exception.ConstraintViolationException e) {
		BusinessException be = null;

		Throwable rootCause = org.hibernate.exception.ExceptionUtils.getRootCause(e);

		if (rootCause instanceof SQLException) {
			SQLException rootE = (SQLException) rootCause;
			String rootMsg = rootE.getMessage();
			int errorCode = rootE.getErrorCode();

			if (errorCode == SQL_ERRORCODE_MYSQL_DUPLICATE_ENTRY) {
				// Expect message starting with "Duplicate entry"
				String technicalMessage = rootE.getLocalizedMessage();
				String[] chunks = rootMsg.split("\'");
				if (rootMsg.indexOf("for key 1") > -1) {
					Serializable identifier = chunks[1];
					be = new DuplicatePrimaryKeyException(identifier);
				}
				else {
					be = new DuplicateAlternateKeyException(technicalMessage);
				}
			}
			else if (errorCode == SQL_ERRORCODE_HSQLDB_UNIQUE_CONSTRAINT_VIOLATION) {
				// This will be a message like this: 
				// Violation of unique constraint SYS_CT_63: duplicate value(s) for column $$ in statement [insert into ...
				if (rootE.getMessage().startsWith("Violation of unique constraint")) {
					String[] chunks = rootE.getMessage().split(" ");
					String constraintName = chunks[4];
					String technicalMessage = rootE.getLocalizedMessage();
					if (constraintName.startsWith("SYS_CT")) {
						be = new DuplicateAlternateKeyException(technicalMessage);
					}
				}
			}
			else if (errorCode == SQL_ERRORCODE_HSQLDB_INTEGRITY_CONSTRAINT_VIOLATION) {
				String[] chunks = rootE.getMessage().split(" ");
				if (chunks[4].equals("table:") && chunks[6].equals("in")) {
					String entityName = chunks[5];
					// String constraintName = chunks[3];
					be = new CannotDeleteIsReferencedException(entityName);
				}
			}
		}

		return be;
	}

	private BusinessException interpretPropertyValueException(org.hibernate.PropertyValueException e) {
		BusinessException be = null;

		String rootMsg = e.getMessage();
		// String technicalMessage = rootE.getLocalizedMessage();
		if (rootMsg.indexOf("not-null property references") > -1) {
			String[] qualifiedEntityNameParts = e.getEntityName().split("\\.");
			String referencedByEntityName = qualifiedEntityNameParts[qualifiedEntityNameParts.length - 1];
			String referencedByPropertyName = e.getPropertyName();
			be = new CannotDeleteIsReferencedException(referencedByEntityName, referencedByPropertyName);
		}

		return be;
	}
	*/
}
