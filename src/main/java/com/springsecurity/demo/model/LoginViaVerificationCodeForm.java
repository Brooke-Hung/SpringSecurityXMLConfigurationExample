package com.springsecurity.demo.model;

/**
 * Login via Verification Code Form
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public class LoginViaVerificationCodeForm {

	private String cellphoneNo;
	
	private String verificationCode;
	
	private int authenticationType;

	public String getCellphoneNo() {
		return cellphoneNo;
	}

	public void setCellphoneNo(String cellphoneNo) {
		this.cellphoneNo = cellphoneNo;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public int getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(int authenticationType) {
		this.authenticationType = authenticationType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginViaVerificationCodeForm [cellphoneNo=");
		builder.append(cellphoneNo);
		builder.append(", verificationCode=");
		builder.append(verificationCode);
		builder.append(", authenticationType=");
		builder.append(authenticationType);
		builder.append("]");
		return builder.toString();
	}

}
