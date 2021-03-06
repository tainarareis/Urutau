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
			Project project = projectDAO.find(id);
			
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
	public boolean exists(long id) {
		logger.info("Verifying the requirement existence in database.");
		
		boolean projectExists = projectDAO.find(id) != null;
		
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
	 * See if title can be used
	 * 
	 * @param projectTitle
	 * @throws DataBaseCorruptedException 
	 */
	public boolean canBeUsed(final String projectTitle) throws DataBaseCorruptedException {
		boolean valueNotUsed = false;
		
		try {
			if(projectDAO.get(TITLE_COLUMN, projectTitle) == null) {
				valueNotUsed = true;
			}
		} catch (NoResultException noResultException) {
		    valueNotUsed = true;
		} catch (NonUniqueResultException exception) {
			throw new DataBaseCorruptedException(this.getClass().getSimpleName() 
					+ " invokes canBeUsed and throw grave exception", exception);
		}

		return valueNotUsed;
	}

	public Project find(Long projectID) {
        return projectDAO.find(projectID);
    }

	/**
	 * Update attributes passed by a detached object
	 * 
	 * @param detachedProject created in a page form
	 */
	public void update(Project detachedProject) {
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
