package com.repetentia.web.config;

import org.springframework.context.annotation.Bean;

import com.repetentia.component.log.RtaLoggingFilter;
import com.repetentia.support.log.Marker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingFilterConfig {
    @Bean
    public RtaLoggingFilter logFilter() {
        RtaLoggingFilter filter = new RtaLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        log.info(Marker.CONFIG, "# REGISTERING FILTER");
        return filter;
    }
}
