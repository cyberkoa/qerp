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
public class ChangePassword extends SecureBasePage {

	private User _User;

	@Inject
	private Logger _logger;
	@Inject
	private Block blockFormView;
	@Persist
	private long lng_CurrentID;
	
	@Component(id = "ChangePasswordForm")
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
	   //RefreshRecords();
	   return blockFormView;
	}
	
	
	void setupRender() {
	   //RefreshRecords();
	}
	
	
	void onValidateForm() {
	}
	
/*
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
*/
	
	
	
	private ISecurityFinderServiceRemote getUserService() {
	   return getBusinessServicesLocator().getSecurityFinderSvcRemote();
	}
	private ISecurityManagerServiceRemote getSecurityManagerService() {
		   return getBusinessServicesLocator().getSecurityManagerServiceRemote();
		}
	//private ISecurityFinderServiceRemote getSecurityFinderService() {
	//	return getBusinessServicesLocator().getSecurityFinderSvcRemote();
	//}

	

}
