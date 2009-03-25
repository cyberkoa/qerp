package com.quesoware.business.domain.system.iface;

import java.util.List;

import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.DoesNotExistException;
import com.quesoware.business.domain.system.ApplicationConfiguration;


/**
 * The <code>IApplicationConfigurationServiceRemote</code> bean exposes the business methods
 * in the interface.
 */
public interface IApplicationConfigurationServiceRemote {

	// ApplicationConfiguration

	ApplicationConfiguration findApplicationConfiguration(Long id)throws DoesNotExistException;

	List<ApplicationConfiguration> findApplicationConfigurations() throws DoesNotExistException;

	void updateApplicationConfiguration(ApplicationConfiguration applicationConfiguration) throws BusinessException;
	
	void addApplicationConfiguration(ApplicationConfiguration applicationConfiguration) throws BusinessException;
	
	void logicalDeleteApplicationConfiguration(ApplicationConfiguration applicationConfiguration) throws BusinessException;

}
