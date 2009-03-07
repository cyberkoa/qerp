package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.exception.DuplicateAlternateKeyException;
import com.quesofttech.business.common.exception.DuplicatePrimaryKeyException;

import com.quesofttech.web.common.ContextFixer;
import com.quesofttech.business.domain.finance.iface.ICompanyServiceRemote;
import com.quesofttech.business.domain.general.*;
import com.quesofttech.business.domain.general.iface.IUomServiceRemote;
import com.quesofttech.business.domain.inventory.*;
import com.quesofttech.business.domain.production.*;
import com.quesofttech.business.domain.production.iface.IProductionOrderServiceRemote;
import com.quesofttech.business.domain.inventory.iface.*;
import com.quesofttech.business.domain.sales.SalesOrderMaterial;
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
import org.apache.tapestry5.corelib.components.Select;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;


import com.quesofttech.web.model.base.GenericSelectModel;
//import com.quesofttech.web.model.MaterialTypeSelectModel;



public class WorkOrderReport extends SecureBasePage {
	public String getCodeNo()
	{
		return _rawmat.CodeNo;
	}
	public void setCodeNo(String CodeNo)
	{
		_rawmat.CodeNo = CodeNo;
	}
	
	public String getType()
	{
		return _rawmat.Type;
	}
	public void setType(String Type)
	{
		_rawmat.Type = Type;
	}
	public Double getWidth()
	{
		return _rawmat.Width;
	}
	public void setWidth(Double Width)
	{
		_rawmat.Width = Width;		
	}
	
	public Double getLength()
	{
		return _rawmat.Length;
	}
	public void setLength(Double Length)
	{
		_rawmat.Length = Length;		
	}
	
	public Double getQuantity()
	{
		return _rawmat.Quantity;
	}
	public void setQuantity(Double Quantity)
	{
		_rawmat.Quantity = Quantity;
	}
	public String getProcess()
	{
		return _rawmat.Process;
	}
	public void setProcess(String Process)
	{
		_rawmat.Process = Process;
	}
	public String getProductionDate()
	{
		return _rawmat.ProductionDate;
	}
	public void setProductionDate(String ProductionDate)
	{
		_rawmat.ProductionDate = ProductionDate;
	}
	
	public Double getThickness()
	{
		return _rawmat.Thickness;
	}
	public void setThickness(Double Thickness)
	{
		_rawmat.Thickness = Thickness;
	}
	public class rawmaterial
	{
		public String CodeNo="";
		public String Type="";
		public Double Thickness=0.0;
		public Double Width=0.0;
		public Double Length=0.0;
		public Double Quantity=0.0;
		public String Process="";
		public String ProductionDate="";
	}
	
	private String printOrderNo;
	private String printOrderDate;
	private String printPOno;
	private String printDeliveryDate;
	private String printCustomerNo;

	private Material materiallists;
	@Persist
	private Long _productionOrderid;	
	private List<String> _titles;
	@Persist
	private List<?> _materiallists;	
	@Persist
	private SalesOrderMaterial _salesOrderMaterial;
	@Persist
	private ProductionOrder _productionOrder;
	@Persist
	private List<ProductionOrderMaterial> _productionOrderMaterial;
	//@Persist
	private List<rawmaterial> rawmatlist = new ArrayList<rawmaterial>();
	@Property
	private rawmaterial _rawmat;
	
	
	//public void setRawMat(rawmaterial _rawmat)
	//{
	//	this._rawmat = _rawmat;
	//}
	public List<rawmaterial> getRawMats()
	{		
		if(rawmatlist==null)
		{
			System.out.println("rawmatlist is null");
		}
		else
			System.out.println("output:" + rawmatlist.size());
		return rawmatlist;
	}
	
	
	public String getPrintOrderNo() {
		return printOrderNo;
	}
	public void setPrintOrderNo(String printOrderNo) {
		this.printOrderNo = printOrderNo;
	}
	public String getPrintOrderDate() {
		return printOrderDate;
	}
	public void setPrintOrderDate(String printOrderDate) {
		this.printOrderDate = printOrderDate;
	}
	public String getPrintPOno() {
		return printPOno;
	}
	public void setPrintPOno(String printPOno) {
		this.printPOno = printPOno;
	}
	public String getPrintDeliveryDate() {
		return printDeliveryDate;
	}
	public void setPrintDeliveryDate(String printDeliveryDate) {
		this.printDeliveryDate = printDeliveryDate;
	}
	public String getPrintCustomerNo() {
		return printCustomerNo;
	}
	public void setPrintCustomerNo(String printCustomerNo) {
		this.printCustomerNo = printCustomerNo;
	}
	public String getCompDesc()
	{
		String compdesc = "";
		try{
			compdesc = getCompanyService().findCompany(1L).getName();
		}
		catch (Exception e)
		{
			
		}
		return compdesc;
		
	}
	
