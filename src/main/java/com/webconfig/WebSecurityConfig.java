package com.webconfig;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.admin.service.MyUserDetailsService;

@Configuration
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	static Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	MyUserDetailsService service;
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.requestMatchers().antMatchers("/login", "/logout", "/oauth/token", "/oauth/authorize", "/error").and()
				.authorizeRequests().anyRequest().authenticated()
				// 定制Login
				.and().formLogin().loginPage("/login").permitAll()
				// 定制Logout
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.addLogoutHandler(new MyLogoutHandler())
				// 定制Logout-清除认证信息
				.deleteCookies() // 底层也是使用Handler实现的额
				.clearAuthentication(true).invalidateHttpSession(true);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		// 内存认证
//		auth.inMemoryAuthentication().withUser("rico").password(passwordEncoder().encode("123")).roles("USER");
		// 自定义认证
		auth.userDetailsService(service).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	/**
	 * 自定义logout
	 * 
	 * @author rico.fung
	 *
	 */
	private class MyLogoutHandler extends SecurityContextLogoutHandler
	{
		@Override
		public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
		{
			try
			{
				log.info("==> referer-url: " + request.getHeader("referer"));
				response.sendRedirect(request.getHeader("referer"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}