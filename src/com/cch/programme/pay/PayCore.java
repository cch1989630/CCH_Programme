package com.cch.programme.pay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PayCore {
	public static Map<String, String> paraFilter(Map<String, String> sArray) {
    	Map<String, String> result = new HashMap<String, String>();
    	if(sArray == null || sArray.size() <= 0) {
    		return result;
    	}
    	
    	for(String key : sArray.keySet()) {
    		String value = sArray.get(key);
    		if(value == null || value.equals("") || key.equalsIgnoreCase("sign") || 
    				key.equalsIgnoreCase("sign_type")) {
    			continue;
    		}
    		result.put(key, value);
    	}
    	
    	return result;
    }
    
    public static String createLinkString(Map<String, String> params) {
    	
    	List<String> keys = new ArrayList<String>(params.keySet());
    	Collections.sort(keys);
    	
    	String prestr = "";
    	
    	for(int i=0; i<keys.size(); i++) {
    		String key = keys.get(i);
    		String value = params.get(key);
    		if(i == keys.size() - 1) { 
    			prestr = prestr + key + "=" + value;
    		} else {
    			prestr = prestr + key + "=" + value + "&";
    		}
    	}
    	
    	return prestr;
    }
    
    public static boolean encryptionVerify(Map<String, String> params, SystemToken systemToken) {
    	boolean isVerify = false;
    	isVerify = SignProvider.verify(systemToken.getPublicKey().getBytes(), createLinkString(paraFilter(params)), params.get("sign").getBytes());
    	System.out.println("isVerify====" + isVerify);
    	return isVerify;
    }
    
    public static Map<String, String> decodeMapForRequest(Map requestMap) throws Exception {
    	Map<String, String> decodeMap = new HashMap<String, String>();
    	Set keySet = requestMap.keySet();
    	for(Object key : keySet.toArray()) {
    		String valueString = new String((((String[])requestMap.get(key))[0]).getBytes("ISO-8859-1"),"utf-8");
    		decodeMap.put(key.toString(), valueString);
    	}
    	return decodeMap;
    }
}
