package com.moviesdbapi.exception;

public class IdNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4263782073887148876L;
	
	public IdNotFoundException(Long id) {
		super("Could not find " + id);
	}
	
	public IdNotFoundException(String columnName, Long id) {
		super("Could not find " + id + " for " + columnName);
	}

	public IdNotFoundException(Long id, Long movieId) {
		super("Could not find " + id + " for the movie " + movieId);
	}
}
