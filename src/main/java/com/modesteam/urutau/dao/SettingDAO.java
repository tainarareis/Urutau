package com.modesteam.urutau.dao;

import com.modesteam.urutau.model.system.settings.GlobalSetting;

/**
 * Data Access Object for the Configuration
 */
public interface SettingDAO {
	
	/**
	 * Creates a new instance of configuration
	 */
	boolean create(GlobalSetting setting);
	
	/**
	 * Gets a object instance that have a field with certain value
	 */
	GlobalSetting get(String field, Object value) throws Exception;
		
	boolean update(GlobalSetting config);
	
	boolean destroy(GlobalSetting config);
}
