package com.moviesdbapi.authentication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.service.IUserDetailsService;

@Service
public class UserPrincipalService implements UserDetailsService {
	
	@Autowired
	IUserDetailsService userDetailsService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<UserDetailsEntity> users = userDetailsService.findByFieldValue("email", email);
		
		if (CollectionUtils.isEmpty(users)) {
            throw new UsernameNotFoundException("User not found");
        }
		
		return new UserPrincipal(DataAccessUtils.singleResult(users));
	}

}
