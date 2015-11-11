package com.modesteam.urutau.controller;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;

import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.RequirementService;

public class RequirementsControllerTest {

	private MockResult mockResult;
	private UserManager mockUserSession;
	private MockValidator mockValidator;
	private RequirementService mockArtifactService;

	@Before
	public void setup() {
		// Mocks supported by vraptor
		mockResult = new MockResult();
		mockValidator = new MockValidator();

		// System components
		mockArtifactService = EasyMock.createMock(RequirementService.class);
		
		mockUserSession = EasyMock.createMock(UserManager.class);
		
		User userMock = EasyMock.createNiceMock(User.class);
		
		EasyMock.expect(mockUserSession.getUserLogged()).andReturn(userMock).anyTimes();
		EasyMock.replay(mockUserSession);
	}

	@Test
	public void createValidFeature() {
		ArtifactBuilder builderFeature = new ArtifactBuilder();

		Feature feature = builderFeature.id(1L).title("Example")
				.description("test unit").buildFeature();
 
		mockAdd(feature);
		PowerMock.replayAll();
		RequirementController controllerMock = new RequirementController(
				mockResult, mockUserSession, mockArtifactService, mockValidator);
		controllerMock.createFeature(feature);
	}
	
	@Test
	public void createValidGeneric() {
		ArtifactBuilder builder = new ArtifactBuilder();

		Generic generic = builder.id(1L).title("Example")
				.description("test unit").buildGeneric();
 
		mockAdd(generic);
		PowerMock.replayAll();
		RequirementController controllerMock = new RequirementController(
				mockResult, mockUserSession, mockArtifactService, mockValidator);
		controllerMock.createGeneric(generic);
	}

	@Test
	public void createValidEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();

		Epic epic = builderEpic
					.id(1L)
					.title("Example")
					.description("test unit")
					.buildEpic();

		mockAdd(epic);
		
		PowerMock.replayAll();
		
		RequirementController controllerMock = new RequirementController(
				mockResult, mockUserSession, mockArtifactService, mockValidator);

		controllerMock.createEpic(epic);
	}

	@Test
	public void createValidStorie() {
		ArtifactBuilder builderStorie = new ArtifactBuilder();

		Storie storie = builderStorie.id(1L).title("Example")
				.description("test unit").buildStorie();

		mockAdd(storie);
		PowerMock.replayAll();

		RequirementController controllerMock = new RequirementController(
				mockResult, mockUserSession, mockArtifactService, mockValidator);
		controllerMock.createUserStory(storie);
	}

	@Test
	public void createValidUseCase() {
		ArtifactBuilder builderUseCase = new ArtifactBuilder();

		UseCase useCase = builderUseCase.id(1L).title("Example")
				.description("test unit").buildUseCase();
		
		useCase.setFakeActors("Customer");

		mockAdd(useCase);
		PowerMock.replayAll();
		RequirementController controllerMock = new RequirementController(
				mockResult, mockUserSession, mockArtifactService, mockValidator);
		controllerMock.createUseCase(useCase);
	}
	
	@Test(expected=ValidationException.class)
	public void createInvalidUseCasePassingActor() {
		ArtifactBuilder builderUseCase = new ArtifactBuilder();

		UseCase useCase = builderUseCase
				.id(1L)
				.title("Example")
				.description("test unit")
				.buildUseCase();
		
		// Force error
		useCase.setFakeActors(null);

		mockAdd(useCase);
		PowerMock.replayAll();
		
		RequirementController controllerMock = new RequirementController(
				mockResult, mockUserSession, mockArtifactService, mockValidator);
		controllerMock.createUseCase(useCase);
	}

	@Test
	public void successfullyDeletedEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();

		Epic epic = builderEpic.id(1L).title("Example").description("test unit")
				.buildEpic();

		mockAdd(epic);
		PowerMock.replayAll();
		
		mockRemove(1L);
		RequirementController controllerMock = new RequirementController(
				mockResult, mockUserSession, mockArtifactService, mockValidator);
		controllerMock.excludeRequirement(1L);

	}

	private void mockAdd(Artifact artifact) {
		mockArtifactService.save(artifact);
		EasyMock.expectLastCall();
	}


	private void mockRemove(Long id) {
		mockArtifactService.excludeRequirement(id);
		EasyMock.expectLastCall();
	}
}