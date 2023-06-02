package com.moviesdbapi.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;

import com.moviesdbapi.authentication.UserPrincipal;
import com.moviesdbapi.core.ResponseEntityUtil;
import com.moviesdbapi.exception.IdNotFoundException;
import com.moviesdbapi.exception.MessageConstants;
import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.ReviewEntity;
import com.moviesdbapi.model.UserDetailsEntity;
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

	@GetMapping("/reviews")
	public ResponseEntity<Map<String, Object>> getAllReviews(@PathVariable Long movieId) {
		MovieEntity movie = checkValidMovie(movieId);
		List<ReviewEntity> reviews = iReviewService.findByMovie(movie);

		if (reviews.isEmpty()) {
			return new ResponseEntity<>(ResponseEntityUtil.getRes(MessageConstants.SUCCESS_MESSAGE,
					"There are no reviews given for the movie.", HttpStatus.OK.value()), HttpStatus.OK);
		}

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), reviews, "Record(s) fetched successfully."), HttpStatus.OK);
	}

	@GetMapping("/review")
	public ResponseEntity<Map<String, Object>> getReview(@PathVariable Long movieId) {
		MovieEntity movie = checkValidMovie(movieId);
		UserDetailsEntity currentUser = getCurrentUser();
		ReviewEntity review = iReviewService.findOneByMovieAndUser(movie, currentUser);

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
	public ResponseEntity<Map<String, Object>> addReview(@PathVariable Long movieId,
			@Valid @RequestBody ReviewEntity newReview) throws RuntimeException {
		MovieEntity movie = checkValidMovie(movieId);
		UserDetailsEntity currentUser = getCurrentUser();
		ReviewEntity review = iReviewService.findOneByMovieAndUser(movie, currentUser);

		if (review != null) {
			review.setMovie(movie);
			review.setUser(currentUser);
			review.setRating(newReview.getRating());
			review.setReviewTitle(newReview.getReviewTitle());
			review.setReviewContent(newReview.getReviewContent());
			review.setIsActive(newReview.getIsActive());

			return new ResponseEntity<>(
					ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.OK.value(),
							iReviewService.update(review),
							"There exists a review given by you for the given movie. Updating your review."),
					HttpStatus.OK);
		} else {
			newReview.setMovie(movie);
			newReview.setUser(currentUser);
			movie.addReview(newReview);
			return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
					HttpStatus.CREATED.value(), iReviewService.insert(newReview), "Record Created Successfully."),
					HttpStatus.CREATED);
		}
	}

	@PutMapping("/review")
	public ResponseEntity<Map<String, Object>> updateReview(@PathVariable Long movieId,
			@Valid @RequestBody ReviewEntity updatedReview) throws RuntimeException {
		MovieEntity movie = checkValidMovie(movieId);
		UserDetailsEntity currentUser = getCurrentUser();
		ReviewEntity review = iReviewService.findOneByMovieAndUser(movie, currentUser);

		if (review == null) {
			updatedReview.setMovie(movie);
			updatedReview.setUser(currentUser);
			movie.addReview(updatedReview);
			return new ResponseEntity<>(
					ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.CREATED.value(),
							iReviewService.insert(updatedReview),
							"There is no review given by you for the given movie. Adding your review."),
					HttpStatus.CREATED);
		} else {
			review.setMovie(movie);
			review.setUser(currentUser);
			review.setRating(updatedReview.getRating());
			review.setReviewTitle(updatedReview.getReviewTitle());
			review.setReviewContent(updatedReview.getReviewContent());
			review.setIsActive(updatedReview.getIsActive());

			return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
					HttpStatus.OK.value(), iReviewService.update(review), "Record updated successfully."),
					HttpStatus.OK);
		}
	}

	@DeleteMapping("/review")
	public ResponseEntity<Map<String, Object>> deleteReview(@PathVariable Long movieId) {
		MovieEntity movie = checkValidMovie(movieId);
		UserDetailsEntity currentUser = getCurrentUser();
		ReviewEntity review = iReviewService.findOneByMovieAndUser(movie, currentUser);

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
