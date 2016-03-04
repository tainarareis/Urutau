package com.modesteam.urutau.component;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.modesteam.urutau.annotation.SystemManager;
import com.modesteam.urutau.model.system.setting.SystemSetting;

/**
 * This class produces a {@link EntityManager} 
 * to persist and update {@link SystemSetting} entity
 */
public class SettingEntityManagerProducers {
	
	private final EntityManagerFactory factory;
	
	@Inject
	public SettingEntityManagerProducers(EntityManagerFactory factory) {
		this.factory = factory;
	}
	
	@Produces 
	@SystemManager
	@ApplicationScoped
	public EntityManager getSettingEntityManager() {
		return this.factory.createEntityManager();
	}
	
	public void destroy(@Disposes @SystemManager EntityManager entityManager) {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}
}
