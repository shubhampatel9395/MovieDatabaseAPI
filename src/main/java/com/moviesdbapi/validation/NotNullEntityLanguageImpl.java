package com.moviesdbapi.validation;

import java.util.Set;

import com.moviesdbapi.model.EnuLanguageEntity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullEntityLanguageImpl implements ConstraintValidator<NotNullEntityCollection, Set<EnuLanguageEntity>> {
	@Override
	public boolean isValid(Set<EnuLanguageEntity> value, ConstraintValidatorContext context) {
		return !(value == null || value.size() == 0);
	}
}