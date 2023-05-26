package com.moviesdbapi.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.moviesdbapi.dao.CustomizedMovieDAO;
import com.moviesdbapi.model.dto.MovieDTO;

public class CustomizedMovieDAOImpl implements CustomizedMovieDAO  {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<MovieDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from moviesdb.MST_USERDETAILS where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<MovieDTO>(MovieDTO.class));
	}
	
	@Override
	public List<MovieDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from moviesdb.MST_USERDETAILS where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<MovieDTO>(MovieDTO.class));
	}
}
