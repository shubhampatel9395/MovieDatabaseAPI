package com.moviesdbapi.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDetails {
	private LocalDateTime timestamp;
	private Long errorCode;
	private String message;
	private String description;
}