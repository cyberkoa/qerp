package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.security.Role;
import com.quesofttech.business.domain.security.iface.IRoleServiceRemote;
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
public class RoleMaintenance extends SecureBasePage {
private String _strMode = "";
private Role RoleDetail;
private Role _Role;
@Persist
private List<Role> _Roles;
@Inject
private Logger _logger;
@Inject
private Block blockFormView;
@Persist
private long lng_CurrentID;
@Component(id = "RoleForm")
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
@Component(id = "RoleField")
private TextField _RoleField;
private String RoleField;
public String getRoleField()
{
   return RoleField;
}
public void setRoleField(String RoleField)
{
   this.RoleField = RoleField;
}
void RefreshRecords()
{
try
{
   _Roles = getRoleService().findRoles();
}
catch(BusinessException be)
{

}
if(_Roles!=null && !_Roles.isEmpty())
{
   RoleDetail = _Roles.get(_Roles.size() - 1);
   assignToLocalVariable(RoleDetail);
}
}
private int getRcdLocation( Long id)  throws BusinessException
{
   int int_return ,int_i;
   int_i = 0;
   int_return = 0;
   _Roles = getRoleService().findRoles();
   for(Role p : _Roles)
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

void assignToDatabase(Role role){
   role.setId(id);
   role.setRole(RoleField);
   role.setRecordStatus("A");
}
void assignToLocalVariable(Role role)
{
   this.id = role.getId();
   this.RoleField = role.getRole();
}
void _AddRecord()
{
Role role = new Role();
try {
	role.setCreateLogin(getVisit().getMyLoginId());
	role.setModifyLogin(getVisit().getMyLoginId());
	
	role.setCreateApp(this.getClass().getSimpleName());
	role.setModifyApp(this.getClass().getSimpleName());
	 
   assignToDatabase(role);
   getRoleService().addRole(role);
   }
catch (Exception e) {
   _logger.info("Role_Add_problem");
   e.printStackTrace();
   _form.recordError(getMessages().get("Role_add_problem"));
}
}

void _UpdateRecord(){
Role role = new Role();
try
{
   role = getRoleService().findRole(id);
}
   catch(BusinessException be)
   {

}
if(role !=null)
{
try {
	//role.setCreateLogin(getVisit().getMyLoginId());
	role.setModifyLogin(getVisit().getMyLoginId());
	role.setModifyApp(this.getClass().getSimpleName()); 
	
	assignToDatabase(role);
	getRoleService().updateRole(role);
}
catch (BusinessException e) {
_form.recordError(_RoleField, e.getLocalizedMessage());
}
catch (Exception e) {
   _logger.info("Role_update_problem");
   e.printStackTrace();
   _form.recordError(getMessages().get("Role_update_problem"));
       }
   }
}


void _DeleteRecord(Long id) {
Role role = new Role();
try
{
   role = getRoleService().findRole(id);
}
catch(BusinessException be)
{

}
if(role!=null)
{
   try {
	   //role.setCreateLogin(getVisit().getMyLoginId());
		role.setModifyLogin(getVisit().getMyLoginId());
		role.setModifyApp(this.getClass().getSimpleName());
		 
   getRoleService().logicalDeleteRole(role);
}
   catch (BusinessException e) {
   _form.recordError(_RoleField, e.getLocalizedMessage());
}
catch (Exception e) {
   _logger.info("Role_Delete_problem");
   e.printStackTrace();
   _form.recordError(getMessages().get("Role_Delete_problem"));
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
       RoleDetail = getRoleService().findRole(id);
       int_SelectedRow = getRcdLocation(id);
   }
catch(BusinessException be)
{

}

if(RoleDetail!=null)
{
assignToLocalVariable(RoleDetail);
return blockFormView;
}
return null;
}
private IRoleServiceRemote getRoleService() {
   return getBusinessServicesLocator().getRoleServiceRemote();
}
public List<Role> getRoles() {
   return _Roles;
}


public Role getRole() throws BusinessException{
   return _Role;
}


 public void setRole(Role tb) {
   _Role = tb;
}

}
