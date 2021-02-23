package com.repetentia.web.startup.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignUpController {

    @RequestMapping("/system/signup")
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("/system/signup");
        return mav;
    }
}