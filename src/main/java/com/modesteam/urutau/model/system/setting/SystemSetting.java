package com.modesteam.urutau.model.system.setting;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Represents the settings that catch all system
 */
@Entity
public class SystemSetting extends Setting {
	
	@Transient
	private SystemSettingContext context;
	
	/**
	 * @deprecated JPA eyes only
	 */
	public SystemSetting() {

	}
	
	public SystemSetting(SystemSettingContext context) {
		this.context = context;
		super.setId(context.getId());
	}
	
	@Override
	public void setValue(Object genericValue) {
		switch (context) {
			case SYSTEM_EMAIL:
				super.setValue((String) genericValue);
				break;
			default:
				super.setValue(String.valueOf(genericValue));
				break;
		}
	}
}
