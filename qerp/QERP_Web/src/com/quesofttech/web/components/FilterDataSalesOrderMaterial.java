
package com.quesofttech.web.components;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ComponentResources;

import org.apache.tapestry.commons.components.*;
import org.apache.tapestry5.json.JSONObject;
import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.sales.dto.SalesOrderMaterialSearchFields;
import com.quesofttech.web.base.SecureBasePage;



public class FilterDataSalesOrderMaterial extends SecureBasePage
{

	@Component(id = "lowerMaterialCode")
	private TextField _lowerMaterialCode;
	private Material lowerMaterialCode;

	
	@Component(id = "upperMaterialCode")
	private TextField _upperMaterialCode;
	private Material upperMaterialCode;
	
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
	
	void onSubmitFromSearchForm() {
		System.out.println("onActionFrombtnFilter");
		
		lowerSearchFields.setMaterial(lowerMaterialCode);		
		lowerSearchFields.setRecordStatus("A");
		lowerSearchFields.setPrice(lowerPrice);
		lowerSearchFields.setQtyOrder(lowerQtyOrder);

		upperSearchFields.setRecordStatus("A");
		upperSearchFields.setMaterial(upperMaterialCode);
		upperSearchFields.setPrice(upperPrice);
		upperSearchFields.setQtyOrder(upperQtyOrder);
		
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


		
}