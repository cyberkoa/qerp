package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.system.DocumentType;
import com.quesofttech.business.domain.system.iface.IDocumentTypeServiceRemote;
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
public class DocumentTypeMaintenance extends SecureBasePage {
// Default defination.
    private String _strMode = "";
    private DocumentType DocumentTypeDetail;
    private DocumentType _DocumentType;
    @Persist
    private List<DocumentType> _DocumentTypes;
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

    @Component(id = "DocumentTypeForm")
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
    // Text Component for id
    @Component(id = "numberFormat")
    private TextField _numberFormat;
    private String numberFormat;
    public String getNumberFormat()
    {
       return numberFormat;
    }

    public void setNumberFormat(String numberFormat)
    {
       this.numberFormat = numberFormat;
    }
    //===============================

    //===============================
    // Text Component for Category
    @Component(id = "Catergory")
    private TextField _Category;
    private Long Category;
    public Long getCategory()
    {
       return Category;
    }

    public void setCategory(Long Category)
    {
       this.Category = Category;
    }
    //===============================
    
 
    @Component(id = "Description")
    private TextField _description;
    private String Description;
    
    
    

    public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	//===============================
    // Text Component for Prefix
    @Component(id = "Prefix")
    private TextField _Prefix;
    private String Prefix;
    public String getPrefix()
    {
       return Prefix;
    }

    public void setPrefix(String Prefix)
    {
       this.Prefix = Prefix;
    }
    //===============================

    //===============================
    // Text Component for RunningNo
    @Component(id = "RunningNo")
    private TextField _RunningNo;
    private Long RunningNo;
    public Long getRunningNo()
    {
       return RunningNo;
    }

    public void setRunningNo(Long RunningNo)
    {
       this.RunningNo = RunningNo;
    }
    //===============================

    //===============================
    // Text Component for DocType
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
    void RefreshRecords() throws Exception
    {
       try
       {
           _DocumentTypes = getDocumentTypeService().findDocumentTypes();
       }
       catch(BusinessException be)
       {

       }
       if(_DocumentTypes!=null && !_DocumentTypes.isEmpty())
       {
           if(int_SelectedRow==0)
           {
               DocumentTypeDetail = _DocumentTypes.get(_DocumentTypes.size() - 1);
           }
           else
           {
               DocumentTypeDetail = _DocumentTypes.get(int_SelectedRow - 1);
           }
           DocumentTypeDetail = _DocumentTypes.get(_DocumentTypes.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(DocumentTypeDetail);
       }
             else
            {
                      myState="A"; // If no List then should be in A mode instead of Update mode.
            }
    }
private void refreshDisplay() throws Exception
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
      for(DocumentType p : _DocumentTypes)
       {
          int_i++;
          if((long)p.getId().intValue()==id)
           {
                      int_return = int_i;
           }
       }
       return int_return;
    }

    public Block getBlock()  throws Exception{
       return blockFormView;
    }


    Object onSuccess() throws Exception
    {
       _form.clearErrors();
       RefreshRecords();
       return blockFormView;
    }


    Object onFailure() throws Exception
    {
       _form.clearErrors();
       _form.recordError(getMessages().get("Record_Save_Error"));
       return blockFormView;
    }


    void setupRender()  throws Exception{
       RefreshRecords();
    }


    void onValidateForm()  throws Exception{
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

    void assignToDatabase(DocumentType documentType){
       documentType.setNumberFormat(numberFormat);
       documentType.setId(id);
       documentType.setDescription(Description);
       documentType.setCatergory(Category);
       documentType.setPrefix(Prefix);
       documentType.setRunningNo(RunningNo);
       documentType.setType(Type);
       documentType.setRecordStatus("A");
       //documentType.setDescription("test");
       java.util.Date today = new java.util.Date();
       documentType.setSessionId("");
       documentType.setVersion((long)1);
       documentType.setModifyApp(this.getClass().getSimpleName());
       documentType.setModifyLogin(getVisit().getMyLoginId());       
       documentType.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
       System.out.println("documenttype:" + documentType.toString());
    }
    void assignToLocalVariable(DocumentType documentType)
    {
       this.id = documentType.getId();
       this.Description = documentType.getDescription();
       this.Category = documentType.getCatergory();
       this.Prefix = documentType.getPrefix();
       this.RunningNo = documentType.getRunningNo();
       this.Type = documentType.getType();
       this.numberFormat = documentType.getNumberFormat();
    }
    void _AddRecord()
    {
       DocumentType documentType = new DocumentType();
       try {   	   
               
		   documentType.setCreateLogin(getVisit().getMyLoginId());               
		   documentType.setCreateApp(this.getClass().getSimpleName());		                 
		   java.util.Date today = new java.util.Date();
		   documentType.setCreateTimestamp(new java.sql.Timestamp(today.getTime()));
		       
		   assignToDatabase(documentType);           
		   getDocumentTypeService().addDocumentType(documentType);
       }
       catch (Exception e) {
    	   _form.recordError(e.getMessage());
       }
    }

    void _UpdateRecord(){
       DocumentType documentType = new DocumentType();
       try
       {
           documentType = getDocumentTypeService().findDocumentType(id);
       }
       catch(BusinessException be)
       {
    	   _form.recordError(be.getMessage());
       }
       if(documentType !=null)
       {
           try {
               documentType.setModifyLogin(getVisit().getMyLoginId());
               assignToDatabase(documentType);
               getDocumentTypeService().updateDocumentType(documentType);
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
       DocumentType documentType = new DocumentType();
       try
       {
           documentType = getDocumentTypeService().findDocumentType(id);
       }
       catch(BusinessException be)
       {
    	   _form.recordError(be.getMessage());
       }
       if(documentType!=null)
       {
           try {
               documentType.setModifyLogin(getVisit().getMyLoginId());
               getDocumentTypeService().logicalDeleteDocumentType(documentType);
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

    Object onActionFromToolbarAdd () throws Exception
    {
       _form.clearErrors();
       myState = "A";
       _strMode = "A";
       return blockFormView;
    }

    Object onActionFromSelect(long id) throws Exception
    {
       _form.clearErrors();
       myState = "U";
       _strMode = "U";
       lng_CurrentID = id;
       try
       {
           DocumentTypeDetail = getDocumentTypeService().findDocumentType(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {

       }

       if(DocumentTypeDetail!=null){
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(DocumentTypeDetail);
           return blockFormView;
       }
       return null;
    }

    private IDocumentTypeServiceRemote getDocumentTypeService() {
    	
       return getBusinessServicesLocator().getDocumentTypeServiceRemote();
    }


    public List<DocumentType> getDocumentTypes() {
       return _DocumentTypes;
    }


    public DocumentType getDocumentType() throws BusinessException{
       return _DocumentType;
    }


     public void setDocumentType(DocumentType tb) {
       _DocumentType = tb;
    }

}
