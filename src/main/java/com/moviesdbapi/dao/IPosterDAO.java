package com.moviesdbapi.dao;

import java.util.List;
import java.util.Optional;

import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.PosterEntity;

public interface IPosterDAO extends IJPARepository<PosterEntity, Long>, CustomizedPosterDAO {
	public List<PosterEntity> findAllByMovie(MovieEntity movie);
	public  Optional<PosterEntity> findOneByMovieAndPosterId(MovieEntity movie, Long posterId);
}