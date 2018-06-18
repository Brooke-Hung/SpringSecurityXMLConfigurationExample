package com.springsecurity.demo.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.springsecurity.demo.constants.AuthenticationType;

/**
 * Add additional field - authentication type
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private AuthenticationType authenticationType;

	public AuthenticationType getAuthenticationType() {
		return authenticationType;
	}

	public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, AuthenticationType authenticationType) {
		super(principal, credentials);
		this.authenticationType = authenticationType;
		super.setAuthenticated(false);
	}

	public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, AuthenticationType authenticationType, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.authenticationType = authenticationType;
		super.setAuthenticated(true);
	}

}
