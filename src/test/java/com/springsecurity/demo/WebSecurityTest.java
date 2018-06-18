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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.springsecurity.demo.constants.AuthenticationType;

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
	private static final String USER_USER_NAME = "MichaelJackson";
	private static final String USER_PASSWORD = "hello";
	private static final String ADMIN_USER_NAME = "TaylorSwift";
	private static final String ADMIN_PASSWORD = "hello";
	private static final String OPERATOR_USER_NAME = "SarahBrightman";
	private static final String OPERATOR_PASSWORD = "password";
	private static final String AUTHENTICATION_TYPE_VIA_PASSWORD = String
			.valueOf(AuthenticationType.VIA_PASSWORD.getTypeId());
	private static final String AUTHENTICATION_TYPE_VIA_VERIFICATION_CODE = String
			.valueOf(AuthenticationType.VIA_VERIFICATION_CODE.getTypeId());
	private static final String CELLPHONE_NO = "2011111111";
	private static final String VERIFICATION_CODE = "1984";
	private static final String REMEMBER_ME_DISABLED = "false";
	private static final String REMEMBER_ME_ENABLED = "true";
	private static final String INVALID_VALUE = "Invalid";

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
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
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, USER_USER_NAME).param(PARAM_PASSWORD, USER_PASSWORD)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(authenticated()).andExpect(redirectedUrl(DEFAULT_VIEW_FOR_USER));
	}

	@Test()
	public void testLoginViaPasswordWithRoleAdmin() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, ADMIN_USER_NAME).param(PARAM_PASSWORD, ADMIN_PASSWORD)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(authenticated()).andExpect(redirectedUrl(DEFAULT_VIEW_FOR_ADMIN));
	}

	@Test()
	public void testLoginViaPasswordWithRoleOperator() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, OPERATOR_USER_NAME).param(PARAM_PASSWORD, OPERATOR_PASSWORD)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(authenticated()).andExpect(redirectedUrl(DEFAULT_VIEW_FOR_OPERATION));
	}

	@Test()
	public void testLoginViaPasswordWithRememberMeEnabled() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, USER_USER_NAME).param(PARAM_PASSWORD, USER_PASSWORD)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_ENABLED).with(csrf()))
				.andExpect(authenticated()).andExpect(cookie().exists(COOKIE_NAME_FOR_REMEMBER_ME))
				.andExpect(cookie().maxAge(COOKIE_NAME_FOR_REMEMBER_ME, COOKIE_MAX_AGE));
	}

	@Test()
	public void testLoginViaPasswordWithInvalidCSRFToken() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, USER_USER_NAME).param(PARAM_PASSWORD, USER_PASSWORD)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf().useInvalidToken()))
				.andExpect(status().isForbidden()).andExpect(unauthenticated());
	}

	@Test()
	public void testLoginViaPasswordWithInvalidPassword() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, USER_USER_NAME).param(PARAM_PASSWORD, INVALID_VALUE)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(status().isFound()).andExpect(unauthenticated())
				.andExpect(redirectedUrl(VIEW_LOGIN_FAILURE_VIA_PASSWORD));
	}

	@Test()
	public void testLoginViaPasswordWithInvalidUserName() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_PASSWORD)
						.param(PARAM_USER_NAME, INVALID_VALUE).param(PARAM_PASSWORD, USER_PASSWORD)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(status().isFound()).andExpect(unauthenticated())
				.andExpect(redirectedUrl(VIEW_LOGIN_FAILURE_VIA_PASSWORD));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLoginViaPasswordWithInvalidAuthenticationType() throws Exception {
		this.mockMvc.perform(post(ACTION_PERFORM_LOGIN_VIA_PASSWORD).param(PARAM_AUTHENTICATION_TYPE, INVALID_VALUE)
				.param(PARAM_USER_NAME, USER_USER_NAME).param(PARAM_PASSWORD, USER_PASSWORD)
				.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()));
	}

	// login via verification code
	@Test()
	public void testLoginViaVerificationCodeWithValidCSRFToken() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_VERIFICATION_CODE)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_VERIFICATION_CODE)
						.param(PARAM_CELLPHONE_NO, CELLPHONE_NO).param(PARAM_VERIFICATION_CODE, VERIFICATION_CODE)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_DISABLED).with(csrf()))
				.andExpect(authenticated());
	}

	@Test()
	public void testLoginViaVerificationCodeWithRememberMeEnabled() throws Exception {
		this.mockMvc
				.perform(post(ACTION_PERFORM_LOGIN_VIA_VERIFICATION_CODE)
						.param(PARAM_AUTHENTICATION_TYPE, AUTHENTICATION_TYPE_VIA_VERIFICATION_CODE)
						.param(PARAM_CELLPHONE_NO, CELLPHONE_NO).param(PARAM_VERIFICATION_CODE, VERIFICATION_CODE)
						.param(PARAM_REMEMBER_ME, REMEMBER_ME_ENABLED).with(csrf()))
				.andExpect(cookie().maxAge(COOKIE_NAME_FOR_REMEMBER_ME, COOKIE_MAX_AGE));
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
	@WithMockUser(username = "Alan", roles = { "USER" })
	public void testAccessToPersonalCenterWithRoleUser() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_USER)).andExpect(status().isOk());
	}

	@Test()
	@WithMockUser(username = "Alan", roles = { "ADMIN" })
	public void testAccessToPersonalCenterWithRoleAdmin() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_USER)).andExpect(status().isForbidden());
	}

	@Test()
	@WithAnonymousUser
	public void testAccessToPersonalCenterWithAnonymousUser() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_USER)).andExpect(status().isFound())
				.andExpect(redirectedUrl(VIEW_DEFAULT_LOGIN));
	}

	@Test()
	@WithMockUser(username = "Alan", roles = { "USER" })
	public void testAccessToSaveAccountWithRoleUser() throws Exception {
		this.mockMvc.perform(get(VIEW_SAVE_ACCOUNT)).andExpect(status().isForbidden());
	}

	@Test()
	@WithMockUser(username = "Alan", authorities = { "WRITE" })
	public void testAccessToSaveAccountWithAuthorityWrite() throws Exception {
		this.mockMvc.perform(get(VIEW_SAVE_ACCOUNT)).andExpect(status().isOk());
	}

	@Test()
	@WithMockUser(username = "Alex", roles = { "ADMIN" })
	public void testAccessToAdminConsoleWithRoleAdmin() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_ADMIN)).andExpect(status().isOk());
	}

	@Test()
	@WithMockUser(username = "Alex", roles = { "USER" })
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
	@WithMockUser(username = "Anna", roles = { "OPERATOR" })
	public void testAccessToOperationCenterWithRoleOperator() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_OPERATION)).andExpect(status().isOk());
	}

	@Test()
	@WithMockUser(username = "Anna", roles = { "USER" })
	public void testAccessToOperationCenterWithRoleUser() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_OPERATION)).andExpect(status().isForbidden());
	}

	@Test()
	@WithAnonymousUser
	public void testAccessToOperationCenterWithAnonymousUser() throws Exception {
		this.mockMvc.perform(get(DEFAULT_VIEW_FOR_OPERATION)).andExpect(status().isFound())
				.andExpect(redirectedUrl(VIEW_DEFAULT_LOGIN));
	}

}
