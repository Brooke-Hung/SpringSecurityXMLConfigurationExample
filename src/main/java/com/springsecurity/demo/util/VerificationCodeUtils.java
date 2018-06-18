package com.springsecurity.demo.util;

/**
 * Verification Code Utilities
 * This is for demo purposes only. Replace the implementation when used in production environment.
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public class VerificationCodeUtils {
	
	private static final String VERIFICATION_CODE = "1984";

	public static String getVerificationCode(String cellphoneNo){
		return VERIFICATION_CODE;
	}
	
	public static boolean verifyVerificationCode(String cellphoneNo, String verificationCode){
		return VERIFICATION_CODE.equals(verificationCode);
	}
	
}
