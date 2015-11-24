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
import com.modesteam.urutau.exception.ActionException;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.User;

public class RequirementCreatorTest {

	private final Logger logger = Logger.getLogger(RequirementCreator.class);
	
	private MockResult mockResult;
	private UserSession mockUserSession;
	private MockValidator mockValidator;
	private RequirementDAO mockDAO;

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
		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, mockUserSession);
		controllerMock.createFeature(feature);
	}
	
	@Test
	public void createValidGeneric() {
		ArtifactBuilder builder = new ArtifactBuilder();

		Generic generic = builder.id(1L).title("Example")
				.description("test unit").buildGeneric();
 
		mockAdd(generic);
		PowerMock.replayAll();
		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, mockUserSession);
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
		
		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, mockUserSession);
		
		controllerMock.createEpic(epic);
	}

	@Test
	public void createValidStorie() {
		ArtifactBuilder builderStorie = new ArtifactBuilder();

		Storie storie = builderStorie.id(1L).title("Example")
				.description("test unit").buildStorie();

		mockAdd(storie);
		PowerMock.replayAll();

		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, mockUserSession);
		
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
		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, mockUserSession);
		
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
		
		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, mockUserSession);
		
		controllerMock.createUseCase(useCase);
	}
	
	@Test(expected=ActionException.class)
	public void testWithInvalidUser() {
		ArtifactBuilder builder = new ArtifactBuilder();

		Generic generic = builder.id(1L).title("Example")
				.description("test unit").buildGeneric();

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
				new RequirementCreator(mockResult, mockValidator, mockDAO, InvalidUserMock);
		controllerMock.createGeneric(generic);
	}
	
	@Test(expected=ValidationException.class)
	public void testWithoutTitle() {
		ArtifactBuilder builder = new ArtifactBuilder();

		Generic generic = builder
				.id(1L)
				.title(null)
				.description("test unit")
				.buildGeneric();

		mockAdd(generic);
				
		PowerMock.replayAll();
		
		RequirementCreator controllerMock = 
				new RequirementCreator(mockResult, mockValidator, mockDAO, mockUserSession);
		controllerMock.createGeneric(generic);
	}

	public void successfullyDeletedEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();

		Epic epic = builderEpic.id(1L).title("Example").description("test unit")
				.buildEpic();

		mockAdd(epic);
		PowerMock.replayAll();
		
//		mockRemove(1L);
//		RequirementController controllerMock = new RequirementController(
//				mockResult, mockUserSession, mockArtifactService, mockValidator);
//		controllerMock.excludeRequirement(1L);

	}

	private void mockAdd(Artifact artifact) {
		mockDAO.create(artifact);
		EasyMock.expectLastCall();
	}
}