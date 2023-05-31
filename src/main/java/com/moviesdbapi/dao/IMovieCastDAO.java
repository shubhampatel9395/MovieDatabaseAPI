package com.moviesdbapi.dao;

import java.util.List;

import com.moviesdbapi.model.MovieCastEntity;
import com.moviesdbapi.model.MovieEntity;

public interface IMovieCastDAO extends IJPARepository<MovieCastEntity, Long>, CustomizedMovieCastDAO {
	public List<MovieCastEntity> findAllByMovie(MovieEntity movie);
}