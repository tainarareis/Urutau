package com.modesteam.urutau.controller;


import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.UserService;

/**
 * 
 * This controller have actions directly connect to user
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static final String LOGIN_ERROR = "loginError";
	private static final String REGISTER_ERROR = "registerError";

	private final Result result;
	private final UserService userService;
	private final UserManager userManager;
	private final Validator validator;

	/*
	 * CDI needs this
	 */
	public UserController() {
		this(null, null, null, null);
	}
	
	@Inject
	public UserController(Result result, 
			UserService userService, UserManager userManager,
			Validator validator) {
		this.result = result;
		this.userService = userService;
		this.userManager = userManager;
		this.validator = validator;
	}
	
	/**
	 * Method to register another user in system.
	 * 
	 * @param user is an user of model class.
	 * 
	 */
	@Post
	@Path("/register")
	public void register(User user) {
		
		logger.info("Initiate an register");

		if(user.getPassword() == user.getPasswordVerify()) {
			logger.info("User will be persisted, and page redirected");
			userService.create(user);
			result.redirectTo(this).showSignInSucess();
		} else if(user.getEmail() == null || user.getLogin() == null || 
				user.getName() == null || user.getPasswordVerify() == null) {
				validator.add(new SimpleMessage(REGISTER_ERROR, "Campo em branco!"));
		} else {			
			//Verifies the existence the current login and email are already registered
			if(!userService.existsField("login", user.getLogin())) {
				validator.add(new SimpleMessage(REGISTER_ERROR, "Login em uso!"));
			} else if(!userService.existsField("email", user.getEmail())) {
				validator.add(new SimpleMessage(REGISTER_ERROR, "Email já utilizado"));			
			} else {
				validator.add(new SimpleMessage(REGISTER_ERROR, "As senhas não são compatíveis!"));
			}
		}
		// If happens any error of validation
		validator.onErrorUsePageOf(IndexController.class).index();
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
	/**
	 * Authenticate user, putting him on session
	 * 
	 * @param login field of user
	 * @param password secret word of user
	 */
	@Post("/userAuthentication")
    public void authenticateUser(String login, String password) {
        User user = userService.authenticate(login, password);

        if (user != null) {
            userManager.login(user);
            result.redirectTo(UserController.class).projectManager();
            logger.info("The user was found and is authenticated");
        } else {
        	logger.info("The called user wasn't found");
        	validator.add(new SimpleMessage(LOGIN_ERROR, "Senha ou login não conferem!"));
        	validator.onErrorUsePageOf(IndexController.class).index();
        }
    }

    @Get("/logout")
    public void logout() {
        userManager.logout();
        result.redirectTo(IndexController.class).index();
    }
	
	@View
	public void login() {		
	
	}
	
	@View
	@Get("/home")
	public void home() {
		
	}
	
	@View
	@Get("/projectManager")
	public void projectManager() {
		
	}
	@View
	@Get("/project")
	public void project() {
		
	}
	
	@View
	public void showSignInSucess() {
		
	}
}
