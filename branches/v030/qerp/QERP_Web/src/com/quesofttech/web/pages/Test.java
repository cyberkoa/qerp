package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.sql.Timestamp;
import javax.annotation.Resource;
import org.apache.tapestry5.services.*;

import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.base.SecureBasePage;
import com.quesofttech.web.state.Visit;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.domain.general.*;
import com.quesoware.business.domain.general.iface.*;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;



import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
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
    
    /**
     * Set up via the traditional or Ajax component event request handler
     */
    @Environmental
    private ComponentEventResultProcessor componentEventResultProcessor;
    

    @Component(id="toolbar", 
    		parameters = {
    		"zone=zoneFormView"
    			})    
    private StandardToolbar toolbar;
    
    @Component 
    private Viewer viewer;
    
    @Property
    @Inject
    private Block blockViewer;
    
    @Property
    @Inject
    private Block blockFormView;

    @Inject
    private Block blockGridView;
    
    
    @Property
    private Zone zoneFormView;

    private Zone zoneGridView;
    
    private String testvalue;
    
    private String testvalue2;

    private Operation _operation;
    private Collection<?> _operations;

	private String zone;


	
    
    /**
     * Event handler for inplaceupdate event triggered from nested components when an Ajax update occurs. The event
     * context will carry the zone, which is recorded here, to allow the Grid and its sub-components to properly
     * re-render themselves.  Invokes {@link org.apache.tapestry5.services.ComponentEventResultProcessor#processResultValue(Object)}
     * passing this (the Grid component) as the content provider for the update.
     */
	@OnEvent(value = "addRecord")
    void onAddRecord(String zone) throws IOException
    {
		// Koa : check why zone = null
		// Koa : next try to createEventLink then use ClientBehaviourSupport to link the zone and link
		
		System.out.println("triggered addRecord, zone : " + zone);
        this.zone = zone;
	    //return blockViewer;
        //componentEventResultProcessor.processResultValue(this);

	    ComponentResources viewerResources = viewer.getResources();
        viewerResources.triggerEvent("addRecord", new Object[] { zone }, null);
        
    }
    


	public String getTestvalue()
	{
		return testvalue;
	}



	public void setTestvalue(String testvalue)
	{
		this.testvalue = testvalue;
	}



	public Block getBlockGridView()
	{
		return blockGridView;
	}



	public void setBlockGridView(Block blockGridView)
	{
		this.blockGridView = blockGridView;
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



	public String getTestvalue2()
	{
		return testvalue2;
	}



	public void setTestvalue2(String testvalue2)
	{
		this.testvalue2 = testvalue2;
	}
    
    
    

    
}
