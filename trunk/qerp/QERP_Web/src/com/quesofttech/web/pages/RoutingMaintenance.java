package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.inventory.iface.IMaterialServiceRemote;
import com.quesofttech.business.domain.production.Routing;
import com.quesofttech.business.domain.production.iface.IRoutingServiceRemote;
import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.base.SecureBasePage;
import com.quesofttech.web.model.base.GenericSelectModel;
import com.quesofttech.web.state.Visit;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Retain;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
public class RoutingMaintenance extends SecureBasePage {
	
	//========================================
	//          Material Combo
	//========================================
	@Inject
    private PropertyAccess _access;
	
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<Material> _materials;	
	
	//@Component(id = "Customer")
	//private TextField _Customer;
	
	//@Persist
	private Material material;
	
	
/*
	@Component(id = "Material")
	private TextField _Material;
	private Long Material;
*/
	public Material getMaterial()
	{
	   return material;
	}

	public void setMaterial(Material material)
	{
	   this.material = material;
	}
	
	
	
	//========================================
// Default defination.
    private String _strMode = "";
    private Routing RoutingDetail;
    private Routing _Routing;
    @Persist
    private List<Routing> _Routings;
    @Inject
    private Logger _logger;
    @Inject
    private Block blockFormView;
    @Persist
    private long lng_CurrentID;
// End of  Default defination.
    private String viewDisplayText="", viewEditText="";
    public String getViewDisplayText()
    {
     return viewDisplayText;
    }

    public String getviewEditText()
    {
         return viewEditText;
    }

    @Component(id = "RoutingForm")
    private Form _form;
    @Persist
    private int int_SelectedRow;

    @ApplicationState
    private String myState;

    @Component(id = "GRID")
    private Grid _Grid;

    //===============================
    // Text Component for id
    @Component(id = "id")
    private TextField _id;
    private Long id;
    public Long getid()
    {
       return id;
    }

    public void setid(Long id)
    {
       this.id = id;
    }
    //===============================

    //===============================
    // Text Component for Description
    @Component(id = "Description")
    private TextField _Description;
    private String Description;
    public String getDescription()
    {
       return Description;
    }

    public void setDescription(String Description)
    {
       this.Description = Description;
    }
    //===============================

    //===============================
    // Text Component for Operation
    @Component(id = "Operation")
    private TextField _Operation;
    private Integer Operation;
    public Integer getOperation()
    {
       return Operation;
    }

    public void setOperation(Integer Operation)
    {
       this.Operation = Operation;
    }
    //===============================

    //===============================
    // Text Component for SequenceType
    
