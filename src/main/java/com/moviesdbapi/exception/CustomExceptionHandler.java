package com.moviesdbapi.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> MethodArgumentNotValidExceptionHandler(
			MethodArgumentNotValidException exp) {
		Map<String, Object> res = new HashMap<>();
		res.put("timestamp", LocalDateTime.now());
		exp.getBindingResult().getAllErrors().forEach(err -> {
			res.put(((FieldError) err).getField(), err.getDefaultMessage());
		});
		return new ResponseEntity<Map<String, Object>>(res, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ExceptionDetails> AccessDeniedExceptionHandler(AccessDeniedException exp, WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setErrorCode(Long.valueOf(HttpStatus.FORBIDDEN.value()));
		exceptionDetails.setMessage(MessageConstants.ACCESS_DENIED_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.FORBIDDEN);
	}

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ExceptionDetails> OtherExceptionHandler(Exception exp, WebRequest req) {
//		ExceptionDetails exceptionDetails = new ExceptionDetails();
//		exceptionDetails.setTimestamp(LocalDateTime.now());
//		exceptionDetails.setMessage(exp.getLocalizedMessage());
//		exceptionDetails.setDescription(req.getDescription(false));
//
//		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
//	}

	@ExceptionHandler(ParameterMissingException.class)
	public ResponseEntity<ExceptionDetails> ParameterMissingExceptionHandler(ParameterMissingException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage("One or more required parameter/s were missing in the request.");
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<ExceptionDetails> DuplicateEmailExceptionHandler(DuplicateEmailException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.DUPLICATE_EMAIL_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.DUPLICATE_EMAIL_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ExceptionDetails> InvalidPasswordExceptionExceptionHandler(InvalidPasswordException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_PASSWORD_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.INVALID_PASSWORD_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidCountryException.class)
	public ResponseEntity<ExceptionDetails> InvalidCountryExceptionHandler(InvalidCountryException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_COUNTRY_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.INVALID_COUNTRY_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidUserRoleException.class)
	public ResponseEntity<ExceptionDetails> InvalidUserRoleExceptionHandler(InvalidUserRoleException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_USER_ROLE_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.INVALID_USER_ROLE_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidDateException.class)
	public ResponseEntity<ExceptionDetails> InvalidDateExceptionHandler(InvalidDateException exp, WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_DATE_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.INVALID_DATE_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidTimeException.class)
	public ResponseEntity<ExceptionDetails> InvalidTimeExceptionHandler(InvalidTimeException exp, WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_TIME_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.INVALID_TIME_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ExceptionDetails> IdNotFoundExceptionHandler(IdNotFoundException exp, WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_ID_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(exp.getMessage());
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidLanguageException.class)
	public ResponseEntity<ExceptionDetails> InvalidLanguageExceptionHandler(InvalidLanguageException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_LANGUAGE_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.INVALID_LANGUAGE_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidGenreException.class)
	public ResponseEntity<ExceptionDetails> InvalidGenreExceptionHandler(InvalidGenreException exp, WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_GENRE_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.INVALID_GENRE_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidCurrencyException.class)
	public ResponseEntity<ExceptionDetails> InvalidCurrencyExceptionHandler(InvalidCurrencyException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_CURRENCY_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.INVALID_CURRENCY_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidFilePathException.class)
	public ResponseEntity<ExceptionDetails> InvalidFilePathExceptionHandler(InvalidFilePathException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode((long) HttpStatus.BAD_REQUEST.value());
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(exp.getMessage());
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AddFileException.class)
	public ResponseEntity<ExceptionDetails> AddFileExceptionHandler(AddFileException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.ADD_FILE_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.ADD_FILE_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(GetFileException.class)
	public ResponseEntity<ExceptionDetails> GetFileExceptionHandler(GetFileException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.GET_FILE_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.GET_FILE_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NotAllowedImageFileExtensionException.class)
	public ResponseEntity<ExceptionDetails> NotAllowedImageFileExtensionExceptionHandler(NotAllowedImageFileExtensionException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.NOT_ALLOWED_IMAGE_FILE_EXTENSION_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.NOT_ALLOWED_IMAGE_FILE_EXTENSION_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateMovieException.class)
	public ResponseEntity<ExceptionDetails> DuplicateMovieExceptionHandler(DuplicateMovieException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.DUPLICATE_MOVIE_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.DUPLICATE_MOVIE_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidMovieCastTypeException.class)
	public ResponseEntity<ExceptionDetails> InvalidMovieCastTypeExceptionHandler(InvalidMovieCastTypeException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_MOVIE_CAST_TYPE_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.INVALID_MOVIE_CAST_TYPE_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateMovieCastException.class)
	public ResponseEntity<ExceptionDetails> DuplicateMovieCastExceptionHandler(DuplicateMovieCastException exp,
			WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.DUPLICATE_MOVIE_CAST_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.DUPLICATE_MOVIE_CAST_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));

		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

}
