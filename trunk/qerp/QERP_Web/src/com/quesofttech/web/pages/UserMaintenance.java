package com.quesofttech.web.pages;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.security.*;
import com.quesofttech.business.domain.security.iface.*;

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
import org.apache.tapestry5.corelib.components.DateField;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.omg.CosTransactions._SubtransactionAwareResourceStub;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
public class UserMaintenance extends SecureBasePage {
	private String _strMode = "";
	private User UserDetail;
	private User _User;
	@Persist
	private List<User> _Users;
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
	
	@Component(id = "UserForm")
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
	// Text Component for Login
	@Component(id = "Login")
	private TextField _Login;
	private String Login;
	public String getLogin()
	{
	   return Login;
	}
	
	public void setLogin(String Login)
	{
	   this.Login = Login;
	}
	//===============================
	
	//===============================
	// Text Component for FirstName
	@Component(id = "FirstName")
	private TextField _FirstName;
	private String FirstName;
	public String getFirstName()
	{
	   return FirstName;
	}
	
	public void setFirstName(String FirstName)
	{
	   this.FirstName = FirstName;
	}
	//===============================
	
	//===============================
	// Text Component for LastName
	@Component(id = "LastName")
	private TextField _LastName;
	private String LastName;
	public String getLastName()
	{
	   return LastName;
	}
	
	public void setLastName(String LastName)
	{
	   this.LastName = LastName;
	}
	//===============================
	
	//===============================
	// Text Component for Salutation
	@Component(id = "Salutation")
	private TextField _Salutation;
	private String Salutation;
	public String getSalutation()
	{
	   return Salutation;
	}
	
	public void setSalutation(String Salutation)
	{
	   this.Salutation = Salutation;
	}
	//===============================
	
	//===============================
	// Text Component for Telephone
	@Component(id = "Telephone")
	private TextField _Telephone;
	private String Telephone;
	public String getTelephone()
	{
	   return Telephone;
	}
	
	public void setTelephone(String Telephone)
	{
	   this.Telephone = Telephone;
	}
	//===============================
	
	//===============================
	// Text Component for EmailAddress
	@Component(id = "EmailAddress")
	private TextField _EmailAddress;
	private String EmailAddress;
	public String getEmailAddress()
	{
	   return EmailAddress;
	}
	
	public void setEmailAddress(String EmailAddress)
	{
	   this.EmailAddress = EmailAddress;
	}
	//===============================
	
	//===============================
	// Text Component for ExpiryDate
	
	@Component(id = "ExpiryDate")
	private DateField _ExpiryDate;
	private java.util.Date ExpiryDate;
	public java.util.Date getExpiryDate()
	{
	   return ExpiryDate;
	}
	
	public void setExpiryDate(java.util.Date ExpiryDate)
	{
	   this.ExpiryDate = ExpiryDate;
	}
	
	//===============================
	
	//===============================
	// Text Component for UserPassword
	@Component(id = "UserPassword")
	private TextField _UserPassword;
	private Long UserPassword;
	public Long getUserPassword()
	{
	   return UserPassword;
	}
	
