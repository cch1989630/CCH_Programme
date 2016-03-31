package com.cch.programme.backstage.controller;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/*
 * 用于给Ajax统一调用的controller，并且做到
 */
@Controller(value="AjaxSubmitController")
public class AjaxSubmitController {
	
	@RequestMapping("/ajaxNotUrl.do")
	public void ajaxNotUrl(HttpServletRequest req,HttpServletResponse res) throws Exception{
		res.setContentType("text/html;charset=UTF-8"); 
		try {
			String data = req.getParameter("data");
			JSONObject json = new JSONObject(data);
			
			
			String className = req.getParameter("beanName");
			String methodName = req.getParameter("functionName");
			
			if(className==null || "".equals(className)) {
				throw new Exception();
			}
			if(methodName==null || "".equals(methodName)) {
				throw new Exception();
			}
			
			Class[] argsClassType = new Class[1];
			argsClassType[0] = json.getClass();
			
			Object[] argsObject = new Object[1];
			argsObject[0] = json;
			
			
			WebApplicationContext web = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
			Object classObject = web.getBean(className);
			
			Class<?> ajaxClass = classObject.getClass();
			Method method = ajaxClass.getMethod(methodName, argsClassType);
			
			JSONObject jo = new JSONObject();
		
			jo = (JSONObject) method.invoke(classObject, argsObject);
			jo.put("state", 1);
			res.getWriter().print(jo.toString());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			res.setStatus(500);
			res.getWriter().print(e.getTargetException().getMessage());
		}
	}
	
	@RequestMapping("/ajaxRedirect.do")
	public String ajaxRedirect(ModelMap model, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		res.setContentType("text/html;charset=UTF-8"); 
		String returnString="";
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userName = userDetails.getUsername();
			
			String data = req.getParameter("data");
			JSONObject json = new JSONObject(data);
			
			String className = req.getParameter("beanName");
			String methodName = req.getParameter("functionName");
			
			if(className==null || "".equals(className)) {
				throw new Exception();
			}
			if(methodName==null || "".equals(methodName)) {
				throw new Exception();
			}
			
			HashMap map = new HashMap();
			
			Class[] argsClassType = new Class[2];
			argsClassType[0] = map.getClass();
			argsClassType[1] = json.getClass();
			
			Object[] argsObject = new Object[2];
			argsObject[0] = map;
			argsObject[1] = json;
			
			WebApplicationContext web = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
			Object classObject = web.getBean(className);
			
			Class<?> ajaxClass = classObject.getClass();
			Method method = ajaxClass.getMethod(methodName, argsClassType);
			
			returnString = (String) (method.invoke(classObject, argsObject));
			model.addAllAttributes(map);
			return returnString;
		} catch (InvocationTargetException e) {
			res.setStatus(500);
			res.getWriter().print(e.getTargetException().getMessage());
		}
		return returnString;
	}
}
