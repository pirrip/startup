package com.repetentia.component.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.repetentia.component.log.RtaLogFactory;

public class RtaLoginFailureHandler implements AuthenticationFailureHandler {
    private static final Logger log = RtaLogFactory.getLogger(RtaLoginFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.trace("LOGIN FAILED");
    }

}
