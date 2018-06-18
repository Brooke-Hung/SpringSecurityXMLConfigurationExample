package com.springsecurity.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springsecurity.demo.constants.AuthenticationType;
import com.springsecurity.demo.model.LoginViaPasswordForm;
import com.springsecurity.demo.model.LoginViaVerificationCodeForm;

/**
 * Controller for Account Management
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
@Controller
@RequestMapping("/account-management")
public class AccountManagementController {
	
	private static final String VIEW_LOGIN_VIA_PASSWORD = "account-management/login-via-password";
	private static final String VIEW_LOGIN_VIA_VERIFICATION_CODE = "account-management/login-via-verification-code";
	private static final String VIEW_LOGIN_SUCCESS = "account-management/login-success";
	private static final String SAVE_ACCOUNT_RESULT = "SUCCESS";

	@RequestMapping(value = "/loginViaPassword", method = RequestMethod.GET)
	public String loginViaPassword(Model model) {
		LoginViaPasswordForm loginViaPasswordForm = new LoginViaPasswordForm();
		loginViaPasswordForm.setAuthenticationType(AuthenticationType.VIA_PASSWORD.getTypeId());
		model.addAttribute("loginViaPasswordForm", loginViaPasswordForm);
		return VIEW_LOGIN_VIA_PASSWORD;
	}
	
	@RequestMapping(value = "/loginViaVerificationCode", method = RequestMethod.GET)
	public String loginViaVerificationCode(Model model) {
		LoginViaVerificationCodeForm loginViaVerificationCodeForm = new LoginViaVerificationCodeForm();
		loginViaVerificationCodeForm.setAuthenticationType(AuthenticationType.VIA_VERIFICATION_CODE.getTypeId());
		model.addAttribute("loginViaVerificationCodeForm", loginViaVerificationCodeForm);
		return VIEW_LOGIN_VIA_VERIFICATION_CODE;
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(Model model) {
		return VIEW_LOGIN_SUCCESS;
	}
	
	@RequestMapping(value = "/saveAccount", method = RequestMethod.GET)
	@ResponseBody
	public String saveAccount(Model model) {
		return SAVE_ACCOUNT_RESULT;
	}

}
