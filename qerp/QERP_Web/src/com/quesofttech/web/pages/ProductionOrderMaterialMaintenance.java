package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.exception.DuplicateAlternateKeyException;
import com.quesofttech.business.common.exception.DuplicatePrimaryKeyException;
import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.production.*;
import com.quesofttech.business.domain.production.iface.*;
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
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
import com.quesofttech.web.model.base.GenericSelectModel;
import com.quesofttech.business.domain.inventory.*;
import com.quesofttech.business.domain.inventory.iface.*;


public class ProductionOrderMaterialMaintenance extends SimpleBasePage {
	//===============================================================
	//			Material ComboBox
	//===============================================================
	void onActionFromtoolbarback()
    {
    	
    }
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<Material> _materials;	
	
	@Inject
    private PropertyAccess _access;
	/*
	public ProductionOrderMaterialMaintenance() throws BusinessException{
	  	List<Material> list = null;
    	try {
           list = this.getMaterialService().findMaterials();
           
    	}
    	catch (DoesNotExistException e) {}
    	
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
	
	
	private String _strMode = "";
	private ProductionOrderMaterial ProductionOrderMaterialDetail;
	private ProductionOrderMaterial _ProductionOrderMaterial;
	@Persist
	private List<ProductionOrderMaterial> _ProductionOrderMaterials;
	@Inject
	private Logger _logger;
	@Inject
	private Block blockFormView;
	@Persist
	private long lng_CurrentID;
	
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
	public String getProductionOrderDocNo()
	{
		String output = "";
		try
		{
			output = getProductionOrderService().findProductionOrder(_headerid).getFormattedDocNo();
		}
		catch (Exception e)
		{
			output = "N/A";			
		}
		return output;
	}
	public String getviewEditText()
	{
		refreshDisplay();
	     return viewEditText;
	}
	
	@Component(id = "ProductionOrderMaterialForm")
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
	// Text Component for QuantityRequired
	@Component(id = "QuantityRequired")
	private TextField _QuantityRequired;
	private double quantityRequired;
	public double getQuantityRequired()
	{
	   return quantityRequired;
	}
	
	public void setQuantityRequired(double quantityRequired)
	{
	   this.quantityRequired = quantityRequired;
	}
	//===============================
	
	//===============================
	// Text Component for QuantityConsumed
	@Component(id = "QuantityConsumed")
	private TextField _QuantityConsumed;
	private double quantityConsumed;
	public double getQuantityConsumed()
	{
	   return quantityConsumed;
	}
	
	public void setQuantityConsumed(double quantityConsumed)
	{
	   this.quantityConsumed = quantityConsumed;
	}
	//===============================
	
	//===============================
	// Text Component for version
	
	//===============================
	
	//===============================
	// Text Component for ProductionOrderOperation
	/*
	@Component(id = "ProductionOrderOperation")
	private TextField _ProductionOrderOperation;
	private Long ProductionOrderOperation;
	public Long getProductionOrderOperation()
	{
	   return ProductionOrderOperation;
	}
	
	public void setProductionOrderOperation(Long ProductionOrderOperation)
	{
	   this.ProductionOrderOperation = ProductionOrderOperation;
	}
	*/
	//===============================
	
