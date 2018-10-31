package com.admin.dao;

import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import chok.devwork.springboot.BaseDao;
import com.admin.entity.OauthAuthorities;

@Repository
public class OauthAuthoritiesDao extends BaseDao<OauthAuthorities,Long>
{
	@Resource(name = "sqlSessionTemplate")
	private SqlSession sqlSession;

	@Override
	protected SqlSession getSqlSession()
	{
		return sqlSession;
	}
	
	@Override
	public Class<OauthAuthorities> getEntityClass()
	{
		return OauthAuthorities.class;
	}
}
