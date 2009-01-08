// TODO:SalesOrderMaintenance is remarked. due to some error.
package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.exception.DuplicateAlternateKeyException;
import com.quesofttech.business.common.exception.DuplicatePrimaryKeyException;
import com.quesofttech.business.domain.sales.Customer;
import com.quesofttech.business.domain.sales.SalesOrderMaterial;
import com.quesofttech.business.domain.inventory.Material;

//import com.quesofttech.business.domain.sales.iface.ISalesOrderMaterialServiceRemote;
import com.quesofttech.business.domain.inventory.iface.IMaterialServiceRemote;
import com.quesofttech.business.domain.sales.iface.ISalesOrderServiceRemote;
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
import org.omg.CosTransactions._SubtransactionAwareResourceStub;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;



public class SalesOrderMaterialMaintenance extends SecureBasePage {
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
	/*public SalesOrderMaterialMaintenance() throws BusinessException{
	  	List<Material> list = null;
    	try {
           list = this.getMaterialService().findMaterials();
    	}
    	catch (DoesNotExistException e) {}
    	
        _materials = new GenericSelectModel<Material>(list,Material.class,"code","id",_access);


	}*/
	
	
	
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
	@Persist
	private String _headerId;
	@Persist
	private Long _headerIDLng;
	private String _strMode = "";
	private SalesOrderMaterial SalesOrderMaterialDetail;
	private SalesOrderMaterial _SalesOrderMaterial;
	@Persist
	private List<SalesOrderMaterial> _SalesOrderMaterials;
	@Inject
	private Logger _logger;
	@Inject
	private Block blockFormView;
	@Persist
	private long lng_CurrentID;
	@Component(id = "SalesOrderMaterialForm")
	private Form _form;
	@Persist
	private int int_SelectedRow;
	@ApplicationState
	private String myState;
	
	@Component(id = "GRID")
	private Grid _Grid;

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

	@Component(id = "CustomerPOLine")
	private TextField _CustomerPOLine;
	private Long CustomerPOLine;
	public Long getCustomerPOLine()
	{
	   return CustomerPOLine;
	}
	public void setCustomerPOLine(Long CustomerPOLine)
	{
	   this.CustomerPOLine = CustomerPOLine;
	}

	@Component(id = "Line")
	private TextField _Line;
	private Long Line;
	public Long getLine()
	{
	   return Line;
	}
	public void setLine(Long Line)
	{
	   this.Line = Line;
	}

	@Component(id = "Price")
	private TextField _Price;
	private Double Price;
	public Double getPrice()
	{
	   return Price;
	}
	public void setPrice(Double Price)
	{
	   this.Price = Price;
	}

	@Component(id = "QuantityOrder")
	private TextField _QuantityOrder;
	private Double QuantityOrder;
	public Double getQuantityOrder()
	{
	   return QuantityOrder;
	}

	public void setQuantityOrder(Double QuantityOrder)
	{
	   this.QuantityOrder = QuantityOrder;
	}

	@Component(id = "QuantityShipped")
	private TextField _QuantityShipped;
	
	private Double QuantityShipped;
	public Double getQuantityShipped()
	{
	   return QuantityShipped;
	}
	
	public void setQuantityShipped(Double QuantityShipped)
	{
	   this.QuantityShipped = QuantityShipped;
	}

	
	
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

