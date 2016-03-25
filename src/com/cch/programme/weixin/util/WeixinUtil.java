package com.cch.programme.weixin.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.cch.programme.util.HttpRequestUtil;

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
	
}
