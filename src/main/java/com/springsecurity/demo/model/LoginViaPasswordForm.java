package com.springsecurity.demo.model;

/**
 * Login via Password Form
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public class LoginViaPasswordForm {

	private String userName;
	
	private String password;
	
	private boolean rememberMe;
	
	private int authenticationType;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
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
		builder.append("LoginViaPasswordForm [userName=");
		builder.append(userName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", rememberMe=");
		builder.append(rememberMe);
		builder.append(", authenticationType=");
		builder.append(authenticationType);
		builder.append("]");
		return builder.toString();
	}

}
