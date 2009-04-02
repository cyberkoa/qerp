package com.quesofttech.web.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.quesofttech.web.services.IBusinessServicesLocator;
import com.quesofttech.web.state.Visit;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.CannotDeleteIsReferencedException;
import com.quesoware.business.common.exception.DuplicateAlternateKeyException;
import com.quesoware.business.common.exception.DuplicatePrimaryKeyException;
import com.quesoware.business.common.exception.OptimisticLockException;
import com.quesoware.business.common.interpreter.ServicesExceptionInterpreter;

import org.apache.tapestry5.annotations.ApplicationState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class SimpleBasePage {

	@ApplicationState
	private String _gen_username;
	public String getGenUserName()
	{
		return _gen_username;
	}
	@ApplicationState
	private Visit _visit;
	private boolean _visitExists;
	
	// businessServicesLocator locates our business services (which are EJB3 session
	// beans). It is a global singleton registered in AppModule.
	@Inject
	private IBusinessServicesLocator _businessServicesLocator;

	// Formatters
	// If you want to make these static or move them into Visit, first read
	// http://thread.gmane.org/gmane.comp.java.tapestry.user/20925
	private DateFormat _dateViewFormat = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat _dateListFormat = new SimpleDateFormat("dd/MM/yyyy");

	private ServicesExceptionInterpreter _servicesExceptionInterpreter = new ServicesExceptionInterpreter();

	@Inject
	private Messages _messages;

	protected Messages getMessages() {
		return _messages;
	}

	public DateFormat getDateViewFormat() {
		return _dateViewFormat;
	}

	public DateFormat getDateListFormat() {
		return _dateListFormat;
	}

	/**
	 * @see http://www.dynarch.com/demos/jscalendar/doc/html/reference.html#node_sec_5.3.5
	 */
	public String getDateFieldFormat() {
		return "%d/%m/%Y";
	}

	protected String interpretBusinessServicesExceptionForCreate(Exception e) {
		String message = "";
		BusinessException x = _servicesExceptionInterpreter.interpretAsBusinessException(e);

		if (x instanceof DuplicatePrimaryKeyException) {
			message = getMessages().get("create_failed_duplicate_primary_key");
		}
		else if (x instanceof DuplicateAlternateKeyException) {
			DuplicateAlternateKeyException d = (DuplicateAlternateKeyException) x;
			message = getMessages().format("create_failed_duplicate_alternate_key", d.getTechnicalMessageText());
		}
		else {
			message = x.getMessage();
		}
		return message;
	}

	protected String interpretBusinessServicesExceptionForAdd(Exception e) {
		String message = "";
		BusinessException x = _servicesExceptionInterpreter.interpretAsBusinessException(e);

		if (x instanceof OptimisticLockException) {
			message = getMessages().get("add_failed_optimistic_lock");
		}
		else if (x instanceof DuplicatePrimaryKeyException) {
			message = getMessages().get("add_failed_duplicate_primary_key");
		}
		else if (x instanceof DuplicateAlternateKeyException) {
			DuplicateAlternateKeyException d = (DuplicateAlternateKeyException) x;
			message = getMessages().format("add_failed_duplicate_alternate_key", d.getTechnicalMessageText());
		}
		else {
			message = x.getMessage();
		}
		return message;
	}
	
	protected String interpretBusinessServicesExceptionForChange(Exception e) {
		String message = "";
		BusinessException x = _servicesExceptionInterpreter.interpretAsBusinessException(e);

		if (x instanceof OptimisticLockException) {
			message = getMessages().get("change_failed_optimistic_lock");
		}
		else if (x instanceof DuplicateAlternateKeyException) {
			DuplicateAlternateKeyException d = (DuplicateAlternateKeyException) x;
			message = getMessages().format("change_failed_duplicate_alternate_key", d.getTechnicalMessageText());
		}
		else {
			message = x.getMessage();
		}
		return message;
	}
	
	protected String interpretBusinessServicesExceptionForRemove(Exception e) {
		String message = "";
		BusinessException x = _servicesExceptionInterpreter.interpretAsBusinessException(e);

		if (x instanceof OptimisticLockException) {
			message = getMessages().get("remove_failed_optimistic_lock");
		}
		else if (x instanceof CannotDeleteIsReferencedException) {
			CannotDeleteIsReferencedException c = (CannotDeleteIsReferencedException) x;
			message = getMessages().format("remove_failed_is_referenced",
					new Object[] { c.getReferencedByEntityName() });
		}
		else {
			message = x.getMessage();
		}
		return message;
	}
	
	protected String interpretBusinessServicesExceptionForDelete(Exception e) {
		String message = "";
		BusinessException x = _servicesExceptionInterpreter.interpretAsBusinessException(e);

		if (x instanceof OptimisticLockException) {
			message = getMessages().get("delete_failed_optimistic_lock");
		}
		else if (x instanceof CannotDeleteIsReferencedException) {
			CannotDeleteIsReferencedException c = (CannotDeleteIsReferencedException) x;
			message = getMessages().format("delete_failed_is_referenced",
					new Object[] { c.getReferencedByEntityName() });
		}
		else {
			message = x.getMessage();
		}
		return message;
	}

	protected IBusinessServicesLocator getBusinessServicesLocator() {
		return _businessServicesLocator;
	}

	public Visit getVisit() {
		return _visit;
	}

	public boolean isVisitExists() {
		return _visitExists;
	}

}
