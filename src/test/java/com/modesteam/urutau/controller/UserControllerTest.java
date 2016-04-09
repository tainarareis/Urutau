package com.modesteam.urutau.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.builder.UserBuilder;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.service.UserService;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;

public class UserControllerTest {

	private static final String LOGIN_ATTRIBUTE = "login";

	private static final String EMAIL_ATTRIBUTE = "email";
	
	private MockResult result;
	private UserService userService;
	private UserSession userSession;
	private MockValidator validator;

	@Before
	public void setup() {
		// Mocks supported by vraptor
		result = new MockResult();
		validator = new MockValidator();
		
		// Components of system
		userService = mock(UserService.class);
		userSession = mock(UserSession.class);


		Logger.getLogger(UserController.class).setLevel(Level.DEBUG);
	}
	
	@Test
	public void registerValid() {
		UserBuilder builder = new UserBuilder();
		
		UrutaUser user = builder
				.email("example@email.com")
				.login("fulano")
				.password("123456")
				.passwordVerify("123456")
				.name("Tester")
				.lastName("Sobrenome")
				.build();
		
		mockFieldWithValueToReturn(LOGIN_ATTRIBUTE, user.getLogin(), true);
		mockFieldWithValueToReturn(EMAIL_ATTRIBUTE, user.getEmail(), true);
		
		doNothingWhenCreateAn(user);

		UserController controller = new UserController(result, userService, userSession, validator);
		
		controller.register(user);
		
		assertFalse(validator.hasErrors());
	}
	

	@Test(expected=ValidationException.class)
	public void registerInvalidCaseOne() {
		UserBuilder builder = new UserBuilder();
		
		UrutaUser user = builder.build();

		UserController controller = new UserController(result, userService, userSession, validator);
		
		controller.register(user);
	}

	@Test(expected=ValidationException.class)
	public void registerInvalidCaseTwo() {
		UserBuilder builder = new UserBuilder();
		
		UrutaUser user = builder
				.email("example@email.com")
				.login("fulano")
				.password("123456")
				.passwordVerify("diff")
				.name("Tester")
				.lastName("Sobrenome")
				.build();
		
		mockFieldWithValueToReturn(LOGIN_ATTRIBUTE, user.getLogin(), true);
		mockFieldWithValueToReturn(EMAIL_ATTRIBUTE, user.getLogin(), true);
		
		doNothingWhenCreateAn(user);

		UserController controller = new UserController(result, userService, userSession, validator);

		controller.register(user);
	}

	@Test
	public void tryLoginWithSucces() {
		UserBuilder builder = new UserBuilder();
		
		UrutaUser user = builder
					.email("example@email.com")
					.login("fulano")
					.password("123456")
					.passwordVerify("diff")
					.name("Tester")
					.lastName("Sobrenome")
					.build();

		mockAuthenticate(user.getLogin(), user.getPassword(), user );

		UserController controller = new UserController(result, userService, userSession, validator);
		
		controller.authenticate("fulano","123456");
		
		assertFalse(validator.hasErrors());
	}
	
	/**
	 * Throws an validation exception, not covarage by eclemma
	 */
	@Test(expected=ValidationException.class)
	public void tryLoginFail() {
		UserBuilder builder = new UserBuilder();
		
		UrutaUser user = builder
					.email("example@email.com")
					.login("fulano")
					.password("123456")
					.passwordVerify("diff")
					.name("Tester")
					.lastName("Sobrenome")
					.build();

		mockAuthenticate(user.getLogin(), user.getPassword(), null);

		UserController controller = new UserController(result, userService, userSession, validator);
		
		controller.authenticate(user.getLogin(), user.getPassword());
	}
	
	private void mockAuthenticate(String login, String password, UrutaUser returnValue) {
		when(userService.authenticate(login, password)).thenReturn(returnValue);
	}

	private void mockFieldWithValueToReturn(String field, String value, boolean result) {
		when(userService.canBeUsed(field, value)).thenReturn(result);
	}
	
	private void doNothingWhenCreateAn(UrutaUser user){
		doNothing().when(userService).create(user);
	}
}