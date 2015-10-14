package com.modesteam.urutau.controller;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;

import com.modesteam.urutau.UserManager;
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
				
		// System's components
		mockArtifactService = EasyMock.createMock(RequirementService.class);
		mockUserSession = EasyMock.createMock(UserManager.class);
		
	}
	
	@Test
	public void registerValidRequirement() {
		
	}
	
	@Test
	public void successfullyDeletedRequirement() {
		
	}
}