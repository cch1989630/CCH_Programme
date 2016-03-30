package com.cch.programme.weixin.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cch.programme.util.HttpRequestUtil;
import com.cch.programme.weixin.util.WeixinConstants;
import com.cch.programme.weixin.util.WeixinUtil;

@Controller(value = "WeixinController")
public class WeixinController {
	
	private final Log log = LogFactory.getLog(getClass());
	
	/**
	 * 用于微信校验我们服务器的请求
	 * 只支持get方法
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/weixinAccess.do", method = {RequestMethod.GET})
	public void weixinAccess(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
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
	
	
	/**
	 * 用于接受微信发送过来的消息推送
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/weixinAccess.do", method = {RequestMethod.POST})
	public void accessMessageFromWeixin(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		
		PrintWriter out = res.getWriter();
		String encryptString = WeixinUtil.encryptAccess(timestamp, nonce);
		if (signature.equals(encryptString)) {
			String returnXml = WeixinUtil.processRequest(req);
			log.info(returnXml);
			out.write(returnXml);
		}
		out.flush();
		out.close();
		out = null;
	}
	
	/**
	 * 自定义菜单
	 * @param req
	 * @param res
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/customMenu.do", method = {RequestMethod.POST, RequestMethod.GET})
	public void customMenu(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
		JSONObject returnJson = new JSONObject();
		JSONArray buttonArray = new JSONArray();
		JSONObject oneButton = new JSONObject();
		oneButton.put("type", "view");
		oneButton.put("name", "百度");
		oneButton.put("url", "http://www.baidu.com");
		buttonArray.put(oneButton);
		JSONObject twoButton = new JSONObject();
		twoButton.put("type", "view");
		twoButton.put("name", "百度");
		twoButton.put("url", "http://www.baidu.com");
		buttonArray.put(twoButton);
		JSONObject threeButton = new JSONObject();
		threeButton.put("type", "view");
		threeButton.put("name", "百度");
		threeButton.put("url", "http://www.baidu.com");
		buttonArray.put(threeButton);
		returnJson.put("button", buttonArray);
		
		String accessToken = WeixinUtil.getAccessToken(WeixinConstants.AppId, WeixinConstants.AppSecret);
		String returnString = HttpRequestUtil.httpConnectionPostMethod(WeixinConstants.CustomMenu+"?access_token=" + accessToken, returnJson.toString());
		System.out.println(returnString);
	}
	
}