	public void setUserPassword(Long UserPassword)
	{
	   this.UserPassword = UserPassword;
	}
	//===============================
	void RefreshRecords()
	{
	   try
	   {
	       _Users = getUserService().findUsers();
	   }
	   finally
	   {
		   
	   }
	   
	   if(_Users!=null && !_Users.isEmpty())
	   {
	       if(int_SelectedRow==0)
	       {
	           UserDetail = _Users.get(_Users.size() - 1);
	       }
	       else
	       {
	           UserDetail = _Users.get(int_SelectedRow - 1);
	       }
	       myState="U";
	       UserDetail = _Users.get(_Users.size() - 1);
	       viewDisplayText="Block";
	       viewEditText="none";
	       assignToLocalVariable(UserDetail);
	   }
	}
	private int getRcdLocation( Long id)  throws BusinessException
	{
	   int int_return ,int_i;
	   int_i = 0;
	   int_return = 0;
	   _Users = getUserService().findUsers();
	   for(User p : _Users)
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
	
	void assignToDatabase(User user){
	   user.setLogin(Login);
	   user.setFirstName(FirstName);
	   user.setLastName(LastName);
	   user.setSalutation(Salutation);
	   user.setTelephone(Telephone);
	   user.setEmailAddress(EmailAddress);
	   user.setExpiryDate(new java.sql.Timestamp(ExpiryDate.getTime()));
	   //user.setUserPassword(UserPassword);
	   user.setRecordStatus("A");
	}
	void assignToLocalVariable(User user)
	{
	   this.id = user.getId();
	   this.Login = user.getLogin();
	   this.FirstName = user.getFirstName();
	   this.LastName = user.getLastName();
	   this.Salutation = user.getSalutation();
	   this.Telephone = user.getTelephone();
	   this.EmailAddress = user.getEmailAddress();
	   this.ExpiryDate = user.getExpiryDate();
	   //this.UserPassword = user.ge
	}
	void _AddRecord()
	{
	   User user = new User();
	   try {
	           user.setModifyLogin(getVisit().getMyLoginId());
	           user.setCreateLogin(getVisit().getMyLoginId());
	       assignToDatabase(user);
	      // getUserService().addUser(user);
	       getSecurityManagerService().createUser(user, "testing");	       
	   }
	   catch (Exception e) {
	       _logger.info("User_Add_problem");
	       e.printStackTrace();
	       _form.recordError(getMessages().get("User_add_problem"));
	   }
	}
	
	void _UpdateRecord(){
	   User user = new User();
	   try
	   {
	       user = getUserService().findUser(id);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	   if(user !=null)
	   {
	       try {
	           user.setModifyLogin(getVisit().getMyLoginId());
	           assignToDatabase(user);
	           //getUserService().updateUser(user);
	           getSecurityManagerService().updateUser(user);
	       }
	       catch (BusinessException e) {
	           _form.recordError(_Login, e.getLocalizedMessage());
	       }
	       catch (Exception e) {
	           _logger.info("User_update_problem");
	           e.printStackTrace();
	           _form.recordError(getMessages().get("User_update_problem"));
	       }
	   }
	}
	
	
	void _DeleteRecord(Long id) {
	   User user = new User();
	   try
	   {
	       user = getUserService().findUser(id);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	   if(user!=null)
	   {
	       try {
	           user.setModifyLogin(getVisit().getMyLoginId());
	           //getUserService().d(user);
	           getSecurityManagerService().deleteUser(user);
	           if(int_SelectedRow!=0)
	           {
	               int_SelectedRow--;
	           }
	           RefreshRecords();
	       }
	           catch (BusinessException e) {
	           _form.recordError(_Login, e.getLocalizedMessage());
	   }
	       catch (Exception e) {
	           _logger.info("User_Delete_problem");
	           e.printStackTrace();
	           _form.recordError(getMessages().get("User_Delete_problem"));
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
	       UserDetail = getUserService().findUser(id);
	       int_SelectedRow = getRcdLocation(id);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	
	   if(UserDetail!=null)
	   {
	       viewDisplayText="Block";
	       viewEditText="none";
	       assignToLocalVariable(UserDetail);
	       return blockFormView;
	   }
	   return null;
	}
	
	private ISecurityFinderServiceRemote getUserService() {
	   return getBusinessServicesLocator().getSecurityFinderSvcRemote();
	}
	private ISecurityManagerServiceRemote getSecurityManagerService() {
		   return getBusinessServicesLocator().getSecurityManagerServiceRemote();
		}
	//private ISecurityFinderServiceRemote getSecurityFinderService() {
	//	return getBusinessServicesLocator().getSecurityFinderSvcRemote();
	//}

	
	public List<User> getUsers() {
		
	   return _Users;
	}
	
	
	public User getUser() throws BusinessException{
	   return _User;
	}
	
	
	 public void setUser(User tb) {
	   _User = tb;
	}

}
