package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.general.Operation;
import com.quesofttech.business.domain.general.iface.IOperationServiceRemote;
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
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
public class OperationMaintenance extends SimpleBasePage {
// Default defination.
    private String _strMode = "";
    private Operation OperationDetail;
    private Operation _Operation;
    @Persist
    private List<Operation> _Operations;
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

    @Component(id = "OperationForm")
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
    @Component(id = "Type")
    private TextField _Type;
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
    void RefreshRecords()
    {
       try
       {
           _Operations = getOperationService().findOperations();
       }
       catch(BusinessException be)
       {

       }
       if(_Operations!=null && !_Operations.isEmpty())
       {
           if(int_SelectedRow==0)
           {
               OperationDetail = _Operations.get(_Operations.size() - 1);
           }
           else
           {
               OperationDetail = _Operations.get(int_SelectedRow - 1);
           }
           OperationDetail = _Operations.get(_Operations.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(OperationDetail);
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
      for(Operation p : _Operations)
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

    void assignToDatabase(Operation operation){
       operation.setId(id);
       operation.setCode(Code);
       operation.setDescription(Description);
       operation.setType(Type);
       operation.setRecordStatus("A");
       java.util.Date today = new java.util.Date();
       operation.setModifyApp(this.getClass().getSimpleName());
       operation.setModifyLogin(getVisit().getMyLoginId());
       operation.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
    }
    void assignToLocalVariable(Operation operation)
    {
       this.id = operation.getId();
       this.Code = operation.getCode();
       this.Description = operation.getDescription();
       this.Type = operation.getType();
    }
    void _AddRecord()
    {
       Operation operation = new Operation();
       try {
                java.util.Date today = new java.util.Date();
                operation.setCreateApp(this.getClass().getSimpleName());
                operation.setCreateLogin(getVisit().getMyLoginId());
                operation.setCreateTimestamp(new java.sql.Timestamp(today.getTime()));
           assignToDatabase(operation);
           getOperationService().addOperation(operation);
       }
       catch (Exception e) {
           _logger.info("Operation_Add_problem");
           e.printStackTrace();
           _form.recordError(getMessages().get("Operation_add_problem"));
       }
    }

    void _UpdateRecord(){
       Operation operation = new Operation();
       try
       {
           operation = getOperationService().findOperation(id);
       }
       catch(BusinessException be)
       {

       }
       if(operation !=null)
       {
           try {
               operation.setModifyLogin(getVisit().getMyLoginId());
               assignToDatabase(operation);
               getOperationService().updateOperation(operation);
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
       Operation operation = new Operation();
       try
       {
           operation = getOperationService().findOperation(id);
       }
       catch(BusinessException be)
       {

       }
       if(operation!=null)
       {
           try {
               operation.setModifyLogin(getVisit().getMyLoginId());
               getOperationService().logicalDeleteOperation(operation);
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
           OperationDetail = getOperationService().findOperation(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {

       }

       if(OperationDetail!=null){
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(OperationDetail);
           return blockFormView;
       }
       return null;
    }

    private IOperationServiceRemote getOperationService() {
       return getBusinessServicesLocator().getOperationServiceRemote();
    }


    public List<Operation> getOperations() {
       return _Operations;
    }


    public Operation getOperation() throws BusinessException{
       return _Operation;
    }


     public void setOperation(Operation tb) {
       _Operation = tb;
    }

}
