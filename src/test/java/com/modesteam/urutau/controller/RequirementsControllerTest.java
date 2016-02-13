package com.modesteam.urutau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;

public class RequirementsControllerTest {
	
	private MockResult mockResult;
	private MockValidator mockValidator;
	private RequirementService requirementService;
	
	
	@Before
	public void setup() {
		// Mocks supported by vraptor
		mockResult = new MockResult();
		mockValidator = new MockValidator();
  		
		// System's components
		requirementService = mock(RequirementService.class);		
	}

	@Test
	public void successfullyDeletedEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();
		
		Epic epic = builderEpic
					.id(1L)
					.title("exemple")
					.description("blabla")
					.buildEpic();
		
		doNothingWhenRemoveBy(epic.getId());

		RequirementController controllerMock = new RequirementController(mockResult, requirementService, mockValidator);
	
		controllerMock.delete(1L);		
	}
	
	private void doNothingWhenRemoveBy(long id) {
		doNothing().when(requirementService).delete(id);
	}
}