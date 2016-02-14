package com.modesteam.urutau.dao;

public interface EntityCreator<Entity> {
	/**
	 * Basic and generic validation of attributes like
	 * user authentication, null fields
	 * 
	 * @param entity that will be persisted
	 */
	boolean validate(Entity entity);
	
	/**
	 * Calls persistence of entity
	 */
	boolean create(Entity entity);
}
