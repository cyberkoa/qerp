package com.quesoware.business.domain.sales.iface;

import java.util.List;

import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.domain.sales.Customer;


/**
 * The <code>ICustomerServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface ICustomerServiceRemote {

	// Customer

	Customer findCustomer(Long id)throws DoesNotExistException;

	List<Customer> findCustomers() throws DoesNotExistException;

	void updateCustomer(Customer customer) throws BusinessException;
	
	void addCustomer(Customer customer) throws BusinessException;
	
	void logicalDeleteCustomer(Customer customer) throws BusinessException;

}
