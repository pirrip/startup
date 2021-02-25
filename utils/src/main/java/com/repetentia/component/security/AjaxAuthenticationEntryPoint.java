package com.repetentia.component.security;

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
public class AjaxAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public AjaxAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String accept = ((HttpServletRequest) request).getHeader(HttpHeaders.ACCEPT);
        log.trace("# HTTP HEADER FOR AJAX REQUEST - Accept: {}", accept);
        if (accept != null && accept.indexOf(MimeTypeUtils.APPLICATION_JSON_VALUE) > -1) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHENTICATED (May Be Session Expired)");
        } else {
            super.commence(request, response, authException);
        }
    }
}
