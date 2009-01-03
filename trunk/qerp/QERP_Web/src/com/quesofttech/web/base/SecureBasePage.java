package com.quesofttech.web.base;

import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesofttech.web.pages.Index;
import com.quesofttech.web.pages.Main;

import org.apache.tapestry5.annotations.InjectPage;

/**
 * Base page for pages that must not be accessible if the user is not logged in.
 */
public class SecureBasePage extends SimpleBasePage {
	
	@InjectPage
	private Index _index;
	
	@InjectPage
	private Main _frmQERP;
	
	
	
	/**
	 * Validate that the user is logged in.  If not logged in, then redirects to the login page.
	 */
	  
    private ISecurityFinderServiceRemote getISecurityFinderServiceRemote()
    {
    	return getBusinessServicesLocator().getSecurityFinderSvcRemote();
    }

	
	Object onActivate() {

		//if (!isVisitExists() || !getVisit().isLoggedIn()) {
		//	return _index;
		//}
		//if ( getISecurityFinderServiceRemote().)

		return null;
	}
	
	
}
