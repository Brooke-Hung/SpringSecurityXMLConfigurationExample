package com.springsecurity.demo.security.authentication.logout;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

/**
 * Clear cookies on successful logout and customize logout success URL
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public class CookieClearingLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CookieClearingLogoutSuccessHandler.class);

	private List<String> cookiesToClear;
	
	private String logoutSuccessUrl;

	public CookieClearingLogoutSuccessHandler(List<String> cookiesToClear, String logoutSuccessUrl) {
		this.cookiesToClear = cookiesToClear;
		this.logoutSuccessUrl = logoutSuccessUrl;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {		
		clearCookies(request, response);		
		LOGGER.debug(String.format("Setting default target Url to %s", logoutSuccessUrl));
		super.setDefaultTargetUrl(logoutSuccessUrl);
		super.onLogoutSuccess(request, response, authentication);
	}
	
	/**
	 * clear cookies on successful logout using the same cookie path as AbstractRememberMeServices
	 * TODO: Use CookieClearingLogoutHandler instead once the cookie path fix is released
	 * @param request
	 * @param response
	 */
	private void clearCookies(HttpServletRequest request, HttpServletResponse response){
		if (cookiesToClear != null) {
			for (String cookieName : cookiesToClear) {
				Cookie cookie = new Cookie(cookieName, null);
				String cookiePath = getCookiePath(request);
				LOGGER.debug(String.format("Clearing Cookie(CookieName: %s, CookiePath: %s)", cookieName, cookiePath));
				cookie.setPath(cookiePath);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}

	private String getCookiePath(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return contextPath.length() > 0 ? contextPath : "/";
	}
}
