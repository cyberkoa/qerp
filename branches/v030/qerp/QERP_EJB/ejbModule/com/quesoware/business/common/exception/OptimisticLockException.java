package com.quesoware.business.common.exception;

import java.io.Serializable;

import javax.ejb.ApplicationException;

import com.quesofttech.business.domain.base.BaseEntity;
import com.quesoware.util.ClassUtil;
import com.quesoware.util.MessageUtil;

@ApplicationException(rollback = true)
@SuppressWarnings("serial")
public class OptimisticLockException extends BusinessException {
	private String _entityLabelMessageId;
	private Serializable _id;
	private String _entityName;
	private Exception _exception;

	/**
	 * This exception is thrown by an IEJBExceptionInterpreter when an attempt to update an entity has failed because
	 * the entity has been updated by an intervening transaction since we read its state.
	 * 
	 * @param entityName
	 *            the name of the entity being updated. It will be stripped down to its unqualified name (eg.
	 *            "jumpstart.Code" would be stripped down to "Code") to be used as a message key when generating a
	 *            message in getMessage().
	 * @param id
	 *            the id of the entity.
	 * @param exception
	 *            the root cause exception, eg. a Hibernate StaleObjectException.
	 */
	public OptimisticLockException(String entityName, Serializable id, Exception exception) {

		// Don't convert the message ids to messages yet because we're in the server's locale, not the user's.

		super();
		_entityLabelMessageId = ClassUtil.extractUnqualifiedName(entityName);
		_id = id;
		_entityName = entityName;
		_exception = exception;
	}

	/**
	 * This exception is thrown by an IEJBExceptionInterpreter when an attempt to update an entity has failed because
	 * the entity has been updated by an intervening transaction since we read its state.
	 * 
	 * @param entityName
	 *            the entity being updated. It will be stripped down to its unqualified name (eg. jumpstart.Code
	 *            would be stripped down to Code) to be used as a message key when generating a message in getMessage().
	 * @param id
	 *            the id of the entity.
	 * @param exception
	 *            the root cause exception, eg. a Hibernate StaleObjectException.
	 */
	public OptimisticLockException(BaseEntity entity, Serializable id, Exception exception) {

		// Don't convert the message ids to messages yet because we're in the server's locale, not the user's.

		super();
		_entityLabelMessageId = ClassUtil.extractUnqualifiedName(entity);
		_id = id;
		_entityName = entity.getClass().getName();
		_exception = exception;
	}

	@Override
	public String getMessage() {

		// We deferred converting the message ids to messages until now, when we are more likely to be in the user's
		// locale.

		Object[] msgArgs = new Object[] { MessageUtil.toText(_entityLabelMessageId), _id,
				_exception.getLocalizedMessage() };

		String msg = MessageUtil.toText("OptimisticLockException", msgArgs);
		return msg;
	}

	public String getEntityName() {
		return _entityName;
	}

	public String getEntityLabelMessageId() {
		return _entityLabelMessageId;
	}

	public Exception getException() {
		return _exception;
	}

	public Serializable getId() {
		return _id;
	}
}
