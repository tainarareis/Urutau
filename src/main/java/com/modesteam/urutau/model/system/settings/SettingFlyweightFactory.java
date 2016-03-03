package com.modesteam.urutau.model.system.settings;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.modesteam.urutau.dao.SettingDAO;
import com.modesteam.urutau.model.User;

/**
 * {@link User} are {@link SessionScoped}
 * System are {@link ApplicationScoped}
 */
public class SettingFlyweightFactory {
	
	private Set<Setting> settings;
	
	@Inject
	private SettingDAO settingDAO;
	
	public SettingFlyweightFactory() {
		this.settings = new HashSet<Setting>();
	}
	
	public Set<Setting> create(SettingContext context) throws Exception {
		Set<Setting> requestedSettings = new HashSet<Setting>();
		
		switch (context) {
			case SYSTEM:
				
				for(SystemSettingContext s : SystemSettingContext.values()){
					
					SystemSetting setting = new SystemSetting(s);
					/*
					 *  TODO see this cast
					 *  That part is needed to get value of Setting 
					 */
					setting = (SystemSetting) settingDAO.get(setting);
					
					settings.add(setting);
					requestedSettings.add(setting);
				}
				
			case USER:
				// TODO
				break;
			default:
				// TODO
				break;
		}
		
		return requestedSettings;
	}
}
