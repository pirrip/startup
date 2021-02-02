package com.repetentia.web.startup.config;

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
@Profile({"dev", "prod", "default"})
@Import({ LoggingFilterConfig.class})
public class StartupConfiguration {
	
	public StartupConfiguration(Environment env) {
		String [] profiles = env.getActiveProfiles();
		String [] defaultProfiles = env.getDefaultProfiles();
		log.info(Marker.CONFIG, "# dev, prod - {}", Arrays.toString(profiles));
		log.info(Marker.CONFIG, "# default Profiles - {}", Arrays.toString(defaultProfiles));
	}
}