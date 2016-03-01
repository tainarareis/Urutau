package com.modesteam.urutau.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.GenericDAO;
import com.modesteam.urutau.dao.ProjectDAO;
import com.modesteam.urutau.exception.DataBaseCorruptedException;
import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.model.Project;

public class ProjectService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
	private static final String TITLE_ATTRIBUTE_NAME = "title";
	private static final String ID_PARAMETER = "id";
	private static final int INVALID_ID = -1;
	private static final String TITLE_FIELD = "title";
	
	private ProjectDAO projectDAO;
	
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

	public List<Project> loadAll() {
		return projectDAO.loadAll();
	}
	
	/**
	 * Method to communicate with the DAO
	 * asking for the project exclusion from database.
	 * @param project
	 */
	public void excludeProject(Long id) {
		if( id != null ) {
			Project project = (Project) projectDAO.find(id);
			
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
	public boolean verifyProjectExistence(long id) {
		logger.info("Verifying the requirement existence in database.");
		
		Project project = null;
		
		try {
			project = projectDAO.get(ID_PARAMETER, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		 
		Long idOfProject = new Long(INVALID_ID);
		
		try {
			idOfProject = projectDAO.get(TITLE_FIELD, title).getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		boolean validURL = project.getId() == idOfProject;
		 
		if(!validURL) {
			project = null;
		} else {
			logger.debug("Valid url, should return project");
		}
		 
		return project; 
	}
	/**
	 * Load an project by id
	 * 
	 * @param id identifier of project
	 * @return existence project
	 * 
	 * @throws SystemBreakException 
	 */
	public Project getByID(Long id) {
		assert(id == null);
		
		Project loaded = null;
		
		logger.info("Search id" + id);
		
			try {
				if(verifyProjectExistence(id)){
					loaded = projectDAO.get("id", id);
				} else {
					logger.trace("Do not found any project");
				}
			} catch(NoResultException noResultException) {
				throw new SystemBreakException("Maybe this project do not exist!");
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			
		return loaded;
	}
	
	/**
	 * See if title can be used
	 * 
	 * @param projectTitle
	 * @return
	 */
	public boolean canBeUsed(final String projectTitle) {
		boolean valueNotUsed = false;
		
		try{
			if(projectDAO.get(TITLE_ATTRIBUTE_NAME, projectTitle) == null) {
				valueNotUsed = true;
			}
		} catch (NonUniqueResultException exception) {
			throw new DataBaseCorruptedException(this.getClass().getSimpleName() 
					+ " returns twice " + TITLE_ATTRIBUTE_NAME + " equals");
		} catch (Exception exception) {
			exception.printStackTrace();
		} 
		
		return valueNotUsed;
	}

	public Project load(Project project) {
		return projectDAO.find(project.getId());
	}
	
	/**
	 * 
	 * See {@link GenericDAO#update(Object)}
	 */
	public boolean update(Project project) {
		return projectDAO.update(project);
	}
}
