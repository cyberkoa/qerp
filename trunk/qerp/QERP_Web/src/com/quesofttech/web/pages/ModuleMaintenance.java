// TODO: ModuleMaintenance is remarked. due to some error.
/*package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.security.Module;
//import com.quesofttech.business.domain.security.iface.;
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
public class ModuleMaintenance extends SecureBasePage {
private String _strMode = "";
private Module ModuleDetail;
private Module _Module;
@Persist
private List<Module> _Modules;
@Inject
private Logger _logger;
@Inject
private Block blockFormView;
@Persist
private long lng_CurrentID;
@Component(id = "ModuleForm")
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
@Component(id = "IsBasic")
private TextField _IsBasic;
private String IsBasic;
public String getIsBasic()
{
   return IsBasic;
}
public void setIsBasic(String IsBasic)
{
   this.IsBasic = IsBasic;
}
@Component(id = "IsEnabled")
private TextField _IsEnabled;
private String IsEnabled;
public String getIsEnabled()
{
   return IsEnabled;
}
public void setIsEnabled(String IsEnabled)
{
   this.IsEnabled = IsEnabled;
}
@Component(id = "IsPurchased")
private TextField _IsPurchased;
private String IsPurchased;
public String getIsPurchased()
{
   return IsPurchased;
}
public void setIsPurchased(String IsPurchased)
{
   this.IsPurchased = IsPurchased;
}
void RefreshRecords()
{
try
{
   _Modules = getModuleService().findModules();
}
catch(BusinessException be)
{

}
if(_Modules!=null && !_Modules.isEmpty())
{
   ModuleDetail = _Modules.get(_Modules.size() - 1);
   assignToLocalVariable(ModuleDetail);
}
}
private int getRcdLocation( Long id)  throws BusinessException
{
   int int_return ,int_i;
   int_i = 0;
   int_return = 0;
   _Modules = getModuleService().findModules();
   for(Module p : _Modules)
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
   RefreshRecords();
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

void assignToDatabase(Module module){
   module.setid(id);
   module.setCode(Code);
   module.setDescription(Description);
   module.setIsBasic(IsBasic);
   module.setIsEnabled(IsEnabled);
   module.setIsPurchased(IsPurchased);
   module.setRecordStatus("A");
}
void assignToLocalVariable(Module module)
{
   this.id = module.getid();
   this.Code = module.getCode();
   this.Description = module.getDescription();
   this.IsBasic = module.getIsBasic();
   this.IsEnabled = module.getIsEnabled();
   this.IsPurchased = module.getIsPurchased();
}
void _AddRecord()
{
Module module = new Module();
try {
   assignToDatabase(module);
   getModuleService().addModule(module);
   }
catch (Exception e) {
   _logger.info("Module_Add_problem");
   e.printStackTrace();
   _form.recordError(getMessages().get("Module_add_problem"));
}
}

void _UpdateRecord(){
Module module = new Module();
try
{
   module = getModuleService().findModule(id);
}
   catch(BusinessException be)
   {

}
if(module !=null)
{
try {
assignToDatabase(module);
getModuleService().updateModule(module);
}
catch (BusinessException e) {
_form.recordError(_typeField, e.getLocalizedMessage());
}
catch (Exception e) {
   _logger.info("Module_update_problem");
   e.printStackTrace();
   _form.recordError(getMessages().get("Module_update_problem"));
       }
   }
}


void _DeleteRecord(Long id) {
Module module = new Module();
try
{
   module = getModuleService().findModule(id);
}
catch(BusinessException be)
{

}
if(module!=null)
{
   try {
   getModuleService().logicalDeleteModule(module);
}
   catch (BusinessException e) {
   _form.recordError(_typeField, e.getLocalizedMessage());
}
catch (Exception e) {
   _logger.info("Module_Delete_problem");
   e.printStackTrace();
   _form.recordError(getMessages().get("Module_Delete_problem"));
}
}
}

Object onActionFromtoolbarDel(Long id)
{
   myState = "D";
   _strMode = "D";
   _DeleteRecord(id);
   return blockFormView;
}

Object onActionFromToolbarAdd ()
{
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
       ModuleDetail = getModuleService().findModule(id);
       int_SelectedRow = getRcdLocation(id);
   }
catch(BusinessException be)
{

}

if(ModuleDetail!=null)
{
assignToLocalVariable(ModuleDetail);
return blockFormView;
}
return null;
}
private IModuleServiceRemote getModuleService() {
   return getBusinessServicesLocator().getModuleServiceRemote();
}
public List<Module> getModules() {
   return _Modules;
}


public Module getModule() throws BusinessException{
   return _Module;
}


 public void setModule(Module tb) {
   _Module = tb;
}

}
*/