package com.springsecurity.demo.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springsecurity.demo.constants.AuthenticationType;

/**
 * Add additional logic to set parameter names based on authentication type
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFilter.class);
	
	private static final String AUTHENTICATION_TYPE = "authenticationType";
	private static final String USERNAME_PARAMETER_VIA_PASSWORD = "userName";
	private static final String PASSWORD_PARAMETER_VIA_PASSWORD = "password";
	private static final String USERNAME_PARAMETER_VIA_VERIFICATION_CODE = "cellphoneNo";
	private static final String PASSWORD_PARAMETER_VIA_VERIFICATION_CODE = "verificationCode";	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		CustomUsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	/**
	 * Customize parameters based on the specified authentication type
	 * @param authenticationType AuthenticationType authentication type used
	 */
	private void setParamenterNames(AuthenticationType authenticationType) {
		switch (authenticationType) {
			case VIA_PASSWORD:
				super.setUsernameParameter(USERNAME_PARAMETER_VIA_PASSWORD);
				super.setPasswordParameter(PASSWORD_PARAMETER_VIA_PASSWORD);
				break;
			case VIA_VERIFICATION_CODE:
				super.setUsernameParameter(USERNAME_PARAMETER_VIA_VERIFICATION_CODE);
				super.setPasswordParameter(PASSWORD_PARAMETER_VIA_VERIFICATION_CODE);
				break;
		}
	}
	
	private CustomUsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {
		AuthenticationType authenticationType = obtainAuthenticationType(request);
		setParamenterNames(authenticationType);
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		log(username, password, authenticationType);
		return new CustomUsernamePasswordAuthenticationToken(username, password, authenticationType);
	}
	
	/**
	 * logging for debug purposes
	 * @param username String user name
	 * @param password String password or verification code
	 * @param authenticationType AuthenticationType authentication type
	 */
	private void log(String username, String password, AuthenticationType authenticationType) {
		if (authenticationType == AuthenticationType.VIA_PASSWORD) {
			LOGGER.debug(String.format("userName: %s, Password: [PROTECTED]", username));
		} else {
			LOGGER.debug(String.format("cellphoneNo: %s, verificationCode: %s", username, password));
		}
	}
	
	private AuthenticationType obtainAuthenticationType(HttpServletRequest request) {
		AuthenticationType authenticationType = null;
		String authenticationTypeFromRequest = request.getParameter(AUTHENTICATION_TYPE);
		if (StringUtils.isNumeric(authenticationTypeFromRequest)) {
			int authenticationTypeInInt = Integer.valueOf(authenticationTypeFromRequest);
			authenticationType = AuthenticationType.fromTypeId(authenticationTypeInInt);
		}
		if (authenticationType == null) {
			throw new IllegalArgumentException(String.format("No valid authenticationType from the request: %s", authenticationTypeFromRequest));
		}
        return authenticationType;
    }

}
