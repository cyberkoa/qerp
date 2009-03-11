package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.exception.DuplicateAlternateKeyException;
import com.quesofttech.business.common.exception.DuplicatePrimaryKeyException;
import com.quesofttech.business.domain.inventory.*;
import com.quesofttech.business.domain.inventory.iface.*;
import com.quesofttech.business.domain.sales.*;
import com.quesofttech.business.domain.sales.iface.*;
import com.quesofttech.business.domain.production.ProductionOrder;
import com.quesofttech.business.domain.production.iface.IProductionOrderServiceRemote;
import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.base.SecureBasePage;
import com.quesofttech.web.model.base.GenericSelectModel;
import com.quesofttech.web.state.Visit;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
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
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
import org.apache.tapestry5.beaneditor.BeanModel;
public class ProductionOrderMaintenance extends SecureBasePage {

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
	

	@Inject
    private BeanModelSource beanModelSource;

	@Inject
    private ComponentResources modelResources;
	
	@SuppressWarnings("unchecked")
	@Property
	@Retain
	private BeanModel _productOrderModel;
	
	
	void ModelRefresh()
	{
		if(_productOrderModel==null){
		_productOrderModel = beanModelSource.createDisplayModel(ProductionOrder.class,modelResources.getMessages());
		_productOrderModel.add("ProductionOrderDetailSelect",null);
		_productOrderModel.get("ProductionOrderDetailSelect").label("Detail");
			//_salesOrderMaterialModel.
//			_salesOrderMaterialModel.include(//arg0)
			
		}
	}
	//===============================================================
	//			Material ComboBox
	//===============================================================
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<Material> _materials;	
	
	@Inject
    private PropertyAccess _access;
	/*
	public ProductionOrderMaintenance() throws BusinessException{
	  	List<Material> list = null;
	  	List<SalesOrder> list_so = null;
    	try {    		
    		list = this.getMaterialService().findMaterials();           
    	}
    	catch (DoesNotExistException e) {}
    	try{
    		list_so = this.getSalesOrderService().findSalesOrders();
    	}
    	catch (DoesNotExistException e){}
    	
    	_salesorders = new GenericSelectModel<SalesOrder>(list_so,SalesOrder.class,"docNo","id",_access1);
        _materials = new GenericSelectModel<Material>(list,Material.class,"code","id",_access);
	}*/
	private IMaterialServiceRemote getMaterialService() {
		return getBusinessServicesLocator().getMaterialServiceRemote();
	}

	
	private Material material;
	public Material getMaterial()
	{
	   return material;
	}

	public void setMaterial(Material material)
	{
	   this.material = material;
	}
	//===============================================================
	//				Material ComboBox
	//===============================================================
	
	
	//===============================================================
	//			SalesOrder ComboBox
	//===============================================================
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<SalesOrderMaterial> _salesordermaterials;	
	
	@Inject
    private PropertyAccess _access1;
	
	private ISalesOrderServiceRemote getSalesOrderService() {
		return getBusinessServicesLocator().getSalesOrderServiceRemote();
	}

	
	private SalesOrderMaterial salesordermaterial;
	
	//===============================================================
	//				SalesOrder ComboBox
	//===============================================================
	
	
	
	
	
	
	
	public SalesOrderMaterial getSalesordermaterial() {
		return salesordermaterial;
	}

	public void setSalesordermaterial(SalesOrderMaterial salesordermaterial) {
		this.salesordermaterial = salesordermaterial;
	}

	Object onFailure()
	{
		//System.out.println("onFailure lah");
		_form.recordError("Page having error.  Please select/Add record and save again.");
		return blockFormView;
	}
	
	private String _strMode = "";
	private ProductionOrder ProductionOrderDetail;
	private ProductionOrder _ProductionOrder;
	@Persist
	private List<ProductionOrder> _ProductionOrders;
	@Inject
	private Logger _logger;
	@Inject
	private Block blockFormView;
	@Persist
	private long lng_CurrentID;
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
	
