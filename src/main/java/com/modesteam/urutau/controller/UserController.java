package com.modesteam.urutau.controller;


import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.dao.SystemDAO;
import com.modesteam.urutau.dao.UserDAO;
import com.modesteam.urutau.model.User;

/**
 * 
 * This controller have actions directly connect to user
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static final String CATEGORY_ERROR = "message";
	@Inject
	private final Result result;
	@Inject
	private final SystemDAO systemDAO;
	@Inject
	private final UserDAO userDAO;
	@Inject
	private final UserManager userManager;
	@Inject
	private final Validator validator;
	
	/*
	 * CDI needs this
	 */
	public UserController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public UserController(Result result, SystemDAO systemDAO, 
			UserDAO userDAO, UserManager userManager, Validator validator) {
		this.result = result;
		this.systemDAO = systemDAO;
		this.userDAO = userDAO;
		this.userManager = userManager;
		this.validator = validator;
	}
	
	/**
	 * Method to register another user in system.
	 * @param user is an user of model class.
	 * @return
	 * 
	 */
	@Post
	@Path("/register")
	public void register(User user) {
		
		logger.info("Initiate an register");

		// Validate if any field is null
		if(user.getEmail() == null || user.getLogin() == null || 
				user.getName() == null || user.getPasswordVerify() == null) {
				validator.add(new SimpleMessage(CATEGORY_ERROR, "Campo em branco!"));
		} else {			
			// If login or email already exist
			if(!userDAO.existsField(user.getLogin(), "login")) {
				validator.add(new SimpleMessage(CATEGORY_ERROR, "Login em uso!"));
			} 
			
			if(!userDAO.existsField(user.getEmail(), "email")) {
				validator.add(new SimpleMessage(CATEGORY_ERROR, "Email já utilizado"));
			} 
			
			if(user.getPassword().equalsIgnoreCase(user.getPasswordVerify())) {
					logger.info("User will be persisted, and page redirected");
					userDAO.add(user);
					result.redirectTo(this).showSignInSucess();
			} else {
					validator.add(new SimpleMessage(CATEGORY_ERROR, "As senhas não são compatíveis!"));
			}
		}
		
		validator.onErrorRedirectTo(IndexController.class).index();
	
	}	
	
	/**
	 * Setts up the user and redirects him to two possible jsp pages
	 * depending on the user kind.
	 * @param user
	 */
	@Post
	@Path("/login")
	public void login(User user) {
		// Se for o primeiro administrador, ja redireciona pra configurar
		// Deixar login limpo!
		if(systemDAO.isFirstAdministrator()){
			result.redirectTo(AdministratorController.class).changeFirstSettings();
		} else {
			result.redirectTo(this).welcomeUser();
		}
	}
		
	/**
	 * Set the new first administrator login and password
	 */
	@Post
	@Path("/administratorSettings")
	public void administratorSettings(User user) {
		User logged = userManager.getUserLogged();
		logged.setLogin(user.getLogin());
		logged.setPassword(user.getPassword());
		userManager.setUserLogged(logged);
		userDAO.newUserSettings(logged);
		result.redirectTo(AdministratorController.class).welcomeAdministrator();
	}
	
	@View
	public void welcomeUser() {
		
	}
	
	@View
	public void showSignInSucess() {
		
	}

}
