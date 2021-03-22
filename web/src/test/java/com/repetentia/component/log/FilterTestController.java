package com.repetentia.component.log;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FilterTestController {
    @ResponseBody
    @PostMapping("/test")
    public String test(@RequestBody TBean bean) {
        log.info("## /test - for filter - {}", bean);

        return "{\"code\":404}";
    }
}
