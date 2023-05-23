package com.moviesdbapi.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.exception.DuplicateEmailException;
import com.moviesdbapi.exception.InvalidCountryException;
import com.moviesdbapi.exception.InvalidDateException;
import com.moviesdbapi.exception.InvalidPasswordException;
import com.moviesdbapi.exception.InvalidUserRoleException;
import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.model.dto.UserDetailsDTO;

public interface IUserDetailsService {
	public List<UserDetailsEntity> findAll();

	public List<UserDetailsDTO> findAllActive();

	public UserDetailsEntity findById(Long id);

	public List<UserDetailsEntity> findByFieldValue(String fieldName, Object fieldValue);

	public List<UserDetailsEntity> findByNamedParameters(MapSqlParameterSource paramSource);

	public UserDetailsEntity insert(UserDetailsEntity entity) throws DuplicateEmailException, InvalidPasswordException,
			InvalidUserRoleException, InvalidCountryException, InvalidDateException;

	public UserDetailsEntity update(UserDetailsEntity entity);

	public void delete(Long id);

	public void delete(UserDetailsEntity entity);

	public boolean activate(Long id);

	public boolean softDelete(Long id);
}
