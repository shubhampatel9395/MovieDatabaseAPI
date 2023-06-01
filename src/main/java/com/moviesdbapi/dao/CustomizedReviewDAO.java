package com.moviesdbapi.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.model.dto.ReviewDTO;

public interface CustomizedReviewDAO {
	public List<ReviewDTO> findByFieldValue(String fieldName, Object fieldValue);

	public List<ReviewDTO> findByNamedParameters(MapSqlParameterSource paramSource);
	
	public ReviewDTO findOneByMovieIdUserId(Long movieId, Long userId);
	
	public default boolean softDelete(Long id) {
		return false;
	}
	
	public default boolean activate(Long id) {
		return false;
	}
}
