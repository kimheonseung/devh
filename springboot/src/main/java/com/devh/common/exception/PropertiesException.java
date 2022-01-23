package com.devh.common.exception;

public class PropertiesException extends Exception {

	private static final long serialVersionUID = -8978634512246579732L;
	
	public PropertiesException(String message) {
		super(message);
	}
	
	public PropertiesException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
