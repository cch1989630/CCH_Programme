package com.cch.programme.pay;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;


/**
 * 校验通过rsa签名是否合法
 * @author yy
 *
 */ 
public class SignProvider {
	
	/**
	 * 校验数字签名,此方法不会抛出任务异常,成功返回true,失败返回false,要求全部参数不能为空
	 * @param pubKeyText	公钥
	 * @param plainText		明文
	 * @param signText		数字签名的密文
	 * @return
	 */
	public static boolean verify(byte[] pubKeyText, String plainText, byte[] signText) {
		
		try {
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Base64CCH.decode(pubKeyText));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			
			PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
			
			byte[] signed = Base64CCH.decode(signText);
			Signature signatureChecker = Signature.getInstance("MD5withRSA");
			signatureChecker.initVerify(pubKey);
			signatureChecker.update(plainText.getBytes());
			
			if(signatureChecker.verify(signed)) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
}
