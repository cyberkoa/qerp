
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
public class TableTemplate extends SimpleBasePage 
{
	@Parameter(defaultPrefix="literal")
	private String ListFields;
	
	@Parameter(defaultPrefix="literal")
	private String ExcludeFields;
	
	@Parameter
	private List<?> DataSource;
	
	@Parameter(required=true)
	private List<String> Headers;
	@Persist
	private List<?> DataSourceDetail;
	
	@Persist
	private List<?> _index;
	@Persist
	private String _colindex;
	
	private String title;
	
	
	private String[] _testnum = {"1","2","3"};	
	
	public void setTitle(String title)
	{
		this.title = title;	
	}
	public String getTitle()
	{
		return title;
	}
	public List<String> getHeaders()
	{
		return Headers;
	}
	public List<?> getDataSource()
	{
		return DataSource;
	}
	
	public List<?> getDatasourceDetail()
	{
		return DataSourceDetail;
	}
	public void setDatasourceDetail(List<?> DataSourceDetail)
	{
		this.DataSourceDetail = DataSourceDetail;
	}
	
	public List<?> getIndex()
	{
		System.out.println(_colindex.toString());
		return _index;
	}
	public void setIndex(List<?> index)
	{
		this._index = index;
		this.DataSourceDetail = index;
	}
	
	public String getcolindex()
	{
		return _colindex;
	}
	public void setcolindex(String _colindex)
	{
		this._colindex = _colindex;
	}
	
	
	
}