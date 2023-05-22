package com.moviesdbapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviesdbapi.dao.IMovieDAO;
import com.moviesdbapi.model.MovieEntity;

@RestController
@RequestMapping("/api/v1")
public class MovieController {
	
	@Autowired
	IMovieDAO iMovieDAO;
	
	@GetMapping("moviesave")
	public MovieEntity test(@RequestBody MovieEntity movieEntity) {
		return iMovieDAO.save(movieEntity);
	}
}
