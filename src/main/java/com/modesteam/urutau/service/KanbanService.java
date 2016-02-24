package com.modesteam.urutau.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.modesteam.urutau.dao.LayerDAO;
import com.modesteam.urutau.dao.ProjectDAO;
import com.modesteam.urutau.model.system.Layer;

public class KanbanService {

	private static final Long FIRST_DEFAULT_LAYER_ID = 1L;

	private static final Long LAST_DEFAULT_LAYER_ID = 4L;

	private final LayerDAO layerDAO;
	private final ProjectDAO projectDAO;
		
	public KanbanService() {
		this(null, null);
	}
	
	@Inject
	public KanbanService(LayerDAO layerDAO, ProjectDAO projectDAO) {
		this.layerDAO = layerDAO;
		this.projectDAO = projectDAO;
	}
	
	/**
	 * Get all layers used into project
	 *  
	 * @param projectID unique code identifier
	 * @return {@link List} of {@link Layer}
	 */
	public List<Layer> load(Long projectID) throws Exception {
		return projectDAO.find(projectID).getLayers();
	}
	
	/**
	 * Get Default layers of all project: 
	 * Backlog, In Progress, Do, Done.
	 * 
	 * @return {@link List} of default layers above
	 */
	public List<Layer> getDefaultLayers() {
		List<Layer> defaultLayers = new ArrayList<Layer>();
		
		for(Long id = FIRST_DEFAULT_LAYER_ID; id <= LAST_DEFAULT_LAYER_ID; id++) {
			Layer found = layerDAO.find(id);
			defaultLayers.add(found);
		}
		
		return defaultLayers;
	}
	
}
