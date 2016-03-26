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
import com.modesteam.urutau.service.validator.RegisterValidator;

/**
 * 
 * This controller have actions directly connect to user
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static final String LOGIN_ATTRIBUTE = "login";

	private static final String EMAIL_ATTRIBUTE = "email";
	
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
	public UserController(Result result, UserService userService, 
			UserSession userSession, Validator validator) {
		this.result = result;
		this.userService = userService;
		this.userSession = userSession;
		this.validator = validator;
	}
	
	/**
	 * Method to register another user in system.
	 * 
	 * @param user is an user of model class.
	 */
	@Post
	@Path("/register")
	public void register(User user) {
		logger.info("Initiate register");
		
		RegisterValidator registerValidator = new RegisterValidator(user);
		
		List<SimpleMessage> errors = new ArrayList<SimpleMessage>();

		if (registerValidator.hasNullField()) {
			SimpleMessage error = new SimpleMessage(FieldMessage.ERROR, "All fields are required");
			errors.add(error);
		} else if (!registerValidator.validPasswordConfirmation()) { 
			SimpleMessage error = new SimpleMessage(FieldMessage.ERROR, "Password are not equals");
			errors.add(error);
		} else if(!userService.canBeUsed(LOGIN_ATTRIBUTE, user.getLogin())) {
			SimpleMessage error = new SimpleMessage(FieldMessage.ERROR, "Login is already in use");
			errors.add(error);
		} else if(!userService.canBeUsed(EMAIL_ATTRIBUTE, user.getEmail())) {
			SimpleMessage error = new SimpleMessage(FieldMessage.ERROR, "Email is already in use");
			errors.add(error);
		}
		
		validator.addAll(errors);
		
		logger.debug("Number of errors are " + errors.size());
		
		// If happens any error of validation
		validator.onErrorUsePageOf(IndexController.class).index();

		userService.create(user);
		
		result.redirectTo(this).showSignInSucess();
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
		userSession.login(logged);
		userService.update(logged);
		result.redirectTo(AdministratorController.class).welcomeAdministrator();
	}
	/**
	 * Authenticate user, putting him on session
	 * 
	 * @param login field of user
	 * @param password secret word of user
	 */
	@Post
    public void authenticate(String login, String password) {
        User user = userService.authenticate(login, password);

        validator.check(user != null, 
        		new SimpleMessage(FieldMessage.ERROR, "Senha ou login nao conferem"));
        
        // On error go to index
        validator.onErrorUsePageOf(IndexController.class).index();
        
    	// put in session
        userSession.login(user);
        
        result.redirectTo(ProjectController.class).index();
    }

    @Get("/logout")
    public void logout() {
        userSession.logout();
        result.redirectTo(IndexController.class).index();
    }
	
	@View
	public void showSignInSucess() {
		
	}
}
