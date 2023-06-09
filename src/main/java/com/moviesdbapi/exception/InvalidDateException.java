package com.moviesdbapi.exception;

import com.fasterxml.jackson.databind.JsonMappingException;

public class InvalidDateException extends JsonMappingException {
	private static final long serialVersionUID = 4785004328419205284L;

	@SuppressWarnings("deprecation")
	public InvalidDateException() {
		super(MessageConstants.INVALID_DATE_ERROR_MESSAGE);
	}
}