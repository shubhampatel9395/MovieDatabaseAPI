package com.moviesdbapi.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MimeTypeUtils;
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
import com.moviesdbapi.exception.AddFileException;
import com.moviesdbapi.exception.GetFileException;
import com.moviesdbapi.exception.IdNotFoundException;
import com.moviesdbapi.exception.InvalidFilePathException;
import com.moviesdbapi.exception.MessageConstants;
import com.moviesdbapi.exception.NotAllowedImageFileExtensionException;
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

	public static List<String> allowedImageFileExtensions = Arrays
			.asList(new String[] { MimeTypeUtils.IMAGE_JPEG_VALUE, MimeTypeUtils.IMAGE_PNG_VALUE, "image/jpg" });

	public MovieEntity checkValidMovie(Long movieId) {
		MovieEntity movie = iMovieService.findOneByMovieId(movieId);

		if (movie == null) {
			throw new IdNotFoundException("movieId", movieId);
		}

		return movie;
	}

	public Long addPoster(MovieEntity movie, MultipartFile poster) throws RuntimeException {
		if (!poster.isEmpty()) {
			PosterEntity obj = new PosterEntity();

			try {
				String fileName = StringUtils.cleanPath(poster.getOriginalFilename());

				if (fileName.contains("..")) {
					throw new InvalidFilePathException(fileName);
				}

				if (!allowedImageFileExtensions.contains(poster.getContentType())) {
					throw new NotAllowedImageFileExtensionException();
				}

				obj.setType(poster.getContentType());
				obj.setPoster(new SerialBlob(poster.getBytes()));
				obj.setMovie(movie);
				movie.addPoster(obj);
			} catch (SQLException | IOException e) {
				throw new AddFileException();
			}

			return iPosterService.insert(obj).getPosterId();
		} else {
			return -1L;
		}
	}

	public byte[] getByteImage(Blob img) {
		byte[] imageContent = null;

		try {
			imageContent = img.getBytes(1, (int) img.length());
		} catch (SQLException e) {
			throw new GetFileException();
		}

		return imageContent;
	}

	@GetMapping("/posters")
	public ResponseEntity<List<PosterEntity>> getAllPosters(@PathVariable Long movieId) {
		MovieEntity movie = checkValidMovie(movieId);
		return new ResponseEntity<List<PosterEntity>>(iPosterService.findAllByMovie(movie), HttpStatus.OK);
	}

	@GetMapping("/posters/{posterId}")
	public ResponseEntity<Resource> getPoster(@PathVariable Long movieId, @PathVariable Long posterId) {
		MovieEntity movie = checkValidMovie(movieId);

		Optional<PosterEntity> posterEntity = iPosterService.findOneByMovieAndPosterId(movie, posterId);

		if (posterEntity.isEmpty()) {
			throw new IdNotFoundException(posterId, movie.getMovieId());
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(posterEntity.get().getType()))
				.body(new ByteArrayResource(getByteImage(posterEntity.get().getPoster())));
	}

	@RequestMapping(method = RequestMethod.POST, value = "/posters", headers = "action=individual")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<?> addSinglePoster(@PathVariable Long movieId,
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

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(posterEntity.get().getType()))
					.body(new ByteArrayResource(getByteImage(posterEntity.get().getPoster())));
		}
	}

	@PostMapping("/posters")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<?> addAllPoster(@PathVariable Long movieId,
			@Valid @RequestParam("posters") MultipartFile[] posters) throws RuntimeException {
		MovieEntity movie = checkValidMovie(movieId);

		if (posters.length == 0) {
			return new ResponseEntity<>(ResponseEntityUtil.getErrorResponse("Posters must not be null or empty.",
					HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}

		for (MultipartFile poster : posters) {
			Long posterId = addPoster(movie, poster);

			if (posterId.equals(-1L)) {
				return new ResponseEntity<>(ResponseEntityUtil.getErrorResponse("Poster must not be null or empty.",
						HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.CREATED.value(), "All poster(s) uploded successfully.", "Record(s) Created Successfully."),
				HttpStatus.CREATED);
	}

	@DeleteMapping("/posters/{posterId}")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<?> deletePoster(@PathVariable Long movieId, @PathVariable Long posterId) {
		MovieEntity movie = checkValidMovie(movieId);

		Optional<PosterEntity> posterEntity = iPosterService.findOneByMovieAndPosterId(movie, posterId);

		if (posterEntity.isEmpty()) {
			throw new IdNotFoundException(posterId, movie.getMovieId());
		}

		iPosterService.delete(posterEntity.get());

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), "Poster removed successfully.", "Record Deleted Successfully."), HttpStatus.OK);
	}

	@DeleteMapping("/posters")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<?> deleteAllPoster(@PathVariable Long movieId) {
		MovieEntity movie = checkValidMovie(movieId);
		List<PosterEntity> posters = iPosterService.findAllByMovie(movie);

		if (posters.isEmpty()) {
			return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
					HttpStatus.OK.value(), "There are no posters for the given movie.", "Operation Success"),
					HttpStatus.OK);
		}

		iPosterService.deleteAll(posters);
		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), "All Poster(s) removed successfully.", "Record(s) Deleted Successfully."),
				HttpStatus.OK);
	}
}