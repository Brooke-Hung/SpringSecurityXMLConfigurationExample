package com.springsecurity.demo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.codec.Hex;

/**
 * MessageDigest Utilities to generate hash using MD5 algorithm
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public class MessageDigestUtils {
	
	private static final String ALGORITHM = "MD5";

	public static String generateHash(String text){
		String hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
			hash = new String(Hex.encode(digest.digest(text.getBytes())));
		} catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException("No algorithms available!");
		}

		return hash;
	}

}
