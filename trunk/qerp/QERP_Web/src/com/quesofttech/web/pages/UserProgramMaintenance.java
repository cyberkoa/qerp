package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.exception.DuplicateAlternateKeyException;
import com.quesofttech.business.common.exception.DuplicatePrimaryKeyException;
import com.quesofttech.business.domain.security.iface.IProgramServiceRemote;
import com.quesofttech.business.domain.security.Program;
import com.quesofttech.business.domain.security.UserProgram;
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
import org.omg.CosTransactions._SubtransactionAwareResourceStub;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;



public class UserProgramMaintenance extends SecureBasePage {
private String _strMode = "";
private UserProgram UserProgramDetail;
private UserProgram _UserProgram;
@Persist
private List<UserProgram> _UserPrograms;
@Inject
private Logger _logger;
@Inject
private Block blockFormView;


	@Inject
	private PropertyAccess _access;
	
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<Program> _programs;



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
/*
//===============================

//===============================
// Text Component for Program
@Component(id = "Program")
private TextField _Program;
private Long Program;
public Long getProgram()
{
   return Program;
}

public void setProgram(Long Program)
{
   this.Program = Program;
}
*/
//===============================

//===============================
// Text Component for User
@Component(id = "User")
private TextField _User;
private Long User;
public Long getUser()
{
   return User;
}

public void setUser(Long User)
{
   this.User = User;
}
//===============================

//===============================
// Text Component for IsAllowed
@Component(id = "IsAllowed")
private Checkbox _IsAllowed;
private boolean IsAllowed;
public boolean getIsAllowed()
{
   return IsAllowed;
}

public void setIsAllowed(boolean IsAllowed)
{
   this.IsAllowed = IsAllowed;
}
//===============================
void RefreshRecords()
{
	
	List<Program> programs = null;
	
	try {
       programs = this.getProgramService().findPrograms();
	}
	catch (DoesNotExistException e) {}
	
	_programs = new GenericSelectModel<Program>(programs,Program.class,"codeDescription","id",_access);
	
	
	
	
	
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
       UserProgramDetail = _UserPrograms.get(_UserPrograms.size() - 1);
       viewDisplayText="Block";
       viewEditText="none";
       assignToLocalVariable(UserProgramDetail);
   }
}
private int getRcdLocation( Long id)  throws BusinessException
{
   int int_return ,int_i;
   int_i = 0;
   int_return = 0;
   _UserPrograms = getUserProgramService().findUserPrograms();
   for(UserProgram p : _UserPrograms)
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

void assignToDatabase(UserProgram userProgram){
   userProgram.setId(id);
  // userProgram.setProgram(Program);
   //userProgram.setUser(User);
   //userProgram.setIsAllowed(IsAllowed);
   //userProgram.setRecordStatus("A");
}
void assignToLocalVariable(UserProgram userProgram)
{
   this.id = userProgram.getId();
  // this.Program = userProgram.getProgram();
  // this.User = userProgram.getUser()
  // this.IsAllowed = userProgram.getIsAllowed();
}
void _AddRecord()
{
   UserProgram userProgram = new UserProgram();
   try {
	   
	   userProgram.setCreateLogin(getVisit().getMyLoginId());
	   userProgram.setModifyLogin(getVisit().getMyLoginId());
	   userProgram.setCreateApp(this.getClass().getSimpleName());
	   userProgram.setModifyApp(this.getClass().getSimpleName());
	   
       assignToDatabase(userProgram);
       getUserProgramService().addUserProgram(userProgram);
   }
	catch (BusinessException e) {
		if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
		{
			//_form.recordError(_Program, e.getLocalizedMessage());
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
    	   userProgram.setModifyApp(this.getClass().getSimpleName());
    	   
           assignToDatabase(userProgram);
           getUserProgramService().updateUserProgram(userProgram);
       }
		catch (BusinessException e) {
			if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
			{
				_form.recordError(_id, e.getLocalizedMessage());
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
    	   
    	   userProgram.setModifyApp(this.getClass().getSimpleName());
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
				   _logger.info("Record_Delete_Error");
				   e.printStackTrace();
				   _form.recordError(getMessages().get("Record_Delete_Error"));
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
       UserProgramDetail = getUserProgramService().findUserProgram(id);
       int_SelectedRow = getRcdLocation(id);
   }
   catch(BusinessException be)
   {

   }

   if(UserProgramDetail!=null)
   {
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

private IProgramServiceRemote getProgramService() {
	return getBusinessServicesLocator().getProgramServiceRemote();
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
