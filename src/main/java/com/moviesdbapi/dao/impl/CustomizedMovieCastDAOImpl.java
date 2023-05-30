package com.moviesdbapi.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
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
		String sql = "select * from moviesdb.MST_MOVIECAST where 1=1 ";
		return jdbcTemplate.query(sql, new MapSqlParameterSource().addValue("movieId", movieId) , new BeanPropertyRowMapper<MovieCastDTO>(MovieCastDTO.class));
	}
}
