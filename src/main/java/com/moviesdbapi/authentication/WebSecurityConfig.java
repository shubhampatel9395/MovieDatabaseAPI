package com.moviesdbapi.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	@Bean
	public UserDetailsService UserPrincipalService() {
		return new UserPrincipalService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(UserPrincipalService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeHttpRequests().requestMatchers("/api/v1/login", "/api/v1/signup","/api/v1/logout")
				.permitAll()
				.requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/v3/api-docs.yaml", "/swagger-ui/**",
						"/swagger-ui.html", "/api-docs.html", "/api-docs/**")
				.permitAll().requestMatchers(HttpMethod.GET, "/api/v1/movies").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/movies/{id}").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/movies/{movieId}/reviews").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/movies/{movieId}/reviews/{reviewId}").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/movies/{movieId}/cast").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/movies/{movieId}/cast/{castId}").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/movies/{movieId}/cast/{castType}").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/movies/{movieId}/posters").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/movies/{movieId}/posters/{posterId}").permitAll().anyRequest()
				.authenticated()
//				.and().logout().logoutUrl("/api/v1/logout").invalidateHttpSession(true)
//				.deleteCookies("JSESSIONID").logoutSuccessUrl("/login").permitAll()
				.and().httpBasic().and().build();
	}
}
