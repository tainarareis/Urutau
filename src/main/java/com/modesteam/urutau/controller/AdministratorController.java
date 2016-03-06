package com.modesteam.urutau.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.Administrator;
import com.modesteam.urutau.model.system.setting.SystemSetting;
import com.modesteam.urutau.model.system.setting.SystemSettingContext;
import com.modesteam.urutau.service.AdministratorService;
import com.modesteam.urutau.service.setting.SystemSettingManager;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
/**
 * Executes main logics of administrator
 */
@Controller
public class AdministratorController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);
	
	private final Result result;
	
	private final AdministratorService administratorService;
	
	private final SystemSettingManager systemSettingManager;
	
	/*
	 * CDI 
	 */
	public AdministratorController() {
		this(null, null, null);
	}
	
	@Inject
	public AdministratorController(Result result, AdministratorService administratorService, 
			SystemSettingManager systemSettingManager) {
		this.result = result;
		this.administratorService = administratorService;
		this.systemSettingManager = systemSettingManager;
	}
	
	@Post("/createFirstAdministrator")
	public void createFirstAdministrator(Administrator administrator) {
		logger.info("Creating first administrator");
		
		logger.debug("New attributes are");
		logger.debug(administrator.getEmail());
		logger.debug(administrator.getLogin());
		logger.debug(administrator.getLastName());
		logger.debug(administrator.getPassword());
		
		administrator.createDefaultSettings();
		
		administratorService.create(administrator);
		
		result.redirectTo(this).changeSystemSettings();
	}
	
	@Post("/changeSystemSettings")
	public void changeSystemSettings(List<String> settings) {
		logger.info("Setting default configuration of system");
		
		SystemSetting emailOfSystem = new SystemSetting(SystemSettingContext.SYSTEM_EMAIL);
		emailOfSystem.setValue(settings.get(0));
		
		systemSettingManager.save(emailOfSystem);
		
		result.redirectTo(IndexController.class).index();
	}

	@View
	public void createFirstAdministrator() {
		
	}
	
	@View
	public void changeSystemSettings() {
		
	}

	@View
	public void welcomeAdministrator() {
		
	}
}
