package com.repetentia.web.startup.config.test;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.repetentia.support.log.Marker;
import com.repetentia.web.config.LoggingFilterConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Profile("test")
//@Import({ LoggingFilterConfig.class})
public class StartupConfiguration {
	public StartupConfiguration(Environment env) {
		String [] profiles = env.getActiveProfiles();
		log.info(Marker.CONFIG, "# test - {}", Arrays.toString(profiles));
	}
}
