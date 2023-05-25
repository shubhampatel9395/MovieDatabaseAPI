package com.moviesdbapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.model.dto.UserDetailsDTO;

public interface IUserDetailsService {
	public List<UserDetailsEntity> findAll();

	public List<UserDetailsDTO> findAllActive();

	public Optional<UserDetailsEntity> findById(Long id);
	
	public UserDetailsEntity findOneByEmail(String email);

	public List<UserDetailsEntity> findByFieldValue(String fieldName, Object fieldValue);

	public List<UserDetailsDTO> findByNamedParameters(MapSqlParameterSource paramSource);

	public UserDetailsEntity insert(UserDetailsEntity entity) throws RuntimeException;

	public UserDetailsEntity update(UserDetailsEntity entity) throws RuntimeException ;

	public void delete(Long id);

	public void delete(UserDetailsEntity entity);

	public boolean activate(Long id);

	public boolean softDelete(Long id);
}
