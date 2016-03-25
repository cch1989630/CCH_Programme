package com.cch.programme.weixin.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WeixinUtil {
	
	/**
	 * ��֤΢�Ž���ʱ����Ч��
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
		//���������������ֵ�����
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
		//ʹ��sha1����ν��м���
		String encryptString = SHA1.encode(needEncryptString);
		
		return encryptString;
		
	}
	
}
