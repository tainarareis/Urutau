package com.modesteam.urutau.controller.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.controller.model.Administrator;

/**
 * URUTAU - 2015
 * Class responsible to ensure/allow administrator interaction 	
 * through the database management information about Administrators
 */
@RequestScoped
public class SystemDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemDAO.class);
	private static final String DEFAULT_ADMIN_DATA = "admin";
	
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
		administrator.setLogin(DEFAULT_ADMIN_DATA);
		administrator.setPassword(DEFAULT_ADMIN_DATA);
		manager.persist(administrator);
	}
	/**
	 * Verify the existence of first administrator,
	 * with login and password "admin","admin".
	 */
	public boolean isFirstAdministrator() {
		String sql = "SELECT u FROM User u WHERE u.login = :login"
				+ " AND u.password = :password";
		Query query = manager.createQuery(sql);
		query.setParameter("login", DEFAULT_ADMIN_DATA);
		query.setParameter("password", DEFAULT_ADMIN_DATA);
		if(query.getSingleResult() != null) {
			return true;
		} else {
			return false;
		}
	}
}
