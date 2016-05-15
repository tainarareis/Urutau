package com.modesteam.urutau.controller;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.exception.UserActionException;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.system.FieldMessage;
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.RequirementService;

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
	public KanbanController(Result result, Validator validator, KanbanService kanbanService,
			ProjectService projectService, RequirementService requirementService) {
		this.result = result;
		this.validator = validator;
		this.kanbanService = kanbanService;
		this.projectService = projectService;
		this.requirementService = requirementService;
	}

	@Get
	@Path("/kanban/{project.id}")
	public void load(final Project project) {
		// TODO specific error
		result.on(UserActionException.class).redirectTo(ApplicationController.class)
				.invalidRequest();

		Project currentProject = projectService.find(project.getId());

		result.include("project", currentProject);
	}

	/**
	 * Move requirement to another layer
	 */
	@Post("/kanban/move")
	public void move(final Long requirementID, final Long layerID) throws Exception {

		logger.info("Requesting the move of one requirement");

		Artifact requirementToMove = null;

		try {
			requirementToMove = requirementService.find(requirementID);
			Layer targetLayer = kanbanService.getLayerByID(layerID);

			if (!validator.hasErrors()) {
				requirementToMove.setLayer(targetLayer);
				requirementService.update(requirementToMove);
			} else {
				validator.onErrorRedirectTo(this).load(requirementToMove.getProject());
			}
		} catch (IllegalArgumentException exception) {
			validator.add(new I18nMessage(FieldMessage.ERROR, "invalid_request"));
			validator.onErrorRedirectTo(ApplicationController.class).invalidRequest();
		}

		// TODO Make this by other way
		I18nMessage successMessage = new I18nMessage(FieldMessage.KANBAN_STATUS,
				"successfully_moved_object");
		successMessage.setBundle(ResourceBundle.getBundle("messages"));

		result.use(Results.json()).withoutRoot().from(successMessage.getMessage()).serialize();
	}

	/**
	 * Creates a new layer
	 * 
	 * @param projectID
	 *            project that needs this layer
	 * @param layer
	 *            new instance into database
	 * @throws Exception
	 */
	@Post
	public void createLayer(final @NotNull Long projectID, @NotNull Layer layer) {
		// TODO more specific
		result.on(UserActionException.class).redirectTo(ApplicationController.class)
				.invalidRequest();

		Project currentProject = projectService.find(projectID);

		try {
			kanbanService.create(layer);
		} catch (IllegalArgumentException exception) {
			SimpleMessage errorMessage = new SimpleMessage(FieldMessage.ERROR,
					exception.getMessage());
			validator.add(errorMessage);
			validator.onErrorRedirectTo(this).load(currentProject);
		} catch (SystemBreakException systemBreakException) {
			// TODO create a specific redirect
			result.redirectTo(ApplicationController.class).dificultError();
		}

		currentProject.add(layer);

		projectService.update(currentProject);

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
