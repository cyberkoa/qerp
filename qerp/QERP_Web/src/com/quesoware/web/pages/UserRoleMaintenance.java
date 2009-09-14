 package com.quesoware.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.domain.security.*;
import com.quesoware.business.domain.security.iface.*;
import com.quesoware.web.base.SecureBasePage;
import com.quesoware.web.base.SimpleBasePage;
import com.quesoware.web.model.base.GenericSelectModel;
import com.quesoware.web.state.Visit;

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
public class UserRoleMaintenance extends SimpleBasePage {
// Default defination.
    private String _strMode = "";
    private UserRole UserRoleDetail;
    private UserRole _UserRole;
    @Persist
    private List<UserRole> _UserRoles;
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

    @Component(id = "UserRoleForm")
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
    @Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<Role> _roles;	
	
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<User> _users;	
	
	@Inject
    private PropertyAccess _access;
	
	@Inject
    private PropertyAccess _access1;
	
	private ISecurityFinderServiceRemote getUserService() {
		return getBusinessServicesLocator().getSecurityFinderSvcRemote();
	}	
	private IRoleServiceRemote getRoleService() {
		return getBusinessServicesLocator().getRoleServiceRemote();
	}	
	
    
    private Role Role;
    public Role getRole()
    {
       return Role;
    }

    public void setRole(Role Role)
    {
       this.Role = Role;
    }
    //===============================

    //===============================
    // Text Component for User
   
    private User User;
    public User getUser()
    {
       return User;
    }

    public void setUser(User User)
    {
       this.User = User;
    }
    //===============================
    void RefreshRecords()
    {
    	
    	List<User> userlist = null;
    	List<Role> rolelist = null;
	  	try {    		
	  		userlist = this.getUserService().findUsers();    
	  		rolelist = this.getRoleService().findRoles();
    	}
    	catch (DoesNotExistException e) {}
    	_roles = null;
    	_users = null;
    	_users = new GenericSelectModel<User>(userlist,User.class,"login","id",_access);
    	_roles = new GenericSelectModel<Role>(rolelist,Role.class,"roleDescription","id",_access1);
        
       try
       {
           _UserRoles = getUserRoleService().findUserRoles();
       }
       catch(BusinessException be)
       {

       }
       if(_UserRoles!=null && !_UserRoles.isEmpty())
       {
           if(int_SelectedRow==0)
           {
               UserRoleDetail = _UserRoles.get(_UserRoles.size() - 1);
           }
           else
           {
               UserRoleDetail = _UserRoles.get(int_SelectedRow - 1);
           }
           UserRoleDetail = _UserRoles.get(_UserRoles.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(UserRoleDetail);
       }
             else
            {
                      myState="A"; // If no List then should be in A mode instead of Update mode.
            }
    }
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
    private int getRcdLocation( Long id)  throws BusinessException
    {
      int int_return ,int_i;
      int_i = 0;
      int_return = 0;
      for(UserRole p : _UserRoles)
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

    void assignToDatabase(UserRole userRole){
       userRole.setId(id);
       userRole.setRole(Role);
       userRole.setUser(User);
       userRole.setRecordStatus("A");
       java.util.Date today = new java.util.Date();
       userRole.setModifyApp(this.getClass().getSimpleName());
       userRole.setModifyLogin(getVisit().getMyLoginId());
       userRole.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
    }
    void assignToLocalVariable(UserRole userRole)
    {
       this.id = userRole.getId();
       this.Role = userRole.getRole();
       this.User = userRole.getUser();
    }
    void _AddRecord()
    {
       UserRole userRole = new UserRole();
       try {
                java.util.Date today = new java.util.Date();
                userRole.setCreateApp(this.getClass().getSimpleName());
                userRole.setCreateLogin(getVisit().getMyLoginId());
                userRole.setCreateTimestamp(new java.sql.Timestamp(today.getTime()));
           assignToDatabase(userRole);
           getUserRoleService().addUserRole(userRole);
       }
       catch (Exception e) {
           _logger.info("UserRole_Add_problem");
           e.printStackTrace();
           _form.recordError(getMessages().get("UserRole_add_problem"));
       }
    }

    void _UpdateRecord(){
       UserRole userRole = new UserRole();
       try
       {
           userRole = getUserRoleService().findUserRole(id);
       }
       catch(BusinessException be)
       {

       }
       if(userRole !=null)
       {
           try {
               userRole.setModifyLogin(getVisit().getMyLoginId());
               assignToDatabase(userRole);
               getUserRoleService().updateUserRole(userRole);
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
       UserRole userRole = new UserRole();
       try
       {
           userRole = getUserRoleService().findUserRole(id);
       }
       catch(BusinessException be)
       {

       }
       if(userRole!=null)
       {
           try {
               userRole.setModifyLogin(getVisit().getMyLoginId());
               getUserRoleService().logicalDeleteUserRole(userRole);
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
           UserRoleDetail = getUserRoleService().findUserRole(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {

       }

       if(UserRoleDetail!=null){
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(UserRoleDetail);
           return blockFormView;
       }
       return null;
    }

    private IUserRoleServiceRemote getUserRoleService() {
       return getBusinessServicesLocator().getUserRoleServiceRemote();
    }


    public List<UserRole> getUserRoles() {
       return _UserRoles;
    }


    public UserRole getUserRole() throws BusinessException{
       return _UserRole;
    }


     public void setUserRole(UserRole tb) {
       _UserRole = tb;
    }

} 
