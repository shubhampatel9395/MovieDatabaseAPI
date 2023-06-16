package com.moviesdbapi.core;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.moviesdbapi.model.EnuMovieCastTypeEntity;
import com.moviesdbapi.model.MovieCastEntity;
import com.moviesdbapi.model.ReviewEntity;
import com.moviesdbapi.model.UserBasicDetailsEntity;
import com.moviesdbapi.model.dto.MovieCastCreateDTO;
import com.moviesdbapi.model.dto.MovieCastDTO;
import com.moviesdbapi.model.dto.ReviewDTO;
import com.moviesdbapi.service.IMovieService;
import com.moviesdbapi.service.IUserDetailsService;

@Configuration
public class MapperConfig {
	
	@Autowired
	IMovieService iMovieService;
	
	@Autowired
	IUserDetailsService iUserDetailsService;

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
		
		Converter<ReviewDTO,ReviewEntity> reviewDTOtoEntityConverter = new AbstractConverter<ReviewDTO,ReviewEntity>() {
			@Override
			protected ReviewEntity convert(ReviewDTO dto) {
				ReviewEntity entity = new ReviewEntity();
				entity.setCreatedBy(dto.getCreatedBy());
				entity.setCreatedDate(dto.getCreatedDate());
				entity.setIsActive(dto.getIsActive());
				entity.setLastModifiedBy(dto.getLastModifiedBy());
				entity.setLastModifiedDate(dto.getLastModifiedDate());
				entity.setMovie(iMovieService.findOneByMovieId(dto.getMovieId()));
				entity.setRating(dto.getRating());
				entity.setReviewContent(dto.getReviewContent());
				entity.setReviewId(dto.getReviewId());
				entity.setReviewTitle(dto.getReviewTitle());
				entity.setUser(iUserDetailsService.findById(dto.getUserId()).get());
				return entity;
			}
		};

		modelMapper.addConverter(castDTOtoEntityConverter);
		modelMapper.addConverter(castEntitytoDTOConverter);
		modelMapper.addConverter(reviewDTOtoEntityConverter);
		return modelMapper;
	}
}