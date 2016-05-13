package com.modesteam.urutau.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.Administrator;
import com.modesteam.urutau.service.AdministratorService;

/**
 * Realizes an filter in index request to create an new {@link Administrator} if
 * no exist
 * 
 * It's an way to do this, the other needs create an static
 * {@link EntityManager} to initialize before of the servletContext.
 */
@WebFilter("/")
public class AdministratorCreatorFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(AdministratorCreatorFilter.class);

	private static final String CHANGE_SETTINGS_VIEW = "/administrator/createFirstAdministrator";

	private final AdministratorService administratorService;

	/**
	 * @deprecated CDI eye only
	 */
	public AdministratorCreatorFilter() {
		this(null);
	}

	@Inject
	public AdministratorCreatorFilter(AdministratorService administratorService) {
		this.administratorService = administratorService;
	}

	/**
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (administratorService.existAdministrator()) {
			// Admin yet created!
			chain.doFilter(request, response);
		} else {
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher(CHANGE_SETTINGS_VIEW);
			logger.debug("Redirecting with " + requestDispatcher + " to change settings");
			requestDispatcher.forward(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}