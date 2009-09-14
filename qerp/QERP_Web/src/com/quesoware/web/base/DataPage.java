
package com.quesoware.web.base;

import com.quesoware.web.base.SecureBasePage;
import com.quesoware.web.components.QERPWindow;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Retain;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.internal.grid.CollectionGridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
//import org.slf4j.*;

public class DataPage extends SecureBasePage 
{
	// sfl4j.logger
	//protected Logger logger; 
	
	protected CollectionGridDataSource collectionGridDataSource;
	
	@Inject
    protected BeanModelSource beanModelSource;
	
	@SuppressWarnings("unchecked")
	@Property
	@Retain
	protected BeanModel beanModel;
	
	@Inject
    protected ComponentResources resources;
	
	
	// This may replace the filter
	@SuppressWarnings("unchecked")
	@Property
	@Retain	
	protected BeanModel filterModel;
	
	/* cyberkoa : make a dynamic filterWindow, with dynamic size 
	@Component(parameters = {"width=300", "height=300", 
			                 "style=bluelighting", "show=false",
			                 "modal=true", "title=literal:Filter Record"})
	@Property
	private QERPWindow _filterWindow;
	*/
	
	//protected Viewer viewer;
	@Component(id = "grid")
	protected Grid grid;
	
	// protected Toolbar toolbar;
	
	
	// protected Viewer viewer;
	
	// Methods
	 
	protected Boolean searchRecord()
	{
		return true;
	}
	
	protected Boolean filterRecord()
	{
		return true;
	}
	
	
	
	protected Boolean previousRecord()
	{
		return true;
	}
	
	protected Boolean nextRecord()
	{
		return true;
	}
	
	protected Boolean firstRecord()
	{
		return true;
	}
	
	protected Boolean lastRecord()
	{
		return true;
	}
	
	
}