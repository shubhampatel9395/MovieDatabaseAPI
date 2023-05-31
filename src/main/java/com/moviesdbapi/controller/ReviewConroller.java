package com.moviesdbapi.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.moviesdbapi.authentication.UserPrincipal;
import com.moviesdbapi.core.ResponseEntityUtil;
import com.moviesdbapi.core.ValidList;
import com.moviesdbapi.exception.IdNotFoundException;
import com.moviesdbapi.exception.InvalidMovieCastTypeException;
import com.moviesdbapi.exception.MessageConstants;
import com.moviesdbapi.model.EnuMovieCastTypeEntity;
import com.moviesdbapi.model.MovieCastEntity;
import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.ReviewEntity;
import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.model.dto.MovieCastCreateDTO;
import com.moviesdbapi.service.IMovieService;
import com.moviesdbapi.service.IReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/movies/{movieId}")
public class ReviewConroller {
	@Autowired
	IMovieService iMovieService;

	@Autowired
	IReviewService iReviewService;

	public MovieEntity checkValidMovie(Long movieId) {
		MovieEntity movie = iMovieService.findOneByMovieId(movieId);

		if (movie == null) {
			throw new IdNotFoundException("movieId", movieId);
		}

		return movie;
	}

	public UserDetailsEntity getCurrentUser() {
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return currentUser.getUser();
	}

	// TODO: Make a method to get all movies review

	@GetMapping("/review")
	public ResponseEntity<Map<String, Object>> getAllCast(@PathVariable Long movieId) {
		checkValidMovie(movieId);
		UserDetailsEntity currentUser = getCurrentUser();
		ReviewEntity review = iReviewService.findOneByMovieIdUserId(movieId, currentUser.getUserId());

		if (review == null) {
			return new ResponseEntity<>(
					ResponseEntityUtil.getRes(MessageConstants.SUCCESS_MESSAGE,
							"There is no review given by you for the given movie.", HttpStatus.OK.value()),
					HttpStatus.OK);
		}

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), review, "Record fetched successfully."), HttpStatus.OK);
	}

	@PostMapping("/review")
	public ResponseEntity<Map<String, Object>> addAllCast(@PathVariable Long movieId,
			@Valid @RequestBody ReviewEntity newReview) throws RuntimeException {
		MovieEntity movie = checkValidMovie(movieId);
		UserDetailsEntity currentUser = getCurrentUser();
		ReviewEntity review = iReviewService.findOneByMovieIdUserId(movieId, currentUser.getUserId());

		if (review != null) {
			if (newReview.getReviewTitle() != null) {
				review.setReviewTitle(newReview.getReviewTitle());
			}

			if (newReview.getReviewContent() != null) {
				review.setReviewContent(newReview.getReviewContent());
			}

			return new ResponseEntity<>(
					ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.OK.value(),
							iReviewService.update(review),
							"There exists a review given by you for the given movie. \nUpdating your review."),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
					HttpStatus.CREATED.value(), iReviewService.insert(newReview), "Record Created Successfully."),
					HttpStatus.CREATED);
		}
	}

	@PutMapping("/review")
	public ResponseEntity<Map<String, Object>> updateCast(@PathVariable Long movieId, @PathVariable Long castId,
			@Valid @RequestBody ReviewEntity updatedReview) throws RuntimeException {
		checkValidMovie(movieId);
		UserDetailsEntity currentUser = getCurrentUser();
		ReviewEntity review = iReviewService.findOneByMovieIdUserId(movieId, currentUser.getUserId());

		if (review == null) {
			return new ResponseEntity<>(
					ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.CREATED.value(),
							iReviewService.insert(updatedReview),
							"There is no review given by you for the given movie. \nAdding your review."),
					HttpStatus.CREATED);
		} else {
			if (updatedReview.getReviewTitle() != null) {
				review.setReviewTitle(updatedReview.getReviewTitle());
			}

			if (updatedReview.getReviewContent() != null) {
				review.setReviewContent(updatedReview.getReviewContent());
			}

			return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
					HttpStatus.OK.value(), iReviewService.update(review), "Record updated successfully."),
					HttpStatus.OK);
		}
	}

	@DeleteMapping("/review")
	public ResponseEntity<Map<String, Object>> deleteAllCast(@PathVariable Long movieId) {
		checkValidMovie(movieId);
		UserDetailsEntity currentUser = getCurrentUser();
		ReviewEntity review = iReviewService.findOneByMovieIdUserId(movieId, currentUser.getUserId());

		if (review == null) {
			return new ResponseEntity<>(
					ResponseEntityUtil.getRes(MessageConstants.SUCCESS_MESSAGE,
							"There is no review given by you for the given movie.", HttpStatus.OK.value()),
					HttpStatus.OK);
		}

		ReviewEntity returnEntity = new ModelMapper().map(review, ReviewEntity.class);

		iReviewService.delete(review);

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), returnEntity, "Record deleted and returned successfully."), HttpStatus.OK);
	}
}
