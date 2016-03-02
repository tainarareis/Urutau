package com.modesteam.urutau.model.system.settings;

public enum SettingType {
	USER_REGISTRATION_IS_OPEN(false),
	ONLY_ADMINISTRATOR_CAN_INVITE(true),
	NEEDS_APPROVAL_OF_ADMINISTRATOR(false),
	SYSTEM_EMAIL(null);
	
	private Object defaultValue;
	
	SettingType(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	/**
	 * @return Default value of object
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * See if name exist
	 * 
	 * @param name to compare
	 * @return true if have the same name
	 */
	public static boolean settingExist(String name){
		
		boolean isEquals = false;
		
		for(SettingType setting : SettingType.values()) {
			if(setting.name().equals(name)) {
				isEquals = true;
				break;
			}
		}
		
		return isEquals;
	}
}
