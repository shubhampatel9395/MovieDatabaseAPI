package com.moviesdbapi.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.moviesdbapi.dao.CustomizedPosterDAO;
import com.moviesdbapi.model.PosterEntity;

public class CustomizedPosterDAOImpl implements CustomizedPosterDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<PosterEntity> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from moviesdb.MST_POSTER where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<PosterEntity>(PosterEntity.class));
	}

	@Override
	public List<PosterEntity> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from moviesdb.MST_POSTER where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<PosterEntity>(PosterEntity.class));
	}

}
