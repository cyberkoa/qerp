package com.quesofttech.web.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import com.quesofttech.business.common.exception.SystemUnavailableException;
//import jumpstart.business.domain.examples.iface.IDateStuffServiceLocal;
//import jumpstart.business.domain.examples.iface.IPersonServiceLocal;
import com.quesofttech.business.domain.sales.SalesOrderService;
import com.quesofttech.business.domain.sales.iface.ISalesOrderServiceRemote;
import com.quesofttech.business.domain.production.*;
import com.quesofttech.business.domain.production.iface.*;
import com.quesofttech.business.domain.general.*;
import com.quesofttech.business.domain.general.iface.*;
import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceLocal;
import com.quesofttech.business.domain.security.iface.ISecurityManagerServiceLocal;
import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesofttech.business.domain.security.iface.ISecurityManagerServiceRemote;
import com.quesofttech.business.domain.security.SecurityManagerService;
import com.quesofttech.business.domain.security.SecurityFinderService;

import com.quesofttech.business.domain.inventory.iface.IMaterialTypeServiceRemote;
import com.quesofttech.business.domain.inventory.MaterialTypeService;

import com.quesofttech.business.domain.inventory.*;
import com.quesofttech.business.domain.inventory.iface.*;


import com.quesofttech.business.domain.sales.iface.*;
import com.quesofttech.business.domain.sales.*;

import com.quesofttech.business.domain.finance.iface.ICompanyServiceRemote;
import com.quesofttech.business.domain.finance.CompanyService;

import com.quesofttech.business.domain.security.iface.*;
import com.quesofttech.business.domain.security.*;

import com.quesofttech.business.domain.production.iface.IRoutingServiceRemote;
import com.quesofttech.business.domain.production.RoutingService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * BusinessServicesLocator is used to centralize all JNDI lookups. It is a global singleton registered in AppModule. It
 * minimises the overhead of JNDI lookups by caching the objects it looks up. If this class becomes a bottleneck, then
 * you may need to decentralise it.
 */
public class BusinessServicesLocator implements IBusinessServicesLocator {
	static private final Log LOG = LogFactory.getLog(BusinessServicesLocator.class);

	private InitialContext _initialContext;
	private Map<String, Object> _cache = Collections.synchronizedMap(new HashMap<String, Object>());
	
	public BusinessServicesLocator() {
	}
/*
	public IPersonServiceLocal getPersonServiceLocal() {
		return (IPersonServiceLocal) getService("jumpstart-min/PersonService/local");
	}

	public IDateStuffServiceLocal getDateStuffServiceLocal() {
		return (IDateStuffServiceLocal) getService("jumpstart-min/DateStuffService/local");
	}
*/
	public ISecurityFinderServiceLocal getSecurityFinderSvcLocal() {
		return (ISecurityFinderServiceLocal) getService(SecurityFinderService.class.getSimpleName()+"Local");
	}
	
	

	public ISecurityManagerServiceLocal getSecurityManagerSvcLocal() {
		return (ISecurityManagerServiceLocal) getService(SecurityManagerService.class.getSimpleName()+"Local");
	}

	public ISecurityFinderServiceRemote getSecurityFinderSvcRemote() {
		return (ISecurityFinderServiceRemote) getService(SecurityFinderService.class.getSimpleName()+"Remote");
	}
	
	public ISecurityManagerServiceRemote getSecurityManagerServiceRemote() {
		return (ISecurityManagerServiceRemote) getService(SecurityManagerService.class.getSimpleName()+"Remote");
	}

	public ISecurityManagerServiceRemote getSecurityManagerSvcRemote() {
		return (ISecurityManagerServiceRemote) getService(SecurityManagerService.class.getSimpleName()+"Remote");
	}
	
	public IMaterialTypeServiceRemote getMaterialTypeServiceRemote() {
		return (IMaterialTypeServiceRemote) getService(MaterialTypeService.class.getSimpleName()+"Remote");
	}
	
	public IProductionOrderServiceRemote getProductionOrderServiceRemote() {
		return (IProductionOrderServiceRemote) getService(ProductionOrderService.class.getSimpleName()+"Remote");
	}

	public ISalesOrderServiceRemote getSalesOrderServiceRemote() {
		return (ISalesOrderServiceRemote) getService(SalesOrderService.class.getSimpleName()+"Remote");
	}
	
	public ICompanyServiceRemote getCompanyServiceRemote() {
		return (ICompanyServiceRemote) getService(CompanyService.class.getSimpleName()+"Remote");
	}
	
