package com.modesteam.urutau.controller;


import java.util.ArrayList;
import java.util.List;

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

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.model.system.FieldMessage;
import com.modesteam.urutau.service.UserService;

/**
 * 
 * This controller have actions directly connect to user
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private final Result result;
	private final UserService userService;
	private final UserSession userSession;
	private final Validator validator;

	/*
	 * CDI needs this
	 */
	public UserController() {
		this(null, null, null, null);
	}
	
	@Inject
	public UserController(Result result, 
			UserService userService, UserSession userSession,
			Validator validator) {
		this.result = result;
		this.userService = userService;
		this.userSession = userSession;
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
		
		logger.info("Initiate register");
		
		boolean haveInvalidField = user.getEmail() == null || user.getLogin() == null 
				|| user.getName() == null || user.getPasswordVerify() == null 
				|| user.getPassword() == null;
		
		List<SimpleMessage> errors = new ArrayList<SimpleMessage>();

		if(haveInvalidField) {
			
			errors.add(new SimpleMessage(FieldMessage.ERROR.toString(), "All fields are required"));

		} else if(!validVerification(user.getPassword(), user.getPasswordVerify())) {
			
			errors.add(new SimpleMessage(FieldMessage.ERROR.toString(), "Password are not equals!"));
	
		} else if(!userService.existsField("login", user.getLogin())) {
		
			errors.add(new SimpleMessage(FieldMessage.ERROR.toString(), "Login is already in use."));
		
		} else if(!userService.existsField("email", user.getEmail())) {
			
			errors.add(new SimpleMessage(FieldMessage.ERROR.toString(), "Email is already in use."));			
		}
		
		validator.addAll(errors);
		
		// If happens any error of validation
		validator.onErrorUsePageOf(IndexController.class).index();

		userService.create(user);
		
		result.redirectTo(this).showSignInSucess();
	}	
			
	private boolean validVerification(String password, String passwordVerify) {
		return password == passwordVerify;
	}

	/**
	 * Set the new first administrator login and password
	 */
	@Post
	@Path("/administratorSettings")
	public void administratorSettings(User user) {
		User logged = userSession.getUserLogged();
		logged.setLogin(user.getLogin());
		logged.setPassword(user.getPassword());
		userSession.setUserLogged(logged);
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
            userSession.login(user);
            result.redirectTo(ProjectController.class).index();
            logger.info("The user was found and is authenticated");
        } else {
        	logger.info("The called user wasn't found");
        	validator.add(new SimpleMessage(FieldMessage.ERROR.toString(), "Senha ou login não conferem!"));
        	validator.onErrorUsePageOf(IndexController.class).index();
        }
    }

    @Get("/logout")
    public void logout() {
        userSession.logout();
        result.redirectTo(IndexController.class).index();
    }
	
	@View
	public void login() {		
	
	}
	
	@View
	public void home() {
		
	}
	
	@View
	public void project() {
		
	}
	
	@View
	public void showSignInSucess() {
		
	}
}
