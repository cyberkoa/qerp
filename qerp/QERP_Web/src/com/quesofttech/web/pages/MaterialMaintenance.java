package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;


import com.quesofttech.web.common.ContextFixer;
import com.quesofttech.web.components.FilterData;
import com.quesofttech.web.components.FilterDataMaterial;
import com.quesofttech.web.components.QERPWindow;
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
import org.apache.tapestry5.corelib.components.Select;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;

import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;


import com.quesofttech.web.model.base.GenericSelectModel;
//import com.quesoware.web.model.MaterialTypeSelectModel;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.common.exception.DuplicateAlternateKeyException;
import com.quesoware.business.common.exception.DuplicatePrimaryKeyException;
import com.quesoware.business.common.query.SearchOptions;
import com.quesoware.business.domain.general.UOM;
import com.quesoware.business.domain.general.iface.IUomServiceRemote;
import com.quesoware.business.domain.inventory.*;
import com.quesoware.business.domain.inventory.dto.MaterialSearchFields;
import com.quesoware.business.domain.inventory.iface.*;
import com.quesoware.business.domain.sales.SalesOrder;
import com.quesoware.business.domain.sales.dto.SalesOrderSearchFields;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;



public class MaterialMaintenance extends SecureBasePage {
	
	//===============Filter window=========================
	@Component(parameters = {"width=500", "height=300", 
			 "style=bluelighting", "show=false","modal=true", "title=literal:Filter Window"})
	@Property
	private QERPWindow _filterWindow;
	
	@Component
	@Property
	private FilterDataMaterial _filterDataMaterial;
	 
	 
	 @Persist
	 @Property
	 private MaterialSearchFields lowerSFs;
	 
	 @Persist
	 @Property
	 private MaterialSearchFields upperSFs;
	 
	 public void onFilterDataMaterial()
	 {
	   lowerSFs = _filterDataMaterial.getLowerSearchFields();
	   upperSFs = _filterDataMaterial.getUpperSearchFields();	   
	 }

	 void _FilterRecord()
	 {		
		   //MaterialSearchFields lowerSearchFields = null;
		   //MaterialSearchFields upperSearchFields = null;
		   			   
		   lowerSFs = _filterDataMaterial.getLowerSearchFields();
		   upperSFs = _filterDataMaterial.getUpperSearchFields();
	 }
	 
	
	
	
	//===============End of Filter Window==================
	
	
	
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

	Object onFailure()
	{
		_form.clearErrors();		
		_form.recordError(getMessages().get("Record_Save_Error"));
		return blockFormView;
	}
	private String _strMode = "";

	private String viewDisplayText="", viewEditText="";
	

	public MaterialMaintenance() {

    	
        
	}
	
	
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

	private Material MaterialDetail;
	private Material _Material;
	
	@Persist
	private List<Material> _Materials;
	
	@Inject
	private Logger _logger;
	
	@Inject
	private Block blockFormView;
	
	//@Persist
	//private long lng_CurrentID;
	
	@Component(id = "MaterialForm")
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
	//@Component(id = "UOM")
	//private TextField _uom;
	//private String uom;
	/*public String getUOM()
	{
		return uom;
	}
	public void setUOM(String uom)
	{
		this.uom = uom;
	}
*/
	@Component(id = "Grade")
	private TextField _grade;
	private String grade;
	public String getGrade()
	{
		return grade;
	}
	public void setGrade(String grade)
	{
		this.grade = grade;
	}
	
	@Component(id = "Width")
	private TextField _width;
	private double width;
	public double getWidth()
	{
		return width;
	}
	public void setWidth(double width)
	{
		this.width = width;
	}
	
	
	@Component(id = "Length")
	private TextField _length;
	
	private double length;
	public double getLength()
	{
		return length;
	}
	public void setLength(double length)
	{
		this.length = length;
	}
	
	@Component(id = "Height")
	private TextField _height;
	
