package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.sql.Timestamp;
import javax.annotation.Resource;
import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.base.SecureBasePage;
import com.quesofttech.web.state.Visit;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.domain.general.*;
import com.quesoware.business.domain.general.iface.*;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.annotations.*;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.corelib.components.DateField;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;

import com.quesofttech.web.components.*;


public class Test {
	//@Parameter(defaultPrefix="literal", required=true)
    //private String _pages;

	
	
    @Inject
    private ComponentResources _resources;

    private StandardToolbar toolbar;

    private Block blockFormView;
    
    private int testvalue;



	public int getTestvalue()
	{
		return testvalue;
	}



	public void setTestvalue(int testvalue)
	{
		this.testvalue = testvalue;
	}
    
	@OnEvent(value = "addRecord", component = "toolbar")
    Object onAddRecord()
    {
		System.out.println("triggered addRecord");
    	return blockFormView;
    }
    
    
}
