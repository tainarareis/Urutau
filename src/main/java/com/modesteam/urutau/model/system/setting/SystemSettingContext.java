package com.modesteam.urutau.model.system.setting;

public enum SystemSettingContext {
	USER_REGISTRATION_IS_OPEN(1L),
	ONLY_ADMINISTRATOR_CAN_INVITE(2L),
	NEEDS_APPROVAL_OF_ADMINISTRATOR(3L),
	SYSTEM_EMAIL(4L);
	
	private final Long id;
	
	SystemSettingContext(final Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
}
