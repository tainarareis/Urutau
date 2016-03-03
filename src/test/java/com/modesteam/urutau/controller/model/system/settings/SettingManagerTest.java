package com.modesteam.urutau.controller.model.system.settings;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.modesteam.urutau.dao.SettingDAO;
import com.modesteam.urutau.model.system.settings.DefaultSettingManager;
import com.modesteam.urutau.model.system.settings.Setting;
import com.modesteam.urutau.model.system.settings.SettingContext;
import com.modesteam.urutau.model.system.settings.SettingFlyweightFactory;
import com.modesteam.urutau.model.system.settings.SystemSetting;
import com.modesteam.urutau.model.system.settings.SystemSettingContext;

public class SettingManagerTest {

	private static final String EMAIL_TEST = "test@email.com";
	
	private DefaultSettingManager manager;
	private SettingDAO daoMock;
	private SettingFlyweightFactory factoryMock;
	
	@Before
	public void setUp() {
		daoMock = mock(SettingDAO.class);
		factoryMock = mock(SettingFlyweightFactory.class);
	}
	
	@After
	public void clear() {
		this.factoryMock = null;
		this.manager = null;
	}
	
	@Test
	public void testLoad() throws Exception {		
		Set<Setting> settings = new HashSet<Setting>();
		settings.add(new SystemSetting(SystemSettingContext.SYSTEM_EMAIL));
		settings.add(new SystemSetting(SystemSettingContext.USER_REGISTRATION_IS_OPEN));
		
		shouldReturnWhenContextIsCalled(settings, SettingContext.SYSTEM);
		
		manager = new DefaultSettingManager(daoMock, factoryMock);
		manager.load(SettingContext.SYSTEM);
		
		assertEquals(2, manager.countSettings());
	}

	@Test
	public void testUpdate() throws Exception {
		shouldReturnWhenContextIsCalled(loadAnMockSettings(), SettingContext.SYSTEM);
		
		manager =  new DefaultSettingManager(daoMock, factoryMock);
		manager.load(SettingContext.SYSTEM);
		
		Setting setting = new SystemSetting(SystemSettingContext.SYSTEM_EMAIL);
		setting.setValue(EMAIL_TEST);
		
		manager.update(setting);
		
		assertEquals(setting.getValue(), 
				manager.get(SystemSettingContext.SYSTEM_EMAIL).getValue());
	}
	
	private Set<Setting> loadAnMockSettings() {
		Set<Setting> requestedSettings = new HashSet<Setting>();
		for(SystemSettingContext s : SystemSettingContext.values()){
			SystemSetting setting = new SystemSetting(s);
			requestedSettings.add(setting);
		}
		return requestedSettings;
	}
	
	private void shouldReturnWhenContextIsCalled(Set<Setting> settings, SettingContext context) throws Exception {
		when(factoryMock.create(context)).thenReturn(settings);
	}
}
