package com.modesteam.urutau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;
import br.com.caelum.vraptor.validator.Validator;

public class KanbanControllerTest {
	
	private static final Long STUB_LONG_NUMBER = 1L;
	private static final Long VALID_PROJECT_ID = 1L;
	
	private Result mockResult;
	private Validator mockValidator;
	private KanbanService mockKanbanService;
	private ProjectService mockProjectService;
	private RequirementService mockRequirementService;
	
	@Before
	public void setUp(){		
		// Mocks supported by vraptor
		mockResult = new MockResult();
		mockValidator = new MockValidator();
	
		// System components
		mockRequirementService = mock(RequirementService.class);
		mockKanbanService = mock(KanbanService.class);
		mockProjectService = mock(ProjectService.class);
	}
	
	@Test
	public void testLoadValid() throws Exception {
		Project project = new Project();
		project.setId(VALID_PROJECT_ID);
		
		whenFind(project);
		
		KanbanController controller = new KanbanController(mockResult, mockValidator, 
				mockKanbanService, mockProjectService, mockRequirementService);
		
		controller.load(project);
	}
	
	@Test
	public void testValidMove() throws Exception {
		Artifact requirement = createAnMockRequirement();
		
		whenGetRequirementByID(STUB_LONG_NUMBER, requirement);
		whenGetLayerByID(STUB_LONG_NUMBER);
		
		doNothingWhenRequirementServiceUpdate(requirement);
		
		KanbanController controller = new KanbanController(mockResult, mockValidator, 
				mockKanbanService, mockProjectService, mockRequirementService);
		
		controller.move(STUB_LONG_NUMBER, STUB_LONG_NUMBER);
	}
	
	@Test
	public void testCreateValidLayer() throws Exception {
		Layer mockLayer = createAnValidMockLayer();
		
		Project mockProject = mock(Project.class);
		mockProject.add(mockLayer);
		
		mockProjectToAddNewLayer(mockProject, mockLayer);
		
		shouldReturnWhenCreateLayer(mockLayer);
		doNothingWhenUpdateProject(mockProject);
		
		KanbanController controller = new KanbanController(mockResult, mockValidator, 
				mockKanbanService, mockProjectService, mockRequirementService);
		
		controller.createLayer(VALID_PROJECT_ID, mockLayer);
	}
	
	@Test(expected=ValidationException.class)
	public void testCreateInvalidLayer() {
	    Layer mockLayer = createAnValidMockLayer();

		Project mockProject = mock(Project.class);
		mockProject.add(mockLayer);

		mockProjectToAddNewLayer(mockProject, mockLayer);

		shouldThrowExceptionWhenCreate(mockLayer);

		doNothingWhenUpdateProject(mockProject);

		KanbanController controller = new KanbanController(mockResult, mockValidator, 
				mockKanbanService, mockProjectService, mockRequirementService);

		controller.createLayer(VALID_PROJECT_ID, mockLayer);
	}

	private void shouldThrowExceptionWhenCreate(Layer mockLayer) {
		doThrow(IllegalArgumentException.class).when(mockKanbanService).create(mockLayer);
	}

	private void shouldReturnWhenCreateLayer(Layer mockLayer) {
		doNothing().when(mockKanbanService).create(mockLayer);
	}

	private Layer createAnValidMockLayer() {
		Layer mockLayer = mock(Layer.class);
		return mockLayer;
	}

	private Artifact createAnMockRequirement() {
		Artifact requirement = mock(Artifact.class);

		Project mockProject = mock(Project.class);
		
		when(mockProject.getId()).thenReturn(STUB_LONG_NUMBER);
		
		when(requirement.getProject()).thenReturn(mock(Project.class));
		
		when(requirement.getProject()).thenReturn(mockProject);
		
		return requirement;
	}

	private void doNothingWhenRequirementServiceUpdate(Artifact artifact) {
		when(mockRequirementService.update(artifact)).thenReturn(true);
	}

	private void whenGetLayerByID(Long id) {
		when(mockKanbanService.getLayerByID(id)).thenReturn(mock(Layer.class));
	}

	private void whenGetRequirementByID(Long id, Artifact mockReturned) {
		when(mockRequirementService.getByID(id)).thenReturn(mockReturned);
	}

	private void whenFind(Project project) {
		when(mockProjectService.find(project.getId())).thenReturn(mock(Project.class));
	}

	private void mockProjectToAddNewLayer(Project project, Layer layer) {		
		when(mockProjectService.find(VALID_PROJECT_ID)).thenReturn(project);
	}
	
	private void doNothingWhenUpdateProject(Project project) {
		doNothing().when(mockProjectService).update(project);
	}
}
