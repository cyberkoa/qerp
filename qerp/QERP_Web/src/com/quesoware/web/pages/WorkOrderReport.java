package com.quesoware.web.pages;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.SimpleFormatter;

import javax.annotation.Resource;


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


//import com.quesoware.web.model.MaterialTypeSelectModel;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.common.exception.DuplicateAlternateKeyException;
import com.quesoware.business.common.exception.DuplicatePrimaryKeyException;
import com.quesoware.business.domain.finance.iface.ICompanyServiceRemote;
import com.quesoware.business.domain.general.*;
import com.quesoware.business.domain.general.iface.*;
import com.quesoware.business.domain.inventory.*;
import com.quesoware.business.domain.inventory.iface.*;
import com.quesoware.business.domain.production.*;
import com.quesoware.business.domain.production.iface.IProductionOrderServiceRemote;
import com.quesoware.business.domain.sales.SalesOrderMaterial;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesoware.web.base.SecureBasePage;
import com.quesoware.web.base.SimpleBasePage;
import com.quesoware.web.common.ContextFixer;
import com.quesoware.web.model.base.GenericSelectModel;
import com.quesoware.web.state.Visit;



public class WorkOrderReport extends SimpleBasePage {
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
	public String getWidth()
	{
		return _rawmat.Width;
	}
	public void setWidth(String Width)
	{
		_rawmat.Width = Width;		
	}
	
	public String getLength()
	{
		return _rawmat.Length;
	}
	public void setLength(String Length)
	{
		_rawmat.Length = Length;		
	}
	
	public String getQuantity()
	{
		return _rawmat.Quantity;
	}
	public void setQuantity(String Quantity)
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
	
	public String getThickness()
	{
		return _rawmat.Thickness;
	}
	public void setThickness(String Thickness)
	{
		_rawmat.Thickness = Thickness;
	}
	public class rawmaterial
	{
		
		public String RowType=""; // If RowType = "H" then is header.
		public String CodeNo="";
		public String Type="";
		public String Thickness="";
		public String Width="";
		public String Length="";
		public String Quantity="";
		public String Process="";
		public String ProductionDate="";
	}
	
	
	private String printOrderQuantity;
	private String printOrderNo;
	private String printOrderDate;
	private String printPOno;
	private String printDeliveryDate;
	private String printCustomerNo;
	private String printBrandCode;	
	private String printMasterCard;
	private String printMasterCode;

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
	@Persist
	private List<rawmaterial> rawmatlist ;
	@Property
	private rawmaterial _rawmat;
	
	public String getPrintMasterCard() {
		return printMasterCard;
	}
	public void setPrintMasterCard(String printMasterCard) {
		this.printMasterCard = printMasterCard;
	}
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
	
	public String getPrintOrderQuantity() {
		return printOrderQuantity;
	}
	public void setPrintOrderQuantity(String printOrderQuantity) {
		this.printOrderQuantity = printOrderQuantity;
	}

