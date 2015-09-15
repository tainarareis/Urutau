package com.modesteam.urutau.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.modesteam.urutau.dao.SystemDAO;
import com.modesteam.urutau.model.Administrator;

/**
 * Realizes an filter in index request to create 
 * an new {@link Administrator} if no exist
 * 
 * It's an way to do this, the other needs create an static {@link EntityManager} 
 * to initialize before of the servletContext.
 */
@WebFilter("/")
public class AdministratorCreatorFilter implements Filter {

	@Inject
	private SystemDAO systemDAO;
	
	/**
	 * @deprecated CDI eye only
	 */
	public AdministratorCreatorFilter() {
	
	}
	
	public AdministratorCreatorFilter(SystemDAO systemDAO) {
		this.systemDAO = systemDAO;
	}

	/**
	 * If database is empty, create an admin
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(!systemDAO.existAdministrator()){
			systemDAO.createFirstAdministrator();
		} else {
			// Admin yet created!
		}
		chain.doFilter(request, response);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}

}