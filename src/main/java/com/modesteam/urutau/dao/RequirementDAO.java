package com.modesteam.urutau.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.service.DaoInterface;

/**
 * 
 * Accesses the database related to the Requirements.
 */
@RequestScoped
public class RequirementDAO implements DaoInterface<Artifact> {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementDAO.class);
	
	@Inject
	private EntityManager manager;
	
	/**
	 * Get all requirements from a specific Artifact subclass
	 * @param artifactSubclass means the artifact type, in this case
	 * represented by the name of the artifact table in database.
	 * Possible values (tables): Epic, Feature, Storie, UseCase, Artifact.
	 * @return an Child of Artifact 
	 */
	public List<? extends Artifact> loadAllRequirements() {
		
		String sql = "SELECT ALL A FROM Artifact A";
		Query query = manager.createQuery(sql);
		
		List<Artifact> requirementsList = query.getResultList();
		return requirementsList;
	}
	
	@Override
	public void create(Artifact entity) {
		manager.persist(entity);
	}
	
	@Override
	public Artifact get(String field, Object value) {
		logger.debug("Field: " + field + ". Value: " + value);
		// Select an child of artifact 
		String sql = "SELECT id FROM "+ Artifact.class.getName() +" requirement"
				+ " WHERE requirement" + field + "=:value";
		
		logger.info(sql);
		
		try {
			// Run sql
			Query query = manager.createQuery(sql);
			query.setParameter("value", value);
			return (Artifact) query.getSingleResult();
		} catch (NonUniqueResultException exception){
			throw new NonUniqueResultException();
		} catch (NoResultException exception) {
			return null;
		}
	}

	@Override
	public Artifact find(Long id) {
		return manager.find(Artifact.class, id);
	}
	
	/**
	 * Treat exception
	 */
	@Override
	public boolean destroy(Artifact entity) {
		manager.remove(entity);
		return true;
	}
	
	/**
	 * Treat exception
	 */
	@Override
	public boolean update(Artifact entity) {
		manager.merge(entity);
		return true;
	}


}