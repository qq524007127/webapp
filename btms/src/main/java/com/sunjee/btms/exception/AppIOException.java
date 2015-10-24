package com.sunjee.btms.exception;

public class AppIOException extends Exception {

	private static final long serialVersionUID = 3652206581267388111L;

	public AppIOException() {
		super();
	}

	public AppIOException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppIOException(String message) {
		super(message);
	}

	public AppIOException(Throwable cause) {
		super(cause);
	}

}
