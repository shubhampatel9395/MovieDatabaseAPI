package com.moviesdbapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviesdbapi.dao.IMovieCastDAO;
import com.moviesdbapi.dao.IMovieDAO;
import com.moviesdbapi.model.MovieCastEntity;
import com.moviesdbapi.model.dto.MovieCastDTO;
import com.moviesdbapi.service.IMovieCastService;

@Service
@Transactional(rollbackFor = Exception.class)
public class MovieCastService implements IMovieCastService {
	@Autowired
	IMovieDAO movieDAO;
	
	@Autowired
	IMovieCastDAO movieCastDAO;
	
	@Override
	public List<MovieCastEntity> findAll() {
		return movieCastDAO.findAll();
	}

	@Override
	public Optional<MovieCastEntity> findById(Long id) {
		return movieCastDAO.findById(id);
	}

	@Override
	public List<MovieCastDTO> findAllByMovieId(Long id) {
		return movieCastDAO.findAllByMovieId(id);
	}

	@Override
	public List<MovieCastDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return movieCastDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<MovieCastDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return movieCastDAO.findByNamedParameters(paramSource);
	}

	@Override
	public List<MovieCastEntity> insert(List<MovieCastEntity> entity) throws RuntimeException {
		return movieCastDAO.saveAll(entity);
	}

	@Override
	public MovieCastEntity update(MovieCastEntity entity) throws RuntimeException {
		return movieCastDAO.save(entity);
	}

	@Override
	public void delete(Long id) {
		movieCastDAO.deleteById(id);
	}

	@Override
	public void delete(MovieCastEntity entity) {
		movieCastDAO.delete(entity);
	}

}
