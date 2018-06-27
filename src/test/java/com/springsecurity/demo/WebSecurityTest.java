package com.springsecurity.demo;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.springsecurity.demo.constants.AccountStatus;
import com.springsecurity.demo.constants.AuthenticationType;
import com.springsecurity.demo.security.core.CustomGrantedAuthority;
import com.springsecurity.demo.security.userdetails.CustomUserDetails;
import com.springsecurity.demo.security.userdetails.CustomUserDetailsService;

/**
 * @author Brooke
 * @since 2018/06/10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring/persistence-application-context.xml",
		"classpath:spring/web-application-context.xml", "classpath:spring/web-security.xml" })
public class WebSecurityTest {

	private static final String COOKIE_NAME_FOR_REMEMBER_ME = "springdemoRememberMe";
	private static final String COOKIE_NAME_FOR_JSESSIONID = "JSESSIONID";
	private static final String EXPECTED_COOKIE_VALUE_ON_SUCCESSFUL_LOGOUT = null;
	private static final Integer EXPECTED_COOKIE_MAX_AGE_ON_SUCCESSFUL_LOGOUT = 0;
	private static final Integer COOKIE_MAX_AGE = 604800;
	private static final String DEFAULT_VIEW_FOR_USER = "/personal-center/messages";
	private static final String DEFAULT_VIEW_FOR_ADMIN = "/admin-console/messages";
	private static final String DEFAULT_VIEW_FOR_OPERATION = "/operation-center/messages";
	private static final String VIEW_SAVE_ACCOUNT = "/account-management/saveAccount";
	private static final String VIEW_DEFAULT_LOGIN = "http://localhost/account-management/loginViaPassword";
	private static final String VIEW_LOGOUT_SUCCESS = "/";
	private static final String VIEW_LOGIN_FAILURE_VIA_PASSWORD = "/account-management/loginViaPassword?error";
	private static final String VIEW_LOGIN_FAILURE_VIA_VERIFICATION_CODE = "/account-management/loginViaVerificationCode?error";
	private static final String ACTION_PERFORM_LOGIN_VIA_VERIFICATION_CODE = "/account-management/performLoginViaVerificationCode";
	private static final String ACTION_PERFORM_LOGIN_VIA_PASSWORD = "/account-management/performLoginViaPassword";
	private static final String PARAM_USER_NAME = "userName";
	private static final String PARAM_PASSWORD = "password";
	private static final String PARAM_AUTHENTICATION_TYPE = "authenticationType";
	private static final String PARAM_CELLPHONE_NO = "cellphoneNo";
	private static final String PARAM_VERIFICATION_CODE = "verificationCode";
	private static final String PARAM_REMEMBER_ME = "rememberMe";
	private static final String USER_NAME = "MichaelJackson";
	private static final String PASSWORD = "hello";
	private static final String AUTHENTICATION_TYPE_VIA_PASSWORD = String
			.valueOf(AuthenticationType.VIA_PASSWORD.getTypeId());
	private static final String AUTHENTICATION_TYPE_VIA_VERIFICATION_CODE = String
			.valueOf(AuthenticationType.VIA_VERIFICATION_CODE.getTypeId());
	private static final String CELLPHONE_NO = "2011111111";
	private static final String VERIFICATION_CODE = "1984";
	private static final String REMEMBER_ME_DISABLED = "false";
	private static final String REMEMBER_ME_ENABLED = "true";
	private static final String INVALID_VALUE = "Invalid";
	private static final Integer ACCOUNT_ID = 1;
	private static final String FIRST_NAME = "Alan";
	private static final String LAST_NAME = "Su";
	private static final String EMAIL = "test@test.com";
	private static final String ROLE_USER = "ROLE_USER";
	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_OPERATOR = "ROLE_OPERATOR";

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsServiceForRememberMeServices;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Before
	public void setup() {
		Mockito.reset(customUserDetailsService);
		Mockito.reset(customUserDetailsServiceForRememberMeServices);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
	}

	/**
	 * 
	 * Test Authentication
	 * 
	 */

	// login via password
	@Test()
	public void testLoginViaPasswordWithRoleUser() throws Exception {
		mockUser(AuthenticationType.VIA_PASSWORD);
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, USER_NAME).param(PARAM_PASSWORD, PASSWORD)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(authenticated()).andExpect(redirectedUrl(DEFAULT_VIEW_FOR_USER));
	}

	@Test()
	public void testLoginViaPasswordWithRoleAdmin() throws Exception {
		mockAdmin(AuthenticationType.VIA_PASSWORD);
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, USER_NAME).param(PARAM_PASSWORD, PASSWORD)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(authenticated()).andExpect(redirectedUrl(DEFAULT_VIEW_FOR_ADMIN));
	}

	@Test()
	public void testLoginViaPasswordWithRoleOperator() throws Exception {
		mockOperator(AuthenticationType.VIA_PASSWORD);
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, USER_NAME).param(PARAM_PASSWORD, PASSWORD)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(authenticated()).andExpect(redirectedUrl(DEFAULT_VIEW_FOR_OPERATION));
	}

	@Test()
	public void testLoginViaPasswordWithRememberMeEnabled() throws Exception {
		mockUser(AuthenticationType.VIA_PASSWORD);
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, USER_NAME).param(PARAM_PASSWORD, PASSWORD)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_ENABLED).with(csrf()))
				.andExpect(authenticated()).andExpect(cookie().exists(COOKIE_NAME_FOR_REMEMBER_ME))
				.andExpect(cookie().maxAge(COOKIE_NAME_FOR_REMEMBER_ME, COOKIE_MAX_AGE));
	}

	@Test()
	public void testLoginViaPasswordWithInvalidCSRFToken() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, USER_NAME).param(PARAM_PASSWORD, PASSWORD)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf().useInvalidToken()))
				.andExpect(status().isForbidden()).andExpect(unauthenticated());
	}

	@Test()
	public void testLoginViaPasswordWithInvalidPassword() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, USER_NAME).param(PARAM_PASSWORD, INVALID_VALUE)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(status().isFound()).andExpect(unauthenticated())
				.andExpect(redirectedUrl(VIEW_LOGIN_FAILURE_VIA_PASSWORD));
	}

	@Test()
	public void testLoginViaPasswordWithInvalidUserName() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, INVALID_VALUE).param(PARAM_PASSWORD, PASSWORD)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(status().isFound()).andExpect(unauthenticated())
				.andExpect(redirectedUrl(VIEW_LOGIN_FAILURE_VIA_PASSWORD));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLoginViaPasswordWithInvalidAuthenticationType() throws Exception {
		this.mockMvc.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD).param(PARAM_AUTHENTICATION_TYPE, INVALID_VALUE)
				.param(PARAM_USER_NAME, USER_NAME).param(PARAM_PASSWORD, PASSWORD)
				.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()));
	}

	// login via verification code
	@Test()
	public void testLoginViaVerificationCodeWithValidCSRFToken() throws Exception {
		mockUser(AuthenticationType.VIA_VERIFICATION_CODE);
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_VERIFICATION_CODE)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_VERIFICATION_CODE)
						.param(PARAM_CELLPHONE_NO, CELLPHONE_NO).param(PARAM_VERIFICATION_CODE, VERIFICATION_CODE)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(authenticated());
	}

	@Test()
	public void testLoginViaVerificationCodeWithRememberMeEnabled() throws Exception {
		mockUser(AuthenticationType.VIA_VERIFICATION_CODE);
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_VERIFICATION_CODE)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_VERIFICATION_CODE)
						.param(PARAM_CELLPHONE_NO, CELLPHONE_NO).param(PARAM_VERIFICATION_CODE, VERIFICATION_CODE)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_ENABLED).with(csrf()))
				.andExpect(authenticated()).andExpect(cookie().maxAge(COOKIE_NAME_FOR_REMEMBER_ME, COOKIE_MAX_AGE));
	}

	@Test()
	public void testLoginViaVerificationCodeWithInvalidCSRFToken() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_VERIFICATION_CODE)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_VERIFICATION_CODE)
						.param(PARAM_CELLPHONE_NO, CELLPHONE_NO).param(PARAM_VERIFICATION_CODE, VERIFICATION_CODE)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf().useInvalidToken()))
				.andExpect(status().isForbidden()).andExpect(unauthenticated());
	}

	@Test()
	public void testLoginViaVerificationCodeWithInvalidCellphoneNo() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_VERIFICATION_CODE)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_VERIFICATION_CODE)
						.param(PARAM_CELLPHONE_NO, INVALID_VALUE).param(PARAM_VERIFICATION_CODE, VERIFICATION_CODE)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(status().isFound()).andExpect(unauthenticated())
				.andExpect(redirectedUrl(VIEW_LOGIN_FAILURE_VIA_VERIFICATION_CODE));
	}

	@Test()
	public void testLoginViaVerificationCodeWithInvalidVerificationCode() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_VERIFICATION_CODE)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_VERIFICATION_CODE)
						.param(PARAM_CELLPHONE_NO, CELLPHONE_NO).param(PARAM_VERIFICATION_CODE, INVALID_VALUE)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(status().isFound()).andExpect(unauthenticated())
				.andExpect(redirectedUrl(VIEW_LOGIN_FAILURE_VIA_VERIFICATION_CODE));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLoginViaVerificationCodeWithInvalidAuthenticationType() throws Exception {
		this.mockMvc.perform(
				post(ACTION_PERFORM_LOGIN_VIA_VERIFICATION_CODE).param(PARAM_AUTHENTICATION_TYPE, INVALID_VALUE)
						.param(PARAM_CELLPHONE_NO, CELLPHONE_NO).param(PARAM_VERIFICATION_CODE, VERIFICATION_CODE)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()));
	}

	// logout
	@Test
	public void testLogout() throws Exception {
		this.mockMvc.perform(logout("/account-management/logout")).andExpect(unauthenticated())
				.andExpect(cookie().value(COOKIE_NAME_FOR_REMEMBER_ME, EXPECTED_COOKIE_VALUE_ON_SUCCESSFUL_LOGOUT))
				.andExpect(cookie().maxAge(COOKIE_NAME_FOR_REMEMBER_ME, EXPECTED_COOKIE_MAX_AGE_ON_SUCCESSFUL_LOGOUT))
				.andExpect(cookie().value(COOKIE_NAME_FOR_JSESSIONID, EXPECTED_COOKIE_VALUE_ON_SUCCESSFUL_LOGOUT))
				.andExpect(cookie().maxAge(COOKIE_NAME_FOR_JSESSIONID, EXPECTED_COOKIE_MAX_AGE_ON_SUCCESSFUL_LOGOUT))
				.andExpect(redirectedUrl(VIEW_LOGOUT_SUCCESS));
	}

	/**
	 * 
	 * Test Authorization
	 * 
	 */

	@Test()
	@WithMockUser
	public void testAccessToPersonalCenterWithRoleUser() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_USER)).andExpect(status().isOk());
	}

	@Test()
	@WithMockAdmin
	public void testAccessToPersonalCenterWithRoleAdmin() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_USER)).andExpect(status().isOk());
	}
	
	@Test()
	@WithMockOperator
	public void testAccessToPersonalCenterWithRoleOperator() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_USER)).andExpect(status().isOk());
	}
	
	@Test()
	@WithMockNonExisting
	public void testAccessToPersonalCenterWithRoleNonexisting() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_USER)).andExpect(status().isForbidden());
	}

	@Test()
	@WithAnonymousUser
	public void testAccessToPersonalCenterWithAnonymousUser() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_USER)).andExpect(status().isFound())
				.andExpect(redirectedUrl(VIEW_DEFAULT_LOGIN));
	}

	@Test()
	@WithMockUser
	public void testAccessToSaveAccountWithRoleUser() throws Exception {
		this.mockMvc.perform(get(VIEW_SAVE_ACCOUNT)).andExpect(status().isForbidden());
	}

	@Test()
	@WithWriteAuthority
	public void testAccessToSaveAccountWithAuthorityWrite() throws Exception {
		this.mockMvc.perform(get(VIEW_SAVE_ACCOUNT)).andExpect(status().isOk());
	}

	@Test()
	@WithMockAdmin
	public void testAccessToAdminConsoleWithRoleAdmin() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_ADMIN)).andExpect(status().isOk());
	}

	@Test()
	@WithMockUser
	public void testAccessToAdminConsoleWithRoleUser() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_ADMIN)).andExpect(status().isForbidden());
	}

	@Test()
	@WithAnonymousUser
	public void testAccessToAdminConsoleWithAnonymousUser() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_ADMIN)).andExpect(status().isFound())
				.andExpect(redirectedUrl(VIEW_DEFAULT_LOGIN));
	}

	@Test()
	@WithMockOperator
	public void testAccessToOperationCenterWithRoleOperator() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_OPERATION)).andExpect(status().isOk());
	}
	
	@Test()
	@WithMockAdmin
	public void testAccessToOperationCenterWithRoleAdmin() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_OPERATION)).andExpect(status().isOk());
	}

	@Test()
	@WithMockUser
	public void testAccessToOperationCenterWithRoleUser() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_OPERATION)).andExpect(status().isForbidden());
	}

	@Test()
	@WithAnonymousUser
	public void testAccessToOperationCenterWithAnonymousUser() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_OPERATION)).andExpect(status().isFound())
				.andExpect(redirectedUrl(VIEW_DEFAULT_LOGIN));
	}

	private void mockUserDetails(String roleName, AuthenticationType authenticationType) {
		List<CustomGrantedAuthority> authorities = Arrays
				.asList(new CustomGrantedAuthority[] { new CustomGrantedAuthority(roleName) });
		CustomUserDetails customUserDetails = new CustomUserDetails(authenticationType, ACCOUNT_ID, FIRST_NAME,
				LAST_NAME, EMAIL, CELLPHONE_NO, AccountStatus.IN_USE.getStatusId(), USER_NAME,
				passwordEncoder.encode(PASSWORD), authorities);
		CustomUserDetails customUserDetailsForRememberMeServices = new CustomUserDetails(authenticationType, ACCOUNT_ID,
				FIRST_NAME, LAST_NAME, EMAIL, CELLPHONE_NO, AccountStatus.IN_USE.getStatusId(), USER_NAME,
				passwordEncoder.encode(PASSWORD), authorities);
		Mockito.when(customUserDetailsService.loadUserByUserNameOrCellphoneNo(Mockito.anyString(),
				Mockito.any(AuthenticationType.class))).thenReturn(customUserDetails);
		Mockito.when(customUserDetailsServiceForRememberMeServices.loadUserByUserNameOrCellphoneNo(Mockito.anyString(),
				Mockito.any(AuthenticationType.class))).thenReturn(customUserDetailsForRememberMeServices);
	}
	
	private void mockUser(AuthenticationType authenticationType){
		mockUserDetails(ROLE_USER, authenticationType);
	}
	
	private void mockAdmin(AuthenticationType authenticationType){
		mockUserDetails(ROLE_ADMIN, authenticationType);
	}
	
	private void mockOperator(AuthenticationType authenticationType){
		mockUserDetails(ROLE_OPERATOR, authenticationType);
	}
	
}
