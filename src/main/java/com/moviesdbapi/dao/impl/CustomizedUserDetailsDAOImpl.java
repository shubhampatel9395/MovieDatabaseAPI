package com.moviesdbapi.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.moviesdbapi.dao.CustomizedUserDetailsDAO;
import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.model.dto.UserDetailsDTO;

public class CustomizedUserDetailsDAOImpl implements CustomizedUserDetailsDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<UserDetailsEntity> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from moviesdb.MST_USERDETAILS where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters,
				new BeanPropertyRowMapper<UserDetailsEntity>(UserDetailsEntity.class));
	}

	@Override
	public List<UserDetailsDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from moviesdb.MST_USERDETAILS where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<UserDetailsDTO>(UserDetailsDTO.class));
	}

	@Override
	public Long signup(UserDetailsEntity entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("firstName", entity.getBasicDetails().getFirstName());
		sc.addValue("lastName", entity.getBasicDetails().getLastName());
		sc.addValue("email", entity.getEmail());
		sc.addValue("password", entity.getPassword());
		sc.addValue("gender", entity.getGender());
		sc.addValue("dob", entity.getDob());

		if (entity.getCountry() != null) {
			sc.addValue("countryId", entity.getCountry().getCountryId());
			sc.addValue("countryName", entity.getCountry().getCountry());
		} else {
			sc.addValue("countryId", null);
			sc.addValue("countryName", null);
		}
		sc.addValue("userRoleId", entity.getUserRole().getUserRoleId());
		sc.addValue("userRole", entity.getUserRole().getRole());

		jdbcTemplate.update(
				"INSERT INTO moviesdb.mst_userdetails(firstName,lastName,dob,email,gender,password,countryId,countryName,userRoleId,userRole) VALUES (:firstName,:lastName,:dob,:email,:gender,:password,:countryId,:countryName,:userRoleId,:userRole)",
				sc, keyHolder, new String[] { "userId" });

		return keyHolder.getKey().longValue();
	}
}
