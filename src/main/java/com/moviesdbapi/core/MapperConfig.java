package com.moviesdbapi.core;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.moviesdbapi.model.EnuMovieCastTypeEntity;
import com.moviesdbapi.model.MovieCastEntity;
import com.moviesdbapi.model.UserBasicDetailsEntity;
import com.moviesdbapi.model.dto.MovieCastCreateDTO;
import com.moviesdbapi.model.dto.MovieCastDTO;

@Configuration
public class MapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		Converter<MovieCastCreateDTO, MovieCastEntity> castDTOtoEntityConverter = new AbstractConverter<MovieCastCreateDTO, MovieCastEntity>() {
			@Override
			protected MovieCastEntity convert(MovieCastCreateDTO dto) {
				MovieCastEntity entity = new MovieCastEntity();
				entity.setOriginalNames(
						new UserBasicDetailsEntity(dto.getOriginalFirstName(), dto.getOriginalLastName()));
				entity.setMovieNames(new UserBasicDetailsEntity(dto.getMovieFirstName(), dto.getMovieLastName()));
				entity.setCastType(new EnuMovieCastTypeEntity(dto.getMovieCastType()));
				return entity;
			}
		};
		
		Converter<MovieCastEntity, MovieCastDTO> castEntitytoDTOConverter = new AbstractConverter<MovieCastEntity, MovieCastDTO>() {
			@Override
			protected MovieCastDTO convert(MovieCastEntity entity) {
				MovieCastDTO dto = new MovieCastDTO();
				dto.setMovieCastId(entity.getMovieCastId());
				dto.setMovieId(entity.getMovie().getMovieId());
				dto.setMovieTitle(entity.getMovie().getTitle());
				dto.setMovieCastTypeId(entity.getCastType().getMovieCastTypeId());
				dto.setMovieCastType(entity.getCastType().getMovieCastType());
				dto.setOriginalFirstName(entity.getOriginalNames().getFirstName());
				dto.setOriginalLastName(entity.getOriginalNames().getLastName());
				
				if(entity.getMovieNames() != null) {
					dto.setMovieFirstName(entity.getMovieNames().getFirstName());
					dto.setMovieLastName(entity.getMovieNames().getLastName());
				}
				
				dto.setCreatedBy(entity.getCreatedBy());
				dto.setCreatedDate(entity.getCreatedDate());
				dto.setLastModifiedBy(entity.getLastModifiedBy());
				dto.setLastModifiedDate(entity.getLastModifiedDate());
				dto.setIsActive(entity.getIsActive());
				return dto;
			}
		};

		modelMapper.addConverter(castDTOtoEntityConverter);
		modelMapper.addConverter(castEntitytoDTOConverter);
		return modelMapper;
	}
}