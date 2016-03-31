package com.cch.programme.weixin.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;

import com.cch.programme.util.HttpRequestUtil;
import com.cch.programme.weixin.vo.TextMessage;
import com.thoughtworks.xstream.XStream;

public class WeixinUtil {
	
	/**
	 * 验证微信接入时的有效性
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static String encryptAccess(String timestamp, String nonce) {
		List<String> params = new ArrayList<String>();
		params.add(WeixinConstants.Token);
		params.add(timestamp);
		params.add(nonce);
		//将三个参数进行字典排序
		Collections.sort(params, new Comparator<String>() {

			@Override
			public int compare(String param1, String param2) {
				return param1.compareTo(param2);
			}
		});
		
		String needEncryptString = "";
		for (String param : params) {
			needEncryptString = needEncryptString + param;
		}
		//使用sha1对入参进行加密
		String encryptString = SHA1.encode(needEncryptString);
		
		return encryptString;
		
	}
	
	/**
	 * 根据appId和secret获取accessToken
	 * @param appid
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static String getAccessToken(String appid, String secret) throws Exception {
		String accessToken = "";
		Map<String, String> map = new HashMap<String, String>();
		map.put("grant_type", "client_credential");
		map.put("appid", appid);
		map.put("secret", secret);
		String returnString = HttpRequestUtil.HttpClicentGetMethod(WeixinConstants.GetAccessToken, map);
		JSONObject returnJson = new JSONObject(returnString);
		if (returnJson.has("access_token")) {
			accessToken = returnJson.getString("access_token");
		}
		
		return accessToken;
	}
	
	/**
	 * 解析微信发送过来的xml信息
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		//从request中获取输入流
		InputStream inputStream = request.getInputStream();
		//读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		//得到xml根元素
		Element root = document.getRootElement();
		//得到根节点下面的子节点
		List<Element> elementList = root.elements();
		//遍历所有子节点
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		
		// 释放资源  
	    inputStream.close();  
	    inputStream = null;
		
	    return map;
	}
	
	/**
	 * 文本消息对象转换成xml格式
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		//XStream xStream = new XStream();
		XStream xStream = XStreamUtil.createXstream();
		xStream.alias("xml", textMessage.getClass());
		return xStream.toXML(textMessage);
	}
	
	/**
	 * 处理微信发来的请求
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		//xml格式的消息数据
		String respXml = null;
		//默认返回文本消息内容
		String respContent = "未知消息类型";
		
		try {
			//调用parseXml方法解析请求消息
			Map<String, String> requestMap = parseXml(request);
			//发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			System.out.println("fromUserName=" + fromUserName);
			//开发者微信号
			String toUserName = requestMap.get("ToUserName");
			System.out.println("toUserName=" + toUserName);
			//消息类型
			String msgType = requestMap.get("MsgType");
			System.out.println("msgType=" + msgType);
			//回复文本消息
			TextMessage textMessage = new TextMessage();
			/**
			 * 下面的要切记
			 * 微信过来的FromUserName， 和toUserName是相反的
			 * 要调过来用
			 */
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(WeixinConstants.RESP_MESSAGE_TYPE_TEXT);
			
			if (msgType.equals(WeixinConstants.RESP_MESSAGE_TYPE_TEXT)) {
				respContent = "您发送的是文本信息";
			} else if (msgType.equals(WeixinConstants.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片信息";
			} else if (msgType.equals(WeixinConstants.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是语音信息";
			} else if (msgType.equals(WeixinConstants.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "您发送的是视频信息";
			} else if (msgType.equals(WeixinConstants.REQ_MESSAGE_TYPE_SHORT_VIDEO)) {
				respContent = "您发送的是短视频信息";
			} else if (msgType.equals(WeixinConstants.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是位置信息";
			} else if (msgType.equals(WeixinConstants.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接信息";
			} else if (msgType.equals(WeixinConstants.REQ_MESSAGE_TYPE_EVENT)) {
				//事件类型
				String eventType = requestMap.get("Event");
				if (eventType.equals(WeixinConstants.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您的关注，您可以从<a href='http://121.42.197.232/CCHPRO/welcome.do'>楠华祈梦</a>中获取到更多的信息。";
				} else if(eventType.equals(WeixinConstants.EVENT_TYPE_UNSUBSCRIBE)) {
					//取消关注
				} else if(eventType.equals(WeixinConstants.EVENT_TYPE_SCAN)) {
					//扫描带有参数的二维码，
				} else if(eventType.equals(WeixinConstants.EVENT_TYPE_LOCATION)) {
					//处理上传的位置信息
				} else if(eventType.equals(WeixinConstants.EVENT_TYPE_CLICK)) {
					//有菜单的公众号，处理菜单点击事件
				}
			}
			//设置文本消息的内容
			textMessage.setContent(respContent);
			System.out.println("respContent=" + respContent);
			//将文本信息对象转换成xml
			respXml = textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return respXml;
	}
	
	
	public static void main(String[] args) {
		//回复文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName("111");
		textMessage.setFromUserName("111");
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(WeixinConstants.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setContent("你好");
		String xml = textMessageToXml(textMessage);
		System.out.println(xml);
	}
}
