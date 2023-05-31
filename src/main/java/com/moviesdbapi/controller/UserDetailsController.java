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
import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.service.IUserDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserDetailsController {
	@Autowired
	IUserDetailsService userDetailsService;

//	@GetMapping("/users")
//	public ResponseEntity<List<UserDetailsDTO>> getAllActiveUsers() {
//		return new ResponseEntity<List<UserDetailsDTO>>(userDetailsService.findAllActive(), HttpStatus.OK);
//	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDetailsEntity>> getAllUsers() {
		return new ResponseEntity<List<UserDetailsEntity>>(userDetailsService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<Map<String, Object>> getUser(@PathVariable Long id) {
		Optional<UserDetailsEntity> existingUser = userDetailsService.findById(id);

		if (existingUser.isEmpty()) {
			throw new IdNotFoundException(id);
		}

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), existingUser.get(), "Record fetched successfully."), HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<Map<String, Object>> addUser(@Valid @RequestBody UserDetailsEntity userDetailsEntity)
			throws RuntimeException {
		return new ResponseEntity<>(
				ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.CREATED.value(),
						userDetailsService.insert(userDetailsEntity), "User Created Successfully."),
				HttpStatus.CREATED);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<Map<String, Object>> updateUser(@Valid @RequestBody UserDetailsEntity userDetailsEntity,
			@PathVariable Long id) throws RuntimeException {
//		
//		if (!(userDetailsEntity.getUserId().equals(id))) {
//			return new ResponseEntity<>(ResponseEntityUtil.getRes("Error",
//					"URI user id and body user id must be same. Please enter same user id",
//					HttpStatus.BAD_REQUEST.value()), HttpStatus.OK);
//		}
		
		Optional<UserDetailsEntity> existingUser = userDetailsService.findById(id);

		if (existingUser.isEmpty()) {
			throw new IdNotFoundException(id);
		}

		UserDetailsEntity existingEntity = new UserDetailsEntity();
		new ModelMapper().map(existingUser.get(), existingEntity);

		if (!(existingEntity.getBasicDetails().equals(userDetailsEntity.getBasicDetails()))) {
			existingEntity.setBasicDetails(userDetailsEntity.getBasicDetails());
		}

		if (!(existingEntity.getEmail().equals(userDetailsEntity.getEmail()))) {
			existingEntity.setEmail(userDetailsEntity.getEmail());
		}

		existingEntity.setPassword(userDetailsEntity.getPassword());

		if (userDetailsEntity.getGender() != null)
			existingEntity.setGender(userDetailsEntity.getGender());

		if (userDetailsEntity.getDob() != null)
			existingEntity.setDob(userDetailsEntity.getDob());

		existingEntity.setUserRole(userDetailsEntity.getUserRole());

		if (userDetailsEntity.getCountry() != null)
			existingEntity.setCountry(userDetailsEntity.getCountry());

		return new ResponseEntity<>(ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE,
				HttpStatus.OK.value(), userDetailsService.update(existingEntity), "User updated successfully."),
				HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
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

}