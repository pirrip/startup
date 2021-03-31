package com.repetentia.component.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.util.MimeTypeUtils;

import com.repetentia.component.log.RtaLogFactory;
import com.repetentia.support.log.Marker;

public class AjaxAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    private static final Logger log = RtaLogFactory.getLogger(AjaxAuthenticationEntryPoint.class);

    public AjaxAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.info(Marker.AUTH, "# {} - AJAX ENTRY POINT - {}", request.getRequestURI(), authException.getMessage());

        String accept = ((HttpServletRequest) request).getHeader(HttpHeaders.ACCEPT);
        log.info(Marker.AUTH, "# HTTP HEADER FOR AJAX REQUEST - Accept: {}", accept);
        if (accept != null && accept.indexOf(MimeTypeUtils.APPLICATION_JSON_VALUE) > -1) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHENTICATED (May Be Session Expired)");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\r\n"
                    + "    \"status\": 403,\r\n"
                    + "    \"error\": \"UNAUTHORIZED\",\r\n"
                    + "    \"message\": \"비인가\",\r\n"
                    + "    \"path\": \"/web/jwt\"\r\n"
                    + "}");
            response.getWriter().flush();
        } else {
            super.commence(request, response, authException);
        }
    }
}
