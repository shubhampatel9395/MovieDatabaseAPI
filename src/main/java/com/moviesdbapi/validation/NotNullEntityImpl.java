
package com.moviesdbapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullEntityImpl implements ConstraintValidator<NotNullEntity, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return !(value == null);
	}

}
