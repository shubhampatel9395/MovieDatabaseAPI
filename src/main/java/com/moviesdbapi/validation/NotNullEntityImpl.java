
package com.moviesdbapi.validation;

import com.moviesdbapi.model.EnuUserRoleEntity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullEntityImpl implements ConstraintValidator<NotNullEntity, EnuUserRoleEntity> {

	@Override
	public boolean isValid(EnuUserRoleEntity value, ConstraintValidatorContext context) {
		return !(value == null);
	}

}
