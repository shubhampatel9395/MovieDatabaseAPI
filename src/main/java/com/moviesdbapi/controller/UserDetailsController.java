package com.moviesdbapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.service.IUserDetailsService;

@RestController
@RequestMapping("/api/v1")
public class UserDetailsController {
	@Autowired
	IUserDetailsService userDetailsService;
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDetailsEntity>> getAllActiveUsers() {
		System.out.println(userDetailsService.findAllActive());
		return new ResponseEntity<List<UserDetailsEntity>>(userDetailsService.findAllActive(), HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<UserDetailsEntity> addUser(@RequestBody JsonNode payload) {
//		UserDetailsEntity entity = payload.has
//		UserDetailsEntity insertedUser = userDetailsService.insert(userDetailsEntity);
//		return new ResponseEntity<UserDetailsEntity>(insertedUser, HttpStatus.CREATED);
		return null;
	}
}