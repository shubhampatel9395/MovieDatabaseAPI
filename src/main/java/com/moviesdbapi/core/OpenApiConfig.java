package com.moviesdbapi.core;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "Movie Database API Documentation", 
				description = "This is a documentation for movies database api.\nDeveloped by: Shubham Patel, Pauravi Parmar",
				contact = @Contact(name = "Shubham Patel, Pauravi Parmar", email = "shubhampatel9395@gmail.com, pauravi.237@gmail.com"), 
				version = "1.0.0"),
		servers = @Server(url = "http://localhost:8080", description = "Local Environment"), 
		security = @SecurityRequirement(name = "Basic Authentication")
)
@SecurityScheme(
		name = "Basic Authentication",
		type = SecuritySchemeType.HTTP,
		description = "Basic Username and Password based authentication", 
		scheme = "basic",
		in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

}
