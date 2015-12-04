package com.modesteam.urutau.controller;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;

import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.service.RequirementService;

public class RequirementsControllerTest {
	
	private MockResult mockResult;
	private MockValidator mockValidator;
	private RequirementService mockArtifactService;
	
	
	@Before
	public void setup() {
		// Mocks supported by vraptor
		mockResult = new MockResult();
		mockValidator = new MockValidator();
  		
		// System's components
		mockArtifactService = EasyMock.createMock(RequirementService.class);		
	}

	@Test
	public void successfullyDeletedEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();
		
		Epic epic = builderEpic
					.id(1L)
					.title("exemple")
					.description("blabla")
					.buildEpic();

		mockAdd(epic);
		PowerMock.replayAll();
		mockRemove(1L);
		RequirementController controllerMock = new RequirementController(mockResult, mockArtifactService,mockValidator);
		controllerMock.excludeRequirement(1L);		
	}
	
	private void mockAdd(Artifact artifact){
		mockArtifactService.save(artifact);
		EasyMock.expectLastCall();
	}
	
	private void mockRemove(Long id){
		mockArtifactService.excludeRequirement(id);
		EasyMock.expectLastCall();
	}
}