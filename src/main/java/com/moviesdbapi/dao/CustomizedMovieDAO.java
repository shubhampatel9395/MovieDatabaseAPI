package com.moviesdbapi.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.model.dto.MovieDTO;

public interface CustomizedMovieDAO {
	public List<MovieDTO> findByFieldValue(String fieldName, Object fieldValue);

	public List<MovieDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	
	public default boolean softDelete(Long id) {
		return false;
	}
	
	public default boolean activate(Long id) {
		return false;
	}
}
