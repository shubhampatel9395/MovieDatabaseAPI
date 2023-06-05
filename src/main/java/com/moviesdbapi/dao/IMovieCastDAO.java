package com.moviesdbapi.dao;

import java.util.List;
import java.util.Optional;

import com.moviesdbapi.model.MovieCastEntity;
import com.moviesdbapi.model.MovieEntity;

public interface IMovieCastDAO extends IJPARepository<MovieCastEntity, Long>, CustomizedMovieCastDAO {
	public List<MovieCastEntity> findAllByMovie(MovieEntity movie);
	public Optional<MovieCastEntity> findOneByMovieAndMovieCastId(MovieEntity movie, Long castId);
}