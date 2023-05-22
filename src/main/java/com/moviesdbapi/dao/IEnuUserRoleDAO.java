package com.moviesdbapi.dao;

import java.util.List;

import com.moviesdbapi.model.EnuUserRoleEntity;

public interface IEnuUserRoleDAO extends IJPARepository<EnuUserRoleEntity, Long> {
	public List<EnuUserRoleEntity> findByRole(String role);
}
