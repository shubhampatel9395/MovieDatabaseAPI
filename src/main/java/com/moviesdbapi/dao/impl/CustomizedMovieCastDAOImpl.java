package com.moviesdbapi.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.moviesdbapi.dao.CustomizedMovieCastDAO;
import com.moviesdbapi.model.dto.MovieCastDTO;

public class CustomizedMovieCastDAOImpl implements CustomizedMovieCastDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<MovieCastDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from moviesdb.MST_MOVIECAST where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<MovieCastDTO>(MovieCastDTO.class));
	}

	@Override
	public List<MovieCastDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from moviesdb.MST_MOVIECAST where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<MovieCastDTO>(MovieCastDTO.class));
	}

	@Override
	public List<MovieCastDTO> findAllByMovieId(Long movieId) {
		String sql = "select * from moviesdb.MST_MOVIECAST where 1=1 and movieId=:movieId ";
		return jdbcTemplate.query(sql, new MapSqlParameterSource().addValue("movieId", movieId),
				new BeanPropertyRowMapper<MovieCastDTO>(MovieCastDTO.class));
	}

	@Override
	public MovieCastDTO findOneByMovieAndCastTypeAndOriginalNames(Long movieId, Long movieCastTypeId,
			String originalFirstName, String originalLastName) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("movieId", movieId);
		params.addValue("movieCastTypeId", movieCastTypeId);
		params.addValue("originalFirstName", originalFirstName);
		params.addValue("originalLastName", originalLastName);

		return DataAccessUtils.singleResult(jdbcTemplate.query(
				"select * from moviesdb.MST_MOVIECAST where 1=1 and movieId=:movieId and movieCastTypeId=:movieCastTypeId and originalFirstName=:originalFirstName and originalLastName=:originalLastName",
				params, new BeanPropertyRowMapper<MovieCastDTO>(MovieCastDTO.class)));
	}

	@Override
	public List<MovieCastDTO> findAllByCastTypeId(Long movieId, Long movieCastTypeId) {
		String sql = "select * from moviesdb.MST_MOVIECAST where 1=1 and movieId=:movieId and movieCastTypeId=:movieCastTypeId ";
		return jdbcTemplate.query(sql,
				new MapSqlParameterSource().addValue("movieCastTypeId", movieCastTypeId).addValue("movieId", movieId),
				new BeanPropertyRowMapper<MovieCastDTO>(MovieCastDTO.class));
	}
}
