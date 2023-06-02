package com.moviesdbapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviesdbapi.core.ResponseEntityUtil;
import com.moviesdbapi.exception.MessageConstants;
import com.moviesdbapi.model.EnuUserRoleEntity;
import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.model.dto.LoginDTO;
import com.moviesdbapi.service.IUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	IUserDetailsService userDetailsService;

	@PostMapping("/login")
	public Map<String, Object> login(HttpServletRequest request, @Valid @RequestBody LoginDTO user) throws Exception {

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(),
				user.getPassword());
		authToken.setDetails(new WebAuthenticationDetails(request));

		try {
			Authentication authentication = applicationContext.getBean(AuthenticationConfiguration.class)
					.getAuthenticationManager().authenticate(authToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid credentials");
		} catch (Exception e) {
			throw new Exception("Exception while authentication {e}", e);
		}

		return ResponseEntityUtil.getSuccessResponse("Login Success", HttpStatus.OK.value(),
				new String(user.getUsername() + " is a valid user."), "User logged in successfully.");
	}

	@PostMapping("/signup")
	public ResponseEntity<Map<String, Object>> signUp(HttpServletRequest request,
			@Valid @RequestBody UserDetailsEntity userDetailsEntity) throws Exception {
		userDetailsEntity.setUserRole(new EnuUserRoleEntity("User"));
		return new ResponseEntity<>(
				ResponseEntityUtil.getSuccessResponse(MessageConstants.SUCCESS_MESSAGE, HttpStatus.CREATED.value(),
						userDetailsService.signup(userDetailsEntity), "Sign-up completed successfully."),
				HttpStatus.CREATED);
	}
}