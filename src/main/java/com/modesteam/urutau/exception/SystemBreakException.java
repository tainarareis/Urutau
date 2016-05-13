package com.modesteam.urutau.exception;

/**
 * This should be thrown when system is in a invalid state, like timeout errors,
 * fails of libraries or some component that is not behaving correctly
 */
public class SystemBreakException extends RuntimeException {

	private static final long serialVersionUID = 5529393463608510066L;

	public SystemBreakException() {
		super();
	}

	public SystemBreakException(String message) {
		super(message);
	}

	public SystemBreakException(Exception exception) {
		super(exception);
	}

	public SystemBreakException(String message, Exception exception) {
		super(message, exception);
	}

}
