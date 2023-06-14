package com.moviesdbapi.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.moviesdbapi.core.ResponseEntityUtil;
import com.moviesdbapi.core.ValidList;
import com.moviesdbapi.dao.IEnuMovieCastTypeDAO;
import com.moviesdbapi.exception.IdNotFoundException;
import com.moviesdbapi.exception.InvalidMovieCastTypeException;
import com.moviesdbapi.exception.MessageConstants;
import com.moviesdbapi.model.EnuMovieCastTypeEntity;
import com.moviesdbapi.model.MovieCastEntity;
import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.dto.MovieCastCreateDTO;
import com.moviesdbapi.model.dto.MovieCastDTO;
import com.moviesdbapi.service.IMovieCastService;
import com.moviesdbapi.service.IMovieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/movies/{movieId}")
@Tag(name = "Movie Cast")
public class MovieCastController {
	@Autowired
	IMovieCastService iMovieCastService;

	@Autowired
	IMovieService iMovieService;

	@Autowired
	IEnuMovieCastTypeDAO movieCastTypeDAO;

	@Autowired
	ModelMapper modelMapper;

	public MovieEntity checkValidMovie(Long movieId) {
		MovieEntity movie = iMovieService.findOneByMovieId(movieId);

		if (movie == null) {
			throw new IdNotFoundException("movieId", movieId);
		}

		return movie;
	}

