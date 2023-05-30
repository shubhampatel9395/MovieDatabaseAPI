package com.moviesdbapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviesdbapi.core.ResponseEntityUtil;
import com.moviesdbapi.core.ValidList;
import com.moviesdbapi.exception.IdNotFoundException;
import com.moviesdbapi.exception.MessageConstants;
import com.moviesdbapi.model.MovieCastEntity;
import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.dto.MovieCastCreateDTO;
import com.moviesdbapi.model.dto.MovieCastDTO;
import com.moviesdbapi.service.IMovieCastService;
import com.moviesdbapi.service.IMovieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/movies/{movieId}")
public class MovieCastController {
	@Autowired
	IMovieCastService iMovieCastService;

	@Autowired
	IMovieService iMovieService;
	
	@Autowired
	ModelMapper modelMapper;

	public MovieEntity checkValidMovie(Long movieId) {
		MovieEntity movie = iMovieService.findOneByMovieId(movieId);

		if (movie == null) {
			throw new IdNotFoundException("movieId", movieId);
		}
		
		return movie;
	}
	
	@GetMapping("/cast")
	public ResponseEntity<List<MovieCastDTO>> getAllCast(@PathVariable Long movieId) {
		checkValidMovie(movieId);
		return new ResponseEntity<List<MovieCastDTO>>(iMovieCastService.findAllByMovieId(movieId), HttpStatus.OK);
	}

	@GetMapping("/cast/{castId}")
	public ResponseEntity<Map<String, Object>> getCast(@PathVariable Long movieId, @PathVariable Long castId) {
		checkValidMovie(movieId);
		
		Optional<MovieCastEntity> existing = iMovieCastService.findById(castId);

		if (existing.isEmpty()) {
			throw new IdNotFoundException(castId);
		}

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), existing.get(), "Record fetched successfully."), HttpStatus.OK);
	}

	@PostMapping("/cast")
	public ResponseEntity<Map<String, Object>> addCast(@PathVariable Long movieId,
			@Valid @RequestBody ValidList<MovieCastCreateDTO> castDTO) throws RuntimeException {
		MovieEntity movie = checkValidMovie(movieId);
		
		TypeMap<MovieCastCreateDTO[], MovieCastEntity[]> propertyMapper = modelMapper.createTypeMap(MovieCastCreateDTO[].class, MovieCastEntity[].class);
		propertyMapper.addMappings(mapper -> {
			mapper.map();
		});
		
		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.CREATED.value(), iMovieCastService.insert(castEntity), "Record(s) Created Successfully."),
				HttpStatus.CREATED);
	}

	@PutMapping("/cast/{castId}")
	public ResponseEntity<Map<String, Object>> updateCast(@PathVariable Long movieId,
			@Valid @RequestBody MovieCastEntity updatedMovieCastEntity, @PathVariable Long id) throws RuntimeException {
		Optional<MovieCastEntity> existingUser = iMovieCastService.findById(id);

		if (existingUser.isEmpty()) {
			throw new IdNotFoundException(id);
		}

		// TODO: Update Cast

//		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
//				HttpStatus.OK.value(), iMovieCastService.update(existingMovieCastEntity), "Record updated successfully."),
//				HttpStatus.OK);
		return null;
	}

	@DeleteMapping("/cast/{castId}")
	public ResponseEntity<Map<String, Object>> deleteCast(@PathVariable Long movieId, @PathVariable Long id) {
		Optional<MovieCastEntity> existingUser = iMovieCastService.findById(id);

		if (existingUser.isEmpty()) {
			throw new IdNotFoundException(id);
		}
		MovieCastEntity returnEntity = new MovieCastEntity();
		new ModelMapper().map(existingUser.get(), returnEntity);

		iMovieCastService.delete(id);

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), returnEntity, "Record deleted and returned successfully."), HttpStatus.OK);
	}

	@DeleteMapping("/cast")
	public ResponseEntity<Map<String, Object>> deleteAllCast(@PathVariable Long movieId, @PathVariable Long id) {
		Optional<MovieCastEntity> existingUser = iMovieCastService.findById(id);

		if (existingUser.isEmpty()) {
			throw new IdNotFoundException(id);
		}
		MovieCastEntity returnEntity = new MovieCastEntity();
		new ModelMapper().map(existingUser.get(), returnEntity);

		iMovieCastService.delete(id);

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), returnEntity, "Record deleted and returned successfully."), HttpStatus.OK);
	}
}
