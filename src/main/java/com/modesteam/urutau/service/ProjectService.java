package com.modesteam.urutau.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.RequirementDAO;
import com.modesteam.urutau.model.Artifact;

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

	public List<? extends Project> loadAllProjects() {
		return projectDAO.loadAllProjects();
	}

	public Project detail(long id) {
		return projectDAO.find(id);
	}
	
	/**
	 * Method to communicate with the DAO
	 * asking for the project exclusion from database.
	 * @param project
	 */
	public void excludeProject(Long projectId) {
		if( projectId != null ) {
			Project project = projectDAO.find(projectId);
			
			if(project != null) {
				projectDAO.destroy(project);
			} else {
				throw new IllegalArgumentException("This project not exist");
			}
		} else {
			logger.info("RequirementService cant find requirementID");
		}
	}

}
