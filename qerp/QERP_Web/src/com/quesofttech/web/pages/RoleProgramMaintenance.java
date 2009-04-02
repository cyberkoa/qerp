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
import com.quesoware.business.domain.security.*;
import com.quesoware.business.domain.security.iface.IProgramServiceRemote;
import com.quesoware.business.domain.security.iface.IRoleProgramServiceRemote;
import com.quesoware.business.domain.security.iface.IRoleServiceRemote;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesoware.business.domain.system.*;

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
public class RoleProgramMaintenance extends SimpleBasePage {
// Default defination.
    private String _strMode = "";
    private RoleProgram RoleProgramDetail;
    private RoleProgram _RoleProgram;
    @Persist
    private List<RoleProgram> _RolePrograms;
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

    @Component(id = "RoleProgramForm")
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
    // Text Component for Program
    //@Component(id = "Program")
    //private TextField _Program;
  //===============================================================
	//			PRogram ComboBox
	//===============================================================
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<Role> _roles;	
	
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<Program> _programs;	
	
	@Inject
    private PropertyAccess _access;
	
	@Inject
    private PropertyAccess _access1;
	
	private IProgramServiceRemote getProgramService() {
		return getBusinessServicesLocator().getProgramServiceRemote();
	}	
	private IRoleServiceRemote getRoleService() {
		return getBusinessServicesLocator().getRoleServiceRemote();
	}	
	
    private Program Program;
    public Program getProgram()
    {
       return Program;
    }

    public void setProgram(Program Program)
    {
       this.Program = Program;
    }
    
    //===============================

    //===============================
    // Text Component for Role
    //@Component(id = "Role")
   // private TextField _Role;
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
    // Text Component for IsAllowed
    @Component(id = "IsAllowed")
    private Checkbox _IsAllowed;
    private Boolean IsAllowed;
    
    public Boolean getIsAllowed() {
		return IsAllowed;
	}

	public void setIsAllowed(Boolean isAllowed) {
		IsAllowed = isAllowed;
	}

	//===============================
    void RefreshRecords()
    {
    	
    	List<Program> programlist = null;
    	List<Role> rolelist = null;
	  	try {    		
	  		programlist = this.getProgramService().findPrograms();    
	  		rolelist = this.getRoleService().findRoles();
    	}
    	catch (DoesNotExistException e) {}
    	_roles = null;
    	_programs = null;
    	_programs = new GenericSelectModel<Program>(programlist,Program.class,"CodeDescription","id",_access);
    	_roles = new GenericSelectModel<Role>(rolelist,Role.class,"roleDescription","id",_access1);
        
       try
       {
           _RolePrograms = getRoleProgramService().findRolePrograms();
       }
       catch(BusinessException be)
       {

       }
       if(_RolePrograms!=null && !_RolePrograms.isEmpty())
       {
           if(int_SelectedRow==0)
           {
               RoleProgramDetail = _RolePrograms.get(_RolePrograms.size() - 1);
           }
           else
           {
               RoleProgramDetail = _RolePrograms.get(int_SelectedRow - 1);
           }
           RoleProgramDetail = _RolePrograms.get(_RolePrograms.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(RoleProgramDetail);
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
      for(RoleProgram p : _RolePrograms)
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

    void assignToDatabase(RoleProgram roleProgram){
       roleProgram.setId(id);
       roleProgram.setProgram(Program);
       roleProgram.setRole(Role);
       roleProgram.setIsAllowed(IsAllowed);
       roleProgram.setRecordStatus("A");
       java.util.Date today = new java.util.Date();
       roleProgram.setModifyApp(this.getClass().getSimpleName());
       roleProgram.setModifyLogin(getVisit().getMyLoginId());
       roleProgram.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
    }
    void assignToLocalVariable(RoleProgram roleProgram)
    {
       this.id = roleProgram.getId();
       this.Program = roleProgram.getProgram();
       this.Role = roleProgram.getRole();
       this.IsAllowed = roleProgram.getIsAllowed();
    }
    void _AddRecord()
    {
       RoleProgram roleProgram = new RoleProgram();
       try {
                java.util.Date today = new java.util.Date();
                roleProgram.setCreateApp(this.getClass().getSimpleName());
                roleProgram.setCreateLogin(getVisit().getMyLoginId());
                roleProgram.setCreateTimestamp(new java.sql.Timestamp(today.getTime()));
           assignToDatabase(roleProgram);
           getRoleProgramService().addRoleProgram(roleProgram);
       }
       catch (Exception e) {
           _logger.info("RoleProgram_Add_problem");
           e.printStackTrace();
           _form.recordError(getMessages().get("RoleProgram_add_problem"));
       }
    }

    void _UpdateRecord(){
       RoleProgram roleProgram = new RoleProgram();
       try
       {
           roleProgram = getRoleProgramService().findRoleProgram(id);
       }
       catch(BusinessException be)
       {

       }
       if(roleProgram !=null)
       {
           try {
               roleProgram.setModifyLogin(getVisit().getMyLoginId());
               assignToDatabase(roleProgram);
               getRoleProgramService().updateRoleProgram(roleProgram);
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
       RoleProgram roleProgram = new RoleProgram();
       try
       {
           roleProgram = getRoleProgramService().findRoleProgram(id);
       }
       catch(BusinessException be)
       {

       }
       if(roleProgram!=null)
       {
           try {
               roleProgram.setModifyLogin(getVisit().getMyLoginId());
               getRoleProgramService().logicalDeleteRoleProgram(roleProgram);
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
           RoleProgramDetail = getRoleProgramService().findRoleProgram(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {

       }

       if(RoleProgramDetail!=null){
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(RoleProgramDetail);
           return blockFormView;
       }
       return null;
    }

    private IRoleProgramServiceRemote getRoleProgramService() {
       return getBusinessServicesLocator().getRoleProgramServiceRemote();
    }


    public List<RoleProgram> getRolePrograms() {
       return _RolePrograms;
    }


    public RoleProgram getRoleProgram() throws BusinessException{
       return _RoleProgram;
    }


     public void setRoleProgram(RoleProgram tb) {
       _RoleProgram = tb;
    }

}