	private IMaterialServiceRemote getMaterialService() {
		return getBusinessServicesLocator().getMaterialServiceRemote();
	}
    //===============================
    void RefreshRecords()
    {
    	List<Material> list = null;
    	try {
           list = this.getMaterialService().findForSaleMaterials();
    	}
    	catch (DoesNotExistException e) {
    		_form.recordError(e.getMessage());
    	}
    	
        _materials = new GenericSelectModel<Material>(list,Material.class,"codeDescription","id",_access);

       try
       {
           _Routings = getRoutingService().findRoutings();
       }
       catch(BusinessException be)
       {

       }
       if(_Routings!=null && !_Routings.isEmpty())
       {
           if(int_SelectedRow==0)
           {
               RoutingDetail = _Routings.get(_Routings.size() - 1);
           }
           else
           {
               RoutingDetail = _Routings.get(int_SelectedRow - 1);
           }
           RoutingDetail = _Routings.get(_Routings.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(RoutingDetail);
       }
             else
            {
                      myState="A"; // If no List then should be in A mode instead of Update mode.
            }
    }
private void refreshDisplay()
    {
        if(myState.equals("U"))
        {
            viewDisplayText="Block";
            viewEditText="none";
        }
        else
        {
                    viewDisplayText="none";
            viewEditText="Block";
        }
    }
    private int getRcdLocation( Long id)  throws BusinessException
    {
      int int_return ,int_i;
      int_i = 0;
      int_return = 0;
      for(Routing p : _Routings)
       {
          int_i++;
          if((long)p.getId().intValue()==id)
           {
                      int_return = int_i;
           }
       }
       return int_return;
    }

    public Block getBlock() {
       return blockFormView;
    }


    Object onSuccess()
    {
       _form.clearErrors();
       RefreshRecords();
       return blockFormView;
    }


    Object onFailure()
    {
       _form.clearErrors();
       _form.recordError(getMessages().get("Record_Save_Error"));
       return blockFormView;
    }


    void setupRender() {
       RefreshRecords();
    }


    void onValidateForm() {
       if ("U"== myState)
       {
           _UpdateRecord();
       }
       else
       if ("A" == myState)
       {
           _AddRecord();
       }
    }

    void assignToDatabase(Routing routing){
       routing.setId(id);
       routing.setDescription(Description);
       routing.setOperation(Operation);       
       routing.setRecordStatus("A");
       routing.setMaterial(material);
    }
    void assignToLocalVariable(Routing routing)
    {
       this.id = routing.getId();
       this.Description = routing.getDescription();
       this.Operation = routing.getOperation();       
       this.material = routing.getMaterial();
    }
    void _AddRecord()
    {
       Routing routing = new Routing();
       try {
               routing.setModifyLogin(getVisit().getMyLoginId());
               routing.setCreateLogin(getVisit().getMyLoginId());
               routing.setCreateApp(this.getClass().getSimpleName());
               routing.setModifyApp(this.getClass().getSimpleName());
           assignToDatabase(routing);
           getRoutingService().addRouting(routing);
       }
       catch (Exception e) {
    	   _form.recordError(e.getMessage());
       }
    }

    void _UpdateRecord(){
       Routing routing = new Routing();
       try
       {
           routing = getRoutingService().findRouting(id);
       }
       catch(BusinessException be)
       {

       }
       if(routing !=null)
       {
           try {
               routing.setModifyLogin(getVisit().getMyLoginId());
               assignToDatabase(routing);
               getRoutingService().updateRouting(routing);
           }
           catch (BusinessException e) {
               _form.recordError(e.getMessage());
           }
       catch (Exception e) {
    	   _form.recordError(e.getMessage());
           }
       }
    }


    void _DeleteRecord(Long id) {
       Routing routing = new Routing();
       try
       {
           routing = getRoutingService().findRouting(id);
       }
       catch(BusinessException be)
       {

       }
       if(routing!=null)
       {
           try {
               routing.setModifyLogin(getVisit().getMyLoginId());
               getRoutingService().logicalDeleteRouting(routing);
               if(int_SelectedRow!=0)
               {
                   int_SelectedRow--;
               }
               RefreshRecords();
           }
               catch (BusinessException e) {
            	   _form.recordError(e.getMessage());
       }
           catch (Exception e) {
        	   _form.recordError(e.getMessage());
           }
       }
    }

    void onActionFromtoolbarDel(Long id)
    {
            if (id!=null){
       _form.clearErrors();
       myState = "D";
       _strMode = "D";
       _DeleteRecord(id);
            }
    }

    Object onActionFromToolbarAdd ()
    {
       _form.clearErrors();
       myState = "A";
       _strMode = "A";
       return blockFormView;
    }

    Object onActionFromSelect(long id)
    {
       _form.clearErrors();
       myState = "U";
       _strMode = "U";
       lng_CurrentID = id;
       try
       {
           RoutingDetail = getRoutingService().findRouting(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {

       }

       if(RoutingDetail!=null){
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(RoutingDetail);
           return blockFormView;
       }
       return null;
    }

    private IRoutingServiceRemote getRoutingService() {
       return getBusinessServicesLocator().getRoutingServiceRemote();
    }


    public List<Routing> getRoutings() {
       return _Routings;
    }


    public Routing getRouting() throws BusinessException{
       return _Routing;
    }


     public void setRouting(Routing tb) {
       _Routing = tb;
    }

}
