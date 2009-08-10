package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.Collection;
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


public class Test extends SimpleBasePage {
	//@Parameter(defaultPrefix="literal", required=true)
    //private String _pages;

	
	
    @Inject
    private ComponentResources _resources;

    private StandardToolbar toolbar;

    @Inject
    private Block blockFormView;

    @Inject
    private Block blockGridView;
    
    private Zone zoneFormView;

    private Zone zoneGridView;
    
    private String testvalue;

    private Operation _operation;
    private Collection<?> _operations;


    
	@OnEvent(value = "addRecord", component = "toolbar")
    Object onAddRecord()
    {
		System.out.println("triggered addRecord");
    	return blockFormView;
    }



	public String getTestvalue()
	{
		return testvalue;
	}



	public void setTestvalue(String testvalue)
	{
		this.testvalue = testvalue;
	}



	public Block getBlockFormView()
	{
		return blockFormView;
	}



	public void setBlockFormView(Block blockFormView)
	{
		this.blockFormView = blockFormView;
	}



	public Block getBlockGridView()
	{
		return blockGridView;
	}



	public void setBlockGridView(Block blockGridView)
	{
		this.blockGridView = blockGridView;
	}
    	
	
    public Zone getZoneFormView()
	{
		return zoneFormView;
	}



	public void setZoneFormView(Zone zoneFormView)
	{
		this.zoneFormView = zoneFormView;
	}



	private IOperationServiceRemote getOperationService() {
        return getBusinessServicesLocator().getOperationServiceRemote();
     }


     public Collection<?> getOperations() {
        return _operations;
     }



	public Operation getOperation()
	{
		return _operation;
	}



	public void setOperation(Operation operation)
	{
		this._operation = operation;
	}
     
     
	
    void setupRender() {
        loadRecords();
     }
	
	
    void loadRecords()
    {
       try
       {
           _operations = getOperationService().findOperations();
       }
       catch(BusinessException be)
       {

       }
       if(_operations!=null && !_operations.isEmpty())
       {
    	   Operation[] a = new Operation[_operations.size()];
    	   this._operation = _operations.toArray(a)[0];
    	   this.setTestvalue(_operation.getCode());
       }
    }
    

    
}
