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

public class DefaultProjectDAO extends GenericDAO<Project> implements ProjectDAO {
	private static final Logger logger = LoggerFactory.getLogger(ProjectDAO.class);
	
	@Inject
	private EntityManager manager;	

	public DefaultProjectDAO() {
		
	}
	
	/**
	 * To inject manager into GenericDAO is required {@link Inject} annotation
	 */
	@Inject
	public DefaultProjectDAO(EntityManager manager) {
		super.setEntityManager(manager);
	}
	
	@Override
	public Project get(String field, Object value) {
		if(!DaoValidator.isValidParameter(value)) {
			
		} else {
			// continue
		}
		
		String sql = "SELECT project FROM " + Project.class.getName() 
				+ " project WHERE project."+ field + "=:value";
				
		logger.info(sql);
		
		Project project = null;
		
		try{
			Query query = manager.createQuery(sql);
			query.setParameter("value", value);
			project = (Project) query.getSingleResult();
		} catch (NonUniqueResultException exception){
			throw new NonUniqueResultException();
		} catch (NoResultException exception) {
			logger.debug("Any project was found", exception);
		}
		
		return project;
	}

	@Override
	public Project find(Long id) {
		return manager.find(Project.class, id);
	}

	@Override
	public List<Project> loadAll() {
		String sql = "SELECT project " + getClass().getSimpleName() + "FROM project";
		
		logger.info(sql);
		
		Query query = manager.createQuery(sql);
		List<Project> projectList = query.getResultList();
		
		return projectList;
	}
	
	@Override
	public boolean update(Project entity) {
		manager.flush();
		return super.update(entity);
	}
}
