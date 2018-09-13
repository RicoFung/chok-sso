package com.admin.action;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAction
{
	@RequestMapping("/user/me")
	public Principal user(Principal principal)
	{
		System.out.println(principal);
		return principal;
	}
}
