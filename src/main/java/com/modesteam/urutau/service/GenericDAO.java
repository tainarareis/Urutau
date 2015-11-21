package com.modesteam.urutau.service;

import javax.persistence.EntityManager;

/**
 * File Name: GenericDAO<Entity>
 * Purpose: Defines the methods common to DAO classes 
 * @param <Entity>
 */
public abstract class GenericDAO<Entity> {
	
	private EntityManager entityManager;
	
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
		entityManager.persist(entity);
	}	
	
	
	/**
	 * Removes everything
	 * @param Entity to remove
	 * @return false if any error occurs
	 */
	public boolean destroy(Entity entity){
		entityManager.remove(entity);
		return true;
	}	
	
	/**
	 * Update an entity
	 * @param entity to be merge with database
	 * @return false if any error occurs
	 */
	public boolean update(Entity entity){
		entityManager.merge(entity);
		return true;
	}
	
}
