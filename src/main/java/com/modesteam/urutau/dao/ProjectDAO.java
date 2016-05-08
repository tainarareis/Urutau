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
	Project find(Long id);
	
	Project update(Project Project);
	
	boolean destroy(Project Project);

	/**
	 * Load all projects
	 */
	List<Project> loadAll();
	
	void refresh(Project project);
}
