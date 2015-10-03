package com.modesteam.urutau.service;
/**
 * 
 * 
 * @param <Entity>
 */
public interface DaoInterface<Entity> {
	/**
	 * Persist an Entity
	 * 
	 * @param entity to be persisted
	 */
	public void create(Entity entity);
	/**
	 * Get an entity from any atributte
	 * 
	 * @param param is any atributte of Entity
	 * @return to be found
	 */
	public Entity get(String field, Object value);
	/**
	 * Search an Entity
	 * 
	 * @param id identifier of Entity
	 * @return what is found
	 */
	public Entity find(Long id);
	/**
	 * Removes everything
	 * 
	 * @param Entity to remove
	 * @return false if any error occurs
	 */
	public boolean destroy(Entity entity);
	/**
	 * Update an entity
	 * 
	 * @param entity to be merge with database
	 * @return false if any error occurs
	 */
	public boolean update(Entity entity);
}
