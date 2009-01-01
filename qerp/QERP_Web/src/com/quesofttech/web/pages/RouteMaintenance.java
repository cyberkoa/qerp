package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.production.Routing;
import com.quesofttech.business.domain.production.iface.IRoutingServiceRemote;
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
import org.apache.tapestry5.Block;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.omg.CosTransactions._SubtransactionAwareResourceStub;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
public class RouteMaintenance extends SecureBasePage {
private String _strMode = "";
private Routing RoutingDetail;
private Routing _Routing;
@Persist
private List<Routing> _Routings;
@Inject
private Logger _logger;
@Inject
private Block blockFormView;
@Persist
private long lng_CurrentID;
@Component(id = "RoutingForm")
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
@Component(id = "Description")
private TextField _Description;
private String Description;
public String getDescription()
{
   return Description;
}
public void setDescription(String Description)
{
   this.Description = Description;
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
@Component(id = "SequenceType")
private TextField _SequenceType;
private String SequenceType;
public String getSequenceType()
{
   return SequenceType;
}
public void setSequenceType(String SequenceType)
{
   this.SequenceType = SequenceType;
}
@Component(id = "version")
private TextField _version;
private Integer version;
public Integer getversion()
{
   return version;
}
public void setversion(Integer version)
{
   this.version = version;
}
@Component(id = "Material")
private TextField _Material;
private Long Material;
public Long getMaterial()
{
   return Material;
}
public void setMaterial(Long Material)
{
   this.Material = Material;
}
void RefreshRecords()
{
try
{
   _Routings = getRoutingService().findRoutings();
}
catch(BusinessException be)
{

}
if(_Routings!=null && !_Routings.isEmpty())
{
   RoutingDetail = _Routings.get(_Routings.size() - 1);
   assignToLocalVariable(RoutingDetail);
}
}
private int getRcdLocation( Long id)  throws BusinessException
{
   int int_return ,int_i;
   int_i = 0;
   int_return = 0;
   _Routings = getRoutingService().findRoutings();
   for(Routing p : _Routings)
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

void assignToDatabase(Routing Routing){
   Routing.setId(id);
   Routing.setDescription(Description);
   Routing.setOperation(Operation);
   Routing.setSequenceType(SequenceType);
   //Routing.setversion(version);
  // Routing.setMaterial(Material);
   Routing.setRecordStatus("A");
}
void assignToLocalVariable(Routing Routing)
{
   this.id = Routing.getId();
   this.Description = Routing.getDescription();
   this.Operation = Routing.getOperation();
   this.SequenceType = Routing.getSequenceType();
  // this.version = Routing.getversion();
  // this.Material = Routing.getMaterial();
}
void _AddRecord()
{
Routing Routing = new Routing();
try {
   assignToDatabase(Routing);
   getRoutingService().addRouting(Routing);
   }
catch (Exception e) {
   _logger.info("Routing_Add_problem");
   e.printStackTrace();
   _form.recordError(getMessages().get("Routing_add_problem"));
}
}

void _UpdateRecord(){
	Routing Routing = new Routing();
	try
	{
	   Routing = getRoutingService().findRouting(id);
	}
	catch(BusinessException be)
	{

	}
	if(Routing !=null)
	{
	try {
	assignToDatabase(Routing);
	getRoutingService().updateRouting(Routing);
	}
	catch (BusinessException e) {
	_form.recordError(_id, e.getLocalizedMessage());
	}
	catch (Exception e) {
	   _logger.info("Routing_update_problem");
	   e.printStackTrace();
	   _form.recordError(getMessages().get("Routing_update_problem"));
	}
   }
}


void _DeleteRecord(Long id) {
Routing Routing = new Routing();
try
{
   Routing = getRoutingService().findRouting(id);
}
catch(BusinessException be)
{

}
if(Routing!=null)
{
   try {
   getRoutingService().logicalDeleteRouting(Routing);
}
   catch (BusinessException e) {
   _form.recordError(_id, e.getLocalizedMessage());
}
catch (Exception e) {
   _logger.info("Routing_Delete_problem");
   e.printStackTrace();
   _form.recordError(getMessages().get("Routing_Delete_problem"));
}
}
}

void onActionFromtoolbarDel(Long id)
{
	if (id!=null) {
		_form.clearErrors();
		myState = "D";
		_strMode = "D";		
		_DeleteRecord(id);
	} 
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
       RoutingDetail = getRoutingService().findRouting(id);
       int_SelectedRow = getRcdLocation(id);
   }
catch(BusinessException be)
{

}

if(RoutingDetail!=null)
{
assignToLocalVariable(RoutingDetail);
return blockFormView;
}
return null;
}
private IRoutingServiceRemote getRoutingService() {
   return getBusinessServicesLocator().getRoutingServiceRemote();
}
public List<Routing> getRoutings() {
   return _Routings;
}


public Routing getRouting() throws BusinessException{
   return _Routing;
}


 public void setRouting(Routing tb) {
   _Routing = tb;
}

}
