package com.cch.programme.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.cch.programme.pay.PayCore;

/**
 * httpClient客户端
 * @author cch
 *
 */
public class HttpRequestUtil {
	/**
	 * 
	 * @param url	请求的url
	 * @param port	请求的端口号
	 * @param requestType	请求的链接方式http https
	 * @param params	请求参数
	 * @return
	 */
	public static String HttpClicentGetMethod(String url, Map<String, String> params) {
		
		String response = "";
		HttpClient client = new HttpClient();
		
		String paramsString = "";
		Iterator<String> it = params.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			paramsString = paramsString + key + "=" + params.get(key) + "&";
		}
		if (paramsString.length() > 0) {
			paramsString = paramsString.substring(0, paramsString.length()-1);
		}
		
		HttpMethod getMethod = new GetMethod(url + "?" + paramsString);
		try {
			client.executeMethod(getMethod);
			System.out.println("StatusLine===" + getMethod.getStatusLine());
			response = new String(getMethod.getResponseBodyAsString());
			System.out.println("response===" + response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return response;
	}
	
	public static String HttpClientPostMethod(String url, Map<String, String> params) {
		String response = "";
		HttpClient client = new HttpClient();
		PostMethod postMethod = new UTF8PostMethod(url);
		
		Map<String, String> paraMap = PayCore.paraFilter(params);
		NameValuePair[] pairs = new NameValuePair[paraMap.size()];
		int i=0;
		Iterator<String> it = paraMap.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			pairs[i++] = new NameValuePair(key, paraMap.get(key));
		}
		
		postMethod.setRequestBody(pairs);
		try {
			client.executeMethod(postMethod);
			System.out.println("StatusLine===" + postMethod.getStatusLine());
			response = new String(postMethod.getResponseBodyAsString());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return response;
	}
	
	//重构一个post请求，避免乱码
	public static class UTF8PostMethod extends PostMethod{     
	    public UTF8PostMethod(String url){     
	    super(url);     
	    }     
	    @Override     
	    public String getRequestCharSet() {     
	        //return super.getRequestCharSet();     
	        return "UTF-8";     
	    }  
	}
	
	/**
	 * 另一种提交post请求的方式
	 * @param url
	 * @param params
	 * @return
	 */
	public static String httpConnectionPostMethod(String url, String params) {
		String response = "";
		HttpURLConnection http = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			URL actionUrl = new URL(url);
			http = (HttpURLConnection)actionUrl.openConnection();
			
			http.setRequestMethod("POST");        
	        http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
	        http.setDoOutput(true);        
	        http.setDoInput(true);
	        http.connect();
	        os = http.getOutputStream();    
	        os.write(params.getBytes("UTF-8"));
	        os.flush();
	        
	        is = http.getInputStream();
	        int size = is.available();
	        byte[] jsonBytes = new byte[size];
	        is.read(jsonBytes);
	        response = new String(jsonBytes,"UTF-8");
	        
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
				if (http != null) {
					http.disconnect();;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}
}
