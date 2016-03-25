package com.cch.programme.weixin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cch.programme.weixin.util.WeixinUtil;

@Controller(value = "WeixinController")
public class WeixinController {
	
	private final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/weixinAccess.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void weixinAccess(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		log.info("signature=" + signature);
		log.info("timestamp=" + timestamp);
		log.info("nonce=" + nonce);
		log.info("echostr=" + echostr);
		
		String encryptString = WeixinUtil.encryptAccess(timestamp, nonce);
		log.info("encryptString=" + encryptString);
		
		if (signature.equals(encryptString)) {
			res.getWriter().write(echostr);;
		}
		
	}
	
}
