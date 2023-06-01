package com.moviesdbapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.ReviewEntity;
import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.model.dto.ReviewDTO;

public interface IReviewService {
	public List<ReviewEntity> findAll();

	public Optional<ReviewEntity> findById(Long id);
	
	public ReviewDTO findOneByMovieIdUserId(Long movieId, Long userId);
	
	public ReviewEntity findOneByMovieAndUser(MovieEntity movie, UserDetailsEntity user);
	
	public List<ReviewEntity> findByMovie(MovieEntity movie);
	
	public List<ReviewDTO> findByFieldValue(String fieldName, Object fieldValue);

	public List<ReviewDTO> findByNamedParameters(MapSqlParameterSource paramSource);

	public ReviewEntity insert(ReviewEntity entity) throws RuntimeException;

	public ReviewEntity update(ReviewEntity entity) throws RuntimeException ;

	public void delete(Long id);

	public void delete(ReviewEntity entity);
}
