
package com.quesofttech.web.components;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ComponentResources;

import org.apache.tapestry.commons.components.*;
import org.apache.tapestry5.json.JSONObject;

import com.quesofttech.web.base.SecureBasePage;

import com.quesoware.business.domain.inventory.dto.MaterialSearchFields;

import com.quesofttech.web.base.*;


public class FilterMaterial extends SimpleBasePage
{

	@Component(id = "lowerDocNo")
	private TextField _lowerDocNo;
	private String lowerDocNo;

	
	@Component(id = "upperDocNo")
	private TextField _upperDocNo;
	private String upperDocNo;
	
	@Component(id = "lowerCustomerCode")
	private TextField _lowerCustomerCode;
	private String lowerCustomerCode;
	
	@Component(id = "upperCustomerCode")
	private TextField _upperCustomerCode;
	private String upperCustomerCode;
	
	
    @Inject
    private ComponentResources resources;
	
    
    private MaterialSearchFields lowerSearchFields = new MaterialSearchFields();
    private MaterialSearchFields upperSearchFields = new MaterialSearchFields();
    
    
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
        //System.out.println("in injectResources");
        //resources.triggerContextEvent(arg0, arg1, arg2)
    }
	
	void onSubmitFromSearchForm() {
		//System.out.println("onActionFrombtnFilter");
		//System.out.println("lowerDocNo : " + lowerDocNo.toString());
		//System.out.println("upperDocNo : " + upperDocNo.toString());
		/*
		lowerSearchFields.setDocNo(lowerDocNo);
		lowerSearchFields.setRecordStatus("A");

		upperSearchFields.setDocNo(upperDocNo);
		upperSearchFields.setRecordStatus("A");
*/
		// Trigger the container event "onFilterData"
		resources.triggerEvent("filterData", new Object[] {}, null);
		
		System.out.println("onSubmitFromSearchForm");
		//resources.getContainer().`
		//return null;
	}

	/**
	 * @return the lowerSearchFields
	 */
	public MaterialSearchFields getLowerSearchFields() {
		return lowerSearchFields;
	}

	/**
	 * @param lowerSearchFields the lowerSearchFields to set
	 */
	public void setLowerSearchFields(MaterialSearchFields lowerSearchFields) {
		this.lowerSearchFields = lowerSearchFields;
	}

	/**
	 * @return the upperSearchFields
	 */
	public MaterialSearchFields getUpperSearchFields() {
		return upperSearchFields;
	}

	/**
	 * @param upperSearchFields the upperSearchFields to set
	 */
	public void setUpperSearchFields(MaterialSearchFields upperSearchFields) {
		this.upperSearchFields = upperSearchFields;
	}



	/**
	 * @return the lowerDocNo
	 */
	public String getLowerDocNo() {
		return lowerDocNo;
	}

	/**
	 * @param lowerDocNo the lowerDocNo to set
	 */
	public void setLowerDocNo(String lowerDocNo) {
		this.lowerDocNo = lowerDocNo;
	}

	/**
	 * @return the upperDocNo
	 */
	public String getUpperDocNo() {
		return upperDocNo;
	}

	/**
	 * @param upperDocNo the upperDocNo to set
	 */
	public void setUpperDocNo(String upperDocNo) {
		this.upperDocNo = upperDocNo;
	}

	/**
	 * @return the lowerCustomerCode
	 */
	public String getLowerCustomerCode() {
		return lowerCustomerCode;
	}

	/**
	 * @param lowerCustomerCode the lowerCustomerCode to set
	 */
	public void setLowerCustomerCode(String lowerCustomerCode) {
		this.lowerCustomerCode = lowerCustomerCode;
	}

	/**
	 * @return the upperCustomerCode
	 */
	public String getUpperCustomerCode() {
		return upperCustomerCode;
	}

	/**
	 * @param upperCustomerCode the upperCustomerCode to set
	 */
	public void setUpperCustomerCode(String upperCustomerCode) {
		this.upperCustomerCode = upperCustomerCode;
	}

	void onValidateFromSearchForm() {
		//System.out.println("onSuccessFromSearchForm");

		// Trigger the container event "onSuccessFromFilterData"
		resources.triggerEvent("successFromFilterData", new Object[] {}, null);
		
		//return null;
	}

		
}