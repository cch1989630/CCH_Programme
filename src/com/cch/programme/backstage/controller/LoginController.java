package com.cch.programme.backstage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 后台登录和退出功能controller
 * @author cch
 *
 */
@Controller(value = "LoginController")
public class LoginController {
	private final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/welcome.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String error(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		return "welcome";
	}
}
