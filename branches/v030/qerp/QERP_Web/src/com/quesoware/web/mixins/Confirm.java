
package com.quesoware.web.mixins;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.annotations.Property;

import sun.security.action.GetLongAction;

import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.domain.inventory.MaterialType;
import com.quesoware.business.domain.inventory.iface.IMaterialTypeServiceRemote;
import com.quesoware.business.domain.security.Module;
import com.quesoware.business.domain.security.Program;
import com.quesoware.business.domain.security.Role;
import com.quesoware.business.domain.security.SecurityFinderService;
import com.quesoware.business.domain.security.User;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesoware.web.base.SimpleBasePage;
import com.quesoware.web.pages.Index;
import com.quesoware.web.state.Visit;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
@IncludeJavaScriptLibrary("confirm.js")
public class Confirm extends SimpleBasePage 
{
	@Parameter(value = "Are you sure?", defaultPrefix = BindingConstants.LITERAL)
    private String message;

    @Inject
    private RenderSupport renderSupport;

    @InjectContainer
    private ClientElement element;

    @AfterRender
    public void afterRender() {
    	if(message.contains("null"))
    	{

            renderSupport.addScript(String.format("new ConfirmNull('%s', '%s');",
                    element.getClientId(), "No record available for delete."));
    	}
    	else
    	{
    		renderSupport.addScript(String.format("new Confirm('%s', '%s');",
                    element.getClientId(), message));
    	}
    }


	

	
	
}