package com.modesteam.urutau.model.system.setting;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.setting.SettingManager;

/**
 * This abstract class is the basis of settings to some system entities like: 
 * System(Global configurations), {@link User}, {@link Project}.
 * It has many ways to keep their children, each one should 
 * be used by one {@link SettingManager}.
 * 
 * {@link SequenceGenerator} is not used only in {@link SystemSetting} 
 * because their ids properties are described by {@link SystemSettingContext} 
 * with a constant values.
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@SequenceGenerator( name = "setting_id", initialValue=100)
public abstract class Setting {
	
	/**
	 * To generate a value automatically, 
	 * use the follow annotation {@link GeneratedValue} 
	 * in the setValue() method override
	 */
	@Id
	private Long id;
	
	/**
	 * The respective children should format this field
	 * to ensure a right value. This property can 
	 * represent booleans, numbers(integer and float)
	 * and some characters
	 */
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 *  This method are used to convert a {@link Object} 
	 *  in a valid value to field above
	 */
	public abstract void setValue(Object genericValue);
	
	public abstract Enum<?> getContext();
}
