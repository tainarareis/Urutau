package com.modesteam.urutau.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.annotation.Updater;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.system.FieldMessage;
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.model.system.MetodologyEnum;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.UserService;

@Controller
public class ProjectController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	private static final int INVALID_METODOLOGY_CODE = -1;

	private final Result result;
	private final UserSession userSession;
	private final ProjectService projectService;
	private final UserService userService;
	private final KanbanService kanbanService;
	private final Validator validator;
	@Inject
	@Updater
	private Event<UrutaUser> reloadEvent;

	/**
	 * @deprecated CDI eye only
	 */
	public ProjectController() {
		this(null,null,null,null, null, null);
	}
	
	@Inject
	public ProjectController(Result result, UserSession userSession, 
			ProjectService projectService, UserService userService, 
			KanbanService kanbanService, Validator validator) {
		this.result = result;
		this.userSession = userSession;
		this.userService = userService;
		this.projectService = projectService;
		this.kanbanService = kanbanService;
		this.validator = validator;
	}
	
	/**
	 *  Method for create one project with what gonna have
	 *  requirements inside
	 *  
	 * @param project to be persisted
	 * 
	 * @throws UnsupportedEncodingException when show is requested
	 * @throws CloneNotSupportedException 
	 * 
	 * TODO treat this exceptions
	 */
	@Post
	public void create(final @Valid Project project) throws 
		UnsupportedEncodingException, CloneNotSupportedException {
		
		validator.addIf(!projectService.canBeUsed(project.getTitle()), 
				new I18nMessage(FieldMessage.PROJECT_CREATE, "title_already_in_used"));
		
		if(validator.hasErrors()) {
			validator.onErrorRedirectTo(ProjectController.class).index();
		} else {
			Project basicProject = retriveWithBasicInformation(project);
			
			logger.info("Trying create a new project...");			
			
			projectService.save(basicProject);

			// TODO Observe this
			userSession.getUserLogged().addProject(basicProject);
			
			result.redirectTo(this).show(basicProject);
		}
	}
	
	
	/**
	 * Delete only one project 
	 * 
	 * @param id primary key of Project
	 */
	@Post
	public void delete(final Long id) {
		logger.info("The project with id " + id +" was solicitated for exclusion");
		
		boolean projectExist = projectService.exists(id);
		
		if(!projectExist) {
			logger.info("The project already deleted or inexistent!");
			validator.add(new SimpleMessage(FieldMessage.ERROR.toString(), "Project already excluded!"));	
		} else {
			
			logger.info("The project will be deleted");
			projectService.excludeProject(id);
		}
			validator.onErrorRedirectTo(ProjectController.class).index();
	}

	/**
	 * Open edit page setting project request
	 *  
	 * @param project to fill with modifications
	 */
	@Get("/{project.title}/edit")
	@View
	public void edit(final Project project) {
		Project requestedProject = null;

		try {
			final String title = project.getTitle();
			requestedProject = projectService.getByTitle(title);
		} catch (Exception exception) {
			result.redirectTo(ApplicationController.class).invalidRequest();
		}

		result.include(requestedProject);
	}

	/**
	 * Update called by form
	 * 
	 * @param project with possible modifications
	 */
	@Put("/{project.id}/setting")
	public void update(Project project) {
		// It is needed when project title has change
		Project currentProject = projectService.getByID(project.getId());
		validator.onErrorRedirectTo(this).edit(currentProject);

		projectService.update(project);
		
		result.redirectTo(this).edit(project);
		
		reloadEvent.fire(userSession.getUserLogged());
	}

	/**
	 * Show the projects that has a certain id and title 
	 * 
	 * @param id Unique attribute
	 * @param title various projects can have same title
	 * 
	 * @return {@link Project} from database
	 * 
	 * @throws UnsupportedEncodingException invalid characters or decodes fails
	 */
	@Get
	@Path("/{project.id}-{project.title}")
	public Project show(Project project) throws UnsupportedEncodingException {
		String titleDecoded = URLDecoder.decode(project.getTitle(), StandardCharsets.UTF_8.name());
		
		logger.info("Show project " + project.getTitle());
		Project targetProject  = projectService.show(project.getId(), titleDecoded);
		
		return targetProject;
	}
	
	/**
	 * Shortcut to {@link ProjectController#show(Project))}, 
	 * available only programmatically
	 * 
	 * @param projectID key to get from database
	 * @throws UnsupportedEncodingException throws by show(Project)
	 */
	public void show(Long projectID) throws UnsupportedEncodingException {
		Project requestProject = projectService.getByID(projectID);

		result.redirectTo(this.getClass()).show(requestProject);
	}
	
	/**
	 * Gives all the existent projects in database.
	 * @return projects
	 */
	@Get
	@Path("/project/showAll")
	public List<? extends Project> showAll() {		
		logger.info("Function showAll");
		
		List<? extends Project> projects = projectService.loadAll();
		
		result.include("projects", projects);
		
		return projects;
	}
		
	
	/**
	 * Load an list of metodology to show in an select field.
	 * Result will include {@link List} of metodology's names.
	 * Restricted to users, logic of flux controll is into
	 * {@link IndexController}
	 */
	@Get
	@Path(value = "/", priority=Path.HIGH)
	public void index() {
		// Loads enum with metodology names to populate 
		loadProjectTypes();
		
		List<Project> projects = new ArrayList<Project>();
		
		for(Project project : userSession.getUserLogged().getProjects()) {
			projects.add(project);
		}
		
		logger.info("Have " + projects.size() +" projects");
		
		result.include("projects", projects);
	}
	

	/**
	 * Called by ajax
	 */
	@Get
	public void reloadProjects() {		
		userSession.reload();
	}
	
	/**
	 * Load enum {@link MetodologyEnum} in string to field select
	 * into project create
	 * 
	 */
	private void loadProjectTypes() {
		List<String> metodologies = new ArrayList<String>();
		
		for(MetodologyEnum metodology : MetodologyEnum.values()) {
			metodologies.add(metodology.toString());
		}
		
		result.include("metodologies", metodologies);
	}

	/**
	 * Setting basic fields programmatically
	 *  
	 * @param project soon persisted
	 * @return 
	 * @throws CloneNotSupportedException 
	 */
	private Project retriveWithBasicInformation(final Project project) 
			throws CloneNotSupportedException {
		Project basicProject = project.clone();
		
		basicProject.setDateOfCreation(getCurrentDate());
		
		UrutaUser author = getCurrentUser();
		basicProject.setAuthor(author);
		basicProject.getMembers().add(author);

		int metodologyCode = selectMetodologyCode(project.getMetodology());
		basicProject.setMetodologyCode(metodologyCode);
		
		List<Layer> defaultLayers = kanbanService.getDefaultLayers();
		basicProject.setLayers(defaultLayers);
		
		return basicProject;
	}
	
	/**
	 * From metodology name, set metodology code
	 *  
	 * @param project to be persisted
	 */
	private int selectMetodologyCode(String name) {
		logger.info("Metodology choose was " + name);

		int metodologyCode = INVALID_METODOLOGY_CODE;

		for(MetodologyEnum metodology : MetodologyEnum.values()) {
			if(metodology.refersTo(name)) {
				metodologyCode = metodology.getId();
				logger.debug("Metodology choose have code " + metodologyCode);
				// Stop loop
				break;
			} else {
				// Keep searching
			}
		}
		
		return metodologyCode;
	}

	/**
	 * Set current user logged like author
	 * 
	 * @param project to be created
	 * @return 
	 */
	private UrutaUser getCurrentUser() {
		UrutaUser logged = userSession.getUserLogged();
		return userService.getUserByID(logged.getUserID());
	}

	/**
	 * Format date with current value
	 * 
	 * @param project to be persisted
	 * @return 
	 */
	private Calendar getCurrentDate() {
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		
		return calendar;
	}
}
