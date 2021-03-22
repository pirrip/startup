package com.repetentia.web.startup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AdminLteSampleController {

    @RequestMapping("/lte/sample")
    public ModelAndView page(ModelAndView mav) {
        log.info("/lte/sample");
        mav.setViewName("lte/sample");
        return mav;
    }
}
