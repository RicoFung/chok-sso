package com.webconfig;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{
	private final Log log = LogFactory.getLog(getClass());
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception
	{
		resources.authenticationEntryPoint(new MyAuthenticationEntryPoint());
	}

	/**
	 *  自定义Token异常信息
	 *  用于token校验失败返回信息401
	 * @author zhuojun.feng
	 *
	 */
	private class MyAuthenticationEntryPoint implements AuthenticationEntryPoint
	{
		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException
		{
			log.error(authException.getMessage());
			request.setAttribute("msg", authException.getMessage());
			request.getRequestDispatcher("error/401").forward(request, response);
		}
	}
}
