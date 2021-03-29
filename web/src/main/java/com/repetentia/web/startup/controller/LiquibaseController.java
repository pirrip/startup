package com.repetentia.web.startup.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.repetentia.component.liquibase.LiquiBaseChangeLogGenerator;
import com.repetentia.web.startup.service.FileUploadService;
import com.repetentia.web.startup.service.TestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LiquibaseController {
    @Autowired
    LiquiBaseChangeLogGenerator liquiBaseChangeLogGenerator;

    @GetMapping("/lb/generate")
    public String generate() throws Exception {
        if (liquiBaseChangeLogGenerator.generateChangeLog("schema")) {
            return "OK";
        } else {
            return "NOT OK";
        }
    }

    @GetMapping("/lb/export")
    public String export() throws Exception {
        if (liquiBaseChangeLogGenerator.generateChangeLog("data")) {
            return "OK";
        } else {
            return "NOT OK";
        }
    }
}
