package com.moviesdbapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.model.ReviewEntity;

public interface IReviewService {
	public List<ReviewEntity> findAll();

	public Optional<ReviewEntity> findById(Long id);
	
	public ReviewEntity findOneByMovieIdUserId(Long movieId, Long userId);
	
	public List<ReviewEntity> findByFieldValue(String fieldName, Object fieldValue);

	public List<ReviewEntity> findByNamedParameters(MapSqlParameterSource paramSource);

	public ReviewEntity insert(ReviewEntity entity) throws RuntimeException;

	public ReviewEntity update(ReviewEntity entity) throws RuntimeException ;

	public void delete(Long id);

	public void delete(ReviewEntity entity);
}
