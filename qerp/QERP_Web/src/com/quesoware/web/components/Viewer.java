
package com.quesoware.web.components;

//import org.apache.tapestry5.annotations.Component;
import java.io.IOException;

import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
//import org.apache.tapestry5.corelib.components.Form;
//import org.apache.tapestry5.corelib.components.TextField;
//import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ClientBehaviorSupport;
import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;






public class Viewer
{
	//@Parameter
	//private Zone zone;
	
	@Parameter
	private BeanModel model;
	

    /**
     * Set up via the traditional or Ajax component event request handler
     */
    @Environmental
    private ComponentEventResultProcessor componentEventResultProcessor;

	
    @Inject
    private ComponentResources resources;
    

 

    /**
     * The name of the psuedo-zone that encloses the Grid.
     */
    @Property(write = false)
    private String zone;
    
    @Environmental
    private ClientBehaviorSupport clientBehaviorSupport;
	
	
       
    
    public ComponentResources getResources()
	{
		return resources;
	}


	// Text Component for Code
    @Component(id = "code")
    private TextField _code;
    private String code;
    public String getCode()
    {
       return this.code;
    }

    public void setCode(String code)
    {
       this.code = code;
    }
    
    
    Object onAddRecord(String zone) throws IOException
    {
		// Koa : check why zone = null
		// Koa : next try to createEventLink then use ClientBehaviourSupport to link the zone and link
		
		System.out.println("triggered addRecord in viewer, zone : " + zone);
        this.zone = zone;
	   
        componentEventResultProcessor.processResultValue(this);
        return true;
        
    }
    
}
