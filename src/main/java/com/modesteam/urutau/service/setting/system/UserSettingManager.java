package com.modesteam.urutau.service.setting.system;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.modesteam.urutau.model.system.setting.Setting;
import com.modesteam.urutau.service.setting.SettingManager;

@SessionScoped
public class UserSettingManager implements SettingManager {
	
	private final EntityManager manager;
	
	/**
	 * @deprecated only CDI
	 */
	public UserSettingManager() {
		this(null);
	}
	
	@Inject
	public UserSettingManager(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void save(Setting setting) {
		try {
			manager.merge(setting);
		} catch(IllegalArgumentException exception) {
			try {
				manager.persist(setting);
			} catch (Exception e) {
				throw new IllegalStateException("When persist, db fails");
			}
		}
	}

	@Override
	public Setting get(Setting setting) {
		return setting;
	}
}
