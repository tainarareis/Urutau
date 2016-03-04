package com.modesteam.urutau.service.setting;

import com.modesteam.urutau.model.system.setting.Setting;

public interface SettingManager {
	public void save(Setting setting);
	public void get(Setting setting);
}
