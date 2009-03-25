package com.quesoware.business.domain.finance;

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
import com.quesoware.business.domain.finance.Company;
import com.quesoware.business.domain.finance.iface.ICompanyServiceLocal;
import com.quesoware.business.domain.finance.iface.ICompanyServiceRemote;

@Stateless
@Local(ICompanyServiceLocal.class)
@Remote(ICompanyServiceRemote.class)
public class CompanyService extends BaseService implements ICompanyServiceLocal, ICompanyServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// Company

	public Company findCompany(Long id) throws DoesNotExistException {
		Company company = (Company) find(Company.class, id);
		return company;
	}

	@SuppressWarnings("unchecked")
	public List<Company> findCompanys() throws DoesNotExistException {
		Query q = _em.createQuery("select company from Company company where company.rowInfo.recordStatus='A' order by company.id ");
		List l = q.getResultList();
		return l;
	}
/*
	public Company findCompanyByType(String type) {
		Company company = _em.find(Company.class, id);
		return company;
	}
*/
	
	
	public void updateCompany(Company company) throws BusinessException {		
		company = (Company) merge(company);
	}

	public void logicalDeleteCompany(Company company) throws BusinessException {
		company.setRecordStatus("D");
		updateCompany(company);
	}
	
	public void addCompany(Company company) throws BusinessException {
		
		//try{
		System.out.println("just before persist in CompanyService");
		System.out.println(company.toString());
		persist(company);
		
		System.out.println("just after persist in CompanyService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[CompanyService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addCompany] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
}
