package com.sunjee.btms.exception;

public class AppRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 2787382261182544810L;

	public AppRuntimeException() {
		super();
	}

	public AppRuntimeException(String message) {
		super(message);
	}
	
	public AppRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