	private double height;
	public double getHeight()
	{
		return height;
	}
	public void setHeight(double height)
	{
		this.height = height;
	}
	
	
	
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
	
	
	
	
	//===============================================================
	//			Material Group ComboBox
	//===============================================================
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<MaterialGroup> materialGroupsSelect;	
	
	//@Inject
    //private PropertyAccess _access;
	
	private IMaterialGroupServiceRemote getMaterialGroupService() {
		return getBusinessServicesLocator().getMaterialGroupServiceRemote();
	}

	
	private MaterialGroup materialgroup;
	public MaterialGroup getMaterialGroup()
	{
	   return materialgroup;
	}

	public void setMaterialGroup(MaterialGroup materialgroup)
	{
	   this.materialgroup = materialgroup;
	}
	//===============================================================
	//				Material Group ComboBox
	//===============================================================
	
	
	//===============================================================
	//			MaterialType ComboBox
	//===============================================================
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<MaterialType> materialTypesSelect;	
	
	@Inject
    private PropertyAccess _access;
	
	private IMaterialTypeServiceRemote getMaterialTypeService() {
		return getBusinessServicesLocator().getMaterialTypeServiceRemote();
	}

	
	private MaterialType materialtype;
	public MaterialType getMaterialType()
	{
	   return materialtype;
	}

	public void setMaterialType(MaterialType materialtype)
	{
	   this.materialtype = materialtype;
	}
	//===============================================================
	//				Material ComboBox
	//===============================================================
	

	//===============================================================
	//			UOM ComboBox
	//===============================================================
	@Property
	@Persist
	private GenericSelectModel<UOM> uomsSelect;	
	
	//@Inject
    //private PropertyAccess _access_uom;
	
	
	private IUomServiceRemote getUOMService(){
		return getBusinessServicesLocator().getUOMServiceRemote();
	}
	
	@Property
	private UOM uom;
	/*
	public UnitMeasurement getUOM()
	{
	   return uom;
	}

	public void setUOM(UnitMeasurement uom)
	{
	   this.uom = uom;
	}*/
	//===============================================================
	//				Material ComboBox
	//===============================================================
	
	
	//@Component(id = "materialType")
	//private Select _MaterialType;
	void RefreshRecords() throws Exception
	{
		RefreshRecords( new Delegate() 
		{ public List<Material> bindData() 
			{ 
			try { 
				 if(lowerSFs==null&&upperSFs==null)
				   return getMaterialService().findMaterials();
				 else
				 {
                	SearchOptions options = new SearchOptions();
            	 
            	    return getMaterialService().findMaterialBySearchFieldsRange(_filterDataMaterial.getLowerSearchFields(), _filterDataMaterial.getUpperSearchFields(),options);
				 } 
			    } 
			catch(BusinessException be) {} 
			
			return null;} 
		    }
		);
		
	}
	
	
	void RefreshRecords(Delegate delegate) throws BusinessException
	{
		List<MaterialType> materialTypes = null;
		List<UOM> uoms = null;
		List<MaterialGroup> materialGroups = null;
		
		try {
	           materialGroups = this.getMaterialGroupService().findMaterialGroups();
	    	}
	    	catch (DoesNotExistException e) {
	    		_form.recordError(e.getMessage());
	    	}
    	try {
           materialTypes = this.getMaterialTypeService().findMaterialTypes();
    	}
    	catch (DoesNotExistException e) {
    		_form.recordError(e.getMessage());
    	}
       	try {       
           uoms = this.getUOMService().findUOMs();
    	}
    	catch (DoesNotExistException e) {
    		_form.recordError(e.getMessage());
    	}
    	    	
    	 	
    	uomsSelect = null;
    	materialTypesSelect = null;
    	materialGroupsSelect = null;
    	
    	
		uomsSelect = new GenericSelectModel<UOM>(uoms,UOM.class,"shortForm","id",_access);
		materialTypesSelect = new GenericSelectModel<MaterialType>(materialTypes,MaterialType.class,"typeDescription","id",_access);
		materialGroupsSelect = new GenericSelectModel<MaterialGroup>(materialGroups,MaterialGroup.class,"CodeDescription","id",_access);
		
		try
		{
			
		   _Materials = (List<Material>)delegate.bindData();
		}
		catch(Exception be)
		{
			_form.recordError(be.getMessage());
		}
		
		if(_Materials!=null && !_Materials.isEmpty())
		{
			
			if(int_SelectedRow==0)
			{
				MaterialDetail = _Materials.get(_Materials.size() - 1);
			}
			else
			{
				MaterialDetail = _Materials.get(int_SelectedRow - 1);
				
			}	
			myState="U";
			viewDisplayText="Block";
			viewEditText="none";
			assignToLocalVariable(MaterialDetail);
		   
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
	   _Materials = getMaterialService().findMaterials();
	   for(Material p : _Materials)
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
	
	
	Object onSuccessFromMaterialForm() throws BusinessException
	{
		try
		{
		   RefreshRecords();		   
		}
		catch (Exception e) {
			   _logger.info("Record_Refresh_Error");
			   e.printStackTrace();
			   _form.recordError(getMessages().get("Record_Refresh_Error"));
			}
		return blockFormView;
		
	}
	
	
	void setupRender() throws BusinessException{
		
		try{
			int_SelectedRow=0;
			
			RefreshRecords();
		}
		catch (Exception e) {
			   _logger.info("Record_Load_Error");
			   e.printStackTrace();
			   _form.recordError(getMessages().get("Record_Load_Error"));
			}
	}
	
	
	void onValidateFormFromMaterialForm() throws BusinessException{
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
					_FilterRecord();
				}
			}
		catch (BusinessException e) {
			if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
			{
				_form.recordError(_Code, e.getLocalizedMessage());
			}
				
			_form.recordError(e.getLocalizedMessage());
		}
		catch (Exception e)
		{
			_form.recordError(getMessages().get("Record_Update_Error"));
		}
	}
	
