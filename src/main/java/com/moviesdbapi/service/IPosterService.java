package com.moviesdbapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.PosterEntity;

public interface IPosterService {
	public List<PosterEntity> findAll();

	public Optional<PosterEntity> findById(Long id);

	public List<PosterEntity> findAllByMovie(MovieEntity movie);

	public List<PosterEntity> findByFieldValue(String fieldName, Object fieldValue);

	public List<PosterEntity> findByNamedParameters(MapSqlParameterSource paramSource);

	public List<PosterEntity> insert(List<PosterEntity> entity) throws RuntimeException;
	
	public PosterEntity insert(PosterEntity entity) throws RuntimeException;

	public void delete(Long id);

	public void delete(PosterEntity entity);

	public void deleteAll(List<PosterEntity> entities);
}
