
package com.quesofttech.web.components;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ComponentResources;

import org.apache.tapestry.commons.components.*;
import org.apache.tapestry5.json.JSONObject;

import com.quesofttech.business.domain.sales.dto.SalesOrderSearchFields;
import com.quesofttech.web.base.SecureBasePage;



public class FilterData extends SecureBasePage
{

	@Component(id = "lowerDocNo")
	private TextField _lowerDocNo;
	private Long lowerDocNo;

	
	@Component(id = "upperDocNo")
	private TextField _upperDocNo;
	private Long upperDocNo;
	
	@Component(id = "lowerCustomerCode")
	private TextField _lowerCustomerCode;
	private String lowerCustomerCode;
	
	@Component(id = "upperCustomerCode")
	private TextField _upperCustomerCode;
	private String upperCustomerCode;
	
	
	@Component(id = "lowerCustomerPO")
	private TextField _lowerCustomerPO;
	private String lowerCustomerPO;
	
	@Component(id = "upperCustomerPO")
	private TextField _upperCustomerPO;
	private String upperCustomerPO;
	
	
    @Inject
    private ComponentResources resources;
	
    
    private SalesOrderSearchFields lowerSearchFields = new SalesOrderSearchFields();
    private SalesOrderSearchFields upperSearchFields = new SalesOrderSearchFields();
    
    
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
		//System.out.println("lowerDocNo : " + lowerDocNo.toString());
		//System.out.println("upperDocNo : " + upperDocNo.toString());
		
		lowerSearchFields.setCustomerCode(lowerCustomerCode);
		upperSearchFields.setCustomerCode(upperCustomerCode);
		
		
		lowerSearchFields.setDocNo(lowerDocNo);
		lowerSearchFields.setRecordStatus("A");

		upperSearchFields.setDocNo(upperDocNo);
		upperSearchFields.setRecordStatus("A");
		
		lowerSearchFields.setCustomerPO(lowerCustomerPO);

		upperSearchFields.setCustomerPO(upperCustomerPO);
		// Trigger the container event "onFilterData"
		resources.triggerEvent("filterData", new Object[] {}, null);
		
		System.out.println("onSubmitFromSearchForm");
		//resources.getContainer().`
		//return null;
	}

	public String getLowerCustomerPO() {
		return lowerCustomerPO;
	}

	public void setLowerCustomerPO(String lowerCustomerPO) {
		this.lowerCustomerPO = lowerCustomerPO;
	}

	public String getUpperCustomerPO() {
		return upperCustomerPO;
	}

	public void setUpperCustomerPO(String upperCustomerPO) {
		this.upperCustomerPO = upperCustomerPO;
	}

	/**
	 * @return the lowerSearchFields
	 */
	public SalesOrderSearchFields getLowerSearchFields() {
		return lowerSearchFields;
	}

	/**
	 * @param lowerSearchFields the lowerSearchFields to set
	 */
	public void setLowerSearchFields(SalesOrderSearchFields lowerSearchFields) {
		this.lowerSearchFields = lowerSearchFields;
	}

	/**
	 * @return the upperSearchFields
	 */
	public SalesOrderSearchFields getUpperSearchFields() {
		return upperSearchFields;
	}

	/**
	 * @param upperSearchFields the upperSearchFields to set
	 */
	public void setUpperSearchFields(SalesOrderSearchFields upperSearchFields) {
		this.upperSearchFields = upperSearchFields;
	}



	/**
	 * @return the lowerDocNo
	 */
	public Long getLowerDocNo() {
		return lowerDocNo;
	}

	/**
	 * @param lowerDocNo the lowerDocNo to set
	 */
	public void setLowerDocNo(Long lowerDocNo) {
		this.lowerDocNo = lowerDocNo;
	}

	/**
	 * @return the upperDocNo
	 */
	public Long getUpperDocNo() {
		return upperDocNo;
	}

	/**
	 * @param upperDocNo the upperDocNo to set
	 */
	public void setUpperDocNo(Long upperDocNo) {
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
		System.out.println("onSuccessFromSearchForm");

		// Trigger the container event "onSuccessFromFilterData"
		resources.triggerEvent("successFromFilterData", new Object[] {}, null);
		
		//return null;
	}

		
}