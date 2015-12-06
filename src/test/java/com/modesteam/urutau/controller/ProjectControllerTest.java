package com.modesteam.urutau.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.builder.ProjectBuilder;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.UserService;

public class ProjectControllerTest {
	
	private final Logger logger = Logger.getLogger(ProjectController.class);
	
	private MockResult mockResult;
	private UserSession mockUserSession;
	private MockValidator mockValidator;
	private ProjectService mockService;

	private UserService mockUserService;
	
	
	@Before
	public void setup() {
		// Catch all!
		logger.setLevel(Level.DEBUG);
		
		// Mocks supported by vraptor
		mockResult = new MockResult();
		mockValidator = new MockValidator();

		// System components
		mockService = EasyMock.createMock(ProjectService.class);
		
		mockUserService = EasyMock.createMock(UserService.class);
		
		mockUserSession = EasyMock.createMock(UserSession.class);
		
		User userMock = EasyMock.createNiceMock(User.class);
		
		EasyMock.expect(mockUserSession.getUserLogged()).andReturn(userMock).anyTimes();
		EasyMock.replay(mockUserSession);
	}
	
	@Test
	public void createValidProject(){
		ProjectBuilder projectBuilder = new ProjectBuilder();

		Project project = projectBuilder.id(1L).title("Example Valid")
				.description("test unit").builProject();
 
		mockAdd(project);
		PowerMock.replayAll();
		ProjectController controllerMock = 
				new ProjectController(mockResult, mockUserSession, mockService, mockUserService, mockValidator);
		controllerMock.createProject(project);
	}
	
	@Test(expected=ValidationException.class)
	public void createInvalidProject(){
		
		ProjectBuilder projectBuilder = new ProjectBuilder();

		Project project = projectBuilder.id(1L).title(null)
				.description("test unit").builProject();
 
		mockAdd(project);
		PowerMock.replayAll();
		ProjectController controllerMock = 
				new ProjectController(mockResult, mockUserSession, mockService, mockUserService, mockValidator);
		controllerMock.createProject(project);
	}
	

	@Test
	public void deleteValidProject(){
		ProjectBuilder projectBuilder = new ProjectBuilder();

		mockExistence(1L, true);
		mockRemove(1L);
		EasyMock.replay(mockService);
		PowerMock.replayAll();
		
		
		ProjectController controllerMock = 
				new ProjectController(mockResult, mockUserSession, mockService, mockUserService, mockValidator);
		controllerMock.deleteProject(1L);
	}
	
	@Test(expected=ValidationException.class)
	public void deleteInvalidProject(){
		
		mockExistence(1L, false);
		mockRemove(1L);
		
		ProjectController controllerMock = 
				new ProjectController(mockResult, mockUserSession, mockService, mockUserService, mockValidator);
		controllerMock.deleteProject(1L);
	}
	
	private void mockAdd(Project project) {
		mockService.save(project);
		EasyMock.expectLastCall();
	}
	
	private void mockRemove(Long id) {
		mockService.excludeProject(id);
		EasyMock.expectLastCall();
	}
	
	private void mockExistence(Long id, boolean returnValue) {
		EasyMock.expect(mockService.verifyProjectExistence(id)).andReturn(returnValue);
	}

}