	@Component(id = "ProductionOrderForm")
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
	// Text Component for Number
	@Component(id = "Number")
	private TextField _Number;
	private Long Number;
	public Long getNumber()
	{
	   return Number;
	}
	
	public void setNumber(Long Number)
	{
	   this.Number = Number;
	}
	//===============================
	
	//===============================
	// Text Component for QuantityOrder
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
	//===============================
	
	//===============================
	// Text Component for QuantityReported
	@Component(id = "QuantityReported")
	private TextField _QuantityReported;
	private Double QuantityReported;
	public Double getQuantityReported()
	{
	   return QuantityReported;
	}
	
	public void setQuantityReported(Double QuantityReported)
	{
	   this.QuantityReported = QuantityReported;
	}
	//===============================
	
	//===============================
	// Text Component for version
	
	//===============================
	
	//===============================
	// Text Component for Material
	/*@Component(id = "Material")
	private TextField _Material;
	private Long Material;
	public Long getMaterial()
	{
	   return Material;
	}
	
	public void setMaterial(Long Material)
	{
	   this.Material = Material;
	}*/
	//===============================
	
	//===============================
	// Text Component for SalesOrder
	/*@Component(id = "SalesOrder")
	private TextField _SalesOrder;
	private Long SalesOrder;
	public Long getSalesOrder()
	{
	   return SalesOrder;
	}
	
	public void setSalesOrder(Long SalesOrder)
	{
	   this.SalesOrder = SalesOrder;
	}*/
	//===============================
	void RefreshRecords()
	{
		ModelRefresh();
		 // ComboBox Refresh	
		List<Material> list = null;
	  	List<SalesOrderMaterial> list_so = null;
    	try {    		
    		list = this.getMaterialService().findProducedMaterials();           
    	}
    	catch (DoesNotExistException e) {}
    	try{
    		list_so = this.getSalesOrderService().findSalesOrderMaterials();
    	}
    	catch (DoesNotExistException e){}
    	_salesordermaterials = null;
    	_materials = null;
    	_salesordermaterials = new GenericSelectModel<SalesOrderMaterial>(list_so,SalesOrderMaterial.class,"FormattedDocNoWithLine","id",_access1);
        _materials = new GenericSelectModel<Material>(list,Material.class,"codeDescription","id",_access);
		// ComboBox Refresh
	   try
	   {
	       _ProductionOrders = getProductionOrderService().findProductionOrders();
	   }
	   catch(BusinessException be)
	   {
	
	   }
	   if(_ProductionOrders!=null && !_ProductionOrders.isEmpty())
	   {
		   if(int_SelectedRow==0)
			{
			   ProductionOrderDetail = _ProductionOrders.get(_ProductionOrders.size() - 1);
			}
			else
			{
				ProductionOrderDetail = _ProductionOrders.get(int_SelectedRow - 1);
				
			}	
		   myState="U";
	      // ProductionOrderMaterial = _ProductionOrders.get(_ProductionOrders.size() - 1);
	       viewDisplayText="Block";
	       viewEditText="none";
	       assignToLocalVariable(ProductionOrderDetail);
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
	   _ProductionOrders = getProductionOrderService().findProductionOrders();
	   for(ProductionOrder p : _ProductionOrders)
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
	
	
	void setupRender() {
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
	
	void assignToDatabase(ProductionOrder productionOrder){
	   productionOrder.setId(id);
	   productionOrder.setDocNo(Number);
	   productionOrder.setQuantityOrder(QuantityOrder);
	   productionOrder.setQuantityReported(QuantityReported);
	  // productionOrder.setversion(version);
	   productionOrder.setMaterial(material);
	   productionOrder.setSalesOrderMaterial(salesordermaterial);
	   productionOrder.setSalesOrder(salesordermaterial.getSalesOrder());
	   productionOrder.setRecordStatus("A");

	}
	void assignToLocalVariable(ProductionOrder productionOrder)
	{
	   this.id = productionOrder.getId();
	   //this.Number = productionOrder.getNumber();
	   this.Number = productionOrder.getDocNo();
	   this.QuantityOrder = productionOrder.getQuantityOrder();
	   this.QuantityReported = productionOrder.getQuantityReported();
	   //this.version = productionOrder.getversion();
	   this.material = productionOrder.getMaterial();
	   //this.salesorder = productionOrder.getSalesOrderMaterial().getSalesOrder();
	   this.salesordermaterial = productionOrder.getSalesOrderMaterial();
	}
	void _AddRecord()
	{
	   ProductionOrder productionOrder = new ProductionOrder();
	   try {
		   productionOrder.setCreateLogin(getVisit().getMyLoginId());
		   productionOrder.setModifyLogin(getVisit().getMyLoginId());
		   productionOrder.setCreateApp(this.getClass().getSimpleName());
		   productionOrder.setModifyApp(this.getClass().getSimpleName());
		   
	       assignToDatabase(productionOrder);
	       getProductionOrderService().addProductionOrder(productionOrder);
	   }
		catch (BusinessException e) {
			if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
			{
				_form.recordError(_Number, e.getLocalizedMessage());
			}
			else
				_form.recordError(e.getLocalizedMessage());
		}  
		catch (Exception e) {
			   _logger.info("Record_Add_Error");
			   e.printStackTrace();
			   _form.recordError(getMessages().get("Record_Add_Error"));
		}
	}
	
	void _UpdateRecord(){
	   ProductionOrder productionOrder = new ProductionOrder();
	   try
	   {
	       productionOrder = getProductionOrderService().findProductionOrder(id);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	   if(productionOrder !=null)
	   {
	       try {
	    	   //productionOrder.setCreateLogin(getVisit().getMyLoginId());
			   productionOrder.setModifyLogin(getVisit().getMyLoginId());
			   productionOrder.setModifyApp(this.getClass().getSimpleName());
				
	           assignToDatabase(productionOrder);
	           getProductionOrderService().updateProductionOrder(productionOrder);
	       }
			catch (BusinessException e) {
				if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
				{
					_form.recordError(_Number, e.getLocalizedMessage());
				}
				else	
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
	   ProductionOrder productionOrder = new ProductionOrder();
	   try
	   {
	       productionOrder = getProductionOrderService().findProductionOrder(id);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	   if(productionOrder!=null)
	   {
	       try {
	    	   //productionOrder.setCreateLogin(getVisit().getMyLoginId());
			   productionOrder.setModifyLogin(getVisit().getMyLoginId());
			   productionOrder.setModifyApp(this.getClass().getSimpleName());
			   
	           getProductionOrderService().logicalDeleteProductionOrder(productionOrder);
	           if(int_SelectedRow!=0)
	           {
	               int_SelectedRow--;
	           }
	           RefreshRecords();
	       }
	       catch (BusinessException e) {
	           _form.recordError(_Number, e.getLocalizedMessage());
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
	       ProductionOrderDetail = getProductionOrderService().findProductionOrder(id);
	       int_SelectedRow = getRcdLocation(id);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	
	   if(ProductionOrderDetail!=null)
	   {
	       viewDisplayText="Block";
	       viewEditText="none";
	       assignToLocalVariable(ProductionOrderDetail);
	       return blockFormView;
	   }
	   return null;
	}
	
	private IProductionOrderServiceRemote getProductionOrderService() {
	   return getBusinessServicesLocator().getProductionOrderServiceRemote(); 
	}
	

	
	 @InjectPage
	 private ProductionOrderMaterialMaintenance detailPage;
	 Object onActionFrombtnDetail(Long id)
	 {
		 _form.clearErrors();
		 //System.out.println("Number: " + this.Number);
		 //detailPage.setHeaderCode(this.Number);
		 detailPage.SetHeaderID(id);
	    return detailPage;
	 }

	
	
	public List<ProductionOrder> getProductionOrders() {
	   return _ProductionOrders;
	}
	
	
	public ProductionOrder getProductionOrder() throws BusinessException{
	   return _ProductionOrder;
	}
	
	
	 public void setProductionOrder(ProductionOrder tb) {
	   _ProductionOrder = tb;
	}

}
