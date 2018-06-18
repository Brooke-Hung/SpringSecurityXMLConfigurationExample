package com.springsecurity.demo.security.authentication.rememberme;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

import com.springsecurity.demo.constants.AuthenticationType;
import com.springsecurity.demo.security.userdetails.CustomUserDetails;
import com.springsecurity.demo.security.userdetails.CustomUserDetailsService;
import com.springsecurity.demo.util.MessageDigestUtils;

/**
 * Customized implementation of the <tt>TokenBasedRememberMeServices</tt>.
 * Some methods are copied from <tt>TokenBasedRememberMeServices</tt>.
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public class CustomRememberMeServices extends AbstractRememberMeServices {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomRememberMeServices.class);
	private static final String DELIMETER = ":";
	private static final String AUTHENTICATION_TYPE = "authenticationType";
	private CustomUserDetailsService customUserDetailsService;

	/**
	 * @param key
	 * @param userDetailsService
	 */
	public CustomRememberMeServices(String key, CustomUserDetailsService customUserDetailsService) {
		super(key, customUserDetailsService);
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication successfulAuthentication) {

		AuthenticationType authenticationType = retrieveAuthenticationType(request, successfulAuthentication);
		String userNameOrCellphoneNo = retrieveUserNameOrCellphoneNo(successfulAuthentication, authenticationType);
		String password = retrievePassword(successfulAuthentication);

		if (StringUtils.isBlank(userNameOrCellphoneNo)) {
			LOGGER.debug("Unable to retrieve username");
			return;
		}

		if (StringUtils.isBlank(password)) {
			CustomUserDetails customUserDetails = this.customUserDetailsService
					.loadUserByUserNameOrCellphoneNo(userNameOrCellphoneNo, authenticationType);
			LOGGER.debug(String.format("CustomUserDetails: %s", customUserDetails));
			if (customUserDetails == null || StringUtils.isBlank(customUserDetails.getPassword())) {
				LOGGER.debug(String.format("Unable to obtain password for user: %s, authenticationType: %s", userNameOrCellphoneNo, authenticationType));
				return;
			}
			password = customUserDetails.getPassword();
		}

		long expiryTime = getExpiryTime();

		String signatureValue = makeTokenSignature(expiryTime, userNameOrCellphoneNo, password, authenticationType);

		setCookie(new String[] { userNameOrCellphoneNo, Long.toString(expiryTime), signatureValue,
				String.valueOf(authenticationType.getTypeId()) }, getTokenValiditySeconds(), request, response);

		LOGGER.debug(
				String.format("Added remember-me cookie for user '%s', expiry: '%s'", userNameOrCellphoneNo, new Date(expiryTime)));
	}

	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request,
			HttpServletResponse response) throws RememberMeAuthenticationException, UsernameNotFoundException {
		if (cookieTokens.length != 4) {
			throw new InvalidCookieException(String.format("Cookie token did not contain 4 tokens, but contained %s",
					Arrays.asList(cookieTokens)));
		}

		long tokenExpiryTime;

		try {
			tokenExpiryTime = Long.parseLong(cookieTokens[1]);
		} catch (NumberFormatException ex) {
			throw new InvalidCookieException(
					String.format("Cookie token[1] did not contain a valid number (contained %s)", cookieTokens[1]));
		}

		if (isTokenExpired(tokenExpiryTime)) {
			throw new InvalidCookieException(
					String.format("Cookie token[1] has expired (expired on '%s'; current time is '%s')",
							new Date(tokenExpiryTime), new Date()));
		}

		AuthenticationType authenticationType = AuthenticationType.fromTypeId(Integer.parseInt(cookieTokens[3]));
		UserDetails user = customUserDetailsService.loadUserByUserNameOrCellphoneNo(cookieTokens[0],
				authenticationType);
		String expectedTokenSignature = makeTokenSignature(tokenExpiryTime, user.getUsername(), user.getPassword(),
				authenticationType);

		if (!StringUtils.equals(expectedTokenSignature, cookieTokens[2])) {
			throw new InvalidCookieException(String.format("Cookie token[2] contained signature '%s' but expected '%s'",
					cookieTokens[2], expectedTokenSignature));
		}

		return user;
	}

	/**
	 * Calculates the digital signature to be put in the cookie. Default value
	 * is MD5 ("username:authenticationType:tokenExpiryTime:password:key")
	 */
	protected String makeTokenSignature(long tokenExpiryTime, String username, String password,
			AuthenticationType authenticationType) {
		StringBuilder sbToken = new StringBuilder();
		sbToken.append(username);
		sbToken.append(DELIMETER);
		sbToken.append(authenticationType.getTypeId());
		sbToken.append(DELIMETER);
		sbToken.append(tokenExpiryTime);
		sbToken.append(DELIMETER);
		sbToken.append(password);
		sbToken.append(DELIMETER);
		sbToken.append(getKey());
		LOGGER.debug(String.format("Token: %s", sbToken.toString()));
		return MessageDigestUtils.generateHash(sbToken.toString());
	}

	protected boolean isTokenExpired(long tokenExpiryTime) {
		return tokenExpiryTime < System.currentTimeMillis();
	}

	private long getExpiryTime() {
		int tokenLifetime = getTokenValiditySeconds();
		long expiryTime = System.currentTimeMillis();
		expiryTime += 1000L * (tokenLifetime < 0 ? TWO_WEEKS_S : tokenLifetime);
		return expiryTime;
	}

	private String retrieveUserNameOrCellphoneNo(Authentication authentication, AuthenticationType authenticationType) {
		String userNameOrCellphoneNo = null;
		if (isInstanceOfCustomUserDetails(authentication)) {
			if (AuthenticationType.VIA_PASSWORD == authenticationType) {
				userNameOrCellphoneNo = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
			} else {
				userNameOrCellphoneNo = ((CustomUserDetails) authentication.getPrincipal()).getCellphoneNo();
			}
		} else {
			userNameOrCellphoneNo = authentication.getPrincipal().toString();
		}
		return userNameOrCellphoneNo;
	}

	private String retrievePassword(Authentication authentication) {
		if (isInstanceOfCustomUserDetails(authentication)) {
			return ((CustomUserDetails) authentication.getPrincipal()).getPassword();
		} else {
			if (authentication.getCredentials() == null) {
				return null;
			}
			return authentication.getCredentials().toString();
		}
	}

	private AuthenticationType retrieveAuthenticationType(HttpServletRequest request, Authentication authentication) {
		if (isInstanceOfCustomUserDetails(authentication)) {
			return ((CustomUserDetails) authentication.getPrincipal()).getAuthenticationType();
		} else {
			AuthenticationType authenticationType = null;
			String authenticationTypeFromRequest = request.getParameter(AUTHENTICATION_TYPE);
			if (StringUtils.isNumeric(authenticationTypeFromRequest)) {
				int authenticationTypeInInt = Integer.valueOf(authenticationTypeFromRequest);
				authenticationType = AuthenticationType.fromTypeId(authenticationTypeInInt);
			}
			return authenticationType;
		}
	}

	private boolean isInstanceOfCustomUserDetails(Authentication authentication) {
		return authentication.getPrincipal() instanceof CustomUserDetails;
	}
}
