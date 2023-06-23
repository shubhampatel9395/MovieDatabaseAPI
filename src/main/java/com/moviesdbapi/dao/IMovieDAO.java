package com.moviesdbapi.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import com.moviesdbapi.model.MovieEntity;

public interface IMovieDAO extends IJPARepository<MovieEntity, Long>, CustomizedMovieDAO {
	public MovieEntity findByTitleAndReleaseDate(String title, LocalDate releaseDate);

	public MovieEntity findOneByMovieId(Long id);
	
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE moviesdb.mst_movie SET mst_movie.avgRatings = (SELECT AVG(rating) from moviesdb.mst_review where movieId=?1) where movieId=?1")
	public void updateAvgRatings(Long id);
	
	@Procedure(procedureName = "updateMoviesAvgReview")
	public void updateAvgRatingsOnUserDeletion();
}
