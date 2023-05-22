package com.moviesdbapi.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.moviesdbapi.dao.CustomizedUserDetailsDAO;
import com.moviesdbapi.model.UserDetailsEntity;

public class CustomizedUserDetailsDAOImpl implements CustomizedUserDetailsDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<UserDetailsEntity> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from moviesdb.MST_USERDETAILS where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);
		System.out.println(sql);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<UserDetailsEntity>(UserDetailsEntity.class));
	}
	
	@Override
	public List<UserDetailsEntity> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from moviesdb.MST_USERDETAILS where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<UserDetailsEntity>(UserDetailsEntity.class));
	}
}
