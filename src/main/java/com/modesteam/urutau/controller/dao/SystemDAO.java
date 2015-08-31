package com.modesteam.urutau.controller.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.modesteam.urutau.controller.model.Administrator;
import com.modesteam.urutau.controller.model.system.Configurations;

@RequestScoped
public class SystemDAO {
	@Inject
	private EntityManager manager;
	/**
	 * Check if there is any account registered on DB as administrator
	 * 
	 * @return true if there is any administrator registered
	 * 
	 */
	public boolean existAdministrator() {
		String sql = "SELECT adm FROM ADMINISTRATOR adm";
		Query query = manager.createQuery(sql);
		int existenceOfAdministrator = query.getFirstResult();
		if(existenceOfAdministrator == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * If no account has been found in DB, the system will create
	 * an administrator with the login and password "admin".
	 */
	public void createFirstAdministrator() {
		Administrator administrator = new Administrator();
		administrator.setLogin("admin");
		administrator.setPassword("admin");
		administrator.setConfigurations(new Configurations());
		manager.persist(administrator);
	}

}
