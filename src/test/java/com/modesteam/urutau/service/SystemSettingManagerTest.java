package com.modesteam.urutau.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.model.system.setting.Setting;
import com.modesteam.urutau.model.system.setting.SystemSetting;
import com.modesteam.urutau.model.system.setting.SystemSettingContext;
import com.modesteam.urutau.service.setting.SystemSettingManager;

public class SystemSettingManagerTest {

    private static final String VALID_VALUE = "Hello world";
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

    @Test
    public void testValidSaveSystemSettings() {
        for (SystemSettingContext context : SystemSettingContext.values()) {
            shouldReturnValidMockWhenFind(entityManager, context);
        }

        manager.setUp();

        SystemSetting setting = new SystemSetting(SystemSettingContext.SYSTEM_EMAIL);
        setting.setValue(VALID_VALUE);

        doNothingWhenMerge(setting);

        manager.save(setting);

        assertEquals(manager.get(SystemSettingContext.SYSTEM_EMAIL).getValue(), VALID_VALUE);
    }

    @Test
    public void testValidSaveSystemSettingsByPersist() {
        for (SystemSettingContext context : SystemSettingContext.values()) {
            shouldReturnValidMockWhenFind(entityManager, context);
        }

        manager.setUp();

        SystemSetting setting = new SystemSetting(SystemSettingContext.SYSTEM_EMAIL);
        setting.setValue(VALID_VALUE);

        throwExceptionWhenMerge(setting);

        doNothingWhenSave(setting);

        manager.save(setting);

        assertEquals(manager.get(SystemSettingContext.SYSTEM_EMAIL).getValue(), VALID_VALUE);
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidSaveSettings() {
        for (SystemSettingContext context : SystemSettingContext.values()) {
            shouldReturnValidMockWhenFind(entityManager, context);
        }

        manager.setUp();

        SystemSetting setting = new SystemSetting(SystemSettingContext.SYSTEM_EMAIL);
        setting.setValue(VALID_VALUE);

        throwExceptionWhenMerge(setting);

        throwExceptionWhenSave(setting);

        manager.save(setting);
    }

    private void throwExceptionWhenSave(SystemSetting setting) {
        doThrow(new IllegalStateException()).when(entityManager).persist(setting);
    }

    private void doNothingWhenSave(SystemSetting setting) {
        doNothing().when(entityManager).persist(setting);
    }

    private void throwExceptionWhenMerge(SystemSetting setting) {
        when(entityManager.merge(setting)).thenThrow(new IllegalArgumentException());
    }

    private void doNothingWhenMerge(Setting setting) {
        when(entityManager.merge(setting)).thenReturn(setting);
    }

    private void shouldReturnValidMockWhenFind(EntityManager entityManager, SystemSettingContext context) {
        when(entityManager.find(SystemSetting.class, context.getId())).thenReturn(new SystemSetting(context));
    }
}
