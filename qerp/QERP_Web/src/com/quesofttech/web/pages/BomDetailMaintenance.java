package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.general.*;
import com.quesofttech.business.domain.inventory.*;
import com.quesofttech.business.domain.inventory.iface.IMaterialServiceRemote;
import com.quesofttech.business.domain.general.iface.*;
import com.quesofttech.business.domain.sales.SalesOrder;
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
import org.apache.tapestry5.corelib.components.DateField;
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
import com.quesofttech.web.util.*;
public class BomDetailMaintenance extends SecureBasePage {
// Default defination.
	void onActionFromtoolbarback()
    {
    	
    }
	private DateTimeConvert DateConv = new DateTimeConvert();
	
    private String _strMode = "";
    private BomDetail BomDetailDetail;
    private BomDetail _BomDetail;
    @Persist
    private List<BomDetail> _BomDetails;
    @Inject
    private Logger _logger;
    @Inject
    private Block blockFormView;
    @Persist
    private long lng_CurrentID;
    @Persist
    private Long _HeaderId;
// End of  Default defination.
    
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
    private String viewDisplayText="", viewEditText="";
    public String getViewDisplayText()
    {
		refreshDisplay();    	 
		return viewDisplayText;
    }

    public String getviewEditText()
    {
    	refreshDisplay();  
         return viewEditText;
    }

    @Component(id = "BomDetailForm")
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
    // Text Component for endDate
    @Component(id = "endDate")
    private DateField _endDate;
    private java.sql.Timestamp endDate;
    public java.util.Date getendDate()
    {
    	if (endDate==null)
        {
     	   return null;   
        }       
        return DateConv.SqlTimestamptoDateUtilDate(endDate);
    }

    public void setendDate(java.util.Date endDate)
    {
    	try
    	{
    		this.endDate = DateConv.utilDateToSqlTimestamp(endDate);    		
    	}
    	catch (Exception e)
    	{
    		_form.recordError(e.getMessage());
    	}
       
    }
    //===============================

    //===============================
    // Text Component for Location
    @Component(id = "Location")
    private TextField _Location;
    private String Location;
    public String getLocation()
    {
       return Location;
    }

    public void setLocation(String Location)
    {
       this.Location = Location;
    }
    //===============================

    //===============================
    // Text Component for quantityRequired
    @Component(id = "quantityRequired")
    private TextField _quantityRequired;
    private double quantityRequired;
    public double getquantityRequired()
    {
       return quantityRequired;
    }

    public void setquantityRequired(double quantityRequired)
    {
       this.quantityRequired = quantityRequired;
    }
    //===============================

    //===============================
    // Text Component for scrapFactor
    @Component(id = "scrapFactor")
    private TextField _scrapFactor;
    private double scrapFactor;
    public double getscrapFactor()
    {
       return scrapFactor;
    }

    public void setscrapFactor(double scrapFactor)
    {
       this.scrapFactor = scrapFactor;
    }
    //===============================

    //===============================
    // Text Component for startDate
    @Component(id = "startDate")
    private DateField _startDate;
    private java.sql.Timestamp startDate;
    public java.util.Date getstartDate()
    {
    	if (startDate==null)
        {
     	   return null;   
        }       
        return DateConv.SqlTimestamptoDateUtilDate(startDate);
    }

    public void setstartDate(java.util.Date startDate)
    {
    	try
    	{
    		this.startDate = DateConv.utilDateToSqlTimestamp(startDate);    		
    	}
    	catch (Exception e)
    	{
    		_form.recordError(e.getMessage());
    	}
       
    }
    //===============================

    //===============================
    // Text Component for BOM
    //@Component(id = "BOM")
    //private TextField _BOM;
    @Persist
    private BOM BOMHeader;
    public BOM getBOM()
    {
       return BOMHeader;
    }

    public void setBOM(BOM BOM)
    {
       this.BOMHeader = BOM;
    }
    //===============================

    //===============================
    // Text Component for Material
    //===============================================================
	//			Material ComboBox
	//===============================================================
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<Material> _materials;	
	
	
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<Operation> _operations;
	
	@Inject
    private PropertyAccess _access;
	
