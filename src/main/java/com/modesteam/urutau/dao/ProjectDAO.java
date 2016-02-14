package com.modesteam.urutau.dao;

import java.util.List;

import com.modesteam.urutau.model.Project;

public interface ProjectDAO {
	boolean create(Project project);
	
	/**
	 * Gets a object instance that have a field with certain value
	 */
	Project get(String field, Object value) throws Exception;
	
	/**
	 * Finds by id
	 */
	Project find(final Long id);
	
	boolean update(final Project Project);
	
	boolean destroy(final Project Project);

	/**
	 * Load all projects
	 */
	List<Project> loadAll();
}
