package com.modesteam.urutau.dao;

import com.modesteam.urutau.model.system.Configuration;

/**
 * Data Access Object for the Configuration
 */
public interface ConfigurationDAO {
	
	/**
	 * Creates a new instance of configuration
	 */
	boolean create(Configuration config);
	
	/**
	 * Gets a object instance that have a field with certain value
	 */
	Configuration get(String field, Object value) throws Exception;
		
	boolean update(Configuration config);
	
	boolean destroy(Configuration config);
}
