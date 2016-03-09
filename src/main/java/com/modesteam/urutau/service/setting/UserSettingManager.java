package com.modesteam.urutau.service.setting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.User;
import com.modesteam.urutau.model.system.setting.Setting;
import com.modesteam.urutau.model.system.setting.UserSetting;

@SessionScoped
public class UserSettingManager implements SettingManager, Serializable {

    /**
     * {@link SessionScoped} requires
     */
    private static final long serialVersionUID = -1457316681261263887L;

    private static final Logger logger = LoggerFactory.getLogger(UserSettingManager.class);

    private final EntityManager manager;

    private List<UserSetting> settings = new ArrayList<UserSetting>();

    /**
     * @deprecated only CDI
     */
    public UserSettingManager() {
        this(null);
    }

    @Inject
    public UserSettingManager(EntityManager manager) {
        this.manager = manager;
    }

    /**
     * TODO create setting if not exists
     * 
     */
    public void load(@Observes User user) {

        this.settings = user.getSettings();

        logger.info("Number loaded " + settings.size());
    }

    @Override
    public void save(Setting setting) {
        try {
            manager.merge(setting);
        } catch (IllegalArgumentException exception) {
            throw new IllegalStateException("When merge, db fails");
        }
    }
}
