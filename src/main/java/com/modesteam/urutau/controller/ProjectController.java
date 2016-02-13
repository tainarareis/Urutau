package com.modesteam.urutau.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.model.system.FieldMessage;
import com.modesteam.urutau.model.system.MetodologyEnum;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.UserService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class ProjectController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	private static final int INVALID_METODOLOGY_CODE = -1;
	
	private final Result result;
	
	private final UserSession userSession;
	
	private final ProjectService projectService;

	private final UserService userService;
	
	private final Validator validator;

	/**
	 * @deprecated CDI eye only
	 */
	public ProjectController() {
		this(null,null,null,null, null);
	}
	
	@Inject
	public ProjectController(Result result, UserSession userSession, 
			ProjectService projectService, UserService userService, Validator validator) {
		this.result = result;
		this.userSession = userSession;
		this.userService = userService;
		this.projectService = projectService;
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
	 */
	@Post
	@Path("/project/create")
	public void create(Project project) throws UnsupportedEncodingException, CloneNotSupportedException {

		Project basicProject = null;

		logger.info("Project will be persisted have name " + project.getTitle());
		
		if(project.getTitle() == null) {
			logger.debug("The title is null!");
			
			validator.add(new SimpleMessage(FieldMessage.ERROR, "The title cant be empty"));
		} else if(!projectService.canBeUsed(project.getTitle())) {
			// projects can not have the same titles
			SimpleMessage error = new SimpleMessage(FieldMessage.ERROR, "Title already used");
			validator.add(error);
			
		} else {
			basicProject = retriveWithBasicInformation(project);

			logger.info("Trying save project...");			
			
			projectService.save(basicProject);
		}
		
		validator.onErrorRedirectTo(ProjectController.class).index();
		
		result.redirectTo(this).show((int) basicProject.getProjectID(), 
				basicProject.getTitle());
	}
	
	
	/**
	 * Method for delete only one project 
	 * @param id
	 */
	@Post
	public void deleteProject(long id) {
		
		logger.info("The project with id " +id+" was solicitated for exclusion");
		
		boolean projectExist = projectService.verifyProjectExistence(id);
		
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
	@Path("/{id}-{title}")
	public Project show(int id, String title) throws UnsupportedEncodingException {
		title = URLDecoder.decode(title, "utf-8");
		
		logger.info("Show project " + title);
		
		// Project id is a integer
		Project project = projectService.show(new Long(id), title);
		
		return project;
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
	 * Called only by ajax
	 */
	@Get
	public void reloadProjects() {
		User logged = userSession.getUserLogged();
		
		logged = userService.reloadFromDB(logged.getUserID());
		
		userSession.reload(logged);
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
	private Project retriveWithBasicInformation(final Project project) throws CloneNotSupportedException {
		Project basicProject = project.clone();
		
		basicProject.setDateOfCreation(getCurrentDate());
		
		User author = getCurrentUser();
		basicProject.setAuthor(author);
		basicProject.getMembers().add(author);

		int metodologyCode = selectMetodologyCode(project.getMetodology());
		basicProject.setMetodologyCode(metodologyCode);
		
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
	private User getCurrentUser() {
		User logged = userSession.getUserLogged();		
		logged = userService.reloadFromDB(logged.getUserID());

		return logged;
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
