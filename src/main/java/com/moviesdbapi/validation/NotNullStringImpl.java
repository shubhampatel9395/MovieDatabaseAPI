package com.moviesdbapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullStringImpl implements ConstraintValidator<NotBlankString, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value != null) {
			return !(value.isBlank());
		} else {
			return true;
		}
	}

}
