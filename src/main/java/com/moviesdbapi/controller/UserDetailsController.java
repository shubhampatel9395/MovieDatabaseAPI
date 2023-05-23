package com.moviesdbapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviesdbapi.exception.DuplicateEmailException;
import com.moviesdbapi.exception.InvalidCountryException;
import com.moviesdbapi.exception.InvalidPasswordException;
import com.moviesdbapi.exception.InvalidUserRoleException;
import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.model.dto.UserDetailsDTO;
import com.moviesdbapi.service.IUserDetailsService;

@RestController
@RequestMapping("/api/v1")
public class UserDetailsController {
	@Autowired
	IUserDetailsService userDetailsService;

	@GetMapping("/users")
	public ResponseEntity<List<UserDetailsDTO>> getAllActiveUsers() {
		return new ResponseEntity<List<UserDetailsDTO>>(userDetailsService.findAllActive(), HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<UserDetailsEntity> addUser(@Valid @RequestBody UserDetailsEntity userDetailsEntity)
			throws DuplicateEmailException, InvalidPasswordException, InvalidUserRoleException,
			InvalidCountryException {
		UserDetailsEntity insertedUser = userDetailsService.insert(userDetailsEntity);
		return new ResponseEntity<UserDetailsEntity>(insertedUser, HttpStatus.CREATED);
	}

//	@GetMapping("/users")
//	public ResponseEntity<List<UserDetailsEntity>> getAllUsers() {
//		return new ResponseEntity<List<UserDetailsEntity>>(userDetailsService.findAll(), HttpStatus.OK);
//	}
}