package com.modesteam.urutau.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;

public class RequirementControllerTest {

	private static final Long FAKE_REQUIREMENT_ID = 1L;

	private final Logger logger = Logger.getLogger(RequirementController.class);
	
	private MockResult result;
	private UserSession userSession;
	private MockValidator validator;
	private RequirementService requirementService;

	@Before
	public void setup() {
		// Catch all!
		logger.setLevel(Level.DEBUG);
		
		// Mocks supported by vraptor
		result = new MockResult();
		validator = new MockValidator();

		// System components
		requirementService = mock(RequirementService.class);
		
		userSession = mock(UserSession.class);
		
		UrutaUser userMock = mock(UrutaUser.class);
		
		when(userSession.getUserLogged()).thenReturn(userMock);
	}
	
	@Test
	public void successfullyDeletedEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();

		Epic epic = builderEpic
				.id(FAKE_REQUIREMENT_ID)
				.title("Example")
				.description("test unit")
				.buildEpic();
		
		shouldReturnTrueWhenRemoveById(epic);
		
		RequirementController controllerMock = 
				new RequirementController(result, requirementService, validator);
		
		controllerMock.delete(FAKE_REQUIREMENT_ID);
	}

	@Test
	public void validShow() throws UnsupportedEncodingException {
		ArtifactBuilder builder = new ArtifactBuilder();
		Generic genericRequirement = builder
				.id(FAKE_REQUIREMENT_ID)
				.title("Example")
				.description("test unit")
				.buildGeneric();
		
		mockShow(genericRequirement);
		
		RequirementController controller = 
				new RequirementController(result, requirementService, validator);
		
		controller.show(1, genericRequirement.getTitle());
	}
	
	private void mockShow(Generic genericRequirement) throws UnsupportedEncodingException {
		when(requirementService.getRequirement((int) genericRequirement.getId(), genericRequirement.getTitle()))
			.thenReturn(genericRequirement);
	}

	private void shouldReturnTrueWhenRemoveById(Artifact requirement) {
		when(requirementService.delete(requirement)).thenReturn(true);
	}
}