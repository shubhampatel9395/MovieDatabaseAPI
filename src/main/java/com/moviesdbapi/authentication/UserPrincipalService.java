package com.moviesdbapi.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.service.IUserDetailsService;

@Service
public class UserPrincipalService implements UserDetailsService {
	
	@Autowired
	IUserDetailsService userDetailsService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailsEntity user = userDetailsService.findOneByEmail(username);
		
		if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
		
		return new UserPrincipal(user);
	}

}
