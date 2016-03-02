package com.modesteam.urutau.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.UserDAO;
import com.modesteam.urutau.model.Administrator;
import com.modesteam.urutau.model.User;

@RequestScoped
public class AdministratorService {
	
	private static final Logger logger = LoggerFactory.getLogger(AdministratorService.class);
	
	private static final String DEFAULT_ADMIN_DATA = "administrator";
	
	private final UserDAO userDAO;
	
	public AdministratorService() {
		this(null);
	}
	
	@Inject
	public AdministratorService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public void create(Administrator administrator) {
		userDAO.create(administrator);
	}
	
	/**
	 * Check if there is any account registered on DB as administrator
	 * 
	 * @return true if there is any administrator registered
	 * 
	 */
	public boolean existAdministrator() {
		logger.debug("Verifying existence of administrator...");
		
		return userDAO.hasAnyRegister();
	}
}
