package com.modesteam.urutau.dao;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File Name: GenericDAO<Entity>
 * Purpose: Defines the methods common to DAO classes 
 * @param <Entity>
 */
public abstract class GenericDAO<Entity> {
	private static final Logger logger = LoggerFactory.getLogger(GenericDAO.class);
	
	/* Used to logger when he register a exception */
	private static final String EXCEPTION_MESSAGE = "When use some method, "+ GenericDAO.class.getSimpleName() 
			+" was thrown this message";

	private EntityManager entityManager;
		
	/**
	 * Creates a new instance of User into database
	 * 
	 * @return true if operation do not throw any exception
	 */
	public boolean create(final Entity entity) {
		boolean objectCreated = false;
		
		try {
			entityManager.persist(entity);
			objectCreated = true;
		} catch (Exception exception) {
			logger.warn(EXCEPTION_MESSAGE, exception);
		}
		
		return objectCreated;
	}	
	
	
	/**
	 * Removes everything
	 * 
	 * @param Entity to remove
	 * @return false if any error occurs
	 */
	public boolean destroy(final Entity entity) {
		boolean objectDestroyed = false;
		
		try {
			entityManager.remove(entity);
			objectDestroyed = true;
		} catch (Exception exception) {
			logger.warn(EXCEPTION_MESSAGE, exception);
		} 
		
		return objectDestroyed;
	}	
	
	/**
	 * Update an entity
	 * 
	 * @param entity to be merge with database
	 * @return false if any error occurs
	 */
	public boolean update(final Entity entity) {
		boolean updateCompleted = false; 
		
		try {
			entityManager.merge(entity);
			updateCompleted = true;
		} catch (Exception exception) {
			logger.warn(EXCEPTION_MESSAGE, exception);
		} 
		
		return updateCompleted;
	}
	
	public boolean flush() {
		entityManager.flush();
		return false;
	}
	
	/**
	 * @param entityManager
	 */
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
