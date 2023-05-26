package com.moviesdbapi.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.moviesdbapi.exception.IdNotFoundException;
import com.moviesdbapi.exception.MessageConstants;
import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.service.IMovieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class MovieController {
	
	@Autowired
	IMovieService iMovieService;
	

	@GetMapping("/movies")
	public ResponseEntity<List<MovieEntity>> getAllUsers() {
		return new ResponseEntity<List<MovieEntity>>(iMovieService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/movies/{id}")
	public Map<String, Object> getEmployee(@PathVariable Long id) {
		Optional<MovieEntity> existingUser = iMovieService.findById(id);

		if (existingUser.isEmpty()) {
			throw new IdNotFoundException(id);
		}

		return ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.OK.value(),
				existingUser.get(), "Record fetched successfully.");
	}

	@PostMapping("/movies")
	public Map<String, Object> addUser(@Valid @RequestBody MovieEntity MovieEntity)
			throws RuntimeException {
		return ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.CREATED.value(),
				iMovieService.insert(MovieEntity), "User Created Successfully.");
	}

	@PutMapping("/movies/{id}")
	public Map<String, Object> updateUser(@Valid @RequestBody MovieEntity MovieEntity,
			@PathVariable Long id) throws RuntimeException {
		Optional<MovieEntity> existingUser = iMovieService.findById(id);

		if (existingUser.isEmpty()) {
			throw new IdNotFoundException(id);
		}
		
		// TODO : Update Movie

//		return ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.OK.value(),
//				iMovieService.update(existingEntity), "User updated successfully.");
		return null;
	}

	@DeleteMapping("/movies/{id}")
	public Map<String, Object> deleteEmployee(@PathVariable Long id) {
		Optional<MovieEntity> existingUser = iMovieService.findById(id);

		if (existingUser.isEmpty()) {
			throw new IdNotFoundException(id);
		}
		MovieEntity returnEntity = existingUser.get();

		iMovieService.delete(id);

		return ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.OK.value(),
				returnEntity, "Record deleted and returned successfully.");
	}
}
