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
import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.dao.RequirementDAO;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.ProjectService;

public class RequirementCreatorTest {

	private final Logger logger = Logger.getLogger(RequirementCreator.class);
	
	private MockResult mockResult;
	private UserSession mockUserSession;
	private MockValidator mockValidator;
	private RequirementDAO mockDAO;
	private ProjectService mockProjectService;

	@Before
	public void setup() {
		// Catch all!
		logger.setLevel(Level.DEBUG);
		
		// Mocks supported by vraptor
		mockResult = new MockResult();
		mockValidator = new MockValidator();

		// System components
		mockDAO = EasyMock.createMock(RequirementDAO.class);
		mockUserSession = EasyMock.createMock(UserSession.class);
		mockProjectService = EasyMock.createMock(ProjectService.class);
		
		User userMock = EasyMock.createNiceMock(User.class);
		
		EasyMock.expect(mockUserSession.getUserLogged()).andReturn(userMock).anyTimes();
		EasyMock.replay(mockUserSession);
	}

	@Test
	public void createValidFeature() {
		ArtifactBuilder builderFeature = new ArtifactBuilder();

		Feature feature = builderFeature
				.id(1L)
				.title("Example")
				.description("test unit")
				.projectID(1L)
				.buildFeature();
 
		mockAdd(feature);
		PowerMock.replayAll();
		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, 
						mockUserSession, mockProjectService);
		controllerMock.createFeature(feature);
	}
	
	@Test
	public void createValidGeneric() {
		ArtifactBuilder builder = new ArtifactBuilder();

		Generic generic = builder
				.id(1L)
				.title("Example")
				.description("test unit")
				.projectID(1L)
				.buildGeneric();
 
		mockAdd(generic);
		PowerMock.replayAll();
		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, 
						mockUserSession, mockProjectService);
		controllerMock.createGeneric(generic);
	}

	@Test
	public void createValidEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();

		Epic epic = builderEpic
					.id(1L)
					.title("Example")
					.description("test unit")
					.projectID(1L)
					.buildEpic();

		mockAdd(epic);
		
		PowerMock.replayAll();
		
		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, 
						mockUserSession, mockProjectService);
		
		controllerMock.createEpic(epic);
	}

	@Test
	public void createValidStorie() {
		ArtifactBuilder builderStorie = new ArtifactBuilder();

		Storie storie = builderStorie
				.id(1L)
				.title("Example")
				.description("test unit")
				.projectID(1L)
				.buildStorie();

		mockAdd(storie);
		PowerMock.replayAll();

		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO,
						mockUserSession, mockProjectService);
		
		controllerMock.createUserStory(storie);
	}

	@Test
	public void createValidUseCase() {
		ArtifactBuilder builderUseCase = new ArtifactBuilder();

		UseCase useCase = builderUseCase
				.id(1L)
				.title("Example")
				.description("test unit")
				.projectID(1L)
				.buildUseCase();
		
		useCase.setFakeActors("Customer");

		mockAdd(useCase);
		PowerMock.replayAll();
		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, 
						mockUserSession, mockProjectService);
		
		controllerMock.createUseCase(useCase);
	}
	
	/**
	 * Verifies if a requirement with invalid actors can be saved
	 */
	@Test(expected=ValidationException.class)
	public void createInvalidUseCasePassingActor() {
		ArtifactBuilder builderUseCase = new ArtifactBuilder();

		UseCase useCase = builderUseCase
				.id(1L)
				.title("Example")
				.description("test unit")
				.projectID(1L)
				.buildUseCase();
		
		// Force error
		useCase.setFakeActors(null);

		mockAdd(useCase);
		PowerMock.replayAll();
		
		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, 
						mockUserSession, mockProjectService);
		
		controllerMock.createUseCase(useCase);
	}
	
	/**
	 * Verifies if a requirement with an invalid user can be created.
	 */
	@Test(expected=ValidationException.class)
	public void testWithInvalidUser() {
		ArtifactBuilder builder = new ArtifactBuilder();

		Generic generic = builder
				.id(1L)
				.title("Example")
				.description("test unit")
				.projectID(1L)
				.buildGeneric();

		mockAdd(generic);
		
		UserSession InvalidUserMock = new UserSession() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public User getUserLogged() {
				return null;
			}
		};
		
		PowerMock.replayAll();
		
		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, 
						InvalidUserMock, mockProjectService);

		controllerMock.createGeneric(generic);
	}
	
	/**
	 * Verifies if a requirement without an obligatory attribute can be created.
	 */
	@Test(expected=ValidationException.class)
	public void testWithoutTitle() {
		ArtifactBuilder builder = new ArtifactBuilder();

		Generic generic = builder
				.id(1L)
				.title(null)
				.description("test unit")
				.projectID(1L)
				.buildGeneric();

		mockAdd(generic);
				
		PowerMock.replayAll();
		
		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, 
						mockUserSession, mockProjectService);
		
		controllerMock.createGeneric(generic);
	}

	public void successfullyDeletedEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();

		Epic epic = builderEpic
				.id(1L)
				.title("Example")
				.description("test unit")
				.projectID(1L)
				.buildEpic();

		mockAdd(epic);
		PowerMock.replayAll();
		
//		mockRemove(1L);
//		RequirementController controllerMock = new RequirementController(
//				mockResult, mockUserSession, mockArtifactService, mockValidator);
//		controllerMock.excludeRequirement(1L);

	}

	/**
	 * Mocks DAO create method
	 * @param artifact
	 */
	private void mockAdd(Artifact artifact) {
		mockDAO.create(artifact);
		EasyMock.expectLastCall();
	}
}