package com.modesteam.urutau.exception;

public class SystemBreakException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SystemBreakException(String exceptionMessage) {
		super(exceptionMessage);
	}
	
	public SystemBreakException() {
		super();
	}
}
