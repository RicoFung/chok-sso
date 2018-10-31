package com.admin.dao;

import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import chok.devwork.springboot.BaseDao;
import com.admin.entity.OauthUsers;

@Repository
public class OauthUsersDao extends BaseDao<OauthUsers, Long>
{
	@Resource
	private SqlSession sqlSession;

	@Override
	protected SqlSession getSqlSession()
	{
		return sqlSession;
	}
	
	@Override
	public Class<OauthUsers> getEntityClass()
	{
		return OauthUsers.class;
	}
	
	public OauthUsers getByUsername(String username)
	{
		return (OauthUsers) get("getByUsername", username);
	}
}
