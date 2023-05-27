package com.moviesdbapi.validation;

import java.util.Set;

import com.moviesdbapi.model.EnuGenreEntity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullEntityGenreImpl implements ConstraintValidator<NotNullEntityCollection, Set<EnuGenreEntity>> {
	@Override
	public boolean isValid(Set<EnuGenreEntity> value, ConstraintValidatorContext context) {
		return !(value == null || value.size() == 0);
	}
}