package com.modesteam.urutau.controller;


import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.dao.IndexController;
import com.modesteam.urutau.dao.SystemDAO;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.UserService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;


/**
 * 
 * This controller have actions directly connect to user
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static final String CATEGORY_ERROR = "message";

	private final Result result;
	private final SystemDAO systemDAO;
	private final UserService userService;
	private final UserManager userManager;
	private final Validator validator;

	/*
	 * CDI needs this
	 */
	public UserController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public UserController(Result result, SystemDAO systemDAO, 
			UserService userService, UserManager userManager,
			Validator validator) {
		this.result = result;
		this.systemDAO = systemDAO;
		this.userService = userService;
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
			//Verifies the existence the current login and email are already registered
			if(!userService.existsField("login", user.getLogin())) {
				validator.add(new SimpleMessage(CATEGORY_ERROR, "Login em uso!"));
			} else if(!userService.existsField("email", user.getEmail())) {
				validator.add(new SimpleMessage(CATEGORY_ERROR, "Email já utilizado"));
			} else if(user.getPassword().equalsIgnoreCase(user.getPasswordVerify())) {
				logger.info("User will be persisted, and page redirected");
				userService.create(user);
				result.redirectTo(this).showSignInSucess();
			} else {
				validator.add(new SimpleMessage(CATEGORY_ERROR, "As senhas não são compatíveis!"));
			}
		}
		// If happens any error of validation
		validator.onErrorUsePageOf(IndexController.class).index();
	}	
	
	
	
	@Get
	public void login(){		
	
	}
	
	/**
	 * Sets up the user and redirects him to two possible jsp pages
	 * depending on the user kind.
	 * @param user
	 */
	@Post
	@Path("/login")
	public void userAuthentication(User user) {
		// Se for o primeiro administrador, ja redireciona pra configurar
		// Deixar login limpo!
		if (!userService.existsUser(user)){
			validator.add(new SimpleMessage(CATEGORY_ERROR, "Usuário inexistente no sistema!"));
			
		}else{			
		
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
		userService.update(logged);
		result.redirectTo(AdministratorController.class).welcomeAdministrator();
	}
	
	@View
	public void welcomeUser() {
		
	}
	
	@View
	public void showSignInSucess() {
		
	}

	@Post("/authenticate")
    public void authenticateUser(User user) {
        User user = userDao.authenticate(user.getLogin(), user.getPassword());

        if (user != null) {
            userSession.setUser(user);

            result.redirectTo(IndexController.class).index();
        } else {
            result.include("error", "E-mail ou senha incorreta!").redirectTo(this).login();
        }
    }

    @Get("/logout")
    public void logout() {
        userSession.logout();
        result.redirectTo(this).login();
    }
}
