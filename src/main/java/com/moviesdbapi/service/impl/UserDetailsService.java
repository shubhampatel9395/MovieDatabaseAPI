package com.moviesdbapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviesdbapi.dao.IEnuCountryDAO;
import com.moviesdbapi.dao.IEnuUserRoleDAO;
import com.moviesdbapi.dao.IUserDetailsDAO;
import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.service.IUserDetailsService;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserDetailsService implements IUserDetailsService {

	@Autowired
	IUserDetailsDAO userDetailsDAO;
	
	@Autowired
	IEnuUserRoleDAO enuUserRoleDAO;

	@Autowired
	IEnuCountryDAO enuCountryDAO;
	
//	@PersistenceContext
//	private EntityManager entityManager;

	@Override
	public List<UserDetailsEntity> findAll() {
		return userDetailsDAO.findAll();
	}

	@Override
	public List<UserDetailsEntity> findAllActive() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("isActive", true);
		return userDetailsDAO.findByNamedParameters(paramSource);
	}

	@Override
	public UserDetailsEntity findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDetailsEntity> findByFieldValue(String fieldName, Object fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDetailsEntity> findByNamedParameters(MapSqlParameterSource paramSource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDetailsEntity insert(UserDetailsEntity entity) {
		// Set Password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(entity.getPassword());
		entity.setPassword(encodedPassword);

		// Set User Role
		entity.setUserRole((DataAccessUtils.singleResult(enuUserRoleDAO.findByRole(entity.getUserRole().getRole()))));
		
		// Set Country
		if(entity.getCountry() != null) {
			entity.setCountry(DataAccessUtils.singleResult(enuCountryDAO.findByCountry(entity.getCountry().getCountry())));
		}
		
		return userDetailsDAO.save(entity);
	}

	@Override
	public UserDetailsEntity update(UserDetailsEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(UserDetailsEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UserDetailsEntity> isUniqueEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean activate(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean softDelete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
