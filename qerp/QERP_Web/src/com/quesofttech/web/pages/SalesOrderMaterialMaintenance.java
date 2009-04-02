// TODO:SalesOrderMaintenance is remarked. due to some error.
package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.tapestry5.beaneditor.BeanModel;



//import com.quesoware.business.domain.sales.iface.ISalesOrderMaterialServiceRemote;
import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.base.SecureBasePage;
//import com.quesoware.web.components.FilterData;
import com.quesofttech.web.components.FilterDataSalesOrderMaterial;
import com.quesofttech.web.components.QERPWindow;
import com.quesofttech.web.model.base.GenericSelectModel;
import com.quesofttech.web.state.Visit;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.common.exception.DuplicateAlternateKeyException;
import com.quesoware.business.common.exception.DuplicatePrimaryKeyException;
import com.quesoware.business.common.query.SearchOptions;
import com.quesoware.business.domain.embeddable.RowInfo;
import com.quesoware.business.domain.inventory.Material;
import com.quesoware.business.domain.inventory.iface.IMaterialServiceRemote;
import com.quesoware.business.domain.production.ProductionOrder;
import com.quesoware.business.domain.production.iface.IProductionOrderServiceRemote;
import com.quesoware.business.domain.sales.Customer;
import com.quesoware.business.domain.sales.SalesOrder;
import com.quesoware.business.domain.sales.SalesOrderMaterial;
import com.quesoware.business.domain.sales.dto.SalesOrderMaterialSearchFields;
import com.quesoware.business.domain.sales.dto.SalesOrderSearchFields;
import com.quesoware.business.domain.sales.iface.ISalesOrderServiceRemote;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.sun.org.apache.xpath.internal.operations.Bool;
//import com.sun.org.apache.xml.internal.serializer.utils.Messages;
//import com.sun.xml.internal.ws.api.message.Message;

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
import org.apache.tapestry5.services.*;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
import org.apache.tapestry.commons.components.Window;
//interface Delegate
//{
//	List<SalesOrderMaterial> bindData();
//}


public class SalesOrderMaterialMaintenance extends SimpleBasePage {
	
	@Component(parameters = {"width=300", "height=300", 
			 "style=bluelighting", "show=false","modal=true", "title=literal:Filter Window"})
	@Property
	private QERPWindow _filterWindow;
	
	
	// Check SO is being converted to Production Order already or not.
	//@Property\
	private boolean soConvert;
	

	public boolean isSoConvert() {
		soConvert=false;
		try
		{
			soConvert = false;
			if(_SalesOrderMaterial!=null)
			{
				//System.out.println("isSOConvert SODetail:" + _SalesOrderMaterial.toString() + ", " + _SalesOrderMaterial.getId());
				List<ProductionOrder> productionorders = getProductionOrderService().findProductionOrderBySalesOrderMaterialId(_SalesOrderMaterial.getId());
				//System.out.println("productionorders.size():" + productionorders.size());
				
				if(productionorders.size()!=0)
				{
					System.out.println("so convert 1");
					soConvert = false;						
				}
				else 
				{
					System.out.println("so convert 2");
					soConvert = true;
				}
			}
			
		}
		catch (DoesNotExistException de)
		{
			System.out.println("so convert 3");
			soConvert = true;			
		}
		catch (BusinessException be)
		{
			System.out.println("so convert 4");
			soConvert = true;			
		}
		System.out.println("so convert result: " + soConvert);
		return soConvert;
	}

	public void setSoConvert(boolean soConvert) {
		this.soConvert = soConvert;
	}

	void onActionFromtoolbarback()
    {
    	
    }
	@Inject
    private BeanModelSource beanModelSource;

	@Inject
    private ComponentResources resources;
	
	@SuppressWarnings("unchecked")
	@Property
	@Retain
	private BeanModel _salesOrderMaterialModel;

   /* public BeanModel getModel() {
        BeanModel model = beanModelSource.create(User.class, false, resources);
        model.add("delete", null);
        return model;
    }
	*/
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
	
	@Persist
	private String FormatedDocNo;
	public String getFormatedDocNo() {
		return FormatedDocNo;
	}

