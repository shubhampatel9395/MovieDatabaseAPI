package com.moviesdbapi.exception;

public class MessageConstants {
	
	// Common
	public static final String SUCCESS_MESSAGE = "Operation success";
	
	public static final String ACCESS_DENIED_MESSAGE = "You are not authorized to access this URI.";
	
	public static final Long INVALID_ID_ERROR_CODE = 601L;
	
	public static final Long INVALID_COUNTRY_ERROR_CODE = 602L;

	public static final String INVALID_COUNTRY_ERROR_MESSAGE = "Invalid country name";
	
	public static final Long INVALID_USER_ROLE_ERROR_CODE = 603L;

	public static final String INVALID_USER_ROLE_ERROR_MESSAGE = "Invalid user role";
	
	public static final Long INVALID_DATE_ERROR_CODE = 604L;

	public static final String INVALID_DATE_ERROR_MESSAGE = "Invalid Date (Date must be valid and must be in dd/MM/yyyy format)";
	
	public static final Long INVALID_TIME_ERROR_CODE = 605L;

	public static final String INVALID_TIME_ERROR_MESSAGE = "Invalid Time (Time must be valid and must be in HH:mm:ss format)";
	
	public static final Long INVALID_LANGUAGE_ERROR_CODE = 606L;

	public static final String INVALID_LANGUAGE_ERROR_MESSAGE = "Invalid Language";
	
	public static final Long INVALID_CURRENCY_ERROR_CODE = 607L;

	public static final String INVALID_CURRENCY_ERROR_MESSAGE = "Invalid Currency";
	
	public static final Long NOT_ALLOWED_IMAGE_FILE_EXTENSION_ERROR_CODE = 608L;

	public static final String NOT_ALLOWED_IMAGE_FILE_EXTENSION_ERROR_MESSAGE = "Not allowed extension (Only .jpg,.jpeg and .png are allowed.)";
	
	public static final Long ADD_FILE_ERROR_CODE = 609L;

	public static final String ADD_FILE_ERROR_MESSAGE = "Error while adding poster";
	
	public static final Long GET_FILE_ERROR_CODE = 610L;

	public static final String GET_FILE_ERROR_MESSAGE = "Error while fetching poster";
	
	// User
	public static final Long DUPLICATE_EMAIL_ERROR_CODE = 701L;

	public static final String DUPLICATE_EMAIL_ERROR_MESSAGE = "Email id is already registered in the system.";
	
	public static final Long INVALID_PASSWORD_ERROR_CODE = 702L;

	public static final String INVALID_PASSWORD_ERROR_MESSAGE = "Password must be at least 8 characters long, must not contain white-space, must contain at least one number, one special character and must have a mixture of uppercase and lowercase letters.";

	// Movie
	public static final Long DUPLICATE_MOVIE_ERROR_CODE = 703L;

	public static final String DUPLICATE_MOVIE_ERROR_MESSAGE = "Given movie title released on the given release date is already present in the system.";
	
	public static final Long INVALID_GENRE_ERROR_CODE = 704L;

	public static final String INVALID_GENRE_ERROR_MESSAGE = "Invalid Movie Genre";
	
	// Movie Cast Type
	public static final Long DUPLICATE_MOVIE_CAST_ERROR_CODE = 705L;

	public static final String DUPLICATE_MOVIE_CAST_ERROR_MESSAGE = "Cast already exists for the given movie.";
	
	public static final Long INVALID_MOVIE_CAST_TYPE_ERROR_CODE = 706L;

	public static final String INVALID_MOVIE_CAST_TYPE_ERROR_MESSAGE = "Invalid Movie Cast Type";
	
}