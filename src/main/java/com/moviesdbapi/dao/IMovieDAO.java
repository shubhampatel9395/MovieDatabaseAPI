package com.moviesdbapi.dao;

import java.sql.Date;

import com.moviesdbapi.model.MovieEntity;

public interface IMovieDAO extends IJPARepository<MovieEntity, Long>, CustomizedMovieDAO {
	public MovieEntity findByTitleAndReleaseDate(String title, Date releaseDate);
}
