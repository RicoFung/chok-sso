package com.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
		List<OauthUsers> users = dao.query(new HashMap<String, Object>()
		{
			private static final long serialVersionUID = 1L;
			{
				put("username", username);
			}
		});

		OauthUsers u = users.get(0);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		// 模拟admin权限
		authorities.add(new SimpleGrantedAuthority("USER"));
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		// //模拟admin用户
		u.setAuthorities(authorities);
		return new User(u.getUsername(), u.getPassword(), u.getAuthorities());
	}

}
