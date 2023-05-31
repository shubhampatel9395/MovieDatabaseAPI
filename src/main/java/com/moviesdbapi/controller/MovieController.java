package com.moviesdbapi.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
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
	public ResponseEntity<List<MovieEntity>> getAllMovies() {
		return new ResponseEntity<List<MovieEntity>>(iMovieService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/movies/{id}")
	public ResponseEntity<Map<String, Object>> getMovie(@PathVariable Long id) {
		Optional<MovieEntity> existing = iMovieService.findById(id);

		if (existing.isEmpty()) {
			throw new IdNotFoundException(id);
		}

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), existing.get(), "Record fetched successfully."), HttpStatus.OK);
	}

	@PostMapping("/movies")
	public ResponseEntity<Map<String, Object>> addMovie(@Valid @RequestBody MovieEntity MovieEntity)
			throws RuntimeException {
		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.CREATED.value(), iMovieService.insert(MovieEntity), "Movie Created Successfully."),
				HttpStatus.CREATED);
	}

	@PutMapping("/movies/{id}")
	public ResponseEntity<Map<String, Object>> updateMovie(@Valid @RequestBody MovieEntity updatedMovieEntity,
			@PathVariable Long id) throws RuntimeException {
		
//		if (!(updatedMovieEntity.getMovieId().equals(id))) {
//			return new ResponseEntity<>(ResponseEntityUtil.getRes("Error",
//					"URI movie id and body movie id must be same. Please enter same movie id",
//					HttpStatus.BAD_REQUEST.value()), HttpStatus.OK);
//		}
		
		Optional<MovieEntity> existing = iMovieService.findById(id);

		if (existing.isEmpty()) {
			throw new IdNotFoundException(id);
		}

		MovieEntity existingMovieEntity = new MovieEntity();
		new ModelMapper().map(existing.get(), existingMovieEntity);

		if (!(existingMovieEntity.getTitle().equals(updatedMovieEntity.getTitle()))) {
			existingMovieEntity.setTitle(updatedMovieEntity.getTitle());
		}

		if (!(existingMovieEntity.getReleaseDate().equals(updatedMovieEntity.getReleaseDate()))) {
			existingMovieEntity.setReleaseDate(updatedMovieEntity.getReleaseDate());
		}

		if (!(existingMovieEntity.getDuration().equals(updatedMovieEntity.getDuration()))) {
			existingMovieEntity.setDuration(updatedMovieEntity.getDuration());
		}

		if (updatedMovieEntity.getTagline() != null) {
			existingMovieEntity.setTagline(updatedMovieEntity.getTagline());
		}

		if (!(existingMovieEntity.getOverview().equals(updatedMovieEntity.getOverview()))) {
			existingMovieEntity.setOverview(updatedMovieEntity.getOverview());
		}

		if (!(existingMovieEntity.getGenres().equals(updatedMovieEntity.getGenres()))) {
			existingMovieEntity.setGenres(updatedMovieEntity.getGenres());
		}

		if (!(existingMovieEntity.getLanguages().equals(updatedMovieEntity.getLanguages()))) {
			existingMovieEntity.setLanguages(updatedMovieEntity.getLanguages());
		}

		if (updatedMovieEntity.getOriginCountry() != null) {
			existingMovieEntity.setOriginCountry(updatedMovieEntity.getOriginCountry());
		}

		if (updatedMovieEntity.getCurrency() != null) {
			existingMovieEntity.setCurrency(updatedMovieEntity.getCurrency());
		}

		if (updatedMovieEntity.getBudget() != 0)
			existingMovieEntity.setBudget(updatedMovieEntity.getBudget());

		if (updatedMovieEntity.getRevenue() != 0)
			existingMovieEntity.setRevenue(updatedMovieEntity.getRevenue());

		if (updatedMovieEntity.getWebsiteURL() != null)
			existingMovieEntity.setWebsiteURL(updatedMovieEntity.getWebsiteURL());

		if (updatedMovieEntity.getFacebookPage() != null)
			existingMovieEntity.setFacebookPage(updatedMovieEntity.getFacebookPage());

		if (updatedMovieEntity.getInstagramPage() != null)
			existingMovieEntity.setInstagramPage(updatedMovieEntity.getInstagramPage());

		if (updatedMovieEntity.getTrailorLink() != null)
			existingMovieEntity.setTrailorLink(updatedMovieEntity.getTrailorLink());

		if (updatedMovieEntity.getCertificate() != null)
			existingMovieEntity.setCertificate(updatedMovieEntity.getCertificate());

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), iMovieService.update(existingMovieEntity), "Movie updated successfully."),
				HttpStatus.OK);
	}

	@DeleteMapping("/movies/{id}")
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
