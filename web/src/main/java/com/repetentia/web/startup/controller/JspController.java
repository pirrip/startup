package com.repetentia.web.startup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JspController {
    @RequestMapping("/sample")
    public ModelAndView page(ModelAndView mav) {
        mav.setViewName("sample");
        return mav;
    }

    @RequestMapping("/tiles")
    public ModelAndView tiles(ModelAndView mav) {
        mav.setViewName("tiles:tiles");
        return mav;
    }
}
