package com.quesofttech.web.state;

import java.io.Serializable;

import org.apache.tapestry5.annotations.ApplicationState;

import com.quesoware.business.domain.security.User;

@SuppressWarnings("serial")
public class Visit implements Serializable {
	
	// The logged in user
	private boolean _loggedIn = false;
	private Long _myUserId = null;
	private String _myLoginId = null;
	
	public void noteLogIn(User user) {
		_loggedIn = true;
		_myUserId = user.getId();
		_myLoginId = user.getLogin();
	}

	public void noteLogOut() {
		_loggedIn = false;
		_myUserId = null;
		_myLoginId = null;
	}

	public boolean isLoggedIn() {
		return _loggedIn;
	}
	public void setLoggedIn(boolean bln_input)
	{
		
		_loggedIn = bln_input;
	}

	public Long getMyUserId() {
		return _myUserId;
	}

	public String getMyLoginId() {
		return _myLoginId;
	}

}