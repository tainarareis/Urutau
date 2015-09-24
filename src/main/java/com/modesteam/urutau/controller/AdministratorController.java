package com.modesteam.urutau.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.model.system.Configuration;
import com.modesteam.urutau.service.AdministratorService;
import com.modesteam.urutau.service.ConfigurationService;
/**
 * Executes main logics of administrator
 */
@Controller
public class AdministratorController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);
	
	private final Result result;
	
	private final AdministratorService administratorService;

	private final ConfigurationService systemService;
	
	/*
	 * CDI 
	 */
	public AdministratorController() {
		this(null, null, null);
	}
	
	@Inject
	public AdministratorController(Result result, AdministratorService administratorService, 
			ConfigurationService systemService) {
		this.result = result;
		this.administratorService = administratorService;
		this.systemService = systemService;
	}
	
	@Post("/changeFirstSettings")
	public void changeFirstSettings(User user) {
		logger.info("Configure default administrator");
		
		logger.info("New attributes is " + user.getEmail() + "|"
				+ user.getLogin() + "|"
				+ user.getName() + "|"
				+ user.getLastName() + "|" 
				+ user.getPassword());
		
		administratorService.configureNew(user);
		
		result.redirectTo(this).changeSecondSettings();
	}
	
	@Post("/changeSecondSettings")
	public void changeSecondSettings(Configuration config) {
		logger.info("Setting default configuration of system");
		
		systemService.put(config);
		
		result.redirectTo(UserController.class).login();
	}
	
	@View
	public void changeSecondSettings() {
		
	}

	@View
	public void welcomeAdministrator() {
		
	}
	
	@View
	public void changeFirstSettings() {
		
	}
}
