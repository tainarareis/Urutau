package com.modesteam.urutau.controller;

import java.util.List;

import javax.inject.Inject;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.system.FieldMessage;
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class KanbanController {	
	
	private final Result result;
	private final Validator validator;
	private final KanbanService kanbanService;
	private final ProjectService projectService;
	
	/**
	 * @deprecated CDI 
	 */
	public KanbanController() {
		this(null, null, null, null);
	}
	
	@Inject
	public KanbanController(Result result, Validator validator, 
			KanbanService kanbanService, ProjectService projectService) {
		this.result = result;
		this.validator = validator;
		this.kanbanService = kanbanService;
		this.projectService = projectService;
	}
	
	@Get
	@Path("/kanban/{projectID}")
	public List<Layer> load(long projectID) throws Exception {
		Project currentProject = projectService.load(projectID);
		
		result.include("requirements", currentProject.getRequirements());
		
		return currentProject.getLayers();
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
