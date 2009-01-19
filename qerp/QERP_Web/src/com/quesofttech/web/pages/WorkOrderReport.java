package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.common.exception.DuplicateAlternateKeyException;
import com.quesofttech.business.common.exception.DuplicatePrimaryKeyException;

import com.quesofttech.web.common.ContextFixer;
import com.quesofttech.business.domain.general.*;
import com.quesofttech.business.domain.general.iface.IUomServiceRemote;
import com.quesofttech.business.domain.inventory.*;
import com.quesofttech.business.domain.inventory.iface.*;
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
import org.omg.CosTransactions._SubtransactionAwareResourceStub;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;


import com.quesofttech.web.model.base.GenericSelectModel;
//import com.quesofttech.web.model.MaterialTypeSelectModel;



public class WorkOrderReport extends SecureBasePage {
	private Material materiallists;
	
	private List<String> _titles;
	@Persist
	private List<?> _materiallists;
	
	public List<String> getTitles()
	{
		_titles.add("No:");
		_titles.add("Item:");
		_titles.add("Desc:");
		_titles.add("Amount:");
		
		return _titles;
		
	}
	
	private IMaterialServiceRemote getMaterialService() {
	   return getBusinessServicesLocator().getMaterialServiceRemote();
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
