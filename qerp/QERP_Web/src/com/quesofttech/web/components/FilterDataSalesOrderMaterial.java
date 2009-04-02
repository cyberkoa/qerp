
package com.quesofttech.web.components;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ComponentResources;

import org.apache.tapestry.commons.components.*;
import org.apache.tapestry5.json.JSONObject;
import com.quesofttech.web.base.SecureBasePage;

import com.quesoware.business.domain.inventory.Material;
import com.quesoware.business.domain.sales.dto.SalesOrderMaterialSearchFields;

import com.quesofttech.web.base.SimpleBasePage;


public class FilterDataSalesOrderMaterial extends SimpleBasePage
{

	@Component(id = "lowerMaterialCode")
	private TextField _lowerMaterialCode;
	private String lowerMaterialCode;

	
	@Component(id = "upperMaterialCode")
	private TextField _upperMaterialCode;
	private String upperMaterialCode;
	
	@Component(id = "lowerQtyOrder")
	private TextField _lowerQtyOrder;
	private double lowerQtyOrder;
	
	@Component(id = "upperQtyOrder")
	private TextField _upperQtyOrder;
	private double upperQtyOrder;
	
	@Component(id = "lowerPrice")
	private TextField _lowerPrice;
	private double lowerPrice;
	
	@Component(id = "upperPrice")
	private TextField _upperPrice;
	private double upperPrice;
	
	private Long lowerSalesOrderId;
	private Long upperSalesOrderId;
	
    @Inject
    private ComponentResources resources;
	
    
    private SalesOrderMaterialSearchFields lowerSearchFields = new SalesOrderMaterialSearchFields();
    private SalesOrderMaterialSearchFields upperSearchFields = new SalesOrderMaterialSearchFields();
    
    
    /*
    * @param options windows option object
    */
    /*
   protected void configure(JSONObject options)
   {
       options.put("minimizable", false);
       options.put("title", "FilterData");
       //options.put("url", "http://www.google.com");
   }
    */
    void injectResources(ComponentResources resources)
    {
        this.resources = resources;
        System.out.println("in injectResources");
        //resources.triggerContextEvent(arg0, arg1, arg2)
    }
    void onFailure()
    {
    	System.out.println("here failed");
    }
	
	void onSubmitFromSearchForm() {
		System.out.println("onActionFrombtnFilter");
		//Material lowmat = new Material();
		//Material upper = new Material();
		System.out.println("onActionFrombtnFilter1");
		//lowmat.setCode(lowerMaterialCode);		
		System.out.println("onActionFrombtnFilter2");
		lowerSearchFields.setMaterial(lowerMaterialCode);		
		lowerSearchFields.setRecordStatus("A");
		lowerSearchFields.setPrice(lowerPrice);

		lowerSearchFields.setQuantityOrder(lowerQtyOrder);

		System.out.println("onActionFrombtnFilter3");

		
		//upper.setCode(upperMaterialCode);
		System.out.println("onActionFrombtnFilter5");
		upperSearchFields.setRecordStatus("A");
		upperSearchFields.setMaterial(upperMaterialCode);
		System.out.println("onActionFrombtnFiltery");
		upperSearchFields.setPrice(upperPrice);

		upperSearchFields.setQuantityOrder(upperQtyOrder);
		//upperSearchFields.setQtyOrder(upperQtyOrder);

		//System.out.println("Upperfield:" + upperSearchFields.toString());
		// Trigger the container event "onFilterData"
		resources.triggerEvent("filterDataSalesOrderMaterial", new Object[] {}, null);
		
		System.out.println("onSubmitFromSearchForm");
		//resources.getContainer().`
		//return null;
	}

	/**
	 * @return the lowerSearchFields
	 */
	public SalesOrderMaterialSearchFields getLowerSearchFields() {
		return lowerSearchFields;
	}

	/**
	 * @param lowerSearchFields the lowerSearchFields to set
	 */
	public void setLowerSearchFields(SalesOrderMaterialSearchFields lowerSearchFields) {
		this.lowerSearchFields = lowerSearchFields;
	}

	/**
	 * @return the upperSearchFields
	 */
	public SalesOrderMaterialSearchFields getUpperSearchFields() {
		return upperSearchFields;
	}

	/**
	 * @param upperSearchFields the upperSearchFields to set
	 */
	public void setUpperSearchFields(SalesOrderMaterialSearchFields upperSearchFields) {
		this.upperSearchFields = upperSearchFields;
	}



	void onValidateFromSearchForm() {
		System.out.println("onSuccessFromSearchForm");

		// Trigger the container event "onSuccessFromFilterData"
		resources.triggerEvent("successFromFilterDataSalesOrderMaterial", new Object[] {}, null);
		
		//return null;
	}

	public String getLowerMaterialCode() {
		return lowerMaterialCode;
	}

	public void setLowerMaterialCode(String lowerMaterialCode) {
		this.lowerMaterialCode = lowerMaterialCode;
	}

	public String getUpperMaterialCode() {
		return upperMaterialCode;
	}

	public void setUpperMaterialCode(String upperMaterialCode) {
		this.upperMaterialCode = upperMaterialCode;
	}

	public double getLowerQtyOrder() {
		return lowerQtyOrder;
	}

	public void setLowerQtyOrder(double lowerQtyOrder) {
		this.lowerQtyOrder = lowerQtyOrder;
	}

	public double getUpperQtyOrder() {
		return upperQtyOrder;
	}

	public void setUpperQtyOrder(double upperQtyOrder) {
		this.upperQtyOrder = upperQtyOrder;
	}

	public double getLowerPrice() {
		return lowerPrice;
	}

	public void setLowerPrice(double lowerPrice) {
		this.lowerPrice = lowerPrice;
	}

	public double getUpperPrice() {
		return upperPrice;
	}

	public void setUpperPrice(double upperPrice) {
		this.upperPrice = upperPrice;
	}
	/**
	 * @return the lowerSalesOrderId
	 */
	public Long getLowerSalesOrderId() {
		return lowerSalesOrderId;
	}
	/**
	 * @param lowerSalesOrderId the lowerSalesOrderId to set
	 */
	public void setLowerSalesOrderId(Long lowerSalesOrderId) {
		this.lowerSalesOrderId = lowerSalesOrderId;
	}
	/**
	 * @return the upperSalesOrderId
	 */
	public Long getUpperSalesOrderId() {
		return upperSalesOrderId;
	}
	/**
	 * @param upperSalesOrderId the upperSalesOrderId to set
	 */
	public void setUpperSalesOrderId(Long upperSalesOrderId) {
		this.upperSalesOrderId = upperSalesOrderId;
	}


		
}