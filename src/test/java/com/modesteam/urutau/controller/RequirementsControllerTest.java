package com.modesteam.urutau.controller;

import java.util.Calendar;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.service.RequirementService;
import com.modesteam.urutau.controller.RequirementController;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;

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
				
		// System's components
		mockArtifactService = EasyMock.createMock(RequirementService.class);
		mockUserSession = EasyMock.createMock(UserManager.class);
		
	}
	
	@Test
	public void registerValidRequirement() {
		
	}
	
	@Test
	public void successfullyDeletedRequirement() {
		
		//Instantiation of an artifact
		ArtifactBuilder artifactBuilder = new ArtifactBuilder();
		
		Calendar c = Calendar.getInstance();
		c.set(2013, Calendar.FEBRUARY, 28);
		
		Artifact artifact = artifactBuilder
							.dateOfCreation(c)
							.id(123456)
							.title("O sistema deve permitir a avaliação de feedbacks ")
							.description("O professor pode avaliar os feedbacks a cerca da matéria")
							.build();
		
		
		RequirementController requirementController = new RequirementController
				(mockResult, mockUserSession, mockArtifactService, mockValidator);
		
		mockAdd(artifact);
		
		requirementController.excludeRequirement(artifact);
	}
	
	
	private void mockAdd(Artifact artifact){
		mockArtifactService.save(artifact);
		EasyMock.expectLastCall();
	}
	
}