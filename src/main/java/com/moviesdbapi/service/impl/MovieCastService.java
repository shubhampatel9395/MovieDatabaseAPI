package com.moviesdbapi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviesdbapi.dao.IEnuMovieCastTypeDAO;
import com.moviesdbapi.dao.IMovieCastDAO;
import com.moviesdbapi.dao.IMovieDAO;
import com.moviesdbapi.exception.DuplicateMovieCastException;
import com.moviesdbapi.exception.InvalidMovieCastTypeException;
import com.moviesdbapi.model.EnuMovieCastTypeEntity;
import com.moviesdbapi.model.MovieCastEntity;
import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.dto.MovieCastDTO;
import com.moviesdbapi.service.IMovieCastService;

@Service
@Transactional(rollbackFor = Exception.class)
public class MovieCastService implements IMovieCastService {
	@Autowired
	IMovieDAO movieDAO;

	@Autowired
	IMovieCastDAO movieCastDAO;

	@Autowired
	IEnuMovieCastTypeDAO movieCastTypeDAO;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<MovieCastEntity> findAll() {
		return movieCastDAO.findAll();
	}

	@Override
	public Optional<MovieCastEntity> findById(Long id) {
		return movieCastDAO.findById(id);
	}

	@Override
	public List<MovieCastDTO> findAllByMovieId(Long id) {
		return movieCastDAO.findAllByMovieId(id);
	}

	@Override
	public List<MovieCastDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return movieCastDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<MovieCastDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return movieCastDAO.findByNamedParameters(paramSource);
	}

	public MovieCastEntity isValidMovieCast(MovieCastEntity entity) {
		EnuMovieCastTypeEntity castType = movieCastTypeDAO
				.findOneByMovieCastType(entity.getCastType().getMovieCastType());

		if (castType == null) {
			throw new InvalidMovieCastTypeException();
		} else {
			entity.setCastType(castType);
		}

		MovieCastDTO exists = movieCastDAO.findOneByMovieAndCastTypeAndOriginalNames(entity.getMovie().getMovieId(),
				castType.getMovieCastTypeId(), entity.getOriginalNames().getFirstName(),
				entity.getOriginalNames().getLastName());

		if (entity.getMovieCastId() == null) {
			if (exists != null) {
				throw new DuplicateMovieCastException();
			}
		} else {
			if (exists != null && !(exists.getMovieCastId().equals(entity.getMovieCastId()))) {
				throw new DuplicateMovieCastException();
			}
		}

		return movieCastDAO.save(entity);
	}

	public List<MovieCastDTO> isValidMovieCasts(List<MovieCastEntity> entities) {
		List<MovieCastDTO> returnDTOs = new ArrayList<>();
		for (MovieCastEntity obj : entities) {
			returnDTOs.add(modelMapper.map(isValidMovieCast(obj), MovieCastDTO.class));
		}
		return returnDTOs;
	}

	@Override
	public List<MovieCastDTO> insert(List<MovieCastEntity> entity) throws RuntimeException {
		return isValidMovieCasts(entity);
	}

	@Override
	public MovieCastDTO update(MovieCastEntity entity) throws RuntimeException {
		return modelMapper.map(isValidMovieCast(entity), MovieCastDTO.class);
	}

	@Override
	public void delete(Long id) {
		movieCastDAO.deleteById(id);
	}

	@Override
	public void delete(MovieCastEntity entity) {
		movieCastDAO.delete(entity);
	}

	@Override
	public void deleteAll(List<MovieCastEntity> entities) {
		movieCastDAO.deleteAll(entities);
	}

	@Override
	public List<MovieCastEntity> findAllByMovie(MovieEntity movie) {
		return movieCastDAO.findAllByMovie(movie);
	}

	@Override
	public List<MovieCastDTO> findAllByCastTypeId(Long movieId, Long movieCastTypeId) {
		return movieCastDAO.findAllByCastTypeId(movieId, movieCastTypeId);
	}

}
