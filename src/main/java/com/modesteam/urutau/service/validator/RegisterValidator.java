package com.modesteam.urutau.service.validator;

import com.modesteam.urutau.model.User;

public class RegisterValidator {
	private final User user;
	
	public RegisterValidator(User user) {
		this.user = user;
	}
		
	public boolean hasNullField() {
		boolean hasNullField = user.getEmail() == null 
				|| user.getLogin() == null 
				|| user.getName() == null 
				|| user.getPasswordVerify() == null 
				|| user.getPassword() == null;
		
		return hasNullField;
	}
	
	public boolean validPasswordConfirmation() {
		String original = user.getPassword();
		String confirmation = user.getPasswordVerify();
		return original.equals(confirmation);
	}
}
