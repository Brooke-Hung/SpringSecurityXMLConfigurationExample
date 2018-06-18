package com.springsecurity.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for Personal Center
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
@Controller
@RequestMapping("/personal-center")
public class PersonalCenterController {
	
	private static final String VIEW_MESSAGE_LIST = "personal-center/message-list";
	
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public String showMessages(Model model) {
		return VIEW_MESSAGE_LIST;
	}

}
