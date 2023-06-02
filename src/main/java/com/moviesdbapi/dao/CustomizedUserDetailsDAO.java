package com.moviesdbapi.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.model.dto.UserDetailsDTO;

public interface CustomizedUserDetailsDAO {
	public List<UserDetailsEntity> findByFieldValue(String fieldName, Object fieldValue);

	public List<UserDetailsDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	
	public Long signup(UserDetailsEntity entity);
	
	public default boolean softDelete(Long id) {
		return false;
	}
	
	public default boolean activate(Long id) {
		return false;
	}
}