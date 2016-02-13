package com.modesteam.urutau.controller;

import java.io.IOException;

import static org.mockito.Mockito.*;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.service.AdministratorService;

public class AdministratorCreatorFilterTest {
	private final Logger logger = Logger.getLogger(AdministratorCreatorFilter.class); 
	private ServletRequest request;
	private ServletResponse response;
	private FilterChain chain;
	private AdministratorService administratorService;
	
	@Before
	public void setUp() throws ServletException, IOException{
		logger.setLevel(Level.DEBUG);
		
		this.request = mock(ServletRequest.class);
		this.response = mock(ServletResponse.class);
		
		when(request.getRequestDispatcher("/administrator/changeFirstSettings"))
			.thenReturn(EasyMock.createMock(RequestDispatcher.class));
		
		this.chain = mock(FilterChain.class);
		this.administratorService = mock(AdministratorService.class);
	}

	@Test
	public void testDoFilterNoAdminRegistered() throws IOException, ServletException {
		mockExistenceOfAdministratorWith(false);
		
		doNothingWhenCreateFirst();
		
		AdministratorCreatorFilter filter = new AdministratorCreatorFilter(administratorService);
		filter.doFilter(request, response, chain);
	}

	@Test
	public void testDoFilterWithAdminRegistered() throws IOException, ServletException {
		mockExistenceOfAdministratorWith(false);
		
		doNothingWhenCreateFirst();
		
		AdministratorCreatorFilter filter = new AdministratorCreatorFilter(administratorService);
		filter.doFilter(request, response, chain);
	}

	private void mockExistenceOfAdministratorWith(boolean condition) {
		when(administratorService.existAdministrator()).thenReturn(condition);
	}
	
	private void doNothingWhenCreateFirst() {
		doNothing().when(administratorService).createFirst();
	}
	
}
