
package com.quesoware.web.components;

//import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
//import org.apache.tapestry5.corelib.components.Form;
//import org.apache.tapestry5.corelib.components.TextField;
//import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;




@SupportsInformalParameters
public class StandardToolbar
{
	@Parameter
	@Property
	private String zone;
	
	@Parameter
	private Object viewer;
	
	
	@Parameter
	@Property
	private Block block;
	
	
	/**
     * client event name
    */
    public static final String EVENT_ADD = "addRecord";
    
    public static final String EVENT_SAVE = "saveRecord";
    
    public static final String EVENT_DELETE = "deleteRecord";

    public static final String EVENT_FILTER = "filterRecord";

    
    
    /**
     * The context for the link (optional parameter). This list of values will be converted into strings and included in
     * the URI. The strings will be coerced back to whatever their values are and made available to event handler
     * methods.
     */
    @Parameter
    private Object[] context;
    
    
    @Inject
    private ComponentResources resources;
    
    //@Inject
    //private Zone zone;
    
    // ComponentLinks
    private Object[] navigationTargets;
    private Object[] ioTargets;
    private Object[] updateTargets;
    
    
    
//	@Component(id = "lowerDocNo")
//	Viewer

    private void addRecord()
    {
        // Now that the environment is setup, inform the component or other listeners that the form
        // is about to render.  
        
        resources.triggerEvent(EVENT_ADD, context, null);

        //resources.triggerEvent(EventConstants.PREPARE, context, null);
    }    
    void onAction ()
    {
    	System.out.println("trigger in toolbar onAction");
    }
    
    Object onActionFromToolbarAdd ()
    {
       //_form.clearErrors();
       //myState = "A";
       //_strMode = "A";
       //return blockFormView;
    	
        // Now that the environment is setup, inform the component or other listeners that the form
        // is about to render.  
    	System.out.println("trigger in toolbar"); 
    	//ComponentResources containerResources = resources.getContainerResources();
    	
    	
    	//return block;
        resources.triggerEvent(EVENT_ADD, new Object[] { zone }, null);
        return resources.getBlockParameter("block");
        //return new MultiZoneUpdate("zoneFormView", block);
        //return true; // abort event
    }

    Object onActionFromToolbarSave ()
    {
       //_form.clearErrors();
       //myState = "A";
       //_strMode = "A";
       //return blockFormView;
    	
        // Now that the environment is setup, inform the component or other listeners that the form
        // is about to render.  

    	
    	
    	
        resources.triggerEvent(EVENT_SAVE, context, null);
        // should update here , or let the container update ?
        //return new MultiZoneUpdate("userInput", registrationForm).add("helpPanel", registrationHelp);

    	return null;
    }
    
    
    
    
    
    
    void onActionFromtoolbarDel(Long id)
    {
       if (id!=null){
       //_form.clearErrors();
       //myState = "D";
       //_strMode = "D";
       //_DeleteRecord(id);
       }
    }
    
    

    
}