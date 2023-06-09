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
		ReviewEntity ent = reviewDAO.save(entity);
		updateMovieAverageRatings(entity.getMovie().getMovieId());
		return ent;
	}

	@Override
	public ReviewEntity update(ReviewEntity entity) throws RuntimeException {
		ReviewEntity ent = reviewDAO.save(entity);
		updateMovieAverageRatings(entity.getMovie().getMovieId());
		return ent;
	}

	@Override
	public void delete(Long id) {
		Long movieId = findById(id).get().getMovie().getMovieId();
		reviewDAO.deleteById(id);
		updateMovieAverageRatings(movieId);
	}

	@Override
	public void delete(ReviewEntity entity) {
		reviewDAO.delete(entity);
		updateMovieAverageRatings(entity.getMovie().getMovieId());
	}

	@Override
	public ReviewEntity findOneByMovieAndUser(MovieEntity movie, UserDetailsEntity user) {
		return reviewDAO.findOneByMovieAndUser(movie, user);
	}

	@Override
	public void deleteAll(List<ReviewEntity> entities) {
		if(!entities.isEmpty()) {
			Long movieId = entities.get(0).getMovie().getMovieId();
			reviewDAO.deleteAll(entities);
			updateMovieAverageRatings(movieId);
		}
	}

	@Override
	public Optional<ReviewEntity> findOneByMovieAndReviewId(MovieEntity movie, Long reviewId) {
		return reviewDAO.findOneByMovieAndReviewId(movie, reviewId);
	}
}
