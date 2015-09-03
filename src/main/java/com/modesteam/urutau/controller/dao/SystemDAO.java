package com.modesteam.urutau.controller.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.controller.model.Administrator;

@RequestScoped
public class SystemDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemDAO.class);
	
	@Inject
	private EntityManager manager;
	
	/**
	 * Check if there is any account registered on DB as administrator
	 * 
	 * @return true if there is any administrator registered
	 * 
	 */
	public boolean existAdministrator() {
		String sql = "SELECT admin FROM " + Administrator.class.getName() + " admin";
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
		logger.info("Creating first administrator");
		Administrator administrator = new Administrator();
		administrator.setLogin("admin");
		administrator.setPassword("admin");
		manager.persist(administrator);
	}
}
