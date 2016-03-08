package com.modesteam.urutau.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.model.system.setting.SystemSetting;
import com.modesteam.urutau.model.system.setting.SystemSettingContext;
import com.modesteam.urutau.service.setting.SystemSettingManager;

public class SystemSettingManagerTest {

    private SystemSettingManager manager;
    private EntityManager entityManager;

    @Before
    public void setUp() {
        entityManager = mock(EntityManager.class);

        manager = new SystemSettingManager(entityManager);
    }

    @Test
    public void testLoadSystemSettings() {
        for (SystemSettingContext context : SystemSettingContext.values()) {
            shouldReturnValidMockWhenFind(entityManager, context);
        }

        manager.setUp();
    }

    @Test
    public void testGetSystemSettings() {
        shouldReturnValidMockWhenFind(entityManager, SystemSettingContext.SYSTEM_EMAIL);
        shouldReturnValidMockWhenFind(entityManager, SystemSettingContext.USER_REGISTRATION_IS_OPEN);

        manager.setUp();

        assertEquals(SystemSettingContext.SYSTEM_EMAIL.getId(), manager.get(SystemSettingContext.SYSTEM_EMAIL).getId());
    }

    private void shouldReturnValidMockWhenFind(EntityManager entityManager, SystemSettingContext context) {
        when(entityManager.find(SystemSetting.class, context.getId())).thenReturn(new SystemSetting(context));
    }
}
