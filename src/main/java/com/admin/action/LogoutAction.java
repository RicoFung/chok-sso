package com.admin.action;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import chok.devwork.BaseController;

@Scope("prototype")
@Controller
public class LogoutAction extends BaseController<Object>
{
	@RequestMapping("/exit")
	public void exit()
	{
		// token can be revoked here if needed
		new SecurityContextLogoutHandler().logout(req, null, null);
		try
		{
			// sending back to client app
			System.out.println("==>>" + req.getHeader("referer"));
			response.sendRedirect(req.getHeader("referer"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
