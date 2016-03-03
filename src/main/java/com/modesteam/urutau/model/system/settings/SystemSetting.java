package com.modesteam.urutau.model.system.settings;

public class SystemSetting extends Setting {
	
	private final SystemSettingContext context;
	
	public SystemSetting(SystemSettingContext context) {
		this.context = context;
		super.setId(context.getId());
	}
	
	@Override
	public void setValue(Object genericValue) {
		switch (context) {
			case NEEDS_APPROVAL_OF_ADMINISTRATOR:
				setLikeBooleanValue(genericValue);
				break;
			case ONLY_ADMINISTRATOR_CAN_INVITE:
				setLikeBooleanValue(genericValue);
				break;
			case USER_REGISTRATION_IS_OPEN:
				setLikeBooleanValue(genericValue);
				break;
			case SYSTEM_EMAIL:
				super.setValue(String.valueOf(genericValue));
				break;
			default:
				break;
		}
	}
	
	
	private void setLikeBooleanValue(Object genericValue) {
		if(genericValue == Boolean.TRUE) {
			super.setValue("N");
		} else {
			super.setValue("Y");
		}
	}

	@Override
	public Enum<?> getContext() {
		return context;
	}
}
