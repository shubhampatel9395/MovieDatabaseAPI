package com.moviesdbapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviesdbapi.dao.IPosterDAO;
import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.PosterEntity;
import com.moviesdbapi.service.IPosterService;

@Service
@Transactional(rollbackFor = Exception.class)
public class PosterService implements IPosterService {
	
	@Autowired
	IPosterDAO posterDAO;

	@Override
	public List<PosterEntity> findAll() {
		return posterDAO.findAll();
	}

	@Override
	public Optional<PosterEntity> findById(Long id) {
		return posterDAO.findById(id);
	}

	@Override
	public List<PosterEntity> findAllByMovie(MovieEntity movie) {
		return posterDAO.findAllByMovie(movie);
	}

	@Override
	public List<PosterEntity> findByFieldValue(String fieldName, Object fieldValue) {
		return findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<PosterEntity> findByNamedParameters(MapSqlParameterSource paramSource) {
		return posterDAO.findByNamedParameters(paramSource);
	}

	@Override
	public List<PosterEntity> insert(List<PosterEntity> entity) throws RuntimeException {
		return posterDAO.saveAll(entity);
	}

	@Override
	public void delete(Long id) {
		posterDAO.deleteById(id);
	}

	@Override
	public void delete(PosterEntity entity) {
		posterDAO.delete(entity);
	}

	@Override
	public void deleteAll(List<PosterEntity> entities) {
		posterDAO.deleteAll(entities);
	}

	@Override
	public PosterEntity insert(PosterEntity entity) throws RuntimeException {
		return posterDAO.save(entity);
	}

	@Override
	public  Optional<PosterEntity> findOneByMovieAndPosterId(MovieEntity movie, Long posterId) {
		return posterDAO.findOneByMovieAndPosterId(movie, posterId);
	}

}
