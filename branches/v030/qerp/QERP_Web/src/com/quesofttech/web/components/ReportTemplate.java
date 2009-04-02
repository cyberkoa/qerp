
package com.quesofttech.web.components;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.annotations.Property;

import sun.security.action.GetLongAction;

import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.pages.BomDetailMaintenance;
import com.quesofttech.web.pages.Index;
import com.quesofttech.web.state.Visit;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.domain.inventory.MaterialType;
import com.quesoware.business.domain.inventory.iface.IMaterialTypeServiceRemote;
import com.quesoware.business.domain.security.Module;
import com.quesoware.business.domain.security.Program;
import com.quesoware.business.domain.security.Role;
import com.quesoware.business.domain.security.SecurityFinderService;
import com.quesoware.business.domain.security.User;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;
@SupportsInformalParameters

public class ReportTemplate extends SimpleBasePage 
{
	@Parameter(defaultPrefix = "literal")
	private String CompanyHeader;
	
	@Parameter(defaultPrefix = "literal")
	private String ReportName;
	
	@Parameter(defaultPrefix = "literal")
	private String FormNo;
	
	public String getReportTitle()
	{
		return ReportName;
	}
	public String getCompanyName()
	{
		return CompanyHeader;
	}
	public String getFormNo()
	{
		return FormNo;
	}
	
}