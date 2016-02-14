package com.modesteam.urutau.dao;

import java.util.List;

import com.modesteam.urutau.model.Artifact;

/**
 *
 * Data access object for Artifacts
 *
 */
public interface RequirementDAO {
	boolean create(final Artifact Artifact);
	
	/**
	 * Gets a object instance that have a field with certain value
	 */
	Artifact get(String field, Object value) throws Exception;
	
	/**
	 * Finds by id
	 */
	Artifact find(final Long id);
	
	boolean update(final Artifact Artifact);
	
	boolean destroy(final Artifact Artifact);
	
	List<Artifact> getRequirementBetweenInterval(Long projectID, int firstResult, int maxResult);
}