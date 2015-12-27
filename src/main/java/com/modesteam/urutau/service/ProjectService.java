package com.modesteam.urutau.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.ProjectDAO;
import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.model.Project;

public class ProjectService {
	private ProjectDAO projectDAO;
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
	
	public ProjectService() {
		this(null);
		
	}

	@Inject
	public ProjectService(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
	}
	
	public void save(Project project) {
		projectDAO.create(project);
	}

	public List<? extends Project> loadAll() {
		return projectDAO.loadAllProjects();
	}
	
	/**
	 * Method to communicate with the DAO
	 * asking for the project exclusion from database.
	 * @param project
	 */
	public void excludeProject(Long projectId) {
		if( projectId != null ) {
			Project project = (Project) projectDAO.find(projectId);
			
			if(project != null) {
				projectDAO.destroy(project);
			} else {
				throw new SystemBreakException("This project not exist");
			}
		} else {
			logger.info("RequirementService cant find requirementID");
		}
	}
	
	/**
	 * Verifies the existence of a project by its id
	 * 
	 * @param Id of an project
	 * @return true if the project exists
	 * 
	 */
	public boolean verifyProjectExistence(long Id) {
		logger.info("Verifying the requirement existence in database.");
		
		Project project = projectDAO.get("id", Id);
		
		boolean projectExists;
		
		if (project == null) {
			logger.info("The project is null");
			projectExists = false;
		} else {
			logger.info("The project isn't null");
			projectExists = true;
		}
		
		return projectExists;
		
	}

	public Project show(Long id, String title) {
		 Project project = projectDAO.find(id);
		 Long idFromOperationGetTitle = projectDAO.get("title", title).getProjectID();
		 boolean validURL = project.getProjectID() == idFromOperationGetTitle;
		 
		 if(!validURL) {
			project = null;
		 } else {
			 logger.debug("Valid url, returning project");
		 }
		 
		 return project;
		 
	}
	/**
	 * Load an project by id
	 * 
	 * @param projectID identifier of project
	 * @return existence project
	 * 
	 * @throws SystemBreakException 
	 */
	public Project load(Long projectID) {
		assert(projectID == null);
		
		Project loaded = null;
		
		logger.info("Search projectID" + projectID);
		
			try {
				if(verifyProjectExistence(projectID)){
					loaded = projectDAO.get("projectID", projectID);
				} else {
					logger.trace("Do not found any project");
				}
			} catch(NoResultException noResultException) {
				throw new SystemBreakException("Maybe this project do not exist!");
			}
			
		return loaded;
	}
}
