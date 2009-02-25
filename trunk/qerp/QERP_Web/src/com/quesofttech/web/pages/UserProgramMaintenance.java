package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.security.*;
import com.quesofttech.business.domain.security.iface.IProgramServiceRemote;
import com.quesofttech.business.domain.security.iface.IRoleServiceRemote;
import com.quesofttech.business.domain.security.iface.IUserProgramServiceRemote;
import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.base.SecureBasePage;
import com.quesofttech.web.model.base.GenericSelectModel;
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
public class UserProgramMaintenance extends SecureBasePage {
// Default defination.
    private String _strMode = "";
    private UserProgram UserProgramDetail;
    private UserProgram _UserProgram;
    @Persist
    private List<UserProgram> _UserPrograms;
    @Inject
    private Logger _logger;
    @Inject
    private Block blockFormView;
    @Persist
    private long lng_CurrentID;
// End of  Default defination.
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

    public String getviewEditText()
    {
    	refreshDisplay();
         return viewEditText;
    }

    @Component(id = "UserProgramForm")
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
  //===============================================================
	//			PRogram ComboBox
	//===============================================================
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<User> _users;	
	
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
	
	private ISecurityFinderServiceRemote getUserService() {
		return getBusinessServicesLocator().getSecurityFinderSvcRemote();
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

    //===============================
    //===============================

    //===============================
    // Text Component for User   
    
    
    //===============================

    //===============================
    // Text Component for IsAllowed
    @Component(id = "IsAllowed")
    private Checkbox _IsAllowed;
    private Boolean IsAllowed;
    public Boolean getIsAllowed()
    {
       return IsAllowed;
    }

    public void setIsAllowed(Boolean IsAllowed)
    {
       this.IsAllowed = IsAllowed;
    }
    //===============================
    void RefreshRecords()
    {
    	List<Program> programlist = null;
    	List<User> userlist = null;
	  	try {    		
	  		programlist = this.getProgramService().findPrograms();    
	  		userlist = this.getUserService().findUsers();
    	}
    	catch (DoesNotExistException e) {}
    	_users = null;
    	_programs = null;
    	_programs = new GenericSelectModel<Program>(programlist,Program.class,"CodeDescription","id",_access);
    	_users = new GenericSelectModel<User>(userlist,User.class,"login","id",_access1);
        
    	
       try
       {
           _UserPrograms = getUserProgramService().findUserPrograms();
       }
       catch(BusinessException be)
       {

       }
       if(_UserPrograms!=null && !_UserPrograms.isEmpty())
       {
           if(int_SelectedRow==0)
           {
               UserProgramDetail = _UserPrograms.get(_UserPrograms.size() - 1);
           }
           else
           {
               UserProgramDetail = _UserPrograms.get(int_SelectedRow - 1);
           }
          // UserProgramDetail = _UserPrograms.get(_UserPrograms.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(UserProgramDetail);
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
      for(UserProgram p : _UserPrograms)
       {
          int_i++;
          if(p.getId()==id)
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

    void assignToDatabase(UserProgram userProgram){
       userProgram.setId(id);
       userProgram.setProgram(Program);
       userProgram.setUser(User);
       userProgram.setIsAllowed(IsAllowed);
       userProgram.setRecordStatus("A");
    }
    void assignToLocalVariable(UserProgram userProgram)
    {
       this.id = userProgram.getId();
       this.Program = userProgram.getProgram();
       this.User = userProgram.getUser();
       this.IsAllowed = userProgram.getIsAllowed();
    }
    void _AddRecord()
    {
       UserProgram userProgram = new UserProgram();
       try {
               userProgram.setModifyLogin(getVisit().getMyLoginId());
               userProgram.setCreateLogin(getVisit().getMyLoginId());
               userProgram.setCreateApp(this.getClass().getSimpleName());
               userProgram.setModifyApp(this.getClass().getSimpleName());  
           assignToDatabase(userProgram);
           getUserProgramService().addUserProgram(userProgram);
       }
       catch (Exception e) {
           _logger.info("UserProgram_Add_problem");
           e.printStackTrace();
           _form.recordError(getMessages().get("UserProgram_add_problem"));
       }
    }

    void _UpdateRecord(){
       UserProgram userProgram = new UserProgram();
       try
       {
           userProgram = getUserProgramService().findUserProgram(id);
       }
       catch(BusinessException be)
       {

       }
       if(userProgram !=null)
       {
           try {
               userProgram.setModifyLogin(getVisit().getMyLoginId());
               assignToDatabase(userProgram);
               getUserProgramService().updateUserProgram(userProgram);
           }
           catch (BusinessException e) {
               _form.recordError(_id, e.getLocalizedMessage());
           }
       catch (Exception e) {
               _logger.info("UserProgram_update_problem");
               e.printStackTrace();
               _form.recordError(getMessages().get("UserProgram_update_problem"));
           }
       }
    }


    void _DeleteRecord(Long id) {
       UserProgram userProgram = new UserProgram();
       try
       {
           userProgram = getUserProgramService().findUserProgram(id);
       }
       catch(BusinessException be)
       {

       }
       if(userProgram!=null)
       {
           try {
               userProgram.setModifyLogin(getVisit().getMyLoginId());
               getUserProgramService().logicalDeleteUserProgram(userProgram);
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
               _logger.info("UserProgram_Delete_problem");
               e.printStackTrace();
               _form.recordError(getMessages().get("UserProgram_Delete_problem"));
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
           UserProgramDetail = getUserProgramService().findUserProgram(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {

       }

       if(UserProgramDetail!=null){
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(UserProgramDetail);
           return blockFormView;
       }
       return null;
    }

    private IUserProgramServiceRemote getUserProgramService() {
       return getBusinessServicesLocator().getUserProgramServiceRemote();
    }


    public List<UserProgram> getUserPrograms() {
       return _UserPrograms;
    }


    public UserProgram getUserProgram() throws BusinessException{
       return _UserProgram;
    }


     public void setUserProgram(UserProgram tb) {
       _UserProgram = tb;
    }

}
