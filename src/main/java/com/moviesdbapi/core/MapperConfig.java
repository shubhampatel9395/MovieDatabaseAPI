package com.moviesdbapi.core;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.moviesdbapi.model.MovieCastEntity;
import com.moviesdbapi.model.UserBasicDetailsEntity;
import com.moviesdbapi.model.dto.MovieCastCreateDTO;

@Configuration
public class MapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		Converter<MovieCastCreateDTO, MovieCastEntity> converter = new AbstractConverter<MovieCastCreateDTO, MovieCastEntity>() {
			@Override
			protected MovieCastEntity convert(MovieCastCreateDTO dto) {
				MovieCastEntity entity = new MovieCastEntity();
				entity.setOriginalNames(
						new UserBasicDetailsEntity(dto.getOriginalFirstName(), dto.getOriginalLastName()));
				entity.setMovieNames(new UserBasicDetailsEntity(dto.getMovieFirstName(), dto.getMovieLastName()));
				return entity;
			}
		};

		modelMapper.addConverter(converter);
		return modelMapper;
	}
}