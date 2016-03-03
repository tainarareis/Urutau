package com.modesteam.urutau.model.system.settings;

public interface SettingManager {
	public void load(SettingContext context);
	public boolean update(Setting setting);
	public Setting get(Enum<?> settingContext);
	public boolean clean(SettingContext context);
}
