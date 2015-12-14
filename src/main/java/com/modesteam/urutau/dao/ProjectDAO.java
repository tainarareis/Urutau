package com.modesteam.urutau.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.service.GenericDAO;

public class ProjectDAO extends GenericDAO<Project>{
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectDAO.class);
	
	@Inject
	private EntityManager manager;	

	public ProjectDAO() {
		
	}
	
	@Inject
	public ProjectDAO(EntityManager manager) {
		super.setEntityManager(manager);
	}
	
	/**
	 * Captures a single project if it exists in database
	 * @param field - identifies the object, is used to search for it
	 * @param value - object  
	 */
	@Override
	public Project get(String field, Object value) {
		logger.debug("field."+field + "value." +value);
		
		String sql = "SELECT project FROM " + Project.class.getName() + " project " 
				+ "WHERE project."+ field + "=:value";
				
		logger.info(sql);
		
		try{
			Query query = manager.createQuery(sql);
			query.setParameter("value", value);
			return (Project) query.getSingleResult();
		} catch (NonUniqueResultException exception){
			throw new NonUniqueResultException();
		} catch (NoResultException exception) {
			logger.trace("No result was throw into " + this.getClass().getName());
			return null;
		}
	}

	@Override
	public Project find(Long id) {
		Project projectFound = entityManager.find(Project.class, id);
		return projectFound;
	}

	/**
	 * Captures all projects existent in which the user has access
	 * @return projects
	 */
	public List<? extends Project> loadAllProjects() {

		String sql = "SELECT ALL PROJECT FROM Project PROJECT";
		Query query = manager.createQuery(sql);		
		List<Project> projectList = query.getResultList();
		return projectList;
	}
}
