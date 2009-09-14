package com.quesoware.web.pages;

/*
// TODO: ProductionOrderOperationMaintenance is remarked. due to some error.
package com.quesoware.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.domain.production.ProductionOrderOperation;
import com.quesoware.business.domain.production.iface.IProductionOrderOperationServiceRemote;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesoware.web.base.SimpleBasePage;
import com.quesoware.web.base.SecureBasePage;
import com.quesoware.web.state.Visit;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Retain;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.omg.CosTransactions._SubtransactionAwareResourceStub;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
public class ProductionOrderOperationMaintenance extends SecureBasePage {
private String _strMode = "";
private ProductionOrderOperation ProductionOrderOperationDetail;
private ProductionOrderOperation _ProductionOrderOperation;
@Persist
private List<ProductionOrderOperation> _ProductionOrderOperations;
@Inject
private Logger _logger;
@Inject
private Block blockFormView;
@Persist
private long lng_CurrentID;
@Component(id = "ProductionOrderOperationForm")
private Form _form;
@Persist
private int int_SelectedRow;
@ApplicationState
private String myState;

@Component(id = "GRID")
private Grid _Grid;
@Component(id = "id")
private TextField _id;
private Long id;
public Long getid()
{
   return id;
}
public void setid(Long id)
{
   this.id = id;
}
@Component(id = "Operation")
private TextField _Operation;
private Integer Operation;
public Integer getOperation()
{
   return Operation;
}
public void setOperation(Integer Operation)
{
   this.Operation = Operation;
}
	//===============================
	// Text Component for QuantityRequired
	@Component(id = "QuantityRequired")
	private TextField _QuantityRequired;
	private double quantityRequired;
	public double getQuantityRequired()
	{
	   return quantityRequired;
	}
	
	public void setQuantityRequired(double quantityRequired)
	{
	   this.quantityRequired = quantityRequired;
	}
	//===============================
	
	//===============================
	// Text Component for QuantityConsumed
	@Component(id = "QuantityConsumed")
	private TextField _QuantityConsumed;
	private double quantityConsumed;
	public double getQuantityConsumed()
	{
	   return quantityConsumed;
	}
	
	public void setQuantityConsumed(double quantityConsumed)
	{
	   this.quantityConsumed = quantityConsumed;
	}
void RefreshRecords()
{
try
{
   _ProductionOrderOperations = getProductionOrderOperationService().findProductionOrderOperations();
}
catch(BusinessException be)
{

}
if(_ProductionOrderOperations!=null && !_ProductionOrderOperations.isEmpty())
{
   ProductionOrderOperationDetail = _ProductionOrderOperations.get(_ProductionOrderOperations.size() - 1);
   assignToLocalVariable(ProductionOrderOperationDetail);
}
}
private int getRcdLocation( Long id)  throws BusinessException
{
   int int_return ,int_i;
   int_i = 0;
   int_return = 0;
   _ProductionOrderOperations = getProductionOrderOperationService().findProductionOrderOperations();
   for(ProductionOrderOperation p : _ProductionOrderOperations)
{
   int_i++;
   if((long)p.getId().intValue()==id)
{
                   int_return = int_i;
}
}
return int_return;
}

public Block getBlock() {
   return blockFormView;
}


Object onSuccess()
{
   RefreshRecords();
   return blockFormView;
}


void setupRender() {
   RefreshRecords();
}


void onValidateForm() {
if ("U"== myState)
{
   _UpdateRecord();
}
else
if ("A" == myState)
{
   _AddRecord();
}
}

void assignToDatabase(ProductionOrderOperation ProductionOrderOperation){
   ProductionOrderOperation.setid(id);
   ProductionOrderOperation.setOperation(Operation);
   ProductionOrderOperation.setQuantityOrder(QuantityOrder);
   ProductionOrderOperation.setQuantityConsumed(QuantityConsumed);
   ProductionOrderOperation.setRecordStatus("A");
}
void assignToLocalVariable(ProductionOrderOperation ProductionOrderOperation)
{
   this.id = ProductionOrderOperation.getid();
   this.Operation = ProductionOrderOperation.getOperation();
   this.QuantityOrder = ProductionOrderOperation.getQuantityOrder();
   this.QuantityConsumed = ProductionOrderOperation.getQuantityConsumed();
}
void _AddRecord()
{
ProductionOrderOperation ProductionOrderOperation = new ProductionOrderOperation();
try {
   assignToDatabase(ProductionOrderOperation);
   getProductionOrderOperationService().addProductionOrderOperation(ProductionOrderOperation);
   }
catch (Exception e) {
   _logger.info("ProductionOrderOperation_Add_problem");
   e.printStackTrace();
   _form.recordError(getMessages().get("ProductionOrderOperation_add_problem"));
}
}

void _UpdateRecord(){
ProductionOrderOperation ProductionOrderOperation = new ProductionOrderOperation();
try
{
   ProductionOrderOperation = getProductionOrderOperationService().findProductionOrderOperation(id);
}
   catch(BusinessException be)
   {

}
if(ProductionOrderOperation !=null)
{
try {
assignToDatabase(ProductionOrderOperation);
getProductionOrderOperationService().updateProductionOrderOperation(ProductionOrderOperation);
}
catch (BusinessException e) {
_form.recordError(_typeField, e.getLocalizedMessage());
}
catch (Exception e) {
   _logger.info("ProductionOrderOperation_update_problem");
   e.printStackTrace();
   _form.recordError(getMessages().get("ProductionOrderOperation_update_problem"));
       }
   }
}


void _DeleteRecord(Long id) {
ProductionOrderOperation ProductionOrderOperation = new ProductionOrderOperation();
try
{
   ProductionOrderOperation = getProductionOrderOperationService().findProductionOrderOperation(id);
}
catch(BusinessException be)
{

}
if(ProductionOrderOperation!=null)
{
   try {
   getProductionOrderOperationService().logicalDeleteProductionOrderOperation(ProductionOrderOperation);
}
   catch (BusinessException e) {
   _form.recordError(_typeField, e.getLocalizedMessage());
}
catch (Exception e) {
   _logger.info("ProductionOrderOperation_Delete_problem");
   e.printStackTrace();
   _form.recordError(getMessages().get("ProductionOrderOperation_Delete_problem"));
}
}
}

Object onActionFromtoolbarDel(Long id)
{
   myState = "D";
   _strMode = "D";
   _DeleteRecord(id);
   return blockFormView;
}

Object onActionFromToolbarAdd ()
{
   myState = "A";
   _strMode = "A";
   return blockFormView;
}

Object onActionFromSelect(long id)
{
   myState = "U";
   _strMode = "U";
   lng_CurrentID = id;
   try
   {
       ProductionOrderOperationDetail = getProductionOrderOperationService().findProductionOrderOperation(id);
       int_SelectedRow = getRcdLocation(id);
   }
catch(BusinessException be)
{

}

if(ProductionOrderOperationDetail!=null)
{
assignToLocalVariable(ProductionOrderOperationDetail);
return blockFormView;
}
return null;
}
private IProductionOrderOperationServiceRemote getProductionOrderOperationService() {
   return getBusinessServicesLocator().getProductionOrderOperationServiceRemote();
}
public List<ProductionOrderOperation> getProductionOrderOperations() {
   return _ProductionOrderOperations;
}


public ProductionOrderOperation getProductionOrderOperation() throws BusinessException{
   return _ProductionOrderOperation;
}


 public void setProductionOrderOperation(ProductionOrderOperation tb) {
   _ProductionOrderOperation = tb;
}

}
*/
