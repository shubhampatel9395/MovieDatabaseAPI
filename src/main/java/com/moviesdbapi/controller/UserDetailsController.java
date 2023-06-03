package com.moviesdbapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.moviesdbapi.model.ReviewEntity;
import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.service.IReviewService;
import com.moviesdbapi.service.IUserDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserDetailsController {
	@Autowired
	IUserDetailsService userDetailsService;

	@Autowired
	IReviewService iReviewService;

//	@GetMapping("/users")
//	@PreAuthorize(value = "hasAuthority('Admin')")
//	public ResponseEntity<List<UserDetailsDTO>> getAllActiveUsers() {
//		return new ResponseEntity<List<UserDetailsDTO>>(userDetailsService.findAllActive(), HttpStatus.OK);
//	}

	public UserDetailsEntity getCurrentUser() {
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return currentUser.getUser();
	}

	@GetMapping("/users")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<List<UserDetailsEntity>> getAllUsers() {
		return new ResponseEntity<List<UserDetailsEntity>>(userDetailsService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/users/{id}")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> getUser(@PathVariable Long id) {
		Optional<UserDetailsEntity> existingUser = userDetailsService.findById(id);

		if (existingUser.isEmpty()) {
			throw new IdNotFoundException(id);
		}

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), existingUser.get(), "Record fetched successfully."), HttpStatus.OK);
	}

	@PostMapping("/users")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> addUser(@Valid @RequestBody UserDetailsEntity userDetailsEntity)
			throws RuntimeException {
		return new ResponseEntity<>(
				ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.CREATED.value(),
						userDetailsService.insert(userDetailsEntity), "User Created Successfully."),
				HttpStatus.CREATED);
	}

	@PutMapping("/users/{id}")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> updateUser(@Valid @RequestBody UserDetailsEntity userDetailsEntity,
			@PathVariable Long id) throws RuntimeException {
		if (!(userDetailsEntity.getUserId().equals(id))) {
			return new ResponseEntity<>(ResponseEntityUtil.getRes("Error",
					"URI user id and body user id must be same. Please enter same user id",
					HttpStatus.BAD_REQUEST.value()), HttpStatus.OK);
		}

		Optional<UserDetailsEntity> existingUser = userDetailsService.findById(id);

		if (existingUser.isEmpty()) {
			throw new IdNotFoundException(id);
		}

		UserDetailsEntity existingEntity = new UserDetailsEntity();
		new ModelMapper().map(existingUser.get(), existingEntity);

		existingEntity.setBasicDetails(userDetailsEntity.getBasicDetails());
		existingEntity.setEmail(userDetailsEntity.getEmail());
		existingEntity.setPassword(userDetailsEntity.getPassword());
		existingEntity.setGender(userDetailsEntity.getGender());
		existingEntity.setDob(userDetailsEntity.getDob());
		existingEntity.setUserRole(userDetailsEntity.getUserRole());
		existingEntity.setCountry(userDetailsEntity.getCountry());
		existingEntity.setIsActive(userDetailsEntity.getIsActive());

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), userDetailsService.update(existingEntity), "User updated successfully."),
				HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	@PreAuthorize(value = "hasAuthority('Admin')")
	public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
		Optional<UserDetailsEntity> existingUser = userDetailsService.findById(id);

		if (existingUser.isEmpty()) {
			throw new IdNotFoundException(id);
		}
		UserDetailsEntity returnEntity = existingUser.get();

		userDetailsService.delete(id);

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), returnEntity, "Record deleted and returned successfully."), HttpStatus.OK);
	}

	@GetMapping("/reviews")
	@PreAuthorize(value = "hasAnyAuthority('User')")
	public ResponseEntity<Map<String, Object>> getAllReviews() {
		UserDetailsEntity currentUser = getCurrentUser();
		List<ReviewEntity> reviews = new ArrayList<>(currentUser.getReviews());

		if (reviews.isEmpty()) {
			return new ResponseEntity<>(ResponseEntityUtil.getRes(MessageConstants.SUCCESS_MESSAGE,
					"There are no reviews given by you.", HttpStatus.OK.value()), HttpStatus.OK);
		}

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), reviews, "Record(s) fetched successfully."), HttpStatus.OK);
	}
}