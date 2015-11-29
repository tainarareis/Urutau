package com.modesteam.urutau.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.dao.ProjectDAO;
import com.modesteam.urutau.dao.RequirementDAO;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.ProjectService;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;

public class ProjectControllerTest {
	
	private final Logger logger = Logger.getLogger(ProjectController.class);
	
	private MockResult mockResult;
	private UserSession mockUserSession;
	private MockValidator mockValidator;
	private ProjectService mockService;
	
	
	@Before
	public void setup() {
		// Catch all!
		logger.setLevel(Level.DEBUG);
		
		// Mocks supported by vraptor
		mockResult = new MockResult();
		mockValidator = new MockValidator();

		// System components
		mockService = (ProjectService) EasyMock.createMock(ProjectService.class);
		
		mockUserSession = EasyMock.createMock(UserSession.class);
		
		User userMock = EasyMock.createNiceMock(User.class);
		
		EasyMock.expect(mockUserSession.getUserLogged()).andReturn(userMock).anyTimes();
		EasyMock.replay(mockUserSession);
	}
	
	@Test
	public void createValidProject(){
		ProjectBuilder projectBuilder = new ProjectBuilder();

		Project project = projectBuilder.id(1L).title("Example")
				.description("test unit").buildProject();
 
		mockAdd(project);
		PowerMock.replayAll();
		ProjectController controllerMock = 
				new ProjectController(mockResult, mockUserSession, mockService, mockValidator);
		controllerMock.createProject(project);
	}
	
	private void mockAdd(Project project) {
		mockService.save(project);
		EasyMock.expectLastCall();
	}
	

}
