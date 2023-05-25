package com.moviesdbapi.exception;

public class MessageConstants {
	public static final String SUCCESS_MESSAGE = "Operation success";
	
	public static final Long DUPLICATE_EMAIL_ERROR_CODE = 701L;

	public static final String DUPLICATE_EMAIL_ERROR_MESSAGE = "Email id is already registered in the system.";
	
	public static final Long INVALID_PASSWORD_ERROR_CODE = 702L;

	public static final String INVALID_PASSWORD_ERROR_MESSAGE = "Password must be at least 8 characters long, must contain at least one number, one special character and must have a mixture of	uppercase and lowercase letters.";

	public static final Long INVALID_COUNTRY_ERROR_CODE = 601L;

	public static final String INVALID_COUNTRY_ERROR_MESSAGE = "Invalid country name";
	
	public static final Long INVALID_USER_ROLE_ERROR_CODE = 602L;

	public static final String INVALID_USER_ROLE_ERROR_MESSAGE = "Invalid user role";
	
	public static final Long INVALID_DATE_ERROR_CODE = 603L;

	public static final String INVALID_DATE_ERROR_MESSAGE = "Invalid Date";
	
	public static final Long INVALID_ID_ERROR_CODE = 604L;
}