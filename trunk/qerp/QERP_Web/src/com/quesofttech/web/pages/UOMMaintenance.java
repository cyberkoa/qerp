package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.general.UOM;
import com.quesofttech.business.domain.general.iface.IUomServiceRemote;
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
public class UOMMaintenance extends SecureBasePage {
// Default defination.
    private String _strMode = "";
    private UOM UOMDetail;
    private UOM _UOM;
    @Persist
    private List<UOM> _UOMs;
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

    @Component(id = "UOMForm")
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
    // Text Component for PluralLabel
    @Component(id = "PluralLabel")
    private TextField _PluralLabel;
    private String PluralLabel;
    public String getPluralLabel()
    {
       return PluralLabel;
    }

    public void setPluralLabel(String PluralLabel)
    {
       this.PluralLabel = PluralLabel;
    }
    //===============================

    //===============================
    // Text Component for ShortForm
    @Component(id = "ShortForm")
    private TextField _ShortForm;
    private String ShortForm;
    public String getShortForm()
    {
       return ShortForm;
    }

    public void setShortForm(String ShortForm)
    {
       this.ShortForm = ShortForm;
    }
    //===============================

    //===============================
    // Text Component for SingularLabel
    @Component(id = "SingularLabel")
    private TextField _SingularLabel;
    private String SingularLabel;
    public String getSingularLabel()
    {
       return SingularLabel;
    }

    public void setSingularLabel(String SingularLabel)
    {
       this.SingularLabel = SingularLabel;
    }
    //===============================

    //===============================
    // Text Component for Symbol
    @Component(id = "Symbol")
    private TextField _Symbol;
    private String Symbol;
    public String getSymbol()
    {
       return Symbol;
    }

    public void setSymbol(String Symbol)
    {
       this.Symbol = Symbol;
    }
    //===============================

    //===============================
    // Text Component for Unit
    @Component(id = "Unit")
    private TextField _Unit;
    private String Unit;
    public String getUnit()
    {
       return Unit;
    }

    public void setUnit(String Unit)
    {
       this.Unit = Unit;
    }
    //===============================
    void RefreshRecords()
    {
       try
       {
           _UOMs = getUOMService().findUOMs();
       }
       catch(BusinessException be)
       {

       }
       if(_UOMs!=null && !_UOMs.isEmpty())
       {
           if(int_SelectedRow==0)
           {
               UOMDetail = _UOMs.get(_UOMs.size() - 1);
           }
           else
           {
               UOMDetail = _UOMs.get(int_SelectedRow - 1);
           }
           UOMDetail = _UOMs.get(_UOMs.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(UOMDetail);
       }
    }
    private int getRcdLocation( Long id)  throws BusinessException
    {
      int int_return ,int_i;
      int_i = 0;
      int_return = 0;
      for(UOM p : _UOMs)
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

    void assignToDatabase(UOM uOM){
       uOM.setId(id);      
       uOM.setPluralLabel(PluralLabel);
       uOM.setShortForm(ShortForm);
       uOM.setSingularLabel(SingularLabel);
       uOM.setSymbol(Symbol);
       uOM.setUnit(Unit);
       
       uOM.setRecordStatus("A");
    }
    void assignToLocalVariable(UOM uOM)
    {
       this.id = uOM.getId();
       this.PluralLabel = uOM.getPluralLabel();
       this.ShortForm = uOM.getShortForm();
       this.SingularLabel = uOM.getSingularLabel();
       this.Symbol = uOM.getSymbol();
       this.Unit = uOM.getUnit();
    }
    void _AddRecord()
    {
       UOM uOM = new UOM();
       try {
           uOM.setModifyLogin(getVisit().getMyLoginId());
           uOM.setCreateLogin(getVisit().getMyLoginId());
           assignToDatabase(uOM);
           System.out.println(uOM.toString());
           getUOMService().addUOM(uOM);
       }
       catch (Exception e) {
           _logger.info("UOM_Add_problem");
           e.printStackTrace();
           _form.recordError(getMessages().get("UOM_add_problem"));
       }
    }

    void _UpdateRecord(){
       UOM uOM = new UOM();
       try
       {
           uOM = getUOMService().findUOM(id);
       }
       catch(BusinessException be)
       {

       }
       if(uOM !=null)
       {
           try {
               uOM.setModifyLogin(getVisit().getMyLoginId());
               assignToDatabase(uOM);
               getUOMService().updateUOM(uOM);
           }
           catch (BusinessException e) {
               _form.recordError(_Unit, e.getLocalizedMessage());
           }
       catch (Exception e) {
               _logger.info("UOM_update_problem");
               e.printStackTrace();
               _form.recordError(getMessages().get("UOM_update_problem"));
           }
       }
    }


    void _DeleteRecord(Long id) {
       UOM uOM = new UOM();
       try
       {
           uOM = getUOMService().findUOM(id);
       }
       catch(BusinessException be)
       {

       }
       if(uOM!=null)
       {
           try {
               uOM.setModifyLogin(getVisit().getMyLoginId());
               getUOMService().logicalDeleteUOM(uOM);
               if(int_SelectedRow!=0)
               {
                   int_SelectedRow--;
               }
               RefreshRecords();
           }
               catch (BusinessException e) {
               _form.recordError(_Unit, e.getLocalizedMessage());
       }
           catch (Exception e) {
               _logger.info("UOM_Delete_problem");
               e.printStackTrace();
               _form.recordError(getMessages().get("UOM_Delete_problem"));
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
           UOMDetail = getUOMService().findUOM(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {

       }

       if(UOMDetail!=null){
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(UOMDetail);
           return blockFormView;
       }
       return null;
    }

    private IUomServiceRemote getUOMService() {
       return getBusinessServicesLocator().getUOMServiceRemote();
    }


    public List<UOM> getUOMs() {
       return _UOMs;
    }


    public UOM getUOM() throws BusinessException{
       return _UOM;
    }


     public void setUOM(UOM tb) {
       _UOM = tb;
    }

}
