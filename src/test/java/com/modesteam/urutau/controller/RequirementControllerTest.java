package com.modesteam.urutau.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.RequirementService;

public class RequirementControllerTest {

	private final Logger logger = Logger.getLogger(RequirementController.class);
	
	private MockResult mockResult;
	private UserSession mockUserSession;
	private MockValidator mockValidator;
	private RequirementService mockService;

	@Before
	public void setup() {
		// Catch all!
		logger.setLevel(Level.DEBUG);
		
		// Mocks supported by vraptor
		mockResult = new MockResult();
		mockValidator = new MockValidator();

		// System components
		mockService = EasyMock.createMock(RequirementService.class);
		
		mockUserSession = EasyMock.createMock(UserSession.class);
		
		User userMock = EasyMock.createNiceMock(User.class);
		
		EasyMock.expect(mockUserSession.getUserLogged()).andReturn(userMock).anyTimes();
		EasyMock.replay(mockUserSession);
	}
	
	@Test
	public void successfullyDeletedEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();

		Epic epic = builderEpic
				.id(1L)
				.title("Example")
				.description("test unit")
				.buildEpic();

		mockAdd(epic);
		PowerMock.replayAll();
		
		mockRemove(1L);
		
		RequirementController controllerMock = 
				new RequirementController(mockResult, mockService, mockValidator);
		controllerMock.delete(1L);
	}
	
	@Test
	public void validShow() throws UnsupportedEncodingException {
		ArtifactBuilder builder = new ArtifactBuilder();
		Generic genericRequirement = builder
				.id(1L)
				.title("Example")
				.description("test unit")
				.buildGeneric();
		
		EasyMock.expect(mockService.getRequirement(1, "Example")).andReturn(genericRequirement);
		
		RequirementController controller = new RequirementController(mockResult,
				mockService, mockValidator);
		
		controller.show(1, genericRequirement.getTitle());
	}
	
	@Test
	public void validShowAll() throws UnsupportedEncodingException{
		ArtifactBuilder builder = new ArtifactBuilder();
		Generic genericRequirement = builder
				.id(1L)
				.title("Example")
				.description("test unit")
				.buildGeneric();
		
		final List<Artifact> requirements = new ArrayList<Artifact>();
		
		requirements.add(genericRequirement);
		
		mockService = new RequirementService(){
			public java.util.List<? extends Artifact> loadAllRequirements() {
				return requirements;
			};
		};
		
		RequirementController controller = new RequirementController(mockResult,
				mockService, mockValidator);
		
		controller.showAll();
	}
	
	public void showAllRequirements() {
		
	}

	private void mockRemove(Long id) {
		mockService.delete(id);
		EasyMock.expectLastCall();
	}

	private void mockAdd(Artifact artifact) {
		mockService.save(artifact);
		EasyMock.expectLastCall();
	}
}