	public void setFormatedDocNo(String formatedDocNo) {
		FormatedDocNo = formatedDocNo;
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
	 @Component
	 @Property
	 private FilterDataSalesOrderMaterial _filterDataSalesOrderMaterial;
	 
	 @Persist
	 @Property
	 private SalesOrderMaterialSearchFields lowerSFs;
	 
	 @Persist
	 @Property
	 private SalesOrderMaterialSearchFields upperSFs;
	 
	void RefreshRecords()
	{
		RefreshRecords( new Delegate() 
		{ public List<SalesOrderMaterial> bindData() 
			{ 
			try { 
				//System.out.println("LowerFS:" + lowerSFs.toString());
				//System.out.println("upperSFs:" + upperSFs.toString());
				 if(lowerSFs==null&&upperSFs==null)
				 {
				   System.out.println("nothing. refresh normally");
				   return getSalesOrderService().findSalesOrderMaterialsBySalesOrderId(_headerIDLng);
				 }
				 else
				 {
                	SearchOptions options = new SearchOptions();
            	 
                	System.out.println("filtering. refresh with filter");
                	//return getSalesOrderService().findSalesOrdersMaterialBySearchFieldsRange(lower, upper, options)
            	    return getSalesOrderService().findSalesOrdersMaterialBySearchFieldsRange(_filterDataSalesOrderMaterial.getLowerSearchFields(), _filterDataSalesOrderMaterial.getUpperSearchFields(),options);
				 } 
			    } 
			catch(BusinessException be) {
				
				_form.recordError(be.getMessage());
			} 
			
			return null;} 
		    }
		);
	}

	void RefreshRecords(Delegate delegate)
	{
		
		
		if(_salesOrderMaterialModel==null){
			_salesOrderMaterialModel = beanModelSource.createDisplayModel(SalesOrderMaterial.class,resources.getMessages());
			_salesOrderMaterialModel.add("ConvertSO",null);
			_salesOrderMaterialModel.get("ConvertSO").label("Convert SO to WO");			
		}
		
		
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
    	   //_SalesOrderMaterials = getSalesOrderService().findSalesOrderMaterialsBySalesOrderId(_headerIDLng);
    	   _SalesOrderMaterials = (List<SalesOrderMaterial>) delegate.bindData();
    	   //(List<SalesOrder>)delegate.bindData();
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
    		_form.recordError(be.getMessage());
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
		
		//_form.recordError("Page having error.  Please select/Add record and save again.");
		return blockFormView;
	}
	Object onSuccessFromSalesOrderMaterialForm()
	{
		_form.clearErrors();
	   RefreshRecords();
	   return blockFormView;
	}
	
	
	void setupRender() {
		try
		{
			int_SelectedRow=0;
			SalesOrder salesorder = getSalesOrderService().findSalesOrder(_headerIDLng);
			if(salesorder!=null)
			{
				FormatedDocNo = salesorder.getFormattedDocNo();
			}
			
			RefreshRecords();
		}
		catch(Exception e)
		{
			_form.recordError(e.getMessage());
		}
	}
	
	
	void onValidateFormFromSalesOrderMaterialForm()  {
		
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
			   else
					if("F" == myState)
					{
						_FilterRecordSalesOrderMaterial();
					}
			}
		catch (Exception e)
		{
			_form.recordError(e.getMessage());
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
	   
	   
	   java.util.Date today = new java.util.Date();	   
	   salesOrderMaterial.setModifyApp(this.getClass().getSimpleName());
	   salesOrderMaterial.setModifyLogin(getVisit().getMyLoginId());       
	   salesOrderMaterial.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
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
        	
		    java.util.Date today = new java.util.Date();
		    salesOrderMaterial.setCreateApp(this.getClass().getSimpleName());
		    salesOrderMaterial.setCreateLogin(getVisit().getMyLoginId());
		    salesOrderMaterial.setCreateTimestamp(new java.sql.Timestamp(today.getTime()));
            assignToDatabase(salesOrderMaterial);     
            if(salesOrderMaterial.getQuantityShipped()>1)
            {
            	throw new Exception("Quantity Shipped must equal to 0 in record creation.");	
            }
            
            getSalesOrderService().addSalesOrderMaterial(_headerIDLng, salesOrderMaterial);
        }
    	catch (BusinessException e) {
    			_form.recordError(e.getMessage());
    	}  
    	catch (Exception e) {
    		_form.recordError(e.getMessage());
    	}
	}
	
