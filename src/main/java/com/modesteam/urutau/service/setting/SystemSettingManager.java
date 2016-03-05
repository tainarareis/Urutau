package com.modesteam.urutau.service.setting;

import java.util.EnumMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.modesteam.urutau.annotation.SystemManager;
import com.modesteam.urutau.model.system.setting.Setting;
import com.modesteam.urutau.model.system.setting.SystemSetting;
import com.modesteam.urutau.model.system.setting.SystemSettingContext;

@ApplicationScoped
public class SystemSettingManager implements SettingManager {
	
	@SystemManager
	private final EntityManager manager;
	
	private Map<SystemSettingContext, SystemSetting> settings;
	
	/**
	 * @deprecated only CDI
	 */
	public SystemSettingManager() {
		this(null);
	}
	
	@Inject
	public SystemSettingManager(EntityManager manager) {
		this.manager = manager;
		this.settings = new EnumMap<>(SystemSettingContext.class);
		// Load all system settings
		loadAll();
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
	
	private void loadAll() {
		for (SystemSettingContext context : SystemSettingContext.values()) {
			SystemSetting systemSetting = manager.find(SystemSetting.class, 
					context.getId()); 
			this.settings.put(context, systemSetting);
		}
	}
	
}
