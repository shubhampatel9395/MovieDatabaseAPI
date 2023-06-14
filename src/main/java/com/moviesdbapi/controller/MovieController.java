package com.moviesdbapi.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.moviesdbapi.service.IMovieCastService;
import com.moviesdbapi.service.IMovieService;
import com.moviesdbapi.service.IReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Movie")
public class MovieController {

	@Autowired
	IMovieService iMovieService;

	@Autowired
	IMovieCastService iMovieCastService;

	@Autowired
	IReviewService iMovieReviewService;

	@Operation(summary = "Get all movies", description = "Get all movies", responses = {
			@ApiResponse(responseCode = "200", description = "Operation success"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	@GetMapping("/movies")
	public ResponseEntity<List<MovieEntity>> getAllMovies() {
		return new ResponseEntity<List<MovieEntity>>(iMovieService.findAll(), HttpStatus.OK);
	}

	@Operation(summary = "Get a particular movie", description = "Get a particular movie", responses = {
			@ApiResponse(responseCode = "200", description = "Operation success"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	@GetMapping("/movies/{id}")
	public ResponseEntity<Map<String, Object>> getMovie(@PathVariable Long id) {
		Optional<MovieEntity> existing = iMovieService.findById(id);

		if (existing.isEmpty()) {
			throw new IdNotFoundException(id);
		}

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), existing.get(), "Record fetched successfully."), HttpStatus.OK);
	}

	@Operation(summary = "Add a movie", description = "Add a movie", responses = {
			@ApiResponse(responseCode = "200", description = "Operation success"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PostMapping("/movies")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> addMovie(@Valid @RequestBody MovieEntity MovieEntity)
			throws RuntimeException {
		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.CREATED.value(), iMovieService.insert(MovieEntity), "Movie Created Successfully."),
				HttpStatus.CREATED);
	}

	@Operation(summary = "Update a movie", description = "Update a movie", responses = {
			@ApiResponse(responseCode = "200", description = "Operation success"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PutMapping("/movies/{id}")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> updateMovie(@Valid @RequestBody MovieEntity updatedMovieEntity,
			@PathVariable Long id) throws RuntimeException {
		if (!(updatedMovieEntity.getMovieId().equals(id))) {
			return new ResponseEntity<>(ResponseEntityUtil.getRes("Error",
					"URI movie id and body movie id must be same. Please enter same movie id",
					HttpStatus.BAD_REQUEST.value()), HttpStatus.OK);
		}

		Optional<MovieEntity> existing = iMovieService.findById(id);

		if (existing.isEmpty()) {
			throw new IdNotFoundException(id);
		}

		MovieEntity existingMovieEntity = new MovieEntity();
		new ModelMapper().map(existing.get(), existingMovieEntity);

		existingMovieEntity.setTitle(updatedMovieEntity.getTitle());
		existingMovieEntity.setReleaseDate(updatedMovieEntity.getReleaseDate());
		existingMovieEntity.setDuration(updatedMovieEntity.getDuration());
		existingMovieEntity.setTagline(updatedMovieEntity.getTagline());
		existingMovieEntity.setOverview(updatedMovieEntity.getOverview());
		existingMovieEntity.setGenres(updatedMovieEntity.getGenres());
		existingMovieEntity.setLanguages(updatedMovieEntity.getLanguages());
		existingMovieEntity.setOriginCountry(updatedMovieEntity.getOriginCountry());
		existingMovieEntity.setCurrency(updatedMovieEntity.getCurrency());
		existingMovieEntity.setBudget(updatedMovieEntity.getBudget());
		existingMovieEntity.setRevenue(updatedMovieEntity.getRevenue());
		existingMovieEntity.setWebsiteURL(updatedMovieEntity.getWebsiteURL());
		existingMovieEntity.setFacebookPage(updatedMovieEntity.getFacebookPage());
		existingMovieEntity.setInstagramPage(updatedMovieEntity.getInstagramPage());
		existingMovieEntity.setTrailorLink(updatedMovieEntity.getTrailorLink());
		existingMovieEntity.setCertificate(updatedMovieEntity.getCertificate());
		existingMovieEntity.setIsActive(updatedMovieEntity.getIsActive());

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), iMovieService.update(existingMovieEntity), "Movie updated successfully."),
				HttpStatus.OK);
	}

	@Operation(summary = "Delete a movie", description = "Delete a movie", responses = {
			@ApiResponse(responseCode = "200", description = "Operation success"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@DeleteMapping("/movies/{id}")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> deleteMovie(@PathVariable Long id) {
		Optional<MovieEntity> existing = iMovieService.findById(id);

		if (existing.isEmpty()) {
			throw new IdNotFoundException(id);
		}
		MovieEntity returnEntity = new MovieEntity();
		new ModelMapper().map(existing.get(), returnEntity);

		iMovieService.delete(id);

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), returnEntity, "Record deleted and returned successfully."), HttpStatus.OK);
	}
}
