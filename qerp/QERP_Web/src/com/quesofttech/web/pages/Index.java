package com.quesofttech.web.pages;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.security.User;
import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceLocal;
import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.state.Visit;
import com.quesofttech.web.pages.Main;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
public class Index extends SimpleBasePage {
	
	private String _loginId;
	
	private String _password;

	@Component(id = "login")
	private Form _form;

	@Component(id = "loginId")
	private TextField _loginIdField;
	
	@Inject
	private Logger _logger;
	
	void onActivate(String loginId) {
		_loginId = loginId;
	}
	// when go to the login page. it will base on previous section to direct login 
	// to the main page.
	Object onActivate(){
		if(getVisit().isLoggedIn())
			return Main.class;
		else 
			return null;
	}
	
	String onPassivate() {
		return _loginId;
	}

	void onValidateForm() {
		try {

			// Authenticate the user, userPassword
			User user = getSecurityFinderService().authenticateUser(_loginId, _password);
			/*if (user==null)
			{
				_lng_gen_username = 0L;				
			}
			else {
				_lng_gen_username = user.getId();
			}*/
			// Store the user and roles in the Visit
			Visit visit = getVisit();
			visit.noteLogIn(user);
			_logger.info(user.getLogin() + " has logged in.");
		}
		catch (BusinessException e) {
			_form.recordError(_loginIdField, e.getLocalizedMessage());
		}
		catch (Exception e) {
			_logger.info("Could not log in.  Stack trace follows...");
			e.printStackTrace();
			_form.recordError(getMessages().get("login_problem"));
		}
	}

	Object onSuccess() {
		return Main.class;
	}

	private ISecurityFinderServiceRemote getSecurityFinderService() {
		return getBusinessServicesLocator().getSecurityFinderSvcRemote();
	}

	public String getLoginId() {
		return _loginId;
		
	}

	public void setLoginId(String loginId) {
		_loginId = loginId;
	}

	public String getPassword() {
		return _password;
	}

	public void setPassword(String password) {
		_password = password;
	}
	
}