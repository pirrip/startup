package com.repetentia.web.config;

import java.security.Principal;
import java.util.UUID;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

public class HttpRequestConfig {
    @Bean
    public ServletRequestListener requestListener() {
        return new ServletRequestListener() {

            @Override
            public void requestInitialized(ServletRequestEvent sre) {
                HttpServletRequest hsr = (HttpServletRequest) sre.getServletRequest();
                Principal principal = hsr.getUserPrincipal();
                HttpSession session = hsr.getSession(true);
                String principalName = getName(principal);
                String ip = hsr.getRemoteAddr();
                String id = session.getId();
                UUID uuid = UUID.randomUUID();
                ThreadContext.put("principal", principalName);
                ThreadContext.put("id", id);
                ThreadContext.put("ip", ip);
                ThreadContext.put("uuid", uuid.toString());
            }

            @Override
            public void requestDestroyed(ServletRequestEvent sre) {
                ThreadContext.clearAll();
            }
        };
    }

    private String getName(Principal principal) {
        if (ObjectUtils.isEmpty(principal)) {
            return "anonymous";
        } else {
            return principal.getName();
        }
    }
}
