package com.modesteam.urutau.dao;

import java.util.List;

import com.modesteam.urutau.model.Artifact;

/**
 *
 * Data access object for Artifacts
 *
 */
public interface RequirementDAO {
	void create(Artifact artifact);
	
	/**
	 * Gets a object instance that have a field with certain value
	 */
	Artifact get(String field, Object value) throws Exception;
	
	/**
	 * Finds by id
	 */
	Artifact find(Long id);
	
	Artifact update(Artifact artifact);
	
	void destroy(Artifact artifact);
	
	List<Artifact> findUsing(String sql);
}