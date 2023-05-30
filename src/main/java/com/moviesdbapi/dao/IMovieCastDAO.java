package com.moviesdbapi.dao;

import com.moviesdbapi.model.MovieCastEntity;

public interface IMovieCastDAO extends IJPARepository<MovieCastEntity, Long>, CustomizedMovieCastDAO {
	
}