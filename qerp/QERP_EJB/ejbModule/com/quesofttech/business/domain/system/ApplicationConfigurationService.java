package com.quesofttech.business.domain.system;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.base.BaseService;
import com.quesofttech.business.domain.system.ApplicationConfiguration;
import com.quesofttech.business.domain.system.iface.IApplicationConfigurationServiceLocal;
import com.quesofttech.business.domain.system.iface.IApplicationConfigurationServiceRemote;

@Stateless
@Local(IApplicationConfigurationServiceLocal.class)
@Remote(IApplicationConfigurationServiceRemote.class)
public class ApplicationConfigurationService extends BaseService implements IApplicationConfigurationServiceLocal, IApplicationConfigurationServiceRemote {

//	@PersistenceContext(unitName = "QERP_EJB")
//	protected EntityManager _em;

	// ApplicationConfiguration

	public ApplicationConfiguration findApplicationConfiguration(Long id) throws DoesNotExistException {
		ApplicationConfiguration applicationConfiguration = (ApplicationConfiguration) find(ApplicationConfiguration.class, id);
		return applicationConfiguration;
	}

	@SuppressWarnings("unchecked")
	public List<ApplicationConfiguration> findApplicationConfigurations() throws DoesNotExistException {
		Query q = _em.createQuery("select applicationConfiguration from ApplicationConfiguration applicationConfiguration where applicationConfiguration.rowInfo.recordStatus='A' order by applicationConfiguration.id");
		List l = q.getResultList();
		return l;
	}
/*
	public ApplicationConfiguration findApplicationConfigurationByType(String type) {
		ApplicationConfiguration applicationConfiguration = _em.find(ApplicationConfiguration.class, id);
		return applicationConfiguration;
	}
*/
	
	
	public void updateApplicationConfiguration(ApplicationConfiguration applicationConfiguration) throws BusinessException {		
		applicationConfiguration = (ApplicationConfiguration) merge(applicationConfiguration);
	}

	public void logicalDeleteApplicationConfiguration(ApplicationConfiguration applicationConfiguration) throws BusinessException {
		applicationConfiguration.rowInfo.setRecordStatus("D");
		updateApplicationConfiguration(applicationConfiguration);
	}
	
	public void addApplicationConfiguration(ApplicationConfiguration applicationConfiguration) throws BusinessException {
		
		//try{
		System.out.println("just before persist in ApplicationConfigurationService");
		persist(applicationConfiguration);
		
		System.out.println("just after persist in ApplicationConfigurationService");
		//}
	/*	catch (java.lang.RuntimeException e)
		{
			System.out.println("[ApplicationConfigurationService] RuntimeException caught in persist");
		
		}
		catch(Exception e)
		{
			System.out.println("[addApplicationConfiguration] : exception caught during persist" + e.getMessage());
			throw (BusinessException) e;
		}
		*/
	}
}