	void assignToDatabase(Material Material){
	   Material.setId(id);
	   Material.setCode(Code);
	   Material.setDescription(Description);
	   //Material.setversion(version);
	   Material.setMaterialType(materialtype);
	   Material.setMaterialGroup(materialgroup);
	   Material.setGrade(grade);
	   Material.setBaseUOM(uom);
	   Material.getDimension().setHeight(height);
	   Material.getDimension().setLength(length);
	   Material.getDimension().setWidth(width);	   
	   Material.setRecordStatus("A");	
	   
	   java.util.Date today = new java.util.Date();	   
	   Material.setModifyApp(this.getClass().getSimpleName());
	   Material.setModifyLogin(getVisit().getMyLoginId());       
	   Material.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	}
	
	void assignToLocalVariable(Material Material)
	{
	   this.id = Material.getId();
	   this.Code = Material.getCode();
	   this.Description = Material.getDescription();
	   this.materialgroup = Material.getMaterialGroup();
	   //this.version = Material.getversion();
	  this.materialtype = Material.getMaterialType();
	  this.height = Material.getDimension().getHeight();
	  this.width = Material.getDimension().getWidth();
	  this.length = Material.getDimension().getLength();
	  this.grade = Material.getGrade();
	  this.uom = Material.getBaseUOM();
	}
	
