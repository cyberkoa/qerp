package com.quesofttech.web.services;

//import jumpstart.business.domain.examples.iface.IDateStuffServiceLocal;
//import jumpstart.business.domain.examples.iface.IPersonServiceLocal;
//import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceLocal;
import com.quesofttech.business.domain.sales.iface.ISalesOrderServiceRemote;
import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceRemote;
//import com.quesofttech.business.domain.security.iface.ISecurityManagerServiceLocal;
import com.quesofttech.business.domain.inventory.iface.*;
import com.quesofttech.business.domain.sales.iface.*;
import com.quesofttech.business.domain.general.iface.*;
import com.quesofttech.business.domain.finance.iface.ICompanyServiceRemote;
import com.quesofttech.business.domain.security.iface.*;
import com.quesofttech.business.domain.production.iface.IRoutingServiceRemote;
import com.quesofttech.business.domain.production.iface.IProductionOrderServiceRemote;
public interface IBusinessServicesLocator {

	//public abstract IPersonServiceLocal getPersonServiceLocal();

	//public abstract IDateStuffServiceLocal getDateStuffServiceLocal();

	//public abstract ISecurityFinderServiceLocal getSecurityFinderSvcLocal();
	public abstract ISecurityFinderServiceRemote getSecurityFinderSvcRemote();
	public abstract ISecurityManagerServiceRemote getSecurityManagerServiceRemote();
	//public abstract ISecurityManagerServiceLocal getSecurityManagerSvcLocal();

	public abstract IMaterialTypeServiceRemote getMaterialTypeServiceRemote();
	
	public abstract ISalesOrderServiceRemote getSalesOrderServiceRemote();
	
	public abstract ICompanyServiceRemote getCompanyServiceRemote();
	
	public abstract IRoleServiceRemote getRoleServiceRemote();
	
	public abstract IRoutingServiceRemote getRoutingServiceRemote();
	public abstract ICustomerServiceRemote getCustomerServiceRemote();
	
	public abstract IMaterialServiceRemote getMaterialServiceRemote();
	public abstract IProductionOrderServiceRemote getProductionOrderServiceRemote();
	public abstract IBOMServiceRemote getBOMServiceRemote();
	public abstract IUserProgramServiceRemote getUserProgramServiceRemote();
	public abstract IProgramServiceRemote getProgramServiceRemote();
	public abstract IUOMServiceRemote getUOMServiceRemote();
	public abstract IModuleServiceRemote getModuleServiceRemote();
	
	/**
	 * Invoked after any kind of naming or remote exception. All cached naming contexts and interfaces are discarded.
	 */
	public abstract void clear();

}