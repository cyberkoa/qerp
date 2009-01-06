package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.security.Module;
import com.quesofttech.business.domain.security.iface.IModuleServiceRemote;
import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.base.SecureBasePage;
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
import org.apache.tapestry5.services.Request;
import org.omg.CosTransactions._SubtransactionAwareResourceStub;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
public class ModuleMaintenance extends SimpleBasePage {
// Default defination.
    private String _strMode = "";
    private Module ModuleDetail;
    private Module _Module;
    @Persist
    private List<Module> _Modules;
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

    @Component(id = "ModuleForm")
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
    // Text Component for IsBasic
    @Component(id = "IsBasic")
    private Checkbox _IsBasic;
    private boolean IsBasic;
    public boolean getIsBasic()
    {
       return IsBasic;
    }

    public void setIsBasic(boolean IsBasic)
    {
       this.IsBasic = IsBasic;
    }
    //===============================

    //===============================
    // Text Component for IsEnabled
    @Component(id = "IsEnabled")
    private Checkbox _IsEnabled;
    private boolean IsEnabled;
    public boolean getIsEnabled()
    {
       return IsEnabled;
    }

    public void setIsEnabled(boolean IsEnabled)
    {
       this.IsEnabled = IsEnabled;
    }
    //===============================

    //===============================
    // Text Component for IsPurchased
    @Component(id = "IsPurchased")
    private Checkbox _IsPurchased;
    private boolean IsPurchased;
    public boolean getIsPurchased()
    {
       return IsPurchased;
    }

    public void setIsPurchased(boolean IsPurchased)
    {
       this.IsPurchased = IsPurchased;
    }
    //===============================
    void RefreshRecords()
    {
       try
       {
           _Modules = getModuleService().findModules();
       }
       catch(BusinessException be)
       {

       }
       if(_Modules!=null && !_Modules.isEmpty())
       {
           if(int_SelectedRow==0)
           {
               ModuleDetail = _Modules.get(_Modules.size() - 1);
           }
           else
           {
               ModuleDetail = _Modules.get(int_SelectedRow - 1);
           }
           //ModuleDetail = _Modules.get(_Modules.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(ModuleDetail);
       }
    }
    private int getRcdLocation( Long id)  throws BusinessException
    {
      int int_return ,int_i;
      int_i = 0;
      int_return = 0;
      for(Module p : _Modules)
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

    void assignToDatabase(Module module){
       module.setId(id);
       module.setCode(Code);
       module.setDescription(Description);
       module.setIsBasic(IsBasic);
       module.setIsEnabled(IsEnabled);
       module.setIsPurchased(IsPurchased);
       module.setRecordStatus("A");
    }
    void assignToLocalVariable(Module module)
    {
       this.id = module.getId();
       this.Code = module.getCode();
       this.Description = module.getDescription();
       this.IsBasic = module.getIsBasic();
       this.IsEnabled = module.getIsEnabled();
       this.IsPurchased = module.getIsPurchased();
    }
    void _AddRecord()
    {
       Module module = new Module();
       try {
               module.setModifyLogin(getVisit().getMyLoginId());
               module.setCreateLogin(getVisit().getMyLoginId());
           assignToDatabase(module);
           getModuleService().addModule(module);
       }
       catch (Exception e) {
           _logger.info("Module_Add_problem");
           e.printStackTrace();
           _form.recordError(getMessages().get("Module_add_problem"));
       }
    }

    void _UpdateRecord(){
       Module module = new Module();
       try
       {
           module = getModuleService().findModule(id);
       }
       catch(BusinessException be)
       {

       }
       if(module !=null)
       {
           try {
               module.setModifyLogin(getVisit().getMyLoginId());
               assignToDatabase(module);
               getModuleService().updateModule(module);
           }
           catch (BusinessException e) {
               _form.recordError(_Code, e.getLocalizedMessage());
           }
       catch (Exception e) {
               _logger.info("Module_update_problem");
               e.printStackTrace();
               _form.recordError(getMessages().get("Module_update_problem"));
           }
       }
    }


    void _DeleteRecord(Long id) {
       Module module = new Module();
       try
       {
           module = getModuleService().findModule(id);
       }
       catch(BusinessException be)
       {

       }
       if(module!=null)
       {
           try {
               module.setModifyLogin(getVisit().getMyLoginId());
               getModuleService().logicalDeleteModule(module);
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
               _logger.info("Module_Delete_problem");
               e.printStackTrace();
               _form.recordError(getMessages().get("Module_Delete_problem"));
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
           ModuleDetail = getModuleService().findModule(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {

       }

       if(ModuleDetail!=null){
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(ModuleDetail);
           return blockFormView;
       }
       return null;
    }

    private IModuleServiceRemote getModuleService() {
       return getBusinessServicesLocator().getModuleServiceRemote();
    }


    public List<Module> getModules() {
       return _Modules;
    }


    public Module getModule() throws BusinessException{
       return _Module;
    }


     public void setModule(Module tb) {
       _Module = tb;
    }

}
