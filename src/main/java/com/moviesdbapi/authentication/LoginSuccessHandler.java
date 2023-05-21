package com.moviesdbapi.authentication;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {

		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

		String redirectURL = request.getContextPath();

		if (user.getRoleName().equalsIgnoreCase("ROLE_USER")) {
			redirectURL = "customer/index";
		} else if (user.getRoleName().equalsIgnoreCase("ROLE_ADMIN")) {
			redirectURL = "admin/dashboard";
		}

		response.sendRedirect(redirectURL);
	}
}
