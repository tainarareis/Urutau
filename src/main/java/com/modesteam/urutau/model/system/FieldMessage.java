package com.modesteam.urutau.model.system;

/**
 * This enum have name of fields which contains messages after some
 * post request.
 * 
 */
public enum FieldMessage {
	SUCCESS("message-success"), 
	ERROR("message-error");
	
	private final String fieldName;
    
	private FieldMessage(final String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public String toString() {
		return fieldName;
	}	
}