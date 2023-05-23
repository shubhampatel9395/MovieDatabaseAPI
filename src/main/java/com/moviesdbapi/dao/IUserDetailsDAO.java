package com.moviesdbapi.dao;

import java.util.List;

import com.moviesdbapi.model.UserDetailsEntity;

public interface IUserDetailsDAO extends IJPARepository<UserDetailsEntity, Long>, CustomizedUserDetailsDAO {
	public List<UserDetailsEntity> findByEmail(String email);
}
