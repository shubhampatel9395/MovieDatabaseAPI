package com.moviesdbapi.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.moviesdbapi.core.ResponseEntityUtil;
import com.moviesdbapi.exception.IdNotFoundException;
import com.moviesdbapi.exception.MessageConstants;
import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.PosterEntity;
import com.moviesdbapi.service.IMovieService;
import com.moviesdbapi.service.IPosterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/movies/{movieId}")
public class MoviePosterController {

	@Autowired
	IMovieService iMovieService;

	@Autowired
	IPosterService iPosterService;

	public MovieEntity checkValidMovie(Long movieId) {
		MovieEntity movie = iMovieService.findOneByMovieId(movieId);

		if (movie == null) {
			throw new IdNotFoundException("movieId", movieId);
		}

		return movie;
	}

	@GetMapping("/posters")
	@PreAuthorize(value = "hasAnyAuthority('User','Admin')")
	public ResponseEntity<List<PosterEntity>> getAllCast(@PathVariable Long movieId) {
		MovieEntity movie = checkValidMovie(movieId);
		return new ResponseEntity<List<PosterEntity>>(iPosterService.findAllByMovie(movie), HttpStatus.OK);
	}

	@GetMapping("/posters/{posterId}")
	@PreAuthorize(value = "hasAnyAuthority('User','Admin')")
	public ResponseEntity<Map<String, Object>> getCast(@PathVariable Long movieId, @PathVariable Long posterId) {
		checkValidMovie(movieId);

		Optional<PosterEntity> posterEntity = iPosterService.findById(posterId);

		if (posterEntity.isEmpty()) {
			throw new IdNotFoundException(posterId);
		}

		Blob posterImg = posterEntity.get().getPoster();

		byte[] imageContent = null;

		try {
			imageContent = posterImg.getBytes(1, (int) posterImg.length());
		} catch (SQLException e) {
			return new ResponseEntity<>(
					ResponseEntityUtil.getErrorResponse("Error while fetching poster.", HttpStatus.OK.value()),
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.CREATED.value(),
						ResponseEntity.ok().contentType(MediaType.parseMediaType(posterEntity.get().getType()))
								.body(new ByteArrayResource(imageContent)),
						"Record fetched successfully."),
				HttpStatus.CREATED);
	}

	public Long addPoster(MovieEntity movie, MultipartFile poster) throws RuntimeException {
		if (!poster.isEmpty()) {
			PosterEntity obj = new PosterEntity();

			try {
				String fileName = StringUtils.cleanPath(poster.getOriginalFilename());

				if (fileName.contains("..")) {
					throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
				}

				obj.setType(poster.getContentType());
				obj.setPoster(new SerialBlob(poster.getBytes()));
				obj.setMovie(movie);
				movie.addPoster(obj);
			} catch (SQLException | IOException e) {
				throw new RuntimeException("Error while adding poster.");
			}

			return iPosterService.insert(obj).getPosterId();
		} else {
			return -1L;
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/posters", headers = "action=individual")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<?> addCast(@PathVariable Long movieId,
			@Valid @RequestParam("poster") MultipartFile poster) throws RuntimeException {
		MovieEntity movie = checkValidMovie(movieId);
		Long posterId = addPoster(movie, poster);

		if (posterId.equals(-1L)) {
			return new ResponseEntity<>(ResponseEntityUtil.getErrorResponse("Poster must not be null or empty.",
					HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		} else {
			Optional<PosterEntity> posterEntity = iPosterService.findById(posterId);

			if (posterEntity.isEmpty()) {
				throw new IdNotFoundException(posterId);
			}

			Blob posterImg = posterEntity.get().getPoster();

			byte[] imageContent = null;

			try {
				imageContent = posterImg.getBytes(1, (int) posterImg.length());
			} catch (SQLException e) {
				return new ResponseEntity<>(
						ResponseEntityUtil.getErrorResponse("Error while fetching poster.", HttpStatus.OK.value()),
						HttpStatus.OK);
			}
			
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(posterEntity.get().getType()))
			.body(new ByteArrayResource(imageContent));
		}
	}

	@PostMapping("/posters")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> addAllCast(@PathVariable Long movieId,
			@Valid @RequestParam("posters") MultipartFile[] posters) throws RuntimeException {
		MovieEntity movie = checkValidMovie(movieId);

		if (posters.length != 0) {
			return new ResponseEntity<>(ResponseEntityUtil.getErrorResponse("Posters must not be null or empty.",
					HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}

		List<ResponseEntity<Resource>> returnPosters = new ArrayList<>();
		for (MultipartFile poster : posters) {
			Long posterId = addPoster(movie, poster);

			if (posterId.equals(-1L)) {
				return new ResponseEntity<>(ResponseEntityUtil.getErrorResponse("Poster must not be null or empty.",
						HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
			} else {
				Optional<PosterEntity> posterEntity = iPosterService.findById(posterId);

				if (posterEntity.isEmpty()) {
					throw new IdNotFoundException(posterId);
				}

				Blob posterImg = posterEntity.get().getPoster();

				byte[] imageContent = null;

				try {
					imageContent = posterImg.getBytes(1, (int) posterImg.length());
				} catch (SQLException e) {
					return new ResponseEntity<>(
							ResponseEntityUtil.getErrorResponse("Error while fetching poster.", HttpStatus.OK.value()),
							HttpStatus.OK);
				}

				returnPosters
						.add(ResponseEntity.ok().contentType(MediaType.parseMediaType(posterEntity.get().getType()))
								.body(new ByteArrayResource(imageContent)));
			}
		}

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.CREATED.value(), returnPosters, "Record Created Successfully."), HttpStatus.CREATED);
	}

	@DeleteMapping("/posters/{posterId}")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> deleteCast(@PathVariable Long movieId, @PathVariable Long posterId) {
		MovieEntity movie = checkValidMovie(movieId);
		return null;
	}

	@DeleteMapping("/posters")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> deleteAllCast(@PathVariable Long movieId) {
		MovieEntity movie = checkValidMovie(movieId);
		return null;
	}
}