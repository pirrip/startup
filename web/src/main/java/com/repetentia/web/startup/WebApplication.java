package com.repetentia.web.startup;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ComponentScan(basePackages = { "com.repetentia.web.startup" })
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WebApplication.class);
		log.info("parameter", Arrays.toString(args));
//		String profile = System.getProperty("spring.profiles.active");
//		System.out.println(profile);
//		System.setProperty("spring.profiles.default", "prod");
		String def  = System.getProperty("spring.profiles.default");
		log.info("# default profile  : {}" , def);
		
//		if (profile == null) {
//			app.setAdditionalProfiles("local");
//		} else {
//			app.setAdditionalProfiles(profile);
//		}

		app.setDefaultProperties(Collections.singletonMap("server.port", "8090"));
		app.run(args);

	}

}
