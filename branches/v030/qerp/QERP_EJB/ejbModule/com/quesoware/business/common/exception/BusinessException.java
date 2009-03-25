package com.quesoware.business.common.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
abstract public class BusinessException extends Exception {

}
