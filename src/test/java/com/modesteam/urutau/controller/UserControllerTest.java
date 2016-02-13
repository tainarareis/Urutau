package com.modesteam.urutau.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.builder.UserBuilder;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.UserService;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;

public class UserControllerTest {
	private MockResult mockResult;
	private UserService mockUserService;
	private UserSession mockUserSession;
	private MockValidator mockValidator;

	@Before
	public void setup() {
		// Mocks supported by vraptor
		mockResult = new MockResult();
		mockValidator = new MockValidator();
		
		// Components of system
		mockUserService = EasyMock.createMock(UserService.class);
		mockUserSession = EasyMock.createMock(UserSession.class);


		Logger.getLogger(UserController.class).setLevel(Level.DEBUG);
	}
	
	@Test
	public void registerValid() {
		UserBuilder builder = new UserBuilder();
		
		User user = builder
				.email("example@email.com")
				.login("fulano")
				.password("123456")
				.passwordVerify("123456")
				.name("Tester")
				.lastName("Sobrenome")
				.build();
		
		mockIsExistsField(user.getLogin(), "login", true);
		mockIsExistsField(user.getEmail(), "email", true);
		
		mockAdd(user);

		EasyMock.replay(mockUserService);

		UserController controller = new UserController(mockResult, mockUserService, 
				mockUserSession, mockValidator);
		
		controller.register(user);
	}
	
	@Test(expected=ValidationException.class)
	public void registerInvalid1() {
		UserBuilder builder = new UserBuilder();
		
		User user = builder.build();

		UserController controller = new UserController(mockResult, 
				mockUserService, mockUserSession, mockValidator);
		
		controller.register(user);
	}

	@Test(expected=ValidationException.class)
	public void registerInvalid2() {
		UserBuilder builder = new UserBuilder();
		
		User user = builder
				.email("example@email.com")
				.login("fulano")
				.password("123456")
				.passwordVerify("diff")
				.name("Tester")
				.lastName("Sobrenome")
				.build();
		
		mockIsExistsField(user.getLogin(), "login", false);
		mockIsExistsField(user.getEmail(), "email", false);
		
		mockAdd(user);

		EasyMock.replay(mockUserService);

		UserController controller = new UserController(mockResult,
				mockUserService, mockUserSession, mockValidator);
		
		controller.register(user);
	}

	@Test
	public void loginValid(){
		UserBuilder builder = new UserBuilder();
		
		User user = builder
					.email("example@email.com")
					.login("fulano")
					.password("123456")
					.passwordVerify("diff")
					.name("Tester")
					.lastName("Sobrenome")
					.build();

		mockAuthenticate(user.getLogin(), user.getPassword(), user );
		
		EasyMock.replay(mockUserService);

		UserController controller = new UserController(mockResult,
				mockUserService, mockUserSession, mockValidator);
		
		controller.authenticateUser("fulano","123456");
	}
	
	/**
	 * Throws an validation exception, not covarage by eclemma
	 */
	@Test(expected=ValidationException.class)
	public void loginInvalid() {
		UserBuilder builder = new UserBuilder();
		
		User user = builder
					.email("example@email.com")
					.login("fulano")
					.password("123456")
					.passwordVerify("diff")
					.name("Tester")
					.lastName("Sobrenome")
					.build();

		mockAuthenticate(user.getLogin(), user.getPassword(), null);
		
		EasyMock.replay(mockUserService);

		UserController controller = new UserController(mockResult,
				mockUserService, mockUserSession, mockValidator);
		
		controller.authenticateUser(user.getLogin(), user.getPassword());
	}
	
	private void mockAuthenticate(String login, String password, User returnValue) {
		EasyMock.expect(mockUserService.authenticate(login, password)).andReturn(returnValue);
		
	}

	
	private void mockIsExistsField(Object value, String field, Boolean returnValue) {
		EasyMock.expect(mockUserService.canBeUsed(field, value)).andReturn(returnValue);
	}
	
	private void mockAdd(User user){
		mockUserService.create(user);
		EasyMock.expectLastCall();
	}
}