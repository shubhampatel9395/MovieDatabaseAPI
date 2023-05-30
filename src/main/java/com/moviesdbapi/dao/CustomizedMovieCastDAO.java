package com.moviesdbapi.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.model.dto.MovieCastDTO;

public interface CustomizedMovieCastDAO {
	public List<MovieCastDTO> findByFieldValue(String fieldName, Object fieldValue);

	public List<MovieCastDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	
	public List<MovieCastDTO> findAllByMovieId(Long movieId);

	public default boolean softDelete(Long id) {
		return false;
	}

	public default boolean activate(Long id) {
		return false;
	}
}
