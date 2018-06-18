package com.springsecurity.demo.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springsecurity.demo.security.userdetails.CustomUserDetailsService;
import com.springsecurity.demo.util.VerificationCodeUtils;

/**
 * Add additional logic to verify user credentials based on authentication type
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(CustomUsernamePasswordAuthenticationToken.class);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			LOGGER.error("Authentication failed: no credentials provided");
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

		CustomUsernamePasswordAuthenticationToken auth = (CustomUsernamePasswordAuthenticationToken) authentication;
		String presentedPassword = auth.getCredentials().toString();
		switch (auth.getAuthenticationType()) {
			case VIA_PASSWORD:
				if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
					LOGGER.error("Authentication failed: password does not match stored value");
					throw new BadCredentialsException(
							messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
				}
				break;
	
			case VIA_VERIFICATION_CODE:
				if (!VerificationCodeUtils.verifyVerificationCode(auth.getPrincipal().toString(), presentedPassword)) {
					LOGGER.error("Authentication failed: verification code does not match stored value");
					throw new BadCredentialsException(
							messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
				}
				break;
		}
		
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		CustomUsernamePasswordAuthenticationToken auth = (CustomUsernamePasswordAuthenticationToken) authentication;
		UserDetails loadedUser = null;

		try {
			loadedUser = this.customUserDetailsService.loadUserByUserNameOrCellphoneNo(auth.getPrincipal().toString(), auth.getAuthenticationType());
		} catch (UsernameNotFoundException notFound) {
			throw notFound;
		} catch (Exception repositoryProblem) {
			throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}

		if (loadedUser == null) {
			throw new InternalAuthenticationServiceException(
					"UserDetailsService returned null, which is an interface contract violation");
		}
		return loadedUser;
	}

}
