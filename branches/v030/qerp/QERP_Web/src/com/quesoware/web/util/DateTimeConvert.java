package com.quesoware.web.util;
import java.io.IOException;
import java.util.List;
import org.apache.tapestry5.corelib.components.DateField;
import javax.annotation.Resource;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.domain.security.User;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesoware.business.domain.security.iface.ISecurityManagerServiceRemote;
import com.quesoware.web.base.SecureBasePage;
import com.quesoware.web.base.SimpleBasePage;
import com.quesoware.web.state.Visit;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Retain;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
public class DateTimeConvert extends SimpleBasePage {
// Default defination.
	public  java.util.Date SqlTimestamptoDateUtilDate(java.sql.Timestamp timestamp) {
	    long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
	    return new java.util.Date(milliseconds);
	}
	public java.sql.Timestamp utilDateToSqlTimestamp(java.util.Date utilDate) {
		return new java.sql.Timestamp(utilDate.getTime());
		}
   
}
