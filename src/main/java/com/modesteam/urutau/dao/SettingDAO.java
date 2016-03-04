package com.modesteam.urutau.dao;

import com.modesteam.urutau.model.system.setting.Setting;

/**
 * Data Access Object for the Configuration
 */
public interface SettingDAO {
		
	/**
	 * Gets a object instance that have a field with certain value
	 */
	Setting get(Setting setting) throws Exception;
	
	/**
	 * Update an setting
	 */
	void update(Setting setting);
	
}
