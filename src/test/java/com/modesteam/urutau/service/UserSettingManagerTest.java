package com.modesteam.urutau.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.model.User;
import com.modesteam.urutau.model.system.setting.UserSetting;
import com.modesteam.urutau.model.system.setting.UserSettingContext;
import com.modesteam.urutau.service.setting.UserSettingManager;

public class UserSettingManagerTest {

    private UserSettingManager manager;

    private EntityManager entityManager;

    @Before
    public void setUp() {
        this.entityManager = mock(EntityManager.class);

        this.manager = new UserSettingManager(entityManager);
    }

    @Test
    public void testLoad() {
        User userMock = mock(User.class);

        List<UserSetting> settings = new ArrayList<UserSetting>();
        settings.add(new UserSetting(UserSettingContext.THEME));

        when(userMock.getSettings()).thenReturn(settings);

        manager.load(userMock);
    }

    @Test
    public void testSave() {
        UserSetting setting = mock(UserSetting.class);

        doNothingWhenMerge(setting);

        manager.save(setting);
    }

    private void doNothingWhenMerge(UserSetting setting) {
        when(entityManager.merge(setting)).thenReturn(setting);
    }

}
