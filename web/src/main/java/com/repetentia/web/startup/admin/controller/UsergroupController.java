package com.repetentia.web.startup.admin.controller;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.repetentia.component.log.RtaLogFactory;

@Controller
public class UsergroupController {
    private static final Logger log = RtaLogFactory.getLogger(UsergroupController.class);

    @RequestMapping("/admin/usergroup")
    public ModelAndView page(ModelAndView mav) {
        mav.setViewName("tiles:admin/usergroup");
        return mav;
    }

}
