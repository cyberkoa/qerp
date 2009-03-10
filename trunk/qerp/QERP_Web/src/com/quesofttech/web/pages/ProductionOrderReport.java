package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.query.SearchOptions;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.exception.DuplicateAlternateKeyException;
import com.quesofttech.business.common.exception.DuplicatePrimaryKeyException;
import com.quesofttech.business.domain.general.UOM;
import com.quesofttech.business.domain.inventory.MaterialType;
import com.quesofttech.business.domain.inventory.iface.IMaterialTypeServiceRemote;
import com.quesofttech.business.domain.production.*;
import com.quesofttech.business.domain.production.iface.*;
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
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.InjectPage;
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
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.*;
public class ProductionOrderReport extends SecureBasePage {
	//===============================================================
	//			Production Order ComboBox
	//===============================================================
	@Property
	@Persist
	@SuppressWarnings("unused")
	private GenericSelectModel<ProductionOrder> _productionOrderSelect;	
	
	@Inject
    private PropertyAccess _access;
	
	
	//@Persist
	//private long lng_CurrentID;
	
	@Component(id = "ProductionOrderReportForm")
	private Form _form;
	
	
	private IProductionOrderServiceRemote getProductionOrderService() {
		return getBusinessServicesLocator().getProductionOrderServiceRemote();
	}

	//@Persist
	private ProductionOrder productionOrder;
	public ProductionOrder getProductionOrder()
	{
	   return productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder)
	{
	   this.productionOrder = productionOrder;
	}
	//===============================================================
	//				Production Order ComboBox
	//===============================================================
	private void RenderCombo()
	{
		System.out.println("Setup");
		List<ProductionOrder> productionOrders = null;
				
    	try {
    		productionOrders = this.getProductionOrderService().findProductionOrders(); 
    		if(productionOrders==null)
    			System.out.println("productoinOrders is null");
	       	_productionOrderSelect = null;
	       	_productionOrderSelect = new GenericSelectModel<ProductionOrder>(productionOrders,ProductionOrder.class,"DoctypeNo","id",_access);
	       	if(_productionOrderSelect==null){
	       		System.out.println("Setuprender productionOrderselect is null");
	       	}
    	}
    	catch(Exception e)
    	{
    		
    	}
    	finally  {
    		
    	}
	}
	public ProductionOrderReport()
	{
		//RenderCombo();
	}
	 void setupRender() 
	{
		productionOrder = new ProductionOrder();
		RenderCombo();
	}
	
	//Object onSuccessFromProductionOrderReportForm() throws BusinessException
	//{		
	//	return blockFormView;		
	//}
	@InjectPage
	private WorkOrderReport workorderreport;
	void onValidateForm() {
		System.out.println("onValidateForm(): " + productionOrder.getId());
		
	}
	Object onSuccess()
	{
		System.out.println("productionOrder.getId(): " + productionOrder.getId());
		workorderreport.setProductionOrderID(productionOrder.getId());
		return workorderreport;
	}
	

	//@OnEvent(component = "textfield", event="blur")
	//	public void onBlurEvent(String value)
	//	{
	//		System.out.println("here clicked: " + productionOrder.getId());
	//	}

		 
}
