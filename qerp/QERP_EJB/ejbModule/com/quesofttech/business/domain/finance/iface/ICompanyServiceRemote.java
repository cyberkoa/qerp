package com.quesofttech.business.domain.finance.iface;

import java.util.List;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.finance.Company;

/**
 * The <code>ICompanyServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface ICompanyServiceRemote {

	// Company

	Company findCompany(Long id)throws DoesNotExistException;

	List<Company> findCompanys() throws DoesNotExistException;

	void updateCompany(Company company) throws BusinessException;
	
	void addCompany(Company company) throws BusinessException;
	
	void logicalDeleteCompany(Company company) throws BusinessException;

}
