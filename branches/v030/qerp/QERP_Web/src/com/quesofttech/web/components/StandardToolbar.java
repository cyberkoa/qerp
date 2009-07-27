
package com.quesofttech.web.components;

//import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.*;
//import org.apache.tapestry5.corelib.components.Form;
//import org.apache.tapestry5.corelib.components.TextField;
//import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ComponentResources;





public class StandardToolbar
{
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


    Object onActionFromToolbarAdd ()
    {
       //_form.clearErrors();
       //myState = "A";
       //_strMode = "A";
       //return blockFormView;
    	
        // Now that the environment is setup, inform the component or other listeners that the form
        // is about to render.  

        resources.triggerEvent(EVENT_ADD, context, null);
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