	@Operation(summary = "Get all cast of a movie", description = "Get all cast of a movie", responses = {
			@ApiResponse(responseCode = "200", description = "Operation success"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	@GetMapping("/cast")
	public ResponseEntity<List<MovieCastDTO>> getAllCast(@PathVariable Long movieId) {
		checkValidMovie(movieId);
		return new ResponseEntity<List<MovieCastDTO>>(iMovieCastService.findAllByMovieId(movieId), HttpStatus.OK);
	}

	@Operation(summary = "Get particular cast of a movie", description = "Get particular cast of a movie", responses = {
			@ApiResponse(responseCode = "200", description = "Operation success"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	@RequestMapping(method = RequestMethod.GET, value = "/cast/{castId}", headers = "type=id")
	public ResponseEntity<Map<String, Object>> getCast(@PathVariable Long movieId, @PathVariable Long castId) {
		MovieEntity movie = checkValidMovie(movieId);

		Optional<MovieCastEntity> existing = iMovieCastService.findOneByMovieAndMovieCastId(movie, castId);

		if (existing.isEmpty()) {
			throw new IdNotFoundException(castId, movie.getMovieId());
		}

		if (!(existing.get().getMovie().getMovieId().equals(movieId))) {
			return new ResponseEntity<>(
					ResponseEntityUtil.getRes(MessageConstants.SUCCESS_MESSAGE,
							"There exists no cast with id " + castId + " for the given movie.", HttpStatus.OK.value()),
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.OK.value(),
						modelMapper.map(existing.get(), MovieCastDTO.class), "Record fetched successfully."),
				HttpStatus.OK);
	}

	@Operation(summary = "Get all cast of particular type of a movie", description = "Get all cast of particular type of a movie", responses = {
			@ApiResponse(responseCode = "200", description = "Operation success"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	@RequestMapping(method = RequestMethod.GET, value = "/cast/{castType}", headers = "type=type")
	public ResponseEntity<Map<String, Object>> getCastByType(@PathVariable Long movieId,
			@PathVariable String castType) {
		checkValidMovie(movieId);

		EnuMovieCastTypeEntity castTypeEntity = movieCastTypeDAO.findOneByMovieCastType(castType);

		if (castTypeEntity == null) {
			throw new InvalidMovieCastTypeException();
		}

		List<MovieCastDTO> returnDTOs = iMovieCastService.findAllByCastTypeId(movieId,
				castTypeEntity.getMovieCastTypeId());

		if (returnDTOs.isEmpty()) {
			return new ResponseEntity<>(
					ResponseEntityUtil.getRes(MessageConstants.SUCCESS_MESSAGE,
							"There exists no cast for the given cast type and movie.", HttpStatus.OK.value()),
					HttpStatus.OK);
		}

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), returnDTOs, "Record(s) fetched successfully."), HttpStatus.OK);
	}

	@Operation(summary = "Add particular cast of a movie", description = "Add particular cast of a movie", responses = {
			@ApiResponse(responseCode = "200", description = "Operation success"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@RequestMapping(method = RequestMethod.POST, value = "/cast", headers = "action=individual")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> addCast(@PathVariable Long movieId,
			@Valid @RequestBody MovieCastCreateDTO castDTO) throws RuntimeException {
		ValidList<MovieCastCreateDTO> lst = new ValidList<>();
		lst.add(castDTO);
		return addAllCast(movieId, lst);
	}

	@Operation(summary = "Add multiple cast of a movie", description = "Add multiple cast of a movie", responses = {
			@ApiResponse(responseCode = "200", description = "Operation success"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PostMapping("/cast")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> addAllCast(@PathVariable Long movieId,
			@Valid @RequestBody ValidList<MovieCastCreateDTO> castDTOs) throws RuntimeException {
		MovieEntity movie = checkValidMovie(movieId);

		List<MovieCastEntity> castEntities = castDTOs.getList().stream()
				.map(obj -> modelMapper.map(obj, MovieCastEntity.class)).collect(Collectors.toList());
		castEntities.stream().forEach(obj -> {
			obj.setMovie(movie);
		});

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.CREATED.value(), iMovieCastService.insert(castEntities), "Record(s) Created Successfully."),
				HttpStatus.CREATED);
	}

	@Operation(summary = "Update particular cast of a movie", description = "Update particular cast of a movie", responses = {
			@ApiResponse(responseCode = "200", description = "Operation success"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PutMapping("/cast/{castId}")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> updateCast(@PathVariable Long movieId, @PathVariable Long castId,
			@Valid @RequestBody MovieCastCreateDTO updatedMovieCastDTO) throws RuntimeException {
		MovieEntity movie = checkValidMovie(movieId);

		Optional<MovieCastEntity> existing = iMovieCastService.findOneByMovieAndMovieCastId(movie, castId);

		if (existing.isEmpty()) {
			throw new IdNotFoundException(castId, movie.getMovieId());
		}

		if (!(existing.get().getMovie().getMovieId().equals(movieId))) {
			return new ResponseEntity<>(
					ResponseEntityUtil.getRes(MessageConstants.SUCCESS_MESSAGE,
							"There exists no cast with id " + castId + " for the given movie.", HttpStatus.OK.value()),
					HttpStatus.OK);
		}

		MovieCastEntity updatedMovieCastEntity = modelMapper.map(updatedMovieCastDTO, MovieCastEntity.class);
		MovieCastEntity existingMovieCastEntity = new MovieCastEntity();
		new ModelMapper().map(existing.get(), existingMovieCastEntity);

		existingMovieCastEntity.setCastType(updatedMovieCastEntity.getCastType());
		existingMovieCastEntity.setOriginalNames(updatedMovieCastEntity.getOriginalNames());
		existingMovieCastEntity.setMovieNames(updatedMovieCastEntity.getMovieNames());
		existingMovieCastEntity.setIsActive(updatedMovieCastEntity.getIsActive());

		return new ResponseEntity<>(
				ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.OK.value(),
						iMovieCastService.update(existingMovieCastEntity), "Record updated successfully."),
				HttpStatus.OK);
	}

	@Operation(summary = "Delete particular cast of a movie", description = "Delete particular cast of a movie", responses = {
			@ApiResponse(responseCode = "200", description = "Operation success"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@DeleteMapping("/cast/{castId}")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> deleteCast(@PathVariable Long movieId, @PathVariable Long castId) {
		MovieEntity movie = checkValidMovie(movieId);

		Optional<MovieCastEntity> existing = iMovieCastService.findOneByMovieAndMovieCastId(movie, castId);

		if (existing.isEmpty()) {
			throw new IdNotFoundException(castId, movie.getMovieId());
		}

		if (!(existing.get().getMovie().getMovieId().equals(movieId))) {
			return new ResponseEntity<>(
					ResponseEntityUtil.getRes(MessageConstants.SUCCESS_MESSAGE,
							"There exists no cast with id " + castId + " for the given movie.", HttpStatus.OK.value()),
					HttpStatus.OK);
		}

		MovieCastDTO returnDTO = modelMapper.map(existing.get(), MovieCastDTO.class);

		iMovieCastService.delete(castId);

		return new ResponseEntity<>(
				ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.OK.value(),
						modelMapper.map(returnDTO, MovieCastDTO.class), "Record deleted and returned successfully."),
				HttpStatus.OK);
	}

	@Operation(summary = "Delete all cast of a movie", description = "Delete all cast of a movie", responses = {
			@ApiResponse(responseCode = "200", description = "Operation success"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized access"),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@DeleteMapping("/cast")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> deleteAllCast(@PathVariable Long movieId) {
		MovieEntity movie = checkValidMovie(movieId);

		List<MovieCastEntity> existing = iMovieCastService.findAllByMovie(movie);

		if (existing.isEmpty()) {
			return new ResponseEntity<>(ResponseEntityUtil.getRes(MessageConstants.SUCCESS_MESSAGE,
					"There exists no cast for the given movie.", HttpStatus.OK.value()), HttpStatus.OK);
		}

		List<MovieCastDTO> returnDTOs = existing.stream().map(obj -> modelMapper.map(obj, MovieCastDTO.class))
				.collect(Collectors.toList());

		iMovieCastService.deleteAll(existing);

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), returnDTOs, "Record(s) deleted and returned successfully."), HttpStatus.OK);
	}
}
