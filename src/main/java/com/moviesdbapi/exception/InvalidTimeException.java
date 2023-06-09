package com.moviesdbapi.exception;

import com.fasterxml.jackson.databind.JsonMappingException;

public class InvalidTimeException extends JsonMappingException {
	private static final long serialVersionUID = 5816474032658392501L;

	@SuppressWarnings("deprecation")
	public InvalidTimeException() {
		super(MessageConstants.INVALID_TIME_ERROR_MESSAGE);
	}
}