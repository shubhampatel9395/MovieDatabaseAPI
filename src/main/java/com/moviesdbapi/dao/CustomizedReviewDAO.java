package com.moviesdbapi.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.model.ReviewEntity;

public interface CustomizedReviewDAO {
	public List<ReviewEntity> findByFieldValue(String fieldName, Object fieldValue);

	public List<ReviewEntity> findByNamedParameters(MapSqlParameterSource paramSource);
	
	public ReviewEntity findOneByMovieIdUserId(Long movieId, Long userId);
	
	public default boolean softDelete(Long id) {
		return false;
	}
	
	public default boolean activate(Long id) {
		return false;
	}
}
