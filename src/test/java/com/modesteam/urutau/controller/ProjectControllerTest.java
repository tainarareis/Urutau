package com.modesteam.urutau.controller;

import static org.mockito.Mockito.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.builder.ProjectBuilder;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.model.system.MetodologyEnum;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.UserService;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;

public class ProjectControllerTest {
	
	private final Logger logger = Logger.getLogger(ProjectController.class);
	
	private MockResult result;
	private UserSession userSession;
	private MockValidator validator;
	private ProjectService projectService;
	private KanbanService kanbanService;
	private UserService userService;
	
	
	@Before
	public void setup() {
		// Catch all!
		logger.setLevel(Level.DEBUG);
		
		// Mocks supported by vraptor
		result = new MockResult();
		validator = new MockValidator();

		// System components
		projectService = mock(ProjectService.class);
		
		kanbanService = mock(KanbanService.class);
		
		userService = mock(UserService.class);
		
		userSession = mock(UserSession.class);
		
		UrutaUser userLogged = mock(UrutaUser.class);
		
		when(userSession.getUserLogged()).thenReturn(userLogged);
	}
	
	@Test
	public void createValidProject() throws UnsupportedEncodingException, CloneNotSupportedException {
		ProjectBuilder projectBuilder = new ProjectBuilder();

		Project project = projectBuilder
				.id(1L)
				.title("Example Valid")
				.description("test unit")
				.metodology(MetodologyEnum.GENERIC.toString())
				.builProject();
		
		mockCanBeUse(project.getTitle());
		mockGetDefaultLayers();
		mockSave(project);
		
		ProjectController controllerMock = 
				new ProjectController(result, userSession, projectService, 
						userService, kanbanService, validator);
		
		controllerMock.create(project);
	}

	@Test(expected=ValidationException.class)
	public void createInvalidProject() throws UnsupportedEncodingException, CloneNotSupportedException {
		
		ProjectBuilder projectBuilder = new ProjectBuilder();

		Project project = projectBuilder
				.id(1L)
				.title(null)
				.description("test unit")
				.builProject();
 
		mockSave(project);
		
		ProjectController controllerMock = 
				new ProjectController(result, userSession, projectService, 
						userService, kanbanService, validator);
		
		controllerMock.create(project);
	}

	@Test
	public void deleteValidProject(){
		mockExistence(1L, true);
		mockRemove(1L);		
		
		ProjectController controllerMock = 
				new ProjectController(result, userSession, projectService, 
						userService, kanbanService, validator);
		
		controllerMock.delete(1L);
	}
	
	@Test(expected=ValidationException.class)
	public void deleteInvalidProject(){
		
		mockExistence(1L, false);
		mockRemove(1L);
		
		ProjectController controllerMock = 
				new ProjectController(result, userSession, projectService, 
						userService, kanbanService, validator);

		controllerMock.delete(1L);
	}
	
	private void mockSave(Project project) {
		doNothing().when(projectService).save(project);
	}
	
	private void mockRemove(Long id) {
		doNothing().when(projectService).excludeProject(id);
	}
	
	private void mockExistence(Long id, boolean returnValue) {
		when(projectService.verifyProjectExistence(id)).thenReturn(returnValue);
	}

	private void mockCanBeUse(String title) {
		when(projectService.canBeUsed(title)).thenReturn(true);
	}
	
	private void mockGetDefaultLayers() {
		List<Layer> layers = new ArrayList<Layer>();
		layers.add(mock(Layer.class));
		
		when(kanbanService.getDefaultLayers()).thenReturn(layers);
	}
	
}
