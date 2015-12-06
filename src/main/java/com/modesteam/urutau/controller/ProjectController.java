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

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.Metodology;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.model.system.FieldMessage;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.UserService;

/**
 * This class is responsible to manager simple operations of projects!
 * The systems operations are received by the path /requirement followed
 * by the operation defined path.
 */

@Controller
public class ProjectController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	private final Result result;
	
	private final UserSession userSession;
	
	private final ProjectService projectService;

	private final UserService userService;
	
	private final Validator validator;

	
	/*
	 * CDI needs this
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
	 * @param project
	 */
	@Post
	public void createProject(Project project) {
		
		logger.info("Project will be persisted: " + project.getTitle());
		
		if(project.getTitle() == null) {
			
			logger.debug("The title is null!");
			
			validator.add(new SimpleMessage(FieldMessage.ERROR.toString(),
					"The title cant be empty!"));
		} else {
			insertBasicInformation(project);
			
			logger.info("Trying save project...");			
			
			projectService.save(project);
		}
		
		validator.onErrorRedirectTo(ProjectController.class).index();
		result.nothing();
	}
	
	/**
	 * Load an list of metodology to show in an select field.
	 * Result will include {@link List} of metodology's names
	 */
	@Get
	public void createProject() {
		List<String> metodologies = new ArrayList<String>();
		
		for(Metodology metodology : Metodology.values()){
			metodologies.add(metodology.getName());
		}
		
		result.include("metodologies", metodologies); 
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
	@Path("/{id}/{title}")
	public Project show(int id, String title) throws UnsupportedEncodingException {
		title = URLDecoder.decode(title, "utf-8");
		
		logger.info("Show project " + title);
		
		Project project = projectService.detail(id);
		
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
	
	@Post
	public void detailProject() {
		
	}
	
	@View
	public void showCreationResult() {
		
	}
	
	/**
	 * Load only if user is logged
	 * 
	 */
	@Get
	@Path(value = "/", priority=Path.HIGH)
	public void index() {
		List<Project> projects = new ArrayList<Project>();
		
		for(Project project : userSession.getUserLogged().getProjects()) {
			projects.add(project);
		}
		
		logger.info("Have " + projects.size());
		
		result.include("projects", projects);
	}
	

	/**
	 * Setting basic fields programmatically
	 *  
	 * @param project soon persisted
	 */
	private void insertBasicInformation(Project project) {
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		
		project.setDateOfCreation(calendar);
		
		User logged = userSession.getUserLogged();
		
		logged = userService.reloadFromDB(logged.getUserID());

		project.setAuthor(logged);
		
		// Owner is member too
		project.getMembers().add(logged);
	}
	
	@View
	public void home(){
		
	}
}
