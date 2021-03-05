package com.repetentia.component.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.util.MimeTypeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public JwtAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.info("# {} - JWT ENTRY POINT - {}", request.getRequestURI(), authException.getMessage());

        String accept = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
        log.trace("# HTTP HEADER FOR JWT REQUEST - Accept: {}", accept);
        if (accept != null && accept.indexOf(MimeTypeUtils.APPLICATION_JSON_VALUE) > -1) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHENTICATED (May Be Session Expired)");
        } else {
            super.commence(request, response, authException);
        }
    }
}
