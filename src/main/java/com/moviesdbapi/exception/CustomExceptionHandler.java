package com.moviesdbapi.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionDetails> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exp) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage("Validation Error");
		exceptionDetails.setDescription(exp.getBindingResult().getFieldError().getDefaultMessage());
		
		return new ResponseEntity<ExceptionDetails>(exceptionDetails,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ExceptionDetails> AccessDeniedExceptionHandler(AccessDeniedException exp, WebRequest req){
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(exp.getMessage());
		exceptionDetails.setDescription(req.getDescription(false));
		
		return new ResponseEntity<ExceptionDetails>(exceptionDetails,HttpStatus.FORBIDDEN);
	}
	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ExceptionDetails> OtherExceptionHandler(Exception exp, WebRequest req){
//		ExceptionDetails exceptionDetails = new ExceptionDetails();
//		exceptionDetails.setTimestamp(LocalDateTime.now());
//		exceptionDetails.setMessage(exp.getLocalizedMessage());
//		exceptionDetails.setDescription(req.getDescription(false));
//		
//		return new ResponseEntity<ExceptionDetails>(exceptionDetails,HttpStatus.BAD_REQUEST);
//	}
	
	@ExceptionHandler(ParameterMissingException.class)
	public ResponseEntity<ExceptionDetails> ParameterMissingExceptionHandler(ParameterMissingException exp, WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage("One or more required parameter/s were missing in the request.");
		exceptionDetails.setDescription(req.getDescription(false));
		
		return new ResponseEntity<ExceptionDetails>(exceptionDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<ExceptionDetails> DuplicateEmailExceptionHandler(DuplicateEmailException exp, WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.DUPLICATE_EMAIL_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.DUPLICATE_EMAIL_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));
		
		return new ResponseEntity<ExceptionDetails>(exceptionDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ExceptionDetails> InvalidPasswordExceptionExceptionHandler(InvalidPasswordException exp, WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_PASSWORD_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.INVALID_PASSWORD_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));
		
		return new ResponseEntity<ExceptionDetails>(exceptionDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidCountryException.class)
	public ResponseEntity<ExceptionDetails> InvalidCountryExceptionHandler(InvalidCountryException exp, WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_COUNTRY_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.INVALID_COUNTRY_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));
		
		return new ResponseEntity<ExceptionDetails>(exceptionDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidUserRoleException.class)
	public ResponseEntity<ExceptionDetails> InvalidUserRoleExceptionHandler(InvalidUserRoleException exp, WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_USER_ROLE_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.INVALID_USER_ROLE_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));
		
		return new ResponseEntity<ExceptionDetails>(exceptionDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidDateException.class)
	public ResponseEntity<ExceptionDetails> InvalidDateExceptionHandler(InvalidDateException exp, WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_DATE_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(MessageConstants.INVALID_DATE_ERROR_MESSAGE);
		exceptionDetails.setDescription(req.getDescription(false));
		
		return new ResponseEntity<ExceptionDetails>(exceptionDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ExceptionDetails> IdNotFoundExceptionHandler(IdNotFoundException exp, WebRequest req) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setErrorCode(MessageConstants.INVALID_ID_ERROR_CODE);
		exceptionDetails.setTimestamp(LocalDateTime.now());
		exceptionDetails.setMessage(exp.getMessage());
		exceptionDetails.setDescription(req.getDescription(false));
		
		return new ResponseEntity<ExceptionDetails>(exceptionDetails,HttpStatus.BAD_REQUEST);
	}
	
}
