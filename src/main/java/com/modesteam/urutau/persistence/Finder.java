package com.modesteam.urutau.persistence;

import java.util.List;

/**
 * Responsible to find Entities of many ways. It are not perform order, to this
 * you should use {@link Order}
 */
public interface Finder<Entity> {
	/**
	 * Verify by id if Entity is stored
	 * 
	 * @param id
	 *            primary key
	 */
	public boolean exists(Long id);

	/**
	 * Find an Entity by your primary key
	 * 
	 * @return managed Object
	 */
	public Entity find(Long id);

	/**
	 * Find one or many Entities by some of its attributes
	 * 
	 * @return {@link List} with many Entities or empty
	 */
	public List<Entity> findBy(String field, Object value);

	/**
	 * Like {@link #findBy(String, Object)} but accept one or more parameter
	 * that should be concatenated in condition String. This string should have
	 * an expected format.
	 * 
	 * @param condition
	 *            in JPA case it would be like field=:value AND
	 *            other_field=:value2
	 * 
	 * @return {@link List} with many Entities or empty
	 */
	public List<Entity> where(String conditions);
}
