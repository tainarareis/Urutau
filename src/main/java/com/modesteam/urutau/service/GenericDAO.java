package com.modesteam.urutau.service;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.User;

/**
 * File Name: GenericDAO<Entity>
 * Purpose: Defines the methods common to DAO classes 
 * @param <Entity>
 */
public abstract class GenericDAO<Entity> {
	
	protected EntityManager entityManager;
	private static final Logger logger = LoggerFactory.getLogger(GenericDAO.class);
	
	/**
	 * Get an entity from any attribute
	 * @param param is any attribute of Entity
	 * @return to be found
	 */
	public abstract Entity get(String field, Object value);
	
	
	/**
	 * Search an Entity
	 * @param id identifier of Entity
	 * @return what is found
	 */
	public abstract Entity find(Long id);
	
	/**
	 * Persist an Entity
	 * @param entity to be persisted
	 */
	public void create(Entity entity){
		try {
			entityManager.persist(entity);
		} catch (Exception exception) {
			exception.printStackTrace();
		} 
	}	
	
	
	/**
	 * Removes everything
	 * @param Entity to remove
	 * @return false if any error occurs
	 */
	public boolean destroy(Entity entity){
		try {
			entityManager.remove(entity);
			logger.info("Successfully deleted.");
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		} 
	}	
	
	/**
	 * Update an entity
	 * @param entity to be merge with database
	 * @return false if any error occurs
	 */
	public boolean update(Entity entity){
		try {
			entityManager.merge(entity);
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		} 
	}
	
	/**
	 * 
	 * @param entityManager
	 */
	public void setEntityManager(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	public EntityManager getEntityManager(){
		return entityManager;
	}
	
}
