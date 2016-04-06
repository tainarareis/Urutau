package com.modesteam.urutau.dao;

import com.modesteam.urutau.model.UrutaUser;

/**
 * Data Access Object for User
 */
public interface UserDAO {

	boolean create(final UrutaUser user);
	
	/**
	 * Gets a object instance that have a field with certain value
	 */
	UrutaUser get(String field, Object value) throws Exception;
	
	/**
	 * Finds by id
	 */
	UrutaUser find(final Long id);
	
	boolean update(final UrutaUser user);
	
	boolean destroy(final UrutaUser user);
	
	/**
	 * Verifies if database have some User
	 */
	boolean hasAnyRegister();

	boolean flush();
}
