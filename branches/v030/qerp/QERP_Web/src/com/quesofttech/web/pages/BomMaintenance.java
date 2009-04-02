package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.base.SecureBasePage;
import com.quesofttech.web.model.base.GenericSelectModel;
import com.quesofttech.web.state.Visit;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.domain.general.BOM;
import com.quesoware.business.domain.general.iface.IBomServiceRemote;
import com.quesoware.business.domain.inventory.Material;
import com.quesoware.business.domain.inventory.iface.IMaterialServiceRemote;
import com.quesoware.business.domain.production.ProductionOrder;
import com.quesoware.business.domain.sales.SalesOrder;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;

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
public class BomMaintenance extends SecureBasePage {
// Default defination.
	
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
    private String _strMode = "";
    private BOM BOMDetail;
    private BOM _BOM;
    @Persist
    private List<BOM> _BOMs;
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
    	refreshDisplay();
     return viewDisplayText;
    }

    public String getviewEditText()
    {
    	refreshDisplay();
         return viewEditText;
    }

    @Component(id = "BOMForm")
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
    // Text Component for Type
    /*@Component(id = "Type")
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
    
    */
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
    // Text Component for Material
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

	public String getMaterialCode()
	{
		String _temp = "";
		if (material!=null)
			_temp = material.getCodeDescription();
		return _temp;
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
	
    //===============================
	@Inject
    private BeanModelSource beanModelSource;

	@Inject
    private ComponentResources modelResources;
	
	@SuppressWarnings("unchecked")
	@Property
	@Retain
	private BeanModel _bomModel;
	void ModelRefresh()
	{
		if(_bomModel==null){
			_bomModel = beanModelSource.createDisplayModel(BOM.class,modelResources.getMessages());
			_bomModel.add("bomDetailSelect",null);
			_bomModel.get("bomDetailSelect").label("Detail");
			//_salesOrderMaterialModel.
//			_salesOrderMaterialModel.include(//arg0)
			
		}
	}
    void RefreshRecords()
    {    	
    	ModelRefresh();
    	List<Material> list = null;	  	
    	try {    		
    		list = this.getMaterialService().findProducedMaterials();           
    	}
    	catch (DoesNotExistException e) {}    	
    	_materials = null;
    	_materials = new GenericSelectModel<Material>(list,Material.class,"codeDescription","id",_access);
		// ComboBox Refresh
       try
       {
           _BOMs = getBOMService().findBOMs();
       }
       catch(BusinessException be)
       {

       }
       if(_BOMs!=null && !_BOMs.isEmpty())
       {
           if(int_SelectedRow==0)
           {
               BOMDetail = _BOMs.get(_BOMs.size() - 1);
           }
           else
           {
               BOMDetail = _BOMs.get(int_SelectedRow - 1);
           }
           //BOMDetail = _BOMs.get(_BOMs.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(BOMDetail);
       }
       else
       {
    	   myState="A"; // If no List then should be in A mode instead of Update mode.
       }
    }
    @InjectPage
	 private BomDetailMaintenance detailPage;
	 Object onActionFrombtnDetail(Long id)
	 {
		 try
		 {
			 _form.clearErrors();
			// System.out.println("Number: " + this.Number);
			 //detailPage.setHeaderCode(this.Number);
			  detailPage.setHeaderID(id);
			return detailPage;
		 }
		 catch (Exception e)
		 {
			 _form.recordError(getMessages().get(e.getMessage()));
			 return null;
		 }
	 }
    
    
    private int getRcdLocation( Long id)  throws BusinessException
    {
      int int_return ,int_i;
      int_i = 0;
      int_return = 0;
      for(BOM p : _BOMs)
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

    void assignToDatabase(BOM bom){
       bom.setId(id);
       //bom.setType(Type);
       bom.setType("P"); // BOM always is P (Production type).
       bom.setCode(Code);
       bom.setMaterial(material);
       bom.setRecordStatus("A");
       java.util.Date today = new java.util.Date();	   
	   bom.setModifyApp(this.getClass().getSimpleName());
	   bom.setModifyLogin(getVisit().getMyLoginId());       
	   bom.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
       
    }
    void assignToLocalVariable(BOM bom)
    {
       this.id = bom.getId();
       //this.Type = bom.getType();
       this.material = bom.getMaterial();
       this.Code = bom.getCode();
    }
    void _AddRecord()
    {
       BOM bom = new BOM();
       try {
	    	   java.util.Date today = new java.util.Date();	   
	    	   bom.setCreateApp(this.getClass().getSimpleName());
	    	   bom.setCreateLogin(getVisit().getMyLoginId());       
	    	   bom.setCreateTimestamp(new java.sql.Timestamp(today.getTime()));
               
           assignToDatabase(bom);
           getBOMService().addBOM(bom);
       }
       catch (Exception e) {
           _logger.info("BOM_Add_problem");
           e.printStackTrace();
           _form.recordError(getMessages().get("BOM_add_problem"));
       }
    }

    void _UpdateRecord(){
       BOM bom = new BOM();
       try
       {
           bom = getBOMService().findBOM(id);
       }
       catch(BusinessException be)
       {

       }
       if(bom !=null)
       {
           try {
               bom.setModifyLogin(getVisit().getMyLoginId());
               assignToDatabase(bom);
               getBOMService().updateBOM(bom);
           }
           catch (BusinessException e) {
               _form.recordError(_id, e.getLocalizedMessage());
           }
       catch (Exception e) {
               _logger.info("BOM_update_problem");
               e.printStackTrace();
               _form.recordError(getMessages().get("BOM_update_problem"));
           }
       }
    }


    void _DeleteRecord(Long id) {
       BOM bom = new BOM();
       try
       {
           bom = getBOMService().findBOM(id);
       }
       catch(BusinessException be)
       {

       }
       if(bom!=null)
       {
           try {
               bom.setModifyLogin(getVisit().getMyLoginId());
               getBOMService().logicalDeleteBOM(bom);
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
               _logger.info("BOM_Delete_problem");
               e.printStackTrace();
               _form.recordError(getMessages().get("BOM_Delete_problem"));
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
           BOMDetail = getBOMService().findBOM(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {

       }

       if(BOMDetail!=null){
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(BOMDetail);
           return blockFormView;
       }
       return null;
    }

    private IBomServiceRemote getBOMService() {
       return getBusinessServicesLocator().getBOMServiceRemote();
    }


    public List<BOM> getBOMs() {
       return _BOMs;
    }


    public BOM getBOM() throws BusinessException{
       return _BOM;
    }


     public void setBOM(BOM tb) {
       _BOM = tb;
    }

}
