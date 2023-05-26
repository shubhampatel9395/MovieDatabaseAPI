package com.moviesdbapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.dto.MovieDTO;

public interface IMovieService {
	public List<MovieEntity> findAll();

	public Optional<MovieEntity> findById(Long id);
	
	public List<MovieDTO> findByFieldValue(String fieldName, Object fieldValue);

	public List<MovieDTO> findByNamedParameters(MapSqlParameterSource paramSource);

	public MovieEntity insert(MovieEntity entity) throws RuntimeException;

	public MovieEntity update(MovieEntity entity) throws RuntimeException ;

	public void delete(Long id);

	public void delete(MovieEntity entity);
}
