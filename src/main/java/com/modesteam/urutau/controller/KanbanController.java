package com.modesteam.urutau.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.system.FieldMessage;
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class KanbanController {	
	private static final Logger logger = LoggerFactory.getLogger(KanbanController.class);
	
	private final Result result;
	private final Validator validator;
	private final KanbanService kanbanService;
	private final ProjectService projectService;
	private final RequirementService requirementService;
	
	/**
	 * @deprecated CDI 
	 */
	public KanbanController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public KanbanController(Result result, Validator validator, 
			KanbanService kanbanService, ProjectService projectService, 
			RequirementService requirementService) {
		this.result = result;
		this.validator = validator;
		this.kanbanService = kanbanService;
		this.projectService = projectService;
		this.requirementService = requirementService;
	}
	
	@Get
	@Path("/kanban/{projectID}")
	public List<Layer> load(long projectID) throws Exception {
		Project currentProject = projectService.load(projectID);
		
		result.include("requirements", currentProject.getRequirements());
		
		return currentProject.getLayers();
	}
	
	/**
	 * Move requirement to another layer
	 */
	@Post("/kanban/move")
	public void move(Long requirementID, Long layerID) throws Exception {
		logger.info("Requesting the move of one requirement");
		
		Artifact requirementToTransfer = requirementService.getByID(requirementID);
		
		Layer targetLayer = kanbanService.getByID(layerID);
		
		requirementToTransfer.setLayer(targetLayer);
		requirementService.update(requirementToTransfer);
		
		result.redirectTo(this).load(requirementToTransfer.getProject().getProjectID());
	}
	
	public void customize() {
		
	}
	
	@Post
	public void createLayer(long projectID, Layer layer) throws Exception {
		if(layer.getName() == null) {
			SimpleMessage errorMessage = new SimpleMessage(FieldMessage.ERROR, 
					"Layer name can not be blank");
			validator.add(errorMessage);
		}
		result.redirectTo(this).load(projectID);
	}
	
	public void deleteLayer() {

	}
	
	public void updateLayer() {
		
	}
	
	@View
	public void editLayer() {
		
	}
	
}
