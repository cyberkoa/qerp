package com.quesofttech.business.domain.sales.dto;


import java.io.Serializable;

import com.quesofttech.business.domain.base.dto.BaseSearchFields;
import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.sales.SalesOrder;
import com.quesofttech.business.domain.sales.Customer;


//import java.util.Date;

@SuppressWarnings("serial")
public class SalesOrderSearchFields extends BaseSearchFields implements Serializable {

	private String docNo;
	private Customer customer = new Customer();
	private String customerCode = null;
    private String material = null;
	private String recordStatus = null;
	private Integer version = null;

	/*
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("SalesOrderSearchFields: [");
		buf.append("record status=" + recordStatus + ", ");
		buf.append("version=" + version);
		buf.append("]");
		return buf.toString();
	}
    */


	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @return the material
	 */
	public String getMaterial() {
		return material;
	}

	/**
	 * @param material the material to set
	 */
	public void setMaterial(String material) {
		this.material = material;
	}

	/**
	 * @return the recordStatus
	 */
	public String getRecordStatus() {
		return recordStatus;
	}

	/**
	 * @param recordStatus the recordStatus to set
	 */
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the docNo
	 */
	public String getDocNo() {
		return docNo;
	}

	/**
	 * @param docNo the docNo to set
	 */
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}





}
