package com.moviesdbapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviesdbapi.dao.IMovieDAO;
import com.moviesdbapi.dao.IReviewDAO;
import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.ReviewEntity;
import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.model.dto.ReviewDTO;
import com.moviesdbapi.service.IReviewService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReviewService implements IReviewService {
	@Autowired
	IReviewDAO reviewDAO;
	
	@Autowired
	IMovieDAO movieDAO;

	@Override
	public List<ReviewEntity> findAll() {
		return reviewDAO.findAll();
	}

	@Override
	public Optional<ReviewEntity> findById(Long id) {
		return reviewDAO.findById(id);
	}

	@Override
	public ReviewDTO findOneByMovieIdUserId(Long movieId, Long userId) {
		return reviewDAO.findOneByMovieIdUserId(movieId, userId);
	}

	@Override
	public List<ReviewEntity> findByMovie(MovieEntity movie) {
		return reviewDAO.findByMovie(movie);
	}

	@Override
	public List<ReviewDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return reviewDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<ReviewDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return reviewDAO.findByNamedParameters(paramSource);
	}
	
	public void updateMovieAverageRatings(Long id) {
		movieDAO.updateAvgRatings(id);
	}

	@Override
	public ReviewEntity insert(ReviewEntity entity) throws RuntimeException {
		updateMovieAverageRatings(entity.getMovie().getMovieId());
		return reviewDAO.save(entity);
	}

	@Override
	public ReviewEntity update(ReviewEntity entity) throws RuntimeException {
		updateMovieAverageRatings(entity.getMovie().getMovieId());
		return reviewDAO.save(entity);
	}

	@Override
	public void delete(Long id) {
		reviewDAO.deleteById(id);
	}

	@Override
	public void delete(ReviewEntity entity) {
		reviewDAO.delete(entity);
	}

	@Override
	public ReviewEntity findOneByMovieAndUser(MovieEntity movie, UserDetailsEntity user) {
		return reviewDAO.findOneByMovieAndUser(movie, user);
	}

	@Override
	public void deleteAll(List<ReviewEntity> entities) {
		reviewDAO.deleteAll(entities);
	}
}
