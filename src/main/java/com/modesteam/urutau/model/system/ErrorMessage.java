package com.modesteam.urutau.model.system;

public enum ErrorMessage {
	USER_NOT_LOGGED("Generic");

	private final String errorDescription;

	private ErrorMessage(final String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	@Override
	public String toString() {
		return errorDescription;
	}
}