package com.admin.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author rico
 * @version 1.0
 * @since 1.0
 */
public class OauthUsers implements UserDetails
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// username db_column: username
	private java.lang.String username;
	// password db_column: password
	private java.lang.String password;
	// enabled db_column: enabled
	private java.lang.Boolean enabled;

    private List<? extends GrantedAuthority> authorities;

	public OauthUsers()
	{
	}

	public OauthUsers(java.lang.String username, java.lang.String password, java.lang.Boolean enabled)
	{
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public void setUsername(java.lang.String value)
	{
		this.username = value;
	}

	public java.lang.String getUsername()
	{
		return this.username;
	}

	public void setPassword(java.lang.String value)
	{
		this.password = value;
	}

	public java.lang.String getPassword()
	{
		return this.password;
	}

	public void setEnabled(java.lang.Boolean value)
	{
		this.enabled = value;
	}
	
	public void setAuthorities(List<? extends GrantedAuthority> authorities)
	{
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return this.authorities;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled()
	{
		// TODO Auto-generated method stub
		return this.enabled;
	}
}
