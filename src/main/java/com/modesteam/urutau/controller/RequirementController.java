package com.modesteam.urutau.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.system.FieldMessage;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;

/**
 * This class is responsible to manager simple operations of requirements! The
 * systems operations are received by the path /requirement followed by the
 * operation defined path.
 */
@Controller
public class RequirementController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);

	private static final int DEFAULT_PAGINATION_SIZE = 5;

	// Injected objects
	private final Result result;

	private final RequirementService requirementService;

	private final Validator validator;

	public RequirementController() {
		this(null, null, null);
	}

	@Inject
	public RequirementController(Result result, RequirementService requirementService,
			Validator validator) {
		this.result = result;
		this.requirementService = requirementService;
		this.validator = validator;
	}

	/**
	 * Show a requirement that has a certain id and title
	 * 
	 * @param id
	 *            Unique attribute
	 * @param title
	 *            various requirement can have same title
	 * 
	 * @return {@link Artifact} requirement from database
	 * 
	 * @throws UnsupportedEncodingException
	 *             invalid characters or decodes fails
	 */
	@Get
	@Path("/show/{id}/{title}")
	public void show(int id, String title) throws UnsupportedEncodingException {
		String decodedTitle = URLDecoder.decode(title, StandardCharsets.UTF_8.name());

		logger.info("Show requirement " + title);

		Artifact requirement = requirementService.getRequirement(id, decodedTitle);

		validator.addIf(requirement == null,
				new I18nMessage(FieldMessage.ERROR, "requirement_no_exist"));
		validator.onErrorUsePageOf(ApplicationController.class).dificultError();

		result.include(requirement);
	}

	/**
	 * Paginate requirements into home page of projects
	 * 
	 * @param page,
	 *            current page
	 * 
	 * @return List of {@link Artifact} to be easy display into home page of a
	 *         project
	 */
	@Get("{projectID}/paginate/{page}")
	public void paginate(long projectID, long page) {
		long upperLimit = page + DEFAULT_PAGINATION_SIZE;
		List<Artifact> requirements = requirementService.desc("dateOfCreation")
				.between(page, upperLimit).find().where("project_id=" + projectID);

		result.include("requirements", requirements);
	}

	/**
	 * This method is used to delete one requirement
	 */
	@Delete("/requirement/{requirementID}")
	public void delete(Long requirementID) {

		logger.info("The artifact with the id " + requirementID + " is solicitated for exclusion");

		Artifact requirement = requirementService.find(requirementID);
		try {
			requirementService.delete(requirement);
		} catch (Exception exception) {
			// TODO treat this
			result.notFound();
		}

		result.nothing();
	}

	@View
	public void detailRequirement() {

	}

	@View
	public void showExclusionResult() {

	}

}
