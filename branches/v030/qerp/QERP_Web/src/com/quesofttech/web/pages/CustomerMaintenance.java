// TODO: Address Object is not able to view in TML Grid object.
package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.base.SecureBasePage;
import com.quesofttech.web.state.Visit;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DuplicateAlternateKeyException;
import com.quesoware.business.common.exception.DuplicatePrimaryKeyException;
import com.quesoware.business.domain.sales.Customer;
import com.quesoware.business.domain.sales.iface.ICustomerServiceRemote;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;

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
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
import org.apache.tapestry5.annotations.OnEvent;
public class CustomerMaintenance extends SecureBasePage {
	// TabSet Column
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
	@Persist
    private String _activePanel;

    public String getActivePanel()
    {
        return _activePanel;
    }

    public void setActivePanel(String activePanel)
    {
        _activePanel = activePanel;
    }

    @OnEvent(component = "tabset", value = "action")
    public void onChange(String choosenPanelId)
    {
        _activePanel = choosenPanelId;
    }

	
	// Tabset Column site.
	
	
	
	
	
	
	
	
	
private String _strMode = "";
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

private Customer CustomerDetail;
private Customer _Customer;
@Persist
private List<Customer> _Customers;
@Inject
private Logger _logger;
@Inject
private Block blockFormView;
@Persist
private long lng_CurrentID;
@Component(id = "CustomerForm")
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
	//if(_Customer!=null && !_Customers.isEmpty())
		return id;
	//else 
		//return 0;
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
@Component(id = "Email")
private TextField _Email;
private String Email;
public String getEmail()
{
   return Email;
}
public void setEmail(String Email)
{
   this.Email = Email;
}
@Component(id = "Fax")
private TextField _Fax;
private String Fax;
public String getFax()
{
   return Fax;
}
public void setFax(String Fax)
{
   this.Fax = Fax;
}
@Component(id = "Name")
private TextField _Name;
private String Name;
public String getName()
{
   return Name;
}
public void setName(String Name)
{
   this.Name = Name;
}
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
/*@Component(id = "version")
private TextField _version;
private Integer version;
public Integer getversion()
{
   return version;
}
public void setversion(Integer version)
{
   this.version = version;
}*/
@Component(id = "area")
private TextField _area;
private String area;
public String getarea()
{
   return area;
}
public void setarea(String area)
{
   this.area = area;
}
@Component(id = "buildingLot")
private TextField _buildingLot;
private String buildingLot;
public String getbuildingLot()
{
   return buildingLot;
}
public void setbuildingLot(String buildingLot)
{
   this.buildingLot = buildingLot;
}
@Component(id = "country")
private TextField _country;
private String country;
public String getcountry()
{
   return country;
}
public void setcountry(String country)
{
   this.country = country;
}
@Component(id = "postalCode")
private TextField _postalCode;
private String postalCode;
public String getpostalCode()
{
   return postalCode;
}
public void setpostalCode(String postalCode)
{
   this.postalCode = postalCode;
}
@Component(id = "state")
private TextField _state;
private String state;
public String getstate()
{
   return state;
}
public void setstate(String state)
{
   this.state = state;
}
@Component(id = "street")
private TextField _street;
private String street;
public String getstreet()
{
   return street;
}
public void setstreet(String street)
{
   this.street = street;
}
void RefreshRecords()
{
	try
	{
	   _Customers = getCustomerService().findCustomers();
	}
	catch(BusinessException be)
	{
		
	}
	if(_Customers!=null && !_Customers.isEmpty())
	{
		if(int_SelectedRow==0)
		{
			CustomerDetail = _Customers.get(int_SelectedRow);
		}
		else
		{
			CustomerDetail = _Customers.get(int_SelectedRow - 1);
			
		}
	       myState="U";
		viewDisplayText="Block";
		viewEditText="none";
	    assignToLocalVariable(CustomerDetail);
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
   _Customers = getCustomerService().findCustomers();
   for(Customer p : _Customers)
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
	try
	{
		_form.clearErrors();
		RefreshRecords();	
	}
	catch (Exception e)
	{
		_form.recordError(getMessages().get("Customer_problem"));
	}
   
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

void assignToDatabase(Customer customer){
   customer.setId(id);
   customer.setCode(Code);
   customer.setEmail(Email);
   customer.setFax(Fax);
   customer.setName(Name);
   customer.setTelephone(Telephone);
   //customer.setversion(version);
   customer.getAddress().setArea(area);
   customer.getAddress().setBuildingLot(buildingLot);
   customer.getAddress().setCountry(country);
   customer.getAddress().setPostalCode(postalCode);
   customer.getAddress().setState(state);
   customer.getAddress().setStreet(street);
   customer.setRecordStatus("A");
}
void assignToLocalVariable(Customer customer)
{
   this.id = customer.getId();
   this.Code = customer.getCode();
   this.Email = customer.getEmail();
   this.Fax = customer.getFax();
   this.Name = customer.getName();
   this.Telephone = customer.getTelephone();
 //  this.version = customer.getversion();
   this.area = customer.getAddress().getArea();
   this.buildingLot = customer.getAddress().getBuildingLot();
   this.country = customer.getAddress().getCountry();
   this.postalCode = customer.getAddress().getPostalCode();
   this.state = customer.getAddress().getState();
   this.street = customer.getAddress().getStreet();
}
void _AddRecord() throws BusinessException
{

	try {
		Customer customer = new Customer();
		customer.setCreateLogin(getVisit().getMyLoginId());
		customer.setModifyLogin(getVisit().getMyLoginId());
		customer.setCreateApp(this.getClass().getSimpleName());
		customer.setModifyApp(this.getClass().getSimpleName());
		
	   assignToDatabase(customer);
	   System.out.println("after assign customer liao");
	   getCustomerService().addCustomer(customer);
	   System.out.println(" you shouldn't be here actually");
	   }
	catch (BusinessException e) {
		if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
		{
			_form.recordError(_Code, e.getLocalizedMessage());
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

void _UpdateRecord() throws BusinessException{
		Customer customer = new Customer();
		try
		{
		   customer = getCustomerService().findCustomer(id);
		}
		catch(BusinessException be)
		{
		   _logger.info("Record_Deleted_By_Others");
		   be.printStackTrace();
		   _form.recordError(getMessages().get("Record_Deleted_By_Others"));			
		}
		
		if(customer !=null)
		{
			try 
			{
				//customer.setCreateLogin(getVisit().getMyLoginId());
				customer.setModifyLogin(getVisit().getMyLoginId());
				customer.setModifyApp(this.getClass().getSimpleName());
				
				assignToDatabase(customer);
				getCustomerService().updateCustomer(customer);
			}
			catch (BusinessException e) 
			{
				if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
				{
					_form.recordError(_Code, e.getLocalizedMessage());
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


void _DeleteRecord(Long id) //throws BusinessException
{
	Customer customer = new Customer();
	try
	{
		//customer.setCreateLogin(getVisit().getMyLoginId());
		customer.setModifyLogin(getVisit().getMyLoginId());
		 
	   customer = getCustomerService().findCustomer(id);
	}
	   catch(BusinessException be)
		{
			_logger.info("Record_Deleted_By_Others");
			   be.printStackTrace();
			   _form.recordError(getMessages().get("Record_Deleted_By_Others"));
		}
	if(customer!=null)
	{
	   try 
	   {	   
		   //int_SelectedRow=getRcdLocation(id);			  
		   customer.setModifyLogin(getVisit().getMyLoginId());
		   customer.setModifyApp(this.getClass().getSimpleName());
		   
		   getCustomerService().logicalDeleteCustomer(customer);
		  if(int_SelectedRow!=0)
		  {
			  int_SelectedRow--;
		  }		  
		  RefreshRecords();
	   }
		catch (BusinessException e) {
		   _form.recordError(_Code, e.getLocalizedMessage());
		}
		catch (Exception e) {
		   _logger.info("Record_Delete_Error");
		   e.printStackTrace();
		   _form.recordError(getMessages().get("Record_Delete_Error"));
		}
	}
}

void onActionFromtoolbarDel(Long id) //throws BusinessException
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
       CustomerDetail = getCustomerService().findCustomer(id);
       int_SelectedRow = getRcdLocation(id);
   }
	catch(BusinessException be)
	{
	
	}
	
	if(CustomerDetail!=null)
	{
		viewDisplayText="Block";
		viewEditText="none";
		assignToLocalVariable(CustomerDetail);
		//return blockFormView;
	}
	return blockFormView;
}
private ICustomerServiceRemote getCustomerService() {
   return getBusinessServicesLocator().getCustomerServiceRemote();
}
public List<Customer> getCustomers() {
   return _Customers;
}


public Customer getCustomer() throws BusinessException{
   return _Customer;
}


 public void setCustomer(Customer tb) {
   _Customer = tb;
}

}
