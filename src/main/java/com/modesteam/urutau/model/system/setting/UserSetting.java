package com.modesteam.urutau.model.system.setting;

import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import com.modesteam.urutau.model.User;

public class UserSetting extends Setting {
	private User user;
	
	@Transient
	private final UserSettingContext context;
	
	@Override
	@GeneratedValue
	public void setId(Long id) {
		super.setId(id);
	}
	
	public UserSetting(UserSettingContext context) {
		this.context = context;
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
