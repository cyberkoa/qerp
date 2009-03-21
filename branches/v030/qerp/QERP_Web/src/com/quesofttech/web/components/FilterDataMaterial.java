
package com.quesofttech.web.components;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ComponentResources;

import org.apache.tapestry.commons.components.*;
import org.apache.tapestry5.json.JSONObject;
import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.inventory.dto.MaterialSearchFields;
import com.quesofttech.business.domain.sales.dto.SalesOrderMaterialSearchFields;
import com.quesofttech.web.base.SecureBasePage;
import com.quesofttech.web.base.SimpleBasePage;

public class FilterDataMaterial extends SimpleBasePage
{

	@Component(id = "lowerCode")
	private TextField _lowerCode;
	private String lowerCode;

	
	@Component(id = "upperCode")
	private TextField _upperCode;
	private String upperCode;
	
	@Component(id = "lowerDescription")
	private TextField _lowerDescription;
	private String lowerDescription;
	
	@Component(id = "upperDescription")
	private TextField _upperDescription;
	private String upperDescription;
	
	@Component(id = "lowerType")
	private TextField _lowerType;
	private String lowerType;
	
	@Component(id = "upperType")
	private TextField _upperType;
	private String upperType;
	
	private Long lowerId;
	private Long upperId;
	
    @Inject
    private ComponentResources resources;
	
    
    private MaterialSearchFields lowerSearchFields = new MaterialSearchFields();
    private MaterialSearchFields upperSearchFields = new MaterialSearchFields();
    
    void injectResources(ComponentResources resources)
    {
        this.resources = resources;        
    }
    void onFailure()
    {
    	System.out.println("here failed");
    }
	
	void onSubmitFromSearchForm() {
		
		lowerSearchFields.setCode(lowerCode);		
		lowerSearchFields.setRecordStatus("A");
		lowerSearchFields.setDescription(lowerDescription);
		lowerSearchFields.setType(lowerType);
		
		upperSearchFields.setRecordStatus("A");
		upperSearchFields.setCode(upperCode);
		upperSearchFields.setDescription(upperDescription);
		upperSearchFields.setType(upperType);
		
		resources.triggerEvent("filterDataMaterial", new Object[] {}, null);
		
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



	void onValidateFromSearchForm() {		
		resources.triggerEvent("successFromFilterDataMaterial", new Object[] {}, null);		
	}
	public String getLowerCode() {
		return lowerCode;
	}
	public void setLowerCode(String lowerCode) {
		this.lowerCode = lowerCode;
	}
	public String getUpperCode() {
		return upperCode;
	}
	public void setUpperCode(String upperCode) {
		this.upperCode = upperCode;
	}
	public String getLowerDescription() {
		return lowerDescription;
	}
	public void setLowerDescription(String lowerDescription) {
		this.lowerDescription = lowerDescription;
	}
	public String getUpperDescription() {
		return upperDescription;
	}
	public void setUpperDescription(String upperDescription) {
		this.upperDescription = upperDescription;
	}
	public String getLowerType() {
		return lowerType;
	}
	public void setLowerType(String lowerType) {
		this.lowerType = lowerType;
	}
	public String getUpperType() {
		return upperType;
	}
	public void setUpperType(String upperType) {
		this.upperType = upperType;
	}
	public Long getLowerId() {
		return lowerId;
	}
	public void setLowerId(Long lowerId) {
		this.lowerId = lowerId;
	}
	public Long getUpperId() {
		return upperId;
	}
	public void setUpperId(Long upperId) {
		this.upperId = upperId;
	}



		
}