	private IOperationServiceRemote getOperationService(){
		return getBusinessServicesLocator().getOperationServiceRemote();
	}
	private IMaterialServiceRemote getMaterialService() {
		return getBusinessServicesLocator().getMaterialServiceRemote();
	}
	private Operation Operation;
	public Operation getOperation()
	{
		return Operation;
	}
	public void setOperation(Operation Operation)
	{
		this.Operation = Operation;
	}

    private Material Material;
    public Material getMaterial()
    {
       return Material;
    }

    public void setMaterial(Material Material)
    {
       this.Material = Material;
    }
    public String getMaterialCode()
    {
    	if(Material==null)
    	{
    		return "";
    	}
    	else
    	return Material.getCodeDescription();
    }
    //===============================
    void RefreshRecords()
    {
    	List<Material> list = null;	 
    	List<Operation> listoperation = null;
    	try {
    		listoperation = this.getOperationService().findOperationsByType("P");
    		list = this.getMaterialService().findNotForSaleMaterials();           
    	}
    	catch (DoesNotExistException e) {
    		_form.recordError(e.getMessage());
    	}
    	
    	_materials = null;
    	_materials = new GenericSelectModel<Material>(list,Material.class,"codeDescription","id",_access);
    	_operations = null;
    	_operations = new GenericSelectModel<Operation>(listoperation,Operation.class,"CodeDescription","id",_access);
		// ComboBox Refresh
       try
       {
           _BomDetails = getBomDetailService().findBomDetailsByBomId(_HeaderId);
       }
       catch(BusinessException be)
       {
    	   _form.recordError(be.getMessage());
       }
       if(_BomDetails!=null && !_BomDetails.isEmpty())
       {
           if(int_SelectedRow==0)
           {
               BomDetailDetail = _BomDetails.get(_BomDetails.size() - 1);
           }
           else
           {
               BomDetailDetail = _BomDetails.get(int_SelectedRow - 1);
           }
           //BomDetailDetail = _BomDetails.get(_BomDetails.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(BomDetailDetail);
       }
       else
       {
    	   myState="A"; // If no List then should be in A mode instead of Update mode.
       }
    }
    private int getRcdLocation( Long id)  throws BusinessException
    {
      int int_return ,int_i;
      int_i = 0;
      int_return = 0;
      for(BomDetail p : _BomDetails)
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
    	System.out.println("1 onSuccess:" + myState);
       _form.clearErrors();
       RefreshRecords();
       System.out.println("2 onSuccess:" + myState);
       return blockFormView;
    }


    Object onFailure() 
    {
       //_form.clearErrors();
    	try {
    	System.out.println("onFailure:" + myState + ", " );
    	System.out.println("onFailure:" + _form.getHasErrors());
    	}
    	catch (Exception e)
    	{
    		System.out.println("Error: " + e.getMessage());
    		_form.recordError(e.getMessage());
    	}
    	if(_form.getHasErrors())
    	{
    		_form.recordError(getMessages().get("Record_Save_Error"));
    	}
    		
       return blockFormView;
    }


    void setupRender() {
       RefreshRecords();
    }


