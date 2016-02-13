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
	
	public void configureNew(User userUp) {
		logger.info("Initiation of configuration!");
		
		User administratorDefault = userDAO.get("login", DEFAULT_ADMIN_DATA);
		
		administratorDefault.setEmail(userUp.getEmail());
		administratorDefault.setLastName(userUp.getLastName());
		administratorDefault.setName(userUp.getName());
		administratorDefault.setLogin(userUp.getLogin());
		administratorDefault.setPassword(userUp.getPassword());		

		userDAO.update(administratorDefault);
	}

	/**
	 * If no account has been found in DB, the system will create
	 * an administrator with the login and password "admin".
	 */
	public void createFirst() {
		logger.info("Creating first administrator");
		
		Administrator administrator = new Administrator();

		// Yet confirmed!
		administrator.setConfirmed(1);
		administrator.setLogin(DEFAULT_ADMIN_DATA);
		administrator.setPassword(DEFAULT_ADMIN_DATA);
		administrator.setEmail(DEFAULT_ADMIN_DATA.concat("@")
				.concat(DEFAULT_ADMIN_DATA).concat(".com"));
		administrator.setName(DEFAULT_ADMIN_DATA);
		administrator.setLastName(DEFAULT_ADMIN_DATA);
		administrator.setPasswordVerify(DEFAULT_ADMIN_DATA);
		
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
