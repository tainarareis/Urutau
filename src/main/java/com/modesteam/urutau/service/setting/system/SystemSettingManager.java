package com.modesteam.urutau.service.setting.system;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.modesteam.urutau.annotation.SystemManager;
import com.modesteam.urutau.model.system.setting.Setting;
import com.modesteam.urutau.service.setting.SettingManager;

@ApplicationScoped
public class SystemSettingManager implements SettingManager {
	
	@SystemManager
	private final EntityManager manager;
	
	/**
	 * @deprecated only CDI
	 */
	public SystemSettingManager() {
		this(null);
	}
	
	@Inject
	public SystemSettingManager(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public void save(Setting setting) {
		manager.persist(setting);
	}

	@Override
	public Setting get(Setting setting) {
		return manager.find(Setting.class, setting.getId());
	}
	
}
