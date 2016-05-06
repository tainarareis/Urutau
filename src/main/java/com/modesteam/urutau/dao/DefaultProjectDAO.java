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
	
	private final EntityManager manager;
	private final DaoHelper daoHelper;
	
	/**
	 * To inject manager into GenericDAO is required {@link Inject} annotation
	 */
	@Inject
	public DefaultProjectDAO(EntityManager manager, DaoHelper helper) {
	    this.daoHelper = helper;
	    this.manager = manager;
	    // TODO Rethink this with liskov principle
		super.setEntityManager(manager);
	}
	
	@Override
	public Project get(String field, Object value) {
	    Project project = null;
		
	    if(daoHelper.isValidParameter(value)) {
		    try {
	            final String sql = daoHelper.getSelectQuery(Project.class, field);
	            Query query = manager.createQuery(sql);
	            query.setParameter("value", value);
	            
	            project = (Project) query.getSingleResult();
	        } catch (NonUniqueResultException exception) {
	            throw new NonUniqueResultException();
	        } catch (NoResultException exception) {
	            logger.debug("Any project was found", exception);
	        }
		} else {
		    throw new IllegalArgumentException("Invalid param has been passed");
		}
	    
	    return project;
	}

    @Override
	public Project find(Long id) {
		return manager.find(Project.class, id);
	}

	@Override
	public List<Project> loadAll() {
		String sql = "SELECT project " + getClass().getSimpleName() + " FROM project";
		
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
