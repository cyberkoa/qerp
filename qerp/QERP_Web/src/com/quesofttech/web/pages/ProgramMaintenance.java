package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.general.UOM;
import com.quesofttech.business.domain.inventory.MaterialType;
import com.quesofttech.business.domain.inventory.iface.*;
import com.quesofttech.business.domain.security.*;
import com.quesofttech.business.domain.security.iface.*;
import com.quesofttech.business.domain.security.iface.*;
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
import org.omg.CosTransactions._SubtransactionAwareResourceStub;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
public class ProgramMaintenance extends SecureBasePage {
	
	//===============================================================
	//			MaterialType ComboBox
	//===============================================================
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<Module> modulesSelect;	
	
	@Inject
    private PropertyAccess _access;
	
	private IModuleServiceRemote getModuleService() {
		return getBusinessServicesLocator().getModuleServiceRemote();
	}
	
	
	//===============================================================
	//				Material ComboBox
	//===============================================================
	
	
	
// Default defination.
    private String _strMode = "";
    private Program ProgramDetail;
    private Program _Program;
    @Persist
    private List<Program> _Programs;
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

    @Component(id = "ProgramForm")
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
    // Text Component for Code
    @Component(id = "Code")
    private TextField _Code;
    private String Code;
    public String getCode()
    {
       return Code;
    }

    public void setCode(String Code)
    {
       this.Code = Code;
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
    // Text Component for Type
    //@Component(id = "Type")
    //private TextField _Type;
    private String typesselect[] = {"Maintenance", "Report", "Inquiry", "Processing", "Posting" };
    public String[] getTypesSelect()
    {
    	return typesselect;
    }
    
    
    private String Type;
    public String getType()
    {
       return Type;
    }

    public void setType(String Type)
    {
       this.Type = Type;
    }
    //===============================

    //===============================
    // Text Component for Module
    //@Component(id = "Module")
    //private TextField _Module;
    private Module Module;
    public Module getModule()
    {
       return Module;
    }

    public void setModule(Module Module)
    {
       this.Module = Module;
    }
    //===============================
    void RefreshRecords()
    {
    	
    	List<Module> modules = null;		
    	try {
    		modules = this.getModuleService().findModules();
    		//System.out.println(modules.toString());
    	}
    	catch (DoesNotExistException e) {System.out.println("Error found here cb:");}
    	modulesSelect = null;
    	modulesSelect = new GenericSelectModel<Module>(modules,Module.class,"CodeDescription","id",_access);
		
       try
       {
           _Programs = getProgramService().findPrograms();
       }
       catch(BusinessException be)
       {

       }
       if(_Programs!=null && !_Programs.isEmpty())
       {
           if(int_SelectedRow==0)
           {
               ProgramDetail = _Programs.get(_Programs.size() - 1);
           }
           else
           {
               ProgramDetail = _Programs.get(int_SelectedRow - 1);
           }
           ProgramDetail = _Programs.get(_Programs.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(ProgramDetail);
       }
    }
    private int getRcdLocation( Long id)  throws BusinessException
    {
      int int_return ,int_i;
      int_i = 0;
      int_return = 0;
      for(Program p : _Programs)
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


    Object onFailure()
	{		
		_form.recordError("Page having error.  Please select/Add record and save again.");
		return blockFormView;
	}
    Object onSuccess()
    {
       _form.clearErrors();
       RefreshRecords();
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

    void assignToDatabase(Program program){
       program.setId(id);
       program.setCode(Code);
       program.setDescription(Description);
       program.setType(Type);
       program.setModule(Module);
       program.setRecordStatus("A");
    }
    void assignToLocalVariable(Program program)
    {
       this.id = program.getId();
       this.Code = program.getCode();
       this.Description = program.getDescription();
       this.Type = program.getType();
       this.Module = program.getModule();
    }
    void _AddRecord()
    {
       Program program = new Program();
       try {
    	   		System.out.println("getVisit().getMyLoginId():" + getVisit().getMyLoginId());
               program.setModifyLogin(getVisit().getMyLoginId());
               program.setCreateLogin(getVisit().getMyLoginId());
           assignToDatabase(program);
           getProgramService().addProgram(program);
       }
       catch (Exception e) {
           _logger.info("Program_Add_problem");
           e.printStackTrace();
           System.out.println(e.getMessage());
           _form.recordError(getMessages().get("Program_add_problem"));
       }
    }

    void _UpdateRecord(){
       Program program = new Program();
       try
       {
           program = getProgramService().findProgram(id);
       }
       catch(BusinessException be)
       {

       }
       if(program !=null)
       {
           try {
               program.setModifyLogin(getVisit().getMyLoginId());
               assignToDatabase(program);
               getProgramService().updateProgram(program);
           }
           catch (BusinessException e) {
               _form.recordError(_Code, e.getLocalizedMessage());
           }
       catch (Exception e) {
               _logger.info("Program_update_problem");
               e.printStackTrace();
               _form.recordError(getMessages().get("Program_update_problem"));
           }
       }
    }


    void _DeleteRecord(Long id) {
       Program program = new Program();
       try
       {
           program = getProgramService().findProgram(id);
       }
       catch(BusinessException be)
       {

       }
       if(program!=null)
       {
           try {
               program.setModifyLogin(getVisit().getMyLoginId());
               getProgramService().logicalDeleteProgram(program);
               if(int_SelectedRow!=0)
               {
                   int_SelectedRow--;
               }
               RefreshRecords();
           }
               catch (BusinessException e) {
               _form.recordError(_Code, e.getLocalizedMessage());
       }
           catch (Exception e) {
               _logger.info("Program_Delete_problem");
               e.printStackTrace();
               _form.recordError(getMessages().get("Program_Delete_problem"));
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
           ProgramDetail = getProgramService().findProgram(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {

       }

       if(ProgramDetail!=null){
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(ProgramDetail);
           return blockFormView;
       }
       return null;
    }

    private IProgramServiceRemote getProgramService() {
       return getBusinessServicesLocator().getProgramServiceRemote();
    }


    public List<Program> getPrograms() {
       return _Programs;
    }


    public Program getProgram() throws BusinessException{
       return _Program;
    }


     public void setProgram(Program tb) {
       _Program = tb;
    }

}
