package com.moviesdbapi.dao;

import java.util.List;
import java.util.Optional;

import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.ReviewEntity;
import com.moviesdbapi.model.UserDetailsEntity;

public interface IReviewDAO extends IJPARepository<ReviewEntity, Long>, CustomizedReviewDAO {
	public List<ReviewEntity> findByMovie(MovieEntity movie);

	public ReviewEntity findOneByMovieAndUser(MovieEntity movie, UserDetailsEntity user);
	
	public Optional<ReviewEntity> findOneByMovieAndReviewId(MovieEntity movie, Long reviewId);
}
