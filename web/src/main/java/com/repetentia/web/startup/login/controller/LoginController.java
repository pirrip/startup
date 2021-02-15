package com.repetentia.web.startup.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	@RequestMapping("/system/login")
	public ModelAndView login(ModelAndView mav) {
		
		mav.setViewName("/system/login");
		return mav;
	}
}
