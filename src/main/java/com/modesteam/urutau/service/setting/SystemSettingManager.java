package com.modesteam.urutau.service.setting;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.modesteam.urutau.model.system.setting.Setting;
import com.modesteam.urutau.model.system.setting.SystemSetting;
import com.modesteam.urutau.model.system.setting.SystemSettingContext;

@ApplicationScoped
public class SystemSettingManager implements SettingManager {

	@Inject private EntityManager manager;
	
	private Map<SystemSettingContext, SystemSetting> settings;
	
	@PostConstruct
	public void setUp() {
		this.settings = new EnumMap<>(SystemSettingContext.class);
		
		loadSystemSettings();
	}
	
	@Override
	public void save(Setting setting) {
		// Removes to update current settings	
		settings.remove(setting.getContext());
		
		try {
			manager.merge(setting);
		} catch(IllegalArgumentException exception) {
			try {
				manager.persist(setting);
			} catch (Exception e) {
				throw new IllegalStateException("When persist a setting, db fails");
			}
		}
		
		settings.put((SystemSettingContext) setting.getContext(), 
				(SystemSetting) setting);	
	}

	public Setting get(SystemSettingContext context) {
		return settings.get(context);
	}
	
	private void loadSystemSettings() {
		for (SystemSettingContext context : SystemSettingContext.values()) {
			SystemSetting systemSetting = manager.find(SystemSetting.class, 
					context.getId()); 
			this.settings.put(context, systemSetting);
		}
	}
	
}
