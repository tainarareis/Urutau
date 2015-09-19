package com.modesteam.urutau.controller;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;

import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.builder.UserBuilder;
import com.modesteam.urutau.dao.SystemDAO;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.UserService;

public class UserControllerTest {
	private MockResult mockResult;
	private SystemDAO mockSystemDAO;
	private UserService mockUserService;
	private UserManager mockUserManager;
	private MockValidator mockValidator;

	@Before
	public void setup() {
		// Mocks supported by vraptor
		mockResult = new MockResult();
		mockValidator = new MockValidator();
		
		// Components of system
		mockSystemDAO = EasyMock.createMock(SystemDAO.class);
		mockUserService = EasyMock.createMock(UserService.class);
		mockUserManager = EasyMock.createMock(UserManager.class);
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
				.build();
		
		mockIsExistsField(user.getLogin(), "login", true);
		mockIsExistsField(user.getEmail(), "email", true);
		
		mockAdd(user);

		EasyMock.replay(mockUserService);

		UserController controller = new UserController(mockResult,
				mockSystemDAO, mockUserService, mockUserManager, mockValidator);
		
		controller.register(user);
	}
	
	@Test(expected=ValidationException.class)
	public void registerInvalid1() {
		UserBuilder builder = new UserBuilder();
		
		User user = builder.build();

		UserController controller = new UserController(mockResult,
				mockSystemDAO, mockUserService, mockUserManager, mockValidator);
		
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
				.build();
		
		mockIsExistsField(user.getLogin(), "login", false);
		mockIsExistsField(user.getEmail(), "email", false);
		
		mockAdd(user);

		EasyMock.replay(mockUserService);

		UserController controller = new UserController(mockResult,
				mockSystemDAO, mockUserService, mockUserManager, mockValidator);
		
		controller.register(user);
	}

	private void mockIsExistsField(Object value, String field, Boolean returnValue) {
		EasyMock.expect(mockUserService.existsField(field, value)).andReturn(returnValue);
	}
	
	private void mockAdd(User user){
		mockUserService.create(user);
		EasyMock.expectLastCall();
	}
}
