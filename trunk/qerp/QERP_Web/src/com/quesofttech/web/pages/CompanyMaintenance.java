package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DuplicateAlternateKeyException;
import com.quesofttech.business.common.exception.DuplicatePrimaryKeyException;
import com.quesofttech.business.domain.finance.Company;
import com.quesofttech.business.domain.finance.iface.ICompanyServiceRemote;
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
import org.apache.tapestry5.annotations.OnEvent;
public class CompanyMaintenance extends SimpleBasePage {
	
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
	private Company CompanyDetail;
	private Company _Company;
	@Persist
	private List<Company> _Companys;
	@Inject
	private Logger _logger;
	@Inject
	private Block blockFormView;
	@Persist
	private long lng_CurrentID;
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
	
	@Component(id = "CompanyForm")
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
	// Text Component for code
	@Component(id = "code")
	private TextField _code;
	private String code;
	public String getcode()
	{
	   return code;
	}
	
	public void setcode(String code)
	{
	   this.code = code;
	}
	//===============================
	
	//===============================
	// Text Component for name
	@Component(id = "name")
	private TextField _name;
	private String name;
	public String getname()
	{
	   return name;
	}
	
	public void setname(String name)
	{
	   this.name = name;
	}
	//===============================
	
	//===============================
	// Text Component for area
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
	//===============================
	
	//===============================
	// Text Component for buildingLot
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
	//===============================
	
	//===============================
	// Text Component for city
	@Component(id = "city")
	private TextField _city;
	private String city;
	public String getcity()
	{
	   return city;
	}
	
	public void setcity(String city)
	{
	   this.city = city;
	}
	//===============================
	
	//===============================
	// Text Component for country
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
	//===============================
	
	//===============================
	// Text Component for postalCode
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
	//===============================
	
	//===============================
	// Text Component for state
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
	//===============================
	
	//===============================
	// Text Component for street
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
	//===============================
	void RefreshRecords()
	{
	   try
	   {
	       _Companys = getCompanyService().findCompanys();
	   }
	   catch(BusinessException be)
	   {
	
	   }
	   if(_Companys!=null && !_Companys.isEmpty())
	   {
	       if(int_SelectedRow==0)
	       {
	           CompanyDetail = _Companys.get(_Companys.size() - 1);
	       }
	       else
	       {
	           CompanyDetail = _Companys.get(int_SelectedRow - 1);
	       }
	       myState="U";
	      // CompanyDetail = _Companys.get(_Companys.size() - 1);
	       viewDisplayText="Block";
	       viewEditText="none";
	       assignToLocalVariable(CompanyDetail);
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
	   _Companys = getCompanyService().findCompanys();
	   for(Company p : _Companys)
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
		_form.clearErrors();
		//System.out.println("onFailure lah");
		_form.recordError(getMessages().get("Record_Save_Error"));
		return blockFormView;
	}
	Object onSuccess()
	{
	   _form.clearErrors();
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
	
	void assignToDatabase(Company company){
	   company.setId(id);
	   company.setCode(code);
	   company.setName(name);	  
	   company.getAddress().setArea(area);
	   company.getAddress().setBuildingLot(buildingLot);
	   company.getAddress().setCity(city);
	   company.getAddress().setCountry(country);
	   company.getAddress().setPostalCode(postalCode);
	   company.getAddress().setState(state);
	   company.getAddress().setStreet(street);
	   company.setRecordStatus("A");
	}
	void assignToLocalVariable(Company company)
	{
	   this.id = company.getId();
	   this.code = company.getCode();
	   this.name = company.getName();
	   this.area = company.getAddress().getArea();
	   this.buildingLot = company.getAddress().getBuildingLot();
	   this.city = company.getAddress().getCity();
	   this.country = company.getAddress().getCountry();
	   this.postalCode = company.getAddress().getPostalCode();
	   this.state = company.getAddress().getState();
	   this.street = company.getAddress().getStreet();
	}
	void _AddRecord()
	{
	   Company company = new Company();
	   try 
	   {
	           company.setModifyLogin(getVisit().getMyLoginId());
	           company.setCreateLogin(getVisit().getMyLoginId());
			   company.setModifyApp(this.getClass().getSimpleName());
			   company.setCreateApp(this.getClass().getSimpleName());
			   
	           assignToDatabase(company);
	           getCompanyService().addCompany(company);
	   }
		catch (BusinessException e) {
			if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
			{
				_form.recordError(_code, e.getLocalizedMessage());
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
	   Company company = new Company();
	   try
	   {
	       company = getCompanyService().findCompany(id);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	   if(company !=null)
	   {
	       try {
	           company.setModifyLogin(getVisit().getMyLoginId());
			   company.setModifyApp(this.getClass().getSimpleName());	           
	           
	           assignToDatabase(company);
	           getCompanyService().updateCompany(company);
	       }
			catch (BusinessException e) {
				if(e instanceof DuplicatePrimaryKeyException  || e instanceof DuplicateAlternateKeyException)
				{
					_form.recordError(_code, e.getLocalizedMessage());
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
	   Company company = new Company();
	   try
	   {
	       company = getCompanyService().findCompany(id);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	   if(company!=null)
	   {
	       try {
	           company.setModifyLogin(getVisit().getMyLoginId());
	           company.setModifyApp(this.getClass().getSimpleName());
	           
	           
	           getCompanyService().logicalDeleteCompany(company);
	           if(int_SelectedRow!=0)
	           {
	               int_SelectedRow--;
	           }
	           RefreshRecords();
	       }
	           catch (BusinessException e) {
	           _form.recordError(_code, e.getLocalizedMessage());
	   }
	       catch (Exception e) {
	           _logger.info("Company_Delete_problem");
	           e.printStackTrace();
	           _form.recordError(getMessages().get("Company_Delete_problem"));
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
	       CompanyDetail = getCompanyService().findCompany(id);
	       int_SelectedRow = getRcdLocation(id);
	   }
	   catch(BusinessException be)
	   {
	
	   }
	
	   if(CompanyDetail!=null)
	   {
	       viewDisplayText="Block";
	       viewEditText="none";
	       assignToLocalVariable(CompanyDetail);
	       return blockFormView;
	   }
	   return null;
	}
	
	private ICompanyServiceRemote getCompanyService() {
	   return getBusinessServicesLocator().getCompanyServiceRemote();
	}
	
	
	public List<Company> getCompanys() {
	   return _Companys;
	}
	
	
	public Company getCompany() throws BusinessException{
	   return _Company;
	}
	
	
	 public void setCompany(Company tb) {
	   _Company = tb;
	}

}