	public String getPrintBrandCode() {
		return printBrandCode;
	}
	public void setPrintBrandCode(String printBrandCode) {
		this.printBrandCode = printBrandCode;
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
	
	private void GenerateRawMaterial() throws Exception
	{
		System.out.println("GenerateRawMaterial _productionOrderMaterial 's size:" + _productionOrderMaterial.size());
		if(_productionOrder==null)
			System.out.println("null for _productionOrder");
		else 
			System.out.println("null for _productionOrder not null");
			
		System.out.println("_productionOrder: " + _productionOrder.getFormattedDocNo());
		
		List<ProductionOrderOperation> productionOrderOperations = getProductionOrderService().findProductionOrderOperationsByProductionOrderId(_productionOrder.getId());
		List<ProductionOrderOperation> uniquePOO = new ArrayList<ProductionOrderOperation>();
		List<Integer> _lines = new ArrayList<Integer>();
		int count = 0;
		System.out.println("productionOrderOperations: " + productionOrderOperations.size());
		for(ProductionOrderOperation poo : productionOrderOperations)
		{
			if(!uniquePOO.contains(poo))
			{
				
				uniquePOO.add(poo);
				_lines.add(count);
				count++;
			}
		}
		System.out.println("uniquePOO 's size:" + uniquePOO.size());
		for(ProductionOrderOperation poo1 : uniquePOO)
		{	
			//System.out.println("pom add");
			//rawmaterial listH = new rawmaterial();
			//System.out.println("pom added");
			//listH.RowType = "H";
			//listH.CodeNo = "Code";
			//listH.Length = "Length";
			//listH.Process = "Process";
			//listH.ProductionDate ="Production Date";
			//listH.Quantity = "Quantity";
			//listH.Thickness = "Thickness";
			//listH.Type = "Type";
			//listH.Width ="Width";
			//System.out.println("add on raw mat");
			//rawmatlist.add(listH);
			
			System.out.println("_productionOrderMaterial: " + _productionOrderMaterial.size());
			for(ProductionOrderMaterial pom : _productionOrderMaterial)
			{			
				System.out.println("output in pom:" );
				//System.out.println("getProductionOrderOperation().getOperation().getCode(): " + pom.getProductionOrderOperation().getOperation().getCode());
				//System.out.println("poo1.getOperation().getCode(): " + poo1.getOperation().getCode());
				if(pom.getOperation().getCode().equals(poo1.getOperation().getCode()))
				{
					DecimalFormat decimalFormat = new DecimalFormat("0.00");
					System.out.println("pom add");
					rawmaterial list = new rawmaterial();
					System.out.println("pom added");
					list.RowType = "";
					list.CodeNo = pom.getMaterialCode();
					list.Length = decimalFormat.format(pom.getMaterial().getLength());
					list.Process = pom.getOperation().getCode();
					list.ProductionDate = " - ";
					list.Quantity = decimalFormat.format(pom.getQuantityRequired());
					list.Thickness =  decimalFormat.format( pom.getMaterial().getHeight());
					if(pom.getMaterial().getMaterialGroup()==null)
						list.Type = " - ";
					else 
						list.Type = pom.getMaterial().getMaterialGroup().getGroup();
					list.Width =  decimalFormat.format( pom.getMaterial().getWidth());
					System.out.println("add on raw mat");
					rawmatlist.add(list);
					
				}
				
			}
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
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		Format format = new SimpleDateFormat("dd-MMM-yyyy");
		rawmatlist = null;
		_productionOrder = null;
		_productionOrderMaterial = null;
		_salesOrderMaterial = null;
		
		rawmatlist = new ArrayList<rawmaterial>();
		_productionOrder  = getProductionOrderService().findProductionOrder(_productionOrderid);
		
		_productionOrderMaterial = getProductionOrderService().findProductionOrderMaterialsByProductionOrderId(_productionOrderid);
		_salesOrderMaterial = _productionOrder.getSalesOrderMaterial();	
		printBrandCode = _productionOrder.getMaterial().getCode() + "("  + _productionOrder.getMaterial().getDescription() + ")";
		//printBrandCode = _salesOrderMaterial.getMaterialCode() + "(" + _salesOrderMaterial.getMaterial().getDescription() + ")";
		printCustomerNo = _salesOrderMaterial.getSalesOrder().getCustomerCode();
		printDeliveryDate = " - ";
		printOrderDate = format.format(_salesOrderMaterial.getSalesOrder().getRowInfo().getCreateTimestamp());
		printPOno  = _salesOrderMaterial.getSalesOrder().getCustomerPO();
		printOrderNo = _salesOrderMaterial.getSalesOrder().getFormattedDocNo();
		printMasterCard = _productionOrder.getBom().getCode();
		printOrderQuantity = decimalFormat.format(_productionOrder.getQuantityOrder());
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
	private IBomServiceRemote getBomService() {
		   return getBusinessServicesLocator().getBOMServiceRemote();
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
	
}
