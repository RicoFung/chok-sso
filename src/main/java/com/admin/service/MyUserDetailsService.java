package com.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.admin.dao.OauthUsersDao;
import com.admin.entity.OauthUsers;

import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;

@Service
public class MyUserDetailsService extends BaseService<OauthUsers, Long> implements UserDetailsService
{
	@Autowired
	private OauthUsersDao dao;

	@Override
	public BaseDao<OauthUsers, Long> getEntityDao()
	{
		return dao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		OauthUsers u = dao.getByUsername(username);
		if (u == null)
		{
			throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
		}
		return new User(u.getUsername(), u.getPassword(), u.getAuthorities());
	}

}
