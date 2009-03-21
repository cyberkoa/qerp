package com.quesofttech.business.common.interpreter;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;

import com.quesofttech.business.common.exception.BusinessException;

public interface IEJBExceptionInterpreter {

	public BusinessException interpretOptimisticLockException(javax.persistence.OptimisticLockException e);
	public BusinessException interpretEntityExistsException(EntityExistsException e);
	public BusinessException interpretOtherPersistenceException(PersistenceException e);

}