	void RefreshRecords()
	{
		List<Material> list = null;
    	try {
           list = this.getMaterialService().findForSaleMaterials();
    	}
    	catch (DoesNotExistException e) {}
    	
        _materials = new GenericSelectModel<Material>(list,Material.class,"codeDescription","id",_access);

		try
    	{
    	//	if (_headerId.isEmpty() || _headerId==null)
    //		{
    	//		_headerId = "0";
    		//}
    	
    		//_headerIDLng.getLong(_headerId);
    		//_headerIDLng = Long.parseLong(_headerId);
    		//_headerIDLng = Long.valueOf(_headerId).longValue();
    		System.out.println("going to check liao");
    		//_headerIDLng = 1L;
    	   _SalesOrderMaterials = getSalesOrderService().findSalesOrderMaterialsBySalesOrderId(_headerIDLng);
    	   System.out.println("going to check liao 1");
    	   if(_SalesOrderMaterials!=null && !_SalesOrderMaterials.isEmpty())
       		{
    		   System.out.println("refresh record.  i should be here");
    		   
    		   if(int_SelectedRow==0)
				{
				   SalesOrderMaterialDetail = _SalesOrderMaterials.get(_SalesOrderMaterials.size() - 1);
				   myState = "U";
				   _strMode = "U";
				   lng_CurrentID = _SalesOrderMaterials.size() - 1;
				}
				else
				{
					SalesOrderMaterialDetail = _SalesOrderMaterials.get(int_SelectedRow - 1);
					myState = "U";
					_strMode = "U";
				   	lng_CurrentID = int_SelectedRow - 1;
				}	
    		   myState="U";
    		   viewDisplayText="Block";
    		   viewEditText="none";
    		   assignToLocalVariable(SalesOrderMaterialDetail);
       		}
    	   else
           {
        	   myState="A"; // If no List then should be in A mode instead of Update mode.
           }
    	}
    	catch(Exception be)
    	{
    		_form.recordError(getMessages().get("refresh problem"));
    	}
    	
	}
	
	private int getRcdLocation( Long id)  throws BusinessException
	{
        int int_return ,int_i;
        int_i = 0;
        int_return = 0;
        /*
        //_SalesOrderMaterials = getSalesOrderService().findSalesOrderMaterials();
        if (_headerId.isEmpty() || _headerId==null)
		{
			_headerId = "0";
		}
		*/
        //_headerIDLng.getLong(_headerId);
		//_headerIDLng = Long.parseLong(_headerId);
		//_headerIDLng = Long.valueOf(_headerId).longValue();
        _SalesOrderMaterials = getSalesOrderService().findSalesOrderMaterialsBySalesOrderId(_headerIDLng);
        for(SalesOrderMaterial p : _SalesOrderMaterials)
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
		//System.out.println("onFailure lah");
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
		int_SelectedRow=0;
		RefreshRecords();	   
	}
	
	
	void onValidateForm() {
		try{
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
			_form.recordError(getMessages().get("Record_Update_Error"));
		}
		
	}
	
	void assignToDatabase(SalesOrderMaterial salesOrderMaterial){
	   salesOrderMaterial.setId(id);
	   salesOrderMaterial.setCustomerPOLine(CustomerPOLine);
	   salesOrderMaterial.setLine(Line);
	   salesOrderMaterial.setPrice(Price);
	   salesOrderMaterial.setQuantityOrder(QuantityOrder);
	   salesOrderMaterial.setQuantityShipped(QuantityShipped);
	   //salesOrderMaterial.setversion(version);
	   salesOrderMaterial.setMaterial(material);
	   salesOrderMaterial.setRecordStatus("A");
	}
	
	void assignToLocalVariable(SalesOrderMaterial salesOrderMaterial)
	{
	   this.id = salesOrderMaterial.getId();
	   this.CustomerPOLine = salesOrderMaterial.getCustomerPOLine();
	   this.Line = salesOrderMaterial.getLine();
	   this.Price = salesOrderMaterial.getPrice();
	   this.QuantityOrder = salesOrderMaterial.getQuantityOrder();
	   this.QuantityShipped = salesOrderMaterial.getQuantityShipped();
	   //this.version = salesOrderMaterial.getversion();
	   this.material = salesOrderMaterial.getMaterial();
	}
	
	void _AddRecord()
	{
        SalesOrderMaterial salesOrderMaterial = new SalesOrderMaterial();
        try {
        	salesOrderMaterial.setCreateLogin(getVisit().getMyLoginId());
        	salesOrderMaterial.setModifyLogin(getVisit().getMyLoginId());
			 
 		    salesOrderMaterial.setCreateApp(this.getClass().getSimpleName());
		    salesOrderMaterial.setModifyApp(this.getClass().getSimpleName());
        	
        	
            assignToDatabase(salesOrderMaterial);            
            getSalesOrderService().addSalesOrderMaterial(_headerIDLng, salesOrderMaterial);
        }
    	catch (BusinessException e) {
    			_form.recordError(e.getLocalizedMessage());
    	}  
    	catch (Exception e) {
    		   _logger.info("Record_Add_Error");
    		   e.printStackTrace();
    		   _form.recordError(getMessages().get("Record_Add_Error"));
    	}
	}
	