	//===============================
	// Text Component for ProductionOrder
	
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
	}
	*/
	
	
	
	
	
	
	//===============================
	void RefreshRecords()
	{
		List<Material> list = null;
    	try {
           list = this.getMaterialService().findMaterials();
           
    	}
    	catch (DoesNotExistException e) {}
    	
        _materials = new GenericSelectModel<Material>(list,Material.class,"codeDescription","id",_access);
	
	   try
	   {
	       _ProductionOrderMaterials = getProductionOrderService().findProductionOrderMaterialsByProductionOrderId(_headerid);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	   if(_ProductionOrderMaterials!=null && !_ProductionOrderMaterials.isEmpty())
	   {
	       if(int_SelectedRow==0)
	       {
	           ProductionOrderMaterialDetail = _ProductionOrderMaterials.get(_ProductionOrderMaterials.size() - 1);
	       }
	       else
	       {
	           ProductionOrderMaterialDetail = _ProductionOrderMaterials.get(int_SelectedRow - 1);
	       }
	       myState="U";
	       //ProductionOrderMaterialDetail = _ProductionOrderMaterials.get(_ProductionOrderMaterials.size() - 1);
	       viewDisplayText="Block";
	       viewEditText="none";
	       assignToLocalVariable(ProductionOrderMaterialDetail);
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
	   _ProductionOrderMaterials = getProductionOrderService().findProductionOrderMaterialsByProductionOrderId(_headerid);
	   for(ProductionOrderMaterial p : _ProductionOrderMaterials)
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
				_form.recordError("Error Description: " + e.getMessage());
			}
	}
	
	void assignToDatabase(ProductionOrderMaterial productionOrderMaterial){
	   productionOrderMaterial.setId(id);
	   productionOrderMaterial.setQuantityRequired(quantityRequired);
	   productionOrderMaterial.setQuantityConsumed(quantityConsumed);
	   //productionOrderMaterial.setversion(version);
	   //productionOrderMaterial.)(ProductionOrderOperation);
	  // productionOrderMaterial.setProductionOrder(ProductionOrder);
	   productionOrderMaterial.setMaterial(material);
	   productionOrderMaterial.setRecordStatus("A");
	}
	void assignToLocalVariable(ProductionOrderMaterial productionOrderMaterial)
	{
	   this.id = productionOrderMaterial.getId();
	   this.quantityRequired = productionOrderMaterial.getQuantityRequired();
	   this.quantityConsumed = productionOrderMaterial.getQuantityConsumed();
	   //this.version = productionOrderMaterial.getversion();
	   //this.ProductionOrderOperation = productionOrderMaterial.getProductionOrderOperation();
	  // this.ProductionOrder = productionOrderMaterial.getProductionOrder();
	  this.material = productionOrderMaterial.getMaterial();
	}
	void _AddRecord()
	{
	   ProductionOrderMaterial productionOrderMaterial = new ProductionOrderMaterial();
	   try {
		   productionOrderMaterial.setCreateLogin(getVisit().getMyLoginId());
		   productionOrderMaterial.setModifyLogin(getVisit().getMyLoginId());
		   productionOrderMaterial.setCreateApp(this.getClass().getSimpleName());
		   productionOrderMaterial.setModifyApp(this.getClass().getSimpleName());		   
		   
	       assignToDatabase(productionOrderMaterial);
	       getProductionOrderService().addProductionOrderMaterial(_headerid,productionOrderMaterial);
	   }
		catch (BusinessException e) {
			if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
			{
				_form.recordError(e.getLocalizedMessage());
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
	   ProductionOrderMaterial productionOrderMaterial = new ProductionOrderMaterial();
	   try
	   {
	       productionOrderMaterial = getProductionOrderService().findProductionOrderMaterial(id);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	   if(productionOrderMaterial !=null)
	   {
	       try {
	    	   //productionOrderMaterial.setCreateLogin(getVisit().getMyLoginId());
			   productionOrderMaterial.setModifyLogin(getVisit().getMyLoginId());
			   productionOrderMaterial.setModifyApp(this.getClass().getSimpleName());	
	           assignToDatabase(productionOrderMaterial);
	           getProductionOrderService().updateProductionOrderMaterial(productionOrderMaterial);
	       }
			catch (BusinessException e) {
				if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
				{
					_form.recordError(e.getLocalizedMessage());
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
	   ProductionOrderMaterial productionOrderMaterial = new ProductionOrderMaterial();
	   try
	   {
	       productionOrderMaterial = getProductionOrderService().findProductionOrderMaterial(id);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	   if(productionOrderMaterial!=null)
	   {
	       try {
	    	   //productionOrderMaterial.setCreateLogin(getVisit().getMyLoginId());
			   productionOrderMaterial.setModifyLogin(getVisit().getMyLoginId());
	           getProductionOrderService().logicalDeleteProductionOrderMaterial(productionOrderMaterial);
	           if(int_SelectedRow!=0)
	           {
	               int_SelectedRow--;
	           }
	           RefreshRecords();
	       }
	       catch (BusinessException e) {
	           _form.recordError(e.getLocalizedMessage());
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
	       ProductionOrderMaterialDetail = getProductionOrderService().findProductionOrderMaterial(id);
	       int_SelectedRow = getRcdLocation(id);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	
	   if(ProductionOrderMaterialDetail!=null)
	   {
	       viewDisplayText="Block";
	       viewEditText="none";
	       assignToLocalVariable(ProductionOrderMaterialDetail);
	       return blockFormView;
	   }
	   return null;
	}
	
	private IProductionOrderServiceRemote getProductionOrderService() {
	   return getBusinessServicesLocator().getProductionOrderServiceRemote();
	}
	
	
	public List<ProductionOrderMaterial> getProductionOrderMaterials() {
	   return _ProductionOrderMaterials;
	}
	
	
	public ProductionOrderMaterial getProductionOrderMaterial() throws BusinessException{
	   return _ProductionOrderMaterial;
	}
	
	
	 public void setProductionOrderMaterial(ProductionOrderMaterial tb) {
	   _ProductionOrderMaterial = tb;
	}
	 @Persist
	 private Long _headerid;
	 @Persist
	 private long _headerCode;
	 public void setHeaderCode(Long _value)
	 {
		 System.out.println("headercode:" + _value);
		 _headerCode = _value;
	 }
	 public Long getheadercode()
	 {
		 System.out.println("ID:" + _headerid);
		 if(_headerid!=null)
		 {
			 try
			 {
				 _headerCode = getProductionOrderService().findProductionOrder(_headerid).getDocNo();
				 System.out.println("HeaderCode" + _headerCode);
			 }
			 catch(Exception e)
			 {
				 _form.recordError(e.getMessage());
			 }
			 
			 
		 }
		// if(_headerCode==null)
		// {
		//	 _headerCode = "N/A";
		// }
		 return _headerCode;
		 
	 }
	 
	 public void SetHeaderID(Long id)
	 {
		 //this._headerCode = _headerCode;
		 this.id = id;
		 _headerid = id;
		// System.out.println("salesheader 's id:" + id);
	 }

}
