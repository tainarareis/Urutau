package com.modesteam.urutau.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.ProjectDAO;
import com.modesteam.urutau.exception.DataBaseCorruptedException;
import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.model.Project;

public class ProjectService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
	private static final String TITLE_COLUMN = "title";
	private static final String ID_COLUMN = "id";
	private static final int INVALID_ID = -1;
	
	private final ProjectDAO projectDAO;
	
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
			project = projectDAO.get(ID_COLUMN, id);
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
			idOfProject = projectDAO.get(TITLE_COLUMN, title).getId();
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
			if(projectDAO.get(TITLE_COLUMN, projectTitle) == null) {
				valueNotUsed = true;
			}
		} catch (NonUniqueResultException exception) {
			throw new DataBaseCorruptedException(this.getClass().getSimpleName() 
					+ " returns twice " + TITLE_COLUMN + " equals");
		} catch (Exception exception) {
			exception.printStackTrace();
		} 
		
		return valueNotUsed;
	}

	public Project load(Project project) {
		return projectDAO.find(project.getId());
	}
	
	/**
	 * Update attributes passed by a detached object
	 * 
	 * @param detachedProject created in a page form
	 */
	public boolean update(Project detachedProject) {
		Project managedProject = projectDAO.find(detachedProject.getId());
		
		final String description = detachedProject.getDescription();
		final String title = detachedProject.getTitle();
		final Integer metodology = detachedProject.getMetodologyCode();
		final boolean isPublic = detachedProject.isPublic();

		if (!description.equals(managedProject.getDescription())) {
			logger.trace("update description");
			managedProject.setDescription(description);
		}

		if (!title.equals(managedProject.getTitle())) {
			logger.trace("update title");
			managedProject.setTitle(title);
		}

		if (!metodology.equals(managedProject.getMetodologyCode())) {
			logger.trace("update metodology");
			managedProject.setMetodologyCode(metodology);
		}

		// ^ is XOR operand
		if (isPublic ^ managedProject.isPublic()) {
			logger.trace("update privacy");
			managedProject.setPublic(isPublic);
		}
		
		return true;
	}
	
	/**
	 * TODO refactor this innocence
	 * @throws Exception 
	 */
	public Project getByTitle(String title) throws Exception {
		return projectDAO.get(TITLE_COLUMN, title);
	}
	
	public void refresh(Project project) {
		projectDAO.refresh(project);
	}
}