	void _UpdateRecord(){
        SalesOrderMaterial salesOrderMaterial = new SalesOrderMaterial();
        try
        {
            salesOrderMaterial = getSalesOrderService().findSalesOrderMaterial(id);
        }
    	catch(BusinessException be)
    	{
    	   _logger.info("Record_Deleted_By_Others");
    	   be.printStackTrace();
    	   _form.recordError(getMessages().get("Record_Deleted_By_Others"));			
    	}
        if(salesOrderMaterial !=null)
        {
            try {
            	//salesOrderMaterial.setCreateLogin(getVisit().getMyLoginId());
            	salesOrderMaterial.setModifyLogin(getVisit().getMyLoginId());   		   
    		    salesOrderMaterial.setModifyApp(this.getClass().getSimpleName());
            	
                assignToDatabase(salesOrderMaterial);
                getSalesOrderService().updateSalesOrderMaterial(salesOrderMaterial);
                }
    		catch (BusinessException e) {	
    				_form.recordError(e.getLocalizedMessage());
    		}
    		catch (Exception e) {
    		   _logger.info("Record_Update_Error");
    		   e.printStackTrace();
    		   _form.recordError(getMessages().get("Record_Update_Error"));
           }
        }
	}
	
	
	void _DeleteRecord(Long id) {
    	SalesOrderMaterial salesOrderMaterial = new SalesOrderMaterial();
    	try
    	{
    	   salesOrderMaterial = getSalesOrderService().findSalesOrderMaterial(id);
    	}
    	   catch(BusinessException be)
    		{
    			_logger.info("Record_Deleted_By_Others");
    			   be.printStackTrace();
    			   _form.recordError(getMessages().get("Record_Deleted_By_Others"));
    		}
    	   
    	if(salesOrderMaterial!=null)
    	{
    	   try {
    		   
    		   int_SelectedRow=getRcdLocation(id);
		   		
				salesOrderMaterial.setModifyLogin(getVisit().getMyLoginId());
    		    salesOrderMaterial.setModifyApp(this.getClass().getSimpleName());
   				
    		   getSalesOrderService().logicalDeleteSalesOrderMaterial(salesOrderMaterial);
	    		  if(int_SelectedRow!=0)
	    		  {
	    			  int_SelectedRow--;
	    		  }		  
    		  RefreshRecords();
    		   
    	   
    	   	}
           catch (BusinessException e) {
               _form.recordError(_id, e.getLocalizedMessage());
           }
    	   catch (Exception e) {
    				   _logger.info("Record_Delete_Error");
    				   e.printStackTrace();
    				   _form.recordError(getMessages().get("Record_Delete_Error"));
    	   }
    	}
	}
	
	void onActionFromtoolbarDel(Long id)
	{
		if (id!=null) {
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
            SalesOrderMaterialDetail = getSalesOrderService().findSalesOrderMaterial(id);
            //SalesOrderMaterialDetail
            int_SelectedRow = getRcdLocation(id);
        }
        catch(BusinessException be)
        {
        	
        }
        catch (Exception e)
        {
        	System.out.println("error liao la" + e.getMessage());
        }
        
        if(SalesOrderMaterialDetail!=null)
        {
            assignToLocalVariable(SalesOrderMaterialDetail);
            return blockFormView;
        }
        return null;
	}
	
	private ISalesOrderServiceRemote getSalesOrderService() {
	   return getBusinessServicesLocator().getSalesOrderServiceRemote();
	}
	
	private IMaterialServiceRemote getMaterialService() {
		return getBusinessServicesLocator().getMaterialServiceRemote();
	}
	
	
	public List<SalesOrderMaterial> getSalesOrderMaterials() {
	   return _SalesOrderMaterials;
	}
	
	
	public SalesOrderMaterial getSalesOrderMaterial() throws BusinessException{
	   return _SalesOrderMaterial;
	}
    
	public void setSalesOrderMaterial(SalesOrderMaterial tb) {
        _SalesOrderMaterial = tb;
    }
    
	public Long getHeaderID()
    {
    	return _headerIDLng;
    }
    
	public void setHeaderID(Long id)
    {
    	_headerIDLng = id;
    }
    /*String onPassivate() {
    	System.out.println("onPassivate:" + _headerId);
		return _headerId;
		
	}*/

	

}

