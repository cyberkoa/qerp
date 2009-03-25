package com.quesoware.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Insert the type's description here. Creation date: (26/02/01 22:22:21)
 * 
 * @author: Administrator
 */
public class ResourceReader {
	static public final Log LOG = LogFactory.getLog(ResourceReader.class);
	static private ResourceReader _myClass = null;

	private ResourceReader() {
	}

	static public Properties getAsProperties(String resourceName) throws UtilRuntimeException {
		return getAsProperties(resourceName, Thread.currentThread().getContextClassLoader());
	}

	static public Properties getAsProperties(String resourceName, ClassLoader classLoader) throws UtilRuntimeException {

		Properties p;

		if (resourceName == null) {
			throw new IllegalArgumentException("resourceName is null");
		}
		if (_myClass == null) {
			_myClass = new ResourceReader();
		}

		InputStream resourceStream = null;
		try {
			// Try local

			resourceStream = classLoader.getResourceAsStream("/" + resourceName);

			// If not found, try classpath

			if (resourceStream == null) {
				resourceStream = classLoader.getResourceAsStream(resourceName);
			}
			
			// If not found, then get out

			if (resourceStream == null) {
				LOG.error("Could not load properties from resource \"" + resourceName + "\".  Check the classpath.");
				System.err.println("Could not load properties from resource \"" + resourceName
						+ "\".  Check the classpath.");
				throw new UtilRuntimeException("Could not find resource \"" + resourceName
						+ "\".  Check the classpath.");
			}
			
			// Load the properties!

			p = new Properties();
			p.load(resourceStream);
		}
		catch (UtilRuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new UtilRuntimeException("Could not load properties from resource \"" + resourceName
					+ "\".  Check the classpath.", e);
		}
		finally {
			if (resourceStream != null) {
				try {
					resourceStream.close();
				}
				catch (Throwable ignore) {
				}
			}
		}

		return p;

	}

}
