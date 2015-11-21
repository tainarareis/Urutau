package com.modesteam.urutau.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.ProjectService;

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
	
	private final Validator validator;
	
	
	
	@Inject
	public ProjectController(Result result, UserSession userSession, 
			ProjectService projectService, Validator validator) {
		this.result = result;
		this.userSession = userSession;
		this.projectService = projectService;
		this.validator = validator; 
	}
	
	@Post
	public void createProject(Project project){
		logger.info("Project will be persisted: " + project.getTitle());
		
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		
		project.setDateOfCreation(calendar);
		
		User logged = userSession.getUserLogged();
		project.setAuthor(logged);
		
		logger.info("Requesting for project service");
		projectService.save(project);
		
		//result.redirectTo(this).showCreationResult(project.getId());
		
	}
	
	@Post
	public void deleteProject(){
		
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
	public Project show(int id, String title) throws UnsupportedEncodingException{
		title = URLDecoder.decode(title, "utf-8");
		
		logger.info("Show project " + title);
		
		Project project = projectService.detail(id);
		
		return project;
	}
	
	@Post
	public void detailProject(){
		
	}

}
