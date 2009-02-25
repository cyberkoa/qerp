package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import org.apache.tapestry5.corelib.components.DateField;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.security.User;
import com.quesofttech.business.domain.security.iface.ISecurityManagerServiceRemote;
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
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import com.quesofttech.web.util.*;
import org.apache.tapestry5.annotations.ApplicationState;
public class UserMaintenance extends SecureBasePage {
// Default defination.
	/*public static java.util.Date SqlTimestamptoDateUtilDate(java.sql.Timestamp timestamp) {
	    long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
	    return new java.util.Date(milliseconds);
	}
	private static final java.sql.Timestamp utilDateToSqlTimestamp(java.util.Date utilDate) {
		return new java.sql.Timestamp(utilDate.getTime());
		}
		*/
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
	private DateTimeConvert dateConv = new DateTimeConvert();
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
// End of  Default defination.
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
    private java.sql.Timestamp ExpiryDate;
    public java.util.Date getExpiryDate()
    {
    	
    	System.out.println("is it here error? lao eh");
       if (ExpiryDate==null)
       {
    	   return null;   
       }       
       return dateConv.SqlTimestamptoDateUtilDate(ExpiryDate);
    }

    public void setExpiryDate(java.util.Date ExpiryDate)
    {
    	//java.util.Date jDate = 
        //    new java.util.Date(sDate.getTime());

    	System.out.println("is it here error?");    	
    	this.ExpiryDate = dateConv.utilDateToSqlTimestamp(ExpiryDate);
    	System.out.println("hopefully is here");
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
    // Text Component for UserPassword
    @Component(id = "UserPassword")
    private PasswordField _UserPassword;
    private String UserPassword;
    public String getUserPassword()
    {
       return UserPassword;
    }

    public void setUserPassword(String UserPassword)
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
       catch(Exception be)
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
           UserDetail = _Users.get(_Users.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(UserDetail);
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

    void assignToDatabase(User user){
       user.setId(id);       
       user.setEmailAddress(EmailAddress);
       
       user.setExpiryDate(dateConv.utilDateToSqlTimestamp(ExpiryDate));
       user.setFirstName(FirstName);
       user.setLastName(LastName);
       user.setLogin(Login);
       user.setSalutation(Salutation);
       user.setTelephone(Telephone);
       user.setModifyLogin(getVisit().getMyLoginId());              
       user.setModifyApp(this.getClass().getSimpleName());               
       java.util.Date today = new java.util.Date();
       user.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
       //user.setUserPassword(UserPassword);
       user.setRecordStatus("A");
    }
    void assignToLocalVariable(User user)
    {
       this.id = user.getId();
       this.EmailAddress = user.getEmailAddress();
       System.out.println("lao eh ..going to assigntolocalvariable liao");       
       this.ExpiryDate = user.getExpiryDate();
       System.out.println("lao eh ..after assigntolocalvariable liao");
       this.FirstName = user.getFirstName();
       this.LastName = user.getLastName();
       this.Login = user.getLogin();
       this.Salutation = user.getSalutation();
       this.Telephone = user.getTelephone();
       //this.UserPassword = user.getUserPassword();
    }
    void _AddRecord()
    {
       User user = new User();
       try {
    	   	   user.setCreateLogin(getVisit().getMyLoginId());
    	   	   user.setCreateApp(this.getClass().getSimpleName());
    	   	   
               
               //user.setCreateTimestamp(new java.sql.Timestamp(today.getTime()));
           assignToDatabase(user);
           //getUserService().create(user);
           getUserMgrService().createUser(user, UserPassword);
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
               getUserMgrService().updateUser(user);
           }
           catch (BusinessException e) {
               //_form.recordError(Login, e.getLocalizedMessage());
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
               //getUserService().logicalDeleteUser(user);
               getUserMgrService().deleteUser(user);
               if(int_SelectedRow!=0)
               {
                   int_SelectedRow--;
               }
               RefreshRecords();
           }
               catch (BusinessException e) {
               //_form.recordError(Login, e.getLocalizedMessage());
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
           UserDetail = getUserService().findUser(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {

       }

       if(UserDetail!=null){
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

    private ISecurityManagerServiceRemote getUserMgrService()
    {
    	return getBusinessServicesLocator().getSecurityManagerServiceRemote();
    }

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