    void FormValidation() throws Exception
    {
    	System.out.println("scrapFactor:" + scrapFactor);
    	if (scrapFactor<=0)
		 {    		
			 	throw new Exception("Scrap Factor must not less than or equal to 0");			    
		 }
    	if (quantityRequired<=0)
		 {    		
			 	throw new Exception("Quantity Required must not less than or equal to 0");			    
		 }
    	
    	if(startDate.after(endDate))
    	{
    		throw new Exception("Start Date Can not greater than End Date");
    	}
    }
    void onValidateForm() {
    	
    	try
    	{
    	   FormValidation();
    	   System.out.println("am i here?");
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
    	catch (Exception e)
    	{
    		//_form.clearErrors();
    		_form.recordError(e.getMessage());
    	}
    }

    void assignToDatabase(BomDetail bomDetail){
       bomDetail.setId(id);
       bomDetail.setEndDate(DateConv.utilDateToSqlTimestamp(endDate));
       bomDetail.setLocation(Location);
       bomDetail.setQuantityRequired(quantityRequired);
       bomDetail.setScrapFactor(scrapFactor);
       bomDetail.setStartDate(DateConv.utilDateToSqlTimestamp(startDate));
       //bomDetail.setBom(BOMHeader);
       bomDetail.setMaterial(Material);
       bomDetail.setRecordStatus("A");
       bomDetail.setOperation(Operation);
       
       java.util.Date today = new java.util.Date();	   
       bomDetail.setModifyApp(this.getClass().getSimpleName());
       bomDetail.setModifyLogin(getVisit().getMyLoginId());       
       bomDetail.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
    }
    void assignToLocalVariable(BomDetail bomDetail)
    {
       this.id = bomDetail.getId();
       this.endDate = bomDetail.getEndDate();
       this.Location = bomDetail.getLocation();
       this.quantityRequired = bomDetail.getQuantityRequired();
       this.scrapFactor = bomDetail.getScrapFactor();
       this.startDate = bomDetail.getStartDate();
       this.BOMHeader = bomDetail.getBom();
       this.Material = bomDetail.getMaterial();
       this.Operation = bomDetail.getOperation();
    }
    void _AddRecord()
    {
       BomDetail bomDetail = new BomDetail();
       try {
    	   java.util.Date today = new java.util.Date();	   
    	   bomDetail.setCreateApp(this.getClass().getSimpleName());
    	   bomDetail.setCreateLogin(getVisit().getMyLoginId());       
    	   bomDetail.setCreateTimestamp(new java.sql.Timestamp(today.getTime()));
           assignToDatabase(bomDetail);
           getBomDetailService().addBomDetail(_HeaderId,bomDetail);
       }
       catch (Exception e) {
    	   _form.recordError(e.getMessage());
       }
    }

    void _UpdateRecord(){
       BomDetail bomDetail = new BomDetail();
       try
       {
           bomDetail = getBomDetailService().findBomDetail(id);
       }
       catch(BusinessException be)
       {

       }
       if(bomDetail !=null)
       {
           try {
               bomDetail.setModifyLogin(getVisit().getMyLoginId());
               assignToDatabase(bomDetail);
               getBomDetailService().updateBomDetail(bomDetail);
           }
           catch (BusinessException e) {
               _form.recordError(_id, e.getLocalizedMessage());
           }
       catch (Exception e) {
    	   _form.recordError(e.getMessage());
           }
       }
    }


    void _DeleteRecord(Long id) {
       BomDetail bomDetail = new BomDetail();
       try
       {
           bomDetail = getBomDetailService().findBomDetail(id);
       }
       catch(BusinessException be)
       {
    	   _form.recordError(be.getMessage());
       }
       if(bomDetail!=null)
       {
           try {
               bomDetail.setModifyLogin(getVisit().getMyLoginId());
               getBomDetailService().logicalDeleteBomDetail(bomDetail);
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
           BomDetailDetail = getBomDetailService().findBomDetail(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {
    	   _form.recordError(be.getMessage());
       }

       if(BomDetailDetail!=null){
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(BomDetailDetail);
           return blockFormView;
       }
       return null;
    }

    private IBomServiceRemote getBomDetailService() {
       return getBusinessServicesLocator().getBOMServiceRemote();
    }


    public List<BomDetail> getBomDetails() {
       return _BomDetails;
    }


    public BomDetail getBomDetail() throws BusinessException{
       return _BomDetail;
    }


     public void setBomDetail(BomDetail tb) {
       _BomDetail = tb;
    }
     public Long getHeaderID()
     {
     	return _HeaderId;
     }
     public void setHeaderID(Long id) throws Exception
     {
    	 _HeaderId = id;
    	 BOMHeader  = getBomDetailService().findBOM(id);
     }
     public String getBomType()
     {
    	 String _temp;
    	 if(BOMHeader!=null)
    		 _temp = BOMHeader.getType();
    	 else 
    		 _temp = "";
    	 
    	 return _temp;
     }
     
     public String getBomMaterialCode()
     {
    	 String _temp;
    	 if(BOMHeader!=null)
    		 _temp = BOMHeader.getMaterialCode();
    	 else 
    		 _temp = "";
    	 
    	 return _temp;
     }
     
    


}
