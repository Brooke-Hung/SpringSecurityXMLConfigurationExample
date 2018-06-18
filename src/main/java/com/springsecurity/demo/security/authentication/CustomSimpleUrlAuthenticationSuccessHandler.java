package com.springsecurity.demo.security.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.springsecurity.demo.constants.WebConstants;
import com.springsecurity.demo.security.core.CustomGrantedAuthority;

/**
 * Set default target URL based on user role
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public class CustomSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomSimpleUrlAuthenticationSuccessHandler.class);
	
	private static final String DEFAULT_VIEW_FOR_USER = "/personal-center/messages";
	private static final String DEFAULT_VIEW_FOR_ADMIN = "/admin-console/messages";
	private static final String DEFAULT_VIEW_FOR_OPERATION = "/operation-center/messages";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		List<CustomGrantedAuthority> authorities = (List<CustomGrantedAuthority>)authentication.getAuthorities();
		List<String> authorityList = new ArrayList<String>();
		
		for (CustomGrantedAuthority customGrantedAuthority : authorities) {
			authorityList.add(customGrantedAuthority.getAuthority());
		}
		
		LOGGER.debug(String.format("Username: %s, Authorities: %s", authentication.getName(), authorityList));
		
		if (authorityList.contains(WebConstants.ROLE_ADMIN)) {
			super.setDefaultTargetUrl(DEFAULT_VIEW_FOR_ADMIN);
		} else if (authorityList.contains(WebConstants.ROLE_OPERATOR)) {
			super.setDefaultTargetUrl(DEFAULT_VIEW_FOR_OPERATION);
		} else {
			super.setDefaultTargetUrl(DEFAULT_VIEW_FOR_USER);
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
