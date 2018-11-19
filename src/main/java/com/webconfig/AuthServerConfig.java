package com.webconfig;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import chok.common.BeanFactory;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter
{
    @Autowired
    private AuthenticationManager authenticationManager;

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception
	{
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		//enable client to get the authenticated when using the /oauth/token to get a access token
		//there is a 401 authentication is required if it doesn't allow form authentication for clients when access /oauth/token   
		oauthServer.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception
	{
		// jdbc认证
		clients.jdbc((DataSource) BeanFactory.getBean("dataSource"))
		// 内存认证
//		 clients.inMemory()
//		 .withClient("client1").secret(passwordEncoder.encode("secret"))
//		 .authorizedGrantTypes("authorization_code").scopes("user_info").autoApprove(true)
//		 .redirectUris("http://localhost:9091/chok-sso-client1/login")
//		 .and()
//		 .withClient("client2").secret(passwordEncoder.encode("secret"))
//		 .authorizedGrantTypes("authorization_code").scopes("user_info").autoApprove(true)
//		 .redirectUris("http://localhost:9092/chok-sso-client2/login")
//		 .and()
//		 .withClient("client3").secret(passwordEncoder.encode("secret"))
//		 .authorizedGrantTypes("authorization_code").scopes("user_info").autoApprove(true)
//		 .redirectUris("http://localhost:9093/chok-demo/login")
//		.accessTokenValiditySeconds(3600)
		; // 1 hour
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
	{
		endpoints.authenticationManager(authenticationManager);
		endpoints.tokenStore((TokenStore) BeanFactory.getBean("tokenStore"));
	}

	@Bean(name = "tokenStore")
	public TokenStore tokenStore() throws SQLException
	{
		return new JdbcTokenStore((DataSource) BeanFactory.getBean("dataSource"));
	}
}
