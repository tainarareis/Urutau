package com.modesteam.urutau.model.system.settings;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.modesteam.urutau.exception.SystemBreakException;

@Entity
public class GlobalSetting {
	@Transient
	private final SettingType type;

	@Id
	private String name;
	private String value;

	public SettingType getType() {
		return type;
	}
	
	/**
	 * When instantiated, name can not be changed  
	 * with an unknow value by {@link SettingType}
	 */
	public GlobalSetting() {
		this.type = null;
	}
		
	public GlobalSetting(SettingType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(SettingType.settingExist(name)){
			this.name = name;
		} else {
			throw new SystemBreakException();
		}
	}
	
//	public void setName(SettingType type) {
//		this.name = type.name();
//	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