	public IRoleServiceRemote getRoleServiceRemote() {
		return (IRoleServiceRemote) getService(RoleService.class.getSimpleName()+"Remote");
	}
	
	public IRoutingServiceRemote getRoutingServiceRemote() {
		return (IRoutingServiceRemote) getService(RoutingService.class.getSimpleName()+"Remote");
	}
	public ICustomerServiceRemote getCustomerServiceRemote() {
		return (ICustomerServiceRemote) getService(CustomerService.class.getSimpleName()+"Remote");
	}
	public IMaterialServiceRemote getMaterialServiceRemote() {
		return (IMaterialServiceRemote) getService(MaterialService.class.getSimpleName()+"Remote");
	}
	public IBomServiceRemote getBOMServiceRemote() {
		return (IBomServiceRemote) getService(BomService.class.getSimpleName()+"Remote");
	}
	
	public IUserProgramServiceRemote getUserProgramServiceRemote() {
		return (IUserProgramServiceRemote) getService(UserProgramService.class.getSimpleName()+"Remote");
	}

	public IProgramServiceRemote getProgramServiceRemote() {
		return (IProgramServiceRemote) getService(ProgramService.class.getSimpleName()+"Remote");
	}
	
	public IUomServiceRemote getUOMServiceRemote(){
		return (IUomServiceRemote) getService(UomService.class.getSimpleName()+"Remote");
	}
	
	public IModuleServiceRemote getModuleServiceRemote(){
		return (IModuleServiceRemote) getService(ModuleService.class.getSimpleName()+"Remote");
	}
	public IRoleProgramServiceRemote getRoleProgramServiceRemote(){
		return (IRoleProgramServiceRemote) getService(RoleProgramService.class.getSimpleName()+"Remote");
	}
	
	public IUserPasswordServiceRemote getUserPasswordServiceRemote(){
		return (IUserPasswordServiceRemote) getService(UserPasswordService.class.getSimpleName()+"Remote");
	}
	public IDocumentTypeServiceRemote getDocumentTypeServiceRemote(){
		return (IDocumentTypeServiceRemote) getService(DocumentTypeService.class.getSimpleName() + "Remote");
	}

	
	
	
	/*
	 * @see jumpstart.web.services.IBusinessServicesLocator#clear()
	 */
	public synchronized void clear() {
		_cache.clear();
	}

	private Object getService(String name) {
		Object service = _cache.get(name);

		if (service == null && !_cache.containsKey(name)) {
			try {
				service = lookup(name);
				_cache.put(name, service);
			}
			catch (RuntimeException e) {
				clear();
				throw e;
			}
		}
		return service;
	}

	private synchronized Object lookup(String name) {
		// Recheck the cache - while we waited for sync, the name we're looking for may have been added.
		if (!_cache.containsKey(name)) {
			try {
				return getInitialContext().lookup(name);
			}
			catch (NameNotFoundException e) {
				clear();
				throw new SystemUnavailableException("JNDI lookup failed for \"" + name
						+ "\".  Is Geronimo server not started?", e);
			}
			catch (NamingException e) {
				clear();
				throw new SystemUnavailableException("JNDI lookup failed for \"" + name
						+ "\".  Is Geronimo server not started?", e);
			}
		}
		else {
			return _cache.get(name);
		}
	}

	private synchronized InitialContext getInitialContext() {
		if (_initialContext == null) {
			try {
				
			      ResourceBundle bundle = ResourceBundle.getBundle("jndi", Locale.getDefault(), BusinessServicesLocator.class.getClassLoader());
		            Properties props = new Properties();
		            props.setProperty(Context.INITIAL_CONTEXT_FACTORY,bundle.getString("java.naming.factory.initial"));
		            props.setProperty(Context.PROVIDER_URL,"ejbd://" + bundle.getString("java.naming.provider.url"));
		            props.setProperty(Context.SECURITY_PRINCIPAL, bundle.getString("java.naming.security.principal"));
		            props.setProperty(Context.SECURITY_CREDENTIALS, bundle.getString("java.naming.security.credentials"));
		            props.setProperty("openejb.authentication.realmName","geronimo-admin");
				
				_initialContext = new InitialContext(props);
				LOG.info("InitialContext environment = " + _initialContext.getEnvironment());
			}
			catch (NamingException e) {
				clear();
				throw new SystemUnavailableException("Cannot get initial context. "
						+ "Is Geronimo server not started?", e);
			}
		}
		return _initialContext;
	}

}