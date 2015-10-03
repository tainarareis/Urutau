package com.modesteam.urutau.controller;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;

import com.modesteam.urutau.builder.UserBuilder;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.model.system.Configuration;
import com.modesteam.urutau.service.AdministratorService;
import com.modesteam.urutau.service.ConfigurationService;

public class AdministratorControllerTest {
	
	private static final String ADMINISTRATOR_DATA = "admin";
	
	private MockResult mockResult;
	private AdministratorService mockAdminService;
	private ConfigurationService mockConfigService;

	@Before
	public void setUp() {
		this.mockResult = new MockResult();
		this.mockAdminService = EasyMock.createNiceMock(AdministratorService.class);
		this.mockConfigService = EasyMock.createNiceMock(ConfigurationService.class);
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
				new AdministratorController(mockResult, mockAdminService, mockConfigService);
		
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
		
		AdministratorController controller = new AdministratorController(mockResult, 
							mockAdminService, mockConfigService);
		
		controller.changeSecondSettings(configurations);
	}

	private void mockPutConfiguration(Configuration configuration) {
		mockConfigService.put(configuration);
		EasyMock.expectLastCall().asStub();
		EasyMock.replay(mockConfigService);
	}

	private void mockConfigureNew(User user) {
		mockAdminService.configureNew(user);
		EasyMock.expectLastCall().asStub();
		EasyMock.replay(mockAdminService);
	}
}
