package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.query.SearchOptions;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.exception.DuplicateAlternateKeyException;
import com.quesofttech.business.common.exception.DuplicatePrimaryKeyException;
import com.quesofttech.business.domain.inventory.MaterialType;
import com.quesofttech.business.domain.inventory.iface.IMaterialTypeServiceRemote;
import com.quesofttech.business.domain.sales.SalesOrder;
import com.quesofttech.business.domain.sales.SalesOrderMaterial;
import com.quesofttech.business.domain.sales.dto.SalesOrderSearchFields;
import com.quesofttech.business.domain.sales.iface.ISalesOrderServiceRemote;
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
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
import com.quesofttech.business.domain.sales.*;
import com.quesofttech.web.components.*;

import java.util.*;
import org.apache.tapestry5.ioc.services.*;

import com.quesofttech.business.domain.sales.Customer;
import com.quesofttech.business.domain.sales.iface.ICustomerServiceRemote;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.beaneditor.BeanModel;

import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesofttech.business.common.exception.DoesNotExistException;

import com.quesofttech.business.domain.sales.dto.SalesOrderSearchFields;

import com.sun.org.apache.bcel.internal.generic.NEW;

import org.apache.tapestry.commons.components.*;

interface Delegate
{
	List<?> bindData();
}



public class SalesOrderMaintenance extends SecureBasePage {
	 @Component(parameters = {"width=300", "height=300", 
			 "style=bluelighting", "show=false","modal=true", "title=literal:Filter Window"})
	@Property
	private QERPWindow _filterWindow;
	 
