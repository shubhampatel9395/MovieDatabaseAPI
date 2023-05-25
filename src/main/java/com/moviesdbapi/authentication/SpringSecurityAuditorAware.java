package com.moviesdbapi.authentication;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return Optional.ofNullable(currentUser.getUsername());
	}

}
