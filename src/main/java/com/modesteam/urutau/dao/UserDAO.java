package com.modesteam.urutau.dao;

import com.modesteam.urutau.model.UrutaUser;

/**
 * Data Access Object for User
 */
public interface UserDAO {

	void create(final UrutaUser user);
	
	/**
	 * Gets a object instance that have a field with certain value
	 */
	UrutaUser get(String field, Object value) throws Exception;
	
	/**
	 * Finds by id
	 */
	UrutaUser find(final Long id);
	
	UrutaUser update(final UrutaUser user);
	
	void destroy(final UrutaUser user);
	
	/**
	 * Verifies if database have some User
	 */
	boolean hasAnyRegister();

	/**
	 * Reload user managed instance
	 */
	void reload(UrutaUser userLogged);
}
