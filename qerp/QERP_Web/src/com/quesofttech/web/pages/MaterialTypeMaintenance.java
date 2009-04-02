package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.base.SecureBasePage;
import com.quesofttech.web.state.Visit;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DuplicateAlternateKeyException;
import com.quesoware.business.common.exception.DuplicatePrimaryKeyException;
import com.quesoware.business.domain.inventory.MaterialType;
import com.quesoware.business.domain.inventory.iface.IMaterialTypeServiceRemote;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;

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


public class MaterialTypeMaintenance extends SecureBasePage {
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
private MaterialType MaterialTypeDetail;
private MaterialType _MaterialType;
@Persist
private List<MaterialType> _MaterialTypes;
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

@Component(id = "MaterialTypeForm")
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
// Text Component for IsForSale
@Component(id = "IsForSale")
private Checkbox _IsForSale;
private boolean IsForSale;
public boolean getIsForSale()
{
   return IsForSale;
}

public void setIsForSale(boolean IsForSale)
{
   this.IsForSale = IsForSale;
}
//===============================

//===============================
// Text Component for IsProduced
@Component(id = "IsProduced")
private Checkbox _IsProduced;
private boolean IsProduced;
public boolean getIsProduced()
{
   return IsProduced;
}

public void setIsProduced(boolean IsProduced)
{
   this.IsProduced = IsProduced;
}
//===============================

//===============================
//Text Component for IsPurchased
@Component(id = "IsPurchased")
private Checkbox _IsPurchased;
private boolean IsPurchased;
public boolean getIsPurchased()
{
return IsPurchased;
}

public void setIsPurchased(boolean IsPurchased)
{
this.IsPurchased = IsPurchased;
}
//===============================


//===============================
//Text Component for IsPurchased
@Component(id = "IsJIT")
private Checkbox _IsJIT;
private boolean IsJIT;
public boolean getIsJIT()
{
	return IsJIT;
}

public void setIsJIT(boolean IsJIT)
{
	this.IsJIT = IsJIT;
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
       _MaterialTypes = getMaterialTypeService().findMaterialTypes();
   }
   catch(BusinessException be)
   {

   }
   
   if(_MaterialTypes!=null && !_MaterialTypes.isEmpty())
   {
       if(int_SelectedRow==0)
       {
           MaterialTypeDetail = _MaterialTypes.get(_MaterialTypes.size() - 1);
       }
       else
       {
           MaterialTypeDetail = _MaterialTypes.get(int_SelectedRow - 1);
       }
       myState="U";
       //MaterialTypeDetail = _MaterialTypes.get(_MaterialTypes.size() - 1);
       viewDisplayText="Block";
       viewEditText="none";
       assignToLocalVariable(MaterialTypeDetail);
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
   _MaterialTypes = getMaterialTypeService().findMaterialTypes();
   for(MaterialType p : _MaterialTypes)
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
	myState="U";
   RefreshRecords();
   return blockFormView;
}


void setupRender() {
   RefreshRecords();
}


void onValidateForm()  {
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

void assignToDatabase(MaterialType materialType){
   materialType.setId(id);
   materialType.setDescription(Description);
   materialType.setForSale(IsForSale);
   materialType.setProduced(IsProduced);
   materialType.setPurchased(IsPurchased);
   materialType.setJIT(IsJIT);
   materialType.setType(Type);
   materialType.setRecordStatus("A");
}
void assignToLocalVariable(MaterialType materialType)
{
   this.id = materialType.getId();
   this.Description = materialType.getDescription();
   this.IsForSale = materialType.isForSale();
   this.IsProduced = materialType.isProduced();
   this.IsPurchased = materialType.isPurchased();
   this.Type = materialType.getType();
   this.IsJIT = materialType.isJIT();
}
void _AddRecord()
{
   MaterialType materialType = new MaterialType();
   try {
           materialType.setModifyLogin(getVisit().getMyLoginId());
           materialType.setCreateLogin(getVisit().getMyLoginId());
		   materialType.setCreateApp(this.getClass().getSimpleName());
		   materialType.setModifyApp(this.getClass().getSimpleName());           
           
           assignToDatabase(materialType);
           getMaterialTypeService().addMaterialType(materialType);
   }
	catch (BusinessException e) {
		if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
		{
			_form.recordError(e.getMessage());
		
		}
		else
			_form.recordError(e.getMessage());
	}  
	catch (Exception e) {
		_form.recordError(e.getMessage());
	}
}

void _UpdateRecord(){
   MaterialType materialType = new MaterialType();
   try
   {
       materialType = getMaterialTypeService().findMaterialType(id);
   }
	catch(BusinessException be)
	{
		_form.recordError(be.getMessage());			
	}
   if(materialType !=null)
   {
       try {
           materialType.setModifyLogin(getVisit().getMyLoginId());
		   materialType.setModifyApp(this.getClass().getSimpleName());
           
           assignToDatabase(materialType);
           getMaterialTypeService().updateMaterialType(materialType);
       }
		catch (BusinessException e) {
			if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
			{
				_form.recordError(e.getMessage());
			}
			else	
				_form.recordError(e.getMessage());
		}
		catch (Exception e) {
			_form.recordError(e.getMessage());
       }
   }
}


void _DeleteRecord(Long id) {
   MaterialType materialType = new MaterialType();
  
   try  {
       materialType = getMaterialTypeService().findMaterialType(id);
   }
   catch(BusinessException be)
	{
	   _form.recordError(be.getMessage());
	}
   
   if(materialType!=null)
   {
       try 
       {
           materialType.setModifyLogin(getVisit().getMyLoginId());

		   materialType.setModifyApp(this.getClass().getSimpleName());

           
           getMaterialTypeService().logicalDeleteMaterialType(materialType);
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
	   _form.clearErrors();
       MaterialTypeDetail = getMaterialTypeService().findMaterialType(id);
       int_SelectedRow = getRcdLocation(id);
   }
   catch(BusinessException be)
   {

   }

   if(MaterialTypeDetail!=null)
   {
       viewDisplayText="Block";
       viewEditText="none";
       assignToLocalVariable(MaterialTypeDetail);
       return blockFormView;
   }
   return null;
}

private IMaterialTypeServiceRemote getMaterialTypeService() {
   return getBusinessServicesLocator().getMaterialTypeServiceRemote();
}


public List<MaterialType> getMaterialTypes() {
   return _MaterialTypes;
}


public MaterialType getMaterialType() throws BusinessException{
   return _MaterialType;
}


 public void setMaterialType(MaterialType tb) {
   _MaterialType = tb;
}

}