	private void refreshDisplay()  throws Exception
    {
		ModelRefresh();
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
	
	
	
	@Inject
    private BeanModelSource beanModelSource;

	@Inject
    private ComponentResources modelResources;
	
	@SuppressWarnings("unchecked")
	@Property
	@Retain
	private BeanModel _salesOrderModel;
	
	
	
/*
	void onActivate()
	{
		System.out.println("onactivate");
	}
*/
	/*
	 @Component(parameters = {"width=300", "height=300", "style=bluelighting", "show=false"})
	    @Property
	    private Window _window2;
*/
	 @Component
	 @Property
	 private FilterData _filterData;
	 
	 
	 @Persist
	 @Property
	 private SalesOrderSearchFields lowerSFs;
	 
	 @Persist
	 @Property
	 private SalesOrderSearchFields upperSFs;
	 
	Object onFailure()
	{
		//System.out.println("onFailure lah");
		//RefreshRecords();
		_form.recordError("Page having error.  Please select/Add record and save again.");
		return blockFormView;
	}
	private String viewDisplayText="", viewEditText="";
	
	public String getViewDisplayText()  throws Exception
	{
		refreshDisplay();
		return viewDisplayText;
	}

	public String getviewEditText() throws Exception
	{
		refreshDisplay();
		return viewEditText;
	}
	
	
    @Inject
    private PropertyAccess _access;
	
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<Customer> _customers;	
	
	//@Component(id = "Customer")
	//private TextField _Customer;
	
	@Persist
	private Customer customer;
	
	
    @Inject
    private ComponentResources resources;
	
	 @InjectPage
	 private SalesOrderMaterialMaintenance salesorderDetail1;
	 Object onActionFrombtnDetail(Long id)  throws Exception
	 {
		 _form.clearErrors();
		 salesorderDetail1.setHeaderID(id);
	    return salesorderDetail1;
	 }
 
	
	public SalesOrderMaintenance() throws BusinessException{

		//_filterData.injectResources(resources);
		
		/*
		List<Customer> list = null;
    	try {
           list = this.getCustomerService().findCustomers();
    	}
    	catch (DoesNotExistException e) {}
    	
        _customers = new GenericSelectModel<Customer>(list,Customer.class,"name","id",_access);
        */

	}
	


		private String _strMode = "";
	
		@Persist
		private SalesOrder SalesOrderDetail;
		
		private SalesOrder _SalesOrder;
		
		@Persist
		private List<SalesOrder> _SalesOrders;
		
		@Inject
		private Logger _logger;
		
		@Inject
		private Block blockFormView;
		
		@Persist
		private long lng_CurrentID;
		
		@Component(id = "SalesOrderForm")
		private Form _form;
		
		@Persist
		private int int_SelectedRow;
		
		@ApplicationState
		private String myState;
		
		@Component(id = "grid")
		private Grid _grid;
		
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
		
		@Component(id = "CustomerPO")
		private TextField _CustomerPO;
		
		private String CustomerPO;
		public String getCustomerPO()
		{
		   return CustomerPO;
		}
		
		public void setCustomerPO(String CustomerPO)
		{
		   this.CustomerPO = CustomerPO;
		}
		
		@Component(id = "Number")
		private TextField _Number;
		
		private long Number;
		public long getNumber()
		{
		   return Number;
		}
		
		public void setNumber(long Number)
		{
		   this.Number = Number;
		}
		/*
		@Component(id = "DocType")
		private TextField _DocType;
		*/
		private String DocType;
		public String getDocType()
		{
		   return DocType;
		}
		public void setDocType(String DocType)
		{
		   this.DocType = DocType;
		}
		

		public String getCustomeDesc()
		{
			String _temp = "";
			if (customer!=null)
			{
				_temp = customer.getCodeName();
			}
			return _temp;
				
				
		}
		
		public Customer getCustomer()
		{
			
		   return customer;
		}
		public void setCustomer(Customer customer)
		{
			
		   this.customer = customer;
		}
		
		void RefreshRecords()  
		{
			RefreshRecords( new Delegate() 
			{ public List<SalesOrder> bindData() 
				{ 
				try { 
					 if(lowerSFs==null&&upperSFs==null)
					   return getSalesOrderService().findSalesOrders();
					 else
					 {
                    	SearchOptions options = new SearchOptions();
                	 
                	    return getSalesOrderService().findSalesOrdersBySearchFieldsRange(_filterData.getLowerSearchFields(), _filterData.getUpperSearchFields(),options);
					 } 
				    } 
				catch(BusinessException be) {} 
				
				return null;} 
			    }
			);
		}
		void ModelRefresh()
		{
			//if(_salesOrderModel==null){
				_salesOrderModel = beanModelSource.createDisplayModel(SalesOrder.class,modelResources.getMessages());
				_salesOrderModel.add("SalesOrderDetailSelect",null);
				_salesOrderModel.get("SalesOrderDetailSelect").label("Detail");
				//_salesOrderMaterialModel.
//				_salesOrderMaterialModel.include(//arg0)
				
			//}
		}
		void RefreshRecords(Delegate delegate)
		{
			System.out.println("refresh 1");
			ModelRefresh();
			
			System.out.println("refresh 2");
			
			List<Customer> list = null;
	    	try {
	           list = this.getCustomerService().findCustomers();
	    	}
	    	catch (DoesNotExistException e) {}
	    	
	        _customers = new GenericSelectModel<Customer>(list,Customer.class,"codeName","id",_access);

			//try
			//{
			   //_SalesOrders = getSalesOrderService().findSalesOrders();
				_SalesOrders = (List<SalesOrder>)delegate.bindData();
			//}
			//catch(BusinessException be)
			//{
			
			//}
			
			if(_SalesOrders!=null && !_SalesOrders.isEmpty())
			{	   
				
			   System.out.println("Total record selected : " + _SalesOrders.size());
			   if(int_SelectedRow==0)
				{
				   SalesOrderDetail = _SalesOrders.get(_SalesOrders.size() - 1);
				}
				else
				{
					// Added size checek again before really pointing to the particular record.
					if(_SalesOrders.size()<int_SelectedRow)
						int_SelectedRow = 1; // Once Smaller always reset to pointing to first record.
					System.out.println("error is here: " + int_SelectedRow);
					SalesOrderDetail = _SalesOrders.get(int_SelectedRow - 1);
					
				}	
			   myState="U";
				viewDisplayText="Block";
				viewEditText="none";
			   assignToLocalVariable(SalesOrderDetail);
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
		   System.out.println("No record:" + _SalesOrders.size());
		   //_SalesOrders = getSalesOrderService().findSalesOrders();
		   if(_SalesOrders!=null)
			   System.out.println("_salesOrders getrcdlocation:" + _SalesOrders.toString());
		   else
			   System.out.println("_salesOrders getrcdlocation: null");
		   for(SalesOrder p : _SalesOrders)
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
		
		
		Object onSuccessFromSalesOrderForm()
		{
			_form.clearErrors();
		   RefreshRecords();
		   return blockFormView;
		}
		
		
		void setupRender() {
		   
		   //_filterData.injectResources(resources);
		   ModelRefresh();
		   RefreshRecords();
		}
		
		
		void onValidateFormFromSalesOrderForm()  {
			System.out.println("onValidateForm : " + myState);
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
				else
				if("F" == myState)
				{
					_FilterRecord();
				}
			}
			catch (Exception e)
			{
				_form.recordError(e.getMessage());
			}
		}
		
		void assignToDatabase(SalesOrder salesOrder){
		   salesOrder.setId(id);
		   salesOrder.setCustomerPO(CustomerPO);
		   //salesOrder.setDocNo(Number);
		   salesOrder.setDocType(DocType);
		   //salesOrder.setversion(version);
		   salesOrder.setCustomer(customer);
		   //salesOrder.setRecordStatus("A");
		   salesOrder.setRecordStatus("A");
		}

		void assignToLocalVariable(SalesOrder salesOrder)
		{
		   this.id = salesOrder.getId();
		   this.CustomerPO = salesOrder.getCustomerPO();
		   this.Number = salesOrder.getDocNo();
		   this.DocType = salesOrder.getDocType();
		  //this.version = salesOrder.getversion();
		   this.customer = salesOrder.getCustomer();
		}
		
		void _AddRecord()
		{
			SalesOrder salesOrder = new SalesOrder();
			try 
			{				
				salesOrder.setDocNo(0L);
				salesOrder.setCreateLogin(getVisit().getMyLoginId());
				salesOrder.setModifyLogin(getVisit().getMyLoginId());
				salesOrder.setCreateApp(this.getClass().getSimpleName());
				salesOrder.setModifyApp(this.getClass().getSimpleName());
				
			   assignToDatabase(salesOrder);
			   getSalesOrderService().addSalesOrder(salesOrder);
		    }
			
			catch (Exception e) {
				_form.recordError(e.getMessage());
			}
		}
		
		void _UpdateRecord(){
			SalesOrder salesOrder = new SalesOrder();
			try
			{
			   salesOrder = getSalesOrderService().findSalesOrder(id);
			}
		    catch(BusinessException be)
		    {
		    	_form.recordError(be.getMessage());
		    }
			if(salesOrder !=null)
			{
				try {
					//salesOrder.setCreateLogin(getVisit().getMyLoginId());
					salesOrder.setModifyLogin(getVisit().getMyLoginId());
					salesOrder.setModifyApp(this.getClass().getSimpleName());	 
					
					
					assignToDatabase(salesOrder);
					getSalesOrderService().updateSalesOrder(salesOrder);
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
				SalesOrder salesOrder = new SalesOrder();
				try
				{
				   salesOrder = getSalesOrderService().findSalesOrder(id);
				}
				   catch(BusinessException be)
					{
					   _form.recordError(be.getMessage());
					}
				
				if(salesOrder!=null)
				{
				   try {
				   
					   int_SelectedRow=getRcdLocation(id);
					   //salesOrder.setCreateLogin(getVisit().getMyLoginId());
					   salesOrder.setModifyLogin(getVisit().getMyLoginId());
					   salesOrder.setModifyApp(this.getClass().getSimpleName());
					   
					   getSalesOrderService().logicalDeleteSalesOrder(salesOrder);
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
		
		void onActionFromtoolbarDel(Long id) throws Exception
		{
			if (id!=null) {
				_form.clearErrors();
				myState = "D";
				_strMode = "D";		
				_DeleteRecord(id);
			}	   		   
		}
		
		Object onActionFromToolbarAdd () throws Exception
		{
			_form.clearErrors();
			customer=null;
		   myState = "A";
		   _strMode = "A";
		   
		   //onSelectedFromMinhong();
		   return blockFormView;
		}
		
		Object onActionFromSelect(long id) throws Exception
		{
			
			System.out.println("[onActionFromSelect] Id = " + id);
			_form.clearErrors();
		   myState = "U";
		   _strMode = "U";
		   lng_CurrentID = id;
		   try
		   {
		       SalesOrderDetail = getSalesOrderService().findSalesOrder(id);
		       int_SelectedRow = getRcdLocation(id);
		   }
			catch(BusinessException be)
			{
			
			}
		
			if(SalesOrderDetail!=null)
			{
				assignToLocalVariable(SalesOrderDetail);
				viewDisplayText="Block";
				viewEditText="none";
				
			}
			
			if(blockFormView==null) System.out.println("blockFormView is null");
			
			return blockFormView;
		}
		
		
		private ISalesOrderServiceRemote getSalesOrderService() {
		   return getBusinessServicesLocator().getSalesOrderServiceRemote();
		}
		
		private ICustomerServiceRemote getCustomerService() {
			return getBusinessServicesLocator().getCustomerServiceRemote();
		}
		
		public List<SalesOrder> getSalesOrders() {
		   return _SalesOrders;
		}
		
		
		public SalesOrder getSalesOrder() throws BusinessException{
		   return _SalesOrder;
		}
		
		
		 public void setSalesOrder(SalesOrder tb) {
		   _SalesOrder = tb;
		}
 
		 public void onFilterData()
		 {
		   //SalesOrderSearchFields lowerSearchFields = null;
		   //SalesOrderSearchFields upperSearchFields = null;
		   
		   System.out.println("Yes , trigger");
		   
		   lowerSFs = _filterData.getLowerSearchFields();
		   upperSFs = _filterData.getUpperSearchFields();
		   
		   System.out.println("lowerSearchFields DocNo: " + lowerSFs.getDocNo());
		   System.out.println("upperSearchFields DocNo: " + upperSFs.getDocNo());
		   //myState = "F";
		   //_strMode = "F";
		   
		   /*
		   RefreshRecords( new Delegate() 
		                      { 
			                   public List<SalesOrder> bindData() 
			                    { try 
			                       { 
			                    	 SearchOptions options = new SearchOptions();
			                    	 
			                    	 return getSalesOrderService().findSalesOrdersBySearchFieldsRange(_filterData.getLowerSearchFields(), _filterData.getUpperSearchFields(),options);
			                       } 
			                       catch(BusinessException be) {} 
			                       
			                       return null;
			                   } 
			                  });
		   		*/
		 }

		 void _FilterRecord()
		 {
			 System.out.println("Yes , _FilterRecord");
			   SalesOrderSearchFields lowerSearchFields = null;
			   SalesOrderSearchFields upperSearchFields = null;
			   			   
			   lowerSearchFields = _filterData.getLowerSearchFields();
			   upperSearchFields = _filterData.getUpperSearchFields();
			   
			   System.out.println("_Filter : lowerSearchFields DocNo: " + lowerSearchFields.getDocNo());
			   System.out.println("_Filter : upperSearchFields DocNo: " + upperSearchFields.getDocNo());

			 
		 }
		 
		 
		 
}
