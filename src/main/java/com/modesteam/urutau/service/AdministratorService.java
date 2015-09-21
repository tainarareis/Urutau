package com.modesteam.urutau.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.UserDAO;
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
}