	private void GenerateRawMaterial()
	{
		for(ProductionOrderMaterial pom : _productionOrderMaterial)
		{			
			rawmaterial list = new rawmaterial();			
			list.CodeNo = pom.getMaterialCode();
			list.Length = pom.getMaterial().getLength();
			list.Process = " - ";
			list.ProductionDate = " - ";
			list.Quantity = pom.getQuantityRequired();
			list.Thickness = pom.getMaterial().getHeight();
			list.Type = pom.getMaterial().getMaterialType().getType();
			list.Width = pom.getMaterial().getWidth();
			rawmatlist.add(list);
		}
	}
	
	private String printRMCode;
	private String printRMType;
	private Double printRMThickness;
	private Double printRMWidth;
	private Double printRMLength;
	private Double printRMQuantity;
	private String printRMProcess;
	
	
	/*
	<td>${printRMCode}</td>
	<td>${printRMType}</td>
	<td>${printRMThickness}</td>
	<td>${printRMWidth}</td>
	<td>${printRMLength}</td>
	<td>${printRMQuantity}</td>
	<td>${printRMProcess}</td>
	<td>${printRMProductionDate}</td>
	*/
	
	
	private void getReportInfo() throws BusinessException
	{
		_productionOrder  = getProductionOrderService().findProductionOrder(_productionOrderid);
		
		_productionOrderMaterial = getProductionOrderService().findProductionOrderMaterialsByProductionOrderId(_productionOrderid);
		_salesOrderMaterial = _productionOrder.getSalesOrderMaterial();
		
		printCustomerNo = _salesOrderMaterial.getSalesOrder().getCustomerCode();
		printDeliveryDate = " - ";
		printOrderDate = _salesOrderMaterial.getSalesOrder().getRowInfo().getCreateTimestamp().toString();
		printPOno  = _salesOrderMaterial.getSalesOrder().getCustomerPO();
		printOrderNo = _salesOrderMaterial.getSalesOrder().getDocNo().toString();
		
		System.out.println("printCustomerNo:" + printCustomerNo + ",  :" + _productionOrderMaterial.size());
		System.out.println("printorderDate:" + printOrderDate);
		System.out.println("printOrderNo:" + printOrderNo);
	}
	private IProductionOrderServiceRemote getProductionOrderService() {
		return getBusinessServicesLocator().getProductionOrderServiceRemote();
	}

	public void setProductionOrderID(Long productionorderid)
	{
		System.out.println("setProductionOrderID:" + productionorderid);
		this._productionOrderid = productionorderid;
	}
	private ICompanyServiceRemote getCompanyService() {
		   return getBusinessServicesLocator().getCompanyServiceRemote();
		}
	
	
	private IMaterialServiceRemote getMaterialService() {
	   return getBusinessServicesLocator().getMaterialServiceRemote();
	}
	void setupRender()
	{
		try
		{		
			//_productionOrderid = 150L;
			System.out.println("_productionOrderid: " + _productionOrderid.toString());
			getReportInfo();
			GenerateRawMaterial();
		}
		catch (Exception e)
		{
			
		}
	}
	public List<?> getMaterialDatasource() throws BusinessException
	{
		try
		{
			
			_materiallists = getMaterialService().findForSaleMaterials();
			return _materiallists;
		}
		catch (BusinessException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
}
