package com.moviesdbapi.exception;

public class ParameterMissingException extends RuntimeException {
	private static final long serialVersionUID = 2762852407855864122L;

	public ParameterMissingException() {
		super();
	}

	public ParameterMissingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ParameterMissingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParameterMissingException(String message) {
		super(message);
	}

	public ParameterMissingException(Throwable cause) {
		super(cause);
	}
	
}
