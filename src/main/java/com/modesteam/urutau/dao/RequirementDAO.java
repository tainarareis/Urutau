package com.modesteam.urutau.dao;

import java.util.List;

import com.modesteam.urutau.model.Artifact;

/**
 *
 * Data access object for Artifacts
 *
 */
public interface RequirementDAO {
	boolean create(Artifact artifact);
	
	/**
	 * Gets a object instance that have a field with certain value
	 */
	Artifact get(String field, Object value) throws Exception;
	
	/**
	 * Finds by id
	 */
	Artifact find(Long id);
	
	Artifact update(Artifact artifact);
	
	boolean destroy(Artifact artifact);
	
	List<Artifact> getRequirementBetweenInterval(Long projectID, int firstResult, int maxResult);
}