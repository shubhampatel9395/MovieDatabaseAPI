package com.moviesdbapi.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.moviesdbapi.dao.CustomizedReviewDAO;
import com.moviesdbapi.model.dto.ReviewDTO;

public class CustomizedReviewDAOImpl implements CustomizedReviewDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<ReviewDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from moviesdb.MST_REVIEW where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<ReviewDTO>(ReviewDTO.class));
	}

	@Override
	public List<ReviewDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from moviesdb.MST_REVIEW where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<ReviewDTO>(ReviewDTO.class));
	}

	@Override
	public ReviewDTO findOneByMovieIdUserId(Long movieId, Long userId) {
		String sql = "select * from moviesdb.MST_REVIEW where 1=1 and movieId=:movieId and userId=:userId ";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("movieId", movieId);
		namedParameters.addValue("userId", userId);

		return DataAccessUtils.singleResult(
				jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<ReviewDTO>(ReviewDTO.class)));
	}
}
