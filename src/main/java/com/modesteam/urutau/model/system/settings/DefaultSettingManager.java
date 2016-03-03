package com.modesteam.urutau.model.system.settings;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.modesteam.urutau.dao.SettingDAO;
import com.modesteam.urutau.exception.SystemBreakException;

@ApplicationScoped
public class DefaultSettingManager implements SettingManager {
	
	private final SettingDAO settingDAO;

	private final SettingFlyweightFactory factory;
	
	private Set<Setting> settings;
	
	/**
	 * @deprecated CDI only
	 */
	public DefaultSettingManager() {
		this(null, null);
	}
	
	@Inject
	public DefaultSettingManager(SettingDAO dao, 
			SettingFlyweightFactory factory) {
		this.settingDAO = dao;
		this.factory = factory;
		this.settings = new HashSet<Setting>();
	}
	
	@Override
	public void load(SettingContext context) {
		try {
			settings.addAll(factory.create(context));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean update(Setting setting) {
		try {
			settingDAO.update(setting);
			settings.remove(setting);
			settings.add(setting);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * TODO see performance
	 */
	@Override
	public Setting get(Enum<?> settingContext) {
		Setting settingFound = null;
		
		for(Setting setting : settings) {
			if (setting.getContext() ==  settingContext) {
				settingFound = setting;
			}
		}
		
		if(settingFound == null) {
			throw new SystemBreakException("Invalid context of setting");
		} else {
			return settingFound;			
		}
	}

	@Override
	public boolean clean(SettingContext context) {
		// TODO
		return false;
	}
	
	public int countSettings(){
		return settings.size();
	}

}
