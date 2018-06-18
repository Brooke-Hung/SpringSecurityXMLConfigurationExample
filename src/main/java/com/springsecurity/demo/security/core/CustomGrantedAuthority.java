package com.springsecurity.demo.security.core;

import org.springframework.security.core.GrantedAuthority;

/**
 * Custom GrantedAuthority
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public class CustomGrantedAuthority implements GrantedAuthority {
	
	private String name;

	public CustomGrantedAuthority(String name) {
		this.name = name;
	}

	public String getAuthority() {
		return this.name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomGrantedAuthority [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
