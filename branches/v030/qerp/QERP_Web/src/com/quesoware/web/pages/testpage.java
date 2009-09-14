package com.quesoware.web.pages;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.sql.Timestamp;
import javax.annotation.Resource;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.domain.general.*;
import com.quesoware.business.domain.general.iface.*;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesoware.web.base.SecureBasePage;
import com.quesoware.web.base.SimpleBasePage;
import com.quesoware.web.state.Visit;

import org.chenillekit.tapestry.core.components.datetimefield.*;
import org.apache.tapestry5.annotations.*;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.corelib.components.DateField;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
public class testpage {
	@Parameter(defaultPrefix="literal", required=true)
    private String _pages;

    @Inject
    private ComponentResources _resources;

    private String _pageName;

    public String getPageName() { return _pageName; }

    public void setPageName(String pageName) { _pageName = pageName; }

    public String[] getPageNames()
    {
        return _pages.split(",");
    }

    public String getTabClass()
    {
        if (_pageName.equalsIgnoreCase(_resources.getPageName()))
            return "current";

        return null;
    }

}
