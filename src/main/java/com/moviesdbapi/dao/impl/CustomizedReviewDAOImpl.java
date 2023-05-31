package com.moviesdbapi.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.moviesdbapi.dao.CustomizedReviewDAO;
import com.moviesdbapi.model.ReviewEntity;

public class CustomizedReviewDAOImpl implements CustomizedReviewDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<ReviewEntity> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from moviesdb.MST_MOVIE where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<ReviewEntity>(ReviewEntity.class));
	}
	
	@Override
	public List<ReviewEntity> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from moviesdb.MST_MOVIE where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<ReviewEntity>(ReviewEntity.class));
	}

	@Override
	public ReviewEntity findOneByMovieIdUserId(Long movieId, Long userId) {
		return null;
	}
}