	void _AddRecord ()
	{
		Material material = new Material();
		try {
			java.util.Date today = new java.util.Date();
			material.setCreateApp(this.getClass().getSimpleName());
            material.setCreateLogin(getVisit().getMyLoginId());
            material.setCreateTimestamp(new java.sql.Timestamp(today.getTime()));
			
			
			
		   assignToDatabase(material);
		   getMaterialService().addMaterial(material);
		   }
		catch (BusinessException e) {
			if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
			{
				_form.recordError(_Code, e.getLocalizedMessage());
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
	
	void _UpdateRecord() throws BusinessException{
			Material material = new Material();
			try
			{
			   material = getMaterialService().findMaterial(id);
			}
			catch(BusinessException be)
			{
			   _logger.info("Record_Deleted_By_Others");
			   be.printStackTrace();
			   _form.recordError(getMessages().get("Record_Deleted_By_Others"));			
			}
			if(material !=null)
			{
			try {
				material.setModifyLogin(getVisit().getMyLoginId());
				material.setModifyApp(this.getClass().getSimpleName());
				
				assignToDatabase(material);
				System.out.println("here already");
				getMaterialService().updateMaterial(material);
				System.out.println("finished");
			}
			catch (BusinessException e) {
				if(e instanceof DuplicatePrimaryKeyException || e instanceof DuplicateAlternateKeyException)
				{
					_form.recordError(_Code, e.getLocalizedMessage());
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
	
	
	void _DeleteRecord(Long id) //throws BusinessException 
	{
		Material material = new Material();
		try
		{
		   material = getMaterialService().findMaterial(id);
		}
		catch(BusinessException be)
		{
			_logger.info("Record_Deleted_By_Others");
			   be.printStackTrace();
			   _form.recordError(getMessages().get("Record_Deleted_By_Others"));
		}
		if(material!=null)
		{
		   try {
			  int_SelectedRow=getRcdLocation(id);
			  material.setModifyLogin(getVisit().getMyLoginId());
			  material.setModifyApp(this.getClass().getSimpleName());
			  
			  getMaterialService().logicalDeleteMaterial(material);
			  if(int_SelectedRow!=0)
			  {
				  int_SelectedRow--;
			  }		  
			  RefreshRecords();
			}
			//catch (Exception e) {
		    catch (BusinessException e) {
			   _form.recordError(_Code, e.getLocalizedMessage());
			}
			catch (Exception e) {
			   _logger.info("Record_Delete_Error");
			   e.printStackTrace();
			   _form.recordError(getMessages().get("Record_Delete_Error"));
			}
		}
	}
	
	void onActionFromtoolbarDel(Long id) //throws BusinessException
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
	   //lng_CurrentID = id;
	   try
	   {
	       MaterialDetail = getMaterialService().findMaterial(id);
	       int_SelectedRow = getRcdLocation(id);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	
	if(MaterialDetail!=null)
	{
		assignToLocalVariable(MaterialDetail);
	
		viewDisplayText="Block";
		viewEditText="none";
		return blockFormView;
	}
		return null;
	}
	
	private IMaterialServiceRemote getMaterialService() {
	   return getBusinessServicesLocator().getMaterialServiceRemote();
	}
	
	public List<Material> getMaterials() {
	   return _Materials;
	}
	
	
	public Material getMaterial() throws BusinessException{
	   return _Material;
	}
	
	
	 public void setMaterial(Material tb) {
	   _Material = tb;
	}
	

	
	/*
	void onActivate(Object[] context) {
		
		
		System.out.println(context);
		if(context.length > 0)
		{
			Long materialTypeId = ContextFixer.unencodeLong((String)context[0]);
			if (_materialTypes == null) {
				//refreshMaterialTypes();
		
			}
			System.out.println("before find in model");
			materialType = findMaterialTypeInModel(materialTypeId);
		}
		
	}
	
	String onPassivate() {
		// Put the person's id into the context. Tapestry won't allow null or blank in the context so we "encode" it.
		//return materialType == null ? "null" : ContextFixer.encodeLong(materialType.getId());
		return "";
	}
  */
	/*
	private void refreshMaterialTypes() {
		// Get all persons - ask business service to find them (from the database)
		List<MaterialType> materialTypes = null;
		try {
		   materialTypes = getMaterialTypeService().findMaterialTypes();
		} catch(DoesNotExistException e) { return ;}
		
		_materialTypes = new MaterialTypeSelectModel(materialTypes);
	}
	*/
	
	private MaterialType findMaterialTypeInModel(Long materialTypeId) {
		for (MaterialType materialType : materialTypesSelect.getList() ) {
			if (materialType.getId().equals(materialTypeId)) {
				return materialType;
			}
		}
		return null; 
	}
	 
}
