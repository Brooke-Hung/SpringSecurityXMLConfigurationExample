package com.springsecurity.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springsecurity.demo.constants.AccountStatus;
import com.springsecurity.demo.dao.AccountDao;
import com.springsecurity.demo.dao.AccountRoleDao;
import com.springsecurity.demo.dao.AuthorityDao;
import com.springsecurity.demo.dao.RoleAuthorityDao;
import com.springsecurity.demo.dao.RoleDao;
import com.springsecurity.demo.entity.Account;
import com.springsecurity.demo.entity.AccountCriteria;
import com.springsecurity.demo.entity.AccountRole;
import com.springsecurity.demo.entity.AccountRoleCriteria;
import com.springsecurity.demo.entity.Authority;
import com.springsecurity.demo.entity.AuthorityCriteria;
import com.springsecurity.demo.entity.Role;
import com.springsecurity.demo.entity.RoleAuthority;
import com.springsecurity.demo.entity.RoleAuthorityCriteria;
import com.springsecurity.demo.entity.RoleCriteria;
import com.springsecurity.demo.service.AccountService;

/**
 * AccountService Implementation
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private AuthorityDao authorityDao;
	
	@Autowired
	private AccountRoleDao accountRoleDao;
	
	@Autowired
	private RoleAuthorityDao roleAuthorityDao;
	
	/* (non-Javadoc)
	 * @see com.springsecurity.demo.service.AccountService#getAccountById(java.lang.Integer)
	 */
	public Account getAccountById(Integer accountId) {
		return accountDao.selectByPrimaryKey(accountId);
	}

	/* (non-Javadoc)
	 * @see com.springsecurity.demo.service.AccountService#getAccountByUserName(java.lang.String)
	 */
	public Account getAccountByUserName(String userName) {
		Account account = null;
		AccountCriteria accountCriteria = new AccountCriteria();
		AccountCriteria.Criteria criteria = accountCriteria.createCriteria();
		criteria.andUserNameEqualTo(userName);
		criteria.andStatusEqualTo(AccountStatus.IN_USE.getStatusId());
		List<Account> accounts = accountDao.selectByExample(accountCriteria);
		if (accounts != null && accounts.size() == 1) {
			account = accounts.get(0);
		}
		return account;
	}

	public Account getAccountByCellphoneNo(String cellphoneNo) {
		Account account = null;
		AccountCriteria accountCriteria = new AccountCriteria();
		AccountCriteria.Criteria criteria = accountCriteria.createCriteria();
		criteria.andCellphoneNoEqualTo(cellphoneNo);
		criteria.andStatusEqualTo(AccountStatus.IN_USE.getStatusId());
		List<Account> accounts = accountDao.selectByExample(accountCriteria);
		if (accounts != null && accounts.size() == 1) {
			account = accounts.get(0);
		}
		return account;
	}

	/* (non-Javadoc)
	 * @see com.springsecurity.demo.service.AccountService#getRoles(java.lang.Integer)
	 */
	public List<Role> getRoles(Integer accountId) {
		AccountRoleCriteria accountRoleCriteria = new AccountRoleCriteria();
		AccountRoleCriteria.Criteria accountRoleCriteriaDetails = accountRoleCriteria.createCriteria();
		accountRoleCriteriaDetails.andAccountIdEqualTo(accountId);
		List<AccountRole> accountRoles = accountRoleDao.selectByExample(accountRoleCriteria);
		List<Byte> roleIds = new ArrayList<Byte>();
		for (AccountRole accountRole : accountRoles) {
			roleIds.add(accountRole.getRoleId());
		}
		
		RoleCriteria roleCriteria = new RoleCriteria();
		RoleCriteria.Criteria roleCriteriaDetails = roleCriteria.createCriteria();
		roleCriteriaDetails.andRoleIdIn(roleIds);
		return roleDao.selectByExample(roleCriteria);
	}

	/* (non-Javadoc)
	 * @see com.springsecurity.demo.service.AccountService#getAuthorities(java.lang.Integer)
	 */
	public List<Authority> getAuthorities(Integer accountId) {
		AccountRoleCriteria accountRoleCriteria = new AccountRoleCriteria();
		AccountRoleCriteria.Criteria accountRoleCriteriaDetails = accountRoleCriteria.createCriteria();
		accountRoleCriteriaDetails.andAccountIdEqualTo(accountId);
		List<AccountRole> accountRoles = accountRoleDao.selectByExample(accountRoleCriteria);
		List<Byte> roleIds = new ArrayList<Byte>();
		for (AccountRole accountRole : accountRoles) {
			roleIds.add(accountRole.getRoleId());
		}
		
		RoleAuthorityCriteria roleAuthorityCriteria = new RoleAuthorityCriteria();
		RoleAuthorityCriteria.Criteria roleAuthorityCriteriaDetails = roleAuthorityCriteria.createCriteria();
		roleAuthorityCriteriaDetails.andRoleIdIn(roleIds);
		List<RoleAuthority> roleAuthorities = roleAuthorityDao.selectByExample(roleAuthorityCriteria);
		
		List<Integer> authorityIds = new ArrayList<Integer>();
		for (RoleAuthority roleAuthority : roleAuthorities) {
			authorityIds.add(roleAuthority.getAuthorityId());
		}
		
		AuthorityCriteria authorityCriteria = new AuthorityCriteria();
		AuthorityCriteria.Criteria authorityCriteriaDetails = authorityCriteria.createCriteria();
		authorityCriteriaDetails.andAuthorityIdIn(authorityIds);
		return authorityDao.selectByExample(authorityCriteria);
	}

}
