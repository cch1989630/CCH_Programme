package com.cch.programme.pay;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;


/**
 * @author cch
 *
 */
public class KeyGenerater {
	private byte[] priKey;
	private byte[] pubKey;
	
	public void generater(String tokenKey) {
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
			SecureRandom secrand = new SecureRandom();
			secrand.setSeed(tokenKey.getBytes());
			keygen.initialize(1024, secrand);
			KeyPair keys = keygen.generateKeyPair();
			
			PublicKey generatePubKey = keys.getPublic();
			PrivateKey generatePriKey = keys.getPrivate();
			
			pubKey = Base64CCH.encodeToByte(generatePubKey.getEncoded());
			priKey = Base64CCH.encodeToByte(generatePriKey.getEncoded());
			
			//pubKey = Base64.encodeBase64(generatePubKey.getEncoded());
			//priKey = Base64.encodeBase64(generatePriKey.getEncoded());
			
			System.out.println("pubKey = " + new String(pubKey));
			System.out.println("priKey = " + new String(priKey));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] getPriKey() {
		return priKey;
	}

	public byte[] getPubKey() {
		return pubKey;
	}
}
