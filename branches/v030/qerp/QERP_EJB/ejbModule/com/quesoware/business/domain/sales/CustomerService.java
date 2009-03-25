package com.quesoware.business.domain.sales;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.domain.base.BaseService;
import com.quesoware.business.domain.sales.Customer;
import com.quesoware.business.domain.sales.iface.ICustomerServiceLocal;
import com.quesoware.business.domain.sales.iface.ICustomerServiceRemote;

@Stateless
@Local(ICustomerServiceLocal.class)
@Remote(ICustomerServiceRemote.class)
public class CustomerService extends BaseService implements ICustomerServiceLocal, ICustomerServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// Customer

	public Customer findCustomer(Long id) throws DoesNotExistException {
		Customer customer = (Customer) find(Customer.class, id);
		return customer;
	}

	@SuppressWarnings("unchecked")
	public List<Customer> findCustomers() throws DoesNotExistException {
		Query q = _em.createQuery("select customer from Customer customer where customer.rowInfo.recordStatus='A' order by customer.id");
		List l = q.getResultList();
		return l;
	}
/*
	public Customer findCustomerByType(String type) {
		Customer customer = _em.find(Customer.class, id);
		return customer;
	}
*/
	
	
	public void updateCustomer(Customer customer) throws BusinessException {		
		customer = (Customer) merge(customer);
	}

	public void logicalDeleteCustomer(Customer customer) throws BusinessException {
		customer.setRecordStatus("D");
		updateCustomer(customer);
	}
	
	public void addCustomer(Customer customer) throws BusinessException {
		
		//try{
		System.out.println("just before persist in CustomerService");
		persist(customer);
		
		System.out.println("just after persist in CustomerService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[CustomerService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addCustomer] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
}
