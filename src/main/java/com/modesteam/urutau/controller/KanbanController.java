package com.modesteam.urutau.controller;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

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
	@Path("/kanban/{project.id}")
	public void load(final Project project) throws Exception {
		Project currentProject = projectService.load(project);
		
		result.include("project", currentProject);
	}
	
	/**
	 * Move requirement to another layer
	 */
	@Post("/kanban/move")
	public void move(final @NotNull Long requirementID, final @NotNull Long layerID) throws Exception {

		logger.info("Requesting the move of one requirement");
		
		Artifact requirementToTransfer = requirementService.getByID(requirementID);
		
		try {
			Layer targetLayer = kanbanService.getLayerByID(layerID);
			
			requirementToTransfer.setLayer(targetLayer);
			
			requirementService.update(requirementToTransfer);
		} catch (IllegalArgumentException exception) {
			SimpleMessage simpleMessage = new SimpleMessage(FieldMessage.KANBAN_STATUS, "invalid_layer");
			validator.add(simpleMessage);
		}
		
		validator.onErrorRedirectTo(this).load(requirementToTransfer.getProject());
		result.nothing();
	}
	
	public void customize() {
		
	}
	
	@Post
	public void createLayer(final @NotNull Long projectID, @NotNull Layer layer) 
			throws Exception {
		Project currentProject = projectService.getByID(projectID);
		
		currentProject.add(layer);
		
		boolean isComplete = kanbanService.create(layer) && projectService.update(currentProject);
		
		if(!isComplete) {
			SimpleMessage errorMessage = new SimpleMessage(FieldMessage.ERROR, 
					"Persistence error...");
			validator.add(errorMessage);
		}
		
		validator.onErrorRedirectTo(this).load(currentProject);
		result.redirectTo(this).load(currentProject);
	}
	
	public void deleteLayer() {

	}
	
	public void updateLayer() {
		
	}
	
	@View
	public void editLayer() {
		
	}
	
}
