package com.quesoware.business.common.exception;

import com.quesoware.business.domain.base.BaseEntity;
import com.quesoware.util.ClassUtil;
import com.quesoware.util.MessageUtil;

@SuppressWarnings("serial")
public class DuplicateAlternateKeyException extends BusinessException {
	static public final int INFORMATIONLEVEL_ENTITY_TECHMSG = 1;
	static public final int INFORMATIONLEVEL_TECHMSG = 2;

	private int _informationLevel = INFORMATIONLEVEL_ENTITY_TECHMSG;
	private String _entityLabelMessageId;
	private String _technicalMessageText;

	/**
	 * This exception is thrown by an IEJBExceptionInterpreter when an attempt to create an entity has failed because
	 * the entity specifies an alternate key (a unique key other than the primary key) that is already in use.
	 * 
	 * @param entity	the entity being created.
	 * @param technicalMessageText	typically this is a constraint violation message from the database.
	 */
	public DuplicateAlternateKeyException(BaseEntity entity, String technicalMessageText) {

		// Don't convert the message ids to messages yet because we're in the
		// server's locale, not the user's.

		super();
		_informationLevel = INFORMATIONLEVEL_ENTITY_TECHMSG;
		_entityLabelMessageId = ClassUtil.extractUnqualifiedName(entity);
		_technicalMessageText = technicalMessageText;
	}

	/**
	 * This exception is thrown by an IEJBExceptionInterpreter when an attempt to create an entity has failed because
	 * the entity specifies an alternate key (a unique key other than the primary key) that is already in use.
	 * 
	 * @param technicalMessageText	typically this is a constraint violation message from the database.
	 */
	public DuplicateAlternateKeyException(String technicalMessageText) {

		// Don't convert the message ids to messages yet because we're in the
		// server's locale, not the user's.

		super();
		_informationLevel = INFORMATIONLEVEL_TECHMSG;
		_technicalMessageText = technicalMessageText;
	}

	@Override
	public String getMessage() {
		String msg;
		Object[] msgArgs;

		// We deferred converting the message ids to messages until now, when we
		// are more likely to be in the user's locale.

		if (_informationLevel == INFORMATIONLEVEL_ENTITY_TECHMSG) {
			msgArgs = new Object[] { MessageUtil.toText(_entityLabelMessageId), _technicalMessageText };
			msg = MessageUtil.toText("DuplicateAlternateKeyException", msgArgs);
		}
		else if (_informationLevel == INFORMATIONLEVEL_TECHMSG) {
			msgArgs = new Object[] { _technicalMessageText };
			msg = MessageUtil.toText("DuplicateAlternateKeyException_2", msgArgs);
		}
		else {
			throw new IllegalStateException("_informationLevel = " + _informationLevel);
		}

		return msg;
	}

	public String getEntityLabelMessageId() {
		return _entityLabelMessageId;
	}

	public String getTechnicalMessageText() {
		return _technicalMessageText;
	}

	public int getInformationLevel() {
		return _informationLevel;
	}
}
