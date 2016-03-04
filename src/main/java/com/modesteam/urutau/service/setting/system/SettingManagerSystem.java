package com.modesteam.urutau.service.setting.system;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.modesteam.urutau.annotation.SystemManager;
import com.modesteam.urutau.model.system.setting.Setting;
import com.modesteam.urutau.service.setting.SettingManager;

@ApplicationScoped
public class SettingManagerSystem implements SettingManager {
	
	@SystemManager
	private final EntityManager manager;
	
	/**
	 * @deprecated only CDI
	 */
	public SettingManagerSystem() {
		this(null);
	}
	
	@Inject
	public SettingManagerSystem(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public void save(Setting setting) {
		manager.persist(setting);
	}

	@Override
	public void get(Setting setting) {
		manager.find(Setting.class, setting.getId());
	}
	
}
