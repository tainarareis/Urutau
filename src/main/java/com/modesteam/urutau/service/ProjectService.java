package com.modesteam.urutau.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.RequirementDAO;

public class ProjectService {
	private ProjectDAO projectDAO;
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
	
	public ProjectService() {
		this(null);
	}

	@Inject
	public ProjectService(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
	}

}
