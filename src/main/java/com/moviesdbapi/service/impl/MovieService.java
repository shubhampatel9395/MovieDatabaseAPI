package com.moviesdbapi.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviesdbapi.dao.IEnuCountryDAO;
import com.moviesdbapi.dao.IEnuCurrencyDAO;
import com.moviesdbapi.dao.IEnuGenreDAO;
import com.moviesdbapi.dao.IEnuLanguageDAO;
import com.moviesdbapi.dao.IMovieDAO;
import com.moviesdbapi.exception.DuplicateMovieException;
import com.moviesdbapi.exception.InvalidCountryException;
import com.moviesdbapi.exception.InvalidCurrencyException;
import com.moviesdbapi.exception.InvalidGenreException;
import com.moviesdbapi.exception.InvalidLanguageException;
import com.moviesdbapi.model.EnuCountryEntity;
import com.moviesdbapi.model.EnuCurrencyEntity;
import com.moviesdbapi.model.EnuGenreEntity;
import com.moviesdbapi.model.EnuLanguageEntity;
import com.moviesdbapi.model.MovieEntity;
import com.moviesdbapi.model.dto.MovieDTO;
import com.moviesdbapi.service.IMovieService;

@Service
@Transactional(rollbackFor = Exception.class)
public class MovieService implements IMovieService {

	@Autowired
	IMovieDAO movieDAO;

	@Autowired
	IEnuGenreDAO enuGenreDAO;

	@Autowired
	IEnuCountryDAO enuCountryDAO;

	@Autowired
	IEnuLanguageDAO enuLanguageDAO;

	@Autowired
	IEnuCurrencyDAO enuCurrencyDAO;

	@Override
	public List<MovieEntity> findAll() {
		return movieDAO.findAll();
	}

	@Override
	public Optional<MovieEntity> findById(Long id) {
		return movieDAO.findById(id);
	}
	
	@Override
	public MovieEntity findOneByMovieId(Long id) {
		return movieDAO.findOneByMovieId(id);
	}

	@Override
	public List<MovieDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return movieDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<MovieDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return movieDAO.findByNamedParameters(paramSource);
	}

	public void isValidMovieDetails(MovieEntity entity) throws RuntimeException {
		// Check Unique Movie Title and Release Date
		MovieEntity duplicateMovieCheckEntity = movieDAO.findByTitleAndReleaseDate(entity.getTitle(),
				entity.getReleaseDate());
		if (entity.getMovieId() == null) {
			if (duplicateMovieCheckEntity != null) {
				throw new DuplicateMovieException();
			}
		} else {
			if (duplicateMovieCheckEntity != null) {
				if (duplicateMovieCheckEntity.getMovieId() != entity.getMovieId()) {
					if (duplicateMovieCheckEntity.getTitle().equals(entity.getTitle())
							&& duplicateMovieCheckEntity.getReleaseDate().equals(entity.getReleaseDate())) {
						throw new DuplicateMovieException();
					}
				}
			}
		}

		// Check Movie Genres
		List<EnuGenreEntity> genres = new ArrayList<>(entity.getGenres());

		entity.setGenres(new HashSet<>());
		genres.forEach(genre -> {
			EnuGenreEntity temp = enuGenreDAO.findOneByGenre(genre.getGenre());
			if (temp == null) {
				throw new InvalidGenreException();
			} else {
				entity.getGenres().add(temp);
			}
		});

		// Check Country
		if (entity.getOriginCountry() != null) {
			EnuCountryEntity temp = enuCountryDAO.findOneByCountry(entity.getOriginCountry().getCountry());

			if (temp == null) {
				throw new InvalidCountryException();
			} else {
				entity.setOriginCountry(temp);
			}
		}

		// Check Languages
		List<EnuLanguageEntity> languages = new ArrayList<>(entity.getLanguages());
		entity.setLanguages(new HashSet<>());
		languages.forEach(language -> {
			EnuLanguageEntity temp = enuLanguageDAO.findOneByLanguage(language.getLanguage());
			if (temp == null) {
				throw new InvalidLanguageException();
			} else {
				entity.getLanguages().add(temp);
			}
		});

		// Check Currency
		if (entity.getCurrency() != null) {
			EnuCurrencyEntity temp = enuCurrencyDAO.findOneByCurrencyName(entity.getCurrency().getCurrencyName());
			if (temp == null) {
				throw new InvalidCurrencyException();
			} else {
				entity.setCurrency(temp);
			}
		}
	}

	@Override
	public MovieEntity insert(MovieEntity entity) throws RuntimeException {
		isValidMovieDetails(entity);
		return movieDAO.save(entity);
	}

	@Override
	public MovieEntity update(MovieEntity entity) throws RuntimeException {
		isValidMovieDetails(entity);
		return movieDAO.save(entity);
	}

	@Override
	public void delete(Long id) {
		movieDAO.deleteById(id);
	}

	@Override
	public void delete(MovieEntity entity) {
		movieDAO.delete(entity);
	}

}
