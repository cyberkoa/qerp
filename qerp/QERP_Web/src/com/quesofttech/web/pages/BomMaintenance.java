package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.tapestry5.annotations.InjectPage;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.general.*;
import com.quesofttech.business.domain.general.iface.*;
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
public class BomMaintenance extends SecureBasePage {
private String _strMode = "";
private BOM BomDetail;
private BOM _Bom;
@Persist
private List<BOM> _BOMs;
@Inject
private Logger _logger;
@Inject
private Block blockFormView;
@Persist
private long lng_CurrentID;
private String viewDisplayText="", viewEditText="";
public String getViewDisplayText()
{
     return viewDisplayText;
}

public String getviewEditText()
{
     return viewEditText;
}
@InjectPage
private BomDetailMaintenance BomDetailDetail1;
Object onActionFrombtnDetail(Long id)
{ 
	_form.clearErrors();
	BomDetailDetail1.setHeaderID(id);
   return BomDetailDetail1;
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

//===============================
// Text Component for Material
@Component(id = "Material")
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
//===============================
void RefreshRecords()
{
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
    	   //BomDetail = _BOMs.get(_BOMs.size() - 1);
    	   BomDetail = _BOMs.get(int_SelectedRow);
       }
       else
       {
    	   BomDetail = _BOMs.get(int_SelectedRow - 1);
       }
       myState="U";
       viewDisplayText="Block";
       viewEditText="none";
       assignToLocalVariable(BomDetail);
   }
}
private int getRcdLocation( Long id)  throws BusinessException
{
   int int_return ,int_i;
   int_i = 0;
   int_return = 0;
   _BOMs = getBOMService().findBOMs();
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

void assignToDatabase(BOM bom){
   bom.setId(id);
   bom.setType(Type);
   //bom.setMaterial(Material);
   bom.setRecordStatus("A");
}
void assignToLocalVariable(BOM bom)
{
   this.id = bom.getId();
   this.Type = bom.getType();
   //this.Material = bom.getMaterial();
}
void _AddRecord()
{
   BOM bom = new BOM();
   try {
	   bom.setCreateLogin(getVisit().getMyLoginId());
	   bom.setModifyLogin(getVisit().getMyLoginId());
	   
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
    	   //bom.setCreateLogin(getVisit().getMyLoginId());
    	   bom.setModifyLogin(getVisit().getMyLoginId());
    	   
           assignToDatabase(bom);
           getBOMService().updateBOM(bom);
       }
       catch (BusinessException e) {
           _form.recordError(_Material, e.getLocalizedMessage());
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
    	   //bom.setCreateLogin(getVisit().getMyLoginId());
    	   bom.setModifyLogin(getVisit().getMyLoginId());
    	   
           getBOMService().logicalDeleteBOM(bom);
           if(int_SelectedRow!=0)
           {
               int_SelectedRow--;
           }
           RefreshRecords();
       }
           catch (BusinessException e) {
           _form.recordError(_Material, e.getLocalizedMessage());
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
   myState = "U";
   _strMode = "U";
   lng_CurrentID = id;
   try
   {
	   _form.clearErrors();
       BomDetail = getBOMService().findBOM(id);
       int_SelectedRow = getRcdLocation(id);
   }
   catch(BusinessException be)
   {

   }

   if(BomDetail!=null)
   {
       viewDisplayText="Block";
       viewEditText="none";
       assignToLocalVariable(BomDetail);
       return blockFormView;
   }
   return null;
}

private IBOMServiceRemote getBOMService() {
	 return getBusinessServicesLocator().getBOMServiceRemote();
	 
}


public List<BOM> getBOMs() {
   return _BOMs;
}


public BOM getBOM() throws BusinessException{
   return _Bom;
}


 public void setBOM(BOM tb) {
   _Bom = tb;
}

}
