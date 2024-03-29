package com.quesofttech.business.common.exception;

import java.util.Date;

import com.quesofttech.util.MessageUtil;

@SuppressWarnings("serial")
public class UnexpectedException extends SystemException {
	private String _referenceCode;
	private Throwable _rootCause;

	/**
	 * This exception is thrown by an IEJBExceptionInterpreter when it cannot interpret the exception it has been asked
	 * to interpret. Ideally, this will never occur, but if it does occur then the cause should be identified and the
	 * IEJBExceptionInterpreter modified to cope in future.
	 */
	public UnexpectedException(Throwable throwable) {
		this(throwable, null);
	}

	/**
	 * This exception is thrown by an IEJBExceptionInterpreter when it cannot interpret the exception it has been asked
	 * to interpret. Ideally, this will never occur, but if it does occur then the cause should be identified and the
	 * IEJBExceptionInterpreter modified to cope in future.
	 */
	public UnexpectedException(Throwable throwable, Throwable rootCause) {
		super(throwable);
		_referenceCode = (new Date()).toString();
		_rootCause = rootCause;
	}

	public String getReferenceCode() {
		return _referenceCode;
	}

	public Throwable getRootCause() {
		return _rootCause;
	}

	@Override
	public String getMessage() {
		String msg = MessageUtil.toText("UnexpectedException", new Object[] { _referenceCode });
		return msg;
	}
}
