package com.quesoware.web.base;

import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;

import java.util.List;
import org.apache.tapestry5.annotations.InjectPage;
import com.quesoware.business.domain.security.Program;
import com.quesoware.web.pages.Index;
import com.quesoware.web.pages.Main;
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
		System.out.println("program list: going to check");
		if (!isVisitExists() || !getVisit().isLoggedIn()) {
			return _index;
		}
		else
		{
			System.out.println("here");
			List<Program> program = null;
			try
			{
				Boolean checkProgram=false;
				program = getISecurityFinderServiceRemote().findAuthorizedProgramsByUserId(getVisit().getMyUserId());
				for(Program p : program)
				{
					if(this.getClass().getSimpleName().toLowerCase().contains(p.getCode().toLowerCase()))
					{
						checkProgram = true;
					}					
				}
				System.out.println("checkProgram: " + checkProgram);
				if(!checkProgram)
				{
					return _frmQERP;
				}
			}
			catch (Exception e)
			
			{
				return _frmQERP;
				
			}
			finally
			{
				program = null;				
			}
			
		}
		//if ( getISecurityFinderServiceRemote().)

		return null;
	}
	
	
}
