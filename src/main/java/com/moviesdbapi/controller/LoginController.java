package com.moviesdbapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviesdbapi.core.ResponseEntityUtil;
import com.moviesdbapi.model.dto.LoginDTO;

@RestController
@RequestMapping("/api/v1")
public class LoginController {
	
	@Autowired
	private ApplicationContext applicationContext;

	@PostMapping("/login")
	public Map<String, Object> login(jakarta.servlet.http.HttpServletRequest request,
			@RequestBody LoginDTO user) throws Exception {
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
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
				new String(user.getUsername() + " is a valid user."), null);
	}
}
