package com.modesteam.urutau.service.setting;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.modesteam.urutau.model.system.setting.Setting;

@SessionScoped
public class UserSettingManager implements SettingManager, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1457316681261263887L;
	
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
}
