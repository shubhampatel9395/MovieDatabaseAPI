package com.moviesdbapi.exception;

public class InvalidFilePathException extends RuntimeException {
	private static final long serialVersionUID = 3624711791502138069L;
	
	public InvalidFilePathException(String fileName) {
		super("Sorry! Filename contains invalid path sequence " + fileName);
	}
}