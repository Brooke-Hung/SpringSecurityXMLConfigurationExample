package com.springsecurity.demo.service;

import java.util.List;

import com.springsecurity.demo.entity.Account;
import com.springsecurity.demo.entity.Authority;
import com.springsecurity.demo.entity.Role;

/**
 * Service for Account Management
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public interface AccountService {
	
	Account getAccountById(Integer accountId);
	
	Account getAccountByUserName(String userName);
	
	Account getAccountByCellphoneNo(String cellphoneNo);
	
	List<Role> getRoles(Integer accountId);
	
	List<Authority> getAuthorities(Integer accountId);

}
