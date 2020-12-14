package com.repetentia.web.startup.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.repetentia.web.startup.service.FileUploadService;
import com.repetentia.web.startup.service.TestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {
    private String folder = "D:/rta/folder";
    @Autowired
    TestService testService;
    @Autowired
    FileUploadService fileUploadService;

    @GetMapping("/test")
	public ModelAndView test(ModelAndView mav, HttpServletRequest request) {
	    String id = request.getSession().getId();
	    log.info("# test session id  - {}", id);
	    testService.test();
	    mav.setViewName("test");
		return mav;
	}

	@PostMapping("/upload")
	public void upload(List<MultipartFile> files) {
	    log.info("# file : {}", files);
	    fileUploadService.upload(files);
	}
	
}
