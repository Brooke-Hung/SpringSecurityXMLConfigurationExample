package com.springsecurity.demo.security.userdetails.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springsecurity.demo.constants.AuthenticationType;
import com.springsecurity.demo.entity.Account;
import com.springsecurity.demo.entity.Authority;
import com.springsecurity.demo.entity.Role;
import com.springsecurity.demo.security.core.CustomGrantedAuthority;
import com.springsecurity.demo.security.userdetails.CustomUserDetails;
import com.springsecurity.demo.security.userdetails.CustomUserDetailsService;
import com.springsecurity.demo.service.AccountService;

/**
 * Custom UserDetailsService
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
@Service
@Transactional
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);

	@Value("${security.role.prefix}")
	private String rolePrefix;

	@Autowired
	private AccountService accountService;

	public CustomUserDetails loadUserByUserNameOrCellphoneNo(String userNameOrCellphoneNo,
			AuthenticationType authenticationType) throws UsernameNotFoundException {
		CustomUserDetails customUserDetails = null;
		if (StringUtils.isNotBlank(userNameOrCellphoneNo) && authenticationType != null) {
			Account account = null;
			switch (authenticationType) {
			case VIA_PASSWORD:
				account = accountService.getAccountByUserName(userNameOrCellphoneNo);
				break;

			case VIA_VERIFICATION_CODE:
				account = accountService.getAccountByCellphoneNo(userNameOrCellphoneNo);
				break;
			}
			if (account != null) {
				List<CustomGrantedAuthority> customGrantedAuthorities = new ArrayList<CustomGrantedAuthority>();
				List<Role> roles = accountService.getRoles(account.getAccountId());
				
				//convert role names to upper case and add prefix to them
				for (Role role : roles) {
					customGrantedAuthorities
							.add(new CustomGrantedAuthority(rolePrefix + role.getName().toUpperCase()));
				}
				
				//add authorities
				List<Authority> authorities = accountService.getAuthorities(account.getAccountId());
				for (Authority authority : authorities) {
					customGrantedAuthorities.add(new CustomGrantedAuthority(authority.getName().toUpperCase()));
				}
				
				customUserDetails = new CustomUserDetails(authenticationType, account.getAccountId(),
						account.getFirstName(), account.getLastName(), account.getEmail(), account.getCellphoneNo(),
						account.getStatus(), account.getUserName(), account.getPassword(), customGrantedAuthorities);

				LOGGER.debug(String.format("UserNameOrCellphoneNo: %s, AuthenticationType: %s, Authorities: %s",
						userNameOrCellphoneNo, authenticationType, customGrantedAuthorities));
			}
		}

		return customUserDetails;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		throw new UnsupportedOperationException("loadUserByUsername is not supported by CustomUserDetailsServiceImpl");
	}

}
