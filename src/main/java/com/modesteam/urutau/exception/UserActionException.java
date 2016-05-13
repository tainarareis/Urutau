package com.modesteam.urutau.exception;

/**
 * This exception should be used when data 
 * is invalid because of a entry of user
 */
public class UserActionException extends Exception {

	private static final long serialVersionUID = 2165164598895933811L;

	public UserActionException(String message) {
		super(message);
	}

	public UserActionException(String message, Exception exception) {
		super(message);
	}

}
