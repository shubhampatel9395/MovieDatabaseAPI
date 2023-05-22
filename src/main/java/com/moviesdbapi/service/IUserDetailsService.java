package com.moviesdbapi.service;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.model.UserDetailsEntity;

public interface IUserDetailsService {
	public List<UserDetailsEntity> findAll();
	public List<UserDetailsEntity> findAllActive();
	public UserDetailsEntity findById(Long id);	
	public List<UserDetailsEntity> findByFieldValue(String fieldName, Object fieldValue);
	public List<UserDetailsEntity> findByNamedParameters(MapSqlParameterSource paramSource);
	public UserDetailsEntity insert(UserDetailsEntity entity);
	public UserDetailsEntity update(UserDetailsEntity entity);
	public void delete(Long id);
	public void delete(UserDetailsEntity entity);
	public List<UserDetailsEntity> isUniqueEmail(String email);
	public boolean activate(Long id);
	public boolean softDelete(Long id);
}