	void _UpdateRecord()  {
        SalesOrderMaterial salesOrderMaterial = new SalesOrderMaterial();
        try
       {
            salesOrderMaterial = getSalesOrderService().findSalesOrderMaterial(id);
        }
    	catch(BusinessException be)
    	{
    		_form.recordError(be.getMessage());			
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
    			_form.recordError(e.getMessage());
    		}
    		catch (Exception e) {
    			_form.recordError(e.getMessage());
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
    		   _form.recordError(be.getMessage());
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
        	   _form.recordError(e.getMessage());
           }
    	   catch (Exception e) {
    		   _form.recordError(e.getMessage());
    	   }
    	}
	}
	void onActionFromConvertSOselect(Long id) throws BusinessException
	{
		try
		{
			RowInfo rowInfo = new RowInfo();
			System.out.println("clicked 2 ");
			SalesOrderMaterial salesordermaterialConvert = getSalesOrderService().findSalesOrderMaterial(id);
				
			java.util.Date today = new java.util.Date();	   
			rowInfo.setModifyApp(this.getClass().getSimpleName());
			System.out.println("clicked 3 ");
			rowInfo.setModifyLogin(getVisit().getMyLoginId());       
			rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
			rowInfo.setModifyApp(this.getClass().getSimpleName());
			System.out.println("clicked 4 ");
			rowInfo.setModifyLogin(getVisit().getMyLoginId());       
			rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
			rowInfo.setSessionId("");
			rowInfo.setRecordStatus("A");
			System.out.println("going to convert");
			getSalesOrderService().convertOrderMaterialToProductionOrder(rowInfo, salesordermaterialConvert);
			System.out.println("converted");
		}
		catch (Exception e)
		{
			System.out.println("Error onActionFromConvertSOselect erroro:" + e.getMessage());
			_form.recordError(e.getMessage());
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
            if(SalesOrderMaterialDetail!=null)
            {
                assignToLocalVariable(SalesOrderMaterialDetail);
                return blockFormView;
            }
        }
        catch(BusinessException be)
        {
        	_form.recordError(be.getMessage());
        }
        catch (Exception e)
        {
        	_form.recordError(e.getMessage());
        }
        
        
        return null;
	}
	
	private ISalesOrderServiceRemote getSalesOrderService() {
	   return getBusinessServicesLocator().getSalesOrderServiceRemote();
	}
	
	private IProductionOrderServiceRemote getProductionOrderService() {
		   return getBusinessServicesLocator().getProductionOrderServiceRemote();
		}
	
	private IMaterialServiceRemote getMaterialService() {
		return getBusinessServicesLocator().getMaterialServiceRemote();
	}
	
	
	public List<SalesOrderMaterial> getSalesOrderMaterials() {
		//beanModelSource.
		//_SalesOrderMaterials = beanModelSource.create(SalesOrderMaterial.class, true,"");
		//create(SalesOrderMaterial.class, "esting");
		//_SalesOrderMaterials = beanModelSource.create(SalesOrderMaterial.class, false, test);
	
		//_SalesOrderMaterials.
		//_SalesOrderMaterials.
		   return _SalesOrderMaterials;
	}
	
	
	public SalesOrderMaterial getSalesOrderMaterial() throws BusinessException{
		System.out.print("getSalesOrder()" + _SalesOrderMaterial.getId());
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
	
	public void onFilterDataSalesOrderMaterial()
	 {
	   //SalesOrderSearchFields lowerSearchFields = null;
	   //SalesOrderSearchFields upperSearchFields = null;
	   
	   System.out.println("Yes , trigger");
	   
	   lowerSFs = _filterDataSalesOrderMaterial.getLowerSearchFields();
	   upperSFs = _filterDataSalesOrderMaterial.getUpperSearchFields();
	   
	   System.out.println("lowerSearchFields DocNo: " + lowerSFs.getDocNo());
	   System.out.println("upperSearchFields DocNo: " + upperSFs.getDocNo());
	   //myState = "F";
	   //_strMode = "F";
	   
	   /*
	   RefreshRecords( new Delegate() 
	                      { 
		                   public List<SalesOrder> bindData() 
		                    { try 
		                       { 
		                    	 SearchOptions options = new SearchOptions();
		                    	 
		                    	 return getSalesOrderService().findSalesOrdersBySearchFieldsRange(_filterData.getLowerSearchFields(), _filterData.getUpperSearchFields(),options);
		                       } 
		                       catch(BusinessException be) {} 
		                       
		                       return null;
		                   } 
		                  });
	   		*/
	 }

	 void _FilterRecordSalesOrderMaterial()
	 {
		 System.out.println("Yes , _FilterRecord");
		   SalesOrderMaterialSearchFields lowerSearchFields = null;
		   SalesOrderMaterialSearchFields upperSearchFields = null;
		   			   
		   lowerSearchFields = _filterDataSalesOrderMaterial.getLowerSearchFields();
		   upperSearchFields = _filterDataSalesOrderMaterial.getUpperSearchFields();
		   
		  // System.out.println("_Filter : lowerSearchFields DocNo: " + lowerSearchFields.getDocNo());
		  // System.out.println("_Filter : upperSearchFields DocNo: " + upperSearchFields.getDocNo());

		 
	 }

	

}

