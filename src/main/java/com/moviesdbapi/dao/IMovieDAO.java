package com.moviesdbapi.dao;

import java.time.LocalDate;

import com.moviesdbapi.model.MovieEntity;

public interface IMovieDAO extends IJPARepository<MovieEntity, Long>, CustomizedMovieDAO {
	public MovieEntity findByTitleAndReleaseDate(String title, LocalDate releaseDate);

	public MovieEntity findOneByMovieId(Long id);
}
