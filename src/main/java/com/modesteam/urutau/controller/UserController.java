package com.modesteam.urutau.controller;


import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.annotation.View;
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

	@Post("/userAuthentication")
    public void authenticateUser(User userReceived) {
        User user = userService.confirmateAuthentication(userReceived.getLogin(), userReceived.getPassword(),
        		userReceived);

        if (user != null) {
            userManager.setUserLogged(user);
            logger.info("The user"+ user.getLogin() + " is logged.");
            result.redirectTo(UserController.class).welcomeUser();
            logger.info("The user was found and is authenticated");
        } else {
        	validator.add(new SimpleMessage(CATEGORY_ERROR, "Usuário inexistente no sistema!"));
        	result.redirectTo(IndexController.class).index();
            logger.info("The user wasn't found");
        }
        validator.onErrorUsePageOf(IndexController.class).index();
    }

    @Get("/logout")
    public void logout() {
        userManager.logout();
        result.redirectTo(IndexController.class).index();
    }
}
