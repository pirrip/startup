package com.repetentia.web.startup.config;

import java.util.Arrays;

import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.repetentia.support.log.Marker;
import com.repetentia.web.config.CommandMapperConfig;
import com.repetentia.web.config.LiquibaseConfig;
import com.repetentia.web.config.LoggingFilterConfig;
import com.repetentia.web.config.MessageSourceConfig;
import com.repetentia.web.config.MyBatisConfig;
import com.repetentia.web.config.MyBatisMapperScannerConfig;
import com.repetentia.web.config.PropertiesConfig;
import com.repetentia.web.config.TilesConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Profile({ "dev", "prod", "default" })
@Import({
        LoggingFilterConfig.class,
        LiquibaseConfig.class,
        MyBatisConfig.class,
        MyBatisMapperScannerConfig.class,
        MessageSourceConfig.class,
        PropertiesConfig.class,
        TilesConfig.class,
        CommandMapperConfig.class,
})
public class StartupConfiguration {

    public StartupConfiguration(Environment env) {
        String[] profiles = env.getActiveProfiles();
        String[] defaultProfiles = env.getDefaultProfiles();
        log.info(Marker.CONFIG, "#  active Profiles - {}", Arrays.toString(profiles));
        log.info(Marker.CONFIG, "# default Profiles - {}", Arrays.toString(defaultProfiles));
    }

    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                ((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
            }
        };
    }
}
