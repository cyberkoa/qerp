package com.quesofttech.business.common.interpreter;
import java.io.*;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.UnexpectedException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServicesExceptionInterpreter {
	
	static private final Log LOG = LogFactory.getLog(ServicesExceptionInterpreter.class);
/*
	FileWriter outFile;
	BufferedWriter outBuffer;
	String file;
	*/
	//private IEJBExceptionInterpreter _ejbExceptionInterpreter = new JBossExceptionInterpreter();

	private IEJBExceptionInterpreter _ejbExceptionInterpreter = new AppServerExceptionInterpreter();
	
	public BusinessException interpretAsBusinessException(Throwable t) {
		
		System.out.println("t is : " + t.getClass().toString());
		
		if(t.getCause()!=null)
		 System.out.println("Cause is " + t.getCause().getMessage());
		
		
		try {
			BusinessException be = null;
			
			if (t instanceof BusinessException) {
				be = (BusinessException) t;
			}
			else if (t instanceof javax.persistence.OptimisticLockException) {
								
				be = _ejbExceptionInterpreter
						.interpretOptimisticLockException((javax.persistence.OptimisticLockException) t);
			}
			else if (t instanceof javax.persistence.EntityExistsException) {
				
				be = _ejbExceptionInterpreter
						.interpretEntityExistsException((javax.persistence.EntityExistsException) t);
			}
			else if (t instanceof javax.persistence.PersistenceException) {
				
				be = _ejbExceptionInterpreter
						.interpretOtherPersistenceException((javax.persistence.PersistenceException) t);
			}
			else if (t.getCause() != null && !t.getCause().equals(t)) {
				be = interpretAsBusinessException(t.getCause());
			}
			else {				
				throw new UnexpectedException(t);
			}

			return be;
		}
		catch (UnexpectedException e) {
		
			LOG.error("Unexpected exception.  Root cause follows...");
			LOG.error(e.getRootCause());
			throw e;
		}
	}

}
