package com.modesteam.urutau.dao;

public final class DaoValidator {
	/**
	 * Called only by DAOs, this is a validation of parameter value
	 */
	public static boolean isValidParameter(Object value) {
		boolean validParameter = false;
		
		if (value instanceof Integer || value instanceof String || value instanceof Long) {
			validParameter = true;
		} 
		
		return validParameter;
	}
}
