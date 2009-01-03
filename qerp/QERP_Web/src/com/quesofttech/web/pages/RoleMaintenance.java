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
// Default defination.
    private String _strMode = "";
    private Role RoleDetail;
    private Role _role;
    @Persist
    private List<Role> _Roles;
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
     return viewDisplayText;
    }

    public String getviewEditText()
    {
         return viewEditText;
    }

    @Component(id = "RoleForm")
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
    // Text Component for Role
    @Component(id = "Role")
    private TextField _Role;
    private String Role;
    public String getRole()
    {
       return Role;
    }

    public void setRole(String Role)
    {
       this.Role = Role;
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
           if(int_SelectedRow==0)
           {
               RoleDetail = _Roles.get(_Roles.size() - 1);
           }
           else
           {
               RoleDetail = _Roles.get(int_SelectedRow - 1);
           }
           RoleDetail = _Roles.get(_Roles.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(RoleDetail);
       }
    }
    private int getRcdLocation( Long id)  throws BusinessException
    {
      int int_return ,int_i;
      int_i = 0;
      int_return = 0;
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

    void assignToDatabase(Role role){
       role.setId(id);
       role.setRole(Role);
       role.setDescription(Description);
       role.setRecordStatus("A");
    }
    void assignToLocalVariable(Role role)
    {
       this.id = role.getId();
       this.Role = role.getRole();
       this.Description = role.getDescription();
    }
    void _AddRecord()
    {
       Role role = new Role();
       try {
               role.setModifyLogin(getVisit().getMyLoginId());
               role.setCreateLogin(getVisit().getMyLoginId());
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
               role.setModifyLogin(getVisit().getMyLoginId());
               assignToDatabase(role);
               getRoleService().updateRole(role);
           }
           catch (BusinessException e) {
               _form.recordError(_Role, e.getLocalizedMessage());
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
               role.setModifyLogin(getVisit().getMyLoginId());
               getRoleService().logicalDeleteRole(role);
               if(int_SelectedRow!=0)
               {
                   int_SelectedRow--;
               }
               RefreshRecords();
           }
               catch (BusinessException e) {
               _form.recordError(_Role, e.getLocalizedMessage());
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
           RoleDetail = getRoleService().findRole(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {

       }

       if(RoleDetail!=null){
           viewDisplayText="Block";
           viewEditText="none";
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


    public Role getRoleDetail() throws BusinessException{
       return _role;
    }


     public void setRoleDetail(Role tb) {
       _role = tb;
    }

}
