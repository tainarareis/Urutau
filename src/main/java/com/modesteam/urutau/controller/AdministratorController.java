package com.modesteam.urutau.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.dao.UserDAO;
import com.modesteam.urutau.model.User;
/**
 * Executes main logics of administrator
 */
@Controller
public class AdministratorController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);
	
	private final Result result;
	
	private final UserDAO userDAO;
	
	/*
	 * CDI 
	 */
	public AdministratorController() {
		this(null, null);
	}
	
	@Inject
	public AdministratorController(Result result, UserDAO userDAO) {
		this.result = result;
		this.userDAO = userDAO;
	}
	
	@Post("/changeFirstSettings")
	public void changeFirstSettings(User user){
		userDAO.update(user);
		result.redirectTo(this).changeSecondSettings();
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
