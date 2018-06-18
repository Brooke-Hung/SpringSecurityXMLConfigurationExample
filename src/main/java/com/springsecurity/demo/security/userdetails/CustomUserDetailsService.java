package com.springsecurity.demo.security.userdetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.springsecurity.demo.constants.AuthenticationType;

/**
 * Custom UserDetailsService
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public interface CustomUserDetailsService extends UserDetailsService{
	
	CustomUserDetails loadUserByUserNameOrCellphoneNo(String userNameOrCellphoneNo, AuthenticationType authenticationType) throws UsernameNotFoundException;
	
}
