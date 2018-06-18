package com.springsecurity.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for Homepage
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
@Controller
public class HomepageController {
	
	private static final String VIEW_HOMEPAGE = "homepage";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(Model model) {
		return VIEW_HOMEPAGE;
	}

}
