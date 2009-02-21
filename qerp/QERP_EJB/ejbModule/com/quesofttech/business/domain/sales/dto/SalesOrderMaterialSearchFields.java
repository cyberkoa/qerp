package com.quesofttech.business.domain.sales.dto;


import java.io.Serializable;

import com.quesofttech.business.domain.base.dto.BaseSearchFields;
import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.sales.*;


//import java.util.Date;

@SuppressWarnings("serial")
public class SalesOrderMaterialSearchFields extends BaseSearchFields implements Serializable {

	private String docNo;
	private double qtyOrder;
	private double price;
	//private Customer customer = new Customer();
	private String customerCode = null;
    //private String material = new Material();
	private String material = null;
	private String recordStatus = null;
	private Integer version = null;

	/*
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("SalesOrderMaterialSearchFields:[");
		buff.append("qtyOrder=" + qtyOrder + ",");
		buff.append("price=" + price + ",");
		buff.append("customerCode=" + customerCode + ",");
		buff.append("material.Code=" + material.getCode() + ",");
		buff.append("recordStatus=" + recordStatus + "]");
		return buff.toString();
	}
	
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

	public double getQtyOrder() {
		return qtyOrder;
	}

	public void setQtyOrder(double qtyOrder) {
		this.qtyOrder = qtyOrder;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}





}
