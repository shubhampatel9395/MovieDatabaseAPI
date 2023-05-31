package com.moviesdbapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.model.MovieCastEntity;
import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.dto.MovieCastDTO;

public interface IMovieCastService {
	public List<MovieCastEntity> findAll();

	public Optional<MovieCastEntity> findById(Long id);

	public List<MovieCastDTO> findAllByMovieId(Long id);

	public List<MovieCastEntity> findAllByMovie(MovieEntity movie);

	public List<MovieCastDTO> findByFieldValue(String fieldName, Object fieldValue);

	public List<MovieCastDTO> findByNamedParameters(MapSqlParameterSource paramSource);

	public List<MovieCastDTO> insert(List<MovieCastEntity> entity) throws RuntimeException;

	public MovieCastDTO update(MovieCastEntity entity) throws RuntimeException;

	public void delete(Long id);

	public void delete(MovieCastEntity entity);

	public void deleteAll(List<MovieCastEntity> entities);

	public List<MovieCastDTO> findAllByCastTypeId(Long movieId, Long movieCastTypeId);
}
