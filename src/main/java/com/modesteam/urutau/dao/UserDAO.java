package com.modesteam.urutau.dao;

import com.modesteam.urutau.model.User;

/**
 * Data Access Object for User
 */
public interface UserDAO {

	boolean create(final User user);
	
	/**
	 * Gets a object instance that have a field with certain value
	 */
	User get(String field, Object value) throws Exception;
	
	/**
	 * Finds by id
	 */
	User find(final Long id);
	
	boolean update(final User user);
	
	boolean destroy(final User user);
	
	/**
	 * Verifies if database have some User
	 */
	boolean hasAnyRegister();
}
