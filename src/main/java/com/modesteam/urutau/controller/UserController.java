package com.modesteam.urutau.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.service.UserService;
import com.modesteam.urutau.service.validator.RegisterValidator;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;

/**
 * 
 * This controller have actions directly connect to user
 */
@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private static final String LOGIN_ATTRIBUTE = "login";
	private static final String EMAIL_ATTRIBUTE = "email";
	private static final String REGISTER_VALIDATOR = "register";
	private static final String LOGIN_VALIDATOR = "login";

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
	public UserController(Result result, UserService userService, UserSession userSession,
			Validator validator) {
		this.result = result;
		this.userService = userService;
		this.userSession = userSession;
		this.validator = validator;
	}

	/**
	 * To register a new user instance
	 * 
	 * @param user
	 *            have beans validations that say to {@link Validator} throws or
	 *            not a error message
	 */
	@Post
	public void register(final @Valid UrutaUser user) {

		logger.info("Request user register");

		// To fills form again
		result.include(user);

		// It makes bean validation and manual validations
		validateBeforeCreate(user);

		userService.create(user);

		result.forwardTo(this).showSignInSucess();
	}

	/**
	 * Set the new first administrator login and password
	 */
	@Post
	@Path("/administratorSettings")
	public void administratorSettings(UrutaUser user) {
		UrutaUser logged = userSession.getUserLogged();
		logged.setLogin(user.getLogin());
		logged.setPassword(user.getPassword());
		userSession.login(logged);
		userService.update(logged);
		result.redirectTo(AdministratorController.class).welcomeAdministrator();
	}

	/**
	 * Authenticate user, putting him on session
	 * 
	 * @param login
	 *            field of user
	 * @param password
	 *            secret word of user
	 * @throws Exception
	 */
	@Post
	public void authenticate(String login, String password) throws Exception {
		UrutaUser user = userService.authenticate(login, password);

		validator.check(user != null,
				new I18nMessage(LOGIN_VALIDATOR, "Senha ou login nao conferem"));

		// On error go to index
		validator.onErrorRedirectTo(IndexController.class).index();

		// put in session
		userSession.login(user);

		result.use(Results.referer()).redirect();
	}

	@Get("/logout")
	public void logout() {
		userSession.logout();
		result.redirectTo(IndexController.class).index();
	}

	@View
	public void showSignInSucess() {

	}

	/**
	 * Case of any error redirect to index with errors. This method considers
	 * beans validation too
	 */
	private void validateBeforeCreate(UrutaUser user) {
		RegisterValidator registerValidator = new RegisterValidator(user);

		validator.addIf(registerValidator.hasNullField(),
				new I18nMessage(REGISTER_VALIDATOR, "all_fields_required"));

		validator.onErrorUsePageOf(IndexController.class).index();

		validator.addIf(!registerValidator.validPasswordConfirmation(),
				new I18nMessage(REGISTER_VALIDATOR, "password_are_not_equals"));

		validator.addIf(!userService.canBeUsed(LOGIN_ATTRIBUTE, user.getLogin()),
				new I18nMessage(REGISTER_VALIDATOR, "login_already_in_use"));

		validator.addIf(!userService.canBeUsed(EMAIL_ATTRIBUTE, user.getEmail()),
				new I18nMessage(REGISTER_VALIDATOR, "email_already_in_use"));

		validator.onErrorUsePageOf(IndexController.class).index();
	}
}
