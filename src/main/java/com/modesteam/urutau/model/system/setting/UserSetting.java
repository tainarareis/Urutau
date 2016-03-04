package com.modesteam.urutau.model.system.setting;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.modesteam.urutau.model.User;

@Entity
public class UserSetting extends Setting {
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Transient
	private UserSettingContext context;
	
	/**
	 * @deprecated only eye JPA 
	 */
	public UserSetting() {
		
	}
	
	public UserSetting(UserSettingContext context) {
		this.context = context;
	}

	@Override
	@GeneratedValue
	public void setId(Long id) {
		super.setId(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setValue(Object genericValue) {
		switch (context) {
		case THEME:
			
			break;
		default:
			setValue(String.valueOf(genericValue));
			break;
		}
	}
}
