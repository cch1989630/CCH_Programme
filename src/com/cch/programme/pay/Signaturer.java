package com.cch.programme.pay;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;


/**
 * @author cch
 * 对字符串进行签名
 */
public class Signaturer {
	
	public static byte[] sign(byte[] priKeyText, String plainText) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64CCH.decode(priKeyText));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey prikey = keyf.generatePrivate(priPKCS8);
			
			Signature signet = Signature.getInstance("MD5withRSA");
			signet.initSign(prikey);
			signet.update(plainText.getBytes());
			
			byte[] signed = Base64CCH.encodeToByte(signet.sign());
			
			return signed;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
