package com.modesteam.urutau.dao;

import java.util.List;

import com.modesteam.urutau.model.system.Layer;

/**
 * Data Access Object for Layer
 *
 */
public interface LayerDAO {
	
	boolean create(Layer layer);
	
	/**
	 * Gets a object instance that have a field with certain value
	 */
	Layer get(String field, Object value) throws Exception;
	
	/**
	 * Finds by id
	 */
	Layer find(Long id) throws IllegalArgumentException;
	
	Layer update(Layer Layer);
	
	boolean destroy(Layer Layer);

	/**
	 * Load all Layers of project
	 */
	List<Layer> loadAllByProject(Long id);
}
