package com.modesteam.urutau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.builder.UserBuilder;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.model.system.Configuration;
import com.modesteam.urutau.service.AdministratorService;
import com.modesteam.urutau.service.ConfigurationService;

import br.com.caelum.vraptor.util.test.MockResult;

public class AdministratorControllerTest {
	
	private static final String ADMINISTRATOR_DATA = "admin";
	
	private MockResult result;
	private AdministratorService adminService;

	@Before
	public void setUp() {
		result = new MockResult();
		
		adminService = mock(AdministratorService.class);
	}
	
	@Test
	public void testChangeFirstSettings() {
		UserBuilder builder = new UserBuilder();
		
		User user = builder
			.login(ADMINISTRATOR_DATA)
			.email(ADMINISTRATOR_DATA)
			.password(ADMINISTRATOR_DATA)
			.name(ADMINISTRATOR_DATA)
			.lastName(ADMINISTRATOR_DATA)
			.build();
		
		mockConfigureNew(user);
		
		AdministratorController controller = 
				new AdministratorController(result, adminService);
		
		controller.changeFirstSettings(user);
	}
	
	@Test
	public void changeSecondSettings(){
		List<Configuration> configurations = new ArrayList<Configuration>();
		
		Configuration configuration = new Configuration();
		configuration.setName("email");
		configuration.setValue("example@example.com");
		configurations.add(configuration);
		
		mockPutConfiguration(configuration);
		
		AdministratorController controller = new AdministratorController(result, adminService, configService);
		
		controller.changeSecondSettings(configurations);
	}

	private void mockPutConfiguration(Configuration configuration) {
		doNothing().when(configService).put(configuration);
	}

	private void mockConfigureNew(User user) {
		doNothing().when(adminService).configureNew(user);
	}
}
