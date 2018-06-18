package com.springsecurity.demo.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.springsecurity.demo.constants.AuthenticationType;

/**
 * Set default failure URL based on authentication type
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public class CustomSimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	private static final String AUTHENTICATION_TYPE = "authenticationType";
	private static final String DEFAULT_PAGE_VIA_PASSWORD = "/account-management/loginViaPassword?error";
	private static final String DEFAULT_PAGE_VIA_VERIFICATION_CODE = "/account-management/loginViaVerificationCode?error";
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		AuthenticationType authenticationType = null;
		String authenticationTypeFromRequest = request.getParameter(AUTHENTICATION_TYPE);
		if (StringUtils.isNumeric(authenticationTypeFromRequest)) {
			int authenticationTypeInInt = Integer.valueOf(authenticationTypeFromRequest);
			authenticationType = AuthenticationType.fromTypeId(authenticationTypeInInt);
		}
		
		if (authenticationType != null && authenticationType == AuthenticationType.VIA_VERIFICATION_CODE) {
			super.setDefaultFailureUrl(DEFAULT_PAGE_VIA_VERIFICATION_CODE);
		} else {
			super.setDefaultFailureUrl(DEFAULT_PAGE_VIA_PASSWORD);
		}
		super.onAuthenticationFailure(request, response, exception);
	}

}
