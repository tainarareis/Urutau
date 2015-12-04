package com.modesteam.urutau.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

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
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;

public class RequirementEditorTest {
	
	private final Logger logger = Logger.getLogger(ProjectController.class);
	
	private MockResult mockResult;
	private UserSession mockUserSession;
	private MockValidator mockValidator;
	private RequirementService mockService;
	private ArtifactBuilder artifactBuilder = new ArtifactBuilder();
	private RequirementDAO mockDAO;
	
	@Before
	public void setUp() {
		// Catch all!
		logger.setLevel(Level.DEBUG);
		
		// Mocks supported by vraptor
		mockResult = new MockResult();
		mockValidator = new MockValidator();

		// System components
		mockService = EasyMock.createMock(RequirementService.class);
		
		mockUserSession = EasyMock.createMock(UserSession.class);
		
		mockDAO = EasyMock.createMock(RequirementDAO.class);
		
		User userMock = EasyMock.createNiceMock(User.class);
		
		EasyMock.expect(mockUserSession.getUserLogged()).andReturn(userMock).anyTimes();
		EasyMock.replay(mockUserSession);
		
	}
	
	/**
	 * Verifies a valid epic edition
	 */
	@Test
	public void testValidEpicEdition() {		
		
		//Creating an hypothetical epic
		Epic epic = artifactBuilder
							.id(10L)
							.title("Valid Title")
							.description("Valid Description")
							.buildEpic();
		
		mockAdd(epic);
		
		//Editing the epic
		epic.setId(15L);
		epic.setTitle("New Title");
		epic.setDescription("New Description");
		 
		mockEdit(epic);
		
		//Change all mocks object and classes maintained by PowerMock to replay mode.
		PowerMock.replayAll();
		
		RequirementEditor controller = new RequirementEditor(mockResult, 
				mockValidator, mockUserSession, mockService);
		
		controller.modifyRequirement(epic);
		
	}

	
	/**
	 * Verifies a valid generic requirement edition.
	 */
	@Test
	public void testValidGenericEdition() {		
		
		//Creating an hypothetical generic requirement
		Generic generic = artifactBuilder
				.id(10L)
				.title("Valid Title")
				.description("Valid Description")
				.buildGeneric();
		
		mockAdd(generic);
		
		//Editing the generic requirement
		generic.setId(15L);
		generic.setTitle("New Title");
		generic.setDescription("New Description");
		 
		mockEdit(generic);
		
		//Change all mocks object and classes maintained by PowerMock to replay mode.
		PowerMock.replayAll();
		
		RequirementEditor controller = new RequirementEditor(mockResult, 
				mockValidator, mockUserSession, mockService);
		
		controller.modifyRequirement(generic);
		
	}
	
	/**
	 * Verifies a valid feature edition.
	 */
	@Test
	public void testValidFeatureEdition() {		
		
		//Creating an hypothetical feature
		Feature feature = artifactBuilder
				.id(15L)
				.title("Valid Title")
				.description("Valid Description")
				.buildFeature();
		
		mockAdd(feature);
		
		//Editing the epic
		feature.setId(30L);
		feature.setTitle("New Title");
		feature.setDescription("New Description");
		 
		mockEdit(feature);
		
		//Change all mocks object and classes maintained by PowerMock to replay mode.
		PowerMock.replayAll();
		
		RequirementEditor controller = new RequirementEditor(mockResult, 
				mockValidator, mockUserSession, mockService);
		
		controller.modifyRequirement(feature);
		
	}
	
	
	/**
	 * Verifies a valid use case edition.
	 */
	@Test
	public void testValidUseCaseEdition() {		
		
		//Creating an hypothetical use case
		UseCase useCase = artifactBuilder
				.id(100L)
				.title("Valid Title")
				.description("Valid Description")
				.buildUseCase();
		
		mockAdd(useCase);
		
		//Editing the use case
		useCase.setId(150L);
		useCase.setTitle("New Title");
		useCase.setDescription("New Description");
		 
		mockEdit(useCase);
		
		//Change all mocks object and classes maintained by PowerMock to replay mode.
		PowerMock.replayAll();
		
		RequirementEditor controller = new RequirementEditor(mockResult, 
				mockValidator, mockUserSession, mockService);
		
		controller.modifyRequirement(useCase);
		
	}
	
	/**
	 * Verifies a valid storie edition.
	 */
	@Test
	public void testValidStorieEdition() {		
		
		//Creating an hypothetical storie
		Storie storie = artifactBuilder
				.id(50L)
				.title("Valid Title")
				.description("Valid Description")
				.buildStorie();
		
		mockAdd(storie);
		
		//Editing the storie
		storie.setId(120L);
		storie.setTitle("New Title");
		storie.setDescription("New Description");
		 
		mockEdit(storie);
		
		//Change all mocks object and classes maintained by PowerMock to replay mode.
		PowerMock.replayAll();
		
		RequirementEditor controller = new RequirementEditor(mockResult, 
				mockValidator, mockUserSession, mockService);
		
		controller.modifyRequirement(storie);
		
	}
	
	
	/**
	 * Mocks DAO create method
	 * @param artifact
	 */
	private void mockAdd(Artifact artifact) {
		mockDAO.create(artifact);
		EasyMock.expectLastCall();
	}
	
	/**
	 * Mocks DAO update method
	 * @param requirement
	 */
	private void mockEdit(Artifact requirement) {
		mockDAO.update(requirement);
		EasyMock.expectLastCall();
	}
}
